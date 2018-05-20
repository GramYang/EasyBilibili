package com.developer.gram.easybilibili.mvp.contract;

import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.region.RegionRecommend;
import com.developer.gram.easybilibili.bean.region.RegionTypesInfo;

import java.util.List;

/**
 * Created by Gram on 2018/1/2.
 */

public interface HomeRegionContract {
    interface View extends BaseContract.BaseView {
        void showRegion(List<RegionTypesInfo.DataBean> dataBeans);
        void showRegionRecommend(RegionRecommend regionRecommend);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getRegionTypeData();
        void getRegionData();
    }
}
