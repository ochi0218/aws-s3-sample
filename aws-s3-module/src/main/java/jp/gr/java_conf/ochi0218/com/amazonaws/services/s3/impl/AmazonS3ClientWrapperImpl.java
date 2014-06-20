package jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.impl;

import static com.google.common.base.Preconditions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.AmazonS3ClientWrapper;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.AmazonS3Downloader;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.AmazonS3Remover;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.AmazonS3Uploader;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.config.AmazonApiConfiguration;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.config.AmazonS3Configuration;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.context.AmazonS3UploadDirectoryContext;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.dto.AmazonS3UploadStatus;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.task.AmazonS3UploaderTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * {@link AmazonS3ClientWrapper}の実装クラス。
 */
public class AmazonS3ClientWrapperImpl implements AmazonS3ClientWrapper {
    /** ログ */
    private static final Log LOGGER = LogFactory.getLog("com.amazonaws.service.s3");

    /** バケット名 */
    private String bucketName;

    /**
     * コンストラクタ
     * @param bucketName バケット名
     */
    public AmazonS3ClientWrapperImpl(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int uploadDirectory(File directory) {
        return uploadDirectory(directory, AmazonS3UploadDirectoryContext.DEFAULT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int uploadDirectory(File directory, AmazonS3UploadDirectoryContext context) {
        checkNotNull(directory);
        checkArgument(directory.exists() && directory.isDirectory());

        if (AmazonApiConfiguration.isDryRun()) {
            LOGGER.info(String.format("[DryRun] upload directory of %s", directory.getName()));
            LOGGER.debug("--------------- directory contents start ---------------");
            for (File file : directory.listFiles()) {
                LOGGER.info(String.format("filename : %s", file.getName()));
            }
            LOGGER.debug("--------------- directory contents end ---------------");
        }

        int total = directory.listFiles().length;
        int poolSize = (total < AmazonS3Configuration.getUploadPoolSize()) ? (int) total : AmazonS3Configuration.getUploadPoolSize();
        int uploaded = 0;

        ExecutorService executor = null;
        try {
            executor = Executors.newFixedThreadPool(poolSize);

            List<Future<AmazonS3UploadStatus>> uploadResults = new ArrayList<Future<AmazonS3UploadStatus>>();
            for (File file : directory.listFiles()) {
                Future<AmazonS3UploadStatus> uploadResult = executor.submit(new AmazonS3UploaderTask(bucketName, file, context.getPrefix(), context.isFileDeleteOnFinish()));
                uploadResults.add(uploadResult);
            }

            executor.shutdown();

            for (Future<AmazonS3UploadStatus> uploadResult : uploadResults) {
                AmazonS3UploadStatus uploadStatus = uploadResult.get();
                if (uploadStatus.isSuccess()) {
                    uploaded++;
                } else {
                    LOGGER.warn(String.format("upload error of %s", uploadStatus.getFileKey()));
                    if (context.isStopOnError()) {
                        executor.shutdownNow();
                    }
                }
            }
        } catch (ExecutionException e) {
            if (executor != null) {
                executor.shutdownNow();
            }
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            if (executor != null) {
                executor.shutdownNow();
            }
            throw new RuntimeException(e);
        }

        return uploaded;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean uploadFile(File file) {
        return uploadFile(file, AmazonS3UploadDirectoryContext.DEFAULT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean uploadFile(File file, AmazonS3UploadDirectoryContext context) {
        checkNotNull(file);
        checkArgument(file.exists() && file.isFile());

        if (AmazonApiConfiguration.isDryRun()) {
            LOGGER.info(String.format("[DryRun] upload file of %s", file.getName()));
        }

        AmazonS3UploadStatus status = new AmazonS3UploaderTask(bucketName, file, context.getPrefix(), context.isFileDeleteOnFinish()).call();
        return status.isSuccess();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> findFiles(String prefix) {
        checkNotNull(prefix);

        if (AmazonApiConfiguration.isDryRun()) {
            LOGGER.info(String.format("[DryRun] find object for bucketName=%s, AmazonApiConfiguration=%s", bucketName, prefix));
            return new ArrayList<String>();
        }

        AmazonS3Client amazonS3Client = AmazonS3ClientHolder.currentClient();
        ObjectListing objectListing = amazonS3Client.listObjects(bucketName, prefix);
        if (objectListing == null) {
            return new ArrayList<String>();
        }

        List<String> fileKeys = new ArrayList<String>();
        for (S3ObjectSummary s3ObjectSummary : objectListing.getObjectSummaries()) {
            fileKeys.add(s3ObjectSummary.getKey());
        }

        return fileKeys;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> findDirectories(String prefix) {
        checkNotNull(prefix);

        if (AmazonApiConfiguration.isDryRun()) {
            LOGGER.info(String.format("[DryRun] find direcotries for bucketName=%s, fileOrDirectoryPrefix=%s", bucketName, prefix));
            return new ArrayList<String>();
        }

        ListObjectsRequest request = new ListObjectsRequest(bucketName, null, null, "/", null);

        AmazonS3Client amazonS3Client = AmazonS3ClientHolder.currentClient();
        ObjectListing list = amazonS3Client.listObjects(request);

        List<String> directories = new ArrayList<String>();
        do {
            directories.addAll(list.getCommonPrefixes());
            list = amazonS3Client.listNextBatchOfObjects(list);
        } while (list.getMarker() != null);

        return directories;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean downloadFile(String fileKey, File dist) {
        checkNotNull(fileKey);
        checkNotNull(dist);

        if (AmazonApiConfiguration.isDryRun()) {
            LOGGER.info(String.format("[DryRun] find object for bucketName=%s, fileKey=%s", bucketName, fileKey));
            return true;
        }

        try {
            AmazonS3Client amazonS3Client = AmazonS3ClientHolder.currentClient();
            GetObjectRequest request = new GetObjectRequest(bucketName, fileKey);
            ObjectMetadata metadata = amazonS3Client.getObject(request, dist);
            return metadata != null;
        } catch (AmazonS3Exception e) {
            if (e.getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                return false;
            }
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int removeDirectory(String directoryName) {
        checkNotNull(directoryName);

        if (AmazonApiConfiguration.isDryRun()) {
            LOGGER.info(String.format("[DryRun] remove directory for bucketName=%s, directoryName=%s", bucketName, directoryName));
            return 0;
        }

        int deleted = 0;
        AmazonS3Client amazonS3Client = AmazonS3ClientHolder.currentClient();

        ListObjectsRequest request = new ListObjectsRequest(bucketName, directoryName, null, null, null);
        ObjectListing objectListing = null;
        while ((objectListing = amazonS3Client.listObjects(request)) != null && objectListing.getObjectSummaries().size() > 0) {
            List<KeyVersion> keys = new ArrayList<KeyVersion>();

            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                keys.add(new KeyVersion(objectSummary.getKey()));
            }

            if (keys.isEmpty()) {
                continue;
            }

            DeleteObjectsRequest deleteRequest = new DeleteObjectsRequest(bucketName);
            deleteRequest.setKeys(keys);

            amazonS3Client.deleteObjects(deleteRequest);
            deleted++;
        }

        return deleted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeFile(String fileKey) {
        checkNotNull(fileKey);

        if (AmazonApiConfiguration.isDryRun()) {
            LOGGER.info(String.format("[DryRun] remove file for bucketName=%s, fileKey=%s", bucketName, fileKey));
            return true;
        }

        DeleteObjectsRequest deleteRequest = new DeleteObjectsRequest(bucketName);
        deleteRequest.setKeys(Arrays.asList(new KeyVersion(fileKey)));

        AmazonS3Client amazonS3Client = AmazonS3ClientHolder.currentClient();
        try {
            amazonS3Client.deleteObjects(deleteRequest);
            return true;
        } catch (AmazonClientException e) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AmazonS3Downloader downloader() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AmazonS3Remover remover() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AmazonS3Uploader uploader() {
        return this;
    }
}
