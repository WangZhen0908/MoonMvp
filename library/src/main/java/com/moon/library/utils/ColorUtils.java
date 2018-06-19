package com.moon.library.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;

/**
 * {@link android.graphics.Color}工具类
 */

public class ColorUtils {

    public static int getColor(@NonNull Context context, @ColorRes int id) {
        return ResourcesCompat.getColor(context.getResources(), id, null);
    }

}
