package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.bangumi.BangumiScheduleInfo;
import com.developer.gram.easybilibili.mvp.contract.BangumiScheduleContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * Created by Gram on 2017/12/22.
 */

public class BangumiSchedulePresenter extends RxPresenter<BangumiScheduleContract.View> implements BangumiScheduleContract.Presenter<BangumiScheduleContract.View> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public BangumiSchedulePresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getBangumiScheduleData() {
        addSubscribe(mRetrofitHelper.getBangumiAPI()
                .getBangumiSchedules()
                .delay(2000, TimeUnit.MILLISECONDS)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<BangumiScheduleInfo>(mView) {
                    @Override
                    public void onSuccess(BangumiScheduleInfo bangumiScheduleInfo) {
                        mView.showBangumiSchedule(bangumiScheduleInfo);
                    }
                })
        );
    }
}
