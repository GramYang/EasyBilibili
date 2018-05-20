package com.developer.gram.easybilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.bean.region.RegionRecommend;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gram on 2018/1/2.
 */

public class RegionActivityCenterAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<RegionRecommend.DataBean.BodyBean> bodyBeans = new ArrayList<>();

    public RegionActivityCenterAdapter(Context context, List<RegionRecommend.DataBean.BodyBean> data) {
        this.mContext = context;
        this.bodyBeans = data;
    }

    @Override
    public int getItemCount() {
        return bodyBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_region_activity_center_sub, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder item = (ItemViewHolder) holder;
        RegionRecommend.DataBean.BodyBean bodyBean = bodyBeans.get(position);
        Glide.with(mContext)
                .load(bodyBean.getCover())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into((ImageView) item.ivVideoPreview);
        item.tvVideoTitle.setText(bodyBean.getTitle());
        if (position == getItemCount() - 1) {
            item.space.setVisibility(View.VISIBLE);
        } else {
            item.space.setVisibility(View.GONE);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_video_preview)
        ImageView ivVideoPreview;
        @BindView(R.id.tv_video_title)
        TextView tvVideoTitle;
        @BindView(R.id.space)
        Space space;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
