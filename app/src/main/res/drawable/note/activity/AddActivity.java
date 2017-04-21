package com.anew.note.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.anew.note.R;
import com.anew.note.model.TipModel;
import com.anew.note.utils.DbManager;
import com.anew.note.utils.MySqliteHelper;
import com.anew.note.utils.SPUtils;
import com.anew.note.utils.TimeUtils;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edit_add;
    private TextView text_number,text_date;
    private TipModel mData = new TipModel();
    private Button button_summit;

    private MySqliteHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //获得数据库帮助类对象
        helper= DbManager.getInstance(this);
        initView();
        setData();
        edit_add.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable editable = edit_add.getText();
                int len = editable.length();
                text_number.setText(len+"");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initView() {
        text_date = (TextView) findViewById(R.id.text_date);
        text_number = (TextView) findViewById(R.id.text_number);
        edit_add = (EditText) findViewById(R.id.edit_add);
        button_summit = (Button) findViewById(R.id.button_summit);
        button_summit.setOnClickListener(this);


    }
    private void setData() {
        text_date.setText(TimeUtils.getTodayDate());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_summit:
                if (TextUtils.isEmpty(edit_add.getText())){
                    Toast.makeText(getApplicationContext(),"输入不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    long time = TimeUtils.getUnixStamp();

//                    SQLiteDatabase db = helper.getWritableDatabase();
//                    ContentValues values = new ContentValues();
//                    values.put(Constant.CONTENT,edit_add.getText().toString());
//                    values.put(Constant.DATE,text_date.getText().toString());
//                    long result = db.insert(Constant.TABLE_NAME,null,values);
//                    if(result>0){
//                        Toast.makeText(AddActivity.this,"成功插入数据",Toast.LENGTH_LONG).show();
//                    }else {
//                        Toast.makeText(AddActivity.this,"插入数据失败",Toast.LENGTH_SHORT).show();
//                    }
//                    Cursor cursor = db.query(Constant.TABLE_NAME,null,null,null,null,null,null);
//                    if(cursor.moveToFirst()){
//                        do{
//                            String content = cursor.getString(cursor.getColumnIndex("content"));
//                            String date= cursor.getString(cursor.getColumnIndex("date"));
//                            Log.e("NoteListActivity","note content is" + content);
//                            Log.e("NoteListActivity","note data is" + date);
//
//                        }while(cursor.moveToNext());
//                    }
//                    cursor.close();
//                    db.close();

                    mData.setNumber(Integer.parseInt(text_number.getText().toString()));
                    mData.setContent(edit_add.getText().toString());
                    mData.setDate(text_date.getText().toString());
                    mData.setTip((int) time);
                    Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("AddActivity",mData);
                    Log.e("-----------",mData.toString());

                   ArrayList<TipModel> model = (ArrayList<TipModel>) SPUtils.getInstance(getApplicationContext()).getObject("list");
                   model.add(mData);

                    SPUtils.getInstance(getApplicationContext()).putObject("list",model);
                    ArrayList<TipModel> model22= (ArrayList<TipModel>) SPUtils.getInstance(getApplicationContext()).getObject("list");
                    startActivity(intent);
                    finish();
                }

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
