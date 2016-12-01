package com.wurq.dex.mobilerecovery.hearttouch.common.util.storage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ht-template
 **/
public final class FileManager {
    private static final String STATISTICS_FILE_NAME = ".statistics";

    private FileManager() {
    }

    public static String getStatisticsFilePath() {
        return StorageUtil.getDirectoryByDirType(StorageType.TYPE_STATISTICS) + STATISTICS_FILE_NAME;
    }

    /**
     * @param path   : the file path.
     * @param length : the length need to read.
     * @return the result text.
     * @throws IOException : the exception.
     */
    public static String readFromFile(String path, long length, String charset)
            throws IOException {
        File file = new File(path);
        if (file.isDirectory() || !file.exists()) {
            return null;
        }
        if (length == -1) {
            length = file.length();
        }
        length = Math.min(length, file.length());
        FileInputStream in;

        in = new FileInputStream(file);
        byte[] buffer = new byte[(int) length];
        int currentPos = 0;
        int read = 0;
        while ((read = in.read(buffer, currentPos,
                (int) (length - currentPos))) > 0) {
            currentPos += read;
        }
        if (null == charset) {
            charset = "UTF-8";
        }
        return new String(buffer, charset);

    }

    /**
     * @param path    : the path where write to.
     * @param content : the content will be write.
     * @throws IOException : the exception.
     */
    public static void writeToFile(String path, String content)
            throws IOException {
        writeToFile(path, new ByteArrayInputStream(content.getBytes()));
    }

    public static void writeToFile(String path, InputStream in)
            throws IOException {
        File file = new File(path);
        FileOutputStream out;
        out = new FileOutputStream(file);

        int inputLength;
        final byte[] buffer = new byte[16 * 1024];
        while (-1 != (inputLength = in.read(buffer))) {
            out.write(buffer, 0, inputLength);
            out.flush();
        }

        out.flush();
        out.close();
    }
}
