package com.meishipintu.fucaiShopNew.views

import android.annotation.SuppressLint
import android.graphics.PixelFormat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.meishipintu.fucaiShopNew.R


class WebActivity : AppCompatActivity() {

    private val web: WebView by lazy{ findViewById<WebView>(R.id.wb)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        window.addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)
        window.setFormat(PixelFormat.TRANSLUCENT)
        initWb()

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWb() {
        web.webChromeClient = WebChromeClient()
        web.webViewClient = WebViewClient()
        web.loadUrl("http://www.inke.cn/live.html?uid=706370861&liveid=1531481962901106&ctime=1531481962&share_uid=706370861&share_time=1531481986&share_from=wechat&from=singlemessage&isappinstalled=0")


        val ws = web.settings
        ws.mediaPlaybackRequiresUserGesture = false
        ws.javaScriptCanOpenWindowsAutomatically = true
        ws.allowFileAccess = true
        ws.builtInZoomControls = true// 隐藏缩放按钮
        ws.useWideViewPort = true// 可任意比例缩放
        ws.loadWithOverviewMode = true// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.saveFormData = true// 保存表单数据
        ws.domStorageEnabled = true
        web.isSaveEnabled = false
        ws.javaScriptEnabled = true
        ws.setSupportZoom(false)
        web.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        ws.pluginState = WebSettings.PluginState.ON
    }

    override fun onResume() {
        super.onResume()
        web.onResume()
    }

    override fun onPause() {
        super.onPause()
        web.onPause()
    }

    override fun onBackPressed() {
        if (web.canGoBack()) {
            web.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
