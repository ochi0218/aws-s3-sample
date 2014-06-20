package jp.gr.java_conf.ochi0218.com.amazonaws.services.s3;

import java.io.File;

import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.context.AmazonS3FinderContext;
import jp.gr.java_conf.ochi0218.com.amazonaws.services.s3.dto.S3ObjectList;

/**
 * AmazonS3のダウンローダ。
 */
public interface AmazonS3Downloader {
    /**
     * ファイルを検索する。
     * @param prefix 接頭辞
     * @return ファイルキー名リスト
     */
    S3ObjectList findFiles(String prefix);

    /**
     * ファイルを検索する。
     * @param prefix 接頭辞
     * @param context コンテキスト
     * @return ファイルキー名リスト
     */
    S3ObjectList findFiles(String prefix, AmazonS3FinderContext context);

    /**
     * ディレクトリを検索する。
     * @return ディレクトリ名リスト
     */
    S3ObjectList findDirectories();

    /**
     * ディレクトリを検索する。
     * @param prefix 接頭辞
     * @return ディレクトリ名リスト
     */
    S3ObjectList findDirectories(String prefix);

    /**
     * ディレクトリを検索する。
     * @param prefix 接頭辞
     * @param context コンテキスト
     * @return ディレクトリ名リスト
     */
    S3ObjectList findDirectories(String prefix, AmazonS3FinderContext context);

    /**
     * ファイルをダウンロードする。
     * @param fileKey ファイルキー
     * @param dist 出力先ファイル
     * @return ダウンロードできたかどうか
     */
    boolean downloadFile(String fileKey, File dist);
}
