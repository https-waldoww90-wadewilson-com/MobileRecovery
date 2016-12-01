package com.wurq.dex.mobilerecovery.hearttouch.common.util.string;

import android.text.TextUtils;

import com.wurq.dex.mobilerecovery.hearttouch.common.util.ResourcesUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

//import hirondelle.date4j.DateTime;

/**
 * Created by ht-template
 **/
public class StringUtil {
    public static boolean isMatch(String expression, String input) {
        CharSequence inputStr = input;
        /*创建Pattern*/
        Pattern pattern = Pattern.compile(expression);
        /*将Pattern 以参数传入Matcher作Regular expression*/
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }

    public static boolean isEmailPrefix(String email) {
        String expression = "^([a-zA-Z0-9_\\-]+)$";
        return isMatch(expression, email);
    }

    //判断email格式是否正确
    public static boolean isEmail(String email) {
        String expression = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        return isMatch(expression, email);
    }

    //判断是否全是数字
    public static boolean isNumeric(String str) {
        return isMatch("[0-9]*", str);
    }

    //判断手机格式是否正确
    /*
    130 1 2 3 4 5 6 7 8 9
    145 7
    150 1 2 3 5 6 7 8 9 
    170 5 6 7 8
    18 0 1 2 3 4 5 6 7 8 9
    * */
    public static boolean isMobileNO(String mobiles) {
        String expression = "^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0,5-8])|(14[5,7]))\\d{8}$";
        return isMatch(expression, mobiles);
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为1,英文字符长度为0.5
     *
     * @param s 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static int getLength(String s) {
        int valueLength = 0;
        String chinese = "^([\u4E00-\u9FA5\uF900-\uFA2D]+)$";

        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < s.length(); i++) {
            // 获取一个字符
            String temp = s.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为2
                valueLength += 2;
            } else {
                // 其他字符长度为1
                valueLength += 1;
            }
        }
        //进位取整
        return valueLength;
    }

    /**
     * 四舍五入获取真实字数
     */
    public static int getRealLength(String s) {
        int realLenght = (int) (getLength(s) / 2.0f + 0.5f);
        return realLenght;
//        if (length > maxLength) {length = (int) ((length - maxLength) / 2.0f + 0.5f);
//            mIntroContentLength.setText("" + (-length));
//        }

    }

    /**
     * 如果memberCount大于一万，如11000，返回1.1万；110100，返回11万
     */
    public static String getFormatCount(int memberCount) {
        String memberCountStr;
        DecimalFormat df = new DecimalFormat("#.#");
        if (memberCount < 10000)
            memberCountStr = memberCount + "";
        else
            memberCountStr = df.format(memberCount / 10000.0) + "万";
        return memberCountStr;
    }

    public static String format(int resId, Object... args) {
        return String.format(ResourcesUtil.getString(resId), args);
    }

    public static String formatDateTime(int dateFormat, long timestamp) {
        return formatDateTime(ResourcesUtil.getString(dateFormat), timestamp);
    }

//    public static String formatDateTime(int dateFormat, long timestamp, String timezone) {
//        if (timestamp == 0 || TextUtils.isEmpty(timezone)) {
//            return "";
//        }
//
//        DateTime dateTime = DateTime.forInstant(timestamp, TimeZone.getTimeZone(timezone));
//        return dateTime.format(ResourcesUtil.getString(dateFormat));
//    }

    /**
     * 格式化时间戳为字符串
     *
     * @param dataFormat 时间格式
     * @param timeStamp  时间戳
     * @return
     */
    public static String formatDateTime(String dataFormat, long timeStamp) {
        if (timeStamp == 0 || TextUtils.isEmpty(dataFormat)) {
            return "";
        }
        String result;
        SimpleDateFormat format = new SimpleDateFormat(dataFormat, Locale.getDefault());
        result = format.format(new Date(timeStamp));
        return result;
    }

    /**
     * yyyy-MM-dd   HH:mm
     *
     * @param timeStamp 时间戳
     */
    public static String formatDateTime(long timeStamp) {
        return formatDateTime("yyyy-MM-dd   HH:mm", timeStamp);
    }

    /**
     * 过滤掉特殊字符
     */
    public static String stringFilter(String str) throws PatternSyntaxException {
        if (str == null) return "";
        String regEx = "[/\\:*?<>|\"\n\t]"; //要过滤掉的字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public boolean isChinese(String str) {
        return false;
    }


}
