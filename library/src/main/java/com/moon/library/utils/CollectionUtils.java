package com.moon.library.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionUtils {


    /**
     * 无效集合
     *
     * @param collection
     * @return
     */
    public static boolean isInvalid(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 有效集合
     *
     * @param collection
     * @return
     */
    public static boolean isValid(Collection<?> collection) {
        return !isInvalid(collection);
    }

    public static List<String> splitAsListByComma(String str) {
        return splitAsList(str, ",");
    }

    public static List<String> splitAsList(String str, String regex) {
        if (str == null || "".equals(str.trim()) || regex == null || "".equals(regex)) {
            return null;
        }
        String[] strs = str.split(regex);
        if (strs.length == 0) {
            return null;
        }

        ArrayList<String> list = new ArrayList<>();
        for (String s : strs) {
            list.add(s);
        }

        return list;
    }

    public static String listToString(List<String> list) {
        return listToString(list, ",");
    }

    public static String listToString(List<String> list, String regex) {
        if (isInvalid(list)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0, size = list.size(); i < size; i++) {
            String str = list.get(i);
            sb.append(str);
            //去除最后一位逗号
            if (i != size - 1) {
                sb.append(regex);
            }
        }
        return sb.toString();
    }
}
