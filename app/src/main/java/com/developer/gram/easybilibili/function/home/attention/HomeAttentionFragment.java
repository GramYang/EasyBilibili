package com.developer.gram.easybilibili.function.home.attention;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.AttentionAdapter;
import com.developer.gram.easybilibili.base.LazyRefreshFragment;
import com.developer.gram.easybilibili.bean.attention.Attention;
import com.developer.gram.easybilibili.bean.tree.TreeItem;
import com.developer.gram.easybilibili.mvp.contract.HomeAttentionContract;
import com.developer.gram.easybilibili.mvp.presenter.HomeAttentionPresenter;
import com.developer.gram.easybilibili.widget.CustomEmptyView;
import com.developer.gram.easybilibili.widget.expand.ExpandableLayout;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by Gram on 2018/1/3.
 */

public class HomeAttentionFragment extends LazyRefreshFragment<HomeAttentionPresenter> implements HomeAttentionContract.View {
    @BindView(R.id.guideline)
    Guideline guideline;
    @BindView(R.id.tv_follow)
    TextView tvFollow;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.iv_all)
    ImageView ivAll;
    @BindView(R.id.cl_all)
    ConstraintLayout clAll;
    @BindView(R.id.v_bottom)
    View vBottom;
    @BindView(R.id.empty_layout)
    CustomEmptyView emptyLayout;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.tv_all_select)
    TextView tvAllSelect;
    @BindView(R.id.tv_up_select)
    TextView tvUpSelect;
    @BindView(R.id.tv_bangumi_select)
    TextView tvBangumiSelect;
    @BindView(R.id.expand)
    ExpandableLayout expand;

    private List<TreeItem> treeItems = new ArrayList<>();
    private AttentionAdapter mAdapter;

    public static HomeAttentionFragment newInstance() {
        return new HomeAttentionFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_attention;
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.getAttentionData();
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    protected void initViews() {
        tvAllSelect.setSelected(true);
        RxView.clicks(expand)
                .compose(bindToLifecycle())
                .subscribe(o -> {
                    if(expand.isExpanded()) {
                        expand.collapse();
                    } else {
                        expand.expand();
                    }
                });
        RxView.clicks(tvAllSelect)
                .compose(bindToLifecycle())
                .subscribe(o -> {
                    tvAllSelect.setSelected(true);
                    tvUpSelect.setSelected(false);
                    tvBangumiSelect.setSelected(false);
                    expand.collapse();
                });
        RxView.clicks(tvUpSelect)
                .compose(bindToLifecycle())
                .subscribe(o -> {
                    tvAllSelect.setSelected(false);
                    tvUpSelect.setSelected(true);
                    tvBangumiSelect.setSelected(false);
                    expand.collapse();
                });
        RxView.clicks(tvBangumiSelect)
                .compose(bindToLifecycle())
                .subscribe(o -> {
                    tvAllSelect.setSelected(false);
                    tvUpSelect.setSelected(false);
                    tvBangumiSelect.setSelected(true);
                    expand.collapse();
                });
    }

    @Override
    public void showAttention(List<TreeItem> data) {
        treeItems.addAll(data);
        finishTask();
    }

    @Override
    protected void finishTask() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initRecyclerView() {
        mAdapter = new AttentionAdapter(getContext(), treeItems);
        mRecycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setOnTouchListener((v, event) -> mIsRefreshing);
    }

    @Override
    protected void clear() {
        treeItems.clear();
    }
}
