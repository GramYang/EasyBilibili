package com.developer.gram.easybilibili.function.home.discover;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.base.LazyFragment;
import com.developer.gram.easybilibili.bean.discover.HotSearchTag;
import com.developer.gram.easybilibili.function.common.BrowserActivity;
import com.developer.gram.easybilibili.function.entry.GameCentreActivity;
import com.developer.gram.easybilibili.mvp.contract.HomeDiscoverContract;
import com.developer.gram.easybilibili.mvp.presenter.HomeDiscoverPresenter;
import com.developer.gram.easybilibili.util.ConstantUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Gram on 2018/1/4.
 */

public class HomeDiscoverFragment extends LazyFragment<HomeDiscoverPresenter> implements HomeDiscoverContract.View {

    @BindView(R.id.search_scan)
    ImageView searchScan;
    @BindView(R.id.search_edit)
    TextView searchEdit;
    @BindView(R.id.search_img)
    ImageView searchImg;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.tags_layout)
    TagFlowLayout tagsLayout;
    @BindView(R.id.hide_tags_layout)
    TagFlowLayout hideTagsLayout;
    @BindView(R.id.hide_scroll_view)
    NestedScrollView hideScrollView;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.more_layout)
    LinearLayout moreLayout;
    @BindView(R.id.ic_quanzi)
    ImageView icQuanzi;
    @BindView(R.id.ic_quanzi_layout)
    RelativeLayout icQuanziLayout;
    @BindView(R.id.ic_topic)
    ImageView icTopic;
    @BindView(R.id.topic_center_layout)
    RelativeLayout topicCenterLayout;
    @BindView(R.id.ic_activity)
    ImageView icActivity;
    @BindView(R.id.activity_center_layout)
    RelativeLayout activityCenterLayout;
    @BindView(R.id.ic_original)
    ImageView icOriginal;
    @BindView(R.id.layout_original)
    RelativeLayout layoutOriginal;
    @BindView(R.id.ic_all_rank)
    ImageView icAllRank;
    @BindView(R.id.layout_all_rank)
    RelativeLayout layoutAllRank;
    @BindView(R.id.ic_game)
    ImageView icGame;
    @BindView(R.id.layout_game_center)
    RelativeLayout layoutGameCenter;
    @BindView(R.id.ic_shop)
    ImageView icShop;
    @BindView(R.id.layout_shop)
    RelativeLayout layoutShop;
    Unbinder unbinder;
    private boolean isShowMore = true;
    private List<HotSearchTag.ListBean> hotSearchTags = new ArrayList<>();

    public static HomeDiscoverFragment newInstance() {
        return new HomeDiscoverFragment();
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_discover;
    }

    @Override
    protected void initViews() {
        hideScrollView.setNestedScrollingEnabled(true);
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.getHotSearchTagData();
    }

    @Override
    public void showHotSearchTag(List<HotSearchTag.ListBean> listBeans) {
        hotSearchTags.addAll(listBeans);
        initTagLayout();
    }

    private void initTagLayout() {
        //获取热搜标签集合前9个默认显示
        List<HotSearchTag.ListBean> frontTags = hotSearchTags.subList(0, 8);
        tagsLayout.setAdapter(new TagAdapter<HotSearchTag.ListBean>(frontTags) {
            @Override
            public View getView(FlowLayout parent, int position, HotSearchTag.ListBean listBean) {
                TextView mTags = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.layout_tags_item, parent, false);
                mTags.setText(listBean.getKeyword());
//                mTags.setOnClickListener(v -> TotalStationSearchActivity.launch(getActivity(), listBean.getKeyword()));
                return mTags;
            }
        });
        hideTagsLayout.setAdapter(new TagAdapter<HotSearchTag.ListBean>(hotSearchTags) {
            @Override
            public View getView(FlowLayout parent, int position, HotSearchTag.ListBean listBean) {
                TextView mTags = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.layout_tags_item, parent, false);
                mTags.setText(listBean.getKeyword());
//                mTags.setOnClickListener(v -> TotalStationSearchActivity.launch(getActivity(), listBean.getKeyword()));
                return mTags;
            }
        });
    }


    @OnClick(R.id.more_layout)
    void showAndHideMoreLayout() {
        if (isShowMore) {
            isShowMore = false;
            hideScrollView.setVisibility(View.VISIBLE);
            tvMore.setText("收起");
            tagsLayout.setVisibility(View.GONE);
            Drawable upDrawable = getResources().getDrawable(R.drawable.ic_arrow_up_gray_round);
            upDrawable.setBounds(0, 0, upDrawable.getMinimumWidth(), upDrawable.getMinimumHeight());
            tvMore.setCompoundDrawables(upDrawable, null, null, null);
        } else {
            isShowMore = true;
            hideScrollView.setVisibility(View.GONE);
            tvMore.setText("查看更多");
            tagsLayout.setVisibility(View.VISIBLE);
            Drawable downDrawable = getResources().getDrawable(R.drawable.ic_arrow_down_gray_round);
            downDrawable.setBounds(0, 0, downDrawable.getMinimumWidth(), downDrawable.getMinimumHeight());
            tvMore.setCompoundDrawables(downDrawable, null, null, null);
        }
    }


    /**
     * 前往话题中心界面
     */
    @OnClick(R.id.topic_center_layout)
    void startTopicCenterActivity() {
        startActivity(new Intent(getActivity(), TopicCenterActivity.class));
    }


    /**
     * 前往活动中心界面
     */
    @OnClick(R.id.activity_center_layout)
    void startActivityCenterActivity() {
        startActivity(new Intent(getActivity(), ActivityCenterActivity.class));
    }


    /**
     * 前往全区排行榜界面
     */
    @OnClick(R.id.layout_all_rank)
    void startAllRankActivity() {
        startActivity(new Intent(getActivity(), AllAreasRankActivity.class));
    }


    /**
     * 前往原创排行榜界面
     */
    @OnClick(R.id.layout_original)
    void startOriginalRankActivity() {
        startActivity(new Intent(getActivity(), OriginalRankActivity.class));
    }


    /**
     * 前往游戏中心界面
     */
    @OnClick(R.id.layout_game_center)
    void startGameCenterActivity() {
        startActivity(new Intent(getActivity(), GameCentreActivity.class));
    }


    /**
     * 前往搜索界面
     */
    @OnClick(R.id.card_view)
    void startSearchActivity() {
//        startActivity(new Intent(getActivity(), TotalStationSearchActivity.class));
    }


    @OnClick(R.id.layout_shop)
    void startShop() {
        BrowserActivity.launch(getActivity(), ConstantUtil.SHOP_URL, "bilibili - 周边商城");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
