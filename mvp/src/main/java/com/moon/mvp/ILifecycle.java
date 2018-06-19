package com.moon.mvp;

import com.trello.rxlifecycle2.LifecycleTransformer;

public interface ILifecycle {
    <T> LifecycleTransformer<T> bindLifecycle();
}
