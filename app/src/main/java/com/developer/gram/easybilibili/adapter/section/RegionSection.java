package com.developer.gram.easybilibili.adapter.section;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.bean.region.RegionRecommend;
import com.developer.gram.easybilibili.function.entry.GameCentreActivity;
import com.developer.gram.easybilibili.function.home.bangumi.BangumiDetailsActivity;
import com.developer.gram.easybilibili.util.NumberUtils;
import com.developer.gram.easybilibili.widget.sectioned.StatelessSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gram on 2018/1/3.
 */

public class RegionSection extends StatelessSection {
    private Context mContext;
    private String mTitle;
    private List<RegionRecommend.DataBean.BodyBean> bodyBeans = new ArrayList<>();
    private Random mRandom;

    public RegionSection(Context context, String title, List<RegionRecommend.DataBean.BodyBean> data) {
        super(R.layout.item_region_header, R.layout.item_region_footer, R.layout.item_region_body);
        this.mContext = context;
        this.mTitle = title;
        this.bodyBeans = data;
        mRandom = new Random();
    }

    @Override
    public int getContentItemsTotal() {
        return bodyBeans.size();
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new RegionHeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        RegionHeaderViewHolder header = (RegionHeaderViewHolder) holder;
        //设置标题图片
        setTypeIcon(header, mTitle);
        header.tvTitle.setText(mTitle);
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new RegionFooterViewHolder(view);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        RegionFooterViewHolder footer = (RegionFooterViewHolder) holder;
        setButtonMore(footer, mTitle);
        if (TextUtils.equals("游戏区", mTitle)) {
            footer.btMore.setVisibility(View.GONE);
            footer.btMoreGame.setVisibility(View.VISIBLE);
            footer.btGameCenter.setVisibility(View.VISIBLE);
            //跳转到游戏中心
            footer.btGameCenter.setOnClickListener(view -> mContext.startActivity(new Intent(mContext, GameCentreActivity.class)));
        } else {
            footer.btMore.setVisibility(View.VISIBLE);
            footer.btMoreGame.setVisibility(View.GONE);
            footer.btGameCenter.setVisibility(View.GONE);
        }
        footer.tvDynamic.setText(String.valueOf(mRandom.nextInt(200) + "条新动态，点击这里刷新"));
        footer.ivRefresh.setOnClickListener(view ->
                view.animate()
                        .rotation(360)
                        .setInterpolator(new LinearInterpolator())
                        .setDuration(1000).start());
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new RegionItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        RegionItemViewHolder body = (RegionItemViewHolder) holder;
        RegionRecommend.DataBean.BodyBean bodyBean = bodyBeans.get(position);
        Glide.with(mContext)
                .load(bodyBean.getCover())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into((ImageView) body.ivVideoPreview);
        body.tvVideoTitle.setText(bodyBean.getTitle());
        body.tvVideoPlayNum.setText(NumberUtils.format(bodyBean.getPlay() + ""));
        if (TextUtils.equals("番剧区", mTitle)) {
            body.ivVideoOnlineRegion.setVisibility(View.GONE);
            body.ivVideoOnline.setVisibility(View.VISIBLE);
            body.tvVideoFavourite.setText(NumberUtils.format(bodyBean.getFavourite() + ""));
        } else {
            body.ivVideoOnlineRegion.setVisibility(View.VISIBLE);
            body.ivVideoOnline.setVisibility(View.GONE);
            body.tvVideoFavourite.setText(NumberUtils.format(bodyBean.getDanmaku() + ""));
        }
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) body.itemView.getLayoutParams();
        if (position % 2 == 0) {
            params.setMargins(10, 5, 5, 5);
        } else {
            params.setMargins(5, 5, 10, 5);
        }
        body.itemView.setLayoutParams(params);
        body.itemView.setOnClickListener(view -> mContext.startActivity(new Intent(mContext, BangumiDetailsActivity.class)));
    }

    private void setTypeIcon(RegionHeaderViewHolder header, String title) {
        switch (title) {
            case "直播区":
                header.ivIcon.setImageResource(R.drawable.ic_category_live);
                break;
            case "番剧区":
                header.ivIcon.setImageResource(R.drawable.ic_category_t13);
                break;
            case "动画区":
                header.ivIcon.setImageResource(R.drawable.ic_category_t1);
                break;
            case "音乐区":
                header.ivIcon.setImageResource(R.drawable.ic_category_t3);
                break;
            case "舞蹈区":
                header.ivIcon.setImageResource(R.drawable.ic_category_t129);
                break;
            case "游戏区":
                header.ivIcon.setImageResource(R.drawable.ic_category_t4);
                break;
            case "科技区":
                header.ivIcon.setImageResource(R.drawable.ic_category_t36);
                break;
            case "生活区":
                header.ivIcon.setImageResource(R.drawable.ic_category_t160);
                break;
            case "鬼畜区":
                header.ivIcon.setImageResource(R.drawable.ic_category_t13);
                break;
            case "时尚区":
                header.ivIcon.setImageResource(R.drawable.ic_category_t155);
                break;
            case "广告区":
                header.ivIcon.setImageResource(R.drawable.ic_category_t165);
                break;
            case "娱乐区":
                header.ivIcon.setImageResource(R.drawable.ic_category_t5);
                break;
            case "电影区":
                header.ivIcon.setImageResource(R.drawable.ic_category_t23);
                break;
            case "电视剧区":
                header.ivIcon.setImageResource(R.drawable.ic_category_t11);
                break;
            case "游戏中心区":
                header.ivIcon.setImageResource(R.drawable.ic_category_game_center);
        }
    }

    private void setButtonMore(RegionFooterViewHolder footer, String title) {
        switch (title) {
            case "直播区":
                footer.btMore.setText("更多直播");
                break;
            case "番剧区":
                footer.btMore.setText("更多番剧");
                break;
            case "动画区":
                footer.btMore.setText("更多动画");
                break;
            case "音乐区":
                footer.btMore.setText("更多音乐");
                break;
            case "舞蹈区":
                footer.btMore.setText("更多舞蹈");
                break;
            case "游戏区":
                footer.btMore.setText("更多游戏");
                break;
            case "科技区":
                footer.btMore.setText("更多科技");
                break;
            case "生活区":
                footer.btMore.setText("更多生活");
                break;
            case "鬼畜区":
                footer.btMore.setText("更多鬼畜");
                break;
            case "时尚区":
                footer.btMore.setText("更多时尚");
                break;
            case "广告区":
                footer.btMore.setText("更多广告");
                break;
            case "娱乐区":
                footer.btMore.setText("更多娱乐");
                break;
            case "电影区":
                footer.btMore.setText("更多电影");
                break;
            case "电视剧区":
                footer.btMore.setText("更多电视剧");
                break;
            case "游戏中心区":
                footer.btMore.setText("更多运营游戏");
        }
    }

    static class RegionHeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        RegionHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class RegionFooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bt_more)
        Button btMore;
        @BindView(R.id.bt_game_center)
        Button btGameCenter;
        @BindView(R.id.bt_more_game)
        Button btMoreGame;
        @BindView(R.id.tv_dynamic)
        TextView tvDynamic;
        @BindView(R.id.iv_refresh)
        ImageView ivRefresh;

        RegionFooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class RegionItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_video_preview)
        ImageView ivVideoPreview;
        @BindView(R.id.tv_video_title)
        TextView tvVideoTitle;
        @BindView(R.id.tv_video_play_num)
        TextView tvVideoPlayNum;
        @BindView(R.id.iv_video_online)
        ImageView ivVideoOnline;
        @BindView(R.id.iv_video_online_region)
        ImageView ivVideoOnlineRegion;
        @BindView(R.id.tv_video_favourite)
        TextView tvVideoFavourite;

        RegionItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
