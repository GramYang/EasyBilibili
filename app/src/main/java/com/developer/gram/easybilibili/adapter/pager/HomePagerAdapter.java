package com.developer.gram.easybilibili.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.function.home.attention.HomeAttentionFragment;
import com.developer.gram.easybilibili.function.home.bangumi.HomeBangumiFragment;
import com.developer.gram.easybilibili.function.home.discover.HomeDiscoverFragment;
import com.developer.gram.easybilibili.function.home.live.HomeLiveFragment;
import com.developer.gram.easybilibili.function.home.recommend.HomeRecommendFragment;
import com.developer.gram.easybilibili.function.home.region.HomeRegionFragment;

/**
 * Created by Gram on 2017/12/20.
 * HomePageFragment用来适配子Fragment
 */

public class HomePagerAdapter extends FragmentPagerAdapter {

    private final String[] TITLES;
    private Fragment[] fragments;

    public HomePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        TITLES = context.getResources().getStringArray(R.array.sections);
        fragments = new Fragment[TITLES.length];
    }


    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            switch (position) {
                case 0:
                    fragments[position] = HomeLiveFragment.newIntance();
                    break;
                case 1:
                    fragments[position] = HomeRecommendFragment.newInstance();
                    break;
                case 2:
                    fragments[position] = HomeBangumiFragment.newInstance();
                    break;
                case 3:
                    fragments[position] = HomeRegionFragment.newInstance();
                    break;
                case 4:
                    fragments[position] = HomeAttentionFragment.newInstance();
                    break;
                case 5:
                    fragments[position] = HomeDiscoverFragment.newInstance();
                    break;
                default:
                    break;
            }
        }
        return fragments[position];
    }


    @Override
    public int getCount() {
        return TITLES.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}

