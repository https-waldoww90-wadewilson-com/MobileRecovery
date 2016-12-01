package com.wurq.dex.mobilerecovery.hearttouch.common.util.font;

import android.app.Application;
import android.graphics.Typeface;

//import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
//import uk.co.chrisjenx.calligraphy.R;

/**
 * Created by ht-template
 **/
public class FontUtil {
    public static final String FONT_DIR = "fonts/";

    public static void init(Application application) {
        FontManager.init(application);

//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath(FONT_DIR + FontType.SOURCE_HANSANS_CN_NORMAL.getValue())
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
    }

    public static Typeface getFont(FontType type) {
        try {
            FontManager manager = FontManager.getInstance();
            return manager.getFont(type);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
