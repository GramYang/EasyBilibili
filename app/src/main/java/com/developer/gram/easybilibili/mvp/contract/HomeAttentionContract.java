package com.developer.gram.easybilibili.mvp.contract;

import com.developer.gram.easybilibili.base.BaseContract;
import com.developer.gram.easybilibili.bean.attention.Attention;
import com.developer.gram.easybilibili.bean.tree.TreeItem;

import java.util.List;

/**
 * Created by Gram on 2018/1/3.
 */

public interface HomeAttentionContract {
    interface View extends BaseContract.BaseView {
        void showAttention(List<TreeItem> treeItems);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getAttentionData();
    }
}
