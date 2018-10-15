package com.moon.mvp.demo.account.sp;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface AccountPreference {

    String NAME = "account";

    String SESSION_STRING = "SESSION_STRING";

    String FIRST_IN_BOOL = "FIRST_IN_BOOL";

    String USER_INFO_OBJ = "USER_INFO_OBJ";

}
