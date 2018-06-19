package com.moon.library.utils;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 显示隐藏输入法工具
 */
public class InputMethodUtils {

    private static InputMethodManager imm;

    // 显示输入法
    public static void show(Context context, View focusView) {
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusView, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏输入法
     *
     * @param context
     */
    public static void hide(Context context) {
        View view = ((Activity) context).getWindow().peekDecorView();
        if (view != null && view.getWindowToken() != null) {
            imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // 调用该方法；键盘若显示则隐藏; 隐藏则显示
    public static void toggle(Context context) {
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    // 判断InputMethod的当前状态
    public static boolean isShow(Context context, View focusView) {
        Object obj = context.getSystemService(Context.INPUT_METHOD_SERVICE);
        System.out.println(obj);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean bool = imm.isActive(focusView);
        List<InputMethodInfo> mInputMethodProperties = imm.getEnabledInputMethodList();
        final int N = mInputMethodProperties.size();
        for (int i = 0; i < N; i++) {
            InputMethodInfo imi = mInputMethodProperties.get(i);
            if (imi.getId().equals(
                    Settings.Secure.getString(context.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD))) {
                break;
            }
        }
        return bool;
    }

    /**
     * 解决InputMethodManager内存泄露现象，在基类{@link Activity#onDestroy()}中解决
     */
    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field field;
        Object obj_get;
        for (String param : arr) {
            try {
                field = imm.getClass().getDeclaredField(param);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = field.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    // 被InputMethodManager持有引用的context是想要目标销毁的
                    if (v_get.getContext() == destContext) {
                        // 置空，破坏掉path to gc节点
                        field.set(imm, null);
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    /**
     * 强制显示软键盘
     *
     * @param context
     */
    public static void forceOpenSoftKeyboard(@NonNull Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
