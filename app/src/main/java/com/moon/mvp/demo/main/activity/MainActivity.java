package com.moon.mvp.demo.main.activity;


import android.support.v4.app.Fragment;

import com.moon.common.base.activity.BaseFragmentActivity;
import com.moon.mvp.demo.main.fragment.MainFragment;

public class MainActivity extends BaseFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return MainFragment.newInstance();
    }
}
