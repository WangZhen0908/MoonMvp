package com.moon.mvp.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.moon.mvp.demo.R;

public class HomeActivity extends AppCompatActivity {

    interface Callback {
        void onSuccess();
    }

    public static Callback sCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
    }
}
