package jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.context;

import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CannedAccessControlList;

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

    /** アクセスコントロール */
    private CannedAccessControlList cannedAcl = null;

    /** アクセスコントロールリスト */
    private AccessControlList accessControlList = null;

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
     * cannedAclを取得する<br>
     * @return cannedAcl
     */
    public CannedAccessControlList getCannedAcl() {
        return cannedAcl;
    }

    /**
     * accessControlListを取得する<br>
     * @return accessControlList
     */
    public AccessControlList getAccessControlList() {
        return accessControlList;
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
     * cannedAclを設定する<br>
     * @param cannedAcl cannedAcl
     * @return このインスタンス自身
     */
    public AmazonS3UploadDirectoryContext cannedAcl(CannedAccessControlList cannedAcl) {
        this.cannedAcl = cannedAcl;
        return this;
    }

    /**
     * accessControlListを設定する<br>
     * @param accessControlList accessControlList
     * @return このインスタンス自身
     */
    public AmazonS3UploadDirectoryContext accessControlList(AccessControlList accessControlList) {
        this.accessControlList = accessControlList;
        return this;
    }
}
