package com.meishipintu.fucaiShopNew.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meishipintu.fucaiShopNew.Cookies;
import com.meishipintu.fucaiShopNew.R;
import com.meishipintu.fucaiShopNew.custom.CustomChooseDialog;
import com.meishipintu.fucaiShopNew.utils.MyDialogUtil;
import com.meishipintu.fucaiShopNew.views.ActCouponQuery;
import com.meishipintu.fucaiShopNew.views.ActSetting;
import com.meishipintu.fucaiShopNew.views.DebetListActivity;
import com.meishipintu.fucaiShopNew.views.MainActivity;
import com.meishipintu.fucaiShopNew.views.MyWaiterActivity;
import com.meishipintu.fucaiShopNew.views.WebActivity;


public class D0_MineFrag extends Fragment {


	public static D0_MineFrag createInstance() {
		D0_MineFrag f = new D0_MineFrag();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_mine, null);
		TextView shop_name= view.findViewById(R.id.shop_name);
		shop_name.setText(Cookies.getShopName());
		TextView tel= view.findViewById(R.id.tel);
		TextView waiterName = view.findViewById(R.id.tv_waiter);
		tel.setText(Cookies.getShopTel());
		if (Cookies.isMaster()) {
			waiterName.setVisibility(View.GONE);
		} else {
			waiterName.setText(Cookies.getNickName());
		}
		view.findViewById(R.id.rl_coupon).setOnClickListener(ll);
		view.findViewById(R.id.rl_my_waiter).setOnClickListener(ll);
		view.findViewById(R.id.rl_setting).setOnClickListener(ll);
		view.findViewById(R.id.rl_logout).setOnClickListener(ll);
		view.findViewById(R.id.rl_recommendation).setOnClickListener(ll);

		return view;
	}

	private OnClickListener ll = new OnClickListener() {
		Intent intent = new Intent();

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
				//已验卡券页
				case R.id.rl_coupon:
					intent.setClass(getActivity(), ActCouponQuery.class);
					startActivity(intent);
					getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
					break;
				case R.id.rl_recommendation:
					intent.setClass(getActivity(), DebetListActivity.class);
					startActivity(intent);
					getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
					break;
				case R.id.rl_setting:
//					intent.setClass(getActivity(), ActSetting.class);
					intent.setClass(getActivity(), WebActivity.class);
					startActivity(intent);
					getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
					break;
				case R.id.rl_logout:
					((MainActivity)getActivity()).showDialogQuit(1);
					break;
				case R.id.rl_my_waiter:
					if (Cookies.isMaster()) {
						intent.setClass(getActivity(), MyWaiterActivity.class);
						startActivity(intent);
						getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
					} else {
						//弹窗提示
						CustomChooseDialog dialog = new CustomChooseDialog(getActivity(), R.style.CustomDialog, 1);
						dialog.show();
					}
					break;
				default:
					break;
			}

		}
	};
}
