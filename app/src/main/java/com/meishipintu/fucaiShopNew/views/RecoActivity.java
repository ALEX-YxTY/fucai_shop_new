package com.meishipintu.fucaiShopNew.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.meishipintu.fucaiShopNew.Cookies;
import com.meishipintu.fucaiShopNew.R;
import com.meishipintu.fucaiShopNew.RxBus;
import com.meishipintu.fucaiShopNew.models.NetCallBack;
import com.meishipintu.fucaiShopNew.models.NetDataHelper;
import com.meishipintu.fucaiShopNew.models.bean.BusMessage;
import com.meishipintu.fucaiShopNew.models.bean.RecoInfo;
import com.meishipintu.fucaiShopNew.utils.StringUtils;
import com.meishipintu.fucaiShopNew.views.adaptersAndViewholder.RecommendationAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class RecoActivity extends Activity {

    private static final String TAG = "njfucaiB-RecoActivity";
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_total_percentage)
    TextView tvTotalPercentage;
    @BindView(R.id.rv)
    RecyclerView rv;

    private NetDataHelper mNetDataHelper;
    private String shop_id;
    private int debetType;
    private CompositeDisposable subscriptions;
    private RecyclerView.Adapter adapter;
    private List<RecoInfo> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        ButterKnife.bind(this);
        shop_id = Cookies.getShopId();
        debetType = getIntent().getIntExtra("type", 1);          //标注彩种，1-双色球 2-3d 3-七乐彩
        mNetDataHelper = NetDataHelper.getInstance(this);
        subscriptions = new CompositeDisposable();
        RxBus.getDefault().getObservable(BusMessage.class).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BusMessage>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        subscriptions.add(d);
                    }

                    @Override
                    public void onNext(BusMessage busMessage) {
                        if (busMessage.getMessageType() == 100) {
                            tvTotalPercentage.setText(busMessage.getPlusMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        title.setText(R.string.recommend);
        initRv();
    }

    private NetCallBack<List<RecoInfo>> netCallBack = new NetCallBack<List<RecoInfo>>() {
        @Override
        public void onSuccess(List<RecoInfo> data) {
            if (adapter == null) {
                dataList = data;
                adapter = new RecommendationAdapter(RecoActivity.this, dataList);
                rv.setAdapter(adapter);
            } else {
                dataList.clear();
                dataList.addAll(data);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onError(String error) {
            Toast.makeText(RecoActivity.this, error, Toast.LENGTH_SHORT).show();
        }
    };

    private void initRv() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());

        if (!StringUtils.isNullOrEmpty(shop_id)) {
            switch (debetType) {
                case 1:
                    mNetDataHelper.getSSQRecommendationByShop(shop_id, netCallBack);
                    break;
                case 2:
                    mNetDataHelper.get3DRecommendationByShop(shop_id, netCallBack);
                    break;
                case 3:
                    mNetDataHelper.getQLCRecommendationByShop(shop_id,netCallBack);
                    break;
            }
        }
    }

    @OnClick(R.id.bt_return)
    public void onClick(View v) {
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        subscriptions.clear();
        super.onDestroy();
    }
}
