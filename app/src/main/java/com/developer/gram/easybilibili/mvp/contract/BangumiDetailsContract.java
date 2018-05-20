package com.developer.gram.easybilibili.mvp.contract;


import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.bangumi.BangumiDetailsCommentInfo;
import com.developer.gram.easybilibili.bean.bangumi.BangumiDetailsInfo;
import com.developer.gram.easybilibili.bean.bangumi.BangumiDetailsRecommendInfo;

import java.util.List;

/**
 * Created by Gram on 2017/12/22.
 */

public interface BangumiDetailsContract {
    interface View extends BaseContract.BaseView {
        void showBangumiDetails(BangumiDetailsInfo bangumiDetailsInfo);
        void showBangumiDetailsRecommend(List<BangumiDetailsRecommendInfo.ResultBean.ListBean> listBeans);
        void showBangumiDetailsComments(BangumiDetailsCommentInfo bangumiDetailsCommentInfo);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBangumiDetailsData();
    }
}
