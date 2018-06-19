package com.moon.library.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Toast提示工具类,防止连续{@link Toast}
 */
public class ToastUtils {

    private static Toast mToast = null;
    private static Context sContext;
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    /**
     * toast显示间隔时间
     */
    private static final long INTERVAL_TIME = 1000L;

    private static Map<Object, Long> mLastMap = new HashMap<>();

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    public static void show(String text) {
        showToast(text, Toast.LENGTH_LONG);
    }

    public static void show(@StringRes int resId) {
        showToast(sContext.getString(resId), Toast.LENGTH_LONG);
    }

    public static void showToast(final String text, final int duration) {
        showToast(sContext, text, duration);
    }

    public static void showToast(final @NonNull Context context, final String text, final int duration) {
        if (TextUtils.isEmpty(text) || context == null) {
            return;
        }

        sHandler.post(new Runnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();

                if (mLastMap.isEmpty() || !mLastMap.containsKey(text)
                        || currentTime - mLastMap.get(text) > INTERVAL_TIME) {
                    if (mToast != null) {
                        mToast.setText(text);
                        mToast.setDuration(duration);
                    } else {
                        mToast = Toast.makeText(context.getApplicationContext(), text, duration);
                    }
                    mLastMap.put(text, currentTime + duration);
                    mToast.show();
                }
            }
        });
    }
}
