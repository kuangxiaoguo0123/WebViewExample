package com.asiatravel.webviewstudy;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.asiatravel.webviewstudy.wediget.CustomProgressBar;

public class MainActivity extends AppCompatActivity {

    public static final String BAIDU_URL = "http://www.baidu.com";
    public static final String BLOG_URL = "http://blog.csdn.net/kuangxiaoguo0123";
    public static final String JEKE_URL = "http://www.jikexueyuan.com";
    public static final String GOOGLE_URL = "http://www.google.com";
    public static final String BING_URL = "http://www.bing.com";
    public static final String TAG = "TAG";

    private WebView webView;
    private CustomProgressBar progressBar;
    private LinearLayout animationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        animationLayout = (LinearLayout) findViewById(R.id.layout);
        progressBar = (CustomProgressBar) findViewById(R.id.progress_bar);

        loadUrl(BING_URL);
//        loadUrl(BLOG_URL);
//        loadUrl(JEKE_URL);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void loadUrl(String url) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d(TAG, "onProgressChanged: newProgress-->" + newProgress);
                progressBar.setProgress(newProgress);
//                progressBar.setVisibility(newProgress == 100 ? View.GONE : View.VISIBLE);
                if (newProgress == 100) {
//                    progressBar.loadAnimation();
//                    progressBar.doObjectAnimation();
//                    loadAnimation();
//                    progressBar.setVisibility(View.GONE);
//                    loadAnimation();
                    doObjectAnimator();
                }
            }
        });

        webView.loadUrl(url);
    }

    public void doObjectAnimator() {
        ObjectAnimator moveAnimator = ObjectAnimator.ofFloat(animationLayout, View.TRANSLATION_Y, 0, -animationLayout.getBottom()).setDuration(1000);
        moveAnimator.setInterpolator(new LinearInterpolator());
        moveAnimator.start();
    }

    private void loadAnimation() {
//        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(progressBar, "scaleY", 1f, 0).setDuration(500);
//        scaleAnimator.start();
//        ValueAnimator translateAnimator = ValueAnimator.ofFloat(0, progressBar.getBottom());
        ValueAnimator translateAnimator = ValueAnimator.ofFloat(0, animationLayout.getBottom());
        translateAnimator.setDuration(1000);
        translateAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        translateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
//                progressBar.layout(progressBar.getLeft(), (int) (progressBar.getTop() - value), progressBar.getRight(), (int) (progressBar.getBottom() - value));
                animationLayout.layout(animationLayout.getLeft(), animationLayout.getTop(), animationLayout.getRight(), (int) (animationLayout.getBottom() - value));
//                progressBar.invalidate();
                Log.d(TAG, "onAnimationUpdate: top--->" + progressBar.getTop());
                Log.d(TAG, "onAnimationUpdate: value--->" + value);
                Log.d(TAG, "onAnimationUpdate: bottom--->" + progressBar.getBottom());
                Log.d(TAG, "onAnimationUpdate: left--->" + progressBar.getLeft());
                Log.d(TAG, "onAnimationUpdate: right--->" + progressBar.getRight());
            }
        });
        translateAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                progressBar.setVisibility(View.GONE);
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

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: top--->" + progressBar.getTop());
        Log.d(TAG, "onResume: bottom--->" + progressBar.getBottom());
        Log.d(TAG, "onResume: left--->" + progressBar.getLeft());
        Log.d(TAG, "onResume: right--->" + progressBar.getRight());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG, "onWindowFocusChanged: top--->" + progressBar.getTop());
        Log.d(TAG, "onWindowFocusChanged: bottom--->" + progressBar.getBottom());
        Log.d(TAG, "onWindowFocusChanged: left--->" + progressBar.getLeft());
        Log.d(TAG, "onWindowFocusChanged: right--->" + progressBar.getRight());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class JsObject {
        @Override
        public String toString() {
            return super.toString();
        }
    }
}
