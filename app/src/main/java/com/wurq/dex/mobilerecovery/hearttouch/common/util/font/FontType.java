package com.wurq.dex.mobilerecovery.hearttouch.common.util.font;

/**
 * Created by ht-template
 **/
public enum FontType {
    ROBOTO_NORMAL("Roboto-Normal.ttf"),
    SOURCE_HANSANS_CN_NORMAL("SourceHanSansCN-Normal.otf");
//    SOURCE_HANSANS_CN_NORMAL("SourceHanSansCN-Normal.ttf");

    String value;

    FontType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
