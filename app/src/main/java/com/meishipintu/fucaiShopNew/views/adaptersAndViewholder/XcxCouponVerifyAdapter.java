package com.meishipintu.fucaiShopNew.views.adaptersAndViewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.meishipintu.fucaiShopNew.R;
import com.meishipintu.fucaiShopNew.models.bean.XcxCouponDetail;
import com.meishipintu.fucaiShopNew.utils.DateUtils;
import com.meishipintu.fucaiShopNew.utils.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/12/19.
 * <p>
 * 主要功能：
 */

public class XcxCouponVerifyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int TYPE_NORMAL = 1;
    private int TYPE_EMPTY = 2;

    private List<XcxCouponDetail> data;
    private Context context;

    public XcxCouponVerifyAdapter(List<XcxCouponDetail> data, Context context) {
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
            XcxCouponDetail xcxCouponDetail = data.get(position);
            CouponVerifyViewHolder holder1 = (CouponVerifyViewHolder) holder;
            holder1.tvType.setText(xcxCouponDetail.getRedball_name());
            holder1.tvName.setText(xcxCouponDetail.getWaiter_name());
            holder1.tvMobile.setText(StringUtils.getCouponHide(xcxCouponDetail.getCoupon_sn()));
            holder1.tvTime.setText(DateUtils.formart2_short(xcxCouponDetail.getUse_time()));
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
