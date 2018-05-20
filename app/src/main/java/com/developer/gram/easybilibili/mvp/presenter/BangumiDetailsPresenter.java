package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.bangumi.BangumiDetailsCommentInfo;
import com.developer.gram.easybilibili.bean.bangumi.BangumiDetailsInfo;
import com.developer.gram.easybilibili.bean.bangumi.BangumiDetailsRecommendInfo;
import com.developer.gram.easybilibili.mvp.contract.BangumiDetailsContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Gram on 2017/12/22.
 */

public class BangumiDetailsPresenter extends RxPresenter<BangumiDetailsContract.View> implements BangumiDetailsContract.Presenter<BangumiDetailsContract.View> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public BangumiDetailsPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getBangumiDetailsData() {
        addSubscribe(mRetrofitHelper.getBangumiAPI()
                .getBangumiDetails()
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<BangumiDetailsInfo>(mView) {
                    @Override
                    public void onSuccess(BangumiDetailsInfo bangumiDetailsInfo) {
                        mView.showBangumiDetails(bangumiDetailsInfo);
                    }
                })
        );

        addSubscribe(mRetrofitHelper.getBangumiAPI()
                .getBangumiDetailsRecommend()
                .map(bangumiDetailsRecommendInfo -> bangumiDetailsRecommendInfo.getResult().getList())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<List<BangumiDetailsRecommendInfo.ResultBean.ListBean>>(mView) {
                    @Override
                    public void onSuccess(List<BangumiDetailsRecommendInfo.ResultBean.ListBean> listBeans) {
                        mView.showBangumiDetailsRecommend(listBeans);
                    }
                })
        );

        addSubscribe(mRetrofitHelper.getBiliAPI()
                .getBangumiDetailsComments()
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<BangumiDetailsCommentInfo>(mView) {
                    @Override
                    public void onSuccess(BangumiDetailsCommentInfo bangumiDetailsCommentInfo) {
                        mView.showBangumiDetailsComments(bangumiDetailsCommentInfo);
                    }
                })
        );
    }
}
