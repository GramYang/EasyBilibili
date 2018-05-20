package com.developer.gram.easybilibili.function.entry;

import android.webkit.WebView;

import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.base.BaseActivity;
import com.developer.gram.easybilibili.util.ConstantUtil;
import com.developer.gram.easybilibili.util.SystemBarHelper;

import butterknife.BindView;

/**
 * Created by Gram on 2017/12/19.
 */

public class VipActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_vip;
    }

    @Override
    public void initViews() {
        mWebView.loadUrl(ConstantUtil.VIP_URL);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.setTitle("");
        //设置StatusBar透明
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, mToolbar);
    }

}
