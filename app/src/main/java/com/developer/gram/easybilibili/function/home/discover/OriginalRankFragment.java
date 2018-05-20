package com.developer.gram.easybilibili.function.home.discover;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.OriginalRankAdapter;
import com.developer.gram.easybilibili.base.LazyRefreshFragment;
import com.developer.gram.easybilibili.bean.discover.OriginalRankInfo;
import com.developer.gram.easybilibili.function.video.VideoDetailActivity;
import com.developer.gram.easybilibili.mvp.contract.OriginalRankContract;
import com.developer.gram.easybilibili.mvp.presenter.OriginalRankPresenter;
import com.developer.gram.easybilibili.util.ConstantUtil;
import com.developer.gram.easybilibili.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gram on 2017/12/24.
 */

public class OriginalRankFragment extends LazyRefreshFragment<OriginalRankPresenter> implements OriginalRankContract.View{
    private String order;
    private boolean mIsRefreshing = false;
    private OriginalRankAdapter mAdapter;
    private List<OriginalRankInfo.RankBean.ListBean> originalRanks = new ArrayList<>();

    public static OriginalRankFragment newInstance(String order) {
        OriginalRankFragment mFragment = new OriginalRankFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantUtil.EXTRA_ORDER, order);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_original_rank;
    }

    @Override
    protected void initVariables() {
        order = getArguments().getString(ConstantUtil.EXTRA_ORDER);
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.getOriginalRankData(order);
    }

    @Override
    public void showOriginalRank(List<OriginalRankInfo.RankBean.ListBean> listBeans) {
        originalRanks.addAll(listBeans.subList(0, 20));
        finishTask();
    }

    @Override
    protected void clear() {
        originalRanks.clear();
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        ToastUtils.showSingleToast("加载失败啦,请重新加载~");
    }

    @Override
    protected void finishTask() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initRecyclerView() {
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new OriginalRankAdapter(mRecycler, originalRanks);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setOnTouchListener((v, event) -> mIsRefreshing);
        mAdapter.setOnItemClickListener((position, holder) -> VideoDetailActivity.
                launch(getActivity(), originalRanks.get(position).getAid(),
                        originalRanks.get(position).getPic()));
    }
}
