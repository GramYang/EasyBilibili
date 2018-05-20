package com.developer.gram.easybilibili.network.api;

import com.developer.gram.easybilibili.bean.user.UserCoinsInfo;
import com.developer.gram.easybilibili.bean.user.UserContributeInfo;
import com.developer.gram.easybilibili.bean.user.UserPlayGameInfo;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hcc on 2016/10/12 22:40
 * 100332338@qq.com
 * <p>
 * 用户相关api
 */

public interface UserService {

    /**
     * 用户所玩游戏
     */
    @GET("ajax/game/GetLastPlay")
    Flowable<UserPlayGameInfo> getUserPlayGames(@Query("mid") int mid);

    /**
     * 用户投币视频
     */
    @GET("ajax/member/getCoinVideos")
    Flowable<UserCoinsInfo> getUserCoinVideos(@Query("mid") int mid);


    /**
     * 用户投稿视频
     */
    @GET("ajax/member/getSubmitVideos")
    Flowable<UserContributeInfo> getUserContributeVideos(
            @Query("mid") int mid, @Query("page") int page, @Query("pagesize") int pageSize);
}
