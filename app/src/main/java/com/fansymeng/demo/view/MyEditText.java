package com.fansymeng.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.EditText;

import com.fansymeng.demo.R;

/**
 * Created by pig on 2017/4/20.
 */

public class MyEditText extends EditText {
    /**
     * 三种类型的输入框
     * type1：普通类型
     * type2：自带清除功能的类型
     * type3：自带密码查看的类型
     */

    private static final int TYPE_NORMAL = -1;
    private static final int TYPE_CAN_CLEAR = 0;
    private static final int TYPE_CAN_WATCH = 1;

    /**
     * 右侧图标的Drawable
     */
    private Drawable rightClearDrawable;
    private Drawable rightEyeShowDrawable;
    private Drawable rightEyeCloseDrawable;

    /**
     * 功能的类型
     * 默认为-1，没有功能
     * 0，带有清除文本功能
     * 1，带有查看密码功能
     */
    private int type;
    private TypedArray ta;

    /**
     * 是否开启查看密码，默认没有
     */
    private boolean eyeOpen = false;
    /**
     * 关闭查看密码图标的资源id
     */
    private int eyeCloseResourseId;
    /**
     * 开启查看密码图标的资源id
     */
    private int eyeOpenResourseId;


    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context,attrs,android.R.attr.editTextStyle);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //绑定attr
        ta = context.obtainStyledAttributes(attrs,R.styleable.MyEditText);
        type = ta.getInt(R.styleable.MyEditText_funcType,TYPE_NORMAL);

        eyeCloseResourseId = ta.getResourceId(R.styleable.MyEditText_eyeClose,R.mipmap.eye_close);
        eyeOpenResourseId = ta.getResourceId(R.styleable.MyEditText_eyeOpen,R.mipmap.eye_open);

        init();
    }

    private void init() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片,左上右下
        Drawable leftDrawble = getCompoundDrawables()[0];
        rightClearDrawable = getCompoundDrawables()[2];

        //如果右侧没有图标
        if (rightClearDrawable == null){
           if (type == TYPE_CAN_CLEAR){
               rightClearDrawable = getResources().getDrawable(R.drawable.delete_selector);
           }
           else if (type == TYPE_CAN_WATCH){
               //有查看密码功能，设置默认查看密码功能
               rightEyeCloseDrawable = getResources().getDrawable(eyeCloseResourseId);
               rightEyeShowDrawable = getResources().getDrawable(eyeOpenResourseId);

           }

        }
    }
}
