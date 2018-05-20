package com.developer.gram.easybilibili.function.home.recommend;

import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.section.HomeRecommendActivityCenterSection;
import com.developer.gram.easybilibili.adapter.section.HomeRecommendBannerSection;
import com.developer.gram.easybilibili.adapter.section.HomeRecommendPicSection;
import com.developer.gram.easybilibili.adapter.section.HomeRecommendSection;
import com.developer.gram.easybilibili.adapter.section.HomeRecommendTopicSection;
import com.developer.gram.easybilibili.base.LazyRefreshFragment;
import com.developer.gram.easybilibili.bean.recommend.RecommendBannerInfo;
import com.developer.gram.easybilibili.bean.recommend.RecommendInfo;
import com.developer.gram.easybilibili.mvp.contract.HomeRecommendContract;
import com.developer.gram.easybilibili.mvp.presenter.HomeRecommendPresenter;
import com.developer.gram.easybilibili.util.ConstantUtil;
import com.developer.gram.easybilibili.widget.banner.BannerEntity;
import com.developer.gram.easybilibili.widget.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gram on 2018/1/1.
 */

public class HomeRecommendFragment extends LazyRefreshFragment<HomeRecommendPresenter> implements HomeRecommendContract.View {
    private boolean mIsRefreshing = false;
    private SectionedRecyclerViewAdapter mAdapter;
    private List<BannerEntity> banners = new ArrayList<>();
    private List<RecommendInfo.ResultBean> results = new ArrayList<>();
    //顶部轮播条
    private List<RecommendBannerInfo.DataBean> recommendBanners = new ArrayList<>();

    public static HomeRecommendFragment newInstance() {
        return new HomeRecommendFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_recommend;
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.getRecomendBannerData();
        mPresenter.getRecommendData();
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    public void showRecommendBanner(RecommendBannerInfo recommendBannerInfo) {
        recommendBanners.addAll(recommendBannerInfo.getData());
    }

    @Override
    public void showRecommendInfo(RecommendInfo recommendInfo) {
        results.addAll(recommendInfo.getResult());
        finishTask();
    }

    @Override
    protected void initRecyclerView() {
        mAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 2;
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setOnTouchListener((v, event) -> mIsRefreshing);
    }

    @Override
    protected void clear() {
        banners.clear();
        recommendBanners.clear();
        results.clear();
        mAdapter.removeAllSections();
    }

    /**
     * 设置轮播banners
     */
    private void convertBanner() {
        for(RecommendBannerInfo.DataBean dataBean : recommendBanners) {
            banners.add(new BannerEntity(dataBean.getValue(), dataBean.getTitle(), dataBean.getImage()));
        }
    }

    @Override
    protected void finishTask() {
        convertBanner();
        mAdapter.addSection(new HomeRecommendBannerSection(banners));
        int size = results.size();
        for (int i = 0; i < size; i++) {
            String type = results.get(i).getType();
            if (!TextUtils.isEmpty(type)) {
                switch (type) {
                    case ConstantUtil.TYPE_TOPIC:
                        //话题
                        mAdapter.addSection(new HomeRecommendTopicSection(getContext(),
                                results.get(i).getBody().get(0).getCover(),
                                results.get(i).getBody().get(0).getTitle(),
                                results.get(i).getBody().get(0).getParam()));
                        break;
                    case ConstantUtil.TYPE_ACTIVITY_CENTER:
                        //活动中心
                        mAdapter.addSection(new HomeRecommendActivityCenterSection(
                                getContext(),
                                results.get(i).getBody()));
                        break;
                    default:
                        mAdapter.addSection(new HomeRecommendSection(
                                getContext(),
                                results.get(i).getHead().getTitle(),
                                results.get(i).getType(),
                                results.get(1).getHead().getCount(),
                                results.get(i).getBody()));
                        break;
                }
            }
            String style = results.get(i).getHead().getStyle();
            if (style.equals(ConstantUtil.STYLE_PIC)) {
                mAdapter.addSection(new HomeRecommendPicSection(getContext(),
                        results.get(i).getBody().get(0).getCover(),
                        results.get(i).getBody().get(0).getParam()));
            }
        }
        mAdapter.notifyDataSetChanged();
        mRecycler.scrollToPosition(0);
    }
}
