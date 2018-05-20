package com.developer.gram.easybilibili.function.home.bangumi;

import android.support.v7.widget.GridLayoutManager;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.NewBangumiSerialAdapter;
import com.developer.gram.easybilibili.base.BaseRefreshActivity;
import com.developer.gram.easybilibili.bean.bangumi.NewBangumiSerialInfo;
import com.developer.gram.easybilibili.mvp.contract.NewBangumiSerialContract;
import com.developer.gram.easybilibili.mvp.presenter.NewBangumiSerialPresenter;
import com.developer.gram.easybilibili.widget.CircleProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Gram on 2018/1/9.
 */

public class NewBangumiSerialActivity extends BaseRefreshActivity<NewBangumiSerialPresenter> implements NewBangumiSerialContract.View{
    @BindView(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    private NewBangumiSerialAdapter mAdapter;
    private List<NewBangumiSerialInfo.ListBean> newBangumiSerials = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_bangumi_serial;
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.setTitle("全部新番连载");
    }

    @Override
    protected void loadData() {
        showProgressBar();
        mPresenter.getNewBangumiSerialData();
    }

    @Override
    public void showNewBangumiSerial(List<NewBangumiSerialInfo.ListBean> listBeans) {
        newBangumiSerials.addAll(listBeans);
        finishTask();
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        hideProgressBar();
    }

    @Override
    public void finishTask() {
        hideProgressBar();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void initRecyclerView() {
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new GridLayoutManager(NewBangumiSerialActivity.this, 3));
        mAdapter = new NewBangumiSerialAdapter(mRecycler, newBangumiSerials, true);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((position, holder) -> {

        });
    }

    private void showProgressBar() {
        visible(mCircleProgressView);
        mCircleProgressView.spin();
    }

    private void hideProgressBar() {
        gone(mCircleProgressView);
        mCircleProgressView.stopSpinning();
    }
}
