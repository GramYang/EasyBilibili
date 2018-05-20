package com.developer.gram.easybilibili.mvp.contract;


import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.discover.TopicCenterInfo;

import java.util.List;

/**
 * Created by Gram on 2017/12/24.
 */

public interface TopicCenterContract {
    interface View extends BaseContract.BaseView {
        void showTopicCenter(List<TopicCenterInfo.ListBean> listBeans);
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getTopicCenterData();
    }
}
