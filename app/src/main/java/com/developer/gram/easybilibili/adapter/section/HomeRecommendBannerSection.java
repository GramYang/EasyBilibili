package com.developer.gram.easybilibili.adapter.section;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.widget.banner.BannerEntity;
import com.developer.gram.easybilibili.widget.banner.BannerView;
import com.developer.gram.easybilibili.widget.sectioned.StatelessSection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hcc on 16/8/27 14:06
 * 100332338@qq.com
 * <p/>
 * 首页推荐界面轮播图Section
 */
public class HomeRecommendBannerSection extends StatelessSection {
    private List<BannerEntity> banners = new ArrayList<>();

    public HomeRecommendBannerSection(List<BannerEntity> banners) {
        super(R.layout.item_banner, R.layout.item_section_empty);
        this.banners = banners;
    }


    @Override
    public int getContentItemsTotal() {
        return 1;
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new BannerViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
        bannerViewHolder.itemLiveBanner.delayTime(5).build(banners);
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_live_banner)
        BannerView itemLiveBanner;

        BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
