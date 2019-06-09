package com.hellom.mediastore.util;

import java.text.DecimalFormat;

public class FileUtil {

    private static DecimalFormat decimalFormat;

    static {
        decimalFormat = new DecimalFormat("#.00");
    }

    public static String getFileSize(long byteCount) {
        long stepNum = 1024;
        String fileSize = null;
        if (byteCount <= stepNum) {
            fileSize = decimalFormat.format(byteCount) + "B";
        } else if (byteCount <= stepNum * stepNum) {
            fileSize = decimalFormat.format(byteCount / (stepNum * 1f)) + "KB";
        } else if (byteCount <= stepNum * stepNum * stepNum) {
            fileSize = decimalFormat.format(byteCount / (stepNum * stepNum * 1f)) + "MB";
        } else if (byteCount <= stepNum * stepNum * stepNum * stepNum) {
            fileSize = decimalFormat.format(byteCount / (stepNum * stepNum * stepNum * 1f)) + "GB";
        }
        return fileSize;
    }

    public static String getFileType(String path) {
        return path.substring(path.lastIndexOf(".") + 1);
    }
}
