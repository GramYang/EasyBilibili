package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.bangumi.BangumiAppIndexInfo;
import com.developer.gram.easybilibili.bean.bangumi.BangumiRecommendInfo;
import com.developer.gram.easybilibili.mvp.contract.HomeBangumiContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import javax.inject.Inject;

/**
 * Created by Gram on 2018/1/2.
 */

public class HomeBangumiPresenter extends RxPresenter<HomeBangumiContract.View> implements HomeBangumiContract.Presenter<HomeBangumiContract.View> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public HomeBangumiPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getBangumiIndexData() {
        addSubscribe(mRetrofitHelper.getBangumiAPI()
                .getBangumiAppIndex()
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<BangumiAppIndexInfo>(mView) {
                    @Override
                    public void onSuccess(BangumiAppIndexInfo bangumiAppIndexInfo) {
                        mView.showBangumiIndex(bangumiAppIndexInfo);
                    }
                })
        );
    }

    @Override
    public void getBangumiRecommendData() {
        addSubscribe(mRetrofitHelper.getBangumiAPI()
                .getBangumiRecommended()
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<BangumiRecommendInfo>(mView) {
                    @Override
                    public void onSuccess(BangumiRecommendInfo bangumiRecommendInfo) {
                        mView.showBangumiRecommend(bangumiRecommendInfo);
                    }
                })
        );
    }
}
