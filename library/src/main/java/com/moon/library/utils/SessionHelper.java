package com.moon.library.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.util.ArrayMap;

public class SessionHelper {

    protected SharedPreferences settings;
    protected SharedPreferences.Editor editor;

    private static ArrayMap<String, SessionHelper> sHelpers = new ArrayMap<>();

    public static SessionHelper getInstance(Context context, String name) {
        SessionHelper sessionHelper = sHelpers.get(name);
        if (sessionHelper == null) {
            sessionHelper = new SessionHelper(context, name);
            sHelpers.put(name, sessionHelper);
        }
        return sessionHelper;
    }

    private SessionHelper(Context context, String name) {
        settings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    /**
     * Load the value referred to the configuration given the key
     *
     * @param key the String formatted key representing the value to be loaded
     * @return String String formatted vlaue related to the give key
     */
    protected String loadKey(String key) {
        return settings.getString(key, null);
    }

    /**
     * Save the loaded twin key-value using the android context package SharedPreferences.Editor
     * instance
     *
     * @param key   the key to be saved
     * @param value the value related to the key String formatted
     */
    protected void saveKey(String key, String value) {

        editor.putString(key, value);
        editor.commit();
    }

    public void removeKey(String key) {
        editor.remove(key);
        editor.commit();
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

    public boolean loadBooleanKey(String key, boolean defaultValue) {
        String v = loadKey(key);
        boolean bv;
        if (v == null) {
            bv = defaultValue;
        } else {
            bv = v.equals("TRUE");
        }
        return bv;
    }

    public void saveBooleanKey(String key, boolean value) {
        String v;
        if (value) {
            v = "TRUE";
        } else {
            v = "FALSE";
        }
        saveKey(key, v);
    }

    public int loadIntKey(String key, int defaultValue) {
        String v = loadKey(key);
        int iv;
        if (v == null) {
            iv = defaultValue;
        } else {
            try {
                iv = Integer.parseInt(v);
            } catch (Exception e) {
                iv = defaultValue;
            }
        }
        return iv;
    }

    public void saveIntKey(String key, int value) {
        String v = String.valueOf(value);
        saveKey(key, v);
    }

    public long loadLongKey(String key, long defaultValue) {
        String v = loadKey(key);
        long iv;
        if (v == null) {
            iv = defaultValue;
        } else {
            try {
                iv = Long.parseLong(v);
            } catch (Exception e) {
                iv = defaultValue;
            }
        }
        return iv;
    }

    public void saveLongKey(String key, long value) {
        String v = String.valueOf(value);
        saveKey(key, v);
    }

    public double loadDoubleKey(String key, double defaultValue) {
        String v = loadKey(key);
        double iv;
        if (v == null) {
            iv = defaultValue;
        } else {
            try {
                iv = Double.parseDouble(v);
            } catch (Exception e) {
                iv = defaultValue;
            }
        }
        return iv;
    }

    public void saveDoubleKey(String key, double value) {
        String v = String.valueOf(value);
        saveKey(key, v);
    }

    public double loadFloatKey(String key, float defaultValue) {
        String v = loadKey(key);
        float iv;
        if (v == null) {
            iv = defaultValue;
        } else {
            try {
                iv = Float.parseFloat(v);
            } catch (Exception e) {
                iv = defaultValue;
            }
        }
        return iv;
    }

    public void saveFloatKey(String key, float value) {
        String v = String.valueOf(value);
        saveKey(key, v);
    }

    public String loadStringKey(String key, String defaultValue) {
        String v = loadKey(key);
        if (v == null) {
            v = defaultValue;
        }
        return v;
    }

    public void saveStringKey(String key, String value) {
        saveKey(key, value);
    }
}