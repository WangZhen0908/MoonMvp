package com.moon.library.utils.statusbar;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;

/**
 * 颜色工具类
 */

public class ColorUtils {
    @ColorInt
    public static int getColor(@NonNull Context context, @ColorRes int id) {
        return ResourcesCompat.getColor(context.getResources(), id, null);
    }
}
