package com.meishipintu.fucaiShopNew.views.adaptersAndViewholder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.meishipintu.fucaiShopNew.R
import com.meishipintu.fucaiShopNew.models.bean.WaiterInfo

/**
 * Created by Administrator on 2018/6/11.
 *
 * 主要功能：
 */
class WaiterAdapter(val context:Context, val data:List<WaiterInfo>): RecyclerView.Adapter<WaiterViewHolder>() {

    val listener: WaiterClickListener = context as WaiterClickListener

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WaiterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_my_waiter, parent, false)
        return WaiterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: WaiterViewHolder?, position: Int) {
        val waiter = data[position]
        if (holder != null) {
            holder.tvName.text = "姓名：" + waiter.nickname
            holder.tvAccount.text = "账号：" + waiter.account
            holder.tvTel.text = "电话："+waiter.mobile
            holder.tvPsw.text = "密码："+waiter.real_psw
            holder.btDelete.setOnClickListener {
                listener.onDeleteClick(position)
            }
            holder.btEdit.setOnClickListener {
                listener.onEditClick(position)
            }
        }
    }
}