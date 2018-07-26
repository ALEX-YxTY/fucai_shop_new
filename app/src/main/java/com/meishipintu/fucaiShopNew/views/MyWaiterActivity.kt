package com.meishipintu.fucaiShopNew.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.meishipintu.fucaiShopNew.Cookies
import com.meishipintu.fucaiShopNew.R
import com.meishipintu.fucaiShopNew.custom.ChooseDialogListener
import com.meishipintu.fucaiShopNew.custom.CustomChooseDialog
import com.meishipintu.fucaiShopNew.models.HttpApiClinet
import com.meishipintu.fucaiShopNew.models.HttpApiStores
import com.meishipintu.fucaiShopNew.models.HttpResultFunc
import com.meishipintu.fucaiShopNew.models.bean.HttpResult
import com.meishipintu.fucaiShopNew.models.bean.WaiterInfo
import com.meishipintu.fucaiShopNew.views.adaptersAndViewholder.WaiterAdapter
import com.meishipintu.fucaiShopNew.views.adaptersAndViewholder.WaiterClickListener
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MyWaiterActivity : AppCompatActivity(),WaiterClickListener,ChooseDialogListener {

    val title:TextView by lazy{ findViewById<TextView>(R.id.title)}
    val rv:RecyclerView by lazy{ findViewById<RecyclerView>(R.id.rv)}

    val retrofit:HttpApiStores by lazy { HttpApiClinet.retrofit() }
    val disposable: CompositeDisposable by lazy{ CompositeDisposable() }
    val data = mutableListOf<WaiterInfo>()
    val adapter:WaiterAdapter by lazy{ WaiterAdapter(this@MyWaiterActivity,data)}

    var waiterSelect: WaiterInfo? = null //当前选中营业员

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_waiter)
        title.text = "我的营业员"
        findViewById<ImageView>(R.id.bt_return).setOnClickListener {
            onBackPressed()
        }
        findViewById<View>(R.id.bt_add).setOnClickListener{
            //启动新增营业员页面
            val intent = Intent(this@MyWaiterActivity, EditWaiterActivity::class.java)
            intent.putExtra("is_edit", false)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        initRv()
    }

    private fun initRv() {
        //初始化recyclerView
        if (rv.adapter == null) {
            rv.layoutManager = LinearLayoutManager(this)
            rv.itemAnimator = DefaultItemAnimator()
            rv.adapter = adapter
        }
        retrofit.getShopWaiters(Cookies.getShopId())
                .map(HttpResultFunc())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object:Observer<List<WaiterInfo>>{
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                        disposable.add(d)
                    }

                    override fun onNext(value: List<WaiterInfo>?) {
                        if (value != null) {
                            data.clear()
                            data.addAll(value)
                            adapter.notifyDataSetChanged()
                        }
                    }

                    override fun onError(e: Throwable?) {
                        e?.printStackTrace()
                        Toast.makeText(this@MyWaiterActivity,"获取营业员信息失败，请稍后重试", Toast.LENGTH_SHORT).show()
                    }
                })

    }

    override fun onEditClick(position: Int) {
        //点击编辑
        val intent: Intent = Intent(this@MyWaiterActivity, EditWaiterActivity::class.java)
        intent.putExtra("is_edit", true)
        intent.putExtra("waiter", data[position])
        startActivity(intent)
    }

    override fun onDeleteClick(position: Int) {
        //点击删除
        waiterSelect = data[position]
        val deleteDialog = CustomChooseDialog(this@MyWaiterActivity,R.style.CustomDialog2,2)
        deleteDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun onOkListener(flag:Int) {
        if (flag == 2) {
            //调用删除营业员接口
            Log.i("test","shop_id:"+Cookies.getShopId()+",uid:"+Cookies.getUserId()+",waiterId:"+waiterSelect?.id)
            retrofit.deleteWaiter(Cookies.getShopId(), Cookies.getUserId(), waiterSelect?.id ?: -1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(HttpResultFunc())
                    .subscribe(object :Observer<Any>{
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable?) {
                            disposable.add(d)
                        }

                        override fun onNext(value: Any?) {
                            Toast.makeText(this@MyWaiterActivity,"删除成功",Toast.LENGTH_SHORT).show()
                            initRv()
                        }

                        override fun onError(e: Throwable?) {
                            e?.printStackTrace()
                            Toast.makeText(this@MyWaiterActivity, e?.message, Toast.LENGTH_SHORT).show()
                        }

                    })
        } else {

        }
    }

    override fun onCancelListener() {
    }

}
