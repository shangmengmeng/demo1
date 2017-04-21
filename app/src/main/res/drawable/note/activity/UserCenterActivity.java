package com.anew.note.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anew.note.R;
import com.anew.note.other.PickerLoader;
import com.anew.note.utils.SPUtils;
import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import java.io.File;
import java.util.ArrayList;

public class UserCenterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_image,btn_name,btn_zhuye,btn_save;
    private TextView text_name,text_zhuye;
    private EditText edit_name,edit_zhuye;
    private ImageView imageView_person;
    private final static  int IMAGE_PICKER = 1001;
    private ArrayList<ImageItem> images;
    private boolean isNameEdit = false;
    private boolean isMainEdit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        initView();
    }

    private void initView() {
        btn_image = (Button) findViewById(R.id.btn_image_1);
        btn_image.setOnClickListener(this);
        imageView_person = (ImageView) findViewById(R.id.profile_image);

        text_name = (TextView) findViewById(R.id.text_name_1);
        text_zhuye = (TextView) findViewById(R.id.text_call_1);
        edit_name = (EditText) findViewById(R.id.edit_name );
        edit_zhuye = (EditText) findViewById(R.id.edit_call);
        btn_name = (Button) findViewById(R.id.btn_change_name);
        btn_zhuye = (Button) findViewById(R.id.btn_change_call);
        btn_save = (Button) findViewById(R.id.btn_cente_save);
        btn_save.setOnClickListener(this);
        btn_name.setOnClickListener(this);
        btn_zhuye.setOnClickListener(this);
        if (SPUtils.getInstance(this).getStringValue("name")!=null){
            text_name.setText(SPUtils.getInstance(this).getStringValue("name"));
        }
        if (SPUtils.getInstance(this).getStringValue("zhuye")!=null){
            text_zhuye.setText(SPUtils.getInstance(this).getStringValue("zhuye"));
        }

        if (SPUtils.getInstance(this).getStringValue("image")!=null){
            String path =SPUtils.getInstance(this).getStringValue("image");
            Glide.with(this)
                    .load(path)
                    .into(imageView_person);
        }

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PickerLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_image_1:
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent,IMAGE_PICKER);
                break;
            case R.id.btn_change_name:
                text_name.setVisibility(View.GONE);
                edit_name.setVisibility(View.VISIBLE);
                btn_save.setVisibility(View.VISIBLE);
                isNameEdit = true;
                break;
            case R.id.btn_change_call:
                text_zhuye.setVisibility(View.GONE);
                edit_zhuye.setVisibility(View.VISIBLE);
                btn_save.setVisibility(View.VISIBLE);
                isMainEdit = true;
                break;
            case R.id.btn_cente_save:
                if (isNameEdit){
                    if (TextUtils.isEmpty(edit_name.getText())){
                        Toast.makeText(this,"昵称不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        SPUtils.getInstance(this).putStringValue("name",edit_name.getText().toString().trim());

                    }

                }
                if (isMainEdit){
                    if (TextUtils.isEmpty(edit_zhuye.getText())){
                        Toast.makeText(this,"昵称不能为空",Toast.LENGTH_SHORT).show();
                    }else {
                        SPUtils.getInstance(this).putStringValue("zhuye",edit_zhuye.getText().toString().trim());

                    }
                }
                edit_name.setVisibility(View.GONE);
                text_name.setVisibility(View.VISIBLE);
                edit_zhuye.setVisibility(View.GONE);
                text_zhuye.setVisibility(View.VISIBLE);
                btn_save.setVisibility(View.GONE);
                initView();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS){
            Log.e("----------","啊卡卡卡");
            if (data!=null&&requestCode==IMAGE_PICKER){
                ArrayList<ImageItem> images = (ArrayList<ImageItem>)
                        data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                String path2 =images.get(0).path;
                if (path2 != null) {
                    File file1 = new File(path2);
                    path2 = file1.getAbsolutePath();
                }
                //存储照片位置
                SPUtils.getInstance(getApplicationContext()).putStringValue("image",path2);
                ImagePicker.getInstance().getImageLoader().displayImage
                        (this,path2,imageView_person,imageView_person.getWidth(),imageView_person.getHeight());
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
