package com.anew.note.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anew.note.R;
import com.anew.note.adapter.MyRecyclerViewAdapter;
import com.anew.note.model.TipModel;
import com.anew.note.utils.SPUtils;

import java.util.ArrayList;

public class NoteListActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rc_list;
    private ArrayList<TipModel> mlist;
    private MyRecyclerViewAdapter adapter;
    TextView btnOK;
    private int Tag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        getData();
        initView();
    }

    private void initView() {
        rc_list = (RecyclerView) findViewById(R.id.rc_list);
        ImageView image_nothing = (ImageView) findViewById(R.id.image_nothing);
        if (mlist.size() == 0) {
            image_nothing.setVisibility(View.VISIBLE);
        } else {
            image_nothing.setVisibility(View.GONE);

            adapter = new MyRecyclerViewAdapter(this, mlist);
            LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rc_list.setLayoutManager(lm);
            rc_list.setItemAnimator(new DefaultItemAnimator());
            rc_list.setAdapter(adapter);


            adapter.setmOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {

                @Override
                public void OnItemClick(View v, int position) {

                }

                @Override
                public void OnLongItemClick(View v, final int position) {
                    //这里长按定义的是删除
                    AlertDialog.Builder dialog = new AlertDialog.Builder(NoteListActivity.this);
                    dialog.setTitle("是否删除");
                    dialog.setMessage("确定吗？");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mlist.remove(position);
                            adapter.notifyItemRemoved(position);
                            SPUtils.getInstance(getApplicationContext()).putObject("list",mlist);
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

    public void getData() {
        ArrayList<TipModel> model22 = (ArrayList<TipModel>) SPUtils.getInstance(getApplicationContext()).getObject("list");
        Log.e("----------++++++---", model22.toString());
        mlist = model22;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
