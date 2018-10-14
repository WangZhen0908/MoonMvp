package com.moon.retrofit.upload;

import android.support.annotation.NonNull;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/3/15.
 */
public class UploadUtils {
    @NonNull
    public static RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MultipartBody.FORM, descriptionString);
    }

    @NonNull
    public static MultipartBody.Part prepareFilePart(String partName, File file) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/jpg"), file);
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    @NonNull
    public static MultipartBody.Part prepareFilePart(String partName, File file, ProgressListener requestListener) {
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/jpeg"), file);
//        ProgressRequestBody requestBody = new ProgressRequestBody(requestFile,requestListener);
        CountingRequestBody requestBody = new CountingRequestBody(requestFile, requestListener);
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }
}