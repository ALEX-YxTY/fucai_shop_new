package com.meishipintu.fucaiShopNew.views;

import android.app.Activity;
import android.os.Bundle;

import com.meishipintu.fucaiShopNew.R;

import butterknife.ButterKnife;

public class InfomationCommitActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_commit);
        ButterKnife.bind(this);
    }
}
