package com.developer.gram.easybilibili.dagger;

import com.developer.gram.easybilibili.dagger.qualifier.Account;
import com.developer.gram.easybilibili.dagger.qualifier.Bangumi;
import com.developer.gram.easybilibili.dagger.qualifier.BiliApi;
import com.developer.gram.easybilibili.dagger.qualifier.BiliApp;
import com.developer.gram.easybilibili.dagger.qualifier.BiliGo;
import com.developer.gram.easybilibili.dagger.qualifier.Im9;
import com.developer.gram.easybilibili.dagger.qualifier.Live;
import com.developer.gram.easybilibili.dagger.qualifier.Rank;
import com.developer.gram.easybilibili.dagger.qualifier.Search;
import com.developer.gram.easybilibili.dagger.qualifier.User;
import com.developer.gram.easybilibili.dagger.qualifier.Vip;
import com.developer.gram.easybilibili.network.api.AccountService;
import com.developer.gram.easybilibili.network.api.BangumiService;
import com.developer.gram.easybilibili.network.api.BiliApiService;
import com.developer.gram.easybilibili.network.api.BiliAppService;
import com.developer.gram.easybilibili.network.api.BiliGoService;
import com.developer.gram.easybilibili.network.api.Im9Service;
import com.developer.gram.easybilibili.network.api.LiveService;
import com.developer.gram.easybilibili.network.api.RankService;
import com.developer.gram.easybilibili.network.api.SearchService;
import com.developer.gram.easybilibili.network.api.UserService;
import com.developer.gram.easybilibili.network.api.VipService;
import com.developer.gram.easybilibili.network.helper.ApiConstants;
import com.developer.gram.easybilibili.network.helper.OkHttpHelper;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gram on 2017/12/19.
 * 提供okhttp的实例，根据不同的baseurl生成不同的retrofit的实例，以及包含这些retrofit实例的retrofithelper的实例
 */
@Module
public class ApiModule {
    public Retrofit createRetrofit(OkHttpClient client, String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient() {
        return OkHttpHelper.getInstance().getOkHttpClient();
    }

    @Singleton
    @Provides
    public RetrofitHelper provideRetrofitHelper(LiveService liveService, BiliAppService biliAppService, BiliApiService biliApiService, BiliGoService biliGoService,
                                                RankService rankService, UserService userService, VipService vipService, BangumiService bangumiService,
                                                SearchService searchService, AccountService accountService, Im9Service im9Service) {
        return new RetrofitHelper(liveService, biliAppService, biliApiService, biliGoService, rankService, userService, vipService, bangumiService, searchService,
                accountService, im9Service);
    }

    @Singleton
    @Provides
    @BiliGo
    public Retrofit provideBiliGoRetrofit(OkHttpClient client) {
        return createRetrofit(client, ApiConstants.BILI_GO_BASE_URL);
    }

    @Singleton
    @Provides
    public BiliGoService provideBiliGoService(@BiliGo Retrofit retrofit) {
        return retrofit.create(BiliGoService.class);
    }

    @Singleton
    @Provides
    @Rank
    public Retrofit provideRankRetrofit(OkHttpClient client) {
        return createRetrofit(client, ApiConstants.RANK_BASE_URL);
    }

    @Singleton
    @Provides
    public RankService provideRankService(@Rank Retrofit retrofit) {
        return retrofit.create(RankService.class);
    }

    @Singleton
    @Provides
    @BiliApp
    public Retrofit provideBiliAppRetrofit(OkHttpClient client) {
        return createRetrofit(client, ApiConstants.APP_BASE_URL);
    }

    @Singleton
    @Provides
    public BiliAppService provideBiliAppService(@BiliApp Retrofit retrofit) {
        return retrofit.create(BiliAppService.class);
    }

    @Singleton
    @Provides
    @Live
    public Retrofit provideLiveRetrofit(OkHttpClient client) {
        return createRetrofit(client, ApiConstants.LIVE_BASE_URL);
    }

    @Singleton
    @Provides
    public LiveService provideLiveService(@Live Retrofit retrofit) {
        return retrofit.create(LiveService.class);
    }

    @Singleton
    @Provides
    @BiliApi
    public Retrofit provideBiliApiRetrofit(OkHttpClient client) {
        return createRetrofit(client, ApiConstants.API_BASE_URL);
    }

    @Singleton
    @Provides
    public BiliApiService provideBiliApiService(@BiliApi Retrofit retrofit) {
        return retrofit.create(BiliApiService.class);
    }

    @Singleton
    @Provides
    @Bangumi
    public Retrofit provideBangumiRetrofit(OkHttpClient client) {
        return createRetrofit(client, ApiConstants.BANGUMI_BASE_URL);
    }

    @Singleton
    @Provides
    public BangumiService provideBangumiService(@Bangumi Retrofit retrofit) {
        return retrofit.create(BangumiService.class);
    }

    @Singleton
    @Provides
    @Search
    public Retrofit provideSearchRetrofit(OkHttpClient client) {
        return createRetrofit(client, ApiConstants.SEARCH_BASE_URL);
    }

    @Singleton
    @Provides
    public SearchService provideSearchService(@Search Retrofit retrofit) {
        return retrofit.create(SearchService.class);
    }

    @Singleton
    @Provides
    @Account
    public Retrofit provideAccountRetrofit(OkHttpClient client) {
        return createRetrofit(client, ApiConstants.ACCOUNT_BASE_URL);
    }

    @Singleton
    @Provides
    public AccountService provideAccountService(@Account Retrofit retrofit) {
        return retrofit.create(AccountService.class);
    }

    @Singleton
    @Provides
    @User
    public Retrofit provideUserRetrofit(OkHttpClient client) {
        return createRetrofit(client, ApiConstants.USER_BASE_URL);
    }

    @Singleton
    @Provides
    public UserService provideUserService(@User Retrofit retrofit) {
        return retrofit.create(UserService.class);
    }

    @Singleton
    @Provides
    @Vip
    public Retrofit provideVipRetrofit(OkHttpClient client) {
        return createRetrofit(client, ApiConstants.VIP_BASE_URL);
    }

    @Singleton
    @Provides
    public VipService provideVipService(@Vip Retrofit retrofit) {
        return retrofit.create(VipService.class);
    }

    @Singleton
    @Provides
    @Im9
    public Retrofit provideIm9Retrofit(OkHttpClient client) {
        return createRetrofit(client, ApiConstants.IM9_BASE_URL);
    }

    @Singleton
    @Provides
    public Im9Service provideIm9Service(@Im9 Retrofit retrofit) {
        return retrofit.create(Im9Service.class);
    }



}
