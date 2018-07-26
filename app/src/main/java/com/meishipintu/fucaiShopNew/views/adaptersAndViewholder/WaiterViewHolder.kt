package com.meishipintu.fucaiShopNew.views.adaptersAndViewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.meishipintu.fucaiShopNew.R
import org.w3c.dom.Text

/**
 * Created by Administrator on 2018/6/11.
 *
 * 主要功能：
 */
class WaiterViewHolder(val view:View): RecyclerView.ViewHolder(view) {
    val tvName:TextView = view.findViewById(R.id.tv_name)
    val tvTel: TextView = view.findViewById(R.id.tv_tel)
    val tvAccount: TextView = view.findViewById(R.id.tv_account)
    val tvPsw: TextView = view.findViewById(R.id.tv_psw)
    val btEdit: Button = view.findViewById(R.id.bt_edit)
    val btDelete: Button = view.findViewById(R.id.bt_delete)
}