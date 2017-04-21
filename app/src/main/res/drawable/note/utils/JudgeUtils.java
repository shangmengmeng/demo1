package com.anew.note.utils;

import android.content.Context;

/**
 * Created by pig on 2016/12/2.
 */

public class JudgeUtils  {
    private JudgeUtils() {
    }
   //用单例模式来判断,static公用，final太监类，不能被重写，
    private static JudgeUtils judgeUtils;
    public static JudgeUtils getIntence(Context context){
        if (judgeUtils==null){
            judgeUtils = new JudgeUtils();
        }
        return judgeUtils;
    }
    private boolean isPass =false;
    public boolean isPass() {
        return isPass;
    }
    public void setPass(boolean pass) {
        isPass = pass;
    }
}
