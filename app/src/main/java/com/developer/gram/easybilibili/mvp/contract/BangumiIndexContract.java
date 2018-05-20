package com.developer.gram.easybilibili.mvp.contract;

import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.bangumi.BangumiIndexInfo;

/**
 * Created by Gram on 2017/12/22.
 */

public interface BangumiIndexContract {
    interface View extends BaseContract.BaseView {
        void showBangumiIndex(BangumiIndexInfo bangumiIndexInfo);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBangumiIndexData();
    }
}
