package jp.gr.java_conf.ochi0218.com.amazonaws.services.s3;

import java.io.File;
import java.util.List;

/**
 * AmazonS3のダウンローダ。
 */
public interface AmazonS3Downloader {
    /**
     * ファイルを検索する。
     * @param prefix 接頭辞
     * @return ファイルキー名リスト
     */
    List<String> findFiles(String prefix);

    /**
     * ディレクトリを検索する。
     * @param prefix 接頭辞
     * @return ディレクトリ名リスト
     */
    List<String> findDirectories(String prefix);

    /**
     * ファイルをダウンロードする。
     * @param fileKey ファイルキー
     * @param dist 出力先ファイル
     * @return ダウンロードできたかどうか
     */
    boolean downloadFile(String fileKey, File dist);
}
