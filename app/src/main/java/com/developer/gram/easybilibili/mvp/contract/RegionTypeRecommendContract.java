package com.developer.gram.easybilibili.mvp.contract;

import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.region.RegionRecommendInfo;

/**
 * Created by Gram on 2017/12/24.
 */

public interface RegionTypeRecommendContract {
    interface View extends BaseContract.BaseView {
        void showRegionTypeRecommend(RegionRecommendInfo.DataBean dataBean);
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getRegionTypeRecommendData(int rid);
    }
}
