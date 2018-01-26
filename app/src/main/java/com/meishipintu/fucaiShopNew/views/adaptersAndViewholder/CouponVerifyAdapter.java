package com.meishipintu.fucaiShopNew.views.adaptersAndViewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.meishipintu.fucaiShopNew.R;
import com.meishipintu.fucaiShopNew.models.bean.CouponVerifyed;
import com.meishipintu.fucaiShopNew.utils.DateUtils;
import com.meishipintu.fucaiShopNew.utils.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/12/19.
 * <p>
 * 主要功能：
 */

public class CouponVerifyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int TYPE_NORMAL = 1;
    private int TYPE_EMPTY = 2;

    private List<CouponVerifyed> data;
    private Context context;

    public CouponVerifyAdapter(List<CouponVerifyed> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.size() > 0) {
            return TYPE_NORMAL;
        } else {
            return TYPE_EMPTY;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            return new CouponVerifyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_coupon_verified, parent, false));
        } else {
            return new EmptyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_empty, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            CouponVerifyed couponVerifyed = data.get(position);
            CouponVerifyViewHolder holder1 = (CouponVerifyViewHolder) holder;
            holder1.tvType.setText(couponVerifyed.getName());
            holder1.tvMobile.setText(StringUtils.getMoileHid(couponVerifyed.getMobile()));
            holder1.tvTime.setText(DateUtils.formart2(couponVerifyed.getUse_time()));
        }
    }

    @Override
    public int getItemCount() {
        if (data.size() > 0) {
            return data.size();
        } else {
            return 1;
        }
    }
}
