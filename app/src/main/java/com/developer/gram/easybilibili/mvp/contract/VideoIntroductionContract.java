package com.developer.gram.easybilibili.mvp.contract;

import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.video.VideoDetailsInfo;

/**
 * Created by Gram on 2018/1/9.
 */

public interface VideoIntroductionContract {
    interface View extends BaseContract.BaseView{
        void showVideoIntroduction(VideoDetailsInfo videoDetailsInfo);
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getVideoIntroductionData(int av);
    }
}
