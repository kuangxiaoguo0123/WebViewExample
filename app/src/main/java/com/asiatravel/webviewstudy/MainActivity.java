package com.asiatravel.webviewstudy;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String BAIDU_URL = "http://www.baidu.com";
    public static final String BLOG_URL = "http://blog.csdn.net/kuangxiaoguo0123";
    public static final String JEKE_URL = "http://www.jikexueyuan.com";
    public static final String GOOGLE_URL = "http://www.google.com";
    public static final String BING_URL = "http://www.bing.com";
    public static final String TAG = "TAG";

    private WebView webView;
    private ProgressBar progressBar;
    private LinearLayout animationLayout;
    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        animationLayout = (LinearLayout) findViewById(R.id.layout);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        initButton();

        loadUrl(BING_URL);
    }

    private void initButton() {
        Button backButton = (Button) findViewById(R.id.back_button);
        Button refreshButton = (Button) findViewById(R.id.refresh_button);
        titleTextView = (TextView) findViewById(R.id.title_textView);
        MyListener myListener = new MyListener();
        backButton.setOnClickListener(myListener);
        refreshButton.setOnClickListener(myListener);
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
                titleTextView.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    loadAnimation();
                }
            }
        });

        webView.loadUrl(url);
    }

    private void loadAnimation() {
        ValueAnimator translateAnimator = ValueAnimator.ofFloat(0, animationLayout.getBottom());
        translateAnimator.setDuration(1000);
        translateAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        translateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                animationLayout.layout(animationLayout.getLeft(), animationLayout.getTop(), animationLayout.getRight(), (int) (animationLayout.getBottom() - value));
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back_button:
                    if (webView.canGoBack()) {
                        webView.goBack();
                        return;
                    }
                    finish();
                    break;
                case R.id.refresh_button:
                    webView.reload();
                    break;
            }
        }
    }

}
