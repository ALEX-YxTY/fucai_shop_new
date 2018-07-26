package com.meishipintu.fucaiShopNew.models.bean

/**
 * Created by Administrator on 2018/1/30.
 *
 * @param face_price 面值
 * @param make_money 奖励金，未来要乘以系数
 * @param user_pay_money 用户支付金额
 * @param type 1-话费充值 2-流量充值
 * @param pay_mobile_from  1-中国移动 2-中国联通 3-中国电信
 * @param ll_type 0-全国流量 1-省内流量
 * @param m 如果是流量充值，m表示流量大小，单位M
 * @param pay_type 1-支付宝 2-微信支付 3-易付宝
 */
data class RewardDetail(val id: Int, val order_id: String, val trade_no: String, val goods_id: Int
                        , val shop_id: String
                        , val make_money: Float, val user_pay_money: Float, val create_time: String
                        , val mobile: String,val ll_type:Int
                        , val face_price: Float, val type: Int, val pay_mobile_from: Int, val m:Int, val pay_type:Int)