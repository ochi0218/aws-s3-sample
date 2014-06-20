package jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.retry.PredefinedRetryPolicies;

/**
 * S3の設定情報。
 */
public class AmazonApiConfiguration {
    /** AWS情報 */
    private static AWSCredentials awsCredentials;

    /** 空実行するかどうか */
    private static Boolean dryRun;

    /**
     * コンストラクタ<br>
     * @param newAwsAccessKey AWSアクセスキー
     * @param newAwsSecretKey AWSシークレットキー
     * @param newDryRun 空実行するかどうか
     */
    public static void configure(String newAwsAccessKey, String newAwsSecretKey, boolean newDryRun) {
        awsCredentials = new BasicAWSCredentials(newAwsAccessKey, newAwsSecretKey);
        dryRun = newDryRun;
    }

    /**
     * awsCredentialsを取得する<br>
     * @return awsCredentials
     */
    public static AWSCredentials getAwsCredentials() {
        return awsCredentials;
    }

    /**
     * ClientConfigurationを取得する<br>
     * @return ClientConfiguration
     */
    public static ClientConfiguration getClientConfiguration() {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setRetryPolicy(PredefinedRetryPolicies.DEFAULT);

        return clientConfiguration;
    }

    /**
     * dryRunを取得する<br>
     * @return dryRun
     */
    public static Boolean isDryRun() {
        return dryRun;
    }
}
