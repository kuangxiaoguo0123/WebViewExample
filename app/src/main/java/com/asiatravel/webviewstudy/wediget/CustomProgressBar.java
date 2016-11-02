package com.asiatravel.webviewstudy.wediget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;

import com.asiatravel.webviewstudy.MainActivity;

/**
 * Created by asiatravel on 2016/11/2.
 */

public class CustomProgressBar extends ProgressBar {

    private ProgressEnum loadingState;

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(MainActivity.TAG, "onDraw: left-->" + getLeft());
        Log.d(MainActivity.TAG, "onDraw: top-->" + getTop());
        Log.d(MainActivity.TAG, "onDraw: right-->" + getRight());
        Log.d(MainActivity.TAG, "onDraw: bottom-->" + getBottom());
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(MainActivity.TAG, "onMeasure: height-->" + getMeasuredHeight());
        if (loadingState == ProgressEnum.LOADING_FINISH) {
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(MainActivity.TAG, "onLayout: left-->" + left);
        Log.d(MainActivity.TAG, "onLayout: top-->" + top);
        Log.d(MainActivity.TAG, "onLayout: right-->" + right);
        Log.d(MainActivity.TAG, "onLayout: bottom-->" + bottom);
    }

    public void loadAnimation() {

        ValueAnimator translateAnimator = ValueAnimator.ofFloat(0, getBottom());
        translateAnimator.setDuration(500);
        translateAnimator.setInterpolator(new LinearInterpolator());
        translateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                layout(getLeft(), (int) (getTop() - value), getRight(), (int) (getBottom() - value));
            }
        });
        translateAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                loadingState = ProgressEnum.LOADING_FINISH;
//                setMeasuredDimension(getWidth(), 0);
//                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        translateAnimator.start();
    }

    public enum ProgressEnum {
        IS_LOADING, LOADING_FINISH
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void doObjectAnimation() {
        ObjectAnimator moveAnimator = ObjectAnimator.ofFloat(this, View.TRANSLATION_Z, 0, -100f).setDuration(1000);
        moveAnimator.setInterpolator(new LinearInterpolator());
        moveAnimator.start();
    }
}
