package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.mvp.contract.LivePlayerContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by Gram on 2017/12/24.
 */

public class LivePlayerPresenter extends RxPresenter<LivePlayerContract.View> implements LivePlayerContract.Presenter<LivePlayerContract.View> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public LivePlayerPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getLivePlayerData(int cid) {
        addSubscribe(mRetrofitHelper.getLiveAPI()
                .getLiveUrl(cid)
                .map(responseBody -> {
                    try {
                    String str = responseBody.string();
                    String result = str.substring(str.lastIndexOf("[") + 1, str.lastIndexOf("]") - 1);
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }})
                .flatMap(s -> {
                    mView.playLive(s);
                    return Flowable.timer(2000, TimeUnit.MILLISECONDS);})
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<Long>(mView) {
                    @Override
                    public void onSuccess(Long aLong) {
                        mView.refreshUI();
                    }
                })
        );
    }
}
