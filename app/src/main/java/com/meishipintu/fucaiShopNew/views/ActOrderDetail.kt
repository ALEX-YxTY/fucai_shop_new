package com.meishipintu.fucaiShopNew.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.meishipintu.fucaiShopNew.R
import com.meishipintu.fucaiShopNew.models.HttpApiClinet
import com.meishipintu.fucaiShopNew.models.HttpApiStores
import com.meishipintu.fucaiShopNew.models.HttpResultFunc
import com.meishipintu.fucaiShopNew.models.bean.RewardDetail
import com.meishipintu.fucaiShopNew.models.bean.StoreRewardDetail
import com.meishipintu.fucaiShopNew.utils.DateUtils
import com.meishipintu.fucaiShopNew.utils.NumberUtils
import com.meishipintu.fucaiShopNew.utils.StringUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

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
    val tvName1:TextView by lazy{ findViewById<TextView>(R.id.tv_name1)}
    val tvAddress:TextView by lazy{ findViewById<TextView>(R.id.tv_address)}
    val tvUserName:TextView by lazy{ findViewById<TextView>(R.id.tv_type_name)}
    val tvNum:TextView by lazy{ findViewById<TextView>(R.id.tv_num)}


    val retrofit:HttpApiStores by lazy{ HttpApiClinet.retrofit()}
    val disposable:CompositeDisposable by lazy{ CompositeDisposable()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_order_detail)
        findViewById<View>(R.id.bt_return).setOnClickListener { onBackPressed() }
        val id = intent.getIntExtra("id", 0)
        val type = intent.getIntExtra("type", 1)
        title.text = "奖励详情"
        tvType.text = if(type==1) "充值中心" else "杂货铺"
        Glide.with(this).load(if(type==1) R.drawable.icon_rechargecenter else R.drawable.icon_store).into(ivType)
        //获取奖励详情
        if (type == 1) {
            //充值中心详情
            retrofit.getRewardDetail(id, 2).map(HttpResultFunc())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object: Observer<RewardDetail>{
                        override fun onNext(value: RewardDetail?) {
                            showRechargeDetail(value)
                        }

                        override fun onSubscribe(d: Disposable?) {
                            disposable.add(d)
                        }

                        override fun onComplete() {
                        }

                        override fun onError(e: Throwable?) {
                            e?.printStackTrace()
                            Toast.makeText(this@ActOrderDetail,"获取详情失败，请稍后重试",Toast.LENGTH_SHORT).show()
                        }
                    })
        }else{
            //杂货铺详情
            retrofit.getStoreRewardDetail(id, 1).map(HttpResultFunc())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object: Observer<StoreRewardDetail>{
                        override fun onNext(value: StoreRewardDetail?) {
                            showRechargeDetail(value)
                        }

                        override fun onSubscribe(d: Disposable?) {
                            disposable.add(d)
                        }

                        override fun onComplete() {
                        }

                        override fun onError(e: Throwable?) {
                            e?.printStackTrace()
                            Toast.makeText(this@ActOrderDetail,"获取详情失败，请稍后重试",Toast.LENGTH_SHORT).show()
                        }
                    })
        }

    }

    private fun showRechargeDetail(value: RewardDetail?) {
        tvMoney.text = "+" + NumberUtils.floatFormat(value!!.make_money)
        tvPayMoney.text = "￥" + NumberUtils.floatFormat(value!!.user_pay_money)
        tvAccount.text = StringUtils.specilaFormat(value!!.mobile)
        tvPayTel.text = StringUtils.specilaFormat(value!!.mobile)
        tvBillNo.text = value!!.trade_no
        tvTradeNo.text = value!!.order_id
        tvTime.text = DateUtils.formart4(value!!.create_time)
        var company:String? = null
        when (value.pay_mobile_from) {
            1 -> company = "中国移动"
            2 -> company = "中国联通"
            3 -> company = "中国电信"
        }
        when (value.type) {
            1 -> //话费充值
                tvDesc.text = company + value!!.face_price + "元话费充值"
            2 -> //流量充值
                tvDesc.text = company + value!!.m + "M "+(if(value!!.ll_type==0) "全国流量" else "省内流量")+" 流量包"
        }
    }

    private fun showRechargeDetail(value: StoreRewardDetail?) {
        findViewById<View>(R.id.rl_address).visibility = View.VISIBLE
        findViewById<View>(R.id.rl_num).visibility = View.VISIBLE
        tvUserName.text = "用户姓名"
        tvName1.text = "联系电话"
        tvPayTel.text = StringUtils.specilaFormat(value!!.user_tel)
        tvAccount.text = value.username
        tvAddress.text = value.address
        tvMoney.text = "+" + NumberUtils.floatFormat(value.make_money)
        tvNum.text = "" + value.number
        tvPayMoney.text = "￥" + NumberUtils.floatFormat(value.amount)
        tvBillNo.text = value.trade_no
        tvTradeNo.text = value.order_id
        tvTime.text = DateUtils.formart4(value.create_time)
        tvDesc.text = value.goods_title + " " + value.goods_spec

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
