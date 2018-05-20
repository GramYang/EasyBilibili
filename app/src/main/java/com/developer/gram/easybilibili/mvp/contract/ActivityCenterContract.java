package com.developer.gram.easybilibili.mvp.contract;


import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.discover.ActivityCenterInfo;

import java.util.List;

/**
 * Created by Gram on 2017/12/21.
 */

public interface ActivityCenterContract {
    interface View extends BaseContract.BaseView {
        void showActivityCenter(List<ActivityCenterInfo.ListBean> listBeanList);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getActivityCenterData(int page, int pageSize);
    }
}
