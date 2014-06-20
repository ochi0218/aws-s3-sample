package jp.gr.java_conf.ochi0218.com.amazonaws.services.s3;

/**
 * AmazonS3の削除。
 */
public interface AmazonS3Remover {
    /**
     * ディレクトリを削除する。
     * @param directoryName ディレクトリ名
     * @return 削除件数
     */
    int removeDirectory(String directoryName);

    /**
     * ファイルキーを削除する。
     * @param fileKey ファイルキー
     * @return 削除できた場合true。
     */
    boolean removeFile(String fileKey);
}
