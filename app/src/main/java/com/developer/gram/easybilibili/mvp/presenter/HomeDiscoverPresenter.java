package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.discover.HotSearchTag;
import com.developer.gram.easybilibili.mvp.contract.HomeDiscoverContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Gram on 2018/1/4.
 */

public class HomeDiscoverPresenter extends RxPresenter<HomeDiscoverContract.View> implements HomeDiscoverContract.Presenter<HomeDiscoverContract.View> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public HomeDiscoverPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void getHotSearchTagData() {
        addSubscribe(mRetrofitHelper.getSearchAPI()
                .getHotSearchTags()
                .map(HotSearchTag::getList)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<List<HotSearchTag.ListBean>>(mView) {
                    @Override
                    public void onSuccess(List<HotSearchTag.ListBean> listBeans) {
                        mView.showHotSearchTag(listBeans);
                    }
                })
        );
    }
}