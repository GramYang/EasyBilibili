package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.region.RegionDetailsInfo;
import com.developer.gram.easybilibili.mvp.contract.RegionTypeDetailsContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import javax.inject.Inject;

/**
 * Created by Gram on 2017/12/24.
 */

public class RegionTypeDetailsPresenter extends RxPresenter<RegionTypeDetailsContract.View> implements RegionTypeDetailsContract.Presenter<RegionTypeDetailsContract.View> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public RegionTypeDetailsPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getRegionTypeDetailsData(int rid) {
        addSubscribe(mRetrofitHelper.getBiliAppAPI()
                .getRegionDetails(rid)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<RegionDetailsInfo>(mView) {
                    @Override
                    public void onSuccess(RegionDetailsInfo regionDetailsInfo) {
                        mView.showRegionTypeDetails(regionDetailsInfo);
                    }
                })
        );
    }
}
