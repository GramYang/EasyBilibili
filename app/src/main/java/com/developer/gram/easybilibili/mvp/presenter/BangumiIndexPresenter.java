package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.bangumi.BangumiIndexInfo;
import com.developer.gram.easybilibili.mvp.contract.BangumiIndexContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * Created by Gram on 2017/12/22.
 */

public class BangumiIndexPresenter extends RxPresenter<BangumiIndexContract.View> implements BangumiIndexContract.Presenter<BangumiIndexContract.View>{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public BangumiIndexPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getBangumiIndexData() {
        addSubscribe(mRetrofitHelper.getBangumiAPI()
                .getBangumiIndex()
                .delay(2000, TimeUnit.MILLISECONDS)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<BangumiIndexInfo>(mView) {
                    @Override
                    public void onSuccess(BangumiIndexInfo bangumiIndexInfo) {
                        mView.showBangumiIndex(bangumiIndexInfo);
                    }
                })
        );
    }
}
