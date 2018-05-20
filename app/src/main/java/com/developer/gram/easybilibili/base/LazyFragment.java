package com.developer.gram.easybilibili.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.widget.CustomEmptyView;
import com.trello.rxlifecycle2.components.support.RxFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Gram on 2017/12/19.
 * 基础懒加载Fragment
 */

public abstract class LazyFragment<T extends BaseContract.BasePresenter> extends RxFragment implements BaseContract.BaseView{
    @Inject
    protected T mPresenter;
    protected View parentView;
    protected Activity mActivity;
    protected Context mContext;
    // 标志位 标志已经初始化完成。
    protected boolean isPrepared;
    //标志位 fragment是否可见
    protected boolean isVisible;
    private Unbinder bind;
    public CustomEmptyView mCustomEmptyView;

    public abstract
    @LayoutRes
    int getLayoutResId();

    /**
     *  设置Fragment切换时清空parentView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        if(parentView != null) {
            ViewGroup parent = (ViewGroup) parentView.getParent();
            if(parent != null) {
                parent.removeView(parentView);
            }
        } else {
            parentView = inflater.inflate(getLayoutResId(), container, false);
            mActivity = getActivity();
        }
        return parentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);
        initInject();
        initPresenter();
        initVariables();
        mCustomEmptyView = ButterKnife.findById(parentView, R.id.empty_layout);
        initViews();
        finishCreateView(savedInstanceState);
        initDatas();
    }

    protected void initInject() {}

    protected void initPresenter() {
        if(mPresenter != null)
            mPresenter.attachView(this);
    }

    protected void initVariables() {}

    protected void initViews() {}

    /**
     * 初始化views
     *
     * @param state
     */
    protected void finishCreateView(Bundle state) {
        isPrepared = true;
        lazyLoad();
    }

    protected void initDatas() {
        loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mPresenter != null) mPresenter.detachView();
        bind.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mActivity = (Activity) mContext;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mActivity = null;
    }

    public Context getApplicationContext() {
        return this.mActivity == null ? (getActivity() == null ?
                null : getActivity().getApplicationContext()) : this.mActivity.getApplicationContext();
    }

    /**
     * Fragment数据的懒加载
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * fragment显示时才加载数据
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * fragment懒加载方法
     */
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) return;
        lazyLoadData();
        isPrepared = false;
    }

    protected void lazyLoadData() {}

    /**
     * fragment隐藏
     */
    protected void onInvisible() {
    }

    /**
     * 加载数据
     */
    protected void loadData() {
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

    /**
     * 设置数据显示
     */
    protected void finishTask() {
    }

    /**
     * 显示错误信息
     *
     * @param msg msg
     */
    @Override
    public void showError(String msg) {
        if (mCustomEmptyView != null) {
            visible(mCustomEmptyView);
            mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_load_error);
            mCustomEmptyView.setEmptyText(msg);
        }
    }

    /**
     * 完成加载
     */
    @Override
    public void complete() {
        if (mCustomEmptyView != null) {
            gone(mCustomEmptyView);
        }
    }

    /**
     * 隐藏View
     *
     * @param views view数组
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
     * @param views view数组
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

    public View $(@IdRes int id) {
        View view;
        if (parentView != null) {
            view = parentView.findViewById(id);
            return view;
        }
        return null;
    }
}
