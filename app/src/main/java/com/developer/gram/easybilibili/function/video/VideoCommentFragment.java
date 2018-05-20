package com.developer.gram.easybilibili.function.video;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.VideoCommentAdapter;
import com.developer.gram.easybilibili.adapter.VideoHotCommentAdapter;
import com.developer.gram.easybilibili.adapter.helper.EndlessRecyclerOnScrollListener;
import com.developer.gram.easybilibili.adapter.helper.HeaderViewRecyclerAdapter;
import com.developer.gram.easybilibili.base.LazyRefreshFragment;
import com.developer.gram.easybilibili.bean.video.VideoCommentInfo;
import com.developer.gram.easybilibili.mvp.contract.VideoCommentContract;
import com.developer.gram.easybilibili.mvp.presenter.VideoCommentPresenter;
import com.developer.gram.easybilibili.util.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gram on 2018/1/9.
 */

public class VideoCommentFragment extends LazyRefreshFragment<VideoCommentPresenter> implements VideoCommentContract.View{

    private int aid;
    private int pageNum = 1;
    private int pageSize = 20;
    private int ver = 3;
    private View headView;
    private View loadMoreView;
    private HeaderViewRecyclerAdapter mAdapter;
    private VideoHotCommentAdapter mVideoHotCommentAdapter;
    private List<VideoCommentInfo.List> comments = new ArrayList<>();
    private List<VideoCommentInfo.HotList> hotComments = new ArrayList<>();

    public static VideoCommentFragment newInstance(int aid) {
        VideoCommentFragment fragment = new VideoCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.AID, aid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_video_comment;
    }

    @Override
    protected void initVariables() {
        aid = getArguments().getInt(ConstantUtil.AID);
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.getVideoCommentData(aid, pageNum, pageSize, ver);
    }

    @Override
    public void showVideoComment(VideoCommentInfo videoCommentInfo) {
        ArrayList<VideoCommentInfo.List> list = videoCommentInfo.list;
        ArrayList<VideoCommentInfo.HotList> hotList = videoCommentInfo.hotList;
        if (list.size() < pageSize) {
            loadMoreView.setVisibility(View.GONE);
            mAdapter.removeFootView();
        }
        comments.addAll(list);
        hotComments.addAll(hotList);
        finishTask();
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        gone(loadMoreView);
        gone(headView);
    }

    @Override
    protected void initRecyclerView() {
        VideoCommentAdapter mRecyclerAdapter = new VideoCommentAdapter(mRecycler, comments);
        mRecycler.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLinearLayoutManager);
        mAdapter = new HeaderViewRecyclerAdapter(mRecyclerAdapter);
        mRecycler.setAdapter(mAdapter);
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
    }

    @Override
    protected void finishTask() {
        gone(loadMoreView);
        mVideoHotCommentAdapter.notifyDataSetChanged();
        if (pageNum * pageSize - pageSize - 1 > 0) {
            mAdapter.notifyItemRangeChanged(pageNum * pageSize - pageSize - 1, pageSize);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void createHeadView() {
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_video_hot_comment_head, mRecycler, false);
        RecyclerView mHotCommentRecycler = (RecyclerView) headView.findViewById(R.id.hot_comment_recycler);
        mHotCommentRecycler.setHasFixedSize(false);
        mHotCommentRecycler.setNestedScrollingEnabled(false);
        mHotCommentRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mVideoHotCommentAdapter = new VideoHotCommentAdapter(mHotCommentRecycler, hotComments);
        mHotCommentRecycler.setAdapter(mVideoHotCommentAdapter);
        mAdapter.addHeaderView(headView);
    }

    private void createLoadMoreView() {
        loadMoreView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_load_more, mRecycler, false);
        mAdapter.addFooterView(loadMoreView);
        gone(loadMoreView);
    }
}
