package com.meishipintu.fucaiShopNew.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meishipintu.fucaiShopNew.R;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsDetailActivity extends Activity {

    private String url;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.wv)
    WebView wv;
    @BindView(R.id.pb)
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        title.setText(intent.getBooleanExtra("isNotice",false)?"通知详情":"新闻详情");
        //用于x5 webview 防止视频闪屏
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }

    private void initWebView() {
        wv.setWebViewClient(new WebViewClient());
        wv.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    pb.setVisibility(View.GONE);
                } else {
                    pb.setProgress(newProgress);
                }
            }
        });

        if (url.startsWith("http://")) {
            wv.loadUrl(url);
        } else {
            wv.loadUrl("http://"+url);
        }

        WebSettings ws = wv.getSettings();
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setUseWideViewPort(true);// 可任意比例缩放
        ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.setSaveFormData(true);// 保存表单数据
        ws.setDomStorageEnabled(true);
        wv.setSaveEnabled(false);
        ws.setJavaScriptEnabled(true);
        ws.setSupportZoom(false);
    }

    @OnClick(R.id.bt_return)
    public void onClick() {
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //不可见后暂停网页，防止音乐、视频继续占用资源
        wv.reload();
        wv.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initWebView();      //显示后重新加载网页
    }

    @Override
    public void onBackPressed() {
        //如果网页有返回上一级，则返回，没有则退出页面
        if (wv.canGoBack()) {
            wv.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
