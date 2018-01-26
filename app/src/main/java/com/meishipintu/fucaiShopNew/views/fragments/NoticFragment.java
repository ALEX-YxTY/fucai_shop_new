package com.meishipintu.fucaiShopNew.views.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.meishipintu.fucaiShopNew.Cookies;
import com.meishipintu.fucaiShopNew.R;
import com.meishipintu.fucaiShopNew.models.NetCallBack;
import com.meishipintu.fucaiShopNew.models.NetDataHelper;
import com.meishipintu.fucaiShopNew.models.bean.Notice;
import com.meishipintu.fucaiShopNew.views.adaptersAndViewholder.NoticeAdapter;
import com.meishipintu.fucaiShopNew.views.adaptersAndViewholder.NoticeClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/6.
 * <p>
 * 主要功能：
 */

public class NoticFragment extends android.support.v4.app.Fragment implements View.OnClickListener,NoticeClickListener {

    @BindView(R.id.stystem_View)
    View systemView;
    @BindView(R.id.natice_View)
    View noticeView;
    @BindView(R.id.teaching_View)
    View teachingView;
    @BindView(R.id.lv_notice)
    RecyclerView lvNotice;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;

    private List<Notice> data;
    private NoticeAdapter adapter;
    private String type = "1";                      //1系统通知，2教学,3通知
    private static NoticFragment singleInstance;    //单例
    private NetDataHelper netDataHelper;

    private static String TAG = "njfc_noticeFragment";

    public static NoticFragment createInstance() {
        if (singleInstance == null) {
            singleInstance = new NoticFragment();
        }
        return singleInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notice, container, false);
        ButterKnife.bind(this, view);
        netDataHelper = NetDataHelper.getInstance(getActivity());
        init();
        return view;
    }

    private void init() {
        systemView.setVisibility(View.VISIBLE);
        tv1.setTextColor(0xfffd4f00);
        lvNotice.setLayoutManager(new LinearLayoutManager(getActivity()));
        lvNotice.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<>();
        adapter = new NoticeAdapter(getActivity(), data, type, this);
        lvNotice.setAdapter(adapter);
        getData();
    }

    private void getData() {
        switch (type) {
            case "1":
            case "2":
                netDataHelper.getSysNotices(type, new NetCallBack<List<Notice>>() {
                    @Override
                    public void onSuccess(List<Notice> list) {
                        data.clear();
                        data.addAll(list);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case "3":
                //原接口
                data.clear();
                adapter.notifyDataSetChanged();
                netDataHelper.getGrabOrders(1, Cookies.getShopId(), new NetCallBack<List<Notice>>() {
                    @Override
                    public void onSuccess(List<Notice> list) {
                        data.clear();
                        data.addAll(list);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }


    @OnClick({R.id.bt_stystem, R.id.bt_natice, R.id.bt_teaching})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_stystem:
                String type1 = "1";
                if (!type1.equals(type)) {
                    changeType(type1);
                }
                break;
            case R.id.bt_teaching:
                String type2 = "2";
                if (!type2.equals(type)) {
                    changeType(type2);
                }
                break;
            case R.id.bt_natice:
                String type3 = "3";
                if (!type3.equals(type)) {
                    changeType(type3);
                }
                break;
        }
    }

    private void changeType(String type) {
        Log.d(TAG, "click type:" + type);
        this.type = type;
        getData();
        tv1.setTextColor("1".equals(type) ? 0xfffd4f00 : 0xffa0a0a0);
        systemView.setVisibility("1".equals(type) ? View.VISIBLE : View.INVISIBLE);
        tv2.setTextColor("3".equals(type) ? 0xfffd4f00 : 0xffa0a0a0);
        noticeView.setVisibility("3".equals(type) ? View.VISIBLE : View.INVISIBLE);
        tv3.setTextColor("2".equals(type) ? 0xfffd4f00 : 0xffa0a0a0);
        teachingView.setVisibility("2".equals(type) ? View.VISIBLE : View.INVISIBLE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onIgnore(Notice orderdoGrad) {
        final Notice notice = orderdoGrad;
        new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("提示")
                .setMessage("是否忽略该订单").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                netDataHelper.doIgnore( notice.getOid(),Cookies.getShopId(), new NetCallBack<String>() {
                    @Override
                    public void onSuccess(String data) {
                        getData();
                        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }

    @Override
    public void onGrad(Notice orderdoGrad) {
        final Notice notice = orderdoGrad;
        new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("提示")
                .setMessage("是否抢该订单").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                netDataHelper.doGrab(Cookies.getShopId(), notice.getOid(), new NetCallBack<String>() {
                    @Override
                    public void onSuccess(String data) {
                        getData();
                        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }

    @Override
    public void onCall(Notice orderdoGrad) {
        final Notice notice = orderdoGrad;
        new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("拨号")
                .setMessage(orderdoGrad.getTel()).setPositiveButton("拨号", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri= Uri.parse("tel:" + notice.getTel());
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        }).show();
    }
}
