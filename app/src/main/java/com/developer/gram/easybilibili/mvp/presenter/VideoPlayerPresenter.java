package com.developer.gram.easybilibili.mvp.presenter;

import android.net.Uri;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.media.danmuku.BiliDanmukuDownloadUtil;
import com.developer.gram.easybilibili.mvp.contract.VideoPlayerContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.ConstantUtil;
import com.developer.gram.easybilibili.util.RxUtil;

import javax.inject.Inject;

import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;

/**
 * Created by Gram on 2018/1/9.
 */

public class VideoPlayerPresenter extends RxPresenter<VideoPlayerContract.View> implements VideoPlayerContract.Presenter<VideoPlayerContract.View>{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public VideoPlayerPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getVideoPlayerData(int cid) {
        addSubscribe(mRetrofitHelper.getBiliGoAPI()
                .getHDVideoUrl(cid, 4, ConstantUtil.VIDEO_TYPE_MP4)
                .map(videoInfo -> Uri.parse(videoInfo.getDurl().get(0).getUrl()))
                .flatMap(uri -> {
                    mView.showVideoPlayer(uri);
                    String url = "http://comment.bilibili.com/" + cid + ".xml";
                    return BiliDanmukuDownloadUtil.downloadXML(url);
                })
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<BaseDanmakuParser>(mView) {
                    @Override
                    public void onSuccess(BaseDanmakuParser baseDanmakuParser) {
                        mView.showDanmaku(baseDanmakuParser);
                    }
                })
        );
    }
}
