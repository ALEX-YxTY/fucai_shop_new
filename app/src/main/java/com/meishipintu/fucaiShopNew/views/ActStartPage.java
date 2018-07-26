package com.meishipintu.fucaiShopNew.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.meishipintu.fucaiShopNew.Cookies;
import com.meishipintu.fucaiShopNew.R;
import com.meishipintu.fucaiShopNew.utils.ConstUtil;
import com.meishipintu.fucaiShopNew.utils.StringUtils;


public class ActStartPage extends AppCompatActivity {

	private Handler handler = new Handler();

	private Runnable startPgRun = new Runnable() {

		@Override
		public void run() {
			Intent intent = new Intent();
			/*else if (Cookies.getCityId() == 0){
				intent.putExtra("first_use", true);
				intent.setClass(ActStartPage.this, ActCitySel.class);
			}*/
			//缓存中有用户ID，并且用户选择记住密码后方可自动登录
			if (!StringUtils.isNullOrEmpty(Cookies.getShopId()) && Cookies.getUserId().length() > 0
					&& Cookies.getRemenber()&&Cookies.isMaster()) {
				intent.setClass(ActStartPage.this, MainActivity.class);
			} else {
				intent.setClass(ActStartPage.this, ActLogin.class);
			}
			
			startActivity(intent);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("test", "startPage start");
		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
//				, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
		setContentView(R.layout.layout_start_page);
		handler.postDelayed(startPgRun, ConstUtil.DYNAMIC_START_PAGE_TIMEOUT);
	}

}
