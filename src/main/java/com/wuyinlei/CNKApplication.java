package com.wuyinlei;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LargestLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;


public class CNKApplication  extends Application{
    private static CNKApplication instance=null;
    @Override
    public void onCreate() {
        super.onCreate();
        this.instance=this;
        initImageLoader(getApplicationContext());
    }
    public static CNKApplication getInstance(){
        return instance;
    }

    private void initImageLoader(Context context){
        File cacheDir = StorageUtils.getCacheDirectory(context);
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration
                .Builder(context)
                .threadPoolSize(5)//线程池
                .diskCache(new UnlimitedDiskCache(cacheDir))//内存卡
                .threadPriority(Thread.NORM_PRIORITY -2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LargestLimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .memoryCacheSizePercentage(13) // default
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                        //.writeDebugLogs()
                .tasksProcessingOrder(QueueProcessingType.FIFO)  //先进先出
                .build();
                ImageLoader.getInstance().init(configuration);
    }
}
