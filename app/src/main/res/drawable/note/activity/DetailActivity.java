package com.anew.note.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anew.note.R;
import com.anew.note.model.SecModel;
import com.anew.note.utils.SPUtils;
import com.anew.note.utils.ScreenUtil;
import com.mob.commons.SHARESDK;

import java.util.ArrayList;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

public class DetailActivity extends AppCompatActivity {
    private TextView text_title,text_content,text_date;
    private SecModel data;
    private Button button_edit,button_input,button_share;
    private TextView text_date_input;
    private EditText edit_title,edit_content;
    private LinearLayout ll_show,ll_input;
    private int position;
    private ArrayList<SecModel>mlist;
    private final int TAG = 1;
    private int tag;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        text_title = (TextView) findViewById(R.id.text_title_detail);
        text_content = (TextView) findViewById(R.id.text_content_detail);
        text_date = (TextView) findViewById(R.id.text_date_detail);
        ll_show = (LinearLayout) findViewById(R.id.ll_show);
        ll_input = (LinearLayout) findViewById(R.id.ll_input);
        button_edit = (Button) findViewById(R.id.button_edit);
        button_input = (Button) findViewById(R.id.button_input);
        button_share = (Button) findViewById(R.id.button_share);

        text_date_input = (TextView) findViewById(R.id.text_date_input);
        edit_title = (EditText) findViewById(R.id.edit_title_input);
        edit_content = (EditText) findViewById(R.id.edit_content_input);
        mlist = (ArrayList<SecModel>) getIntent().getSerializableExtra("model");
        position =getIntent().getExtras().getInt("position");
        data = (SecModel) getIntent().getSerializableExtra("data");
        text_title.setText(data.getTitle());
        text_content.setText(data.getContent());
        text_date.setText(data.getDate());
        bitmap = ScreenUtil.getScreen(this);

        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_show.setVisibility(View.GONE);
                ll_input.setVisibility(View.VISIBLE);
                text_date_input.setText(data.getDate());
                edit_title.setText(data.getTitle());
                edit_content.setText(data.getContent());
                tag =TAG;


            }
        });
        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
        button_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mlist.remove(position);
                data.setTitle(edit_title.getText().toString());
                data.setContent(edit_content.getText().toString());
                mlist.add(data);
                SPUtils.getInstance(getApplicationContext()).putObject("slist",mlist);
                Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),ScrollingActivity.class);
                intent.putExtra("from","DetailActivity");
                startActivity(intent);
                finish();
            }
        });
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        oks.setTitle("标题");
        if (SPUtils.getInstance(this).getStringValue("image")!=null){
            String path =SPUtils.getInstance(this).getStringValue("image");
            oks.setImagePath(path);
            Log.e("------------","hhhhh");
        }

        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if ("QZone".equals(platform.getName())) {
                    paramsToShare.setTitle(null);
                    paramsToShare.setTitleUrl(null);
                    paramsToShare.setImageData(bitmap);

                    if (SPUtils.getInstance(getApplicationContext()).getStringValue("image")!=null){
                        String path =SPUtils.getInstance(getApplication()).getStringValue("image");
                        Log.e("------------",path);
                        paramsToShare.setImagePath(path);

                    }

                }
            }
        });

        // 启动分享GUI
        oks.show(this);
    }

    @Override
    public void onBackPressed() {
        if (tag==1){
            final AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("温馨提示")
                    .setMessage("日记没有保存，确定退出？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(),ScrollingActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create();
            alertDialog.show();
        }else {
            Intent intent  = new Intent(this,ScrollingActivity.class);
            startActivity(intent);
            finish();
        }

    }

}
