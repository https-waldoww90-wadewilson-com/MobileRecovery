package com.wurq.dex.mobilerecovery.hearttouch.common.util.media.image;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.opengl.GLES10;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.wurq.dex.mobilerecovery.hearttouch.constant.C;
import com.wurq.dex.mobilerecovery.hearttouch.common.util.ScreenUtil;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.microedition.khronos.opengles.GL10;


/**
 * Created by ht-template
 **/
public class ImageUtil {
    public final static float MAX_IMAGE_RATIO = 5f;
    public static final int IMAGE_WIDTH_SINGLE = ScreenUtil.screenWidth;
    public static final int IMAGE_WIDTH_DOUBLE = ScreenUtil.screenWidth / 2;
    public static final int IMAGE_WIDTH_TRIPLE = ScreenUtil.screenWidth / 3;

    //    public static void setImageWithoutRotate(ImageView imageview, Uri uri) {
//        imageview.setImageURI(uri);
//        String path = uri.getPath();
//        Bitmap srcBitmap = BitmapFactory.decodeFile(path);
//        if (srcBitmap != null) {
//            Bitmap desBitmap = rotateBitmapInNeeded(path, srcBitmap);
//            imageview.setImageBitmap(desBitmap);
//        } else {
//            imageview.setImageURI(Uri.parse(path));
//        }
//    }
    public static final float SCALE_16_9 = (float) 16 / 9;
    public static final float SCALE_9_16 = (float) 9 / 16;
    public static final int HEIGHT_16_9 = (int) (ScreenUtil.screenWidth / SCALE_16_9);
    public static final int HEIGHT_9_16 = (int) (ScreenUtil.screenWidth / SCALE_9_16);

    public static boolean saveBitmap(Bitmap bitmap, String path, boolean recyle) {
        if (bitmap == null || TextUtils.isEmpty(path)) {
            return false;
        }

        BufferedOutputStream bos = null;
        try {
            FileOutputStream fos = new FileOutputStream(path);
            bos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);

            return true;
        } catch (FileNotFoundException e) {
            return false;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                }
            }
            if (recyle) {
                bitmap.recycle();
            }
        }
    }

    public static Bitmap rotateBitmapInNeeded(String path, Bitmap srcBitmap) {
        if (TextUtils.isEmpty(path) || srcBitmap == null) {
            return null;
        }

        ExifInterface localExifInterface;
        try {
            localExifInterface = new ExifInterface(path);
            int rotateInt = localExifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            float rotate = getImageRotate(rotateInt);
            if (rotate != 0) {
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                Bitmap dstBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, false);
                if (dstBitmap == null) {
                    return srcBitmap;
                } else {
                    if (srcBitmap != null && !srcBitmap.isRecycled()) {
                        srcBitmap.recycle();
                    }
                    return dstBitmap;
                }
            } else {
                return srcBitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return srcBitmap;
        }
    }

    /**
     * 获得旋转角度
     *
     * @param rotate
     * @return
     */
    public static float getImageRotate(int rotate) {
        float f;
        if (rotate == 6) {
            f = 90.0F;
        } else if (rotate == 3) {
            f = 180.0F;
        } else if (rotate == 8) {
            f = 270.0F;
        } else {
            f = 0.0F;
        }

        return f;
    }

    // 单位是byte
    public static long getCacheSize(boolean hasMemoryCache, boolean hasDiskCache) {
//        long frescoSize = FrescoUtil.getCacheSize(hasMemoryCache, hasDiskCache);
//        return frescoSize;
        return 0;
    }

    public static String getFormatedCacheSize(boolean hasMemoryCache, boolean hasDiskCache,
                                              @Nullable DecimalFormat decimalFormat) {
        long byteSize = getCacheSize(hasMemoryCache, hasDiskCache);
        if (byteSize < 0) byteSize = 0;
        if (decimalFormat == null) decimalFormat = new DecimalFormat("0.00");

        if (byteSize < C.BYTES_IN_M) {
            float k = 1.0f * byteSize / C.BYTES_IN_K;
            return decimalFormat.format(k) + "K";
        } else {
            float m = 1.0f * byteSize / C.BYTES_IN_M;
            return decimalFormat.format(m) + "M";
        }
    }

    public static void clearCache(boolean clearMemoryCache, boolean clearDiskCache) {
//        FrescoUtil.clearCache(clearMemoryCache, clearDiskCache);
    }

    public static void clearCacheBehind(final boolean clearMemoryCache,
                                        final boolean clearDiskCache,
                                        final IClearCacheComplete completeCB) {
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object... params) {
                clearCache(clearMemoryCache, clearDiskCache);
                return null;
            }

            @Override
            protected void onPostExecute(Object result) {
                if (completeCB != null) completeCB.onClearComplete();
            }
        };
        asyncTask.execute();
    }

    public static boolean hasCache(final String url) {
//        return FrescoUtil.hasCache(url);
        return false;
    }

    public static boolean hasCache(final Uri uri) {
//        return FrescoUtil.hasCache(uri);
        return false;
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    public static int getMaxTextureSize() {
        int[] maxTextureSize = new int[1];
        GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxTextureSize, 0);
        return maxTextureSize[0] == 0 ? 4096 : maxTextureSize[0];
    }

    public static int getImageHeight() {
        return getMaxTextureSize() == 0 ? getMaxTextureSize() : getMaxTextureSize();
    }
}
