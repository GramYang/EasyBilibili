package com.developer.gram.easybilibili.function.home.bangumi;

import android.support.v7.widget.GridLayoutManager;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.section.BangumiScheduleSection;
import com.developer.gram.easybilibili.base.BaseRefreshActivity;
import com.developer.gram.easybilibili.bean.bangumi.BangumiScheduleInfo;
import com.developer.gram.easybilibili.mvp.contract.BangumiScheduleContract;
import com.developer.gram.easybilibili.mvp.presenter.BangumiSchedulePresenter;
import com.developer.gram.easybilibili.util.ConstantUtil;
import com.developer.gram.easybilibili.util.ToastUtils;
import com.developer.gram.easybilibili.util.WeekDayUtil;
import com.developer.gram.easybilibili.widget.CircleProgressView;
import com.developer.gram.easybilibili.widget.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Gram on 2017/12/20.
 */

public class BangumiScheduleActivity extends BaseRefreshActivity<BangumiSchedulePresenter> implements BangumiScheduleContract.View{
    @BindView(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    private SectionedRecyclerViewAdapter mSectionedAdapter;
    private List<BangumiScheduleInfo.ResultBean> bangumiSchedules = new ArrayList<>();
    private List<BangumiScheduleInfo.ResultBean> sundayBangumis = new ArrayList<>();
    private List<BangumiScheduleInfo.ResultBean> mondayBangumis = new ArrayList<>();
    private List<BangumiScheduleInfo.ResultBean> tuesdayBangumis = new ArrayList<>();
    private List<BangumiScheduleInfo.ResultBean> wednesdayBangumis = new ArrayList<>();
    private List<BangumiScheduleInfo.ResultBean> thursdayBangumis = new ArrayList<>();
    private List<BangumiScheduleInfo.ResultBean> fridayBangumis = new ArrayList<>();
    private List<BangumiScheduleInfo.ResultBean> saturdayBangumis = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_bangumi_schedule;
    }

    @Override
    protected void loadData() {
        showProgressBar();
        mPresenter.getBangumiScheduleData();
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    public void showBangumiSchedule(BangumiScheduleInfo bangumiScheduleInfo) {
        bangumiSchedules.addAll(bangumiScheduleInfo.getResult());
        finishTask();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.setTitle("番剧时间表");
    }

    @Override
    public void initRecyclerView() {
        mSectionedAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager mLayoutManager = new GridLayoutManager(BangumiScheduleActivity.this, 3);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mSectionedAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 3;
                    default:
                        return 1;
                }
            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mSectionedAdapter);
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        hideProgressBar();
        ToastUtils.showSingleToast("加载失败啦,请重新加载~");
    }

    @Override
    public void finishTask() {
        for(BangumiScheduleInfo.ResultBean resultBean : bangumiSchedules) {
            accordingWeekGroup(resultBean);
        }

        mSectionedAdapter.addSection(new BangumiScheduleSection(BangumiScheduleActivity.this, sundayBangumis, ConstantUtil.SUNDAY_TYPE,
                saturdayBangumis.size() > 0 ? WeekDayUtil.formatDate(sundayBangumis.get(0).getPub_date()) : ""));
        mSectionedAdapter.addSection(new BangumiScheduleSection(BangumiScheduleActivity.this, mondayBangumis, ConstantUtil.MONDAY_TYPE,
                mondayBangumis.size() > 0 ? WeekDayUtil.formatDate(mondayBangumis.get(0).getPub_date()) : ""));
        mSectionedAdapter.addSection(new BangumiScheduleSection(BangumiScheduleActivity.this, tuesdayBangumis, ConstantUtil.TUESDAY_TYPE,
                tuesdayBangumis.size() > 0 ? WeekDayUtil.formatDate(tuesdayBangumis.get(0).getPub_date()) : ""));
        mSectionedAdapter.addSection(new BangumiScheduleSection(BangumiScheduleActivity.this, wednesdayBangumis, ConstantUtil.WEDNESDAY_TYPE,
                wednesdayBangumis.size() > 0 ? WeekDayUtil.formatDate(wednesdayBangumis.get(0).getPub_date()) : ""));
        mSectionedAdapter.addSection(new BangumiScheduleSection(BangumiScheduleActivity.this, thursdayBangumis, ConstantUtil.THURSDAY_TYPE,
                thursdayBangumis.size() > 0 ? WeekDayUtil.formatDate(thursdayBangumis.get(0).getPub_date()) : ""));
        mSectionedAdapter.addSection(new BangumiScheduleSection(BangumiScheduleActivity.this, fridayBangumis, ConstantUtil.FRIDAY_TYEP,
                fridayBangumis.size() > 0 ? WeekDayUtil.formatDate(fridayBangumis.get(0).getPub_date()) : ""));
        mSectionedAdapter.addSection(new BangumiScheduleSection(BangumiScheduleActivity.this, saturdayBangumis, ConstantUtil.SATURDAY_TYPE,
                saturdayBangumis.size() > 0 ? WeekDayUtil.formatDate(saturdayBangumis.get(0).getPub_date()) : ""));

        mSectionedAdapter.notifyDataSetChanged();
        hideProgressBar();
    }

    private void accordingWeekGroup(BangumiScheduleInfo.ResultBean resultBean) {
        switch (WeekDayUtil.getWeek(resultBean.getPub_date())) {
            case ConstantUtil.SUNDAY_TYPE:
                sundayBangumis.add(resultBean);
                break;
            case ConstantUtil.MONDAY_TYPE:
                mondayBangumis.add(resultBean);
                break;
            case ConstantUtil.TUESDAY_TYPE:
                tuesdayBangumis.add(resultBean);
                break;
            case ConstantUtil.WEDNESDAY_TYPE:
                wednesdayBangumis.add(resultBean);
                break;
            case ConstantUtil.THURSDAY_TYPE:
                thursdayBangumis.add(resultBean);
                break;
            case ConstantUtil.FRIDAY_TYEP:
                fridayBangumis.add(resultBean);
                break;
            case ConstantUtil.SATURDAY_TYPE:
                saturdayBangumis.add(resultBean);
                break;
        }
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
