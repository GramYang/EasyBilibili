package com.developer.gram.easybilibili.base;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.network.ApiException;
import com.developer.gram.easybilibili.util.LogUtils;
import com.developer.gram.easybilibili.util.NetworkUtils;

import java.net.SocketTimeoutException;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * Created by Gram on 2017/12/14.
 * 针对T处理Subscriber
 */

public abstract class BaseSubscriber<T> extends ResourceSubscriber<T> {
    private BaseContract.BaseView mView;

    public BaseSubscriber(BaseContract.BaseView view) {
        this.mView = view;
    }


    public abstract void onSuccess(T t);

    @Override
    protected void onStart() {
        super.onStart();
        if (!NetworkUtils.isConnected(EasyBiliApp.getInstance())) {
            // Logger.d("没有网络");
        } else {

        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(T response) {
        if (mView == null) return;
        mView.complete();
        onSuccess(response);
    }


    @Override
    public void onError(Throwable e) {
        if (mView == null) return;
        mView.complete();//完成操作
        if (e instanceof ApiException) {
            mView.showError(e.toString());
        } else if (e instanceof SocketTimeoutException) {
            mView.showError("服务器响应超时ヽ(≧Д≦)ノ");
        } else if (e instanceof HttpException) {
            mView.showError("数据加载失败ヽ(≧Д≦)ノ");
        } else {
            mView.showError("未知错误ヽ(≧Д≦)ノ");
            LogUtils.e("MYERROR:"+e.toString());
        }
    }
}
