package com.developer.gram.easybilibili.adapter.section;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.HomeRegionItemAdapter;
import com.developer.gram.easybilibili.bean.region.RegionTypesInfo;
import com.developer.gram.easybilibili.function.entry.GameCentreActivity;
import com.developer.gram.easybilibili.function.home.region.AdvertisingActivity;
import com.developer.gram.easybilibili.function.home.region.LiveAppIndexActivity;
import com.developer.gram.easybilibili.function.home.region.RegionTypeDetailsActivity;
import com.developer.gram.easybilibili.widget.sectioned.StatelessSection;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gram on 2018/1/2.
 */

public class RegionEntranceSection extends StatelessSection {
    private Context mContext;
    private List<RegionTypesInfo.DataBean> dataBeans;

    public RegionEntranceSection(Context context, List<RegionTypesInfo.DataBean> data) {
        super(R.layout.item_region_entrance, R.layout.item_section_empty);
        this.mContext = context;
        this.dataBeans = data;
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new RegionHeaderViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new EmptyViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        RegionHeaderViewHolder header = (RegionHeaderViewHolder) holder;
        RecyclerView recyclerView = header.recycler;
        recyclerView.setHasFixedSize(false);
        //不能嵌套滑动
        recyclerView.setNestedScrollingEnabled(false);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 4);
        recyclerView.setLayoutManager(layoutManager);
        HomeRegionItemAdapter adapter = new HomeRegionItemAdapter(recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((position, holder1) -> {
            switch (position) {
                case 0:
                    //直播
                    mContext.startActivity(new Intent(mContext, LiveAppIndexActivity.class));
                    break;
                case 1:
                    //番剧
                    RegionTypesInfo.DataBean mBangumi = dataBeans.get(1);
                    RegionTypeDetailsActivity.launch(mContext, mBangumi);
                    break;
                case 2:
                    //动画
                    RegionTypesInfo.DataBean mAnimation = dataBeans.get(2);
                    RegionTypeDetailsActivity.launch(mContext, mAnimation);
                    break;
                case 3:
                    //音乐
                    RegionTypesInfo.DataBean mMuise = dataBeans.get(3);
                    RegionTypeDetailsActivity.launch(mContext, mMuise);
                    break;
                case 4:
                    //舞蹈
                    RegionTypesInfo.DataBean mDence = dataBeans.get(4);
                    RegionTypeDetailsActivity.launch(mContext, mDence);
                    break;
                case 5:
                    //游戏
                    RegionTypesInfo.DataBean mGame = dataBeans.get(5);
                    RegionTypeDetailsActivity.launch(mContext, mGame);
                    break;
                case 6:
                    //科技
                    RegionTypesInfo.DataBean mScience = dataBeans.get(6);
                    RegionTypeDetailsActivity.launch(mContext, mScience);
                    break;
                case 7:
                    //生活
                    RegionTypesInfo.DataBean mLife = dataBeans.get(7);
                    RegionTypeDetailsActivity.launch(mContext, mLife);
                    break;
                case 8:
                    //鬼畜
                    RegionTypesInfo.DataBean mKichiku = dataBeans.get(8);
                    RegionTypeDetailsActivity.launch(mContext, mKichiku);
                    break;
                case 9:
                    //时尚
                    RegionTypesInfo.DataBean mFashion = dataBeans.get(9);
                    RegionTypeDetailsActivity.launch(mContext, mFashion);
                    break;
                case 10:
                    //广告
                    mContext.startActivity(new Intent(mContext, AdvertisingActivity.class));
                    break;
                case 11:
                    //娱乐
                    RegionTypesInfo.DataBean mRecreation = dataBeans.get(10);
                    RegionTypeDetailsActivity.launch(mContext, mRecreation);
                    break;
                case 12:
                    //电影
                    RegionTypesInfo.DataBean mMovei = dataBeans.get(11);
                    RegionTypeDetailsActivity.launch(mContext, mMovei);
                    break;
                case 13:
                    //电视剧
                    RegionTypesInfo.DataBean mTv = dataBeans.get(12);
                    RegionTypeDetailsActivity.launch(mContext, mTv);
                    break;
                case 14:
                    // 游戏中心
                    mContext.startActivity(new Intent(mContext, GameCentreActivity.class));
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {}

    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class RegionHeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recycler)
        RecyclerView recycler;

        RegionHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
