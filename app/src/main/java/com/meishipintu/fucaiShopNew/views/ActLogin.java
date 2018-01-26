package com.meishipintu.fucaiShopNew.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.meishipintu.fucaiShopNew.Cookies;
import com.meishipintu.fucaiShopNew.R;
import com.meishipintu.fucaiShopNew.models.AccountHttpMgr;
import com.meishipintu.fucaiShopNew.models.PostGetTask;
import com.meishipintu.fucaiShopNew.utils.Des2;
import com.meishipintu.fucaiShopNew.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class ActLogin extends Activity {

	private EditText mEtTel = null;
	private EditText mEtPwd = null;
	private int mRemenber = 0;
	private CheckBox cBRemenber = null;
	private int osVersion = 0;
	private int mIsRelogin = 0;
	private TextView tvClearName;
	private TextView tvClearPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("test", "LoginActivity start");
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);
		TextView tv = findViewById(R.id.tv_title);
		tv.setText(getString(R.string.app_name));

		mEtTel = findViewById(R.id.et_username);
		mEtTel.setText(Cookies.getAccount());
		mEtPwd = findViewById(R.id.et_passwd);
		cBRemenber = findViewById(R.id.remenber_password);
		mRemenber = Cookies.getRemenber();
		osVersion = android.os.Build.VERSION.SDK_INT;
		if (mRemenber == 1) {
			String pwdDes = null;
			try {
				pwdDes = Des2.decodeValue("meishipintu", Cookies.getPassword());
			} catch (Exception e) {
				e.printStackTrace();
			}
			mEtPwd.setText(pwdDes);
			cBRemenber.setChecked(true);
		} else {
			mEtPwd.setText("");
			cBRemenber.setChecked(false);
		}
		mEtTel.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				mEtPwd.setText("");
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.length()==0)
				{
					tvClearName.setVisibility(View.INVISIBLE);
				}else{
					tvClearName.setVisibility(View.VISIBLE);
				}
			}
		});
		
		mEtPwd.addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if(s.length()==0)
				{
					tvClearPassword.setVisibility(View.INVISIBLE);
				}else{
					tvClearPassword.setVisibility(View.VISIBLE);
				}
			}});

		tvClearName= findViewById(R.id.tv_clear);
		tvClearPassword= findViewById(R.id.tv_clear_pwd);
		
		tvClearPassword.setOnClickListener(ll);
		tvClearName.setOnClickListener(ll);
		
		findViewById(R.id.btn_back).setOnClickListener(ll);
		findViewById(R.id.tv_forgot_pwd).setOnClickListener(ll);
		findViewById(R.id.bt_login).setOnClickListener(ll);
	}

	private OnClickListener ll = new OnClickListener() {

		Intent in = new Intent();

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_back:
				finishAndAni();
				break;
			case R.id.tv_forgot_pwd:
				in.setClass(ActLogin.this, ActForgotPwd.class);
				startActivity(in);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
				break;

			case R.id.bt_login:
				login();
				break;
			case R.id.tv_clear:
				mEtTel.setText("");
				break;
			case R.id.tv_clear_pwd:
				mEtPwd.setText("");
				break;
			default:
				break;
			}

		}
	};

	private void login() {
		final String tel = mEtTel.getText().toString();
		if (StringUtils.isNullOrEmpty(tel)) {
			Toast.makeText(ActLogin.this,
					getString(R.string.user_name) + "不能为空", Toast.LENGTH_LONG)
					.show();
			return;
		}

		final String pwd = mEtPwd.getText().toString();
		if (StringUtils.isNullOrEmpty(pwd)) {
			Toast.makeText(ActLogin.this,
					getString(R.string.password) + "不能为空", Toast.LENGTH_LONG)
					.show();
			return;
		}

		new PostGetTask<JSONObject>(this, R.string.logining,
				R.string.login_failed, true, true, false) {

			@Override
			protected JSONObject doBackgroudJob() throws Exception {
				Log.i("test", "login return:" + AccountHttpMgr.getInstance().login(tel, pwd).toString());
				return AccountHttpMgr.getInstance().login(tel, pwd);
			}

			@Override
			protected void doPostJob(Exception e, JSONObject result) {

				if (e == null && result != null) {
					try {
						Log.i("test", "login result.result:" + result.toString());

						if (result.getInt("result") == 1) {
							JSONObject uinfo = result
									.getJSONObject("waiterInfo");
							String shopComment = uinfo.getString("shopComment");
							// hcs
							if (shopComment != null || !shopComment.equals("")) {
								Cookies.setShopComment(shopComment);
							}
							String old_account = Cookies.getAccount();
							int waitorType = uinfo.getInt("type");// hcs
							String old_shopId = Cookies.getShopId();
							String savePwd = null;
							if (cBRemenber.isChecked() == false) {
								savePwd = "";
								Cookies.setRemenber(0);
							} else {
								savePwd = pwd;
								Cookies.setRemenber(1);
							}
							Cookies.saveUserInfo(
									uinfo.getString("uid"),
									uinfo.getString("token"), pwd, tel,
									uinfo.getString("nickname"),
									uinfo.getString("shopId"),
									uinfo.getString("shopCode"),
									uinfo.getString("shopName"), waitorType,
									uinfo.getString("shopType"),uinfo.getInt("shopCategory"));

							Intent in = new Intent();
							Log.i("test", "login unifo.shopId:" + uinfo.getLong("shopId"));

							if (uinfo.getLong("shopId") == 0) {
								in.setClass(ActLogin.this, ActBindShop.class);
							} else {
								in.setClass(ActLogin.this, MainActivity.class);
							}
							startActivity(in);
							overridePendingTransition(R.anim.right_in,
									R.anim.left_out);
							finish();
						}
						else {
							Log.i("test", "login else");
							Toast.makeText(ActLogin.this,
									result.getString("msg"), Toast.LENGTH_LONG)
									.show();
						}
					} catch (JSONException e1) {
						Log.i("test", "login JSONException:" + e1);

						Toast.makeText(ActLogin.this,
								getString(R.string.login_failed),
								Toast.LENGTH_LONG).show();
						e1.printStackTrace();
					}
				}
			}
		}.execute();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finishAndAni();
	}

	protected void finishAndAni() {
		finish();
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}
}
