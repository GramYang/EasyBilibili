package com.developer.gram.easybilibili.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gram on 2017/12/19.
 */

@Module
public class AppModule {
    private Context mContext;

    public AppModule(Context context) {
        this.mContext = context;
    }

    @Provides
    public Context provideContext() {
        return mContext;
    }
}

