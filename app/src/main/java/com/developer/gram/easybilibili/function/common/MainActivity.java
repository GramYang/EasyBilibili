package com.developer.gram.easybilibili.function.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatDelegate;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.base.BaseActivity;
import com.developer.gram.easybilibili.function.entry.AttentionPeopleFragment;
import com.developer.gram.easybilibili.function.entry.ConsumeHistoryFragment;
import com.developer.gram.easybilibili.function.entry.HistoryFragment;
import com.developer.gram.easybilibili.function.entry.IFavoritesFragment;
import com.developer.gram.easybilibili.function.entry.OffLineDownloadActivity;
import com.developer.gram.easybilibili.function.entry.SettingFragment;
import com.developer.gram.easybilibili.function.entry.VipActivity;
import com.developer.gram.easybilibili.function.home.HomePagerFragment;
import com.developer.gram.easybilibili.util.ConstantUtil;
import com.developer.gram.easybilibili.util.PrefsUtils;
import com.developer.gram.easybilibili.util.ToastUtils;
import com.developer.gram.easybilibili.widget.CircleImageView;

import butterknife.BindView;

/**
 * Created by Gram on 2017/12/29.
 */

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    private Fragment[] fragments;
    private int currentTabIndex;
    private int index;
    private long exitTime;
    private HomePagerFragment mHomePagerFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void initViews() {
        //初始化Fragment
        initFragments();
        //初始化侧滑菜单
        initNavigationView();
    }


    /**
     * 初始化Fragments
     */
    private void initFragments() {
        mHomePagerFragment = HomePagerFragment.newInstance();
        IFavoritesFragment mFavoritesFragment = IFavoritesFragment.newInstance();
        HistoryFragment mHistoryFragment = HistoryFragment.newInstance();
        AttentionPeopleFragment mAttentionPeopleFragment = AttentionPeopleFragment.newInstance();
        ConsumeHistoryFragment mConsumeHistoryFragment = ConsumeHistoryFragment.newInstance();
        SettingFragment mSettingFragment = SettingFragment.newInstance();
        fragments = new Fragment[]{
                mHomePagerFragment,
                mFavoritesFragment,
                mHistoryFragment,
                mAttentionPeopleFragment,
                mConsumeHistoryFragment,
                mSettingFragment
        };
        // 添加显示第一个fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, mHomePagerFragment)
                .show(mHomePagerFragment).commit();
    }


    /**
     * 初始化NavigationView
     */
    private void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(this);
        View headerView = mNavigationView.getHeaderView(0);
        CircleImageView mUserAvatarView = (CircleImageView) headerView.findViewById(R.id.user_avatar_view);
        TextView mUserName = (TextView) headerView.findViewById(R.id.user_name);
        TextView mUserSign = (TextView) headerView.findViewById(R.id.user_other_info);
        ImageView mSwitchMode = (ImageView) headerView.findViewById(R.id.iv_head_switch_mode);
        //设置头像
        mUserAvatarView.setImageResource(R.drawable.gramyang);
        //设置用户名 签名
        mUserName.setText(getResources().getText(R.string.gramyang));
        mUserSign.setText(getResources().getText(R.string.about_user_head_layout));
        //设置日夜间模式切换
        mSwitchMode.setOnClickListener(v -> switchNightMode());
        boolean flag = PrefsUtils.getInstance().getBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
        if (flag) {
            mSwitchMode.setImageResource(R.drawable.ic_switch_daily);
        } else {
            mSwitchMode.setImageResource(R.drawable.ic_switch_night);
        }
    }


    /**
     * 日夜间模式切换
     */
    private void switchNightMode() {
        boolean isNight = PrefsUtils.getInstance().getBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
        if (isNight) {
            // 日间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            PrefsUtils.getInstance().putBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
        } else {
            // 夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            PrefsUtils.getInstance().putBoolean(ConstantUtil.SWITCH_MODE_KEY, true);
        }
        recreate();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.item_home:
                // 主页
                changeFragmentIndex(item, 0);
                return true;
            case R.id.item_download:
                // 离线缓存
                startActivity(new Intent(MainActivity.this, OffLineDownloadActivity.class));
                return true;
            case R.id.item_vip:
                //大会员
                startActivity(new Intent(MainActivity.this, VipActivity.class));
                return true;
            case R.id.item_favourite:
                // 我的收藏
                changeFragmentIndex(item, 1);
                return true;
            case R.id.item_history:
                // 历史记录
                changeFragmentIndex(item, 2);
                return true;
            case R.id.item_group:
                // 关注的人
                changeFragmentIndex(item, 3);
                return true;
            case R.id.item_tracker:
                // 我的钱包
                changeFragmentIndex(item, 4);
                return true;
            case R.id.item_theme:
                // 主题选择
                return true;
            case R.id.item_app:
                // 应用推荐
                return true;
            case R.id.item_settings:
                // 设置中心
                changeFragmentIndex(item, 5);
                return true;
        }
        return false;
    }


    /**
     * Fragment切换
     */
    private void switchFragment() {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.hide(fragments[currentTabIndex]);
        if (!fragments[index].isAdded()) {
            trx.add(R.id.container, fragments[index]);
        }
        trx.show(fragments[index]).commit();
        currentTabIndex = index;
    }


    /**
     * 切换Fragment的下标
     */
    private void changeFragmentIndex(MenuItem item, int currentIndex) {
        index = currentIndex;
        switchFragment();
        item.setChecked(true);
    }


    /**
     * DrawerLayout侧滑菜单开关
     */
    public void toggleDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }


    /**
     * 监听back键处理DrawerLayout和SearchView
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(mDrawerLayout.getChildAt(1))) {
                mDrawerLayout.closeDrawers();
            } else {
                if (mHomePagerFragment != null) {
                    if (mHomePagerFragment.isOpenSearchView()) {
                        mHomePagerFragment.closeSearchView();
                    } else {
                        exitApp();
                    }
                } else {
                    exitApp();
                }
            }
        }
        return true;
    }


    /**
     * 双击退出App
     */
    private void exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtils.showSingleLongToast("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            PrefsUtils.getInstance().remove(ConstantUtil.SWITCH_MODE_KEY);
            finish();
        }
    }


    /**
     * 解决App重启后导致Fragment重叠的问题
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }


    @Override
    protected void initToolbar() {
        //去掉toolbar
    }
}
