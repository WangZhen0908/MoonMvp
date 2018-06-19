package com.moon.library.utils.statusbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SystemBarUtils {

    //白色可以替换成其他浅色系
//    public static void lightStatusBar(Activity activity) {
//        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                if (MIUISetStatusBarLightMode(activity.getWindow(), true)) {//MIUI
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
//                        activity.getWindow().setStatusBarColor(Color.WHITE);
//                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4
//                        activity.getWindow().setFlags(
//                                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
//                        tintManager.setStatusBarTintEnabled(true);
//                        tintManager.setStatusBarTintResource(android.R.color.white);
//                    }
//                } else if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {//Flyme
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
//                        activity.getWindow().setStatusBarColor(Color.WHITE);
//                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4
//                        activity.getWindow().setFlags(
//                                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
//                        tintManager.setStatusBarTintEnabled(true);
//                        tintManager.setStatusBarTintResource(android.R.color.white);
//                    }
//                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0
//                    activity.getWindow().setStatusBarColor(Color.WHITE);
//                    activity.getWindow()
//                            .getDecorView()
//                            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//                }
//            }
//        } catch (Exception e) {
//
//        }
//    }

    //白色可以替换成其他浅色系
    public static void lightStatusBar(Activity activity) {
        lightStatusBar(activity, Color.WHITE);
    }


    //白色可以替换成其他浅色系
    public static void lightStatusBar(Activity activity, @ColorInt int color) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }

        try {

            if (MIUISetStatusBarLightMode(activity.getWindow(), true)
                    || FlymeSetStatusBarLightMode(activity.getWindow(), true)) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
                    activity.getWindow().setStatusBarColor(color);
                } else {
                    activity.getWindow().setFlags(
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    SystemBarTintManager tintManager = new SystemBarTintManager(activity);
                    tintManager.setStatusBarTintEnabled(true);
                    tintManager.setStatusBarTintResource(color);
                }
                return;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0
                activity.getWindow().setStatusBarColor(color);
                activity.getWindow()
                        .getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }

        } catch (Exception e) {

        }
    }

//    public static void onlyLightStatusBar(Activity activity) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            return;
//        }
//
//        try {
//
//            if (MIUISetStatusBarLightMode(activity.getWindow(), true)
//                    || FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
//                return;
//            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0
//                activity.getWindow()
//                        .getDecorView()
//                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            }
//
//        } catch (Exception e) {
//
//        }
//    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {

        boolean result = false;
        if (window != null) {

            if (Build.BRAND != null && Build.BRAND.toLowerCase().trim().equals("xiaomi")) {
                String miuiVersionName = getSystemProperties("ro.miui.ui.version.name");
                if (miuiVersionName != null && "v9".equals(miuiVersionName.trim().toLowerCase())) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

                    return true;
                }
            }

            Class clazz = window.getClass();
            try {

                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");

                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }

                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getSystemProperties(String key) {
        System.out.println("obtain key->" + key);
        String value = "";
        try {
            Resources.getSystem();
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getDeclaredMethod("get", String.class);
            value = (String) get.invoke(c, key);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("get " + key + " error...");
            value = "";
        }
        return value;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 手机的Android版本4.4以上设置沉浸式状态栏
     *
     * @param activity resId颜色为空时状态栏穿透
     */
    public static void setTranslucentStatusBar(Activity activity, int resId,
                                               boolean setPaddingTop) {
        if (activity == null) {
            return;
        }
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(ContextCompat.getColor(activity, resId));
            if (setPaddingTop) {
                ViewGroup contentView = ((ViewGroup) activity.findViewById(android.R.id.content));
                View childAt = contentView.getChildAt(0);
                if (childAt != null) {
                    childAt.setFitsSystemWindows(true);
                }
            }
        } else if (Build.VERSION.SDK_INT >= 19) {
            try {
                //透明状态栏
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                if (setPaddingTop) {
                    //设置contentview为fitsSystemWindows
                    ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
                    View childAt = contentView.getChildAt(0);
                    if (childAt != null) {
                        childAt.setFitsSystemWindows(true);
                    }
                    //给statusbar着色
                    View view = new View(activity);
                    view.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity)));
                    view.setBackgroundResource(resId);
                    contentView.addView(view);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 获取手机状态条的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
