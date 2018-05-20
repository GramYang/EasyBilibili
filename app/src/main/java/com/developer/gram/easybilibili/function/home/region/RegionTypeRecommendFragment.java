package com.developer.gram.easybilibili.function.home.region;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.section.RegionRecommendBannerSection;
import com.developer.gram.easybilibili.adapter.section.RegionRecommendDynamicSection;
import com.developer.gram.easybilibili.adapter.section.RegionRecommendHotSection;
import com.developer.gram.easybilibili.adapter.section.RegionRecommendNewSection;
import com.developer.gram.easybilibili.adapter.section.RegionRecommendTypesSection;
import com.developer.gram.easybilibili.base.LazyRefreshFragment;
import com.developer.gram.easybilibili.bean.region.RegionRecommendInfo;
import com.developer.gram.easybilibili.mvp.contract.RegionTypeRecommendContract;
import com.developer.gram.easybilibili.mvp.presenter.RegionTypeRecommendPresenter;
import com.developer.gram.easybilibili.util.ConstantUtil;
import com.developer.gram.easybilibili.util.ToastUtils;
import com.developer.gram.easybilibili.widget.banner.BannerEntity;
import com.developer.gram.easybilibili.widget.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gram on 2017/12/24.
 */

public class RegionTypeRecommendFragment extends LazyRefreshFragment<RegionTypeRecommendPresenter> implements RegionTypeRecommendContract.View{
    private int rid;
    private boolean mIsRefreshing = false;
    private List<BannerEntity> bannerEntities = new ArrayList<>();
    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;
    private List<RegionRecommendInfo.DataBean.BannerBean.TopBean> banners = new ArrayList<>();
    private List<RegionRecommendInfo.DataBean.RecommendBean> recommends = new ArrayList<>();
    private List<RegionRecommendInfo.DataBean.NewBean> news = new ArrayList<>();
    private List<RegionRecommendInfo.DataBean.DynamicBean> dynamics = new ArrayList<>();

    public static RegionTypeRecommendFragment newInstance(int rid) {
        RegionTypeRecommendFragment fragment = new RegionTypeRecommendFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.EXTRA_RID, rid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_region_recommend;
    }

    @Override
    protected void initVariables() {
        rid = getArguments().getInt(ConstantUtil.EXTRA_RID);
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.getRegionTypeRecommendData(rid);
    }

    @Override
    public void showRegionTypeRecommend(RegionRecommendInfo.DataBean dataBean) {
        banners.addAll(dataBean.getBanner().getTop());
        recommends.addAll(dataBean.getRecommend());
        news.addAll(dataBean.getNewX());
        dynamics.addAll(dataBean.getDynamic());
        finishTask();
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
    public void showError(String msg) {
        super.showError(msg);
        ToastUtils.showSingleToast("加载失败啦,请重新加载~");
    }

    @Override
    protected void initRecyclerView() {
        mSectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
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
    protected void finishTask() {
        setBanner();
        mSectionedRecyclerViewAdapter.addSection(new RegionRecommendBannerSection(bannerEntities));
        mSectionedRecyclerViewAdapter.addSection(new RegionRecommendTypesSection(getActivity(), rid));
        mSectionedRecyclerViewAdapter.addSection(new RegionRecommendHotSection(getActivity(), rid, recommends));
        mSectionedRecyclerViewAdapter.addSection(new RegionRecommendNewSection(getActivity(), rid, news));
        mSectionedRecyclerViewAdapter.addSection(new RegionRecommendDynamicSection(getActivity(), dynamics));
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void setBanner() {
        for(RegionRecommendInfo.DataBean.BannerBean.TopBean topBean : banners) {
            bannerEntities.add(new BannerEntity(topBean.getUri(),
                    topBean.getTitle(), topBean.getImage()));
        }
    }
}
