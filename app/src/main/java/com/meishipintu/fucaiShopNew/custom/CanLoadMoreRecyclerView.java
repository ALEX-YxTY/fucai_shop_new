package com.meishipintu.fucaiShopNew.custom;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.meishipintu.fucaiShopNew.R;


/**
 * Created by Administrator on 2017/11/23.
 * <p>
 * 主要功能：
 */

public class CanLoadMoreRecyclerView extends RelativeLayout {

    private SwipeRefreshLayout swipe;
    private RecyclerView rv;
    private ProgressBar pb;
    private LinearLayoutManager layoutManager;

    private StateChangedListener listener;  //状态监听，分为刷新接口和加载更多接口
    private int page = 0;       //标记当前页数，默认初始值为0，首次加载数据后为1

    public CanLoadMoreRecyclerView(Context context) {
        super(context);
        initUI(context);
    }

    public CanLoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    private void initUI(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_recyclerview, this, true);
        swipe = findViewById(R.id.swipe);
        rv = findViewById(R.id.recyclerview);
        pb = findViewById(R.id.pb);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (listener != null) {
                    //重新加载该页
                    listener.onLoadMore(1);
                }
            }
        });
        swipe.setColorSchemeResources(R.color.orange_head, R.color.brown_price);
        layoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(layoutManager);
    }

    public void setListener(StateChangedListener listener) {
        this.listener = listener;
    }

    //设置adapter并载入首页
    public void setAdapter(RecyclerView.Adapter adapter) {
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dy!=0 避免每次notifyDataSetChanged时调用onScrolled
                if (dy != 0 && layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                    pb.setVisibility(View.VISIBLE);
                    if (listener != null) {
                        listener.onLoadMore(page + 1);
                    }
                }
            }
        });
        //载入首页
        reLoad();
    }

    //再次载入，不改变adapter
    public void reLoad(){
        //复原page为0
        page = 0;
        //首次加载,调出swipe
        swipe.setRefreshing(true);
        if (listener != null) {
            listener.onLoadMore(page + 1);
        }
    }

    //取消progressbar
    public void dismissProgressBar() {
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
    }

    //取消loading
    public void dismissLoading() {
        if (pb.getVisibility() == View.VISIBLE) {
            pb.setVisibility(View.GONE);
        }
    }

    //加载成功的回调
    public void onLoadSuccess(int page) {
        dismissLoading();
        dismissProgressBar();
        this.page = page;
        Log.d("test", "now page is $page");
    }

    //使recyclerView滚动到底部
    public void scrollToEnd() {
        if (layoutManager.getItemCount() > 0) {
            rv.scrollToPosition(layoutManager.getItemCount() - 1);
        }
    }

    public interface StateChangedListener{
        void onLoadMore(int page);
    }
}
