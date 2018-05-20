package com.developer.gram.easybilibili.function.home.discover;

import android.support.v7.widget.LinearLayoutManager;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.TopicCenterAdapter;
import com.developer.gram.easybilibili.base.BaseRefreshActivity;
import com.developer.gram.easybilibili.bean.discover.TopicCenterInfo;
import com.developer.gram.easybilibili.function.common.BrowserActivity;
import com.developer.gram.easybilibili.mvp.contract.TopicCenterContract;
import com.developer.gram.easybilibili.mvp.presenter.TopicCenterPresenter;
import com.developer.gram.easybilibili.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gram on 2017/12/21.
 */

public class TopicCenterActivity extends BaseRefreshActivity<TopicCenterPresenter> implements TopicCenterContract.View{
    private TopicCenterAdapter mAdapter;
    private boolean mIsRefreshing = false;
    private List<TopicCenterInfo.ListBean> topicCenters = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_topic_center;
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    protected void loadData() {
        mPresenter.getTopicCenterData();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.setTitle("话题中心");
    }

    @Override
    public void showTopicCenter(List<TopicCenterInfo.ListBean> listBeans) {
        topicCenters.addAll(listBeans);
        finishTask();
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        ToastUtils.showSingleToast("加载失败啦,请重新加载~");
    }

    @Override
    public void initRecyclerView() {
        mRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(linearLayoutManager);
        mAdapter = new TopicCenterAdapter(mRecycler, topicCenters);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((position, holder) -> BrowserActivity.launch(
                TopicCenterActivity.this, topicCenters.get(position).getLink(),
                topicCenters.get(position).getTitle()));
        mRecycler.setOnTouchListener((v, event) -> mIsRefreshing);
    }

    @Override
    protected void clear() {
        topicCenters.clear();
    }

    @Override
    public void finishTask() {
        mAdapter.notifyDataSetChanged();
    }

}
