package com.developer.gram.easybilibili.mvp.presenter;


import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.discover.OriginalRankInfo;
import com.developer.gram.easybilibili.mvp.contract.OriginalRankContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Gram on 2017/12/24.
 */

public class OriginalRankPresenter extends RxPresenter<OriginalRankContract.View> implements OriginalRankContract.Presenter<OriginalRankContract.View>{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public OriginalRankPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getOriginalRankData(String order) {
        addSubscribe(mRetrofitHelper.getRankAPI()
                .getOriginalRanks(order)
                .map(originalRankInfo -> originalRankInfo.getRank().getList())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<List<OriginalRankInfo.RankBean.ListBean>>(mView) {
                    @Override
                    public void onSuccess(List<OriginalRankInfo.RankBean.ListBean> listBeans) {
                        mView.showOriginalRank(listBeans);
                    }
                })
        );
    }
}
