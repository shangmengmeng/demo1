package com.anew.note.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.anew.note.R;
import com.anew.note.adapter.SecRecyclerViewAdapter;
import com.anew.note.model.SecModel;

import com.anew.note.utils.SPUtils;

import java.util.ArrayList;

public class SecretNoteListActivity extends AppCompatActivity {
    private RecyclerView rcSecList;
    private ArrayList<SecModel> mSecrList;
    private SecRecyclerViewAdapter secAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret);
        getData();
        initView();
    }

    private void initView() {
        rcSecList = (RecyclerView) findViewById(R.id.rc_secret_list);
        secAdapter = new SecRecyclerViewAdapter(this,mSecrList);
        rcSecList.setAdapter(secAdapter);
        LinearLayoutManager ly = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rcSecList.setLayoutManager(ly);
    }
    private void getData(){
        ArrayList<SecModel> model22= (ArrayList<SecModel>) SPUtils.getInstance(getApplicationContext()).getObject("list");
        Log.e("----------++++++---",model22.toString());
        mSecrList = model22;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
