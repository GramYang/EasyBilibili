package com.developer.gram.easybilibili.mvp.presenter;


import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.region.RegionRecommendInfo;
import com.developer.gram.easybilibili.mvp.contract.AdvertisingContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.ConstantUtil;
import com.developer.gram.easybilibili.util.RxUtil;

import javax.inject.Inject;

/**
 * Created by Gram on 2017/12/22.
 */

public class AdvertisingPresenter extends RxPresenter<AdvertisingContract.View> implements AdvertisingContract.Presenter<AdvertisingContract.View>{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public AdvertisingPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getAdvertisingData() {
        addSubscribe(mRetrofitHelper.getBiliAppAPI()
                .getRegionRecommends(ConstantUtil.ADVERTISING_RID)
                .map(RegionRecommendInfo::getData)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<RegionRecommendInfo.DataBean>(mView){
                    @Override
                    public void onSuccess(RegionRecommendInfo.DataBean dataBean) {
                        mView.showAdvertising(dataBean);
                    }
                })
        );
    }
}
