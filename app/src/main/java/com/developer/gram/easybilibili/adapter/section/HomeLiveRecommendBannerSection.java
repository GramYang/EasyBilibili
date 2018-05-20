package com.developer.gram.easybilibili.adapter.section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.bean.live.LiveAppIndexInfo;
import com.developer.gram.easybilibili.util.SpanUtils;
import com.developer.gram.easybilibili.widget.sectioned.StatelessSection;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gram on 2018/1/1.
 */

public class HomeLiveRecommendBannerSection extends StatelessSection {
    private LiveAppIndexInfo.DataBean.RecommendDataBean.BannerDataBean recommendBanner;
    private Context mContext;

    public HomeLiveRecommendBannerSection(Context context, LiveAppIndexInfo.DataBean.RecommendDataBean.BannerDataBean bannerDataBean) {
        super(R.layout.item_live_recommend_body, R.layout.item_section_empty);
        this.recommendBanner = bannerDataBean;
        this.mContext = context;
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new LiveRecommendBannerViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new EmptyViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        LiveRecommendBannerViewHolder bannerViewHolder = (LiveRecommendBannerViewHolder) holder;
        Glide.with(mContext)
                .load(recommendBanner.getCover().getSrc())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into((ImageView) bannerViewHolder.ivVideoPreview);
        bannerViewHolder.tvVideoLiveUp.setText(recommendBanner.getTitle());
        bannerViewHolder.tvVideoOnline.setText(recommendBanner.getOnline() + "");
        bannerViewHolder.tvVideoTitle.setText(new SpanUtils()
                .append("#" + recommendBanner.getArea() + "#")
                .append(recommendBanner.getTitle())
                .setForegroundColor(mContext.getResources().getColor(R.color.pink_text_color))
                .create());
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) bannerViewHolder.itemView.getLayoutParams();
        params.setMargins(10, 5, 10, 5);
        bannerViewHolder.itemView.setLayoutParams(params);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {}

    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class LiveRecommendBannerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_video_preview)
        ImageView ivVideoPreview;
        @BindView(R.id.tv_video_title)
        TextView tvVideoTitle;
        @BindView(R.id.tv_video_live_up)
        TextView tvVideoLiveUp;
        @BindView(R.id.tv_video_online)
        TextView tvVideoOnline;
        public LiveRecommendBannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
