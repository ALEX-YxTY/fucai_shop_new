package com.meishipintu.fucaiShopNew.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.meishipintu.fucaiShopNew.Cookies;
import com.meishipintu.fucaiShopNew.R;


public class RecoDebetActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "fucaiB-recoDebet";
    private WebView wv;
    private ProgressBar pb;
    private String url;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendate_debet);

        wv = findViewById(R.id.wv);
        pb = findViewById(R.id.pb);
        findViewById(R.id.tv_record).setOnClickListener(this);
        findViewById(R.id.bt_return).setOnClickListener(this);
        type = getIntent().getIntExtra("type", 1);
        initWebView();
    }

    private void initWebView() {
        switch (type) {
            case 1:
                url = "http://fucai.milaipay.com/Home/info/newbet.html";
                break;
            case 2:
                url = "http://fucai.milaipay.com/Home/info/3dtj.html";
                break;
            case 3:
                url = "http://fucai.milaipay.com/Home/info/7lctj.html";
                break;
        }
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
        Log.d(TAG, "shopid:" + Cookies.getShopId());
        wv.loadUrl(url + "?shopid=" + Cookies.getShopId());

        WebSettings ws = wv.getSettings();

        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setPluginState(WebSettings.PluginState.ON);
        ws.setAllowFileAccess(true);
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);              // 排版适应屏幕
        ws.setUseWideViewPort(true);                                                    // 可任意比例缩放
        ws.setLoadWithOverviewMode(true);                                               // setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.setSaveFormData(true);                                                       // 保存表单数据
        ws.setDomStorageEnabled(true);
        wv.setSaveEnabled(false);
        ws.setJavaScriptEnabled(true);
        ws.setSupportZoom(false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_return) {
            onBackPressed();
        } else if (v.getId() == R.id.tv_record) {
            Intent intent = new Intent(this, RecoActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
        }
    }
}
