package com.fansymeng.demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fansymeng.demo.activity.PhotoViewDemo;
import com.fansymeng.demo.view.MyDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_icon;
    private EditText edit_name;
    private EditText edit_password;
    private Button bt_submit;
    private String name,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();

        final MyDialog.Builder builder = new MyDialog.Builder(this);
        builder.ButtonClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String a =builder.getText();
                edit_name.setText(a);
                Log.e("1111111",a);
                Toast.makeText(MainActivity.this,a,Toast.LENGTH_SHORT).show();

            }
        });
        builder.create().show();

    }

    // End Of Content View Elements

    private void bindViews() {

        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_password = (EditText) findViewById(R.id.edit_password);
        bt_submit = (Button) findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_submit:
                initData();
                break;
        }
    }
    private void initData() {
        name = edit_name.getText().toString();
       password = edit_password.getText().toString();
        if (name==null||name.equals("")){
            Toast.makeText(this,"mingweik",Toast.LENGTH_SHORT).show();
            edit_name.setText("001bx");
            edit_name.requestFocus();

        }
        else if (password==null||password.isEmpty()){
            Toast.makeText(this,"mimaweik",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, PhotoViewDemo.class);
            startActivity(intent);
        }
    }
}
