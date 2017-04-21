package com.anew.note.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.anew.note.R;
import com.anew.note.adapter.MyScrRecyclerAdapter;
import com.anew.note.model.SecModel;
import com.anew.note.utils.SPUtils;
import com.anew.note.views.FullyGridLayoutManager;

import java.util.ArrayList;

/**
 * 私密日志的列表
 */
public class ScrollingActivity extends AppCompatActivity {
    private RecyclerView rec_list;
    private ArrayList<SecModel>mlist;
    private MyScrRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        if (getIntent().getStringExtra("from")!=null){
            //来自重新编辑以防止出错
            mlist = (ArrayList<SecModel>) SPUtils.getInstance(this).getObject("slist");

        }
        initView();
    }

    private void initView() {
        rec_list = (RecyclerView) findViewById(R.id.rc_list_show);
        mlist = (ArrayList<SecModel>) SPUtils.getInstance(this).getObject("slist");
        Log.e("======",mlist.toString());
        ImageView image_nothing = (ImageView) findViewById(R.id.image_nothing_sec);
        if (mlist.size()==0){
            image_nothing.setVisibility(View.VISIBLE);
        }else {
            image_nothing.setVisibility(View.GONE);
            adapter = new MyScrRecyclerAdapter(this, mlist);

            rec_list.setLayoutManager(new FullyGridLayoutManager(this, 2, FullyGridLayoutManager.VERTICAL, false));
            rec_list.setNestedScrollingEnabled(false);
            rec_list.setItemAnimator(new DefaultItemAnimator());

            rec_list.setAdapter(adapter);
            adapter.OnItemClickListiner(new MyScrRecyclerAdapter.OnItemClickListioner() {
                @Override
                public void OnItemClickListioner(View v, int position) {
                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    intent.putExtra("data", mlist.get(position));
                    intent.putExtra("position",position);
                    intent.putExtra("model",mlist);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                    finish();
                }

                @Override
                public void OnItemcLongClickListioner(View v, final int position) {
                    //这里长按定义的是删除
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ScrollingActivity.this);
                    dialog.setTitle("是否删除");
                    dialog.setMessage("确定吗？");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mlist.remove(position);
                            adapter.notifyItemRemoved(position);
                            SPUtils.getInstance(getApplicationContext()).putObject("slist",mlist);
                        }
                    });

                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
