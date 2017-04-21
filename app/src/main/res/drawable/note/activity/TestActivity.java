package com.anew.note.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.anew.note.R;
import com.anew.note.utils.Constant;
import com.anew.note.utils.DbManager;
import com.anew.note.utils.MySqliteHelper;

public class TestActivity extends AppCompatActivity {
    private MySqliteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //获得数据库帮助类对象
        helper= DbManager.getInstance(this);

    }
    //点击按钮创建数据库
    public void creatDb(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
    }
    public void click(View view){
        switch (view.getId()){
            case R.id.btn_insert:
                SQLiteDatabase db = helper.getWritableDatabase();
                String sql = "insert into "+ Constant.TABLE_NAME+" values(1,'时候是','周五')";
                DbManager.exeSQL(db,sql);
                String sql2 = "insert into "+Constant.TABLE_NAME+" values(2,'便签','周三')";
                DbManager.exeSQL(db,sql2);
                db.close();
                break;
            case R.id.btn_update:
                db = helper.getWritableDatabase();
                String updateSql = "update "+Constant.TABLE_NAME+" set "+Constant.CONTENT+
                        "='桑萌萌' where "+Constant._ID+"=1";
                DbManager.exeSQL(db,updateSql);
                db.close();
                break;
            case R.id.btn_delete:
                db = helper.getWritableDatabase();
                String delSql = "delete from "+Constant.TABLE_NAME+" where "+Constant._ID+"=2";
                DbManager.exeSQL(db,delSql);
                db.close();
                break;
        }
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_insertApi:
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Constant._ID,3);
                values.put(Constant.CONTENT,"我是便签");
                values.put(Constant.DATE,"我是日期");
                long result = db.insert(Constant.TABLE_NAME,null,values);
                if(result>0){
                    Toast.makeText(TestActivity.this,"成功插入数据",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(TestActivity.this,"插入数据失败",Toast.LENGTH_SHORT).show();
                }
                db.close();
                break;
            case R.id.btn_updateApi:
                db = helper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(Constant.CONTENT,"商梦梦");
//                int count = db.update(Constant.TABLE_NAME,
//                        cv,Constant._ID+"=1",null);
                int count = db.update(Constant.TABLE_NAME,
                        cv,Constant._ID+"=?",new String[]{"1"});
                if(count>0){
                    Toast.makeText(TestActivity.this,"成功修改数据",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(TestActivity.this,"修改数据失败",Toast.LENGTH_SHORT).show();
                }
                db.close();
                break;
            case R.id.btn_deleteApi:
                db = helper.getWritableDatabase();
                int count2 = db.delete(Constant.TABLE_NAME,Constant._ID+"=?",
                        new String[]{"3"});
                if(count2>0){
                    Toast.makeText(TestActivity.this,"成功删除数据",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(TestActivity.this,"删除数据失败",Toast.LENGTH_SHORT).show();
                }
                db.close();
                break;
        }
    }


}
