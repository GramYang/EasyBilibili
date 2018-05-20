package com.developer.gram.easybilibili.mvp.contract;

import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.bangumi.NewBangumiSerialInfo;

import java.util.List;

/**
 * Created by Gram on 2017/12/24.
 */

public interface NewBangumiSerialContract {
    interface View extends BaseContract.BaseView {
        void showNewBangumiSerial(List<NewBangumiSerialInfo.ListBean> listBeans);
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getNewBangumiSerialData();
    }
}
