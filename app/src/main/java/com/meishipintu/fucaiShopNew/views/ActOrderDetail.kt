package com.meishipintu.fucaiShopNew.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.meishipintu.fucaiShopNew.R

class ActOrderDetail : AppCompatActivity() {

    val title:TextView by lazy { findViewById<TextView>(R.id.title) }
    val ivType:ImageView by lazy{ findViewById<ImageView>(R.id.iv_type) }
    val tvType:TextView by lazy{ findViewById<TextView>(R.id.tv_type) }
    val tvMoney:TextView by lazy{ findViewById<TextView>(R.id.tv_money) }
    val tvAccount:TextView by lazy{ findViewById<TextView>(R.id.tv_number) }
    val tvPayMoney:TextView by lazy{ findViewById<TextView>(R.id.tv_pay_money) }
    val tvPayTel:TextView by lazy{ findViewById<TextView>(R.id.tv_pay_tel) }
    val tvDesc:TextView by lazy{ findViewById<TextView>(R.id.tv_good_name) }
    val tvTime:TextView by lazy{ findViewById<TextView>(R.id.tv_create_time) }
    val tvBillNo:TextView by lazy{ findViewById<TextView>(R.id.tv_bill_no) }
    val tvTradeNo:TextView by lazy{ findViewById<TextView>(R.id.tv_shop_bill_no) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_order_detail)
        findViewById<View>(R.id.bt_return).setOnClickListener { onBackPressed() }
        val id = intent.getIntExtra("id", 0)
        val type = intent.getIntExtra("type", 3)
        
    }
}
