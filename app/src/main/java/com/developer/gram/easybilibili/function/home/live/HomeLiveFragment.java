package com.developer.gram.easybilibili.function.home.live;

import android.support.v7.widget.GridLayoutManager;
import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.section.HomeLiveBannerSection;
import com.developer.gram.easybilibili.adapter.section.HomeLiveEntranceSection;
import com.developer.gram.easybilibili.adapter.section.HomeLiveRecommendBannerSection;
import com.developer.gram.easybilibili.adapter.section.HomeLiveRecommendSection;
import com.developer.gram.easybilibili.adapter.section.HomeLiveSection;
import com.developer.gram.easybilibili.base.LazyRefreshFragment;
import com.developer.gram.easybilibili.bean.live.LiveAppIndexInfo;
import com.developer.gram.easybilibili.mvp.contract.HomeLiveContract;
import com.developer.gram.easybilibili.mvp.presenter.HomeLivePresenter;
import com.developer.gram.easybilibili.widget.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gram on 2017/12/29.
 */

public class HomeLiveFragment extends LazyRefreshFragment<HomeLivePresenter> implements HomeLiveContract.View{
    //最上层轮播条
    private List<LiveAppIndexInfo.DataBean.BannerBean> bannerBeans = new ArrayList<>();
    //推荐主播
    private List<LiveAppIndexInfo.DataBean.RecommendDataBean.LivesBean> recommendLiveBeans = new ArrayList<>();
    //推荐主播中间的那个banner
    private List<LiveAppIndexInfo.DataBean.RecommendDataBean.BannerDataBean> recommendLiveBannerBeans = new ArrayList<>();
    //推荐主播Partition
    private LiveAppIndexInfo.DataBean.RecommendDataBean.PartitionBean recomendLivePartitionBean;
    //分区主播partition+live
    private List<LiveAppIndexInfo.DataBean.PartitionsBean> liveBeans = new ArrayList<>();

    private SectionedRecyclerViewAdapter mAdapter;

    public static HomeLiveFragment newIntance() {
        return new HomeLiveFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_live;
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.getLiveData();
    }

    @Override
    public void showLive(LiveAppIndexInfo liveAppIndexInfo) {
        bannerBeans.addAll(liveAppIndexInfo.getData().getBanner());
        recommendLiveBeans.addAll(liveAppIndexInfo.getData().getRecommend_data().getLives());
        recommendLiveBannerBeans.addAll(liveAppIndexInfo.getData().getRecommend_data().getBanner_data());
        recomendLivePartitionBean = liveAppIndexInfo.getData().getRecommend_data().getPartition();
        liveBeans.addAll(liveAppIndexInfo.getData().getPartitions());
        finishTask();
    }

    @Override
    protected void initRecyclerView() {
        mAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 2;//2格
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER:
                        return 2;//2格
                    default:
                        return 1;
                }
            }
        });
        mRecycler.setLayoutManager(gridLayoutManager);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void clear() {
        bannerBeans.clear();
        recommendLiveBeans.clear();
        recommendLiveBannerBeans.clear();
        recomendLivePartitionBean = null;
        liveBeans.clear();
        mAdapter.removeAllSections();
    }

    @Override
    protected void finishTask() {
        //banner
        mAdapter.addSection(new HomeLiveBannerSection(bannerBeans));
        //entranceIcon
        mAdapter.addSection(new HomeLiveEntranceSection(getContext()));
        //推荐主播
        if(recommendLiveBannerBeans.size() != 0) {
            int count = recommendLiveBeans.size() / 2;
            if(recommendLiveBannerBeans.size() == 1) {
                mAdapter.addSection(new HomeLiveRecommendSection(getContext(), true, false,
                        recomendLivePartitionBean.getName(), recomendLivePartitionBean.getSub_icon().getSrc(),
                        recomendLivePartitionBean.getCount() + "", recommendLiveBeans.subList(0, count)));
                mAdapter.addSection(new HomeLiveRecommendBannerSection(getContext(), recommendLiveBannerBeans.get(0)));
                mAdapter.addSection(new HomeLiveRecommendSection(getContext(), false, true,
                        recomendLivePartitionBean.getName(), recomendLivePartitionBean.getSub_icon().getSrc(),
                        recomendLivePartitionBean.getCount() + "", recommendLiveBeans.subList(count, recommendLiveBeans.size())));
            } else {
                mAdapter.addSection(new HomeLiveRecommendSection(getContext(), true, false,
                        recomendLivePartitionBean.getName(), recomendLivePartitionBean.getSub_icon().getSrc(),
                        recomendLivePartitionBean.getCount() + "", recommendLiveBeans.subList(0, count), recommendLiveBannerBeans.get(0)));
                mAdapter.addSection(new HomeLiveRecommendBannerSection(getContext(), recommendLiveBannerBeans.get(1)));
                mAdapter.addSection(new HomeLiveRecommendSection(getContext(), false, true,
                        recomendLivePartitionBean.getName(), recomendLivePartitionBean.getSub_icon().getSrc(),
                        recomendLivePartitionBean.getCount() + "", recommendLiveBeans.subList(count, recommendLiveBeans.size())));
            }
        } else {
            mAdapter.addSection(new HomeLiveRecommendSection(getContext(), true, true,
                    recomendLivePartitionBean.getName(), recomendLivePartitionBean.getSub_icon().getSrc(),
                    recomendLivePartitionBean.getCount() + "", recommendLiveBeans));
        }
        //分区，剩下最后一个不加载
        for(LiveAppIndexInfo.DataBean.PartitionsBean partitionsBean : liveBeans.subList(0, liveBeans.size() - 1)) {
            mAdapter.addSection(new HomeLiveSection(getContext(), false, partitionsBean.getPartition().getName(),
                    partitionsBean.getPartition().getSub_icon().getSrc(), partitionsBean.getPartition().getCount() + "",
                    partitionsBean.getLives().size() < 4 ? partitionsBean.getLives() : partitionsBean.getLives().subList(0, 4)));
        }
        //分区，最后一个分区有一个加载更多的footer
        mAdapter.addSection(new HomeLiveSection(getContext(), true, liveBeans.get(liveBeans.size() - 1).getPartition().getName(),
                liveBeans.get(liveBeans.size() - 1).getPartition().getSub_icon().getSrc(),
                liveBeans.get(liveBeans.size() - 1).getPartition().getCount() + "",
                liveBeans.get(liveBeans.size() - 1).getLives().size() < 4 ? liveBeans.get(liveBeans.size() - 1).getLives() :
                        liveBeans.get(liveBeans.size() - 1).getLives().subList(0, 4)));
        mAdapter.notifyDataSetChanged();
        mRecycler.scrollToPosition(0);
    }
}
