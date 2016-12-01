package com.wurq.dex.mobilerecovery.recoveryapp.application;

import com.wurq.dex.mobilerecovery.hearttouch.common.util.CryptoUtil;
import com.wurq.dex.mobilerecovery.hearttouch.db.SharePreferenceHelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by ht-template
 **/
public class AccountInfo {
    private static final int ROLE_TYPE_FEMALE_PREPARE = 1;//备孕女性角色类型
    private static final int ROLE_TYPE_FEMALE_PREGNANT = 2;//怀孕女性角色类型
    private static final int ROLE_TYPE_CHILDREN = 3;//儿童角色类型

    private static final String[] USER_COOKIE_KEYS = {"NTES_SESS", "S_INFO", "P_INFO",
            "NTES_OSESS", "S_OINFO", "P_OINFO"};
    private static final String[] ANONYMOUS_COOKIE_KEYS = {"yx_aui"};

    private static String USER_NICKNAME_KEY = "user_nickname";//昵称
    private static String HEAD_PORTRAIT_KEY = "head_portrait";//头像
    private static String FEMALE_ROLE_ID_KEY = "female_role_id";//女性角色ID
    private static String CHILDREN_ROLE_ID_KEY = "children_role_id";//儿童角色ID
    private static String INITIALIZED_DATA_LH_KEY = "initialized_data_lh";//是否已初始化生理资料LH
    private static String INITIALIZED_DATA_HCG_KEY = "initialized_data_hcg";//是否已初始化生理资料HCG
    private static String INITIALIZED_DATA_CRP_KEY = "initialized_data_crp";//是否已初始化生理资料CRP
    private static String SAVED_INIT_DATA_KEY = "saved_init_data";//是否已保存重叠的生理资料
    private static String BIRTH_YEAR_KEY = "birth_year";//生理资料-出生年份
    private static String IS_FIRST_KEY = "is_first";//生理资料-是否初次备孕/怀孕
    private static String LAST_MENSES_DATE_KEY = "last_menses_date";//生理资料-最近一次月经日期
    private static String LAST_LOVE_DATE_KEY = "last_love_date";//生理资料-最近一次同房日期
    private static String NEED_REFRESH_CALENDAR_KEY = "need_refresh_calendar";//是否需要刷新生理日历
    private static String USER_ACCOUNT_KEY = "user_account_key";
    private static String PRIMARY_USER_NAME_KEY = "primary_username";
    private static String PASSWORD_MD5_KEY = "password_md5";
    private static String LOGIN_STATE_KEY = "login";
    private static String AUTH_TOKEN_KEY = "auth_token";
    private static String WZP_PUBKEY_KEY = "wzp_pubkey";
    private static String USER_COOKIE_KEY = "user_cookie";
    private static String ANONYMOUS_COOKIE_KEY = "anonymous_cookie";
    private static String PUSH_SWITCH_USER_KEY = "push_switch_user";
    private static String PUSH_SWITCH_KEY = "push_switch";
    private static String ANONYMOUS_USERNAME = "anonymous";
    private static String DEVICE_RECORDS = "device_records";//连接过的网易盒子的名称
    private static String CURRENT_DEVICE = "current_device";
    private static String DETECT_RAW_FILES = "detect_raw_files";
    private static String DETECTED_ID = "detected_id";
    private static String DETECTED_TYPE = "detected_type";
    private static String SERVER_IMG_NAME = "server_img_name";
    private static String TEST_STEP="test_step";//保存的检测步骤
    private static String DETECT_TIME = "detect_time";
    private static String SUBMIT_POCT_DATA = "submit_poct_data";

    private static String QR_CODE = "qr_code";

    private static Map<String, String> userCookieMap = new HashMap<>();
    private static Map<String, String> anonymousCookieMap = new HashMap<>();
    private static List<Integer> userNotificationIds = new LinkedList<>();
    private static String password;

    public static Map<String, String> getCookie() {
        Map<String, String> allCookies = new HashMap<>();
        getCookie(userCookieMap, USER_COOKIE_KEY);
        getCookie(anonymousCookieMap, ANONYMOUS_COOKIE_KEY);
        allCookies.putAll(userCookieMap);
        allCookies.putAll(anonymousCookieMap);
        return allCookies;
    }

    public static void setCookie(Map<String, String> cookie) {
        parseCookie(cookie, USER_COOKIE_KEYS, userCookieMap, USER_COOKIE_KEY);
        parseCookie(cookie, ANONYMOUS_COOKIE_KEYS, anonymousCookieMap, ANONYMOUS_COOKIE_KEY);
    }

    private static void getCookie(Map<String, String> cookie, String spKey) {
        if (cookie.isEmpty()) {
            String cookieEncryptJson = SharePreferenceHelper.getGlobalString(spKey, null);
            if (cookieEncryptJson != null) {
                String cookieJson;
//                try {
//                    cookieJson = CryptoUtil.decryptText(cookieEncryptJson);
//                    Map map = JSON.parseObject(cookieJson);
//                    for (Object o : map.entrySet()) {
//                        Map.Entry<String, String> entry = (Map.Entry<String, String>) o;
//                        cookie.put(entry.getKey(), entry.getValue());
//                    }
//                } catch (CryptoUtil.CryptoException e) {
//                    HTLog.e(e, "");
//                }
            }
        }
    }

    private static void addToCookies(String keyStr, Map<String, String> cookies) {
        String valueStr = SharePreferenceHelper.getGlobalString(keyStr, null);
        if (valueStr != null) {
            cookies.put(keyStr, valueStr);
        }
    }

    public static void clearUserCookie() {
        SharePreferenceHelper.putGlobalString(USER_COOKIE_KEY, null);
        userCookieMap.clear();
    }

    private static void parseCookie(Map<String, String> rawCookies, String[] keys, Map<String, String> cookieMap, String spKey) {
        boolean isModified = false;
        for (String str : keys) {
            if (rawCookies.containsKey(str)) {
                //为了获取主账户名不得不在这里写死了
                if (str.equals("P_INFO") || str.equals("P_OINFO")) {
                    SharePreferenceHelper.putGlobalString(PRIMARY_USER_NAME_KEY, rawCookies.get(str));
                }
                cookieMap.put(str, rawCookies.get(str));
                isModified = true;
            }
        }
//        if (isModified) {
//            String cookieJson = JSON.toJSONString(cookieMap);
//            String encryptText;
//            try {
//                encryptText = CryptoUtil.encryptText(cookieJson);
//                SharePreferenceHelper.putGlobalString(spKey, encryptText);
//            } catch (CryptoUtil.CryptoException e) {
//                HTLog.e(e, "");
//            }
//        }
    }


    public static void setTestStep(int step){
        SharePreferenceHelper.getGlobalInt(TEST_STEP,step);
    }

    public static int getTestStep(){
        return SharePreferenceHelper.getGlobalInt(TEST_STEP,-1);
    }

    public static String getUserNickname() {
        return SharePreferenceHelper.getGlobalString(USER_NICKNAME_KEY, "").trim();
    }

    public static void setUserNickname(String username) {
        SharePreferenceHelper.putGlobalString(USER_NICKNAME_KEY, username);
    }

    public static String getFemaleRoleId() {
        return SharePreferenceHelper.getGlobalString(FEMALE_ROLE_ID_KEY, "-1");
    }

    public static void setFemaleRoleId(String roleId) {
        SharePreferenceHelper.putGlobalString(FEMALE_ROLE_ID_KEY, roleId);
    }

    public static String getChildrenRoleId() {
        return SharePreferenceHelper.getGlobalString(CHILDREN_ROLE_ID_KEY, "-1");
    }

    public static void setChildrenRoleId(String roleId) {
        SharePreferenceHelper.putGlobalString(CHILDREN_ROLE_ID_KEY, roleId);
    }

    public static Boolean getInitializedDataLh() {
        return SharePreferenceHelper.getGlobalBoolean(INITIALIZED_DATA_LH_KEY, false);
    }

    public static void setInitializedDataLh(Boolean isInit) {
        SharePreferenceHelper.putGlobalBoolean(INITIALIZED_DATA_LH_KEY, isInit);
    }

    public static Boolean getInitializedDataHcg() {
        return SharePreferenceHelper.getGlobalBoolean(INITIALIZED_DATA_HCG_KEY, false);
    }

    public static void setInitializedDataHcg(Boolean isInit) {
        SharePreferenceHelper.putGlobalBoolean(INITIALIZED_DATA_HCG_KEY, isInit);
    }

    public static Boolean getInitializedDataCrp() {
        return SharePreferenceHelper.getGlobalBoolean(INITIALIZED_DATA_CRP_KEY, false);
    }

    public static void setInitializedDataCrp(Boolean isInit) {
        SharePreferenceHelper.putGlobalBoolean(INITIALIZED_DATA_CRP_KEY, isInit);
    }

    public static Boolean getSavedInitData() {
        return SharePreferenceHelper.getGlobalBoolean(SAVED_INIT_DATA_KEY, false);
    }

    public static void setSavedInitData(Boolean savedInitData) {
        SharePreferenceHelper.putGlobalBoolean(SAVED_INIT_DATA_KEY, savedInitData);
    }

    public static int getBirthYear() {
        return SharePreferenceHelper.getGlobalInt(BIRTH_YEAR_KEY, 0);
    }

    public static void setBirthYear(int birthYear) {
        SharePreferenceHelper.putGlobalInt(BIRTH_YEAR_KEY, birthYear);
    }

    public static Boolean getIsFirst() {
        return SharePreferenceHelper.getGlobalBoolean(IS_FIRST_KEY, false);
    }

    public static void setIsFirst(Boolean isFirst) {
        SharePreferenceHelper.putGlobalBoolean(IS_FIRST_KEY, isFirst);
    }

    public static String getLastMensesDate() {
        return SharePreferenceHelper.getGlobalString(LAST_MENSES_DATE_KEY, "");
    }

    public static void setLastMensesDate(String lastMensesDate) {
        SharePreferenceHelper.putGlobalString(LAST_MENSES_DATE_KEY, lastMensesDate);
    }

    public static String getLastLoveDate() {
        return SharePreferenceHelper.getGlobalString(LAST_LOVE_DATE_KEY, "");
    }

    public static void setLastLoveDate(String lastLoveDate) {
        SharePreferenceHelper.putGlobalString(LAST_LOVE_DATE_KEY, lastLoveDate);
    }

    public static Boolean getNeedRefreshCalendar() {
        return SharePreferenceHelper.getGlobalBoolean(NEED_REFRESH_CALENDAR_KEY, false);
    }

    public static void setNeedRefreshCalendar(Boolean needRefresh) {
        SharePreferenceHelper.putGlobalBoolean(NEED_REFRESH_CALENDAR_KEY, needRefresh);
    }

    public static String getUserAccount() {
        return SharePreferenceHelper.getGlobalString(USER_ACCOUNT_KEY, "");
    }

    public static void setUserAccount(String account) {
        SharePreferenceHelper.putGlobalString(USER_ACCOUNT_KEY, account);
    }

    public static String getPushSwitchUsername() {
        return SharePreferenceHelper.getGlobalString(PUSH_SWITCH_USER_KEY, ANONYMOUS_USERNAME);
    }

    public static void setPushSwitchUsername(String username) {
        SharePreferenceHelper.putGlobalString(PUSH_SWITCH_USER_KEY, username);
    }

    //为空的话就让用户自己输入吧！
    public static String getPassword() {
        if (password == null) {
            String passwordEncryptText = SharePreferenceHelper.getGlobalString(PASSWORD_MD5_KEY, null);
            if (passwordEncryptText != null) {
                try {
                    password = CryptoUtil.decryptText(passwordEncryptText);
                } catch (CryptoUtil.CryptoException e) {
                    e.printStackTrace();
                }
            }
        }
        return password;
    }

    public static void setPassword(String newPassword) {
        password = newPassword;
        //出错了没保存下来就算了
        try {
            String passwordEncryptText = (password == null) ? null : CryptoUtil.encryptText(password);
            SharePreferenceHelper.putGlobalString(PASSWORD_MD5_KEY, passwordEncryptText);
        } catch (CryptoUtil.CryptoException e) {
            e.printStackTrace();
        }
    }

    public static void setLoginState(boolean isLogin) {
        SharePreferenceHelper.putGlobalBoolean(LOGIN_STATE_KEY, isLogin);
    }

    public static boolean isLogin() {
        return SharePreferenceHelper.getGlobalBoolean(LOGIN_STATE_KEY, false);
    }

    public static String getAuthToken() {
        return SharePreferenceHelper.getGlobalString(AUTH_TOKEN_KEY, null);
    }

    public static void setAuthToken(String token) {
        SharePreferenceHelper.putGlobalString(AUTH_TOKEN_KEY, token);
    }

    public static String getWzpPubkey() {
        return SharePreferenceHelper.getGlobalString(WZP_PUBKEY_KEY, null);
    }

    public static void setWzpPubkey(String wzpPubkey) {
        SharePreferenceHelper.putGlobalString(WZP_PUBKEY_KEY, wzpPubkey);
    }



    public static boolean getPushSwitchState() {
        String username = getPushSwitchUsername();
        return SharePreferenceHelper.getBoolean(username, PUSH_SWITCH_KEY, true);
    }

    public static void setPushSwitchState(boolean pushSwitchState) {
        String username = getUserNickname();
        if (username == null) {
            username = ANONYMOUS_USERNAME;
        }
        SharePreferenceHelper.putBoolean(username, PUSH_SWITCH_KEY, pushSwitchState);
    }

    public static boolean isSaveTraffic() {
        return false;
    }

    public static String getPrimaryUsername() {
        return SharePreferenceHelper.getGlobalString(PRIMARY_USER_NAME_KEY, null);
    }

    public static List<Integer> getUserNotificationIds() {
        return userNotificationIds;
    }

    public static void addUserNotificationId(int id) {
        userNotificationIds.add(id);
    }

    public static void clearUserNotificationIds() {
        userNotificationIds.clear();
    }




    public static String getHeadPortrait() {
        return SharePreferenceHelper.getGlobalString(HEAD_PORTRAIT_KEY, "");
    }

    public static void setHeadPortrait(String headPortrait) {
        SharePreferenceHelper.putGlobalString(HEAD_PORTRAIT_KEY, headPortrait);
    }

    public static void clearUserInfo() {
        setLoginState(false);
        setUserNickname("");
        setHeadPortrait("");
        setInitializedDataLh(false);
        setInitializedDataHcg(false);
        setInitializedDataCrp(false);
        setFemaleRoleId("-1");
        setChildrenRoleId("-1");
        setSavedInitData(false);
//        HTHttp.clearCache();
    }


    public static int getDetectID() {
        return SharePreferenceHelper.getGlobalInt(DETECTED_ID, -1);
    }

    public static void setDetectID(int detectID) {
        SharePreferenceHelper.putGlobalInt(DETECTED_ID, detectID);
    }

    public static int getDetectType() {
        return SharePreferenceHelper.getGlobalInt(DETECTED_TYPE, -1);
    }

    public static void setDetectType(int detectType) {
        SharePreferenceHelper.putGlobalInt(DETECTED_TYPE, detectType);
    }



    public static String getQRCode() {
        return SharePreferenceHelper.getGlobalString(QR_CODE,null);
    }

    public static void setQRCode(String qrCode) {
        SharePreferenceHelper.putGlobalString(QR_CODE, qrCode);
    }


}
