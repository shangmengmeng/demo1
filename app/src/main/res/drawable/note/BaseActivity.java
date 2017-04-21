package com.anew.note;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by pig on 2016/11/25.
 */

public abstract class  BaseActivity extends AppCompatActivity {
    protected Context context;

    @Override
    protected  void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        initData();
        initView();
        setData();
    }
    protected void initData(){

    }
    protected abstract void initView();
    protected abstract void setData();
    public boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE
            }, 1);
            return false;//第一次开启应用并执行权限检查，虽然返回了false，但是已经调用过了申请权限的方法
        }
        return true;//非第一次开启应用并执行权限检查，或者6.0以下的Android版本
    }
}
