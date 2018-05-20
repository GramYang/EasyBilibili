package com.developer.gram.easybilibili.function.entry;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.GameCentreAdapter;
import com.developer.gram.easybilibili.adapter.helper.HeaderViewRecyclerAdapter;
import com.developer.gram.easybilibili.base.BaseRefreshActivity;
import com.developer.gram.easybilibili.bean.discover.GameCenterInfo;
import com.developer.gram.easybilibili.bean.discover.VipGameInfo;
import com.developer.gram.easybilibili.function.common.BrowserActivity;
import com.developer.gram.easybilibili.mvp.contract.GameCentreContract;
import com.developer.gram.easybilibili.mvp.presenter.GameCentrePresenter;
import com.developer.gram.easybilibili.widget.CircleProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Gram on 2017/12/19.
 */

public class GameCentreActivity extends BaseRefreshActivity<GameCentrePresenter> implements GameCentreContract.View{
    @BindView(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    private List<GameCenterInfo.ItemsBean> items = new ArrayList<>();
    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;
    private VipGameInfo.DataBean mVipGameInfoData;


    @Override
    public int getLayoutId() {
        return R.layout.activity_game_center;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.setTitle("游戏中心");
    }

    @Override
    protected void loadData() {
        showProgressBar();
        mPresenter.getGameCentreData();
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    public void showGameCenterInfo(GameCenterInfo gameCenterInfo) {
        items.addAll(gameCenterInfo.getItems());
        finishTask();
    }

    @Override
    public void showVipGameInfo(VipGameInfo vipGameInfo) {
        mVipGameInfoData = vipGameInfo.getData();
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        hideProgressBar();
    }

    private void showProgressBar() {
        visible(mCircleProgressView);
        mCircleProgressView.spin();
        gone(mRecycler);
    }


    private void hideProgressBar() {
        gone(mCircleProgressView);
        mCircleProgressView.stopSpinning();
        visible(mRecycler);
    }


    @Override
    public void finishTask() {
        hideProgressBar();
    }


    @Override
    public void initRecyclerView() {
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(GameCentreActivity.this));
        GameCentreAdapter mAdapter = new GameCentreAdapter(mRecycler, items);
        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        createHeadView();
        mRecycler.setAdapter(mHeaderViewRecyclerAdapter);
    }


    private void createHeadView() {
        View headView = LayoutInflater.from(this).inflate(R.layout.layout_vip_game_head_view, mRecycler, false);
        ImageView mVipGameImage = (ImageView) headView.findViewById(R.id.vip_game_image);
        Glide.with(GameCentreActivity.this).load(mVipGameInfoData.getImgPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(mVipGameImage);
        mVipGameImage.setOnClickListener(v -> BrowserActivity.launch(GameCentreActivity.this,
                mVipGameInfoData.getLink(), "年度大会员游戏礼包专区"));
        mHeaderViewRecyclerAdapter.addHeaderView(headView);
    }
}
