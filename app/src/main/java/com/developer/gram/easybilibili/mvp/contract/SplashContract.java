package com.developer.gram.easybilibili.mvp.contract;

import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.Splash.Splash;

/**
 * Created by Gram on 2017/12/29.
 */

public interface SplashContract {
    interface View extends BaseContract.BaseView {
        void showSplash(Splash splash);

        void showCountDown(int count);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void getSplashData();

        void setCountDown();

    }
}
