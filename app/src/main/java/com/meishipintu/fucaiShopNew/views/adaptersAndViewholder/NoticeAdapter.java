package com.meishipintu.fucaiShopNew.views.adaptersAndViewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meishipintu.fucaiShopNew.R;
import com.meishipintu.fucaiShopNew.models.bean.Notice;
import com.meishipintu.fucaiShopNew.views.NewsDetailActivity;
import com.meishipintu.fucaiShopNew.views.fragments.NoticFragment;

import java.util.List;


/**
 * Created by Administrator on 2016/12/19.
 */

public class NoticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_NOTICE = 1;
    private final int TYPE_PUSHINFO = 2;

    private Context context;
    private List<Notice> noticeList;
    private String type;
    private NoticeClickListener listener;

    public NoticeAdapter(Context context, List<Notice> list, String type, NoticFragment noticFragment) {
        this.context = context;
        this.noticeList = list;
        this.type = type;
        this.listener = noticFragment;
    }

    @Override
    public int getItemViewType(int position) {
        if (noticeList.get(position).getPush_type() == null) {
            return TYPE_PUSHINFO;
        } else {
            return TYPE_NOTICE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NOTICE) {
            return new NoticeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false));
        } else {
            //pushInfo
            return new PushInfoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_dograd, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Notice notice = noticeList.get(position);
        Log.d("debug", notice.toString());
        if (getItemViewType(position) == TYPE_NOTICE) {
            NoticeViewHolder holder1 = (NoticeViewHolder) holder;
            holder1.title.setText(notice.getPush_title());
            holder1.content.setText(notice.getPush_show());
            holder1.content.setMovementMethod(LinkMovementMethod.getInstance());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NewsDetailActivity.class);
                    intent.putExtra("url", "fucai.milaipay.com/Home/Common/notice.html?id=" +
                            notice.getId());
                    intent.putExtra("isNotice", true);
                    context.startActivity(intent);
                }
            });
        } else {
            PushInfoViewHolder holder1 = (PushInfoViewHolder) holder;
            holder1.orderName.setText("用户" + notice.getUsername() + "需要买" + notice.getMoney()
                    + "元种类" + notice.getName() + "的彩票");
            holder1.phonenumber.setText(notice.getTel());
            holder1.address.setText(notice.getAddress());
            holder1.sum.setText(notice.getMoney());
            holder1.kind.setText(notice.getName());
            holder1.btDograd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onGrad(notice);
                }
            });
            holder1.btIgnored.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onIgnore(notice);
                }
            });
            holder1.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCall(notice);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

}



