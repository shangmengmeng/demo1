package com.anew.note.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.anew.note.R;

import java.util.Random;

/**
 * Created by pig on 2017/3/3.
 */

public class SplashLayout extends RelativeLayout {
    private Drawable[] drawables;
    private Random random;
    private int dWidth, dHeight;
    private int mWidth = 1080, mHeight = 1500;
    private PointF poinF0;
    private PointF pointF1;
    private PointF pointF2;
    private PointF pointF3;
    //多声明几个加速器
    private AccelerateInterpolator acc = new AccelerateInterpolator();
    private AccelerateDecelerateInterpolator acd = new AccelerateDecelerateInterpolator();
    private DecelerateInterpolator add = new DecelerateInterpolator();
    private AnimatorSet set;


    public SplashLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    public SplashLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }
    public void initData() {
        drawables = new Drawable[3];
        drawables[0] = getResources().getDrawable(R.drawable.pao1);
        drawables[1] = getResources().getDrawable(R.drawable.pao2);
        drawables[2] = getResources().getDrawable(R.drawable.pao3);
        dWidth = drawables[0].getIntrinsicWidth();
        dHeight = drawables[0].getIntrinsicHeight();
        random = new Random();


        initView();
    }


    private void initView() {
        final ImageView imageView = new ImageView(getContext());
        imageView.setImageDrawable(drawables[random.nextInt(3)]);
        LayoutParams params = new LayoutParams(dWidth, dHeight);
        addView(imageView, params);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);


       set = getAnimatorSet(imageView);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(imageView);

            }
        });
        //设置拦截器
        Interpolator []interpolators = new Interpolator[3];
        interpolators[0] = acc;
        interpolators[1] = acd;
        interpolators[2] = add;
        set.setInterpolator(interpolators[random.nextInt(3)]);
        set.start();
    }

    private AnimatorSet getAnimatorSet(ImageView imageView) {

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 0.2f, 0.7f);
        scaleX.setRepeatCount(2);
        scaleX.setDuration(5000);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 0.2f, 0.7f);
        scaleY.setRepeatCount(2);
        scaleY.setDuration(5000);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0.3f);
        alpha.setRepeatCount(2);
        alpha.setDuration(5000);
        //贝塞尔曲线动画
        ValueAnimator baser = getBaserAnimator(imageView);
        baser.setRepeatCount(2);
        baser.setDuration(5000);


        //动画集合
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleX, scaleY, alpha, baser);
        return set;
    }

    private ValueAnimator getBaserAnimator(final ImageView imageView) {

        //先需要四个点
        poinF0 = new PointF((mWidth - dWidth) / 2, (mHeight - dHeight));
        pointF3 = new PointF(random.nextInt(mWidth), 0);
        pointF1 = getPointF(1);
        pointF2 = getPointF(2);
        //需要一个baser差值器
        BezierEvaluator baserEvaluator = new BezierEvaluator(pointF1, pointF2);
        ValueAnimator baser = ValueAnimator.ofObject(baserEvaluator, poinF0, pointF3);

        baser.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
            }
        });


        return baser;
    }

    private PointF getPointF(int i) {
        PointF pointF = new PointF();
        pointF.x = random.nextInt(mWidth);
        if (i == 1) {
            pointF.y = random.nextInt(mHeight / 2) + mHeight / 2;
        } else {
            pointF.y = random.nextInt(mHeight / 2);
        }
        return pointF;
    }
    public void stop(){
        set.cancel();
        set.end();
    }
}
