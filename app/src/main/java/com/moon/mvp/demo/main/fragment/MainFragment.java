package com.moon.mvp.demo.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.android.phone.scancode.export.ScanCallback;
import com.alipay.android.phone.scancode.export.ScanRequest;
import com.alipay.android.phone.scancode.export.adapter.MPScan;
import com.moon.common.base.fragment.BaseFragment;
import com.moon.library.utils.ToastUtils;
import com.moon.mvp.demo.R;
import com.moon.mvp.demo.main.contract.MainContract;
import com.moon.mvp.demo.main.presenter.MainPresenter;

public class MainFragment extends BaseFragment<MainContract.Presenter> implements MainContract.View {

    private TextView mHotWordsCountTv;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initViews(View view, @Nullable Bundle args) {
        super.initViews(view, args);
        mHotWordsCountTv = view.findViewById(R.id.hot_words_count_tv);
        mHotWordsCountTv.setText("上次：" + mPresenter.getHotWords());
        mHotWordsCountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });
    }

    @Override
    public MainContract.Presenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.app_fragment_main;
    }

    @Override
    public void requestData() {
        super.requestData();
        mPresenter.test();
    }

    @Override
    public void showHotWordsCount(int count) {
        mHotWordsCountTv.setText("最新：" + count);
    }

    private void scan() {
        ScanRequest scanRequest = new ScanRequest();
        scanRequest.setScanType(ScanRequest.ScanType.QRCODE);
        MPScan.startMPaasScanActivity(getActivity(), scanRequest, new ScanCallback() {
            @Override
            public void onScanResult(final boolean isProcessed, final Intent result) {
                if (!isProcessed) {
                    // 扫码界面点击物理返回键或左上角返回键
                    return;
                }
                // 注意：本回调是在子线程中执行
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result == null || result.getData() == null) {
                            // 扫码失败
                            return;
                        }
                        // 扫码成功
                        String url = result.getData().toString();
                        Toast.makeText(getActivity(), url, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
