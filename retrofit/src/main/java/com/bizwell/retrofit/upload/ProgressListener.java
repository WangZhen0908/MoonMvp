package com.bizwell.retrofit.upload;

public interface ProgressListener {
    void onProgress(long current, long total);
}