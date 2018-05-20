package com.developer.gram.easybilibili.mvp.contract;

import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.live.LiveAppIndexInfo;

/**
 * Created by Gram on 2017/12/24.
 */

public interface LiveAppIndexContract {
    interface View extends BaseContract.BaseView {
        void showLiveAppIndex(LiveAppIndexInfo liveAppIndexInfo);
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getshowLiveAppData();
    }
}
