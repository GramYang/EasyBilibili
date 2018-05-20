package com.developer.gram.easybilibili.function.home.bangumi;

import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.BangumiIndexAdapter;
import com.developer.gram.easybilibili.adapter.helper.HeaderViewRecyclerAdapter;
import com.developer.gram.easybilibili.base.BaseRefreshActivity;
import com.developer.gram.easybilibili.bean.bangumi.BangumiIndexInfo;
import com.developer.gram.easybilibili.mvp.contract.BangumiIndexContract;
import com.developer.gram.easybilibili.mvp.presenter.BangumiIndexPresenter;
import com.developer.gram.easybilibili.widget.CircleProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Gram on 2017/12/20.
 */

public class BangumiIndexActivity extends BaseRefreshActivity<BangumiIndexPresenter> implements BangumiIndexContract.View{
    @BindView(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    private GridLayoutManager mGridLayoutManager;
    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;
    private List<BangumiIndexInfo.ResultBean.CategoryBean> categorys = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_bangumi_index;
    }

    @Override
    protected void loadData() {
        showProgressBar();
        mPresenter.getBangumiIndexData();
    }

    @Override
    public void showBangumiIndex(BangumiIndexInfo bangumiIndexInfo) {
        categorys.addAll(bangumiIndexInfo.getResult().getCategory());
        finishTask();
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.setTitle("番剧索引");
    }

    @Override
    public void initRecyclerView() {
        mRecycler.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(BangumiIndexActivity.this, 3);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position == 0) ? 3 : 1;
            }
        });
        mRecycler.setLayoutManager(mGridLayoutManager);
        BangumiIndexAdapter mAdapter = new BangumiIndexAdapter(mRecycler, categorys);
        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        createHeadLayout();
        mRecycler.setAdapter(mHeaderViewRecyclerAdapter);
    }

    private void createHeadLayout() {
        View headView = LayoutInflater.from(this).inflate(R.layout.layout_bangumi_index_head, mRecycler, false);
        mHeaderViewRecyclerAdapter.addHeaderView(headView);
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        hideProgressBar();
    }

    private void showProgressBar() {
        visible(mCircleProgressView);
        mCircleProgressView.spin();
    }

    private void hideProgressBar() {
        gone(mCircleProgressView);
        mCircleProgressView.stopSpinning();
    }

    @Override
    public void finishTask() {
        mHeaderViewRecyclerAdapter.notifyDataSetChanged();
        hideProgressBar();
    }
}
