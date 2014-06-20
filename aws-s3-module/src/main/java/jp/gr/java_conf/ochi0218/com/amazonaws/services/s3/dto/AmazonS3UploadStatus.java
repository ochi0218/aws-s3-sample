package jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.dto;

/**
 * アップロードステータス。
 */
public class AmazonS3UploadStatus {
    /** ファイルキー */
    private final String fileKey;

    /** 成功したかどうか */
    private final boolean success;

    /**
     * コンストラクタ<br>
     * @param fileKey
     * @param success
     */
    public AmazonS3UploadStatus(String fileKey, boolean success) {
        this.fileKey = fileKey;
        this.success = success;
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
}
