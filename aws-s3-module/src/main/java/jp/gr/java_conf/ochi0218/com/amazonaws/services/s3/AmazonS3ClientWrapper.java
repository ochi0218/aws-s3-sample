package jp.gr.java_conf.ochi0218.com.amazonaws.services.s3;

/**
 * S3クライアントのラッパー。
 */
public interface AmazonS3ClientWrapper extends AmazonS3Uploader, AmazonS3Downloader, AmazonS3Remover {
    /**
     * アップローダを取得する。
     * @return アップローダ
     */
    AmazonS3Uploader uploader();

    /**
     * ダウンローダを取得する。
     * @return ダウンローダ
     */
    AmazonS3Downloader downloader();

    /**
     * リムーバを取得する。
     * @return リムーバ
     */
    AmazonS3Remover remover();
}
