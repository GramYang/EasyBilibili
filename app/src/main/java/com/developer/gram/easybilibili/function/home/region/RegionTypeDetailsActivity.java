package com.developer.gram.easybilibili.function.home.region;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.view.Menu;
import android.widget.TextView;

import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.pager.RegionPagerAdapter;
import com.developer.gram.easybilibili.base.BaseActivity;
import com.developer.gram.easybilibili.bean.region.RegionTypesInfo;
import com.developer.gram.easybilibili.rx.RxBus;
import com.developer.gram.easybilibili.util.ConstantUtil;
import com.developer.gram.easybilibili.widget.NoScrollViewPager;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Gram on 2017/12/21.
 */

public class RegionTypeDetailsActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTab;

    private RegionTypesInfo.DataBean mDataBean;
    private List<String> titles = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_region_details;
    }

    @Override
    protected void initVariables() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            mDataBean = mBundle.getParcelable(ConstantUtil.EXTRA_PARTITION);
        }
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.setTitle(mDataBean.getName());
    }

    @Override
    public void initViews() {
        initViewPager();
        initRxBus();
    }

    private void initRxBus() {
        RxBus.INSTANCE.toFlowable(Integer.class)
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::switchPager);
    }

    private void initViewPager() {
        titles.add("推荐");
        for(RegionTypesInfo.DataBean.ChildrenBean childrenBean : mDataBean.getChildren()) {
            titles.add(childrenBean.getName());
        }
        RegionPagerAdapter mAdapter = new RegionPagerAdapter(getSupportFragmentManager(), mDataBean.getTid(), titles, mDataBean.getChildren());
        mViewPager.setOffscreenPageLimit(titles.size());
        mViewPager.setAdapter(mAdapter);
        mSlidingTab.setViewPager(mViewPager);
        //动态设置tabView的下划线宽度
        measureTabLayoutTextWidth(0);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_region, menu);
        return true;
    }


    public void measureTabLayoutTextWidth(int position) {
        String titleName = titles.get(position);
        TextView titleView = mSlidingTab.getTitleView(position);
        TextPaint paint = titleView.getPaint();
        float v = paint.measureText(titleName);
        mSlidingTab.setIndicatorWidth(v / 3);
    }


    public static void launch(Context context, RegionTypesInfo.DataBean dataBean) {
        Intent mIntent = new Intent(context, RegionTypeDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantUtil.EXTRA_PARTITION, dataBean);
        mIntent.putExtras(bundle);
        context.startActivity(mIntent);
    }

    private void switchPager(int position) {
        switch (position) {
            case 0:
                mViewPager.setCurrentItem(1);
                break;
            case 1:
                mViewPager.setCurrentItem(2);
                break;
            case 2:
                mViewPager.setCurrentItem(3);
                break;
            case 3:
                mViewPager.setCurrentItem(4);
                break;
            case 4:
                mViewPager.setCurrentItem(5);
                break;
            case 5:
                mViewPager.setCurrentItem(6);
                break;
            case 6:
                mViewPager.setCurrentItem(7);
                break;
        }
    }
}
