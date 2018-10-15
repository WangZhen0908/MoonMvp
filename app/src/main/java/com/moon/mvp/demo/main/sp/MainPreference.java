package com.moon.mvp.demo.main.sp;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface MainPreference {

    String PREFERENCE_NAME = "main";

    String TOTAL_WORDS_INT = "TOTAL_WORDS_INT";

}
