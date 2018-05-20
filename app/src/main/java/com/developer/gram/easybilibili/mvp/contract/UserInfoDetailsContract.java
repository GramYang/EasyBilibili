package com.developer.gram.easybilibili.mvp.contract;


import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.user.UserCoinsInfo;
import com.developer.gram.easybilibili.bean.user.UserContributeInfo;
import com.developer.gram.easybilibili.bean.user.UserDetailsInfo;
import com.developer.gram.easybilibili.bean.user.UserInterestQuanInfo;
import com.developer.gram.easybilibili.bean.user.UserLiveRoomStatusInfo;
import com.developer.gram.easybilibili.bean.user.UserPlayGameInfo;

/**
 * Created by Gram on 2017/12/25.
 */

public interface UserInfoDetailsContract {
    interface View extends BaseContract.BaseView {
        void showUserInfoDetails(UserDetailsInfo userDetailsInfo);
        void showUserContributeInfo(UserContributeInfo userContributeInfo);
        void showUserInterestQuanInfo(UserInterestQuanInfo userInterestQuanInfo);
        void showUserCoinsInfo(UserCoinsInfo userCoinsInfo);
        void showUserPlayGameInfo(UserPlayGameInfo userPlayGameInfo);
        void showUserLiveRoomStatusInfo(UserLiveRoomStatusInfo userLiveRoomStatusInfo);
        void isDetailsError();
        void isAllInfoError();
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getUserInfoDetailsData(int mid);
        void getAllUserInfoData(int mid);
    }
}
