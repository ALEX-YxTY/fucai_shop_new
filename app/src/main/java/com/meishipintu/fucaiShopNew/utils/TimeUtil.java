package com.meishipintu.fucaiShopNew.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    static SimpleDateFormat format = new SimpleDateFormat();
    public static String convertLongToFormatString(long time, String formatString) {        
        Date currentTime = new Date(time);
        format.applyPattern(formatString);
        return format.format(currentTime);
    }
    
    //鍙埌鍒�
    public static String convertSecToDayTime(long sec){
    	if (sec < 60){
    		return sec + "绉�";
    	}else if (sec < 60*60){
    		int m = (int) (sec / (60));
    		int s = (int) (sec % (60));
    		return m + "鍒�" + s + "绉�";
    	}else if (sec < 24*60*60){
    		int h = (int) (sec / (60*60));
    		sec = sec - h*60*60;
    		int m = (int) (sec / (60));
    		int s = (int) (sec % (60));
    		return h+"灏忔椂" +m + "鍒�" + s + "绉�";
    	}else {
    		int d = (int) (sec/(24*60*60));
    		sec = sec - d*24*60*60;
    		int h = (int) (sec / (60*60));
    		sec = sec - h*60*60;
    		int m = (int) (sec / (60));
    		int s = (int) (sec % (60));
    		return d+"澶�"+h+"灏忔椂" +m + "鍒�" + s + "绉�";
    	}
    }

}
