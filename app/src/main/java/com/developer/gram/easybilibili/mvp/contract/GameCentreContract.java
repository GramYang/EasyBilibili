package com.developer.gram.easybilibili.mvp.contract;


import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.discover.GameCenterInfo;
import com.developer.gram.easybilibili.bean.discover.VipGameInfo;

/**
 * Created by Gram on 2017/12/24.
 */

public interface GameCentreContract {
    interface View extends BaseContract.BaseView {
        void showGameCenterInfo(GameCenterInfo gameCenterInfo);
        void showVipGameInfo(VipGameInfo vipGameInfo);
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getGameCentreData();
    }
}
