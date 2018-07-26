package com.meishipintu.fucaiShopNew.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;
import com.meishipintu.fucaiShopNew.R;
import com.meishipintu.fucaiShopNew.utils.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActCaptureTicket extends CaptureActivity {

	private static final int XCX_SN = 11;
	private int mCheckCode = 0;
	private int mCheckCodeFrom=-1;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Intent in = getIntent();
		// 0。验证订单获取订单；1.扫描用户支付宝钱包；2。扫描暂存订单到米来支付页面；3.收银扫描红包码，4验证扫红包码/完成路线任务
		mCheckCode = in.getIntExtra("CHECK_CODE", 0);
		mCheckCodeFrom=in.getIntExtra("CHECK_CODE_FROM", -1);
		TextView tv = (TextView) findViewById(R.id.status_view);
		tv.setText("请扫用户手机上的订单二维码");
	}

	/**
	 *     对扫描结果的处理
	 */
	public boolean HandleDecoded(String str) {
		if (!StringUtils.isNullOrEmpty(str)) {

			if (mCheckCode == 1) {
				Intent data = new Intent();
				data.putExtra("dynamicid", str);
				data.putExtra("CHECK_CODE_FROM", mCheckCodeFrom);
				Log.d("ActCaptureTicket", "扫描完毕，mCheckCodeFrom="+Integer.toString(mCheckCodeFrom));
				this.setResult(RESULT_OK, data);
				finish();
				return true;
			}
			if(mCheckCode==2)
			{
				String temp=str.replace(" ", "");
				if(temp.length()==12&&isNumeric(temp))
				{
					Intent in=new Intent();
					in.putExtra("COUPON_SN", temp);
					this.setResult(RESULT_OK, in);
					finish();
					return true;
				}
			}
			if(mCheckCode==4) {
				Log.d("test", "scan result :" + str);
				//12位数字为红包文件SN码,14位数字为中间有空格
				if(str!=null&&(str.length()==12||str.length()==14)) {
					String temp=str.replace(" ", "");
					if(temp.length()==12)
					{
						Intent data=new Intent();
						data.putExtra("CSN", str);
						ActCaptureTicket.this.setResult(RESULT_OK, data);
					}else{
						selfToastShow("非米来红包二维码，请确认后重试");
					}
				} else if (str.startsWith("xcx")) {
					Intent data = new Intent();
					data.putExtra("xcx", str);
					ActCaptureTicket.this.setResult(XCX_SN, data);
				} else {
					selfToastShow("无效的红包二维码");
				}
				finish();
				return true;
			}
		}
		Toast.makeText(this, "无效二维码", Toast.LENGTH_LONG).show();
		restartPreviewAfterDelay(0L);
		return false;
	}

	public boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	
	private void selfToastShow(String message) {
		Toast toast = Toast.makeText(this, "", Toast.LENGTH_LONG);
		LayoutInflater infalter = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = infalter.inflate(R.layout.layout_toast, null);
		TextView tvContent = v.findViewById(R.id.tv_toast_content);
		tvContent.setText(message);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setView(v);
		toast.show();
	}
}