package com.developer.gram.easybilibili.mvp.presenter;


import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.user.UserInterestQuanInfo;
import com.developer.gram.easybilibili.mvp.contract.UserInterestQuanContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Gram on 2017/12/25.
 */

public class UserInterestQuanPresenter extends RxPresenter<UserInterestQuanContract.View> implements UserInterestQuanContract.Presenter<UserInterestQuanContract.View>{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public UserInterestQuanPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getUserInterestData(int mid, int pageNum, int pageSize) {
        addSubscribe(mRetrofitHelper.getIm9API()
                .getUserInterestQuanData(mid, pageNum, pageSize)
                .map(userInterestQuanInfo -> userInterestQuanInfo.getData().getResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<List<UserInterestQuanInfo.DataBean.ResultBean>>(mView) {
                    @Override
                    public void onSuccess(List<UserInterestQuanInfo.DataBean.ResultBean> resultBeans) {
                        mView.showUserInterestQuan(resultBeans);
                    }
                })
        );
    }
}
