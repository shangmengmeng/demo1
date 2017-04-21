package com.anew.note.utils;

import android.app.Activity;
import android.graphics.Bitmap;

/**
 * Created by pig on 2017/1/5.
 */

public  class  ScreenUtil {
    public static Bitmap getScreen(Activity activity){
        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);
        Bitmap bitmap = activity.getWindow().getDecorView().getDrawingCache();
        return bitmap;
    }

}
