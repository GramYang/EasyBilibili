package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.discover.TopicCenterInfo;
import com.developer.gram.easybilibili.mvp.contract.TopicCenterContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

/**
 * Created by Gram on 2017/12/24.
 */

public class TopicCenterPresenter extends RxPresenter<TopicCenterContract.View> implements TopicCenterContract.Presenter<TopicCenterContract.View> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public TopicCenterPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getTopicCenterData() {
        addSubscribe(mRetrofitHelper.getBiliAPI()
                .getTopicCenterList()
                .map(TopicCenterInfo::getList)
                .map(listBeans -> {
                    List<TopicCenterInfo.ListBean> tempList = new ArrayList<>();
                    for (int i = 0, size = listBeans.size(); i < size; i++) {
                        TopicCenterInfo.ListBean listBean = listBeans.get(i);
                        if (!Objects.equals(listBean.getCover(), "")) {
                            tempList.add(listBean);
                        }
                    }
                    return tempList;
                })
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<List<TopicCenterInfo.ListBean>>(mView) {
                    @Override
                    public void onSuccess(List<TopicCenterInfo.ListBean> listBeans) {
                        mView.showTopicCenter(listBeans);
                    }
                })
        );
    }
}
