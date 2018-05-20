package com.developer.gram.easybilibili.function.home.discover;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.Menu;

import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.base.BaseActivity;
import com.developer.gram.easybilibili.widget.NoScrollViewPager;
import com.flyco.tablayout.SlidingTabLayout;

import butterknife.BindView;

/**
 * Created by Gram on 2017/12/20.
 */

public class OriginalRankActivity extends BaseActivity {
    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;

    private String[] titles = new String[]{"原创", "全站", "番剧"};
    private String[] orders = new String[]{"origin-03.json", "all-03.json", "all-3-33.json"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_original_rank;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.setTitle("排行榜");
    }

    @Override
    public void initViews() {
        mViewPager.setAdapter(new OriginalRankPagerAdapter(getSupportFragmentManager(), titles, orders));
        mViewPager.setOffscreenPageLimit(orders.length);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rank, menu);
        return true;
    }

    private static class OriginalRankPagerAdapter extends FragmentStatePagerAdapter {
        private String[] titles;
        private String[] orders;

        OriginalRankPagerAdapter(FragmentManager fm, String[] titles, String[] orders) {
            super(fm);
            this.titles = titles;
            this.orders = orders;
        }

        @Override
        public Fragment getItem(int position) {
            return OriginalRankFragment.newInstance(orders[position]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
