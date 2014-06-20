package jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.context;

import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * AmazonS3のアップロードする時のコンテキスト。
 */
public class AmazonS3UploadDirectoryContext {
    /** 接頭辞 */
    private String prefix = null;

    /** エラー時に停止するか */
    private boolean stopOnError = true;

    /** 終了時にファイルを削除するか */
    private boolean fileDeleteOnFinish = true;

    /** デフォルトのリクエスト */
    private PutObjectRequest defaultRequest = null;

    /** デフォルト */
    public static final AmazonS3UploadDirectoryContext DEFAULT = new AmazonS3UploadDirectoryContext();

    /**
     * prefixを取得する<br>
     * @return prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * stopOnErrorを取得する<br>
     * @return stopOnError
     */
    public boolean isStopOnError() {
        return stopOnError;
    }

    /**
     * fileDeleteOnFinishを取得する<br>
     * @return fileDeleteOnFinish
     */
    public boolean isFileDeleteOnFinish() {
        return fileDeleteOnFinish;
    }

    /**
     * defaultRequestを取得する<br>
     * @return defaultRequest
     */
    public PutObjectRequest getDefaultRequest() {
        return defaultRequest;
    }

    /**
     * prefixを設定する<br>
     * @param prefix prefix
     * @return このインスタンス自身
     */
    public AmazonS3UploadDirectoryContext prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * stopOnErrorを設定する<br>
     * @param stopOnError stopOnError
     * @return このインスタンス自身
     */
    public AmazonS3UploadDirectoryContext stopOnError(boolean stopOnError) {
        this.stopOnError = stopOnError;
        return this;
    }

    /**
     * fileDeleteOnFinishを設定する<br>
     * @param fileDeleteOnFinish fileDeleteOnFinish
     * @return このインスタンス自身
     */
    public AmazonS3UploadDirectoryContext fileDeleteOnFinish(boolean fileDeleteOnFinish) {
        this.fileDeleteOnFinish = fileDeleteOnFinish;
        return this;
    }

    /**
     * defaultRequestを設定する<br>
     * @param defaultRequest defaultRequest
     * @return このインスタンス自身
     */
    public AmazonS3UploadDirectoryContext defaultRequest(PutObjectRequest defaultRequest) {
        this.defaultRequest = defaultRequest;
        return this;
    }
}
