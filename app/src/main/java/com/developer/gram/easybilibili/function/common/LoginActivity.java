package com.developer.gram.easybilibili.function.common;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.base.BaseActivity;
import com.developer.gram.easybilibili.util.ConstantUtil;
import com.developer.gram.easybilibili.util.NetworkUtils;
import com.developer.gram.easybilibili.util.PrefsUtils;
import com.developer.gram.easybilibili.util.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;

/**
 * Created by Gram on 2017/12/29.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.iv_icon_left)
    ImageView mLeftLogo;
    @BindView(R.id.iv_icon_right)
    ImageView mRightLogo;
    @BindView(R.id.delete_username)
    ImageView mDeleteUserName;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_login)
    Button login;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews() {
        //用户名点击监听
        RxView.focusChanges(et_username)
                .compose(bindToLifecycle())
                .subscribe(aBoolean -> {if (aBoolean && et_username.getText().length() > 0) {
                    visible(mDeleteUserName);
                } else {
                    gone(mDeleteUserName);
                }
                    mLeftLogo.setImageResource(R.drawable.ic_22);
                    mRightLogo.setImageResource(R.drawable.ic_33);});
        //密码点击监听
        RxView.focusChanges(et_password)
                .compose(bindToLifecycle())
                .subscribe(aBoolean -> {mLeftLogo.setImageResource(R.drawable.ic_22_hide);
                    mRightLogo.setImageResource(R.drawable.ic_33_hide);});
        //用户名输入监听
        RxTextView.textChangeEvents(et_username)
                .compose(bindToLifecycle())
                .subscribe(textViewTextChangeEvent -> {
                    // 如果用户名清空了 清空密码 清空记住密码选项
                    et_password.setText("");
                    if (textViewTextChangeEvent.text().length() > 0) {
                        // 如果用户名有内容时候 显示删除按钮
                        visible(mDeleteUserName);
                    } else {
                        // 如果用户名有内容时候 显示删除按钮
                        gone(mDeleteUserName);
                    }});
        RxView.clicks(login)
                .compose(bindToLifecycle())
                .subscribe(aBoolean -> {//登录
                    boolean isNetConnected = NetworkUtils.isConnected(this);
                    if (!isNetConnected) {
                        ToastUtils.showSingleToast("当前网络不可用,请检查网络设置");
                        return;
                    }
                    login();});
        RxView.clicks(mDeleteUserName)
                .compose(bindToLifecycle())
                .subscribe(aBoolean -> {
                    // 清空用户名以及密码
                    et_username.setText("");
                    et_password.setText("");
                    gone(mDeleteUserName);
                    et_username.setFocusable(true);
                    et_username.setFocusableInTouchMode(true);
                    et_username.requestFocus();});
    }

    @Override
    protected void initToolbar() {
        mBack = false;
        mToolbar.setNavigationIcon(R.drawable.ic_cancle);
        mToolbar.setTitle("登录");
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    private void login() {
        String name = et_username.getText().toString();
        String password = et_password.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showSingleToast("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showSingleToast("密码不能为空");
            return;
        }
        PrefsUtils.getInstance().putBoolean(ConstantUtil.KEY, true);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}
