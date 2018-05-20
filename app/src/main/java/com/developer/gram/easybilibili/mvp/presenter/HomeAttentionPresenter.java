package com.developer.gram.easybilibili.mvp.presenter;

import com.developer.gram.easybilibili.base.BaseSubscriber;
import com.developer.gram.easybilibili.base.RxPresenter;
import com.developer.gram.easybilibili.bean.attention.Attention;
import com.developer.gram.easybilibili.bean.tree.TreeItem;
import com.developer.gram.easybilibili.mvp.contract.HomeAttentionContract;
import com.developer.gram.easybilibili.network.helper.RetrofitHelper;
import com.developer.gram.easybilibili.util.EmptyUtils;
import com.developer.gram.easybilibili.util.JsonUtils;
import com.developer.gram.easybilibili.util.RxUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by Gram on 2018/1/3.
 */

public class HomeAttentionPresenter extends RxPresenter<HomeAttentionContract.View> implements HomeAttentionContract.Presenter<HomeAttentionContract.View> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public HomeAttentionPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getAttentionData() {
        addSubscribe(Flowable.just(JsonUtils.readAssetsJson("dynamic.json"))
                .map(s -> {
                    Gson gson = new Gson();
                    JsonObject jsonObject = new JsonParser().parse(s).getAsJsonObject();
                    JsonArray item = jsonObject.getAsJsonObject("data").getAsJsonArray("item");
                    List<Attention.ItemBean> itemBeans = new ArrayList<>();
                    for(JsonElement jsonElement : item) {
                        itemBeans.add(gson.fromJson(jsonElement, Attention.ItemBean.class));
                    }
                    return itemBeans;})
                .map(itemBeans -> {
                    List<TreeItem> treeItems = new ArrayList<>();
                    for(Attention.ItemBean itemBean : itemBeans) {
                        treeItems.add(new TreeItem()
                                .setGroup(itemBean)
                                .setItemType(TreeItem.TYPE_LV0)
                                .setLevel(TreeItem.TYPE_LV0)
                                .addSubItem(getRecent(itemBean))
                                .setFlag(!getRecent(itemBean).isEmpty())); //recent为空则flag为false
                    }
                    return treeItems;
                })
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<List<TreeItem>>(mView) {
                    @Override
                    public void onSuccess(List<TreeItem> treeItems) {
                        mView.showAttention(treeItems);
                    }
                })
        );
    }

    private List<TreeItem> getRecent(Attention.ItemBean data) {
        List<Attention.ItemBean.RecentBean> recents = data.recent;
        List<TreeItem> treeItems = new ArrayList<>();
        if(EmptyUtils.isNotEmpty(recents)) {
            for(Attention.ItemBean.RecentBean recentBean : recents) {
                treeItems.add(new TreeItem()
                        .setRecent(recentBean)
                        .setItemType(TreeItem.TYPE_LV1)
                        .setLevel(TreeItem.TYPE_LV1));
            }
        }
        return treeItems;
    }
}
