package com.anew.note.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.anew.note.R;
import com.anew.note.utils.JudgeUtils;
import com.anew.note.utils.RegularUtils;
import com.anew.note.utils.SPUtils;

public class CheckActivity extends AppCompatActivity {
    private boolean isFirst = true;
    private TextView text_input;
    private Button button_input;
    private ImageView a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_check);

        text_input = (TextView) findViewById(R.id.text_input);
        button_input = (Button) findViewById(R.id.button_sec);
        if (SPUtils.getInstance(getApplicationContext()).getStringValue("isFist") == null) {
            text_input.setHint("欢迎来到私密空间，请输入4位数字“1.2.3”密码");
            button_input.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String a = text_input.getText().toString();
                    if (a.length()==4&& RegularUtils.isNumber(a)){
                        SPUtils.getInstance(getApplicationContext()).putStringValue("note", a);
                        SPUtils.getInstance(getApplicationContext()).putStringValue("isFist", "哈哈");
                        Intent intent = new Intent(getApplicationContext(),ScrollingActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"请输入4位数字",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });
        }
        else {
            text_input.setHint("请输入4位数字“1.2.3”密码");
            button_input.setText("确定");
            button_input.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //成功
                    String a = text_input.getText().toString();
                    if (a.equals(SPUtils.getInstance(getApplicationContext())
                            .getStringValue("note")) ) {
                        Intent intent = new Intent(CheckActivity.this, ScrollingActivity.class);
                        startActivity(intent);
                        //通过以后就不需要输入密码
                        JudgeUtils.getIntence(getApplicationContext()).setPass(true);
                        finish();
                    }
                    if (!a.equals(SPUtils.getInstance(getApplicationContext())
                            .getStringValue("note"))) {
                      Toast.makeText(getApplicationContext(),"密码错误，请重新输入",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
