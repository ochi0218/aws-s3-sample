package jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.config;


/**
 * S3の設定情報。
 */
public class AmazonS3Configuration {
    /** アップロード時のスレッドプールサイズ */
    private static int uploadPoolSize = 5;

    /**
     * コンストラクタ<br>
     */
    private AmazonS3Configuration() {
    }

    /**
     * コンストラクタ<br>
     * @param newUploadPoolSize アップロード時のスレッドプールサイズ
     */
    public static void configure(int newUploadPoolSize) {
        uploadPoolSize = newUploadPoolSize;
    }

    /**
     * uploadPoolSizeを取得する<br>
     * @return uploadPoolSize
     */
    public static int getUploadPoolSize() {
        return uploadPoolSize;
    }
}
