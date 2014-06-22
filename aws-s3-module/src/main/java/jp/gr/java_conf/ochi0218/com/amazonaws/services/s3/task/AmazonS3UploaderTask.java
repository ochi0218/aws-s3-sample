package jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.task;

import static com.google.common.base.Preconditions.*;

import java.io.File;
import java.util.concurrent.Callable;

import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.dto.AmazonS3UploadStatus;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.impl.AmazonS3ClientHolder;

import org.apache.commons.io.FileUtils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.common.base.Strings;

/**
 * S3にアップロードするためのタスク。
 */
public class AmazonS3UploaderTask implements Callable<AmazonS3UploadStatus> {
    /** バケット名 */
    private final String bucketName;

    /** キー */
    private final String key;

    /** ファイル */
    private final File file;

    /** アップロード完了時に削除するか */
    private boolean deleteOnFinish = true;

    /**
     * コンストラクタ<br>
     * @param bucketName バケット名
     * @param file ファイル
     */
    public AmazonS3UploaderTask(String bucketName, File file) {
        this(bucketName, file, null, true);
    }

    /**
     * コンストラクタ<br>
     * @param bucketName バケット名
     * @param prefix 接頭辞
     * @param file ファイル
     */
    public AmazonS3UploaderTask(String bucketName, File file, String prefix, boolean deleteOnFinish) {
        this.bucketName = checkNotNull(bucketName);
        this.file = checkNotNull(file);
        this.key = new StringBuilder().append(Strings.nullToEmpty(prefix)).append(file.getName()).toString();
        this.deleteOnFinish = deleteOnFinish;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AmazonS3UploadStatus call() {
        try {
            PutObjectRequest request = new PutObjectRequest(bucketName, key, file);

            AmazonS3Client client = AmazonS3ClientHolder.currentClient();
            client.putObject(request);

            return new AmazonS3UploadStatus(key, true);
        } catch (AmazonClientException e) {
            return new AmazonS3UploadStatus(key, false, e);
        } finally {
            if (deleteOnFinish) {
                FileUtils.deleteQuietly(file);
            }
        }
    }
}
