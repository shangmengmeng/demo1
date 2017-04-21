package com.anew.note.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.anew.note.utils.Constant;

/**
 * Created by Administrator on 2016/11/26 0026.
 */

public class MySqliteHelper extends SQLiteOpenHelper{

    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySqliteHelper(Context context){
        super(context, Constant.DABASE_NAME,null,Constant.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
      // String sql = "create table note(_id Integer primary key,content varchar(40),date varchar(40))";
        String sql = "create table "+Constant.TABLE_NAME+"("+Constant._ID+" Integer primary key,  "+Constant.CONTENT+" varchar(40), "+Constant.DATE+" varchar(40))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
