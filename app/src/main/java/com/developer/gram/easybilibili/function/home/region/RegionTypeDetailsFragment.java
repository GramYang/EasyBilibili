package com.developer.gram.easybilibili.function.home.region;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.section.RegionDetailsHotVideoSection;
import com.developer.gram.easybilibili.adapter.section.RegionDetailsNewsVideoSection;
import com.developer.gram.easybilibili.base.LazyRefreshFragment;
import com.developer.gram.easybilibili.bean.region.RegionDetailsInfo;
import com.developer.gram.easybilibili.mvp.contract.RegionTypeDetailsContract;
import com.developer.gram.easybilibili.mvp.presenter.RegionTypeDetailsPresenter;
import com.developer.gram.easybilibili.util.ConstantUtil;
import com.developer.gram.easybilibili.util.ToastUtils;
import com.developer.gram.easybilibili.widget.CircleProgressView;
import com.developer.gram.easybilibili.widget.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Gram on 2017/12/24.
 */

public class RegionTypeDetailsFragment extends LazyRefreshFragment<RegionTypeDetailsPresenter> implements RegionTypeDetailsContract.View {
    @BindView(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    private int rid;
    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;
    private List<RegionDetailsInfo.DataBean.NewBean> newsVideos = new ArrayList<>();
    private List<RegionDetailsInfo.DataBean.RecommendBean> recommendVideos = new ArrayList<>();


    public static RegionTypeDetailsFragment newInstance(int rid) {
        RegionTypeDetailsFragment fragment = new RegionTypeDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.EXTRA_RID, rid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_region_details;
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    protected void initVariables() {
        rid = getArguments().getInt(ConstantUtil.EXTRA_RID);
    }

    @Override
    protected void lazyLoadData() {
        showProgressBar();
        mPresenter.getRegionTypeDetailsData(rid);
    }

    @Override
    public void showRegionTypeDetails(RegionDetailsInfo regionDetailsInfo) {
        recommendVideos.addAll(regionDetailsInfo.getData().getRecommend());
        newsVideos.addAll(regionDetailsInfo.getData().getNewX());
        finishTask();
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        hideProgressBar();
        ToastUtils.showSingleToast("加载失败啦,请重新加载~");
    }

    @Override
    protected void initRecyclerView() {
        mSectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mSectionedRecyclerViewAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 1;
                    default:
                        return 1;
                }
            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mSectionedRecyclerViewAdapter);
    }

    @Override
    protected void finishTask() {
        hideProgressBar();
        mSectionedRecyclerViewAdapter.addSection(new RegionDetailsHotVideoSection(getActivity(), recommendVideos));
        mSectionedRecyclerViewAdapter.addSection(new RegionDetailsNewsVideoSection(getActivity(), newsVideos));
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void showProgressBar() {
        visible(mCircleProgressView);
        mCircleProgressView.spin();
    }

    private void hideProgressBar() {
        gone(mCircleProgressView);
        mCircleProgressView.stopSpinning();
    }
}
