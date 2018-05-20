package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.region.RegionRecommendInfo;
import com.developer.gram.easybilibili.mvp.contract.RegionTypeRecommendContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import javax.inject.Inject;

/**
 * Created by Gram on 2017/12/24.
 */

public class RegionTypeRecommendPresenter extends RxPresenter<RegionTypeRecommendContract.View> implements RegionTypeRecommendContract.Presenter<RegionTypeRecommendContract.View> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public RegionTypeRecommendPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getRegionTypeRecommendData(int rid) {
        addSubscribe(mRetrofitHelper.getBiliAppAPI()
                .getRegionRecommends(rid)
                .map(RegionRecommendInfo::getData)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<RegionRecommendInfo.DataBean>(mView) {
                    @Override
                    public void onSuccess(RegionRecommendInfo.DataBean dataBean) {
                        mView.showRegionTypeRecommend(dataBean);
                    }
                })
        );
    }
}
