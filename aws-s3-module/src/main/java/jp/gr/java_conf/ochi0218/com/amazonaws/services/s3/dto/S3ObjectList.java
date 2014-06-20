package jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.dto;

import java.util.List;

/**
 * S3オブジェクトリスト。
 */
public class S3ObjectList {
    /** キーリスト */
    private final List<String> keys;

    /** マーカー */
    private final String marker;

    /**
     * コンストラクタ<br>
     * @param keys
     */
    public S3ObjectList(List<String> keys) {
        this(keys, null);
    }

    /**
     * コンストラクタ<br>
     * @param keys
     * @param marker
     */
    public S3ObjectList(List<String> keys, String marker) {
        this.keys = keys;
        this.marker = marker;
    }

    /**
     * keysを取得する<br>
     * @return keys
     */
    public List<String> getKeys() {
        return keys;
    }

    /**
     * markerを取得する<br>
     * @return marker
     */
    public String getMarker() {
        return marker;
    }
}
