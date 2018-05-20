package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.video.VideoCommentInfo;
import com.developer.gram.easybilibili.mvp.contract.VideoCommentContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import javax.inject.Inject;

/**
 * Created by Gram on 2018/1/9.
 */

public class VideoCommentPresenter extends RxPresenter<VideoCommentContract.View> implements VideoCommentContract.Presenter<VideoCommentContract.View>{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public VideoCommentPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getVideoCommentData(int aid, int pageNum, int pageSize, int ver) {
        addSubscribe(mRetrofitHelper.getBiliAPI()
                .getVideoComment(aid, pageNum, pageSize, ver)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<VideoCommentInfo>(mView) {
                    @Override
                    public void onSuccess(VideoCommentInfo videoCommentInfo) {
                        mView.showVideoComment(videoCommentInfo);
                    }
                })
        );
    }
}
