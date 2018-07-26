package com.meishipintu.fucaiShopNew.views.adaptersAndViewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.meishipintu.fucaiShopNew.R;
import com.meishipintu.fucaiShopNew.models.bean.RewardData;
import com.meishipintu.fucaiShopNew.models.bean.RewardDetail;
import com.meishipintu.fucaiShopNew.utils.DateUtils;
import com.meishipintu.fucaiShopNew.utils.StringUtils;
import com.meishipintu.fucaiShopNew.views.ActOrderDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/17.
 * <p>
 * 主要功能：
 */

public class RewardAdapter extends RecyclerView.Adapter<RewardViewHolder> {



    private List<RewardData> dataList;
    private Context context;
    private RequestManager glide;

    public RewardAdapter(List<RewardData> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
        this.glide = Glide.with(context);
    }

    @Override
    public RewardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RewardViewHolder(LayoutInflater.from(context).inflate(R.layout.item_reward, parent, false));
    }

    @Override
    public void onBindViewHolder(RewardViewHolder holder, int position) {
        final RewardData rewardDetail = dataList.get(position);
        glide.load(rewardDetail.getPay_type_from() == 2 ? R.drawable.icon_store : R.drawable.icon_rechargecenter).into(holder.iv);
        holder.tvType.setText(rewardDetail.getPay_type_from() == 1 ? "充值中心":"杂货铺");
        holder.tvTel.setText(StringUtils.specilaFormat(rewardDetail.getMobile()));
        holder.tvMoney.setText("+" + rewardDetail.getMake_money());
        holder.tvTime.setText(DateUtils.formart3(rewardDetail.getCreate_time()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActOrderDetail.class);
                intent.putExtra("type", rewardDetail.getPay_type_from());   //1-充值中心 2-杂货铺
                intent.putExtra("id", rewardDetail.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @OnClick(R.id.bt_return)
    public void onViewClicked() {
    }
}
