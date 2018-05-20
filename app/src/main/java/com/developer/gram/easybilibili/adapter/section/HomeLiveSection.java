package com.developer.gram.easybilibili.adapter.section;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.bean.live.LiveAppIndexInfo;
import com.developer.gram.easybilibili.util.NumberUtils;
import com.developer.gram.easybilibili.util.SpanUtils;
import com.developer.gram.easybilibili.util.ToastUtils;
import com.developer.gram.easybilibili.widget.sectioned.StatelessSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gram on 2018/1/1.
 */

public class HomeLiveSection extends StatelessSection {
    private Context mContext;
    private String mUrl;
    private String mTitle;
    private Random mRandom;
    private String mCount;
    private boolean mHasMore;
    private List<LiveAppIndexInfo.DataBean.PartitionsBean.LivesBean> livesBeans = new ArrayList<>();

    public HomeLiveSection(Context context, boolean hasMore, String title, String url, String count, List<LiveAppIndexInfo.DataBean.PartitionsBean.LivesBean> data) {
        super(R.layout.item_live_recommend_header, R.layout.item_live_recommend_footer, R.layout.item_live_recommend_body);
        this.mHasMore = hasMore;
        this.mTitle = title;
        this.mUrl = url;
        this.mCount = count;
        this.livesBeans = data;
        this.mContext = context;
        this.mRandom = new Random();
    }

    @Override
    public int getContentItemsTotal() {
        return livesBeans.size();
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new LiveHeaderViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new LiveBodyViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new LiveFooterViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        LiveHeaderViewHolder header = (LiveHeaderViewHolder) holder;
        Glide.with(mContext)
                .load(mUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into((ImageView) header.ivIcon);
        header.tvTitle.setText(mTitle);
        header.tvOnline.setText(new SpanUtils().append("当前")
                .append("" + mCount)
                .setForegroundColor(mContext.getResources().getColor(R.color.pink_text_color))
                .append("个直播")
                .create());
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        LiveBodyViewHolder body = (LiveBodyViewHolder) holder;
        LiveAppIndexInfo.DataBean.PartitionsBean.LivesBean livesBean = livesBeans.get(position);
        Glide.with(mContext)
                .load(livesBean.getCover().getSrc())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into((ImageView) body.ivVideoPreview);
        body.tvVideoLiveUp.setText(livesBean.getOwner().getName());
        body.tvVideoOnline.setText(NumberUtils.format(livesBean.getOnline() + ""));
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) body.itemView.getLayoutParams();
        if (position % 2 == 0) {
            params.setMargins(10, 5, 5, 5);
        } else {
            params.setMargins(5, 5, 10, 5);
        }
        body.itemView.setLayoutParams(params);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        LiveFooterViewHolder footer = (LiveFooterViewHolder) holder;
        if (mHasMore) {
            footer.btMoreLive.setVisibility(View.VISIBLE);
        } else {
            footer.btMoreLive.setVisibility(View.GONE);
            footer.btMoreLive.setOnClickListener(v ->
                ToastUtils.showSingleToast("未实现"));
        }
        footer.tvDynamic.setText(String.valueOf(mRandom.nextInt(200) + "条新动态，点击这里刷新"));
        footer.ivRefresh.setOnClickListener(v -> v.animate()
                .rotation(360)
                .setInterpolator(new LinearInterpolator())
                .setDuration(1000).start());
    }

    static class LiveHeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_online)
        TextView tvOnline;

        public LiveHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class LiveBodyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_video_preview)
        ImageView ivVideoPreview;
        @BindView(R.id.tv_video_live_up)
        TextView tvVideoLiveUp;
        @BindView(R.id.tv_video_online)
        TextView tvVideoOnline;

        public LiveBodyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class LiveFooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_dynamic)
        TextView tvDynamic;
        @BindView(R.id.iv_refresh)
        ImageView ivRefresh;
        @BindView(R.id.bt_more_live)
        Button btMoreLive;

        public LiveFooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
