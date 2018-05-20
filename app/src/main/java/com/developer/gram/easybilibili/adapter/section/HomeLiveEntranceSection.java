package com.developer.gram.easybilibili.adapter.section;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.bean.live.LiveEnter;
import com.developer.gram.easybilibili.widget.sectioned.StatelessSection;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gram on 2017/12/31.
 */

public class HomeLiveEntranceSection extends StatelessSection {
    private Context mContext;
    private List<LiveEnter> mList = Arrays.asList(
            new LiveEnter("关注", R.drawable.live_home_follow_anchor),
            new LiveEnter("中心", R.drawable.live_home_live_center),
            new LiveEnter("小视频", R.drawable.live_home_clip_video),
            new LiveEnter("搜索", R.drawable.live_home_search_room),
            new LiveEnter("分类", R.drawable.live_home_all_category));

    public HomeLiveEntranceSection(Context context) {
        super(R.layout.item_live_entrance, R.layout.item_section_empty);
        this.mContext = context;
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new LiveEntranceViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        LiveEntranceViewHolder liveEntranceViewHolder = (LiveEntranceViewHolder) holder;
        RecyclerView recyclerView = liveEntranceViewHolder.recycler;
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 5);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new LiveEntranceAdapter(mList));
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new EmptyViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {}

    static class LiveEntranceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recycler)
        RecyclerView recycler;

        public LiveEntranceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class LiveEntranceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<LiveEnter> mList;

        public LiveEntranceAdapter(List<LiveEnter> data) {
            this.mList = data;
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_entrance_sub, null);
            return new LiveSubEntranceViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            LiveSubEntranceViewHolder liveSubEntranceViewHolder = (LiveSubEntranceViewHolder) holder;
            liveSubEntranceViewHolder.ivIcon.setImageResource(mList.get(position).img);
            liveSubEntranceViewHolder.tvTitle.setText(mList.get(position).title);
        }

        static class LiveSubEntranceViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.iv_icon)
            ImageView ivIcon;
            @BindView(R.id.tv_title)
            TextView tvTitle;

            public LiveSubEntranceViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
