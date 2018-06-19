package com.moon.library.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by wangzhen1 on 2018/3/14.
 */

public final class IntentUtils {


    public static void toDial(Context context, String tel) {
        //跳转到拨号界面，同时传递电话号码
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        context.startActivity(dialIntent);
    }

}
