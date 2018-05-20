package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.Splash.Splash;
import com.developer.gram.easybilibili.mvp.contract.SplashContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Gram on 2017/12/29.
 */

public class SplashPresenter extends RxPresenter<SplashContract.View> implements SplashContract.Presenter<SplashContract.View>{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public SplashPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getSplashData() {
        BaseSubscriber<Splash> subscriber = mRetrofitHelper.getBiliAppAPI().getSplash()
                .subscribeWith(new BaseSubscriber<Splash>(mView) {
                    @Override
                    public void onSuccess(Splash splash) {
                        mView.showSplash(splash);
                    }
                });
        addSubscribe(subscriber);


    }

    /**
     * 5s 倒计时
     */
    @Override
    public void setCountDown() {
        final Long count = 5L;
        Disposable subscribe = Flowable.interval(0, 1, TimeUnit.SECONDS)
                .map(aLong -> count - aLong)
                .take(count + 1)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(aLong -> mView.showCountDown(aLong.intValue()));
        addSubscribe(subscribe);
    }
}