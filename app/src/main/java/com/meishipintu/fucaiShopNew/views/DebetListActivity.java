package com.meishipintu.fucaiShopNew.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.meishipintu.fucaiShopNew.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DebetListActivity extends Activity {

    @BindView(R.id.title)
    TextView title;

    private int fromType;           //标注从哪个页面进入，1-从福豆商城投注进入  2-从站点荐号进入

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debet_list);
        ButterKnife.bind(this);
        title.setText(R.string.debet_select);
    }

    @OnClick({R.id.bt_return, R.id.rl_type1, R.id.rl_type2, R.id.rl_type3})
    public void onClick(View view) {
        Intent intent = new Intent(DebetListActivity.this, RecoDebetActivity.class);
        int lotteryType = -1;
        switch (view.getId()) {
            case R.id.bt_return:
                this.finish();
                break;
            case R.id.rl_type1:
                lotteryType = 1;
                break;
            case R.id.rl_type2:
                lotteryType = 2;
                break;
            case R.id.rl_type3:
                lotteryType = 3;
                break;
        }
        if (lotteryType > 0) {
            intent.putExtra("type", lotteryType);
            startActivity(intent);
        }
    }
}
