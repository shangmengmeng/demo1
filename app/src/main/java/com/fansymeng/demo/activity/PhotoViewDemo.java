package com.fansymeng.demo.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fansymeng.demo.R;
import com.fansymeng.demo.adapter.PhotoViewAdapter;
import com.fansymeng.demo.view.HackyViewPager;

public class PhotoViewDemo extends AppCompatActivity {
    private PhotoViewAdapter adapter;
    private HackyViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view_demo);
        int[]images={R.drawable.person,R.drawable.person,R.drawable.person};
        viewPager = (HackyViewPager) findViewById(R.id.vp_photo);
        adapter = new PhotoViewAdapter(images);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
