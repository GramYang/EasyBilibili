package com.developer.gram.easybilibili.function.home.region;

import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.section.RegionRecommendBannerSection;
import com.developer.gram.easybilibili.adapter.section.RegionRecommendDynamicSection;
import com.developer.gram.easybilibili.adapter.section.RegionRecommendHotSection;
import com.developer.gram.easybilibili.adapter.section.RegionRecommendNewSection;
import com.developer.gram.easybilibili.base.BaseRefreshActivity;
import com.developer.gram.easybilibili.bean.region.RegionRecommendInfo;
import com.developer.gram.easybilibili.mvp.contract.AdvertisingContract;
import com.developer.gram.easybilibili.mvp.presenter.AdvertisingPresenter;
import com.developer.gram.easybilibili.util.ConstantUtil;
import com.developer.gram.easybilibili.util.ToastUtils;
import com.developer.gram.easybilibili.widget.banner.BannerEntity;
import com.developer.gram.easybilibili.widget.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gram on 2017/12/21.
 */

public class AdvertisingActivity extends BaseRefreshActivity<AdvertisingPresenter> implements AdvertisingContract.View{
    private boolean mIsRefreshing = false;
    private List<BannerEntity> bannerEntities = new ArrayList<>();
    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;
    private List<RegionRecommendInfo.DataBean.BannerBean.TopBean> banners = new ArrayList<>();
    private List<RegionRecommendInfo.DataBean.RecommendBean> recommends = new ArrayList<>();
    private List<RegionRecommendInfo.DataBean.NewBean> news = new ArrayList<>();
    private List<RegionRecommendInfo.DataBean.DynamicBean> dynamics = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_advertising;
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        mToolbar.setTitle("广告");
    }

    @Override
    protected void loadData() {
        mPresenter.getAdvertisingData();
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    public void showAdvertising(RegionRecommendInfo.DataBean dataBean) {
        banners.addAll(dataBean.getBanner().getTop());
        recommends.addAll(dataBean.getRecommend());
        news.addAll(dataBean.getNewX());
        dynamics.addAll(dataBean.getDynamic());
        finishTask();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_region, menu);
        return true;
    }

    @Override
    protected void clear() {
        bannerEntities.clear();
        banners.clear();
        recommends.clear();
        news.clear();
        dynamics.clear();
        mSectionedRecyclerViewAdapter.removeAllSections();
    }

    @Override
    public void initRecyclerView() {
        mSectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager mLayoutManager = new GridLayoutManager(AdvertisingActivity.this, 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mSectionedRecyclerViewAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mSectionedRecyclerViewAdapter);
        mRecycler.setOnTouchListener((v, event) -> mIsRefreshing);
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        ToastUtils.showSingleLongToast("加载失败啦,请重新加载~");
    }

    @Override
    public void finishTask() {
        setBanner();
        mSectionedRecyclerViewAdapter.addSection(new RegionRecommendBannerSection(bannerEntities));
        mSectionedRecyclerViewAdapter.addSection(new RegionRecommendHotSection(AdvertisingActivity.this, ConstantUtil.ADVERTISING_RID, recommends));
        mSectionedRecyclerViewAdapter.addSection(new RegionRecommendNewSection(AdvertisingActivity.this, ConstantUtil.ADVERTISING_RID, news));
        mSectionedRecyclerViewAdapter.addSection(new RegionRecommendDynamicSection(AdvertisingActivity.this, dynamics));
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void setBanner() {
        for(RegionRecommendInfo.DataBean.BannerBean.TopBean topBean : banners) {
            bannerEntities.add(new BannerEntity(
                    topBean.getUri(), topBean.getTitle(), topBean.getImage()));
        }
    }
}
