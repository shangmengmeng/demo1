package com.anew.note.app;

import android.os.Environment;

import java.io.File;

/**
 * Created by pig on 2017/2/27.
 */

public class AppContants {

    public static final String APP_DIR =
            Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator +"小便签";
    public static final String IMAGE_DIR = APP_DIR + File.separator + "image";
    public static final String CACHE_DIR = APP_DIR + File.separator + "cache";

}

