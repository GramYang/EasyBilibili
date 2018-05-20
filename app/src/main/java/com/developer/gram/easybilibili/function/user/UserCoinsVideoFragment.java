package com.developer.gram.easybilibili.function.user;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.UserCoinsVideoAdapter;
import com.developer.gram.easybilibili.base.LazyRefreshFragment;
import com.developer.gram.easybilibili.bean.user.UserCoinsInfo;
import com.developer.gram.easybilibili.function.video.VideoDetailActivity;
import com.developer.gram.easybilibili.util.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gram on 2017/12/25.
 */

public class UserCoinsVideoFragment extends LazyRefreshFragment {
    private UserCoinsInfo userCoinsInfo;
    private List<UserCoinsInfo.DataBean.ListBean> userCoins = new ArrayList<>();


    public static UserCoinsVideoFragment newInstance(UserCoinsInfo userCoinsInfo) {
        UserCoinsVideoFragment mFragment = new UserCoinsVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantUtil.EXTRA_DATA, userCoinsInfo);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user_coins;
    }

    @Override
    protected void initVariables() {
        userCoinsInfo = getArguments().getParcelable(ConstantUtil.EXTRA_DATA);
    }

    @Override
    protected void initRecyclerView() {
        userCoins.addAll(userCoinsInfo.getData().getList());
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        UserCoinsVideoAdapter mAdapter = new UserCoinsVideoAdapter(mRecycler, userCoins);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((position, holder) -> VideoDetailActivity.launch(getActivity(), userCoins.get(position).getAid(), userCoins.get(position).getPic()));
        if (userCoins.isEmpty()) {
            initEmptyLayout();
        }
    }

    private void initEmptyLayout() {
        mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_space_no_data);
        mCustomEmptyView.setEmptyText("ㄟ( ▔, ▔ )ㄏ 再怎么找也没有啦");
    }
}
