package com.anew.note.activity;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.gesture.GestureUtils;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.anew.note.R;
import com.anew.note.app.MyApplycation;
import com.anew.note.views.SplashLayout;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.yanzhenjie.permission.AndPermission;

public class SplashActivity extends AppCompatActivity {
    private   long timeExit =0;
    private Handler handler =  new Handler();
    private ImageView iv_welcome;
    private int runCount;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener;
    public  String cityText  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //地图开启----------------------------------------------------
        AndPermission.with(this)
                .requestCode(100)
                .permission(Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE)
                .send();
        myListener = new MyLocationListener();
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);

        //注册监听函数
        initLocation();
        mLocationClient.start();
//---------------------------------------------------------------------------------------
        final SplashLayout layout = (SplashLayout) findViewById(R.id.splashLayout);
        iv_welcome = (ImageView) findViewById(R.id.iv_welcome);
        final Handler handler = new Handler();
        runCount = 0;// 全局变量，用于判断是否是第一次执行
        Runnable runnable = new Runnable(){

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if(runCount == 10){// 第一次执行则关闭定时执行操作
                    // 在此处添加执行的代码
                    layout.initData();
                    layout.initData();
                    layout.initData();
                    layout.initData();
                    handler.removeCallbacks(this);
                }
                handler.postDelayed(this, 10);
                runCount++;
            }

        };
        handler.postDelayed(runnable, 10);// 打开定时器，执行操作


        iv_welcome.setVisibility(View.VISIBLE);
        BounceInterpolator i = new BounceInterpolator();
        ObjectAnimator transX = ObjectAnimator.ofFloat(iv_welcome,"translationX",-500,300);
        transX.setInterpolator(i);
        transX.setDuration(1200);
        ObjectAnimator scalY = ObjectAnimator.ofFloat(iv_welcome,"scaleY",1f,1.5f,1.5f,1f,1f,1.5f,1.5f,1f,1f,1.5f,1.5f,1f);
        scalY.setDuration(1200);
        scalY.setInterpolator(i);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(transX,scalY);
        set.start();

        //双击进入主页面
        layout.setOnTouchListener(new View.OnTouchListener() {
          @Override
          public boolean onTouch(View v, MotionEvent event) {
              if (event.getAction() == MotionEvent.ACTION_DOWN){

                  if (System.currentTimeMillis()-timeExit>1000) {
                      timeExit = System.currentTimeMillis();

                  }
                  else {
                      Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                      intent.putExtra("city",cityText);
                      startActivity(intent);
                      layout.stop();
                      finish();
                  }
                  return true;
              }
              return false;
          }
      });
      handler .postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                intent.putExtra("city",cityText);
                startActivity(intent);
                layout.stop();
                finish();
            }
        },10000);

    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {


            cityText = location.getCity();
            if (cityText==null){
                cityText  = "北京市";
            }


        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    //写入文件

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                MyApplycation.getInstance().initLocalFile();
        }
    }
}
