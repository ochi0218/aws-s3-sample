package jp.gr.java_conf.ochi0218.com.amazonaws.services.s3;

import java.io.File;

import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.context.AmazonS3UploadDirectoryContext;

/**
 * S3へのアップローダ。
 */
public interface AmazonS3Uploader {
    /**
     * ディレクトリをアップロードする。
     * @param directory ディレクトリ
     * @return アップロード数
     */
    int uploadDirectory(File directory);

    /**
     * ディレクトリをアップロードする。
     * @param directory ディレクトリ
     * @param context コンテキスト
     * @return アップロード数
     */
    int uploadDirectory(File directory, AmazonS3UploadDirectoryContext context);

    /**
     * ファイルをアップロードする。
     * @param file ファイル
     * @return アップロードできた場合はtrue。
     */
    boolean uploadFile(File file);

    /**
     * ファイルをアップロードする。
     * @param file ファイル
     * @param context コンテキスト
     * @return アップロードできた場合はtrue。
     */
    boolean uploadFile(File file, AmazonS3UploadDirectoryContext context);
}
