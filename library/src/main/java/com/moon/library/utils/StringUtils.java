package com.moon.library.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String utils
 */

public class StringUtils {

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(@Nullable String str) {
        return str == null || str.length() == 0 || "".equals(str.trim());
    }

    public static boolean isNotEmpty(@Nullable String str) {
        return !isEmpty(str);
    }

    /**
     * 处理字体大小一样，颜色不一样的文字拼接
     *
     * @param spanString
     * @param spanColor
     * @return
     */
    public static SpannableStringBuilder setSpanColor(String[] spanString, int[] spanColor) {
        if (spanString.length != spanColor.length) {
            throw new IllegalArgumentException("must spanString.length == spanColor.length");
        }
        SpannableStringBuilder style = new SpannableStringBuilder();
        for (String aSpanString : spanString) {
            if (!TextUtils.isEmpty(aSpanString)) {
                style.append(aSpanString);
            }
        }
        StringBuilder spanBuilder = new StringBuilder();
        for (int i = 0, length = spanString.length; i < length; i++) {
            if (!TextUtils.isEmpty(spanString[i])) {
                style.setSpan(new ForegroundColorSpan(spanColor[i]), spanBuilder.length(), spanBuilder.length() + spanString[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spanBuilder.append(spanString[i]);
            }
        }
        return style;
    }

    /**
     * 处理字体大小不一样
     *
     * @param spanString
     * @param spanSize
     * @return
     */
    public static SpannableStringBuilder setSpanSize(String[] spanString, float[] spanSize) {
        if (spanString.length != spanSize.length) {
            throw new IllegalArgumentException("must spanString.length == spanSize.length");
        }
        SpannableStringBuilder style = new SpannableStringBuilder();
        for (String aSpanString : spanString) {
            style.append(aSpanString);
        }
        StringBuilder spanBuilder = new StringBuilder();
        for (int i = 0, length = spanString.length; i < length; i++) {
            style.setSpan(new RelativeSizeSpan(spanSize[i]), spanBuilder.length(), spanBuilder.length() + spanString[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanBuilder.append(spanString[i]);
        }
        return style;
    }

    /**
     * 字符串匹配，并填充颜色
     *
     * @param context
     * @param wholeStr
     * @param highlightStr
     * @param color
     * @return
     */
    @Nullable
    public static SpannableStringBuilder fillColor(Context context, String wholeStr, String highlightStr, @ColorRes int color) {
        if (isNotEmpty(wholeStr) && isNotEmpty(highlightStr)) {
            SpannableStringBuilder spBuilder = new SpannableStringBuilder(wholeStr);
            //匹配规则,整个字符串中有匹配到的
            Pattern p = Pattern.compile(highlightStr);
            //匹配字段
            Matcher matcher = p.matcher(spBuilder);
            //上色
            int fillColor = context.getResources().getColor(color);
            //开始循环查找里面是否包含关键字  使得一句话中出现多个关键词都会被高亮
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                spBuilder.setSpan(new ForegroundColorSpan(fillColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return spBuilder;
        }
        return null;
    }

    /**
     * 比较字符串
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) {
            return true;
        }
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断不是相等两个字符串
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean isNotEquals(CharSequence a, CharSequence b) {
        return !equals(a, b);
    }

    public static boolean isNull(String text) {
        return text == null || "".equals(text.trim());
    }

    public static List<String> split(String s, String regex) {
        if (isNull(s) || isNull(regex)) {
            return null;
        }
        return Arrays.asList(s.split(regex));
    }

    /**
     * 判断对象是否为空
     *
     * @param reference
     * @return 如果对象不是null, 返回对象;否则抛出NullPointerException
     */

    public static String checkNotNull(String reference) {

        if (isEmpty(reference)) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    /**
     * 是否是图片文件
     *
     * @param url
     * @return
     */
    public static boolean isImageFile(String url) {
        return url != null && (url.endsWith(".png") || url.endsWith(".jpg") || url.endsWith(".jpeg"));
    }

    /**
     * 是否为pdf文件
     *
     * @param url
     * @return
     */
    public static boolean isPdfFile(String url) {
        return url != null && url.endsWith(".pdf");
    }

    /**
     * @param url
     * @return
     */
    public static boolean isWordFile(String url) {
        return url != null && (url.endsWith(".docx") ||url.endsWith(".doc"));
    }
}
