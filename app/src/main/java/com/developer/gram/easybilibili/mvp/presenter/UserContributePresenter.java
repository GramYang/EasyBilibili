package com.developer.gram.easybilibili.mvp.presenter;


import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.user.UserContributeInfo;
import com.developer.gram.easybilibili.mvp.contract.UserContributeContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Gram on 2017/12/25.
 */

public class UserContributePresenter extends RxPresenter<UserContributeContract.View> implements UserContributeContract.Presenter<UserContributeContract.View> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public UserContributePresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getUserContributeData(int mid, int pageNum, int pageSize) {
        addSubscribe(mRetrofitHelper.getUserAPI()
                .getUserContributeVideos(mid, pageNum, pageSize)
                .map(userContributeInfo -> userContributeInfo.getData().getVlist())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<List<UserContributeInfo.DataBean.VlistBean>>(mView) {
                    @Override
                    public void onSuccess(List<UserContributeInfo.DataBean.VlistBean> vlistBeans) {
                        mView.showUserContribute(vlistBeans);
                    }
                })
        );
    }
}
