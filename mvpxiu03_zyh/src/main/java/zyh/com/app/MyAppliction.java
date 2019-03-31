package zyh.com.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

public class MyAppliction extends Application {

    private static SharedPreferences myUserSp;

    @Override
    public void onCreate() {
        super.onCreate();

        //设置磁盘缓存
        DiskCacheConfig diskCacheConfig =  DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryName("images")
                .setBaseDirectoryPath(Environment.getExternalStorageDirectory())
                .build();
        //设置磁盘缓存的配置,生成配置文件
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();

        //初始化fresco
        Fresco.initialize(this,config);

        myUserSp = getSharedPreferences("MyUser", Context.MODE_PRIVATE);
    }

    public static SharedPreferences getShare() {
        return myUserSp;
    }
}
