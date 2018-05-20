package com.developer.gram.easybilibili.mvp.presenter;


import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.discover.AllareasRankInfo;
import com.developer.gram.easybilibili.mvp.contract.AllAreasRankContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Gram on 2017/12/22.
 */

public class AllAreasRankPresenter extends RxPresenter<AllAreasRankContract.View> implements AllAreasRankContract.Presenter<AllAreasRankContract.View> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public AllAreasRankPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getAllAreasRankData(String type) {
        addSubscribe(mRetrofitHelper.getRankAPI()
                .getAllareasRanks(type)
                .map(allareasRankInfo -> allareasRankInfo.getRank().getList())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<List<AllareasRankInfo.RankBean.ListBean>>(mView){
                    @Override
                    public void onSuccess(List<AllareasRankInfo.RankBean.ListBean> listBeans) {
                        mView.showAllAreasRank(listBeans);
                    }
                })
        );
    }
}
