package com.developer.gram.easybilibili.function.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.VideoRelatedAdapter;
import com.developer.gram.easybilibili.base.LazyRefreshFragment;
import com.developer.gram.easybilibili.bean.video.VideoDetailsInfo;
import com.developer.gram.easybilibili.mvp.contract.VideoIntroductionContract;
import com.developer.gram.easybilibili.mvp.presenter.VideoIntroductionPresenter;
import com.developer.gram.easybilibili.util.ConstantUtil;
import com.developer.gram.easybilibili.util.NumberUtils;
import com.developer.gram.easybilibili.widget.UserTagView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Gram on 2018/1/9.
 */

public class VideoIntroductionFragment extends LazyRefreshFragment<VideoIntroductionPresenter> implements VideoIntroductionContract.View{
    @BindView(R.id.tv_title)
    TextView mTitleText;
    @BindView(R.id.tv_play_time)
    TextView mPlayTimeText;
    @BindView(R.id.tv_review_count)
    TextView mReviewCountText;
    @BindView(R.id.tv_description)
    TextView mDescText;
    @BindView(R.id.author_tag)
    UserTagView mAuthorTagView;
    @BindView(R.id.share_num)
    TextView mShareNum;
    @BindView(R.id.coin_num)
    TextView mCoinNum;
    @BindView(R.id.fav_num)
    TextView mFavNum;
    @BindView(R.id.tags_layout)
    TagFlowLayout mTagFlowLayout;
    @BindView(R.id.layout_video_related)
    RelativeLayout mVideoRelatedLayout;

    private int av;
    private VideoDetailsInfo.DataBean mVideoDetailsInfo;

    public static VideoIntroductionFragment newInstance(int aid) {
        VideoIntroductionFragment fragment = new VideoIntroductionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.EXTRA_AV, aid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_video_introduction;
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    protected void initVariables() {
        av = getArguments().getInt(ConstantUtil.EXTRA_AV);
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.getVideoIntroductionData(av);
    }

    @Override
    public void showVideoIntroduction(VideoDetailsInfo videoDetailsInfo) {
        mVideoDetailsInfo = videoDetailsInfo.getData();
        finishTask();
    }

    @Override
    protected void finishTask() {
        //设置视频标题
        mTitleText.setText(mVideoDetailsInfo.getTitle());
        //设置视频播放数量
        mPlayTimeText.setText(NumberUtils.format(mVideoDetailsInfo.getStat().getView()));
        //设置视频弹幕数量
        mReviewCountText.setText(NumberUtils.format(mVideoDetailsInfo.getStat().getDanmaku()));
        //设置Up主信息
        mDescText.setText(mVideoDetailsInfo.getDesc());
        mAuthorTagView.setUpWithInfo(getActivity(), mVideoDetailsInfo.getOwner().getName(),
                mVideoDetailsInfo.getOwner().getMid(), mVideoDetailsInfo.getOwner().getFace());
        //设置分享 收藏 投币数量
        mShareNum.setText(NumberUtils.format(mVideoDetailsInfo.getStat().getShare()));
        mFavNum.setText(NumberUtils.format(mVideoDetailsInfo.getStat().getFavorite()));
        mCoinNum.setText(NumberUtils.format(mVideoDetailsInfo.getStat().getCoin()));
        //设置视频tags
        setVideoTags();
        //设置视频相关
        setVideoRelated();
    }

    private void setVideoRelated() {
        List<VideoDetailsInfo.DataBean.RelatesBean> relates = mVideoDetailsInfo.getRelates();
        if (relates == null) {
            gone(mVideoRelatedLayout);
            return;
        }
        VideoRelatedAdapter mVideoRelatedAdapter = new VideoRelatedAdapter(mRecycler, relates);
        mRecycler.setHasFixedSize(false);
        mRecycler.setNestedScrollingEnabled(false);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        mRecycler.setAdapter(mVideoRelatedAdapter);
        mVideoRelatedAdapter.setOnItemClickListener((position, holder) -> VideoDetailActivity.launch(getActivity(),
                relates.get(position).getAid(), relates.get(position).getPic()));
    }

    private void setVideoTags() {
        List<String> tags = mVideoDetailsInfo.getTags();
        mTagFlowLayout.setAdapter(new TagAdapter<String>(tags) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView mTags = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.layout_tags_item, parent, false);
                mTags.setText(s);
                return mTags;
            }
        });
    }

    @OnClick(R.id.btn_share)
    void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "来自「哔哩哔哩」的分享:" + mVideoDetailsInfo.getDesc());
        startActivity(Intent.createChooser(intent, mVideoDetailsInfo.getTitle()));
    }
}
