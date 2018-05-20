package com.developer.gram.easybilibili.mvp.contract;

import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.bangumi.SeasonNewBangumiInfo;

/**
 * Created by Gram on 2017/12/24.
 */

public interface SeasonNewBangumiContract {
    interface View extends BaseContract.BaseView {
        void showSeasonNewBangumi(SeasonNewBangumiInfo seasonNewBangumiInfo);
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getSeasonNewBangumiData();
    }
}
