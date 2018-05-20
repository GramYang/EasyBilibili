package com.developer.gram.easybilibili.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.widget.CustomEmptyView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Gram on 2017/12/19.
 * 基础Activity
 */

public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends RxAppCompatActivity implements BaseContract.BaseView{
    private Unbinder bind;
    @Inject
    protected T mPresenter;
    protected Toolbar mToolbar;//Toolbar
    public CustomEmptyView mCustomEmptyView;
    protected boolean mBack = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局内容
        setContentView(getLayoutId());
        //初始化黄油刀控件绑定框架
        bind = ButterKnife.bind(this);
        //toolbar和错误布局
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        mCustomEmptyView = ButterKnife.findById(this, R.id.empty_layout);
        //依赖注入
        initInject();
        //Presenter绑定View
        initPresenter();
        //初始化变量，比如传入的Intent
        initVariables();
        EasyBiliApp.getInstance().addActivity(this);
        if (mToolbar != null) {
            //初始化Toolbar
            initToolbar();
            //让组件支持Toolbar
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(v -> finish());
        }
        //初始化控件
        initViews();
        //初始化数据
        initDatas();
    }


    /**
     * 设置布局layout
     *
     * @return
     */
    @LayoutRes
    public abstract int getLayoutId();

    /**
     * 初始化views
     */
    protected abstract void initViews();

    /**
     * 统一处理toolbar
     */
    protected void initToolbar() {
        if (mBack) mToolbar.setNavigationIcon(R.drawable.ic_clip_back_white);
    }

    /**
     * 初始化数据
     */
    protected void initVariables() {

    }

    /**
     * 加载数据
     */
    protected void initDatas() {
        loadData();
    }

    protected void loadData() {}

    /**
     * 设置数据显示
     */
    public void finishTask() {
    }

    protected void initInject() {

    }

    private void initPresenter() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * 初始化recyclerView
     */
    protected void initRecyclerView() {
    }

    /**
     * 初始化refreshLayout
     */
    protected void initRefreshLayout() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
        EasyBiliApp.getInstance().removeActivity(this);
        bind.unbind();
    }

    @Override
    public void showError(String msg) {
        if (mCustomEmptyView != null) {
            visible(mCustomEmptyView);
            mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_load_error);
            mCustomEmptyView.setEmptyText(msg);
        }
    }

    @Override
    public void complete() {
        if (mCustomEmptyView != null) {
            gone(mCustomEmptyView);
        }
    }

    /**
     * 隐藏View
     *
     * @param views 视图
     */
    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * 显示View
     *
     * @param views 视图
     */
    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 不显示View
     * @param views
     */
    protected void invisible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    /**
     * 隐藏View
     *
     * @param id
     */
    protected void gone(final @IdRes int... id) {
        if (id != null && id.length > 0) {
            for (int resId : id) {
                View view = $(resId);
                if (view != null)
                    gone(view);
            }
        }

    }

    /**
     * 显示View
     *
     * @param id
     */
    protected void visible(final @IdRes int... id) {
        if (id != null && id.length > 0) {
            for (int resId : id) {
                View view = $(resId);
                if (view != null)
                    visible(view);
            }
        }
    }

    /**
     * 不显示View
     * @param id
     */
    protected void invisible(final @IdRes int... id) {
        if (id != null && id.length > 0) {
            for (int resId : id) {
                View view = $(resId);
                if (view != null)
                    invisible(view);
            }
        }
    }

    protected View $(@IdRes int id) {
        View view;
        view = this.findViewById(id);
        return view;
    }
}
