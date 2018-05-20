package com.developer.gram.easybilibili.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.bean.attention.Attention;
import com.developer.gram.easybilibili.bean.tree.TreeItem;
import com.developer.gram.easybilibili.function.video.VideoDetailActivity;
import com.developer.gram.easybilibili.util.EmptyUtils;
import com.developer.gram.easybilibili.util.NumberUtils;
import com.developer.gram.easybilibili.util.time.FormatUtils;
import com.developer.gram.easybilibili.util.time.TimeUtils;
import com.developer.gram.easybilibili.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gram on 2018/1/3.
 *
 */

public class AttentionAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<TreeItem> treeItems = new ArrayList<>();

    public AttentionAdapter(Context context, List<TreeItem> data) {
        this.mContext = context;
        this.treeItems = data;
    }

    @Override
    public int getItemViewType(int position) {
        return treeItems.get(position).getItemType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_attention, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder item = (ItemViewHolder) holder;
        TreeItem tree = treeItems.get(position);
        //item点击跳转
        item.itemView.setOnClickListener(view -> mContext.startActivity(new Intent(mContext, VideoDetailActivity.class)));
        //设置两种viewType的UI
        switch(tree.getItemType()) {
            case TreeItem.TYPE_LV0:
                Attention.ItemBean itemBean = tree.getGroup();
                //flag为true才有flRecent布局
                if(tree.isFlag()) {
                    item.flRecent.setVisibility(View.VISIBLE);
                    item.tvRecent.setText("还有" + itemBean.recent_count + "个视频被隐藏");
                } else {
                    item.flRecent.setVisibility(View.GONE);
                }
                item.flRecent.setOnClickListener(v -> {
                    tree.setFlag(false); //点击后需要隐藏recent布局
                    notifyItemChanged(position); //当前item需要变更UI
                    expand(position); //插入其子recent
                });

                //根据itemBean中的类型type来设置UI
                switch(itemBean.type) {
                    case 0://关注up
                        item.ivAvatar.setVisibility(View.VISIBLE);
                        item.tvTag.setVisibility(View.GONE);
                        item.tvTitleTime.setVisibility(View.VISIBLE);
                        item.tvTitleTagTime.setVisibility(View.GONE);
                        item.tvTitle.setVisibility(View.VISIBLE);

                        item.tvTitleTime.setText(TimeUtils.getFriendlyTimeSpanByNow((long) (itemBean.ctime * Math.pow(10, 3))));
                        item.tvTitle.setText(itemBean.name);
                        item.tvVideoTitle.setText(itemBean.title);
                        item.tvDuration.setText(FormatUtils.formatDuration(itemBean.duration + ""));
                        item.tvDuration.setVisibility(View.VISIBLE);
                        item.ivVideoPlayNum.setVisibility(View.VISIBLE);

                        item.tvVideoPlayNum.setVisibility(View.VISIBLE);
                        item.tvVideoFavourite.setVisibility(View.VISIBLE);
                        item.ivVideoOnlineRegion.setVisibility(View.VISIBLE);
                        item.tvVideoPlayNum.setText(NumberUtils.format(itemBean.play + ""));
                        item.tvVideoFavourite.setText(NumberUtils.format(itemBean.favorite + ""));

                        item.ivTagVideoPlayNum.setVisibility(View.GONE);
                        item.tvTagVideoFavourite.setVisibility(View.GONE);
                        item.ivTagVideoOnlineRegion.setVisibility(View.GONE);
                        item.tvTagVideoPlayNum.setText(itemBean.tname + (itemBean.tag == null ? "" : " · " + itemBean.tag.tag_name));
                        Glide.with(mContext)
                                .load(itemBean.face)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.bili_default_avatar)
                                .dontAnimate()
                                .into((CircleImageView) item.ivAvatar);
                        Glide.with(mContext)
                                .load(itemBean.cover)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.bili_default_image_tv)
                                .dontAnimate()
                                .into((ImageView) item.ivPreview);
                        break;
                    case 2: //国产动画
                        item.ivAvatar.setVisibility(View.GONE);
                        item.tvTitle.setVisibility(View.GONE);
                        item.tvTitleTime.setVisibility(View.GONE);
                        item.tvTitleTagTime.setVisibility(View.VISIBLE);
                        item.tvDuration.setVisibility(View.GONE);

                        item.tvTitleTagTime.setText(TimeUtils.getFriendlyTimeSpanByNow((long) (itemBean.ctime * Math.pow(10, 3))));
                        item.tvTag.setVisibility(View.VISIBLE);
                        item.tvTag.setText("国产动画");
                        item.tvTag.setBackgroundColor(mContext.getResources().getColor(R.color.yellow_30));
                        item.tvVideoTitle.setText(itemBean.title);

                        item.ivVideoPlayNum.setVisibility(View.GONE);
                        item.tvVideoPlayNum.setVisibility(View.VISIBLE);
                        item.tvVideoFavourite.setVisibility(View.GONE);
                        item.ivVideoOnlineRegion.setVisibility(View.GONE);
                        item.tvVideoPlayNum.setText("第" + itemBean.index + "话" + " " + itemBean.index_title);

                        item.tvTagVideoPlayNum.setText(NumberUtils.format(itemBean.play + ""));
                        item.ivTagVideoPlayNum.setVisibility(View.VISIBLE);
                        item.tvTagVideoPlayNum.setVisibility(View.VISIBLE);
                        item.ivTagVideoOnlineRegion.setVisibility(View.VISIBLE);
                        item.tvTagVideoFavourite.setVisibility(View.VISIBLE);

                        item.tvTagVideoFavourite.setText(NumberUtils.format(itemBean.danmaku + ""));
                        Glide.with(mContext)
                                .load(itemBean.cover)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.bili_default_image_tv)
                                .dontAnimate()
                                .into((ImageView) item.ivPreview);
                        break;
                    case 1: //可能是番剧 不知道参数意思
                        item.ivAvatar.setVisibility(View.GONE);
                        item.tvTag.setVisibility(View.VISIBLE);
                        item.tvTitle.setVisibility(View.GONE);
                        item.tvTitleTime.setVisibility(View.GONE);
                        item.tvTitleTagTime.setVisibility(View.VISIBLE);

                        item.tvDuration.setVisibility(View.GONE);
                        item.tvTitleTagTime.setText(TimeUtils.getFriendlyTimeSpanByNow((long) (itemBean.ctime * Math.pow(10, 3))));
                        item.tvTag.setText("番剧");
                        item.tvTag.setBackgroundColor(mContext.getResources().getColor(R.color.pink_text_color));
                        item.tvVideoTitle.setText(itemBean.title);

                        item.ivVideoPlayNum.setVisibility(View.GONE);
                        item.tvVideoPlayNum.setVisibility(View.VISIBLE);
                        item.tvVideoFavourite.setVisibility(View.GONE);
                        item.ivVideoOnlineRegion.setVisibility(View.GONE);
                        item.tvVideoPlayNum.setText("第" + itemBean.index + "话" + " " + itemBean.index_title);

                        item.tvTagVideoPlayNum.setText(NumberUtils.format(itemBean.play + ""));
                        item.ivTagVideoPlayNum.setVisibility(View.VISIBLE);
                        item.tvTagVideoPlayNum.setVisibility(View.VISIBLE);
                        item.ivTagVideoOnlineRegion.setVisibility(View.VISIBLE);
                        item.tvTagVideoFavourite.setVisibility(View.VISIBLE);

                        item.tvTagVideoFavourite.setText(NumberUtils.format(itemBean.danmaku + ""));
                        Glide.with(mContext)
                                .load(itemBean.cover)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.bili_default_image_tv)
                                .dontAnimate()
                                .into((ImageView) item.ivPreview);
                        break;
                }
                break;
            case TreeItem.TYPE_LV1:
                Attention.ItemBean.RecentBean recent = tree.getRecent();
                item.ivAvatar.setVisibility(View.VISIBLE);
                item.tvTag.setVisibility(View.GONE);
                item.tvTitle.setText(recent.name);
                item.tvTitleTime.setText(TimeUtils.getFriendlyTimeSpanByNow((long) (recent.ctime * Math.pow(10, 3))));
                item.flRecent.setVisibility(View.GONE);

                item.tvVideoTitle.setText(recent.title);
                item.tvDuration.setText(FormatUtils.formatDuration(recent.duration + ""));
                item.tvDuration.setVisibility(View.VISIBLE);
                item.ivVideoPlayNum.setVisibility(View.VISIBLE);
                item.tvVideoPlayNum.setVisibility(View.VISIBLE);

                item.tvVideoFavourite.setVisibility(View.VISIBLE);
                item.ivVideoOnlineRegion.setVisibility(View.VISIBLE);
                item.tvVideoPlayNum.setText(" " + NumberUtils.format(recent.play + ""));
                item.tvVideoFavourite.setText(" " + NumberUtils.format(recent.favorite + ""));
                item.ivTagVideoPlayNum.setVisibility(View.GONE);

                item.tvTagVideoFavourite.setVisibility(View.GONE);
                item.ivVideoOnlineRegion.setVisibility(View.GONE);
                item.tvTagVideoPlayNum.setText(recent.tname + (recent.tag == null ? "" : " · " + recent.tag.tag_name));
                Glide.with(mContext)
                        .load(recent.face)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.bili_default_image_tv)
                        .dontAnimate()
                        .into((CircleImageView) item.ivAvatar);
                Glide.with(mContext)
                        .load(recent.cover)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.bili_default_image_tv)
                        .dontAnimate()
                        .into((ImageView) item.ivPreview);
        }

    }

    private void expand(int pos) {
        TreeItem tree = treeItems.get(pos);
        List list = tree.getSubItems();
        treeItems.addAll(pos + 1, list); //将subItems插入到treeItems的position的下一个位置
        notifyDataSetChanged(); //刷新UI
    }

    @Override
    public int getItemCount() {
        return treeItems.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_avatar)
        CircleImageView ivAvatar;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_title_time)
        TextView tvTitleTime;
        @BindView(R.id.tv_title_tag_time)
        TextView tvTitleTagTime;
        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.iv_preview)
        ImageView ivPreview;
        @BindView(R.id.tv_duration)
        TextView tvDuration;
        @BindView(R.id.tv_video_title)
        TextView tvVideoTitle;
        @BindView(R.id.iv_video_play_num)
        ImageView ivVideoPlayNum;
        @BindView(R.id.tv_video_play_num)
        TextView tvVideoPlayNum;
        @BindView(R.id.iv_video_online_region)
        ImageView ivVideoOnlineRegion;
        @BindView(R.id.tv_video_favourite)
        TextView tvVideoFavourite;
        @BindView(R.id.iv_tag_video_play_num)
        ImageView ivTagVideoPlayNum;
        @BindView(R.id.tv_tag_video_play_num)
        TextView tvTagVideoPlayNum;
        @BindView(R.id.iv_tag_video_online_region)
        ImageView ivTagVideoOnlineRegion;
        @BindView(R.id.tv_tag_video_favourite)
        TextView tvTagVideoFavourite;
        @BindView(R.id.tv_recent)
        TextView tvRecent;
        @BindView(R.id.fl_recent)
        RelativeLayout flRecent;
        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
