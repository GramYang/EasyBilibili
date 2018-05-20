package com.developer.gram.easybilibili.function.home.discover;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.ActivityCenterAdapter;
import com.developer.gram.easybilibili.adapter.helper.EndlessRecyclerOnScrollListener;
import com.developer.gram.easybilibili.adapter.helper.HeaderViewRecyclerAdapter;
import com.developer.gram.easybilibili.base.BaseRefreshActivity;
import com.developer.gram.easybilibili.bean.discover.ActivityCenterInfo;
import com.developer.gram.easybilibili.function.common.BrowserActivity;
import com.developer.gram.easybilibili.mvp.contract.ActivityCenterContract;
import com.developer.gram.easybilibili.mvp.presenter.ActivityCenterPresenter;
import com.developer.gram.easybilibili.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gram on 2017/12/21.
 */

public class ActivityCenterActivity extends BaseRefreshActivity<ActivityCenterPresenter> implements ActivityCenterContract.View{
    private int pageNum = 1;
    private int pageSize = 20;
    private View loadMoreView;
    private boolean mIsRefreshing = false;
    private ActivityCenterAdapter mAdapter;
    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;
    private EndlessRecyclerOnScrollListener mEndlessRecyclerOnScrollListener;
    private List<ActivityCenterInfo.ListBean> activityCenters = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_activity_center;
    }

    @Override
    protected void loadData() {
        mPresenter.getActivityCenterData(pageNum, pageSize);
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        mToolbar.setTitle("活动中心");
    }

    @Override
    public void showActivityCenter(List<ActivityCenterInfo.ListBean> listBeans) {
        if (listBeans.size() < pageSize) {
            gone(loadMoreView);
            mHeaderViewRecyclerAdapter.removeFootView();
        }
        activityCenters.addAll(listBeans);
        finishTask();
    }

    @Override
    public void initRecyclerView() {
        mRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(linearLayoutManager);
        mAdapter = new ActivityCenterAdapter(mRecycler, activityCenters);
        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        mRecycler.setAdapter(mHeaderViewRecyclerAdapter);
        createLoadMoreView();
        mEndlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                pageNum++;
                loadData();
                visible(loadMoreView);
            }
        };
        mRecycler.addOnScrollListener(mEndlessRecyclerOnScrollListener);
        mAdapter.setOnItemClickListener((position, holder) -> BrowserActivity.launch(
                ActivityCenterActivity.this, activityCenters.get(position).getLink(),
                activityCenters.get(position).getTitle()));
        mRecycler.setOnTouchListener((v, event) -> mIsRefreshing);
    }

    private void createLoadMoreView() {
        loadMoreView = LayoutInflater.from(ActivityCenterActivity.this).inflate(R.layout.layout_load_more, mRecycler, false);
        mHeaderViewRecyclerAdapter.addFooterView(loadMoreView);
        gone(loadMoreView);
    }

    @Override
    protected void clearData() {
        super.clearData();
        pageNum = 1;
        mEndlessRecyclerOnScrollListener.refresh();
    }

    @Override
    protected void clear() {
        activityCenters.clear();
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        gone(loadMoreView);
        ToastUtils.showSingleToast("加载失败啦,请重新加载~");
    }

    @Override
    public void finishTask() {
        gone(loadMoreView);
        if (pageNum * pageSize - pageSize - 1 > 0) {
            mAdapter.notifyItemRangeChanged(pageNum * pageSize - pageSize - 1, pageSize);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
}
