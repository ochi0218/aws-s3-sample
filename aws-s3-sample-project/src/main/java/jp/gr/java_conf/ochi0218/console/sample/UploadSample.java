package jp.gr.java_conf.ochi0218.console.sample;

import java.io.File;
import java.io.IOException;

import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.AmazonS3ClientWrapper;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.config.AmazonApiConfiguration;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.config.AmazonS3Configuration;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.context.AmazonS3UploadDirectoryContext;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.impl.AmazonS3ClientHolder;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.impl.AmazonS3ClientWrapperImpl;

/**
 * アップロードのサンプル。
 */
public class UploadSample {
    /** テストデータパス */
    private static final String TEST_DATA_PATH = "src¥main¥resources¥data¥upload".replace("¥", File.separator);

    /**
     * メイン。
     * @param args 引数。
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        setup();

        try {
            AmazonS3ClientWrapper amazonS3Client = new AmazonS3ClientWrapperImpl("test");
            File testDir = new File(new StringBuilder().append(TEST_DATA_PATH).append(File.separator).append("testdir").toString());
            amazonS3Client.uploader().uploadDirectory(testDir, new AmazonS3UploadDirectoryContext().fileDeleteOnFinish(false));

            File testfile = new File(new StringBuilder().append(TEST_DATA_PATH).append(File.separator).append("test.json").toString());
            amazonS3Client.uploader().uploadFile(testfile, new AmazonS3UploadDirectoryContext().fileDeleteOnFinish(false));
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
