package com.developer.gram.easybilibili.mvp.contract;

import android.net.Uri;

import com.developer.gram.easybilibili.base.BaseContract;

import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;

/**
 * Created by Gram on 2018/1/9.
 */

public interface VideoPlayerContract {
    interface View extends BaseContract.BaseView{
        void showVideoPlayer(Uri uri);
        void showDanmaku(BaseDanmakuParser baseDanmakuParser);
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getVideoPlayerData(int cid);
    }
}
