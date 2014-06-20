package jp.gr.java_conf.ochi0218.console.sample;

import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.AmazonS3ClientWrapper;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.config.AmazonApiConfiguration;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.config.AmazonS3Configuration;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.dto.S3ObjectList;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.impl.AmazonS3ClientHolder;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.impl.AmazonS3ClientWrapperImpl;

/**
 * ダウンロードのサンプル。
 */
public class DownloadSample {
    /**
     * メイン。
     * @param args 引数。
     */
    public static void main(String[] args) {
        setup();

        try {
            AmazonS3ClientWrapper amazonS3Client = new AmazonS3ClientWrapperImpl("test");
            S3ObjectList list = amazonS3Client.downloader().findDirectories();
            for (String direcotry : list.getKeys()) {
                System.out.println(direcotry);
            }
        } finally {
            teardown();
        }
    }

    /**
     * 設定をする。
     */
    private static void setup() {
        AmazonApiConfiguration.configure("a", "b", true);
        AmazonS3Configuration.configure(10);

        AmazonS3ClientHolder.init();
    }

    /**
     * 終了処理をする。
     */
    private static void teardown() {
        AmazonS3ClientHolder.destory();
    }
}
