package com.meishipintu.fucaiShopNew.views.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.meishipintu.fucaiShopNew.Cookies;
import com.meishipintu.fucaiShopNew.R;
import com.meishipintu.fucaiShopNew.models.NetDataHelper;
import com.meishipintu.fucaiShopNew.views.InfomationCommitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class F0_AddInfoFrag extends Fragment {

    private static final int COMMIT_INFO = 01;

    @BindView(R.id.stub_review)
    ViewStub stubReview;

    private NetDataHelper netDataHelper;
    private static F0_AddInfoFrag instance;

    public F0_AddInfoFrag() {
        // Required empty public constructor
    }

    public static F0_AddInfoFrag createInstance() {
        if (instance == null) {
            instance = new F0_AddInfoFrag();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_add_info, container, false);
        ButterKnife.bind(this, view);
        if (Cookies.isCommitInfo(Cookies.getShopId())) {
            //已提交，显示等待审核
            stubReview.inflate();
        }
        return view;
    }

    @OnClick(R.id.bt_add_info)
    public void onViewClicked() {
        //启动信息填写页面
        Cookies.hasCommitInfo(Cookies.getShopId());
        startActivityForResult(new Intent(getActivity(), InfomationCommitActivity.class),COMMIT_INFO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == COMMIT_INFO) {
            if (Cookies.isCommitInfo(Cookies.getShopId())) {
                //已提交，显示等待审核
                stubReview.inflate();
            }
        }
    }
}
