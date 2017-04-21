package com.anew.note.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;


import java.io.File;

/**
 * Created by pig on 2017/2/27.
 */

public class MyApplycation extends Application {
    private static Context context;
    private static MyApplycation instance;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        instance = this;
        initLocalFile();

    }
    //获取全局上下文
    public static  Context getContext(){
        return context;
    }
    public static MyApplycation getInstance() {
        return instance;
    }

    public void initLocalFile() {
        File cacheDir = new File(AppContants.CACHE_DIR);


        if (!cacheDir.exists()){
            cacheDir.mkdirs();
            Log.e("----------","文件夹已经创建");
        }
    }


}
