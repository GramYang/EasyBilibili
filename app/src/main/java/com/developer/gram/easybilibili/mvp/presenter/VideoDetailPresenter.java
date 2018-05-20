package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.video.VideoDetailsInfo;
import com.developer.gram.easybilibili.mvp.contract.VideoDetailContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import javax.inject.Inject;

/**
 * Created by Gram on 2018/1/5.
 */

public class VideoDetailPresenter extends RxPresenter<VideoDetailContract.View> implements VideoDetailContract.Presenter<VideoDetailContract.View>{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public VideoDetailPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getVideoDetailsData(int av) {
        addSubscribe(mRetrofitHelper.getBiliAppAPI()
                .getVideoDetails(av)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<VideoDetailsInfo>(mView) {
                    @Override
                    public void onSuccess(VideoDetailsInfo videoDetailsInfo) {
                        mView.showVideoDetails(videoDetailsInfo);
                    }
                })
        );
    }
}
