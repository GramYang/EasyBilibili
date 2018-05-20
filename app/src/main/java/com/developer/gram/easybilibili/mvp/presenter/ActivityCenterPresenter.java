package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.discover.ActivityCenterInfo;
import com.developer.gram.easybilibili.mvp.contract.ActivityCenterContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * Created by Gram on 2017/12/21.
 */

public class ActivityCenterPresenter extends RxPresenter<ActivityCenterContract.View> implements ActivityCenterContract.Presenter<ActivityCenterContract.View> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public ActivityCenterPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getActivityCenterData(int page, int pageSize) {
        addSubscribe(mRetrofitHelper.getBiliAPI()
                .getActivityCenterList(page, pageSize)
                .delay(1000, TimeUnit.MILLISECONDS)
                .map(ActivityCenterInfo::getList)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<List<ActivityCenterInfo.ListBean>>(mView) {
                    @Override
                    public void onSuccess(List<ActivityCenterInfo.ListBean> listBeans) {
                        mView.showActivityCenter(listBeans);
                    }
                })
        );
    }
}
