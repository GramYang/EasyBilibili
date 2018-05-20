package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.user.UserDetailsInfo;
import com.developer.gram.easybilibili.bean.user.UserLiveRoomStatusInfo;
import com.developer.gram.easybilibili.mvp.contract.UserInfoDetailsContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.RxUtil;

import javax.inject.Inject;

/**
 * Created by Gram on 2017/12/25.
 */

public class UserInfoDetailsPresenter extends RxPresenter<UserInfoDetailsContract.View> implements UserInfoDetailsContract.Presenter<UserInfoDetailsContract.View>{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public UserInfoDetailsPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getUserInfoDetailsData(int mid) {
        addSubscribe(mRetrofitHelper.getAccountAPI()
                .getUserInfoById(mid)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<UserDetailsInfo>(mView) {
                    @Override
                    public void onSuccess(UserDetailsInfo userDetailsInfo) {
                        mView.showUserInfoDetails(userDetailsInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.isDetailsError();
                        super.onError(e);
                    }
                })
        );
    }

    @Override
    public void getAllUserInfoData(int mid) {
        addSubscribe(mRetrofitHelper.getUserAPI()
                .getUserContributeVideos(mid, 1, 10)
                .flatMap(userContributeInfo -> {
                    mView.showUserContributeInfo(userContributeInfo);
                    return mRetrofitHelper.getIm9API().getUserInterestQuanData(mid, 1, 10);
                })
                .flatMap(userInterestQuanInfo -> {
                    mView.showUserInterestQuanInfo(userInterestQuanInfo);
                    return mRetrofitHelper.getUserAPI().getUserCoinVideos(mid);
                })
                .flatMap(userCoinsInfo -> {
                    mView.showUserCoinsInfo(userCoinsInfo);
                    return mRetrofitHelper.getUserAPI().getUserPlayGames(mid);
                })
                .flatMap(userPlayGameInfo -> {
                    mView.showUserPlayGameInfo(userPlayGameInfo);
                    return mRetrofitHelper.getLiveAPI().getUserLiveRoomStatus(mid);
                })
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<UserLiveRoomStatusInfo>(mView) {
                    @Override
                    public void onSuccess(UserLiveRoomStatusInfo userLiveRoomStatusInfo) {
                        mView.showUserLiveRoomStatusInfo(userLiveRoomStatusInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.isAllInfoError();
                        super.onError(e);
                    }
                })
        );
    }
}
