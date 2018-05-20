package com.developer.gram.easybilibili.function.user;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.base.BaseActivity;
import com.developer.gram.easybilibili.bean.user.UserCoinsInfo;
import com.developer.gram.easybilibili.bean.user.UserContributeInfo;
import com.developer.gram.easybilibili.bean.user.UserDetailsInfo;
import com.developer.gram.easybilibili.bean.user.UserInterestQuanInfo;
import com.developer.gram.easybilibili.bean.user.UserLiveRoomStatusInfo;
import com.developer.gram.easybilibili.bean.user.UserPlayGameInfo;
import com.developer.gram.easybilibili.event.AppBarStateChangeEvent;
import com.developer.gram.easybilibili.mvp.contract.UserInfoDetailsContract;
import com.developer.gram.easybilibili.mvp.presenter.UserInfoDetailsPresenter;
import com.developer.gram.easybilibili.util.NumberUtils;
import com.developer.gram.easybilibili.util.SystemBarHelper;
import com.developer.gram.easybilibili.util.ToastUtils;
import com.developer.gram.easybilibili.widget.CircleImageView;
import com.developer.gram.easybilibili.widget.CircleProgressView;
import com.developer.gram.easybilibili.widget.NoScrollViewPager;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Gram on 2017/12/24.
 */

public class UserInfoDetailsActivity extends BaseActivity<UserInfoDetailsPresenter> implements UserInfoDetailsContract.View{
    @BindView(R.id.user_avatar_view)
    CircleImageView mAvatarImage;
    @BindView(R.id.user_name)
    TextView mUserNameText;
    @BindView(R.id.user_desc)
    TextView mDescriptionText;
    @BindView(R.id.tv_follow_users)
    TextView mFollowNumText;
    @BindView(R.id.tv_fans)
    TextView mFansNumText;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.user_lv)
    ImageView mUserLv;
    @BindView(R.id.user_sex)
    ImageView mUserSex;
    @BindView(R.id.author_verified_layout)
    LinearLayout mAuthorVerifiedLayout;
    @BindView(R.id.author_verified_text)
    TextView mAuthorVerifiedText;
    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.line)
    View mLineView;
    @BindView(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    private int mid;
    private String name = "";
    private String avatar_url;
    private int userContributeCount;
    private int userInterestQuanCount;
    private int userCoinsCount;
    private int userPlayGameCount;
    private UserDetailsInfo mUserDetailsInfo;
    private UserCoinsInfo mUserCoinsInfo;
    private UserPlayGameInfo mUserPlayGameInfo;
    private UserContributeInfo mUserContributeInfo;
    private UserInterestQuanInfo mUserInterestQuanInfo;
    private UserLiveRoomStatusInfo mUserLiveRoomStatusInfo;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private List<UserCoinsInfo.DataBean.ListBean> userCoins = new ArrayList<>();
    private List<UserPlayGameInfo.DataBean.GamesBean> userPlayGames = new ArrayList<>();
    private List<UserContributeInfo.DataBean.VlistBean> userContributes = new ArrayList<>();
    private List<UserInterestQuanInfo.DataBean.ResultBean> userInterestQuans = new ArrayList<>();
    private static final String EXTRA_USER_NAME = "extra_user_name", EXTRA_MID = "extra_mid", EXTRA_AVATAR_URL = "extra_avatar_url";

    private boolean isDetailsError = false;
    private boolean isAllInfoError = false;


    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    protected void initVariables() {
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra(EXTRA_USER_NAME);
            mid = intent.getIntExtra(EXTRA_MID, -1);
            avatar_url = intent.getStringExtra(EXTRA_AVATAR_URL);
        }
    }

    @Override
    protected void loadData() {
        showProgressBar();
        mPresenter.getUserInfoDetailsData(mid);
    }

    @Override
    public void showUserInfoDetails(UserDetailsInfo userDetailsInfo) {
        mUserDetailsInfo = userDetailsInfo;
        finishTask();
    }

    @Override
    public void showUserContributeInfo(UserContributeInfo userContributeInfo) {
        mUserContributeInfo = userContributeInfo;
        userContributeCount = userContributeInfo.getData().getCount();
        userContributes.addAll(userContributeInfo.getData().getVlist());
    }

    @Override
    public void showUserInterestQuanInfo(UserInterestQuanInfo userInterestQuanInfo) {
        mUserInterestQuanInfo = userInterestQuanInfo;
        userInterestQuanCount = userInterestQuanInfo.getData().getTotal_count();
        userInterestQuans.addAll(userInterestQuanInfo.getData().getResult());
    }

    @Override
    public void showUserCoinsInfo(UserCoinsInfo userCoinsInfo) {
        mUserCoinsInfo = userCoinsInfo;
        userCoinsCount = userCoinsInfo.getData().getCount();
        userCoins.addAll(userCoinsInfo.getData().getList());
    }

    @Override
    public void showUserPlayGameInfo(UserPlayGameInfo userPlayGameInfo) {
        mUserPlayGameInfo = userPlayGameInfo;
        userPlayGameCount = userPlayGameInfo.getData().getCount();
        userPlayGames.addAll(userPlayGameInfo.getData().getGames());
    }

    @Override
    public void showUserLiveRoomStatusInfo(UserLiveRoomStatusInfo userLiveRoomStatusInfo) {
        mUserLiveRoomStatusInfo = userLiveRoomStatusInfo;
        initViewPager();
    }

    @Override
    public void isDetailsError() {
        isDetailsError = true;
    }

    @Override
    public void isAllInfoError() {
        isAllInfoError = true;
    }

    @Override
    protected void initViews() {
        if (name != null) {
            mUserNameText.setText(name);
        }
        if (avatar_url != null) {
            Glide.with(UserInfoDetailsActivity.this)
                    .load(avatar_url)
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.ico_user_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mAvatarImage);
        }
        //隐藏ViewPager
        invisible(mViewPager);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.setTitle("");
        //设置StatusBar透明
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, mToolbar);
        //设置AppBar展开折叠状态监听
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeEvent() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int verticalOffset) {
                if (state == State.EXPANDED) {
                    //展开状态
                    mCollapsingToolbarLayout.setTitle("");
                    visible(mLineView);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    mCollapsingToolbarLayout.setTitle(name);
                    gone(mLineView);
                } else {
                    mCollapsingToolbarLayout.setTitle("");
                    visible(mLineView);
                }
            }
        });
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        if(isDetailsError) {
            hideProgressBar();
        }
        if(isAllInfoError) {
            ToastUtils.showSingleToast("用户隐私未公开");
            hideProgressBar();
        }
    }

    public void finishTask() {
        //设置用户头像
        Glide.with(UserInfoDetailsActivity.this)
                .load(mUserDetailsInfo.getCard().getFace())
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.ico_user_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mAvatarImage);
        //设置粉丝和关注
        mUserNameText.setText(mUserDetailsInfo.getCard().getName());
        mFollowNumText.setText(String.valueOf(mUserDetailsInfo.getCard().getAttention()));
        mFansNumText.setText(NumberUtils.format(mUserDetailsInfo.getCard().getFans()));
        //设置用户等级
        setUserLevel(Integer.valueOf(mUserDetailsInfo.getCard().getRank()));
        //设置用户性别
        switch (mUserDetailsInfo.getCard().getSex()) {
            case "男":
                mUserSex.setImageResource(R.drawable.ic_user_male);
                break;
            case "女":
                mUserSex.setImageResource(R.drawable.ic_user_female);
                break;
            default:
                mUserSex.setImageResource(R.drawable.ic_user_gay_border);
                break;
        }
        //设置用户签名信息
        if (!TextUtils.isEmpty(mUserDetailsInfo.getCard().getSign())) {
            mDescriptionText.setText(mUserDetailsInfo.getCard().getSign());
        } else {
            mDescriptionText.setText("这个人懒死了,什么都没有写(・－・。)");
        }
        //设置认证用户信息
        if (mUserDetailsInfo.getCard().isApprove()) {
            //认证用户 显示认证资料
            visible(mAuthorVerifiedLayout);
            if (!TextUtils.isEmpty(mUserDetailsInfo.getCard().getDescription())) {
                mAuthorVerifiedText.setText(mUserDetailsInfo.getCard().getDescription());
            } else {
                mAuthorVerifiedText.setText("这个人懒死了,什么都没有写(・－・。)");
            }
        } else {
            //普通用户
            gone(mAuthorVerifiedLayout);
        }
        //获取用户详情全部数据
        mPresenter.getAllUserInfoData(mid);
    }

    private void initViewPager() {
        fragments.add(UserHomePageFragment.newInstance(mUserContributeInfo,
                mUserInterestQuanInfo, mUserCoinsInfo, mUserPlayGameInfo, mUserLiveRoomStatusInfo));
        fragments.add(UserContributeFragment.newInstance(mid, mUserContributeInfo));
        fragments.add(UserInterestQuanFragment.newInstance(mid, mUserInterestQuanInfo));
        fragments.add(UserCoinsVideoFragment.newInstance(mUserCoinsInfo));
        fragments.add(UserPlayGameFragment.newInstance(mUserPlayGameInfo));
        UserInfoDetailsPagerAdapter mAdapter = new UserInfoDetailsPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                measureTabLayoutTextWidth(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> setPagerTitles());
    }


    private void setPagerTitles() {
        titles.add("主页");
        titles.add("投稿 " + userContributeCount);
        titles.add("兴趣圈 " + userInterestQuanCount);
        titles.add("投币 " + userCoinsCount);
        titles.add("游戏 " + userPlayGameCount);
        mSlidingTabLayout.setViewPager(mViewPager, titles.toArray(new String[titles.size()]));
        measureTabLayoutTextWidth(1);
        mViewPager.setCurrentItem(1);
        hideProgressBar();
        visible(mViewPager);
    }


    private void measureTabLayoutTextWidth(int position) {
        String title = titles.get(position);
        TextView titleView = mSlidingTabLayout.getTitleView(position);
        TextPaint paint = titleView.getPaint();
        float textWidth = paint.measureText(title);
        mSlidingTabLayout.setIndicatorWidth(textWidth / 3);
    }


    private void setUserLevel(int rank) {
        if (rank == 0) {
            mUserLv.setImageResource(R.drawable.ic_lv0);
        } else if (rank == 1) {
            mUserLv.setImageResource(R.drawable.ic_lv1);
        } else if (rank == 200) {
            mUserLv.setImageResource(R.drawable.ic_lv2);
        } else if (rank == 1500) {
            mUserLv.setImageResource(R.drawable.ic_lv3);
        } else if (rank == 3000) {
            mUserLv.setImageResource(R.drawable.ic_lv4);
        } else if (rank == 7000) {
            mUserLv.setImageResource(R.drawable.ic_lv5);
        } else if (rank == 10000) {
            mUserLv.setImageResource(R.drawable.ic_lv6);
        }
    }


    private void showProgressBar() {
        visible(mCircleProgressView);
        mCircleProgressView.spin();
    }


    private void hideProgressBar() {
        gone(mCircleProgressView);
        mCircleProgressView.stopSpinning();
    }


    public static void launch(Activity activity, String name, int mid, String avatar_url) {
        Intent intent = new Intent(activity, UserInfoDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_USER_NAME, name);
        intent.putExtra(EXTRA_MID, mid);
        intent.putExtra(EXTRA_AVATAR_URL, avatar_url);
        activity.startActivity(intent);
    }


    /**
     * 根据下标切换页面
     */
    public void switchPager(int index) {
        switch (index) {
            case 1:
                mViewPager.setCurrentItem(1);
                break;
            case 2:
                mViewPager.setCurrentItem(2);
                break;
            case 3:
                mViewPager.setCurrentItem(3);
                break;
            case 4:
                mViewPager.setCurrentItem(4);
                break;
        }
    }


    private static class UserInfoDetailsPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> fragments;

        UserInfoDetailsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
