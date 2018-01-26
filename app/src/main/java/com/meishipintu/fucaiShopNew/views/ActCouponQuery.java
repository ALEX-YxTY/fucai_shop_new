package com.meishipintu.fucaiShopNew.views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.meishipintu.fucaiShopNew.Cookies;
import com.meishipintu.fucaiShopNew.R;
import com.meishipintu.fucaiShopNew.custom.CustomDatePickeDialog;
import com.meishipintu.fucaiShopNew.custom.CustomNumPickeDialog;
import com.meishipintu.fucaiShopNew.models.NetCallBack;
import com.meishipintu.fucaiShopNew.models.NetDataHelper;
import com.meishipintu.fucaiShopNew.models.bean.CouponQueryResult;
import com.meishipintu.fucaiShopNew.utils.StringUtils;
import com.meishipintu.fucaiShopNew.views.adaptersAndViewholder.CouponVerifyAdapter;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActCouponQuery extends Activity implements CustomNumPickeDialog.OnOkClickListener,CustomDatePickeDialog.OnDatePickListener{


    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_verify)
    TextView tvVerify;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_last_day)
    TextView tvLastDay;
    @BindView(R.id.tv_next_day)
    TextView tvNextDay;
    @BindView(R.id.back)
    View back;

    private Calendar calendarCheck;
    private int dateType = 2;  //1-按月查询 2-按日查询
    private int couponType =-1;  //-1-全部分类 1-2元 2-5元
    private String shopId;

    private CustomDatePickeDialog datePickeDialog;
    private NetDataHelper netDataHelper;
    private CouponQueryResult result = null;
    private CouponVerifyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_query);
        ButterKnife.bind(this);
        netDataHelper = NetDataHelper.getInstance(this);
        shopId = Cookies.getShopId();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
        initUI();
    }

    private void initUI() {
        calendarCheck = Calendar.getInstance();
        calendarCheck.set(Calendar.HOUR_OF_DAY, 0);
        calendarCheck.set(Calendar.MINUTE, 0);
        calendarCheck.set(Calendar.SECOND, 0);
        calendarCheck.set(Calendar.MILLISECOND, 0);

        tvDay.setText(calendarCheck.get(Calendar.YEAR) + "-" + (calendarCheck.get(Calendar.MONTH) + 1)
                + "-" + calendarCheck.get(Calendar.DAY_OF_MONTH));
        //获取初始数据
        getDataByTime();
    }

    //初始化列表
    private void initRv() {
        if (result != null && result.getData() != null) {
            if (adapter == null) {
                adapter = new CouponVerifyAdapter(result.getData(), this);
                rv.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    private void selfToastShow(String message) {
        Toast toast = Toast.makeText(this.getBaseContext(), "", Toast.LENGTH_LONG);
        LayoutInflater infalter = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = infalter.inflate(R.layout.layout_toast, null);
        TextView tvContent = (TextView) v.findViewById(R.id.tv_toast_content);
        tvContent.setText(message);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(v);
        toast.show();
    }

    @OnClick({R.id.bt_return, R.id.rl_type, R.id.rl_month, R.id.rl_search, R.id.tv_last_day, R.id.rl_select, R.id.tv_next_day})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_return:
                this.onBackPressed();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;
            case R.id.rl_type:
                CustomNumPickeDialog areaDialog = new CustomNumPickeDialog(this);
                areaDialog.setDataAndListener(new String[]{"全部分类","2元红包","5元红包"}, this);
                areaDialog.showAtLocation(findViewById(R.id.rl_main), Gravity.BOTTOM, 0, 0);
                //开启滑动动画
                startAnimation(1);
                break;
            case R.id.rl_month:
                datePickeDialog = new CustomDatePickeDialog(this);
                datePickeDialog.setListener(1, this);
                datePickeDialog.showAtLocation(findViewById(R.id.rl_main), Gravity.BOTTOM, 0, 0);
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
                getDataByTime();
                break;
            case R.id.rl_select:
                datePickeDialog = new CustomDatePickeDialog(this);
                datePickeDialog.setListener(2, this);
                datePickeDialog.showAtLocation(findViewById(R.id.rl_main), Gravity.BOTTOM, 0, 0);
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
                getDataByTime();
                break;
        }
    }

    //启动动画方法
    //1-进入动画 2-退出动画
    private void startAnimation(int type) {
        back.setVisibility(View.VISIBLE);
        back.startAnimation(AnimationUtils.loadAnimation(this, type == 1 ? R.anim.popin_anim : R.anim.popout_anim));
    }

    //from CustomNumPickerDialog3.OnOkClickListener
    @Override
    public void onOkClick(int select) {
        switch (select) {
            case 0:
                couponType = -1;
                tvType.setText("全部分类");
                break;
            case 1:
                couponType = 1;
                tvType.setText("2元红包");
                break;
            case 2:
                couponType = 2;
                tvType.setText("5元红包");
                break;
        }
    }

    //from CustomNumPickerDialog3.OnOkClickListener
    @Override
    public void onDismiss() {
        back.setVisibility(View.GONE);
    }

    //1- 类型选择 2- 月份选择
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

        getDataByTime();
    }

    //获取初始数据
    private void getDataByTime() {
        long timeStart = calendarCheck.getTimeInMillis();
        long timeEnd;
        Calendar calendarEnd = (Calendar) calendarCheck.clone();
        if (dateType == 1) {
            calendarEnd.add(Calendar.MONTH, 1);
            timeEnd = calendarEnd.getTimeInMillis()-1;
        } else {
            calendarEnd.add(Calendar.DAY_OF_MONTH, 1);
            timeEnd = calendarEnd.getTimeInMillis()-1;
        }
        //调用时间查询接口
        Log.i("test", "shopid:" + shopId + ",timestart:" + timeStart + ",timeEnd:" + timeEnd);
        netDataHelper.getCouponQuery(couponType, shopId, timeStart, timeEnd, new NetCallBack<CouponQueryResult>() {
            @Override
            public void onSuccess(CouponQueryResult data) {
                result = data;
                tvNumber.setText(data.getData().size() + "");
                tvVerify.setText(data.getCount_mobile());
                tvAmount.setText(StringUtils.floatFormat(Float.parseFloat(data.getTotla_money())));
                initRv();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(ActCouponQuery.this,error,Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onDateDismiss() {
        back.setVisibility(View.GONE);
    }
}
