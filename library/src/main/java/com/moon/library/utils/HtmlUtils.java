package com.moon.library.utils;

import android.content.Context;

/**
 * Created by wangzhen1 on 2018/4/2.
 */

public class HtmlUtils {

    public static String MIME_TYPE = "text/html; charset=UTF-8";

    public static String wordWrap(String text) {
        if (text == null) {
            return null;
        }
        if (!text.startsWith("<p>") && !text.startsWith("<html>")) {

            String css = "<style type=\"text/css\"> img {" +
                    "width:100%;" +//限定图片宽度填充屏幕
                    "height:auto;" +//限定图片高度自动
                    "}" +
                    "body {" +
//            "margin-right:15px;" +//限定网页中的文字右边距为15px(可根据实际需要进行行管屏幕适配操作)
//            "margin-left:15px;" +//限定网页中的文字左边距为15px(可根据实际需要进行行管屏幕适配操作)
//            "margin-top:15px;" +//限定网页中的文字上边距为15px(可根据实际需要进行行管屏幕适配操作)
//            "font-size:40px;" +//限定网页中文字的大小为40px,请务必根据各种屏幕分辨率进行适配更改
                    "word-wrap:break-word;" +//允许自动换行(汉字网页应该不需要这一属性,这个用来强制英文单词换行,类似于word/wps中的西文换行)
                    "}" +
                    "</style>";
            String webPre = "<html><header>" + css + "</header>";
            String webEnd = "</html>";

            return webPre + text + webEnd;
        }
        return text;
    }

    /**
     * 离线加载的网页内容要加上的一些网页源码
     * 博主这里返回的网页源码是指包含body的内容的,所以这些网页的头和一些css样式可以直接拼接
     */
    public static String autoWrap(Context context, String text) {

        String codePrefixOne = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">" +
                "<html>" +
                "<head>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=\">";

        String style = "<style type=\"text/css\"> *{color:" + "#123456" + ";}body{word-wrap:break-word;font-family:Arial} </style>";

        String codePrefixTwo = "</head>" + "<body>";

        String codeSubfix = "</body></html>";

        int screenWidth = context.getResources().getDisplayMetrics().widthPixels - DensityUtil.dip2px(context, 30);
        String js = "<script type=\"text/javascript\">"
                + " var tables = document.getElementsByTagName('img');" + // 找到table标签
                "for(var i = 0; i<tables.length; i++){" +  // 逐个改变
                "if(tables[i].style.width >" + screenWidth + "){" +
                "tables[i].style.width = '100%';" +  // 宽度改为100%
                "tables[i].style.height = 'auto';" +
                "}" +
                "}" +
                "</script>";

        return codePrefixOne + style + js + codePrefixTwo + text + codeSubfix;


    }

}
