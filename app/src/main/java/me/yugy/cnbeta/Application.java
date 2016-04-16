package me.yugy.cnbeta;

import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import butterknife.ButterKnife;
import me.yugy.app.common.utils.DebugUtils;

public class Application extends android.app.Application {

    private static Context sInstance;

    public static Context getContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = getApplicationContext();
        ButterKnife.setDebug(BuildConfig.DEBUG);
        DebugUtils.setLogEnable(BuildConfig.DEBUG);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.color.placeholder)
                .showImageOnFail(R.color.placeholder)
                .showImageOnLoading(R.color.placeholder)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new FadeInBitmapDisplayer(200, true, true, false))
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheSize(20 * 1024 * 1024)
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);
    }
}
