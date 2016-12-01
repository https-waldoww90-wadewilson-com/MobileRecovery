package com.wurq.dex.mobilerecovery.hearttouch.common.util.storage;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.wurq.dex.mobilerecovery.hearttouch.common.util.ToastUtil;
import com.wurq.dex.mobilerecovery.R;

import java.io.File;

/**
 * Created by ht-template
 **/
public class StorageUtil {

    public final static long K = 1024;

    public final static long M = 1024 * 1024;
    // 保存文件时所需的最小空间的默认值
    public static final long THRESHOLD_MIN_SPCAE = 20 * M;
    // 外置存储卡默认预警临界值
    private static final long THRESHOLD_WARNING_SPACE = 100 * M;


    public static String getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath + File.separator + uniqueName;
    }

    /**
     * 获取文件保存路径，空间不足时有toast提示
     *
     * @param fileName
     * @param fileType
     * @return 可用的保存路径或者null
     */
    public static String getWritePath(String fileName, StorageType fileType) {
        return getWritePath(fileName, fileType, true);
    }

    /**
     * 返回下载文件的保存路径
     */
    public static String getDownloadPath(final String filename, StorageType fileType) {
        return getWritePath(filename, fileType, true);
    }

    /**
     * 获取文件保存路径
     *
     * @param fileName 文件全名
     * @param tip      空间不足时是否给出默认的toast提示
     * @return 可用的保存路径或者null
     */
    private static String getWritePath(String fileName, StorageType fileType, boolean tip) {
        if (hasEnoughSpaceForWrite(fileType, tip)) {
            String path = ExternalStorage.getInstance().getWritePath(fileName, fileType);
            if (TextUtils.isEmpty(path)) {
                return null;
            }
            File dir = new File(path).getParentFile();
            if (dir != null && !dir.exists()) {
                dir.mkdirs();
            }
            return path;
        }
        return null;
    }

    /**
     * 判断外部存储是否存在，以及是否有足够空间保存指定类型的文件
     *
     * @param fileType
     * @param tip      是否需要toast提示
     * @return false: 无存储卡或无空间可写, true: 表示ok
     */
    public static boolean hasEnoughSpaceForWrite(StorageType fileType, boolean tip) {
        if (!ExternalStorage.getInstance().isExternalStorageExist()) {
            if (tip) {
                ToastUtil.makeShortToast(R.string.sdcard_not_exist_error);
            }
            return false;
        }

        long residual = ExternalStorage.getInstance().getAvailableExternalSize();
        if (residual < fileType.getStorageMinSize()) {
            if (tip) {
                ToastUtil.makeShortToast(R.string.sdcard_not_enough_error);
            }
            return false;
        } else if (residual < THRESHOLD_WARNING_SPACE) {
            if (tip) {
                ToastUtil.makeShortToast(R.string.sdcard_not_enough_warning);
            }
        }

        return true;
    }

    public static String getDirectoryByDirType(StorageType fileType) {
        return ExternalStorage.getInstance().getDirectoryByDirType(fileType);
    }
}
