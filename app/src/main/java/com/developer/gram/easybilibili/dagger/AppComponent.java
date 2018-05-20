package com.developer.gram.easybilibili.dagger;

import com.developer.gram.easybilibili.function.entry.GameCentreActivity;
import com.developer.gram.easybilibili.function.home.attention.HomeAttentionFragment;
import com.developer.gram.easybilibili.function.home.bangumi.BangumiDetailsActivity;
import com.developer.gram.easybilibili.function.home.bangumi.BangumiIndexActivity;
import com.developer.gram.easybilibili.function.home.bangumi.BangumiScheduleActivity;
import com.developer.gram.easybilibili.function.home.bangumi.HomeBangumiFragment;
import com.developer.gram.easybilibili.function.home.bangumi.NewBangumiSerialActivity;
import com.developer.gram.easybilibili.function.home.bangumi.SeasonNewBangumiActivity;
import com.developer.gram.easybilibili.function.common.SplashActivity;
import com.developer.gram.easybilibili.function.home.discover.ActivityCenterActivity;
import com.developer.gram.easybilibili.function.home.discover.AllAreasRankFragment;
import com.developer.gram.easybilibili.function.home.discover.HomeDiscoverFragment;
import com.developer.gram.easybilibili.function.home.discover.OriginalRankFragment;
import com.developer.gram.easybilibili.function.home.discover.TopicCenterActivity;
import com.developer.gram.easybilibili.function.home.live.HomeLiveFragment;
import com.developer.gram.easybilibili.function.home.live.LivePlayerActivity;
import com.developer.gram.easybilibili.function.home.recommend.HomeRecommendFragment;
import com.developer.gram.easybilibili.function.home.region.AdvertisingActivity;
import com.developer.gram.easybilibili.function.home.region.HomeRegionFragment;
import com.developer.gram.easybilibili.function.home.region.LiveAppIndexActivity;
import com.developer.gram.easybilibili.function.home.region.RegionTypeDetailsFragment;
import com.developer.gram.easybilibili.function.home.region.RegionTypeRecommendFragment;
import com.developer.gram.easybilibili.function.user.UserContributeFragment;
import com.developer.gram.easybilibili.function.user.UserInfoDetailsActivity;
import com.developer.gram.easybilibili.function.user.UserInterestQuanFragment;
import com.developer.gram.easybilibili.function.video.VideoCommentFragment;
import com.developer.gram.easybilibili.function.video.VideoDetailActivity;
import com.developer.gram.easybilibili.function.video.VideoIntroductionFragment;
import com.developer.gram.easybilibili.function.video.VideoPlayerActivity;

import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by Gram on 2017/12/19.
 */

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {
    void inject(SplashActivity splashActivity);

    void inject(HomeLiveFragment homeLiveFragment);

    void inject(HomeRecommendFragment homeRecommendFragment);

    void inject(HomeBangumiFragment homeBangumiFragment);

    void inject(HomeRegionFragment homeRegionFragment);

    void inject(HomeAttentionFragment homeAttentionFragment);

    void inject(HomeDiscoverFragment homeDiscoverFragment);

    void inject(VideoDetailActivity videoDetailActivity);

    void inject(VideoCommentFragment videoCommentFragment);

    void inject(VideoIntroductionFragment videoIntroductionFragment);

    void inject(VideoPlayerActivity videoPlayerActivity);

    void inject(BangumiScheduleActivity bangumiScheduleActivity);

    void inject(BangumiIndexActivity bangumiIndexActivity);

    void inject(BangumiDetailsActivity bangumiDetailsActivity);

    void inject(NewBangumiSerialActivity newBangumiSerialActivity);

    void inject(SeasonNewBangumiActivity seasonNewBangumiActivity);

    void inject(LivePlayerActivity livePlayerActivity);

    void inject(UserInfoDetailsActivity userInfoDetailsActivity);

    void inject(UserContributeFragment userContributeFragment);

    void inject(UserInterestQuanFragment userInterestQuanFragment);

    void inject(OriginalRankFragment originalRankFragment);

    void inject(LiveAppIndexActivity liveAppIndexActivity);

    void inject(RegionTypeDetailsFragment regionTypeDetailsFragment);

    void inject(RegionTypeRecommendFragment regionTypeRecommendFragment);

    void inject(AllAreasRankFragment allAreasRankFragment);

    void inject(AdvertisingActivity advertisingActivity);

    void inject(GameCentreActivity gameCentreActivity);

    void inject(TopicCenterActivity topicCenterActivity);

    void inject(ActivityCenterActivity activityCenterActivity);
}