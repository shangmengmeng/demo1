package com.anew.note.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anew.note.R;
import com.anew.note.app.MyApplycation;
import com.anew.note.model.SecModel;
import com.anew.note.model.TipModel;
import com.anew.note.network.ApiWork;
import com.anew.note.network.BaseModel;
import com.anew.note.network.Netlistioner;
import com.anew.note.network.WeatherModel;
import com.anew.note.utils.AppUtils;
import com.anew.note.utils.JudgeUtils;
import com.anew.note.utils.SPUtils;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.MapView;
import com.bumptech.glide.Glide;
import com.yanzhenjie.permission.AndPermission;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * FancyMeng
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private FrameLayout imageView;
    private AppBarLayout appBarLayout;
    private TextView text_blakborad, text_show, text_add, text_check, text_slide_title, city;
    private NavigationView navigationView;
    private TipModel nData;
    private int from;
    private final static int FROM_ADD = 1;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener;
    private String  cityText_Location,cityText_weather;

    private String code;
    private String text;
    private String tmp, hum;//温度湿度
    private String wind;

    private ImageView iv_weather_icon;
    private TextView tv_weather_text;
    private TextView tv_weather_tmp;
    private TextView tv_weather_hum;
    private TextView tv_weather_wind;
    private Animation animation;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
        initView();
        setData();

    }


    public void getData() {
        if (getIntent().getSerializableExtra("AddActivity") != null) {
            nData = (TipModel) getIntent().getSerializableExtra("AddActivity");
            from = FROM_ADD;
        }
        if (getIntent().getStringExtra("city")!=null){

            cityText_Location = getIntent().getStringExtra("city");
        }


    }

    private void initView() {
        navigationView = (NavigationView) findViewById(R.id.slide_menu);
        imageView = (FrameLayout) findViewById(R.id.image_title);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.ctool);
        text_add = (TextView) findViewById(R.id.add_text);
        text_add.setOnClickListener(this);
        text_check = (TextView) findViewById(R.id.check_text);
        text_check.setOnClickListener(this);
        text_blakborad = (TextView) findViewById(R.id.text_blackboard);
        text_show = (TextView) findViewById(R.id.text_show);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        city = (TextView) findViewById(R.id.city);
        iv_weather_icon = (ImageView) findViewById(R.id.iv_weather_icon);
        tv_weather_text = (TextView) findViewById(R.id.tv_weather_text);
        tv_weather_tmp = (TextView) findViewById(R.id.tv_weather_tmp);
        tv_weather_hum = (TextView) findViewById(R.id.tv_weather_hum);
        tv_weather_wind = (TextView) findViewById(R.id.tv_weather_wind);

        //侧滑插入布局
        View headerView = navigationView.inflateHeaderView(R.layout.navigation_header);
        text_slide_title = (TextView) headerView.findViewById(R.id.tv_secret_title);
        if (SPUtils.getInstance(getApplicationContext()).getStringValue("name") != null) {
            text_slide_title.setText(SPUtils.getInstance(getApplicationContext()).getStringValue("name"));
        }
        //修改图片

        CircleImageView profile_image = (CircleImageView) headerView.findViewById(R.id.profile_image);
        if (SPUtils.getInstance(this).getStringValue("image") != null) {
            String path = SPUtils.getInstance(this).getStringValue("image");
            Glide.with(this)
                    .load(path)
                    .into(profile_image);
        }


        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main);

        //标题栏的设置
        //设置toolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("小签");
        setSupportActionBar(toolbar);


        //设置返回键
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置点击拉出侧滑
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = drawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                if (drawerView.getTag().equals("LEFT")) {
                    float leftScale = 1 - 0.3f * scale;
                    mMenu.setScaleX(leftScale);
                    mMenu.setScaleY(leftScale);

                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


        //设置伸缩栏
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.ctool);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.Cbbc9a));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //到底部时会触发
                if (verticalOffset == 0) {
                    text_show.setVisibility(View.VISIBLE);
                    text_blakborad.setVisibility(View.GONE);
                }
                if (verticalOffset > 100) {
                    text_show.setVisibility(View.GONE);
                }
                //到顶部位置附近会触发
                if (Math.abs(verticalOffset) >= (appBarLayout.getTotalScrollRange() - 500)) {
                    text_show.setVisibility(View.GONE);
                    text_blakborad.setVisibility(View.VISIBLE);
                }
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.slide_item1:
                        //查看判断是否输入密码
                        if (JudgeUtils.getIntence(getApplicationContext()).isPass()) {
                            Intent intent = new Intent(getApplicationContext(), ScrollingActivity.class);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent(getApplicationContext(), CheckActivity.class);
                            startActivity(intent);

                        }

                        break;
                    //添加
                    case R.id.slide_item2:
                        Intent intent1 = new Intent(getApplicationContext(), AddSecretActivity.class);
                        startActivity(intent1);

                        break;
                    //我的
                    case R.id.slide_item3:
                        Intent intent3 = new Intent(getApplicationContext(), UserCenterActivity.class);
                        startActivity(intent3);

                        break;
                    //关于
                    case R.id.slide_item4:
                        Intent intent2 = new Intent(getApplicationContext(), AboutActivity.class);
                        startActivity(intent2);
                        break;
                    //定位
                    case R.id.slide_item5:
                        if (!AppUtils.isGrantExternalRW(MainActivity.this)){
                            Toast.makeText(getApplicationContext(),"请手动开权限",Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent4 = new Intent(getApplicationContext(), MapActivity.class);
                            startActivity(intent4);
                        }

                        break;

                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void setData() {

        if (from == FROM_ADD) {
            text_blakborad.setText(nData.getContent());
        }
        if (SPUtils.getInstance(this).getObject("list") == null) {
            ArrayList<TipModel> model = new ArrayList<TipModel>();
            SPUtils.getInstance(this).putObject("list", model);
        }
        if (SPUtils.getInstance(this).getObject("slist") == null) {
            ArrayList<SecModel> smodel = new ArrayList<>();
            SPUtils.getInstance(this).putObject("slist", smodel);
        }

        if (cityText_Location == null){
            cityText_Location = "北京市";
        }
        city.setText(cityText_Location);
        cityText_weather = cityText_Location.substring(0, cityText_Location.length() - 1);
        Log.e("+++++++++++",cityText_weather);
        String key = "606f03bb96be4f48987a5e8582b49fa1";
        ApiWork.getInstance(this).getWeather(cityText_weather, key, new Netlistioner() {
            @Override
            public void onSuccess(Object data) {
                WeatherModel model = (WeatherModel) data;
                if (model!= null){
                    tmp = model.getHeWeather5().get(0).getNow().getTmp();
                    hum = model.getHeWeather5().get(0).getNow().getHum();
                    code = model.getHeWeather5().get(0).getNow().getCond().getCode();
                    text = model.getHeWeather5().get(0).getNow().getCond().getTxt();
                    wind = model.getHeWeather5().get(0).getNow().getWind().getDir()
                            + "，风力" + model.getHeWeather5().get(0).getNow().getWind().getSc() + "级";
//               iv_weather_icon.setImageResource();
                    tv_weather_text.setText(text);
                    tv_weather_tmp.setText("温度：" + tmp + "℃");
                    tv_weather_hum.setText("湿度：" + hum + "%");
                    tv_weather_wind.setText(wind);
                    path = "http://files.heweather.com/cond_icon/" + code + ".png";
                }



                if (text.contains("晴")) {
                    animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    iv_weather_icon.setAnimation(animation);
                    animation.setRepeatCount(2000000);
                    animation.setRepeatMode(Animation.REVERSE);
                    animation.setDuration(2000);
                    animation.start();
                }else{
                    animation = new ScaleAnimation(0.7f, 1f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
                    iv_weather_icon.setAnimation(animation);
                    animation.setRepeatCount(2000000);
                    animation.setRepeatMode(Animation.REVERSE);
                    animation.setDuration(2000);
                    animation.start();
                }
                Glide.with(getApplicationContext())
                        .load(path)
                        .animate(animation)
                        .into(iv_weather_icon);


            }

            @Override
            public void onFailure(String errMsg) {
                Log.e("----------------", "失败");
                Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_text:
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                break;
            case R.id.check_text:
                Intent intent1 = new Intent(this, NoteListActivity.class);
                startActivity(intent1);
            default:
                break;
        }
    }



    private long timeExit = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - timeExit > 2000) {
                timeExit = System.currentTimeMillis();
                Toast.makeText(getApplicationContext(), "再按一下退出", Toast.LENGTH_SHORT).show();
            } else {
                System.exit(0);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

}
