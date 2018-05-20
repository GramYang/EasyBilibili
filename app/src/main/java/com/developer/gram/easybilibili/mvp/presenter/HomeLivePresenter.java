package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.live.LiveAppIndexInfo;
import com.developer.gram.easybilibili.mvp.contract.HomeLiveContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import javax.inject.Inject;

/**
 * Created by Gram on 2017/12/20.
 */

public class HomeLivePresenter extends RxPresenter<HomeLiveContract.View> implements HomeLiveContract.Presenter<HomeLiveContract.View> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public HomeLivePresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getLiveData() {
        addSubscribe(mRetrofitHelper.getLiveAPI()
                .getLiveAppIndex()
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<LiveAppIndexInfo>(mView) {
                    @Override
                    public void onSuccess(LiveAppIndexInfo liveAppIndexInfo) {
                        mView.showLive(liveAppIndexInfo);
                    }
                }));
    }
}
