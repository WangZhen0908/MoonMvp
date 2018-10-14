package com.moon.retrofit.upload;

public interface ProgressListener {
    void onProgress(long current, long total);
}