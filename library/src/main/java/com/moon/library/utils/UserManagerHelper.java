package com.moon.library.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 多用户管理
 */
public class UserManagerHelper {

    private static UserManagerHelper mUserManagerHelper;
    private Context mApplicationContext;
    private SessionHelper mSessionHelper;

    private UserManagerHelper(@NonNull Context applicationContext, @NonNull String userId) {
        mApplicationContext = applicationContext;
        mSessionHelper = new SessionHelper(applicationContext, userId);
    }

    public static UserManagerHelper getInstance(@NonNull Context context, @NonNull String userId) {
        if (mUserManagerHelper == null) {
            synchronized (UserManagerHelper.class) {
                if (mUserManagerHelper == null) {
                    mUserManagerHelper = new UserManagerHelper(context.getApplicationContext(), userId);
                }
            }
        }
        return mUserManagerHelper;
    }

    /**
     * 退出登录和token失效记得回调该方法
     */
    public static void onDestroy() {
        mUserManagerHelper = null;
    }


    public void setMapValue(Context context, String key, Map<String, String> map) {
        JSONObject jsonObject = new JSONObject(map);
        mSessionHelper.saveStringKey(key, jsonObject.toString());
    }

    public Map<String, String> getMapValue(String key, Map<String, String> defaultValue) {
        String jsonString = mSessionHelper.loadStringKey(key, "");
        if (TextUtils.isEmpty(jsonString)) {
            return defaultValue;
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            Map<String, String> outputMap = new HashMap<String, String>();
            Iterator<String> keysItr = jsonObject.keys();
            while (keysItr.hasNext()) {
                String keyMap = keysItr.next();
                String valueMap = String.valueOf(jsonObject.get(keyMap));
                outputMap.put(keyMap, valueMap);
            }
            return outputMap;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}
