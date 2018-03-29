package com.moon.mvp.impl;

/**
 * Created by Wilson on 2018/3/15.
 */

public interface ISupportView {
    /**
     * 同级下的 懒加载 ＋ ViewPager下的懒加载  的结合回调方法，只回调一次
     */
    void onLazyInitView();

    /**
     * 每次用户页面显示回调
     */
    void onFragmentShow();
}
