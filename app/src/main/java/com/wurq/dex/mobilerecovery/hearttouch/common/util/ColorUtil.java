package com.wurq.dex.mobilerecovery.hearttouch.common.util;

import android.content.res.ColorStateList;

/**
 * Created by ht-template
 **/
public class ColorUtil {
    /**
     * 对TextView设置不同状态时其文字颜色。
     */
    //Color.BLACK, 0xff3fc380, 0xff3fc380, Color.BLACK, 0xff3fc380
    public static ColorStateList createColorStateList(int normal, int pressed, int focused, int unable, int selected) {
        int[] colors = new int[]{pressed, normal, selected, normal};
        int[][] states = new int[4][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
//        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
        states[1] = new int[]{android.R.attr.state_enabled};
//        states[3] = new int[] { android.R.attr.state_focused };
//        states[2] = new int[] { android.R.attr.state_window_focused };
        states[2] = new int[]{android.R.attr.state_selected};
        states[3] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }
}
