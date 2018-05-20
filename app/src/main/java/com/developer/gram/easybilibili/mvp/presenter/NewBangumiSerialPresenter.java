package com.developer.gram.easybilibili.mvp.presenter;


import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.bangumi.NewBangumiSerialInfo;
import com.developer.gram.easybilibili.mvp.contract.NewBangumiSerialContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Gram on 2017/12/24.
 */

public class NewBangumiSerialPresenter extends RxPresenter<NewBangumiSerialContract.View> implements NewBangumiSerialContract.Presenter<NewBangumiSerialContract.View>{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public NewBangumiSerialPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getNewBangumiSerialData() {
        addSubscribe(mRetrofitHelper.getBiliGoAPI()
                .getNewBangumiSerialList()
                .map(NewBangumiSerialInfo::getList)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<List<NewBangumiSerialInfo.ListBean>>(mView) {
                    @Override
                    public void onSuccess(List<NewBangumiSerialInfo.ListBean> listBeans) {
                        mView.showNewBangumiSerial(listBeans);
                    }
                })
        );
    }
}
