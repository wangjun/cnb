package me.yugy.cnbeta;

import android.content.Context;

import butterknife.ButterKnife;
import me.yugy.app.common.utils.DebugUtils;

/**
 * Created by yugy on 4/13/16.
 */
public class Application extends android.app.Application {

    private static Context sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = getApplicationContext();
        ButterKnife.setDebug(BuildConfig.DEBUG);
        DebugUtils.setLogEnable(BuildConfig.DEBUG);
    }
}
