package com.developer.gram.easybilibili.function.user;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.UserInterestQuanAdapter;
import com.developer.gram.easybilibili.adapter.helper.EndlessRecyclerOnScrollListener;
import com.developer.gram.easybilibili.adapter.helper.HeaderViewRecyclerAdapter;
import com.developer.gram.easybilibili.base.LazyRefreshFragment;
import com.developer.gram.easybilibili.bean.user.UserInterestQuanInfo;
import com.developer.gram.easybilibili.mvp.contract.UserInterestQuanContract;
import com.developer.gram.easybilibili.mvp.presenter.UserInterestQuanPresenter;
import com.developer.gram.easybilibili.util.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gram on 2017/12/25.
 */

public class UserInterestQuanFragment extends LazyRefreshFragment<UserInterestQuanPresenter> implements UserInterestQuanContract.View{
    private int mid;
    private int pageNum = 1;
    private int pageSize = 10;
    private View loadMoreView;
    private UserInterestQuanAdapter mAdapter;
    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;
    private List<UserInterestQuanInfo.DataBean.ResultBean> userInterestQuans = new ArrayList<>();

    public static UserInterestQuanFragment newInstance(int mid, UserInterestQuanInfo userInterestQuanInfo) {
        UserInterestQuanFragment mFragment = new UserInterestQuanFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.EXTRA_MID, mid);
        bundle.putParcelable(ConstantUtil.EXTRA_DATA, userInterestQuanInfo);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user_interest_quan;
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    protected void initVariables() {
        mid = getArguments().getInt(ConstantUtil.EXTRA_MID);
        UserInterestQuanInfo userInterestQuanInfo = getArguments().getParcelable(ConstantUtil.EXTRA_DATA);
        if (userInterestQuanInfo != null) {
            userInterestQuans.addAll(userInterestQuanInfo.getData().getResult());
        }
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.getUserInterestData(mid, pageNum, pageSize);
    }

    @Override
    public void showUserInterestQuan(List<UserInterestQuanInfo.DataBean.ResultBean> resultBeans) {
        if (resultBeans.size() < pageSize) {
            gone(loadMoreView);
            mHeaderViewRecyclerAdapter.removeFootView();
        }
        userInterestQuans.addAll(resultBeans);
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
        mAdapter = new UserInterestQuanAdapter(mRecycler, userInterestQuans);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLinearLayoutManager);
        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        mRecycler.setAdapter(mHeaderViewRecyclerAdapter);
        createLoadMoreView();
        mRecycler.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int i) {
                pageNum++;
                loadData();
                visible(loadMoreView);
            }
        });
        if (userInterestQuans.isEmpty()) {
            initEmptyLayout();
        }
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

    private void createLoadMoreView() {
        loadMoreView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_load_more, mRecycler, false);
        mHeaderViewRecyclerAdapter.addFooterView(loadMoreView);
        loadMoreView.setVisibility(View.GONE);
    }

    private void initEmptyLayout() {
        mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_space_no_data);
        mCustomEmptyView.setEmptyText("ㄟ( ▔, ▔ )ㄏ 再怎么找也没有啦");
    }
}
