package com.developer.gram.easybilibili.mvp.contract;


import com.developer.gram.easybilibili.base.BaseContract;

/**
 * Created by Gram on 2017/12/24.
 */

public interface LivePlayerContract {
    interface View extends BaseContract.BaseView {
        void playLive(String url);
        void refreshUI();
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getLivePlayerData(int cid);
    }
}
