package com.developer.gram.easybilibili.mvp.contract;


import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.discover.OriginalRankInfo;

import java.util.List;

/**
 * Created by Gram on 2017/12/24.
 */

public interface OriginalRankContract {
    interface View extends BaseContract.BaseView {
        void showOriginalRank(List<OriginalRankInfo.RankBean.ListBean> listBeans);
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getOriginalRankData(String order);
    }
}
