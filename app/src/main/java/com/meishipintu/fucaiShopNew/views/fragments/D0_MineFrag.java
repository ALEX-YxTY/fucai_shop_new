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
import com.meishipintu.fucaiShopNew.views.ActCouponQuery;
import com.meishipintu.fucaiShopNew.views.ActSetting;
import com.meishipintu.fucaiShopNew.views.DebetListActivity;
import com.meishipintu.fucaiShopNew.views.MainActivity;


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
		tel.setText(Cookies.getShopTel());
		view.findViewById(R.id.rl_coupon).setOnClickListener(ll);
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
					intent.setClass(getActivity(), ActSetting.class);
					startActivity(intent);
					getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
					break;
				case R.id.rl_logout:
					((MainActivity)getActivity()).showDialogQuit();
					break;
				default:
					break;
			}

		}
	};
}
