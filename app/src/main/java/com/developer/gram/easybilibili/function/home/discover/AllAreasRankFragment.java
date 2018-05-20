package com.developer.gram.easybilibili.function.home.discover;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.AllAreasRankAdapter;
import com.developer.gram.easybilibili.base.LazyRefreshFragment;
import com.developer.gram.easybilibili.bean.discover.AllareasRankInfo;
import com.developer.gram.easybilibili.function.video.VideoDetailActivity;
import com.developer.gram.easybilibili.mvp.contract.AllAreasRankContract;
import com.developer.gram.easybilibili.mvp.presenter.AllAreasRankPresenter;
import com.developer.gram.easybilibili.util.ConstantUtil;
import com.developer.gram.easybilibili.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gram on 2017/12/22.
 */

public class AllAreasRankFragment extends LazyRefreshFragment<AllAreasRankPresenter> implements AllAreasRankContract.View{
    private String type;
    private AllAreasRankAdapter mAdapter;
    private List<AllareasRankInfo.RankBean.ListBean> allRanks = new ArrayList<>();

    public static AllAreasRankFragment newInstance(String type) {
        AllAreasRankFragment mFragment = new AllAreasRankFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(ConstantUtil.EXTRA_KEY, type);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_all_areas_rank;
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    protected void initVariables() {
        type = getArguments().getString(ConstantUtil.EXTRA_KEY);
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.getAllAreasRankData(type);
    }

    @Override
    public void showAllAreasRank(List<AllareasRankInfo.RankBean.ListBean> listBeans) {
        allRanks.addAll(listBeans.subList(0, 20));
        finishTask();
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        ToastUtils.showSingleToast("加载失败啦,请重新加载~");
    }

    @Override
    protected void initRecyclerView() {
        mRecycler.setHasFixedSize(true);
        mRecycler.setNestedScrollingEnabled(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new AllAreasRankAdapter(mRecycler, allRanks);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((position, holder) -> VideoDetailActivity.launch(getActivity(),
                allRanks.get(position).getAid(),
                allRanks.get(position).getPic()));
    }

    @Override
    protected void clear() {
        allRanks.clear();
    }

    @Override
    protected void finishTask() {
        mAdapter.notifyDataSetChanged();
    }
}
