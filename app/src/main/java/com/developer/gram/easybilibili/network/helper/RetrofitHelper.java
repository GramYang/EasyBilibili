package com.developer.gram.easybilibili.network.helper;

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

/**
 * Created by Gram on 2017/12/19.
 * 包裹了11个retrofit实例
 */

public class RetrofitHelper {
    private final LiveService mLiveService;
    private final BiliAppService mBiliAppService;
    private final BiliApiService mBiliApiService;
    private final BiliGoService mBiliGoService;
    private final RankService mRankService;
    private final UserService mUserService;
    private final VipService mVipService;
    private final BangumiService mBangumiService;
    private final SearchService mSearchService;
    private final AccountService mAccountService;
    private final Im9Service mIm9Service;

    public RetrofitHelper(LiveService liveService, BiliAppService biliAppService, BiliApiService biliApiService, BiliGoService biliGoService,
                          RankService rankService, UserService userService, VipService vipService, BangumiService bangumiService,
                          SearchService searchService, AccountService accountService, Im9Service im9Service) {
        this.mLiveService = liveService;
        this.mBiliAppService = biliAppService;
        this.mBiliApiService = biliApiService;
        this.mBiliGoService = biliGoService;
        this.mRankService = rankService;
        this.mUserService = userService;
        this.mVipService = vipService;
        this.mBangumiService = bangumiService;
        this.mSearchService = searchService;
        this.mAccountService = accountService;
        this.mIm9Service = im9Service;
    }

    public LiveService getLiveAPI() {
        return mLiveService;
    }

    public BiliAppService getBiliAppAPI() {
        return mBiliAppService;
    }

    public BiliApiService getBiliAPI() {
        return mBiliApiService;
    }

    public BiliGoService getBiliGoAPI() {
        return mBiliGoService;
    }

    public RankService getRankAPI() {
        return mRankService;
    }

    public UserService getUserAPI() {
        return mUserService;
    }

    public VipService getVipAPI() {
        return mVipService;
    }

    public BangumiService getBangumiAPI() {
        return mBangumiService;
    }

    public SearchService getSearchAPI() {
        return mSearchService;
    }

    public AccountService getAccountAPI() {
        return mAccountService;
    }

    public Im9Service getIm9API() {
        return mIm9Service;
    }

}
