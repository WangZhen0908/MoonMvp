package com.moon.library.utils;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jakewharton.rxbinding2.widget.RxRadioGroup;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * rxjava manage view
 */

public class RxViewUtils {
    public static final int CHECK_WINDOW_DURATION = 500;

    /**
     * Non double click observable.
     *
     * @param view the view
     * @return the observable
     */
    public static Observable<Object> nonDoubleClick(@NonNull View view) {
        return RxView.clicks(view)
                .throttleFirst(CHECK_WINDOW_DURATION, TimeUnit.MILLISECONDS);

    }

    /**
     * Text change observable.
     *
     * @param view the view
     * @return the observable
     */
    public static Observable<String> textChange(TextView view) {
        return RxTextView.textChanges(view)
                .debounce(CHECK_WINDOW_DURATION, TimeUnit.MILLISECONDS)
                .map(new Function<CharSequence, String>() {
                    @Override
                    public String apply(CharSequence charSequence) {
                        return charSequence.toString().trim();
                    }
                });
    }

    /**
     * Focus change observable.
     *
     * @param view the view
     * @return the observable
     */
    public static Observable<Boolean> focusChange(View view) {
        return RxView.focusChanges(view);
    }

    public static boolean checkNotNull(View view) {
        return view != null;
    }

    public static <T extends Adapter> Observable<Integer> itemClicks(@NonNull AdapterView<T> view) {
        return RxAdapterView.itemClicks(view)
                .throttleFirst(CHECK_WINDOW_DURATION, TimeUnit.MILLISECONDS);
    }

    public static <T extends Adapter> Observable<Integer> itemSelections(@NonNull AdapterView<T> view) {
        return RxAdapterView.itemSelections(view)
                .throttleFirst(CHECK_WINDOW_DURATION, TimeUnit.MILLISECONDS);
    }

    public static Disposable textChanges(TextView tv, final TextChangeListener listener) {
        return textChanges(tv, false, CHECK_WINDOW_DURATION, listener);
    }

    public static Disposable textChanges(TextView tv, boolean nullable, final TextChangeListener listener) {
        return textChanges(tv, nullable, CHECK_WINDOW_DURATION, listener);
    }

    public static Disposable textChanges(TextView tv, final boolean nullable, long timeout, final TextChangeListener listener) {

        return RxTextView.textChanges(tv).debounce(timeout, TimeUnit.MILLISECONDS)
                .map(new Function<CharSequence, String>() {
                    @Override
                    public String apply(CharSequence charSequence) {
                        return charSequence.toString();
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String charSequence) throws Exception {
                        return nullable || !StringUtils.isNull(charSequence);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        if (listener != null) {
                            listener.onTextChange(s);
                        }
                    }
                });
    }

    public interface TextChangeListener {
        void onTextChange(String text);
    }

    /**
     * Count down observable.
     *
     * @param time 开始时间，默认1秒间隔，单位为秒
     * @return the observable
     */
    public static Observable<Integer> countDown(Long time) {
        return countDown(time, 1L, TimeUnit.SECONDS);
    }

    /**
     * Count down observable.
     *
     * @param time     倒计时的开始时间
     * @param interval 倒计时间隔
     * @param timeUnit 时间单位
     * @return the observable
     */
    public static Observable<Integer> countDown(Long time, Long interval, TimeUnit timeUnit) {
        if (time <= 0) {
            time = 0L;
        }
        final int countTime = time.intValue();

        return Observable.interval(0, interval, timeUnit)
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long aLong) throws Exception {
                        return countTime - aLong.intValue();
                    }
                }).take(countTime + 1);

    }

    /**
     * {@link RadioGroup#setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener)}
     *
     * @param radioGroup
     * @return
     */
    public static Observable<Integer> checkedChanges(RadioGroup radioGroup) {
        return RxRadioGroup.checkedChanges(radioGroup);
    }

}
