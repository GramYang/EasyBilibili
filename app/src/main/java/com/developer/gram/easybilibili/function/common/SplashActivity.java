package com.developer.gram.easybilibili.function.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.bean.Splash.Splash;
import com.developer.gram.easybilibili.mvp.contract.SplashContract;
import com.developer.gram.easybilibili.mvp.presenter.SplashPresenter;
import com.developer.gram.easybilibili.util.ConstantUtil;
import com.developer.gram.easybilibili.util.PrefsUtils;
import com.developer.gram.easybilibili.util.StatusBarUtil;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Gram on 2017/12/29.
 */

public class SplashActivity extends RxAppCompatActivity implements SplashContract.View{
    @Inject
    SplashPresenter mPresenter;
    @BindView(R.id.iv_splash)
    ImageView mIvSplash;
    @BindView(R.id.tv_count_down)
    TextView mTvCountDown;
    @BindView(R.id.ll_count_down)
    LinearLayout mLlCountDown;

    private Unbinder binder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        //设置透明
        StatusBarUtil.setTransparent(this);
        binder = ButterKnife.bind(this);
        initInject();
        initWidget();
        loadData();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) mPresenter.detachView();
        binder.unbind();
        super.onDestroy();
    }

    @Override
    public void showError(String msg) {
        //设置默认图片
        mIvSplash.setImageResource(R.drawable.ic_splash_default);
    }

    @Override
    public void complete() {

    }

    @Override
    public void showSplash(Splash splash) {
        if (!splash.data.isEmpty()) {
            int pos = new Random().nextInt(splash.data.size());
            Glide.with(this)
                    .load(splash.data.get(pos).thumb)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(mIvSplash);
        } else {
            mIvSplash.setImageResource(R.drawable.ic_splash_default);
        }
    }

    @Override
    public void showCountDown(int count) {
        mTvCountDown.setText(count + "");
        if (count == 0) {
            finishTask();
        }
    }

    /**
     * 注入依赖
     */
    private void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
        mPresenter.attachView(this);//依赖 保持p和v生命周期一致
    }

    private void initWidget() {
        RxView.clicks(mLlCountDown)
                .throttleFirst(3, TimeUnit.SECONDS)//3秒内响应第一次发射数据
                .compose(bindToLifecycle())
                .subscribe(object -> finishTask());
    }

    /**
     * 跳转到首页
     */
    private void finishTask() {
        boolean flag = PrefsUtils.getInstance().getBoolean(ConstantUtil.KEY, false);
        flag = false;
        if (flag) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

    }

    private void loadData() {
        mPresenter.getSplashData();
        mPresenter.setCountDown();
    }

}
