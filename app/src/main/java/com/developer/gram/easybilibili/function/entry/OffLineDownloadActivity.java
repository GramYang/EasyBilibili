package com.developer.gram.easybilibili.function.entry;

import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.base.BaseActivity;
import com.developer.gram.easybilibili.util.CommonUtil;
import com.developer.gram.easybilibili.util.ToastUtils;
import com.developer.gram.easybilibili.widget.progressbar.NumberProgressBar;

import butterknife.BindView;

/**
 * Created by Gram on 2017/12/19.
 */

public class OffLineDownloadActivity extends BaseActivity {
    @BindView(R.id.progress_bar)
    NumberProgressBar mProgressBar;
    @BindView(R.id.cache_size_text)
    TextView mCacheSize;

    @Override
    public int getLayoutId() {
        return R.layout.activity_offline_download;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.setTitle("离线缓存");
        mBack = false;
        mToolbar.setNavigationIcon(R.drawable.action_button_back_pressed_light);
    }

    @Override
    public void initViews() {
        long phoneTotalSize = CommonUtil.getPhoneTotalSize();
        long phoneAvailableSize = CommonUtil.getPhoneAvailableSize();
        //转换为G的显示单位
        String totalSizeStr = Formatter.formatFileSize(this, phoneTotalSize);
        String availabSizeStr = Formatter.formatFileSize(this, phoneAvailableSize);
        //计算占用空间的百分比
        int progress = countProgress(phoneTotalSize, phoneAvailableSize);
        mProgressBar.setProgress(progress);
        mCacheSize.setText("主存储:" + totalSizeStr + "/" + "可用:" + availabSizeStr);
        assert mCustomEmptyView != null;
        mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_no_downloads);
        mCustomEmptyView.setEmptyText("没有找到你的缓存哟");
    }

    private int countProgress(long phoneTotalSize, long phoneAvailableSize) {
        double totalSize = phoneTotalSize / (1024 * 3);
        double availabSize = phoneAvailableSize / (1024 * 3);
        //取整相减
        int size = (int) (Math.floor(totalSize) - Math.floor(availabSize));
        double v = (size / Math.floor(totalSize)) * 100;
        return (int) Math.floor(v);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recommend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_more) {
            ToastUtils.showSingleToast("离线设置");
        }
        return super.onOptionsItemSelected(item);
    }
}
