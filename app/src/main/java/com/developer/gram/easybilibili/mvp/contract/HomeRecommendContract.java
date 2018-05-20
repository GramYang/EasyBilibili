package com.developer.gram.easybilibili.mvp.contract;

import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.recommend.RecommendBannerInfo;
import com.developer.gram.easybilibili.bean.recommend.RecommendInfo;

/**
 * Created by Gram on 2018/1/1.
 */

public interface HomeRecommendContract {
    interface View extends BaseContract.BaseView {
        void showRecommendBanner(RecommendBannerInfo recommendBannerInfo);
        void showRecommendInfo(RecommendInfo recommendInfo);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getRecomendBannerData();
        void getRecommendData();
    }
}
