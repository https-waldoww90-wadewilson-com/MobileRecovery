package com.wurq.dex.mobilerecovery.hearttouch.common.util;

import android.text.TextUtils;

//import com.wurq.dex.mobilerecovery.htlog.HTLog;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ht-template
 **/
public final class MiscUtil {
    private static final String DATE_TO_INT_FORMAT = "yyyyMMdd";

    private MiscUtil() {
    }

    /**
     * 将日期转成int
     *
     * @param d date
     * @return
     */
    public static int Date2Int(Date d) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_TO_INT_FORMAT);
        String formatStr;
        try {
            formatStr = format.format(d);
            if (!TextUtils.isEmpty(formatStr)) {
                return Integer.parseInt(formatStr);
            }
        } catch (Exception e) {
//            HTLog.e("date to int transform failed");
        }

        return 0;
    }

    /**
     * 将日期转成int
     *
     * @param intDate 20151201 格式的日期
     * @return
     */
    public static Date Int2Date(int intDate) {
        if (intDate > 20000000) {
            SimpleDateFormat format = new SimpleDateFormat(DATE_TO_INT_FORMAT);
            try {
                return format.parse(Integer.toString(intDate));
            } catch (Exception e) {
//                HTLog.e("int to date transform failed");
            }
        }

        return null;
    }
}
