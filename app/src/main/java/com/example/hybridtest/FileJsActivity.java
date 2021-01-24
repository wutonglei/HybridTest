package com.example.hybridtest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;

public class FileJsActivity extends AppCompatActivity {
    RelativeLayout rl;
    int i = 0;
    private static final String TAG = "FileJsActivity";
    ImagePicker imagePicker;
    private static int IMAGE_PICKER=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_js);
        rl = findViewById(R.id.rl);
        initWebView();



        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
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

        webView.addJavascriptInterface(new H5ButtonInterface(), "android");
//        注意有些是htm  有些事html
        webView.loadUrl("file:///android_asset/UpImage/jquery.html");
//没有效果
//        webView.loadUrl("javascript:javaCallJs("+"'"+"sdasdsa"+"'"+")");
//        setContentView(webView);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rl.addView(webView, params);
    }
    class H5ButtonInterface {
        @JavascriptInterface
        public void takePhoto() {
            Intent intent = new Intent(FileJsActivity.this, ImageGridActivity.class);
            startActivityForResult(intent, IMAGE_PICKER);
            Toast.makeText(FileJsActivity.this, "Android 被 Js 调用", Toast.LENGTH_SHORT).show();
        }

         @JavascriptInterface
        public void displayImg(String path) {
            Toast.makeText(FileJsActivity.this, "Android 被 Js 调用", Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void showToast() {
            Toast.makeText(FileJsActivity.this, "Android 被 Js 调用", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                String path = images.get(0).path;
                /*将图片显示在网页上*/
                String method = "javascript:displayImg('" + path + "')";
                webView.loadUrl(method);
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
