package com.wurq.dex.mobilerecovery.recoveryapp.application;

import com.wurq.dex.mobilerecovery.hearttouch.db.SharePreferenceHelper;

/**
 * Created by ht-template
 **/
public class GlobalInfo {
    private static final String WELCOME_VERSION_KEY = "welcome_version";

    private static final String PHONE_UUID = "phone_uuid";

    private static String LOCAL_DB_VERSION = "local_db_version";//本地数据库版本

    private static final String POCT_INSTALL_CHANNEL = "poct_install_channel";

    public static int getWelComeVersion() {
        return SharePreferenceHelper.getGlobalInt(WELCOME_VERSION_KEY, -1);
    }

    public static void setWelComeVersion(int version) {
        SharePreferenceHelper.putGlobalInt(WELCOME_VERSION_KEY, version);
    }

    public static String getPhoneUUID() {
        return SharePreferenceHelper.getGlobalString(PHONE_UUID, "");
    }

    public static void setPhoneUUID(String uuid) {
        SharePreferenceHelper.putGlobalString(PHONE_UUID, uuid);
    }

    public static Long getLocalDBVersion() {
        return SharePreferenceHelper.getGlobalLong(LOCAL_DB_VERSION, 0);
    }

    public static void setLocalDbVersion(long version) {
        SharePreferenceHelper.putGlobalLong(LOCAL_DB_VERSION, version);
    }

    public static void setInstallChannel(String installChannel) {
        SharePreferenceHelper.putGlobalString(POCT_INSTALL_CHANNEL, installChannel);
    }

    public static String getInstallChannel() {
        return SharePreferenceHelper.getGlobalString(POCT_INSTALL_CHANNEL, null);
    }
}
