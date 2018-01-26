package com.meishipintu.fucaiShopNew;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.meishipintu.fucaiShopNew.models.HttpMgr;
import com.meishipintu.fucaiShopNew.views.ActLogin;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by Administrator on 2018/1/24.
 * <p>
 * 主要功能：
 */

public class MyApplication extends MultiDexApplication {

    public static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = MyApplication.this;
        HttpMgr.getInstance().setContext(instance, ActLogin.class);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
        //webview Bugly上传策略
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setCrashHandleCallback(new CrashReport.CrashHandleCallback() {
            public Map onCrashHandleStart(int crashType, String errorType, String errorMessage, String errorStack) {
                LinkedHashMap map = new LinkedHashMap();
                String x5CrashInfo = com.tencent.smtt.sdk.WebView.getCrashExtraMessage(MyApplication.this);
                map.put("x5crashInfo", x5CrashInfo);
                return map;
            }

            @Override
            public byte[] onCrashHandleStart2GetExtraDatas(int crashType, String errorType, String errorMessage, String errorStack) {
                try {
                    return "Extra data.".getBytes("UTF-8");
                } catch (Exception e) {
                    return null;
                }
            }
        });
        //初始化bugly
        Bugly.init(getApplicationContext(), "0c4266600d", false, strategy);
    }
}
