package com.developer.gram.easybilibili.mvp.contract;


import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.user.UserContributeInfo;

import java.util.List;

/**
 * Created by Gram on 2017/12/25.
 */

public interface UserContributeContract {
    interface View extends BaseContract.BaseView {
        void showUserContribute(List<UserContributeInfo.DataBean.VlistBean> vlistBeans);
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getUserContributeData(int mid, int pageNum, int pageSize);
    }
}
