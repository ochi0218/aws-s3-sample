package jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.context;

/**
 * S3のファインダコンテキスト。
 */
public class AmazonS3FinderContext {
    /** 最大キー */
    private Integer maxKeys = null;

    /** マーカー */
    private String marker = null;

    /** デフォルト */
    public static final AmazonS3FinderContext DEFUALT = new AmazonS3FinderContext();

    /**
     * maxKeysを取得する<br>
     * @return maxKeys
     */
    public Integer getMaxKeys() {
        return maxKeys;
    }

    /**
     * markerを取得する<br>
     * @return marker
     */
    public String getMarker() {
        return marker;
    }

    /**
     * maxKeysを設定する<br>
     * @param maxKeys maxKeys
     * @return このインスタンス自身
     */
    public AmazonS3FinderContext maxKeys(Integer maxKeys) {
        this.maxKeys = maxKeys;
        return this;
    }

    /**
     * markerを設定する<br>
     * @param marker marker
     * @return このインスタンス自身
     */
    public AmazonS3FinderContext marker(String marker) {
        this.marker = marker;
        return this;
    }
}
