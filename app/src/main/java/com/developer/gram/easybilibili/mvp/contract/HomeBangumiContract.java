package com.developer.gram.easybilibili.mvp.contract;

import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.bangumi.BangumiAppIndexInfo;
import com.developer.gram.easybilibili.bean.bangumi.BangumiRecommendInfo;

/**
 * Created by Gram on 2018/1/2.
 */

public interface HomeBangumiContract {
    interface View extends BaseContract.BaseView {
        void showBangumiIndex(BangumiAppIndexInfo bangumiAppIndexInfo);

        void showBangumiRecommend(BangumiRecommendInfo bangumiRecommendInfo);

    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBangumiIndexData();
        void getBangumiRecommendData();
    }
}
