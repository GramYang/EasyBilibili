package com.developer.gram.easybilibili.mvp.contract;


import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.discover.AllareasRankInfo;

import java.util.List;

/**
 * Created by Gram on 2017/12/22.
 */

public interface AllAreasRankContract {
    interface View extends BaseContract.BaseView {
        void showAllAreasRank(List<AllareasRankInfo.RankBean.ListBean> listBeans);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getAllAreasRankData(String type);
    }
}
