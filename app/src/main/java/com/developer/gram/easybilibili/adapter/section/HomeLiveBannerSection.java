package com.developer.gram.easybilibili.adapter.section;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.bean.live.LiveAppIndexInfo;
import com.developer.gram.easybilibili.widget.banner.BannerEntity;
import com.developer.gram.easybilibili.widget.banner.BannerView;
import com.developer.gram.easybilibili.widget.sectioned.StatelessSection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gram on 2017/12/29.
 */

public class HomeLiveBannerSection extends StatelessSection {
    private List<BannerEntity> bannerEntitys = new ArrayList<>();

    public HomeLiveBannerSection(List<LiveAppIndexInfo.DataBean.BannerBean> bannerBeans) {
        super(R.layout.item_banner, R.layout.item_section_empty);
        for(LiveAppIndexInfo.DataBean.BannerBean bannerBean : bannerBeans) {
            bannerEntitys.add(new BannerEntity(
                    bannerBean.getLink(), bannerBean.getTitle(), bannerBean.getImg()));
        }
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new EmptyViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new LiveBannerViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        LiveBannerViewHolder liveBannerViewHolder = (LiveBannerViewHolder) holder;
        liveBannerViewHolder.banner.delayTime(5).build(bannerEntitys);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {}

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class LiveBannerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_live_banner)
        public BannerView banner;

        LiveBannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
