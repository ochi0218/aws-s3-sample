package jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.dto;

/**
 * アップロードステータス。
 */
public class AmazonS3UploadStatus {
    /** ファイルキー */
    private final String fileKey;

    /** 成功したかどうか */
    private final boolean success;

    /** エラーが発生した場合のエラー理由 */
    private Throwable errorCause;

    /**
     * コンストラクタ<br>
     * @param fileKey
     * @param success
     */
    public AmazonS3UploadStatus(String fileKey, boolean success) {
        this(fileKey, success, null);
    }

    /**
     * コンストラクタ<br>
     * @param fileKey
     * @param success
     * @param errorCause
     */
    public AmazonS3UploadStatus(String fileKey, boolean success, Throwable errorCause) {
        this.fileKey = fileKey;
        this.success = success;
        this.errorCause = errorCause;
    }

    /**
     * fileNameを取得する<br>
     * @return fileName
     */
    public String getFileKey() {
        return fileKey;
    }

    /**
     * successを取得する<br>
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * errorCauseを取得する<br>
     * @return errorCause
     */
    public Throwable getErrorCause() {
        return errorCause;
    }
}
