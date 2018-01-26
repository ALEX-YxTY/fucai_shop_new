package com.meishipintu.fucaiShopNew.utils;

import android.content.Context;
import android.os.Environment;


import java.io.File;

public class DownloadMgr {

    private static DownloadMgr instance = new DownloadMgr();

    private DownloadMgr() {
    }

    public static DownloadMgr getInstance() {
        return instance;
    }

    public static String urlToLocalPath(Context context, String url) {
    	String dst = getDownloadCacheDirPath(context) + File.separator + Crc64Util.Crc64(url);
        return dst;
    }

    public static String getDownloadCacheDirPath(Context context) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return Environment.getExternalStorageDirectory() + File.separator + "mspt" + File.separator + "download";
        } else {
            return context.getCacheDir().getAbsolutePath() + File.separator + "download";
        }
    }
    
}
