package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.region.RegionRecommend;
import com.developer.gram.easybilibili.bean.region.RegionTypesInfo;
import com.developer.gram.easybilibili.mvp.contract.HomeRegionContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.JsonUtils;
import com.developer.gram.easybilibili.util.RxUtil;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by Gram on 2018/1/2.
 */

public class HomeRegionPresenter extends RxPresenter<HomeRegionContract.View> implements HomeRegionContract.Presenter<HomeRegionContract.View> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public HomeRegionPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getRegionTypeData() {
        addSubscribe(Flowable.just(JsonUtils.readAssetsJson("region.json"))
                .map(s -> new Gson().fromJson(s, RegionTypesInfo.class))
                .map(RegionTypesInfo::getData)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<List<RegionTypesInfo.DataBean>>(mView) {
                    @Override
                    public void onSuccess(List<RegionTypesInfo.DataBean> dataBeans) {
                        mView.showRegion(dataBeans);
                    }
                }));
    }

    @Override
    public void getRegionData() {
        addSubscribe(mRetrofitHelper.getBiliAppAPI()
                .getRegion()
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<RegionRecommend>(mView) {
                    @Override
                    public void onSuccess(RegionRecommend regionRecommend) {
                        mView.showRegionRecommend(regionRecommend);
                    }
                })
        );
    }
}
