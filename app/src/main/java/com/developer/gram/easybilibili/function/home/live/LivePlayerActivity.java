package com.developer.gram.easybilibili.function.home.live;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.developer.gram.easybilibili.EasyBiliApp;
import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.base.BaseActivity;
import com.developer.gram.easybilibili.function.user.UserInfoDetailsActivity;
import com.developer.gram.easybilibili.mvp.contract.LivePlayerContract;
import com.developer.gram.easybilibili.mvp.presenter.LivePlayerPresenter;
import com.developer.gram.easybilibili.util.ConstantUtil;
import com.developer.gram.easybilibili.widget.CircleImageView;
import com.developer.gram.easybilibili.widget.livelike.LoveLikeLayout;

import java.io.IOException;
import butterknife.BindView;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by Gram on 2017/12/20.
 */

public class LivePlayerActivity extends BaseActivity<LivePlayerPresenter> implements LivePlayerContract.View{
    @BindView(R.id.video_view)
    SurfaceView videoView;
    @BindView(R.id.bili_anim)
    ImageView mAnimView;
    @BindView(R.id.right_play)
    ImageView mRightPlayBtn;
    @BindView(R.id.bottom_layout)
    RelativeLayout mBottomLayout;
    @BindView(R.id.bottom_play)
    ImageView mBottomPlayBtn;
    @BindView(R.id.bottom_fullscreen)
    ImageView mBottomFullscreen;
    @BindView(R.id.video_start_info)
    TextView mLoadTv;
    @BindView(R.id.user_pic)
    CircleImageView mUserPic;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.live_num)
    TextView mLiveNum;
    @BindView(R.id.love_layout)
    LoveLikeLayout mLoveLikeLayout;
    @BindView(R.id.bottom_love)
    ImageView mlove;

    private int flag = 0;
    private int cid;
    private String title;
    private int online;
    private String face;
    private String name;
    private int mid;
    private SurfaceHolder holder;
    private boolean isPlay = false;
    private IjkMediaPlayer ijkMediaPlayer;
    private AnimationDrawable mAnimViewBackground;

    @Override
    public int getLayoutId() {
        return R.layout.activity_live_details;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.setTitle(title);
    }

    @Override
    protected void initVariables() {
        Intent intent = getIntent();
        if (intent != null) {
            cid = intent.getIntExtra(ConstantUtil.EXTRA_CID, 0);
            title = intent.getStringExtra(ConstantUtil.EXTRA_TITLE);
            online = intent.getIntExtra(ConstantUtil.EXTRA_ONLINE, 0);
            face = intent.getStringExtra(ConstantUtil.EXTRA_FACE);
            name = intent.getStringExtra(ConstantUtil.EXTRA_NAME);
            mid = intent.getIntExtra(ConstantUtil.EXTRA_MID, 0);
        }
    }

    @Override
    protected void initViews() {
        initVideo();
        initUserInfo();
        startAnim();
    }

    @Override
    protected void initInject() {
        EasyBiliApp.getAppComponent().inject(this);
    }

    @Override
    protected void loadData() {
        mPresenter.getLivePlayerData(cid);
    }

    @Override
    public void playLive(String url) {
        playVideo(url);
    }

    @Override
    public void refreshUI() {
        stopAnim();
        isPlay = true;
        visible(videoView);
        mRightPlayBtn.setImageResource(R.drawable.ic_tv_stop);
        mBottomPlayBtn.setImageResource(R.drawable.ic_portrait_stop);
    }

    private void initVideo() {
        holder = videoView.getHolder();
        ijkMediaPlayer = new IjkMediaPlayer();
    }

    /**
     * 设置用户信息
     */
    private void initUserInfo() {
        Glide.with(LivePlayerActivity.this)
                .load(face)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.ico_user_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mUserPic);
        mUserName.setText(name);
        mLiveNum.setText(String.valueOf(online));
    }

    private void startAnim() {
        mAnimViewBackground = (AnimationDrawable) mAnimView.getBackground();
        mAnimViewBackground.start();
    }

    private void stopAnim() {
        mAnimViewBackground.stop();
        gone(mAnimView);
        gone(mLoadTv);
    }

    private void playVideo(String uri) {
        try {
            ijkMediaPlayer.setDataSource(this, Uri.parse(uri));
            ijkMediaPlayer.setDisplay(holder);
            holder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    ijkMediaPlayer.setDisplay(holder);
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                }
            });
            ijkMediaPlayer.prepareAsync();
            ijkMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ijkMediaPlayer.setKeepInBackground(false);
    }


    private void startBottomShowAnim() {
        visible(mBottomLayout, mRightPlayBtn);
    }


    private void startBottomHideAnim() {
        gone(mBottomLayout, mRightPlayBtn);
    }

    @OnClick({R.id.right_play, R.id.bottom_play, R.id.bottom_fullscreen,
            R.id.video_view, R.id.user_pic, R.id.bottom_love})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_play:
                ControlVideo();
                break;
            case R.id.bottom_play:
                ControlVideo();
                break;
            case R.id.bottom_fullscreen:
                break;
            case R.id.video_view:
                if (flag == 0) {
                    startBottomShowAnim();
                    flag = 1;
                } else {
                    startBottomHideAnim();
                    flag = 0;
                }
                break;
            case R.id.user_pic:
                UserInfoDetailsActivity.launch(LivePlayerActivity.this, name, mid, face);
                ControlVideo();
                mRightPlayBtn.setVisibility(View.VISIBLE);
                break;
            case R.id.bottom_love:
                mLoveLikeLayout.addLove();
                break;
        }
    }

    private void ControlVideo() {
        if (isPlay) {
            ijkMediaPlayer.pause();
            isPlay = false;
            mRightPlayBtn.setImageResource(R.drawable.ic_tv_play);
            mBottomPlayBtn.setImageResource(R.drawable.ic_portrait_play);
        } else {
            ijkMediaPlayer.start();
            isPlay = true;
            mRightPlayBtn.setImageResource(R.drawable.ic_tv_stop);
            mBottomPlayBtn.setImageResource(R.drawable.ic_portrait_stop);
        }
    }

    public static void launch(Activity activity, int cid, String title, int online, String face, String name, int mid) {
        Intent mIntent = new Intent(activity, LivePlayerActivity.class);
        mIntent.putExtra(ConstantUtil.EXTRA_CID, cid);
        mIntent.putExtra(ConstantUtil.EXTRA_TITLE, title);
        mIntent.putExtra(ConstantUtil.EXTRA_ONLINE, online);
        mIntent.putExtra(ConstantUtil.EXTRA_FACE, face);
        mIntent.putExtra(ConstantUtil.EXTRA_NAME, name);
        mIntent.putExtra(ConstantUtil.EXTRA_MID, mid);
        activity.startActivity(mIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ijkMediaPlayer.release();
    }
}
