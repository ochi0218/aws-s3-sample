package jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.impl;

import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.config.AmazonApiConfiguration;

import com.amazonaws.services.s3.AmazonS3Client;

/**
 * {@link AmazonS3Client}を保持するクラス。
 */
public final class AmazonS3ClientHolder {
    /** S3クライアント */
    private static AmazonS3Client client = null;

    /**
     * コンストラクタ<br>
     */
    private AmazonS3ClientHolder() {
    }

    /**
     * 初期化する。
     */
    public static void init() {
        client = new AmazonS3Client(AmazonApiConfiguration.getAwsCredentials(), AmazonApiConfiguration.getClientConfiguration());
    }

    /**
     * 現在のクライアントを取得する。
     * @return 現在のクライアント
     */
    public static AmazonS3Client currentClient() {
        return client;
    }

    /**
     * 破棄する。
     */
    public static void destory() {
        currentClient().shutdown();
        client = null;
    }
}
