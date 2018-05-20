package com.developer.gram.easybilibili.function.home.bangumi;

import android.support.v7.widget.GridLayoutManager;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.section.SeasonNewBangumiSection;
import com.developer.gram.easybilibili.base.BaseRefreshActivity;
import com.developer.gram.easybilibili.bean.bangumi.SeasonNewBangumiInfo;
import com.developer.gram.easybilibili.mvp.contract.SeasonNewBangumiContract;
import com.developer.gram.easybilibili.mvp.presenter.SeasonNewBangumiPresenter;
import com.developer.gram.easybilibili.widget.CircleProgressView;
import com.developer.gram.easybilibili.widget.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Gram on 2017/12/21.
 */

public class SeasonNewBangumiActivity extends BaseRefreshActivity<SeasonNewBangumiPresenter> implements SeasonNewBangumiContract.View{
    @BindView(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;
    private List<SeasonNewBangumiInfo.ResultBean> results = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_season_new_bangumi;
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    protected void loadData() {
        showProgressBar();
        mPresenter.getSeasonNewBangumiData();
    }

    @Override
    public void showSeasonNewBangumi(SeasonNewBangumiInfo seasonNewBangumiInfo) {
        results.addAll(seasonNewBangumiInfo.getResult().subList(0,50));
        finishTask();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.setTitle("分季全部新番");
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        hideProgressBar();
    }

    @Override
    public void finishTask() {
        for(SeasonNewBangumiInfo.ResultBean resultBean : results) {
            mSectionedRecyclerViewAdapter.addSection(
                    new SeasonNewBangumiSection(SeasonNewBangumiActivity.this,
                            resultBean.getSeason(), resultBean.getYear(), resultBean.getList()));
        }
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
        hideProgressBar();
    }

    @Override
    public void initRecyclerView() {
        mSectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(SeasonNewBangumiActivity.this, 3);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mSectionedRecyclerViewAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 3;
                    default:
                        return 1;
                }
            }
        });
        mRecycler.setHasFixedSize(true);
        mRecycler.setNestedScrollingEnabled(true);
        mRecycler.setLayoutManager(mGridLayoutManager);
        mRecycler.setAdapter(mSectionedRecyclerViewAdapter);
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
