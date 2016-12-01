package com.wurq.dex.mobilerecovery.hearttouch.common.util.font;

import android.app.Application;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ht-template
 **/
public class FontManager {
    private static final Map<String, Typeface> CACHE_FONT_MAP = new HashMap<String, Typeface>();
    private static FontManager instance;
    private AssetManager assetManager;

    private FontManager(Application application) {
        assetManager = application.getAssets();
    }

    public static void init(Application application) {
        if (instance == null) {
            synchronized (FontManager.class) {
                if (instance == null) {
                    instance = new FontManager(application);
                }
            }
        }
    }

    public static FontManager getInstance() {
        if (instance == null) {
            throw new NullPointerException("NullPoint Exception, Please call createInstance first!");
        }
        return instance;
    }

    public Typeface getFont(FontType type) {
        if (type == null) {
            return null;
        }
        final String key = type.getValue();
        Typeface typeface = null;
        switch (type) {
            case ROBOTO_NORMAL:
                if (CACHE_FONT_MAP.containsKey(key)) {
                    return CACHE_FONT_MAP.get(key);
                } else {
                    typeface = Typeface.createFromAsset(assetManager, FontUtil.FONT_DIR + key);
                    CACHE_FONT_MAP.put(key, typeface);
                }
                break;
            default:
                break;
        }
        return typeface;
    }
}