package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.bangumi.SeasonNewBangumiInfo;
import com.developer.gram.easybilibili.mvp.contract.SeasonNewBangumiContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import javax.inject.Inject;

/**
 * Created by Gram on 2017/12/24.
 */

public class SeasonNewBangumiPresenter extends RxPresenter<SeasonNewBangumiContract.View> implements SeasonNewBangumiContract.Presenter<SeasonNewBangumiContract.View>{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public SeasonNewBangumiPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getSeasonNewBangumiData() {
        addSubscribe(mRetrofitHelper.getBangumiAPI()
                .getSeasonNewBangumiList()
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<SeasonNewBangumiInfo>(mView) {
                    @Override
                    public void onSuccess(SeasonNewBangumiInfo seasonNewBangumiInfo) {
                        mView.showSeasonNewBangumi(seasonNewBangumiInfo);
                    }
                })
        );
    }
}
