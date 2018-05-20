package com.developer.gram.easybilibili.adapter.section;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.bean.region.RegionRecommend;
import com.developer.gram.easybilibili.widget.sectioned.StatelessSection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gram on 2018/1/2.
 */

public class RegionTopicSection extends StatelessSection {
    private RegionRecommend.DataBean.BodyBean bodyBean;
    private Context mContext;

    public RegionTopicSection(Context context, RegionRecommend.DataBean.BodyBean data) {
        super(R.layout.item_region_topic, R.layout.item_section_empty);
        this.mContext = context;
        this.bodyBean = data;
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new RegionTopicHeaderViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new EmptyViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        RegionTopicHeaderViewHolder header = (RegionTopicHeaderViewHolder) holder;
        header.tvTitle.setText("话题");
        header.ivIcon.setImageResource(R.drawable.ic_header_topic);
        Glide.with(mContext)
                .load(bodyBean.getCover())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into((ImageView) header.ivVideoPreview);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {}

    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class RegionTopicHeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_video_preview)
        ImageView ivVideoPreview;

        public RegionTopicHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
