package com.developer.gram.easybilibili.mvp.contract;

import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.bangumi.BangumiScheduleInfo;

/**
 * Created by Gram on 2017/12/22.
 */

public interface BangumiScheduleContract {
    interface View extends BaseContract.BaseView {
        void showBangumiSchedule(BangumiScheduleInfo bangumiScheduleInfo);
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBangumiScheduleData();
    }
}
