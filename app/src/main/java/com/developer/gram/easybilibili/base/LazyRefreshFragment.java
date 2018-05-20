package com.developer.gram.easybilibili.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.util.SnackbarUtil;
import com.developer.gram.easybilibili.util.ToastUtils;

import butterknife.ButterKnife;

/**
 * Created by Gram on 2017/12/20.
 * 在LazyFragment的基础上整合了SwipeRefreshLayout和RefreshLayout的逻辑
 */

public abstract class LazyRefreshFragment<T extends BaseContract.BasePresenter> extends LazyFragment<T> implements SwipeRefreshLayout.OnRefreshListener {
    protected SwipeRefreshLayout mRefresh;
    protected RecyclerView mRecycler;
    protected boolean mIsRefreshing = false;

    @Override
    protected void initRefreshLayout() {
        if (mRefresh != null) {
            mRefresh.setColorSchemeResources(R.color.colorPrimary);
            mRecycler.post(() -> {
                mRefresh.setRefreshing(true);
                mIsRefreshing = true;
                lazyLoadData();
            });
            mRefresh.setOnRefreshListener(this);
        }
    }

    @Override
    public void onRefresh() {
        clearData();
        lazyLoadData();
    }

    protected void clearData() {
        mIsRefreshing = true;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mRefresh = ButterKnife.findById(parentView, R.id.refresh);
        mRecycler = ButterKnife.findById(parentView, R.id.recycler);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) return;
        initRefreshLayout();
        initRecyclerView();
        //如果不存在refreshlayout，则仍然要执行lazyLoadData()
        if (mRefresh == null) lazyLoadData();
        isPrepared = false;
    }

    @Override
    public void complete() {
        super.complete();
        if(mRecycler != null) {
            visible(mRecycler);
        }
        if(mRefresh != null) {
            mRefresh.postDelayed(() -> {
                mRefresh.setRefreshing(false);
            }, 650);
        }
        if (mIsRefreshing) {
            clear();
            ToastUtils.showSingleLongToast("刷新成功");
        }
        mIsRefreshing = false;
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        if(mRefresh != null) {
            mRefresh.setRefreshing(false);
        }
        if(mRecycler != null) {
            visible(mRecycler);
            SnackbarUtil.showMessage(mRecycler, "请重新加载或者检查网络是否链接");
        }
    }

    protected void clear() {

    }


}
