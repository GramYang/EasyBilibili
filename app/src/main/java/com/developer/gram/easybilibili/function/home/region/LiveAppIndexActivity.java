package com.developer.gram.easybilibili.function.home.region;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.LiveAppIndexAdapter;
import com.developer.gram.easybilibili.base.BaseRefreshActivity;
import com.developer.gram.easybilibili.bean.live.LiveAppIndexInfo;
import com.developer.gram.easybilibili.mvp.contract.LiveAppIndexContract;
import com.developer.gram.easybilibili.mvp.presenter.LiveAppIndexPresenter;

/**
 * Created by Gram on 2017/12/21.
 */

public class LiveAppIndexActivity extends BaseRefreshActivity<LiveAppIndexPresenter> implements LiveAppIndexContract.View{
    private LiveAppIndexAdapter mLiveAppIndexAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_live_app_index;
    }

    @Override
    protected void loadData() {
        mPresenter.getshowLiveAppData();
    }

    @Override
    public void showLiveAppIndex(LiveAppIndexInfo liveAppIndexInfo) {
        mLiveAppIndexAdapter.setLiveInfo(liveAppIndexInfo);
        finishTask();
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.setTitle("直播");
    }

    @Override
    public void initRecyclerView() {
        mLiveAppIndexAdapter = new LiveAppIndexAdapter(this);
        mRecycler.setAdapter(mLiveAppIndexAdapter);
        GridLayoutManager layout = new GridLayoutManager(this, 12);
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        layout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mLiveAppIndexAdapter.getSpanSize(position);
            }
        });
        mRecycler.setLayoutManager(layout);
    }

    @Override
    public void finishTask() {
        mLiveAppIndexAdapter.notifyDataSetChanged();
        mRecycler.scrollToPosition(0);
    }
}
