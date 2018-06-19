package com.moon.common.base.net.rx;

import android.support.annotation.NonNull;

import com.moon.common.base.net.rx.schedulers.BaseSchedulerProvider;
import com.moon.mvp.IView;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * network 相关方法封装
 */
public class NetWorkUtils {


    /**
     * @param <T>      the type parameter
     * @param flowable the observable
     * @return the observable
     */
    public static <T> Flowable<T> ioUiObservable(Flowable<T> flowable) {
        return flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> FlowableTransformer<T, T> ioUiObservable() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return ioUiObservable(upstream);
            }
        };
    }

    /**
     * 等待弹窗请求
     *
     * @param <T>      the type parameter
     * @param baseView the base view
     * @return the observable . transformer
     */
    public static <T> FlowableTransformer<T, T> netWorkEmptyLoadingScheduler(final @NonNull IView baseView) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return ioUiObservable(upstream)
                        .doOnSubscribe(new Consumer<Subscription>() {
                            @Override
                            public void accept(Subscription subscription) throws Exception {
                                baseView.showEmptyLoading();
                            }
                        })
                        .doOnTerminate(new Action() {
                            @Override
                            public void run() throws Exception {
                                baseView.hideEmptyLoading();
                            }
                        });
            }
        };
    }

    /**
     * 等待弹窗请求
     *
     * @param <T>      the type parameter
     * @param baseView the base view
     * @return the observable . transformer
     */
    public static <T> FlowableTransformer<T, T> netWorkLoadingScheduler(final @NonNull IView baseView) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return ioUiObservable(upstream)
                        .doOnSubscribe(new Consumer<Subscription>() {
                            @Override
                            public void accept(Subscription subscription) throws Exception {
                                baseView.showLoading();
                            }
                        })
                        .doOnTerminate(new Action() {
                            @Override
                            public void run() throws Exception {
                                baseView.hideLoading();
                            }
                        });
            }
        };
    }

    /**
     * 普通网络请求,没有网络加载滚动条
     *
     * @param <T> the type parameter
     * @return the observable . transformer
     */
    public static <T> FlowableTransformer<T, T> netWorkScheduler() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return ioUiObservable(upstream)
                        .doOnSubscribe(new Consumer<Subscription>() {
                            @Override
                            public void accept(Subscription subscription) throws Exception {
                            }
                        })
                        .doOnTerminate(new Action() {
                            @Override
                            public void run() throws Exception {
                            }
                        });
            }
        };
    }


    /**
     * 下拉网络请求
     *
     * @param <T>         the type parameter
     * @param basePtrView the base ptr view
     * @return the observable . transformer
     */
    public static <T> FlowableTransformer<T, T> netWorkRefreshScheduler(@NonNull final IView basePtrView) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return ioUiObservable(upstream)
                        .doOnSubscribe(new Consumer<Subscription>() {
                            @Override
                            public void accept(Subscription subscription) throws Exception {
//                            if (showLoading) basePtrView.showRefresh();
                            }
                        })
                        .doOnTerminate(new Action() {
                            @Override
                            public void run() throws Exception {
                            }
                        });
            }
        };
    }

    /**
     * 计算线程和ui线程转化
     *
     * @param <T>               the type parameter
     * @param schedulerProvider the scheduler provider
     * @return the observable . transformer
     */
    public static <T> FlowableTransformer<T, T> computationUISchedulers(
            final BaseSchedulerProvider schedulerProvider) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(schedulerProvider.computation())
                        .observeOn(schedulerProvider.ui())
                        .doOnSubscribe(new Consumer<Subscription>() {
                            @Override
                            public void accept(Subscription subscription) throws Exception {
                            }
                        })
                        .doOnTerminate(new Action() {
                            @Override
                            public void run() throws Exception {

                            }
                        });
            }
        };
    }


    /**
     * @param <T>               the type parameter
     * @param flowable          the observable
     * @param schedulerProvider the scheduler provider
     * @return the observable
     */
    public static <T> Flowable<T> ioUiObservable(Flowable<T> flowable,
                                                 BaseSchedulerProvider schedulerProvider) {
        return flowable
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui());
    }

    /**
     * @param <T>               the type parameter
     * @param single            the observable
     * @param schedulerProvider the scheduler provider
     * @return the observable
     */
    public static <T> Single<T> ioUiObservable(Single<T> single,
                                               BaseSchedulerProvider schedulerProvider) {
        return single
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui());
    }

    /**
     * 等待弹窗请求
     *
     * @param <T>               the type parameter
     * @param baseView          the base view
     * @param schedulerProvider the scheduler provider
     * @return the observable . transformer
     */
    public static <T> SingleTransformer<T, T> netWorkLoadingScheduler(final @NonNull IView baseView,
                                                                      final @NonNull BaseSchedulerProvider schedulerProvider) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(@io.reactivex.annotations.NonNull Single<T> upstream) {
                return ioUiObservable(upstream, schedulerProvider)
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull Disposable disposable) throws Exception {
                                baseView.showLoading();
                            }
                        })
                        .doOnEvent(new BiConsumer<T, Throwable>() {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull T t, @io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                                baseView.hideLoading();
                            }
                        });
            }
        };
    }

    /**
     * 普通网络请求,没有网络加载滚动条
     *
     * @param <T>               the type parameter
     * @param schedulerProvider the scheduler provider
     * @return the observable . transformer
     */
    public static <T> SingleTransformer<T, T> netWorkScheduler(
            final @NonNull BaseSchedulerProvider schedulerProvider) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(@io.reactivex.annotations.NonNull Single<T> upstream) {
                return ioUiObservable(upstream, schedulerProvider)
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull Disposable disposable) throws Exception {
                            }
                        })
                        .doOnEvent(new BiConsumer<T, Throwable>() {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull T t, @io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                            }
                        });

            }
        };
    }


    /**
     * 下拉网络请求
     *
     * @param <T>               the type parameter
     * @param basePtrView       the base ptr view
     * @param schedulerProvider the scheduler provider
     * @return the observable . transformer
     */
    public static <T> SingleTransformer<T, T> netWorkRefreshScheduler(@NonNull final IView basePtrView,
                                                                      final @NonNull BaseSchedulerProvider schedulerProvider) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(@io.reactivex.annotations.NonNull Single<T> upstream) {
                return ioUiObservable(upstream, schedulerProvider)
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull Disposable disposable) throws Exception {
                                //判断是否显示下拉刷新，没有显示着这显示下拉显示
                            }
                        })
                        .doOnEvent(new BiConsumer<T, Throwable>() {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull T t, @io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
//                                basePtrView.hideRefreshView();
                            }
                        });
            }
        };
    }

    /**
     * @param <T>    the type parameter
     * @param single the observable
     * @return the observable
     */
    public static <T> Single<T> singleIoUiObservable(Single<T> single) {
        return single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 普通网络请求,没有网络加载滚动条
     *
     * @param <T> the type parameter
     * @return the observable . transformer
     */
    public static <T> SingleTransformer<T, T> singleNetWorkScheduler() {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> upstream) {
                return singleIoUiObservable(upstream)
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {

                            }
                        })
                        .doAfterTerminate(new Action() {
                            @Override
                            public void run() throws Exception {

                            }
                        })
                        ;
            }
        };
    }

    /**
     * 普通网络请求,没有网络加载滚动条
     *
     * @param <T> the type parameter
     * @return the observable . transformer
     */
    public static <T> SingleTransformer<T, T> singleNetWorkLoadingScheduler(final @NonNull IView baseView) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> upstream) {
                return singleIoUiObservable(upstream)
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                baseView.showLoading();
                            }
                        })
                        .doAfterTerminate(new Action() {
                            @Override
                            public void run() throws Exception {
                                baseView.hideLoading();
                            }
                        })
                        ;
            }
        };
    }
}
