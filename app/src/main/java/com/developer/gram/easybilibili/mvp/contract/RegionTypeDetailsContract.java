package com.developer.gram.easybilibili.mvp.contract;

import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.region.RegionDetailsInfo;

/**
 * Created by Gram on 2017/12/24.
 */

public interface RegionTypeDetailsContract {
    interface View extends BaseContract.BaseView {
        void showRegionTypeDetails(RegionDetailsInfo regionDetailsInfo);
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getRegionTypeDetailsData(int rid);
    }
}
