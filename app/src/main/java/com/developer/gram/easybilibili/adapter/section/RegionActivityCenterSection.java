package com.developer.gram.easybilibili.adapter.section;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.RegionActivityCenterAdapter;
import com.developer.gram.easybilibili.bean.region.RegionRecommend;
import com.developer.gram.easybilibili.widget.sectioned.StatelessSection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gram on 2018/1/2.
 */

public class RegionActivityCenterSection extends StatelessSection {
    private Context mContext;
    private List<RegionRecommend.DataBean.BodyBean> bodyBeans;

    public RegionActivityCenterSection(Context context, List<RegionRecommend.DataBean.BodyBean> data) {
        super(R.layout.item_region_header, R.layout.item_region_activity_center, R.layout.item_section_empty);
        this.mContext = context;
        this.bodyBeans = data;
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new RegionActivityCenterHeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        RegionActivityCenterHeaderViewHolder header = (RegionActivityCenterHeaderViewHolder) holder;
        header.tvTitle.setText("活动中心");
        header.ivIcon.setImageResource(R.drawable.ic_header_activity_center);
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new RegionActivityCenterFooterViewHolder(view);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        RegionActivityCenterFooterViewHolder footer = (RegionActivityCenterFooterViewHolder) holder;
        RecyclerView recyclerView = footer.recycler;
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RegionActivityCenterAdapter(mContext, bodyBeans));
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new EmptyViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {}

    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class RegionActivityCenterHeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public RegionActivityCenterHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class RegionActivityCenterFooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recycler)
        RecyclerView recycler;

        public RegionActivityCenterFooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
