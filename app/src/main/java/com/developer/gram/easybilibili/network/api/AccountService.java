package com.developer.gram.easybilibili.network.api;

import com.developer.gram.easybilibili.bean.user.UserDetailsInfo;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hcc on 16/8/8 20:48
 * 100332338@qq.com
 * <p>
 * 用户个人账号相关api
 */
public interface AccountService {

    /**
     * 用户详情数据
     */
    @GET("api/member/getCardByMid")
    Flowable<UserDetailsInfo> getUserInfoById(@Query("mid") int mid);
}
