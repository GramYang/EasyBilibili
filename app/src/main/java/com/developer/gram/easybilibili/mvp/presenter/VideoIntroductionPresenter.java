package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.video.VideoDetailsInfo;
import com.developer.gram.easybilibili.mvp.contract.VideoIntroductionContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import javax.inject.Inject;

/**
 * Created by Gram on 2018/1/9.
 */

public class VideoIntroductionPresenter extends RxPresenter<VideoIntroductionContract.View> implements VideoIntroductionContract.Presenter<VideoIntroductionContract.View>{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public VideoIntroductionPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getVideoIntroductionData(int av) {
        addSubscribe(mRetrofitHelper.getBiliAppAPI()
                .getVideoDetails(av)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<VideoDetailsInfo>(mView) {
                    @Override
                    public void onSuccess(VideoDetailsInfo videoDetailsInfo) {
                        mView.showVideoIntroduction(videoDetailsInfo);
                    }
                })
        );
    }
}
