package com.meishipintu.fucaiShopNew.views.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.meishipintu.fucaiShopNew.Cookies;
import com.meishipintu.fucaiShopNew.R;
import com.meishipintu.fucaiShopNew.custom.CustomProgressDialog;
import com.meishipintu.fucaiShopNew.models.HttpMgr;
import com.meishipintu.fucaiShopNew.models.NetCallBack;
import com.meishipintu.fucaiShopNew.models.NetDataHelper;
import com.meishipintu.fucaiShopNew.models.PostGetTask;
import com.meishipintu.fucaiShopNew.models.ServerUrlConstants;
import com.meishipintu.fucaiShopNew.models.bean.Coupons;
import com.meishipintu.fucaiShopNew.models.bean.XcxCouponDetail;
import com.meishipintu.fucaiShopNew.utils.ConstUtil;
import com.meishipintu.fucaiShopNew.utils.DialogUtils;
import com.meishipintu.fucaiShopNew.utils.MyDialogUtil;
import com.meishipintu.fucaiShopNew.utils.StringUtils;
import com.meishipintu.fucaiShopNew.utils.TimeUtil;
import com.meishipintu.fucaiShopNew.views.ActCaptureTicket;
import com.meishipintu.fucaiShopNew.views.ActCouponQuery;

import org.json.JSONException;
import org.json.JSONObject;


public class C0_VipFrag extends Fragment {
	
    private NetDataHelper helper;
	private String mTelNum = null;
	private int mIsFrom = 0;

	private PostGetTask<JSONObject> mGetVerifyTask = null;

	private EditText mEtVerifyTel = null;
	private Button mButtonVerifyTelOrSn = null;
	protected CustomProgressDialog mLoadingDialog;
	private RelativeLayout rlCheckCoupon;

	private StartCameraListener mListener;
	
    public static C0_VipFrag createInstance() {
    	C0_VipFrag f = new C0_VipFrag();
        return f;
    }

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mListener = (StartCameraListener) context;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.frag_vip,null);

        helper = NetDataHelper.getInstance(getContext());
        rlCheckCoupon = view.findViewById(R.id.rl_check_coupon);
		rlCheckCoupon.setOnClickListener(mClickListener);
		mButtonVerifyTelOrSn = view.findViewById(R.id.bt_verify);
		mButtonVerifyTelOrSn.setOnClickListener(mClickListener);

		TextView tvClearTel = view.findViewById(R.id.tv_clear_tel);
		tvClearTel.setOnClickListener(mClickListener);

		mEtVerifyTel = view.findViewById(R.id.et_vip_tel);
		mEtVerifyTel.setFocusable(true);
		mEtVerifyTel.requestFocus();

		TextView tvIconScan= view.findViewById(R.id.tv_icon_scan);
		tvIconScan.setOnClickListener(mClickListener);
		
		mEtVerifyTel.addTextChangedListener(mTextWatcher);		
		return view;
	}
	
	
	private TextWatcher mTextWatcher = new TextWatcher() {

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
			formateNumberAsTelOrSn(s);
		}
	};
	
	
	private void formateNumberAsTelOrSn(Editable s) {
		if(s.length()<3)
		{
			return;
		}
		if(s.charAt(0)=='1')
		{
			if (s.length() > 3 && s.length() <= 13) {
				if (s.charAt(3) != ' ') {
					s.insert(3, " ");
				}
				if (s.length() > 8) {
					if (s.charAt(8) != ' ') {
						s.insert(8, " ");
					}
				}
			}
			if(s.length()>13)
			{
				s.delete(12, s.length() - 1);
				selfToastShow("手机号码长度为11位");
				return;
			}		
		}else {
			if (mIsFrom == 1){
				s.delete(0, s.length());
				selfToastShow("请输入正确的手机号码");
				return;
			}
			if (s.length() > 4 && s.length() <14) {
				if (s.charAt(4) != ' ') {
					s.insert(4, " ");
				}
				if (s.length() > 9) {
					if (s.charAt(9) != ' ') {
						s.insert(9, " ");
					}
				}
			}
			
		}
	}

	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
				case R.id.bt_verify: {
					String getTelString=mEtVerifyTel.getText().toString();
					if(getTelString.length()>0)
					{
						setTel(getTelString);
					}else{
						selfToastShow("输入不能为空");
						return;
					}
					String tel = getTel().replace(" ", "");// 直接从vip进入的方式，此字符串有可能为Sn
					if (tel.startsWith("xcx")) {
						verifySnFromXcx(tel);
					}else if (tel.length() == 12 && mIsFrom != 1)// 等于1的情况下不能验证sn
					{
						verifySnFromNet(tel);
					} else {
						selfToastShow("请输入正确券号");
					}
					break;
				}
				case R.id.tv_icon_scan:{
					mListener.onCameraStart();
					break;
				}
				case R.id.tv_clear_tel: {
					mEtVerifyTel.setText("");
					break;
				}
				case R.id.rl_check_coupon:
					//进入验证卡券页面
					startActivity(new Intent(getActivity(), ActCouponQuery.class));
					break;
			}
		}
	};

	//验证来自小程序的券号
	private void verifySnFromXcx(final String tel) {
		helper.getXcxSnDetail(tel, Cookies.getShopId(), new NetCallBack<XcxCouponDetail>() {
			@Override
			public void onSuccess(XcxCouponDetail data) {
				final MyDialogUtil dialogUtil = new MyDialogUtil(getActivity()) {
					@Override
					public void onClickPositive() {
						String waiterId = Cookies.getWaiterId();
						String waiterName = Cookies.getNickName();
						if (Cookies.isMaster()) {
							waiterId = "0";
							waiterName = "店主";
						}
						helper.checkSnFromXcx(tel, Cookies.getShopId(), waiterId,waiterName,new NetCallBack<Boolean>() {
							@Override
							public void onSuccess(Boolean data) {
								if (data) {
                                    DialogUtils.showCustomDialog(getActivity(), "验证成功", null);
                                }
							}

							@Override
							public void onError(String error) {
                                DialogUtils.showCustomDialog(getActivity(), error, null);
                            }
						});
					}

					@Override
					public void onClickNagative() {
					}
				};
				dialogUtil.showCustomTwoChoice("卡券："+data.getRedball_name()+", 是否验证？","现在验证","取消");
			}

			@Override
			public void onError(String error) {
				selfToastShow(error);
			}
		});

	}

	private void verifySnFromNet(final String couponSn) {
		if (couponSn.length() == 0) {
			selfToastShow("券号不能为空");
			return;
		}
		if (couponSn.length() > 12) {
			selfToastShow("券号格式不正确");
			return;
		}
		mLoadingDialog = new CustomProgressDialog(this.getActivity(), "正在验证红包");
		mLoadingDialog.show();
		new PostGetTask<JSONObject>(this.getActivity()) {

			@Override
			protected JSONObject doBackgroudJob() throws Exception {
				JSONObject jParam = new JSONObject();


				jParam.put("totalFee", "9999");// totalfee
				jParam.put("shopId", Cookies.getShopId());// shopid
				jParam.put("token", Cookies.getToken());
				jParam.put("couponSn", couponSn);
				Log.d("zcz",jParam+"");
				JSONObject jRet = null;
				jRet = HttpMgr.getInstance().postJson(
						ServerUrlConstants.getCouponVerifyUrl(), jParam, true);
				Log.i("test", "jsonResult:" + jRet.toString());
				return jRet;
			}

			@Override
			protected void doPostJob(Exception exception, JSONObject result) {
				if (exception == null && result != null) {
					try {
						if (result.getInt("result") == 1) {
							JSONObject jData = result
									.getJSONObject("couponData");
							String couponName = jData.getString("couponName");
							float couponValue = Float.parseFloat(jData
									.getString("couponValue"));
							String couponSnFromNet = jData
									.getString("couponSn");
							String couponId=jData.getString("couponId");
							long startTime = jData.getLong("startTime");
							long endTime = jData.getLong("endTime");
							if (couponSnFromNet.equals(couponSn)) {
								showDialogCouponDetail(couponName, couponValue,
										couponSnFromNet, startTime, endTime,couponId);
							} else {
								selfToastShow("券号不一致");
							}
						} else {
							if (result.has("msg")) {
								String msg = result.getString("msg");
								selfToastShow(msg);
							}
						}
					} catch (JSONException e) {
						selfToastShow( "数据解析失败");
					}
					if (mLoadingDialog.isShowing()) {
						mLoadingDialog.dismiss();
					}
				}
			}
		}.execute();
	}

	private String mCouponSn=null;
	private String mCouponId=null;
	private Dialog mDialogShowDetail= null;

	private void showDialogCouponDetail(String couponName, float couponValue,
			String couponSn, long startTime, long endTime,String couponId) {
		mCouponSn=couponSn;
		mCouponId=couponId;
		mDialogShowDetail= new Dialog(this.getActivity(), R.style.dialog);
		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.dialog_coupon_detail, null);

		TextView tvValue = v.findViewById(R.id.tv_coupon_value);
		tvValue.setText(String.format("%.2f", couponValue));

		LinearLayout llSn = v.findViewById(R.id.ll_coupon_sn);
		llSn.setVisibility(View.VISIBLE);
		
		TextView tvSn = v.findViewById(R.id.tv_coupon_sn);
		String temp=couponSn.substring(0, 4)+" "+couponSn.substring(4, 8)+" "+couponSn.substring(8, couponSn.length());
		tvSn.setText(temp);
		
		TextView tvSep1= v.findViewById(R.id.tv_sep_1);
		tvSep1.setVisibility(View.GONE);

		String timeEndString = TimeUtil.convertLongToFormatString(
				endTime * 1000, "yyyy.MM.dd");
		String timeStartString = TimeUtil.convertLongToFormatString(
				startTime * 1000, "yyyy.MM.dd");
		String dateLimit = timeStartString + "—" + timeEndString + "可用";

		TextView tvContent = v.findViewById(R.id.tv_coupon_detail);
		tvContent.setVisibility(View.GONE);
		// tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());

		TextView tvDateLimit = v.findViewById(R.id.tv_date_limit);
		tvDateLimit.setText(dateLimit);// 红包可用时间

		Button btUseCoupon = v.findViewById(R.id.bt_use_coupon);
		btUseCoupon.setVisibility(View.VISIBLE);
		btUseCoupon.setOnClickListener(onDialogButtonListener);

		mDialogShowDetail.setContentView(v);
		mDialogShowDetail.show();
	}

	private void clearCoupon()
	{
		mCouponSn="";
		mCouponId="";
		mEtVerifyTel.setText("");
		if (mDialogShowDetail != null && mDialogShowDetail.isShowing()) {
			mDialogShowDetail.dismiss();
		}
	}


	private OnClickListener onDialogButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
				case R.id.bt_use_coupon: {
					useCouponNet(mCouponSn,mCouponId);
					break;
				}
			}
		}
	};

	private void useCouponNet(final String couponSn,final String couponId)
	{
		new PostGetTask<JSONObject>(this.getActivity()) {

			@Override
			protected JSONObject doBackgroudJob() throws Exception {
				JSONObject jsonParams = new JSONObject();
				jsonParams.put("shopId", Cookies.getShopId());
				jsonParams.put("totalFee", 9999);
				jsonParams.put("uid", Cookies.getUserId());
				jsonParams.put("token", Cookies.getToken());
				jsonParams.put("couponSn", couponSn);
				jsonParams.put("couponId", couponId);
				jsonParams.put("type", 1);		//仅使用卡券
				Log.d("jsonParams",jsonParams.toString());
				JSONObject jRet = null;
				jRet = HttpMgr.getInstance().postJson(
						ServerUrlConstants.getUseCouponResult(), jsonParams, true);
				Log.i("test", "jRet:" + jRet.toString());
				return jRet;
			}

			@Override
			protected void doPostJob(Exception exception, JSONObject result) {
				if (exception == null && result != null) {
					try {
						if (result.getInt("result") == 1) {
							Log.d("zcz", "验证成功");
							selfToastShow("红包验证使用成功");
							clearCoupon();

							//qDialog.showCustomMessageOK(getActivity().getBaseContext().getString(R.string.notice),"红包验证使用成功", "知道了");
						}else{
							String msg="红包使用失败";
							if(result.has("msg"))
							{
								try{
									msg=result.getString("msg");
								}catch(Exception e){
									
								}
								
							}
							selfToastShow(msg);

						}
					} catch (JSONException e) {
						selfToastShow("数据解析失败");
					}
				}
			}
		}.execute();
	}

	private void selfToastShow(String message)
	{
		Toast toast=Toast.makeText(this.getActivity().getBaseContext(), "", Toast.LENGTH_LONG);
		LayoutInflater infalter=(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v=infalter.inflate(R.layout.layout_toast, null);
		TextView tvContent= v.findViewById(R.id.tv_toast_content);
		tvContent.setText(message);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setView(v);
		toast.show();
	}

	private void setTel(String tel) {
		if (tel.length() > 0) {
			this.mTelNum = tel;
		}
	}

	private String getTel() {
		return mTelNum;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==Activity.RESULT_OK) {
			switch(requestCode) {
				case 50:
					if(data!=null)
					{
						String sn=data.getStringExtra("CSN");
						mEtVerifyTel.setText(sn);
					}else{
						selfToastShow("取消扫码");
					}
					break;
				case ConstUtil.REQUEST_CODE.PAY_REQUEST_VIP_INFO:
					this.getActivity().setResult(Activity.RESULT_OK,data);
					//finish();
					break;
			}

		} else if (resultCode == 11) {		//小程序码
			if(data!=null)
			{
				String sn=data.getStringExtra("xcx");
				mEtVerifyTel.setText(sn);
			}else{
				selfToastShow("取消扫码");
			}
		} else if (requestCode == Activity.RESULT_CANCELED) {
			Log.i("test","back");
		}
	}
	
}
