package com.developer.gram.easybilibili.adapter.section;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.function.common.BrowserActivity;
import com.developer.gram.easybilibili.widget.sectioned.StatelessSection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hcc on 16/8/27 20:19
 * 100332338@qq.com
 * <p/>
 * 首页推荐界面话题section
 */
public class HomeRecommendTopicSection extends StatelessSection {
    private Context mContext;
    private String imgUrl;
    private String title;
    private String link;

    public HomeRecommendTopicSection(Context context, String imgUrl, String title, String link) {
        super(R.layout.item_recommend_topic, R.layout.item_section_empty);
        this.mContext = context;
        this.imgUrl = imgUrl;
        this.title = title;
        this.link = link;
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new EmptyViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new TopicViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        TopicViewHolder topicViewHolder = (TopicViewHolder) holder;
        Glide.with(mContext)
                .load(imgUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(topicViewHolder.topicImage);

        topicViewHolder.topicTitle.setText(title);
        if (TextUtils.isEmpty(title)) {
            topicViewHolder.topicTitle.setVisibility(View.GONE);
        } else {
            topicViewHolder.topicTitle.setVisibility(View.VISIBLE);
        }
        topicViewHolder.cardView.setOnClickListener(v -> BrowserActivity.
                launch((Activity) mContext, link, title));
    }


    static class TopicViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.topic_image)
        ImageView topicImage;
        @BindView(R.id.topic_title)
        TextView topicTitle;
        @BindView(R.id.card_view)
        CardView cardView;

        TopicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
