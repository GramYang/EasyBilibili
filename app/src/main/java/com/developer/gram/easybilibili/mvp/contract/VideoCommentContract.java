package com.developer.gram.easybilibili.mvp.contract;

import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.video.VideoCommentInfo;

/**
 * Created by Gram on 2018/1/9.
 */

public interface VideoCommentContract {
    interface View extends BaseContract.BaseView {
        void showVideoComment(VideoCommentInfo videoCommentInfo);
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getVideoCommentData(int aid, int pageNum, int pageSize, int ver);
    }
}
