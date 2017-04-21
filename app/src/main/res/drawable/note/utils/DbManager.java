package com.anew.note.utils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2016/11/26 0026.
 */

public class DbManager {
    private static MySqliteHelper helper;
    public static MySqliteHelper getInstance(Context context){
        if(helper==null){
            helper  = new MySqliteHelper(context);
        }
        return helper;
    }
    public static void exeSQL(SQLiteDatabase db,String sql){
        if(db!=null){
            if(sql!=null&&!"".equals(sql)){
                db.execSQL(sql);
            }
        }
    }

}
