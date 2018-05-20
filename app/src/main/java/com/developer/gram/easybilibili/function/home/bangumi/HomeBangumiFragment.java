package com.developer.gram.easybilibili.function.home.bangumi;

import android.support.v7.widget.GridLayoutManager;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.section.HomeBangumiBannerSection;
import com.developer.gram.easybilibili.adapter.section.HomeBangumiBodySection;
import com.developer.gram.easybilibili.adapter.section.HomeBangumiItemSection;
import com.developer.gram.easybilibili.adapter.section.HomeBangumiNewSerialSection;
import com.developer.gram.easybilibili.adapter.section.HomeBangumiRecommendSection;
import com.developer.gram.easybilibili.adapter.section.HomeBangumiSeasonNewSection;
import com.developer.gram.easybilibili.base.LazyRefreshFragment;
import com.developer.gram.easybilibili.bean.bangumi.BangumiAppIndexInfo;
import com.developer.gram.easybilibili.bean.bangumi.BangumiRecommendInfo;
import com.developer.gram.easybilibili.mvp.contract.HomeBangumiContract;
import com.developer.gram.easybilibili.mvp.presenter.HomeBangumiPresenter;
import com.developer.gram.easybilibili.widget.banner.BannerEntity;
import com.developer.gram.easybilibili.widget.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gram on 2018/1/2.
 */

public class HomeBangumiFragment extends LazyRefreshFragment<HomeBangumiPresenter> implements HomeBangumiContract.View{
    private int season;
    private boolean mIsRefreshing = false;
    private List<BannerEntity> bannerList = new ArrayList<>();
    private SectionedRecyclerViewAdapter mAdapter;
    private List<BangumiRecommendInfo.ResultBean> bangumiRecommends = new ArrayList<>();
    private List<BangumiAppIndexInfo.ResultBean.AdBean.HeadBean> banners = new ArrayList<>();
    private List<BangumiAppIndexInfo.ResultBean.AdBean.BodyBean> bangumibodys = new ArrayList<>();
    private List<BangumiAppIndexInfo.ResultBean.PreviousBean.ListBean> seasonNewBangumis = new ArrayList<>();
    private List<BangumiAppIndexInfo.ResultBean.SerializingBean> newBangumiSerials = new ArrayList<>();

    public static HomeBangumiFragment newInstance() {
        return new HomeBangumiFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_bangumi;
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.getBangumiIndexData();
        mPresenter.getBangumiRecommendData();
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    public void showBangumiIndex(BangumiAppIndexInfo bangumiAppIndexInfo) {
        banners.addAll(bangumiAppIndexInfo.getResult().getAd().getHead());
        bangumibodys.addAll(bangumiAppIndexInfo.getResult().getAd().getBody());
        seasonNewBangumis.addAll(bangumiAppIndexInfo.getResult().getPrevious().getList());
        season = bangumiAppIndexInfo.getResult().getPrevious().getSeason();
        newBangumiSerials.addAll(bangumiAppIndexInfo.getResult().getSerializing());
    }

    @Override
    public void showBangumiRecommend(BangumiRecommendInfo bangumiRecommendInfo) {
        bangumiRecommends.addAll(bangumiRecommendInfo.getResult());
        finishTask();
    }

    @Override
    protected void initRecyclerView() {
        mAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 3;
                    default:
                        return 1;
                }
            }
        });
        mRecycler.setHasFixedSize(true);
        mRecycler.setNestedScrollingEnabled(true);
        mRecycler.setLayoutManager(mGridLayoutManager);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setOnTouchListener((v, event) -> mIsRefreshing);
    }

    @Override
    protected void clear() {
        banners.clear();
        bannerList.clear();
        bangumibodys.clear();
        bangumiRecommends.clear();
        newBangumiSerials.clear();
        seasonNewBangumis.clear();
        mAdapter.removeAllSections();
    }

    @Override
    protected void finishTask() {
        for(BangumiAppIndexInfo.ResultBean.AdBean.HeadBean headBean : banners) {
            bannerList.add(new BannerEntity(
                    headBean.getLink(), headBean.getTitle(), headBean.getImg()));
        }
        mAdapter.addSection(new HomeBangumiBannerSection(bannerList));
        mAdapter.addSection(new HomeBangumiItemSection(getActivity()));
        mAdapter.addSection(new HomeBangumiNewSerialSection(getActivity(), newBangumiSerials));
        if (!bangumibodys.isEmpty()) {
            mAdapter.addSection(new HomeBangumiBodySection(getActivity(), bangumibodys));
        }
        mAdapter.addSection(new HomeBangumiSeasonNewSection(getActivity(), season, seasonNewBangumis));
        mAdapter.addSection(new HomeBangumiRecommendSection(getActivity(), bangumiRecommends));
        mAdapter.notifyDataSetChanged();
        mRecycler.scrollToPosition(0);
    }
}
