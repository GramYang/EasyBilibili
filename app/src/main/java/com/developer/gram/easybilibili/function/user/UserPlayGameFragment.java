package com.developer.gram.easybilibili.function.user;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.UserPlayGameAdapter;
import com.developer.gram.easybilibili.base.LazyRefreshFragment;
import com.developer.gram.easybilibili.bean.user.UserPlayGameInfo;
import com.developer.gram.easybilibili.util.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gram on 2017/12/25.
 */

public class UserPlayGameFragment extends LazyRefreshFragment {
    private UserPlayGameInfo userPlayGameInfo;
    private List<UserPlayGameInfo.DataBean.GamesBean> games = new ArrayList<>();

    public static UserPlayGameFragment newInstance(UserPlayGameInfo userPlayGameInfo) {
        UserPlayGameFragment mFragment = new UserPlayGameFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantUtil.EXTRA_DATA, userPlayGameInfo);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user_play_game;
    }

    @Override
    protected void initVariables() {
        userPlayGameInfo = getArguments().getParcelable(ConstantUtil.EXTRA_DATA);
    }

    @Override
    protected void initRecyclerView() {
        games.addAll(userPlayGameInfo.getData().getGames());
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        UserPlayGameAdapter mAdapter = new UserPlayGameAdapter(mRecycler, games);
        mRecycler.setAdapter(mAdapter);
        if (games.isEmpty()) {
            initEmptyLayout();
        }
    }

    private void initEmptyLayout() {
        mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_space_no_data);
        mCustomEmptyView.setEmptyText("ㄟ( ▔, ▔ )ㄏ 再怎么找也没有啦");
    }
}
