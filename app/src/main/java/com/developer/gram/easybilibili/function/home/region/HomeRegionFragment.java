package com.developer.gram.easybilibili.function.home.region;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.section.RegionActivityCenterSection;
import com.developer.gram.easybilibili.adapter.section.RegionEntranceSection;
import com.developer.gram.easybilibili.adapter.section.RegionSection;
import com.developer.gram.easybilibili.adapter.section.RegionTopicSection;
import com.developer.gram.easybilibili.base.LazyRefreshFragment;
import com.developer.gram.easybilibili.bean.region.RegionRecommend;
import com.developer.gram.easybilibili.bean.region.RegionTypesInfo;
import com.developer.gram.easybilibili.mvp.contract.HomeRegionContract;
import com.developer.gram.easybilibili.mvp.presenter.HomeRegionPresenter;
import com.developer.gram.easybilibili.widget.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gram on 2018/1/2.
 */

public class HomeRegionFragment extends LazyRefreshFragment<HomeRegionPresenter> implements HomeRegionContract.View {
    private List<RegionTypesInfo.DataBean> regionTypes = new ArrayList<>();
    private List<RegionRecommend.DataBean> dataBeans = new ArrayList<>();
    private SectionedRecyclerViewAdapter mAdapter;

    public static HomeRegionFragment newInstance() {
        return new HomeRegionFragment();
    }

    @Override
    public
    @LayoutRes
    int getLayoutResId() {
        return R.layout.fragment_home_region;
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.getRegionTypeData();
        mPresenter.getRegionData();
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    public void showRegion(List<RegionTypesInfo.DataBean> dataBeans) {
        regionTypes.addAll(dataBeans);
    }

    @Override
    public void showRegionRecommend(RegionRecommend regionRecommend) {
        dataBeans.addAll(regionRecommend.getData());
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
                        return 2;//2格
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER:
                        return 2;//2格
                    default:
                        return 1;
                }
            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void finishTask() {
        mAdapter.addSection(new RegionEntranceSection(getContext(), regionTypes));
        for(RegionRecommend.DataBean dataBean : dataBeans) {
            String type = dataBean.getType();
            if(type.equals("topic")) {
                mAdapter.addSection(new RegionTopicSection(getContext(), dataBean.getBody().get(0)));
            } else if(type.equals("activity")) {
                mAdapter.addSection(new RegionActivityCenterSection(getContext(), dataBean.getBody()));
            } else {
                mAdapter.addSection(new RegionSection(getContext(), dataBean.getTitle(), dataBean.getBody()));
            }
        }
        mAdapter.notifyDataSetChanged();
        mRecycler.scrollToPosition(0);
    }
}
