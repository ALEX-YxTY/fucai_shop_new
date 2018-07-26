package com.meishipintu.fucaiShopNew.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.meishipintu.fucaiShopNew.Cookies;
import com.meishipintu.fucaiShopNew.R;
import com.meishipintu.fucaiShopNew.custom.CanLoadMoreRecyclerView;
import com.meishipintu.fucaiShopNew.custom.CustomDatePickeDialog;
import com.meishipintu.fucaiShopNew.custom.CustomNumPickeDialog;
import com.meishipintu.fucaiShopNew.models.NetCallBack;
import com.meishipintu.fucaiShopNew.models.NetDataHelper;
import com.meishipintu.fucaiShopNew.models.bean.RewardByMonth;
import com.meishipintu.fucaiShopNew.models.bean.RewardData;
import com.meishipintu.fucaiShopNew.models.bean.RewardDetail;
import com.meishipintu.fucaiShopNew.models.bean.RewardRecord;
import com.meishipintu.fucaiShopNew.utils.DensityUtils;
import com.meishipintu.fucaiShopNew.utils.StringUtils;
import com.meishipintu.fucaiShopNew.views.adaptersAndViewholder.RewardAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class E0_RewardFrag extends Fragment implements CustomNumPickeDialog.OnOkClickListener,CustomDatePickeDialog.OnDatePickListener
        ,CanLoadMoreRecyclerView.StateChangedListener{

    private static E0_RewardFrag instance = null;
    @BindView(R.id.tv_last_month_money)
    TextView tvLastMonthMoney;
    @BindView(R.id.tv_this_month_money)
    TextView tvThisMonthMoney;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_last_day)
    TextView tvLastDay;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_next_day)
    TextView tvNextDay;
    @BindView(R.id.tv_now)
    TextView tvNow;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_store_number)
    TextView tvStoreNumber;
    @BindView(R.id.tv_recharge_number)
    TextView tvRechargeNumber;
    @BindView(R.id.tv_store_money)
    TextView tvStoreMoney;
    @BindView(R.id.tv_recharge_money)
    TextView tvRechargeMoney;
    @BindView(R.id.rv)
    CanLoadMoreRecyclerView rv;
    @BindView(R.id.back)
    View back;
    @BindView(R.id.rl_main)
    View rlMain;

    private Calendar calendarCheck;
    private long startTime, endTime;
    private CustomDatePickeDialog datePickeDialog;
    private NetDataHelper netDataHelper;
    private List<RewardData> dataList;
    private RewardAdapter adapter;


    private int dateType = 2;  //1-按月查询 2-按日查询
    private int couponType = 1;  //1-全部分类 2-杂货铺 3-充值中心
    private String shopId;

    public static E0_RewardFrag createInstance() {
        if (instance == null) {
            instance = new E0_RewardFrag();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_reward, null);
        ButterKnife.bind(this, view);
        netDataHelper = NetDataHelper.getInstance(getActivity());
        shopId = Cookies.getShopId();
        calendarCheck = Calendar.getInstance();
        calendarCheck.set(Calendar.HOUR_OF_DAY, 0);
        calendarCheck.set(Calendar.MINUTE, 0);
        calendarCheck.set(Calendar.SECOND, 0);
        calendarCheck.set(Calendar.MILLISECOND, 0);

        tvDay.setText(calendarCheck.get(Calendar.YEAR) + "-" + (calendarCheck.get(Calendar.MONTH) + 1)
                + "-" + calendarCheck.get(Calendar.DAY_OF_MONTH));

        tvType.setText("全部");

        //获取月度奖励信息
        getRewardByMonth();
        //获取初始数据
        getTime();
        initRv();
        return view;
    }

    private void initRv() {
        dataList = new ArrayList<>();
        adapter = new RewardAdapter(dataList, getActivity());
        rv.setListener(this);
        rv.setAdapter(adapter);
    }

    @Override
    public void onLoadMore(final int page) {
        netDataHelper.getRewardDetail(shopId, startTime + "", endTime + ""
                , couponType, page, new NetCallBack<RewardRecord>() {
                    @Override
                    public void onSuccess(RewardRecord data) {
                        Log.d("test", "load data:" + data.toString());
                        tvRechargeNumber.setText(data.getCz_center_recharge_count() + "笔");
                        tvRechargeMoney.setText("￥" + data.getCz_center_recharge_sum());
                        tvStoreNumber.setText(data.getGoods_recharge_count() + "笔");
                        tvStoreMoney.setText("￥" + data.getGoods_recharge_sum());
                        tvMoney.setText("￥" + (data.getGoods_recharge_sum() + data.getCz_center_recharge_sum()));

                        if (page == 1) {
                            //首次加载
                            dataList.clear();
                            dataList.addAll(data.getData());
                            rv.onLoadSuccess(page);
                            adapter.notifyDataSetChanged();
                        } else if (data.getData().size() > 0) {
                            //load more 并且有数据
                            dataList.addAll(data.getData());
                            rv.onLoadSuccess(page);
                            adapter.notifyDataSetChanged();
                        } else {
                            //load more 无数据
                            rv.dismissLoading();
                            rv.dismissProgressBar();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        rv.dismissLoading();
                        rv.dismissProgressBar();
                        Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getRewardByMonth() {
        netDataHelper.getRewardByMonth(shopId, new NetCallBack<RewardByMonth>() {
            @Override
            public void onSuccess(RewardByMonth data) {
                Log.d("test", "data:" + data.toString());
                SpannableStringBuilder style = new SpannableStringBuilder("￥ "+ StringUtils.floatFormat(
                        data.getMoney_this_month()+data.getGoods_this_money_money()));
                style.setSpan(new AbsoluteSizeSpan(DensityUtils.dip2px(14)),0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                tvThisMonthMoney.setText(style);
                SpannableStringBuilder style2 = new SpannableStringBuilder("￥ "+ StringUtils.floatFormat(
                        data.getMoney_last_month()+data.getGoods_before_month_money()));
                style2.setSpan(new AbsoluteSizeSpan(DensityUtils.dip2px(14)),0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                tvLastMonthMoney.setText(style2);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.rl_type, R.id.rl_month, R.id.rl_search, R.id.tv_last_day, R.id.rl_select, R.id.tv_next_day})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_type:
                CustomNumPickeDialog areaDialog = new CustomNumPickeDialog(getActivity());
                areaDialog.setDataAndListener(new String[]{"全部分类","杂货铺","充值中心"}, this);
                areaDialog.showAtLocation(rlMain, Gravity.BOTTOM, 0, 0);
                //开启滑动动画
                startAnimation(1);
                break;
            case R.id.rl_month:
                datePickeDialog = new CustomDatePickeDialog(getActivity());
                datePickeDialog.setListener(1, this);
                datePickeDialog.showAtLocation(rlMain, Gravity.BOTTOM, 0, 0);
                //开启滑动动画
                startAnimation(1);
                break;
            case R.id.rl_search:
                break;
            case R.id.tv_last_day:
                if (dateType == 1) {
                    //按月查询
                    calendarCheck.add(Calendar.MONTH, -1);
                    tvDay.setText(calendarCheck.get(Calendar.YEAR) + "-" + (calendarCheck.get(Calendar.MONTH) + 1));
                } else {
                    //按日查询
                    calendarCheck.add(Calendar.DAY_OF_MONTH, -1);
                    tvDay.setText(calendarCheck.get(Calendar.YEAR) + "-" + (calendarCheck.get(Calendar.MONTH) + 1)
                            + "-" + calendarCheck.get(Calendar.DAY_OF_MONTH));
                }
                getTime();
                rv.reLoad();
                break;
            case R.id.rl_select:
                datePickeDialog = new CustomDatePickeDialog(getActivity());
                datePickeDialog.setListener(2, this);
                datePickeDialog.showAtLocation(rlMain, Gravity.BOTTOM, 0, 0);
                //开启滑动动画
                startAnimation(1);
                break;
            case R.id.tv_next_day:
                if (dateType == 1) {
                    //按月查询
                    calendarCheck.add(Calendar.MONTH, 1);
                    tvDay.setText(calendarCheck.get(Calendar.YEAR) + "-" + (calendarCheck.get(Calendar.MONTH) + 1));
                } else {
                    //按日查询
                    calendarCheck.add(Calendar.DAY_OF_MONTH, 1);
                    tvDay.setText(calendarCheck.get(Calendar.YEAR) + "-" + (calendarCheck.get(Calendar.MONTH) + 1)
                            + "-" + calendarCheck.get(Calendar.DAY_OF_MONTH));
                }
                getTime();
                rv.reLoad();
                break;
        }
    }

    private void getTime() {
        startTime = calendarCheck.getTimeInMillis()/1000;
        Calendar calendarEnd = (Calendar) calendarCheck.clone();
        calendarEnd.add(dateType==1?Calendar.MONTH:Calendar.DAY_OF_MONTH, 1);
        endTime = (calendarEnd.getTimeInMillis()-1)/1000;
    }

    //启动动画方法
    //1-进入动画 2-退出动画
    private void startAnimation(int type) {
        back.setVisibility(View.VISIBLE);
        back.startAnimation(AnimationUtils.loadAnimation(getActivity(), type == 1 ? R.anim.popin_anim : R.anim.popout_anim));
    }

    //from CustomNumPickerDialog3.OnOkClickListener
    @Override
    public void onOkClick(int select) {
        switch (select) {
            case 0:
                couponType = 1;
                tvType.setText("全部分类");
                break;
            case 1:
                couponType = 2;
                tvType.setText("杂货铺");
                break;
            case 2:
                couponType = 3;
                tvType.setText("充值中心");
                break;
        }
    }

    //from CustomNumPickerDialog3.OnOkClickListener
    @Override
    public void onDismiss() {
        back.setVisibility(View.GONE);
    }


    //from CustomDatePickerDialog.OnDatePickListener
    @Override
    public void onDatePick(int type, int year, int month, final int day) {
        dateType = type;
        if (type == 1) {
            //按月查询
            tvLastDay.setText("前一月");
            tvNextDay.setText("后一月");
            tvDay.setText(year + "-" + (month + 1));
        } else {
            //按日查询
            tvLastDay.setText("前一天");
            tvNextDay.setText("后一天");
            tvDay.setText(year + "-" + (month + 1)+"-"+day);
        }

        calendarCheck.set(Calendar.YEAR, year);
        calendarCheck.set(Calendar.MONTH, month);
        calendarCheck.set(Calendar.DAY_OF_MONTH, type == 1 ? 1 : day);

        getTime();
        rv.reLoad();
    }

    //from CustomDatePickerDialog.OnDatePickListener
    @Override
    public void onDateDismiss() {
        back.setVisibility(View.GONE);
    }
}
