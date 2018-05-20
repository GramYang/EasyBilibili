package com.developer.gram.easybilibili.adapter.section;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.function.home.bangumi.BangumiIndexActivity;
import com.developer.gram.easybilibili.function.home.bangumi.BangumiScheduleActivity;
import com.developer.gram.easybilibili.widget.sectioned.StatelessSection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hcc on 2016/10/14 19:15
 * 100332338@qq.com
 * <p>
 * 首页番剧顶部追番，放送表，索引条目Section
 */

public class HomeBangumiItemSection extends StatelessSection {
    private Context mContext;

    public HomeBangumiItemSection(Context context) {
        super(R.layout.item_bangumi_entrance, R.layout.item_section_empty);
        this.mContext = context;
    }


    @Override
    public int getContentItemsTotal() {
        return 1;
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new HomeBangumiItemSection.EmptyViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HomeBangumiItemSection.TopItemViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HomeBangumiItemSection.TopItemViewHolder topItemViewHolder
                = (HomeBangumiItemSection.TopItemViewHolder) holder;
        //前往追番
        topItemViewHolder.mChaseBangumi.setOnClickListener(v -> {
        });
        //前往番剧放送表
        topItemViewHolder.mBangumiSchedule.setOnClickListener(v -> mContext.startActivity(
                new Intent(mContext, BangumiScheduleActivity.class)));
        //前往番剧索引
        topItemViewHolder.mBangumiIndex.setOnClickListener(v -> mContext.startActivity(
                new Intent(mContext, BangumiIndexActivity.class)));
    }


    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class TopItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_chase_bangumi)
        TextView mChaseBangumi;
        @BindView(R.id.layout_bangumi_schedule)
        TextView mBangumiSchedule;
        @BindView(R.id.layout_bangumi_index)
        TextView mBangumiIndex;

        TopItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
