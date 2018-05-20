package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.recommend.RecommendBannerInfo;
import com.developer.gram.easybilibili.bean.recommend.RecommendInfo;
import com.developer.gram.easybilibili.mvp.contract.HomeRecommendContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import javax.inject.Inject;

/**
 * Created by Gram on 2018/1/1.
 */

public class HomeRecommendPresenter extends RxPresenter<HomeRecommendContract.View> implements HomeRecommendContract.Presenter<HomeRecommendContract.View>{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public HomeRecommendPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getRecomendBannerData() {
        addSubscribe(mRetrofitHelper.getBiliAppAPI()
                .getRecommendedBannerInfo()
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<RecommendBannerInfo>(mView) {
                    @Override
                    public void onSuccess(RecommendBannerInfo recommendBannerInfo) {
                        mView.showRecommendBanner(recommendBannerInfo);
                    }
                })
        );
    }

    @Override
    public void getRecommendData() {
        addSubscribe(mRetrofitHelper.getBiliAppAPI()
                .getRecommendedInfo()
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<RecommendInfo>(mView){
                    @Override
                    public void onSuccess(RecommendInfo recommendInfo) {
                        mView.showRecommendInfo(recommendInfo);
                    }
                }));
    }
}
