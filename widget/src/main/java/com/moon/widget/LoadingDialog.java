package com.moon.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


public class LoadingDialog extends AlertDialog {

    private View mContentView;
    private Context mContext;
    private ProgressBar mProgressBar;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.loading_dialog_style);
        init(context);
    }

    public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init(context);
    }

    public LoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void show() {
        super.show();
        if (mContentView == null) {
            mContentView = LayoutInflater.from(mContext).inflate(R.layout.layout_loading, null);
            mProgressBar = (ProgressBar) mContentView.findViewById(R.id.progressBar);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setContentView(mContentView, params);
        }

        mProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void dismiss() {
        super.dismiss();
        mProgressBar.setVisibility(View.INVISIBLE);
    }

}
