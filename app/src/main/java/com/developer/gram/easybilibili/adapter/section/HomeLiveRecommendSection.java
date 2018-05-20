package com.developer.gram.easybilibili.adapter.section;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.bean.live.LiveAppIndexInfo;
import com.developer.gram.easybilibili.util.NumberUtils;
import com.developer.gram.easybilibili.util.SpanUtils;
import com.developer.gram.easybilibili.util.ToastUtils;
import com.developer.gram.easybilibili.widget.sectioned.StatelessSection;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gram on 2017/12/31.
 */

public class HomeLiveRecommendSection extends StatelessSection {
    private boolean mHasHead;
    private boolean mHasFooter;
    private String mUrl;
    private String mTitle;
    private Random mRandom;
    private String mCount;
    private List<LiveAppIndexInfo.DataBean.RecommendDataBean.LivesBean> mLiveBeans;
    private LiveAppIndexInfo.DataBean.RecommendDataBean.BannerDataBean mBannerDataBean;
    private Context mContext;

    public HomeLiveRecommendSection(Context context, boolean hasHead, boolean hasFooter, String title, String url, String count,
                                    List<LiveAppIndexInfo.DataBean.RecommendDataBean.LivesBean> livesBeans) {
        super(R.layout.item_live_recommend_header, R.layout.item_live_recommend_footer, R.layout.item_live_recommend_body);
        this.mHasFooter = hasFooter;
        this.mHasHead = hasHead;
        this.mUrl = url;
        this.mTitle = title;
        this.mCount = count;
        this.mRandom = new Random();
        this.mLiveBeans = livesBeans;
        this.mContext = context;
    }

    public HomeLiveRecommendSection(Context context, boolean hasHead, boolean hasFooter, String title, String url, String count,
                                    List<LiveAppIndexInfo.DataBean.RecommendDataBean.LivesBean> livesBeans,
                                    LiveAppIndexInfo.DataBean.RecommendDataBean.BannerDataBean bannerDataBean) {
        this(context, hasHead, hasFooter, title, url, count, livesBeans);
        this.mBannerDataBean = bannerDataBean;
    }

    @Override
    public int getContentItemsTotal() {
        return mLiveBeans == null ? 1 : mLiveBeans.size();
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new LiveRecommendHeaderViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new LiveRecommendBodyViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new LiveRecommendFooterViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        LiveRecommendHeaderViewHolder headerViewHolder = (LiveRecommendHeaderViewHolder) holder;
        if(mHasHead) {
            headerViewHolder.clTypeRoot.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(mUrl)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into((ImageView) headerViewHolder.ivIcon);
            headerViewHolder.tvTitle.setText(mTitle);
            //将显示的当前xxx个直播中的mCount改变其颜色
            headerViewHolder.tvOnline.setText(new SpanUtils().append("当前")
                    .append("" + mCount)
                    .setForegroundColor(mContext.getResources().getColor(R.color.pink_text_color))
                    .append("个直播")
                    .create());
            //设置推荐直播里的banner在header中
            if(mBannerDataBean != null) {
                headerViewHolder.clVideoRoot.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(mBannerDataBean.getCover().getSrc())
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .into((ImageView) headerViewHolder.ivVideoPreview);
                headerViewHolder.tvVideoLiveUp.setText(mBannerDataBean.getOwner().getName());
                headerViewHolder.tvVideoOnline.setText(mBannerDataBean.getOnline() + "");
                headerViewHolder.tvVideoTitle.setText(new SpanUtils().append("#" + mBannerDataBean.getArea() + "#")
                        .setForegroundColor(mContext.getResources().getColor(R.color.pink_text_color))
                        .append(mBannerDataBean.getTitle())
                        .create());
            } else {
                headerViewHolder.cardView.setVisibility(View.GONE);
            }
        } else {
            //没有header就把header隐藏
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, 0);
            headerViewHolder.itemView.setLayoutParams(params);
        }
        //header点击事件，因为没有跳转数据，所以只toast
        headerViewHolder.itemView.setOnClickListener(v -> {
            ToastUtils.showSingleLongToast(holder.getAdapterPosition() + "");
        });
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        LiveRecommendBodyViewHolder bodyViewHolder = (LiveRecommendBodyViewHolder) holder;
        LiveAppIndexInfo.DataBean.RecommendDataBean.LivesBean livesBean = mLiveBeans.get(position);
        Glide.with(mContext)
                .load(livesBean.getCover().getSrc())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into((ImageView) bodyViewHolder.ivVideoPreview);
        bodyViewHolder.tvVideoLiveUp.setText(livesBean.getOwner().getName());
        bodyViewHolder.tvVideoOnline.setText(NumberUtils.format(livesBean.getOnline() + ""));
        bodyViewHolder.tvVideoTitle.setText(new SpanUtils()
                .append("#" + livesBean.getArea() + "#")
                .setForegroundColor(mContext.getResources().getColor(R.color.pink_text_color))
                .append(livesBean.getTitle())
                .create());
        //设置左边item与左间距为10dp，右边item与右间距为10dp，其余均为5dp
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) bodyViewHolder.itemView.getLayoutParams();
        if (position % 2 == 0) {
            params.setMargins(10, 5, 5, 5);
        } else {
            params.setMargins(5, 5, 10, 5);
        }
        bodyViewHolder.itemView.setLayoutParams(params);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        LiveRecommendFooterViewHolder footerViewHolder = (LiveRecommendFooterViewHolder) holder;
        if (mHasFooter) {
            footerViewHolder.btMore.setVisibility(View.VISIBLE);
            footerViewHolder.tvDynamic.setText(String.valueOf(mRandom.nextInt(200) + "条新动态，点击这里刷新"));
            footerViewHolder.ivRefresh.setOnClickListener(v ->
                v.animate()
                        .rotation(360)
                        .setInterpolator(new LinearInterpolator())
                        .setDuration(1000)
                        .start());
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 0);
            footerViewHolder.itemView.setLayoutParams(params);
        }
        footerViewHolder.itemView.setOnClickListener(v ->
            ToastUtils.showSingleLongToast(holder.getAdapterPosition() + ""));
    }

    static class LiveRecommendHeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_online)
        TextView tvOnline;
        @BindView(R.id.cl_type_root)
        ConstraintLayout clTypeRoot;
        @BindView(R.id.iv_video_preview)
        ImageView ivVideoPreview;
        @BindView(R.id.tv_video_title)
        TextView tvVideoTitle;
        @BindView(R.id.tv_video_live_up)
        TextView tvVideoLiveUp;
        @BindView(R.id.tv_video_online)
        TextView tvVideoOnline;
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.cl_video_root)
        ConstraintLayout clVideoRoot;

        public LiveRecommendHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class LiveRecommendBodyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_video_preview)
        ImageView ivVideoPreview;
        @BindView(R.id.tv_video_title)
        TextView tvVideoTitle;
        @BindView(R.id.tv_video_live_up)
        TextView tvVideoLiveUp;
        @BindView(R.id.tv_video_online)
        TextView tvVideoOnline;

        public LiveRecommendBodyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class LiveRecommendFooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bt_more)
        Button btMore;
        @BindView(R.id.tv_dynamic)
        TextView tvDynamic;
        @BindView(R.id.iv_refresh)
        ImageView ivRefresh;
        public LiveRecommendFooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
