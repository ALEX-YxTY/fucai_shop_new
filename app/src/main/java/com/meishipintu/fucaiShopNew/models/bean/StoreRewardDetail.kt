package com.meishipintu.fucaiShopNew.models.bean

/**
 * Created by Administrator on 2018/1/30.
 * 杂货铺
 * @param pay_type 1-支付宝 2-微信支付 3-易付宝
 */
data class StoreRewardDetail(val id: Int, val order_id: String, val trade_no: String, val goods_title: String
                             , val goods_spec: String
                             , val make_money: Float, val amount: Float, val create_time: String
                             , val user_tel: String, val username:String, val address:String
                             , val pay_type: Int, val number:Int)