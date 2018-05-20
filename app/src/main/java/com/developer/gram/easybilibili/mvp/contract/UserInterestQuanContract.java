package com.developer.gram.easybilibili.mvp.contract;


import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.user.UserInterestQuanInfo;

import java.util.List;

/**
 * Created by Gram on 2017/12/25.
 */

public interface UserInterestQuanContract {
    interface View extends BaseContract.BaseView {
        void showUserInterestQuan(List<UserInterestQuanInfo.DataBean.ResultBean> resultBeans);
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getUserInterestData(int mid, int pageNum, int pageSize);
    }
}
