package com.example.hybridtest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class JavaAndJsActivity extends AppCompatActivity {
    RelativeLayout rl;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_call_js);

        rl = findViewById(R.id.rl);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                //注意这里必须延后加载 不能直接放数据sdasdsa 传入的数据
//                javaCallJs对应h5的方法
                webView.loadUrl("javascript:javaCallJs('梧桐+" + i + "')");
//                setContentView(webView);
//                webView.loadUrl("javascript:javaCallJs(" + "'" + "sdasdsa" + "'" + ")");
//                setContentView(webView);
            }
        });
//        WebViewUtil myWebView = new WebViewUtil(this);
//        ////        myWebView.setProgress(pb);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        myWebView.intoView(rl, params);
//        myWebView.loadUrl("https://www.baidu.com/s?wd=213&ie=UTF-8");

/**
 * 1.优先使用本地缓存功能
 * 2.清理本地缓存
 * Created by jiuman on 2019/10/14.
 * 使用注意
 * 使用完调用 onDestroy
 * <p>
 * myWebView = new WebViewUtil(this);
 * ////        myWebView.setProgress(pb);
 *
 * myWebView.intoView(rl, params);
 * myWebView.loadUrl("https://www2.9man.com//syncshuxe//start.html?path=3-1");
 */

        initWebView();
    }

    WebView webView;


    private void initWebView() {

        webView = new WebView(this);
        WebSettings settings = webView.getSettings();

        //支持双击-前提是页面要支持才显示
//        webSettings.setUseWideViewPort(true);

        //支持缩放按钮-前提是页面要支持才显示
        settings.setBuiltInZoomControls(true);

        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("https://www.baidu.com/s?wd=213&ie=UTF-8");

        webView.addJavascriptInterface(new H5ButtonInterface(), "Android");
//        注意有些是htm  有些事html
        webView.loadUrl("file:///android_asset/JsCallJavaCallPhone.html");
//没有效果
//        webView.loadUrl("javascript:javaCallJs("+"'"+"sdasdsa"+"'"+")");
//        setContentView(webView);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rl.addView(webView, params);
    }

    private static final String TAG = "JavaAndJsActivity";
    class H5ButtonInterface {
        @JavascriptInterface
        public void showToast() {
            Toast.makeText(JavaAndJsActivity.this, "Android 被 Js 调用", Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void playVideo(int itemid, String videourl, String itemtitle) {
//            playVideo(itemid, videourl, itemtitle)
            Toast.makeText(JavaAndJsActivity.this, "Android 被 Js 调用" + itemid + videourl + itemtitle, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void showcontacts() {
            Log.i(TAG, "showcontacts: ");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String json = "[{\"name\":\"阿福\", \"phone\":\"18600012345\"}]";
                    // 调用JS中的方法
                    webView.loadUrl("javascript:show('" + json + "')");
                }
            });
        }

        @JavascriptInterface
        public void call(String t) {
//            playVideo(itemid, videourl, itemtitle)
            Toast.makeText(JavaAndJsActivity.this, "Android 被 Js 调用" + t, Toast.LENGTH_SHORT).show();
        }

    }
}
