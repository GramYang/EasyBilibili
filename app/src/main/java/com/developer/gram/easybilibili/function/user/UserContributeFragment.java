package com.developer.gram.easybilibili.function.user;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.UserContributeVideoAdapter;
import com.developer.gram.easybilibili.adapter.helper.EndlessRecyclerOnScrollListener;
import com.developer.gram.easybilibili.adapter.helper.HeaderViewRecyclerAdapter;
import com.developer.gram.easybilibili.base.LazyRefreshFragment;
import com.developer.gram.easybilibili.bean.user.UserContributeInfo;
import com.developer.gram.easybilibili.function.video.VideoDetailActivity;
import com.developer.gram.easybilibili.mvp.contract.UserContributeContract;
import com.developer.gram.easybilibili.mvp.presenter.UserContributePresenter;
import com.developer.gram.easybilibili.util.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gram on 2017/12/25.
 */

public class UserContributeFragment extends LazyRefreshFragment<UserContributePresenter> implements UserContributeContract.View{
    private int mid;
    private int pageNum = 1;
    private int pageSize = 10;
    private View loadMoreView;
    private UserContributeVideoAdapter mAdapter;
    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;
    private List<UserContributeInfo.DataBean.VlistBean> userContributes = new ArrayList<>();


    public static UserContributeFragment newInstance(int mid, UserContributeInfo userContributeInfo) {
        UserContributeFragment mFragment = new UserContributeFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt(ConstantUtil.EXTRA_MID, mid);
        mBundle.putParcelable(ConstantUtil.EXTRA_DATA, userContributeInfo);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user_contribute;
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    protected void initVariables() {
        mid = getArguments().getInt(ConstantUtil.EXTRA_MID);
        UserContributeInfo userContributeInfo = getArguments().getParcelable(ConstantUtil.EXTRA_DATA);
        if (userContributeInfo != null) {
            userContributes.addAll(userContributeInfo.getData().getVlist());
        }
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.getUserContributeData(mid, pageNum, pageSize);
    }

    @Override
    public void showUserContribute(List<UserContributeInfo.DataBean.VlistBean> vlistBeans) {
        if (vlistBeans.size() < pageSize) {
            gone(loadMoreView);
            mHeaderViewRecyclerAdapter.removeFootView();
        }
        userContributes.addAll(vlistBeans);
        finishTask();
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        gone(loadMoreView);
    }

    @Override
    protected void initRecyclerView() {
        mRecycler.setHasFixedSize(true);
        mAdapter = new UserContributeVideoAdapter(mRecycler, userContributes);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLinearLayoutManager);
        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        mRecycler.setAdapter(mHeaderViewRecyclerAdapter);
        createHeadView();
        createLoadMoreView();
        mRecycler.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int i) {
                pageNum++;
                loadData();
                visible(loadMoreView);
            }
        });
        if (userContributes.isEmpty()) {
            initEmptyLayout();
        }
        mAdapter.setOnItemClickListener((position, holder) -> VideoDetailActivity.launch(getActivity(),
                userContributes.get(position).getAid(), userContributes.get(position).getPic()));
    }


    @Override
    protected void finishTask() {
        gone(loadMoreView);
        if (pageNum * pageSize - pageSize - 1 > 0) {
            mAdapter.notifyItemRangeChanged(pageNum * pageSize - pageSize - 1, pageSize);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void createHeadView() {
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_user_chase_bangumi_head, mRecycler, false);
        mHeaderViewRecyclerAdapter.addHeaderView(headView);
    }


    private void createLoadMoreView() {
        loadMoreView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_load_more, mRecycler, false);
        mHeaderViewRecyclerAdapter.addFooterView(loadMoreView);
        gone(loadMoreView);
    }

    private void initEmptyLayout() {
        mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_space_no_data);
        mCustomEmptyView.setEmptyText("ㄟ( ▔, ▔ )ㄏ 再怎么找也没有啦");
    }
}
