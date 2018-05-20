package com.developer.gram.easybilibili;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.developer.gram.easybilibili.dagger.ApiModule;
import com.developer.gram.easybilibili.dagger.AppComponent;
import com.developer.gram.easybilibili.dagger.AppModule;
import com.developer.gram.easybilibili.dagger.DaggerAppComponent;
import com.developer.gram.easybilibili.util.LogUtils;
import com.developer.gram.easybilibili.util.PrefsUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gram on 2017/12/29.
 */

public class EasyBiliApp extends Application{
    private static EasyBiliApp mInstance;
    private Set<Activity> allActivities;
    private static AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initBugly();
        mInstance = this;
        initPrefs();
        initComponent();
        initLog();
    }

    public static EasyBiliApp getInstance() {
        return mInstance;
    }

    /**
     * 初始化bugly
     */
    private void initBugly() {
        CrashReport.initCrashReport(getApplicationContext(), "721778a677", false);
    }

    /**
     * 初始化sharedpreferences
     */
    private void initPrefs() {
        PrefsUtils.init(this, getPackageName() + "_preference", Context.MODE_PRIVATE);
    }

    /**
     * 初始化AppComponent，内含网络请求模块
     */
    private void initComponent() {
        mAppComponent = DaggerAppComponent.builder()
                .apiModule(new ApiModule())
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }

    /**
     * 初始化Log
     */
    private void initLog() {
        LogUtils.init(this);
    }

    /**
     * 添加Activity
     * @param act
     */
    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    /**
     * 移除Activity
     * @param act
     */
    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    /**
     * 退出应用
     */
    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
