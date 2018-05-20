package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.discover.GameCenterInfo;
import com.developer.gram.easybilibili.bean.discover.VipGameInfo;
import com.developer.gram.easybilibili.mvp.contract.GameCentreContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.JsonUtils;
import com.developer.gram.easybilibili.util.RxUtil;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by Gram on 2017/12/24.
 */

public class GameCentrePresenter extends RxPresenter<GameCentreContract.View> implements GameCentreContract.Presenter<GameCentreContract.View> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public GameCentrePresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getGameCentreData() {
        addSubscribe(Flowable.just(JsonUtils.readAssetsJson("gamecenter.json"))
                .delay(2000, TimeUnit.MILLISECONDS)
                .flatMap(s -> {GameCenterInfo gameCenterInfo = new Gson().fromJson(s, GameCenterInfo.class);
                mView.showGameCenterInfo(gameCenterInfo);
                return mRetrofitHelper.getVipAPI().getVipGame();})
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<VipGameInfo>(mView) {
                    @Override
                    public void onSuccess(VipGameInfo vipGameInfo) {
                        mView.showVipGameInfo(vipGameInfo);
                    }
                })
        );
    }
}
