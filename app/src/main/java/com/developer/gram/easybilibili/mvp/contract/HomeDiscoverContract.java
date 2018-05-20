package com.developer.gram.easybilibili.mvp.contract;

import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.discover.HotSearchTag;

import java.util.List;

/**
 * Created by Gram on 2018/1/4.
 */

public interface HomeDiscoverContract {
    interface View extends BaseContract.BaseView {
        void showHotSearchTag(List<HotSearchTag.ListBean> listBeans);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getHotSearchTagData();
    }
}
