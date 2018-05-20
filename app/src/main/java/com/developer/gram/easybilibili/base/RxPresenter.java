package com.developer.gram.easybilibili.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Gram on 2017/12/20.
 */

public class RxPresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {
    protected T mView;
    private CompositeDisposable mCompositeDisposable;

    private void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    protected boolean remove(Disposable disposable) {
        return mCompositeDisposable != null && mCompositeDisposable.remove(disposable);
    }

    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}
