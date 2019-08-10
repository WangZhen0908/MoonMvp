package com.moon.mvp.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.moon.mvp.demo.R;

public class SplashActivity extends AppCompatActivity {

    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.i("SplashActivity", "SplashActivity onCreate");

        //1、启动页，3s后打开首页，统计启动时长
        //2、上一个页面被回收后,onActivityForResult是否能正常收到
        mTv = findViewById(R.id.tv);
        /*mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//                HomeActivity.sCallback = new HomeActivity.Callback() {
//                    @Override
//                    public void onSuccess() {
//                        mTv.setText("哈哈哈哈哈");
//                    }
//                };
//                startActivity(intent);
                startActivityForResult(intent, 1001);
            }
        });*/

        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                reportFullyDrawn();
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1001:
                mTv.setText("成功啦");
                break;
        }
    }
}
