package com.developer.gram.easybilibili.network.api;

import com.developer.gram.easybilibili.bean.Splash.Splash;
import com.developer.gram.easybilibili.bean.recommend.RecommendBannerInfo;
import com.developer.gram.easybilibili.bean.recommend.RecommendInfo;
import com.developer.gram.easybilibili.bean.region.RegionDetailsInfo;
import com.developer.gram.easybilibili.bean.region.RegionRecommend;
import com.developer.gram.easybilibili.bean.region.RegionRecommendInfo;
import com.developer.gram.easybilibili.bean.search.SearchArchiveInfo;
import com.developer.gram.easybilibili.bean.search.SearchBangumiInfo;
import com.developer.gram.easybilibili.bean.search.SearchMovieInfo;
import com.developer.gram.easybilibili.bean.search.SearchSpInfo;
import com.developer.gram.easybilibili.bean.search.SearchUpperInfo;
import com.developer.gram.easybilibili.bean.video.VideoDetailsInfo;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hcc on 16/8/4 12:03
 * 100332338@qq.com
 */
public interface BiliAppService {

    /**
     * splash界面
     *
     * @return
     */
    @GET("/x/v2/splash?mobi_app=android&build=505000&channel=360&width=1080&height=1920&ver=4344558841496142006")
    Flowable<Splash> getSplash();

    /**
     * 首页推荐数据
     */
    @GET("x/show/old?platform=android&device=&build=412001")
    Flowable<RecommendInfo> getRecommendedInfo();

    /**
     * 首页推荐banner
     */
    @GET("x/banner?plat=4&build=411007&channel=bilih5")
    Flowable<RecommendBannerInfo> getRecommendedBannerInfo();

    /**
     * 视频详情数据
     */
    @GET("x/view?access_key=19946e1ef3b5cad1a756c475a67185bb&actionKey=appkey&appkey=27eb53fc9058f8c3&build=3940&device=phone&mobi_app=iphone&platform=ios&sign=1206255541e2648c1badb87812458046&ts=1478349831")
    Flowable<VideoDetailsInfo> getVideoDetails(@Query("aid") int aid);

    /**
     * 综合搜索
     */
    @GET("x/v2/search?actionKey=appkey&appkey=27eb53fc9058f8c3&build=3710&device=phone&duration=0&mobi_app=iphone&order=default&platform=ios&rid=0")
    Flowable<SearchArchiveInfo> searchArchive(
            @Query("keyword") String content, @Query("pn") int page, @Query("ps") int pagesize);

    /**
     * 番剧搜索
     */
    @GET("x/v2/search/type?actionKey=appkey&appkey=27eb53fc9058f8c3&build=3710&device=phone&mobi_app=iphone&platform=ios&type=1")
    Flowable<SearchBangumiInfo> searchBangumi(
            @Query("keyword") String content, @Query("pn") int page, @Query("ps") int pagesize);

    /**
     * up主搜索
     */
    @GET("x/v2/search/type?actionKey=appkey&appkey=27eb53fc9058f8c3&build=3710&device=phone&mobi_app=iphone&platform=ios&type=2")
    Flowable<SearchUpperInfo> searchUpper(
            @Query("keyword") String content, @Query("pn") int page, @Query("ps") int pagesize);

    /**
     * 影视搜索
     */
    @GET("x/v2/search/type?actionKey=appkey&appkey=27eb53fc9058f8c3&build=3710&device=phone&mobi_app=iphone&platform=ios&type=3")
    Flowable<SearchMovieInfo> searchMovie(
            @Query("keyword") String content, @Query("pn") int page, @Query("ps") int pagesize);

    /**
     * 专题搜索
     */
    @GET("x/v2/search/type?actionKey=appkey&appkey=27eb53fc9058f8c3&build=3710&device=phone&mobi_app=iphone&platform=ios&type=4")
    Flowable<SearchSpInfo> searchSp(
            @Query("keyword") String content, @Query("pn") int page, @Query("ps") int pagesize);

    /**
     * 分区推荐
     */
    @GET("x/v2/region/show?access_key=67cbf6a1e9ad7d7f11bfbd918e50c837&actionKey=appkey&appkey=27eb53fc9058f8c3&build=3600&device=phone&mobi_app=iphone&plat=1&platform=ios&sign=959d7b8c09c65e7a66f7e58b1a2bdab9&ts=1472310694")
    Flowable<RegionRecommendInfo> getRegionRecommends(@Query("rid") int rid);

    /**
     * 分区类型详情
     */
    @GET("x/v2/region/show/child?build=3600")
    Flowable<RegionDetailsInfo> getRegionDetails(@Query("rid") int rid);

    /**
     * 首页分区推荐（新加入）
     *
     * @return
     */
    @GET("/x/v2/show/index?access_key=fcbe0b2d947971fd3cc2b9e759d63097&appkey=1d8b6e7d45233436&build=505000&mobi_app=android&platform=android&ts=1495780436&sign=93ebfdf6018d866239977af373d45dba")
    Flowable<RegionRecommend> getRegion();
}
