package com.developer.gram.easybilibili.function.entry;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.base.LazyFragment;
import com.developer.gram.easybilibili.function.common.MainActivity;

import butterknife.BindView;

/**
 * Created by Gram on 2017/12/19.
 */

public class IFavoritesFragment extends LazyFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static IFavoritesFragment newInstance() {
        return new IFavoritesFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_empty;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mToolbar.setTitle("我的收藏");
        mToolbar.setNavigationIcon(R.drawable.ic_navigation_drawer);
        mToolbar.setNavigationOnClickListener(v -> {
            Activity activity1 = getActivity();
            if (activity1 instanceof MainActivity) {
                ((MainActivity) activity1).toggleDrawer();
            }
        });
        mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_fav_no_data);
        mCustomEmptyView.setEmptyText("没有找到你的收藏哟");
    }
}
