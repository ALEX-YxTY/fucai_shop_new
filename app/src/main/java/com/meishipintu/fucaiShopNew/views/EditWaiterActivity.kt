package com.meishipintu.fucaiShopNew.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.meishipintu.fucaiShopNew.Cookies
import com.meishipintu.fucaiShopNew.R
import com.meishipintu.fucaiShopNew.models.HttpApiClinet
import com.meishipintu.fucaiShopNew.models.HttpApiStores
import com.meishipintu.fucaiShopNew.models.bean.HttpResult
import com.meishipintu.fucaiShopNew.models.bean.WaiterInfo
import com.meishipintu.fucaiShopNew.utils.StringUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class EditWaiterActivity : AppCompatActivity() {

    val isEdit: Boolean by lazy {
        intent.getBooleanExtra("is_edit", false)
    }
    val title: TextView by lazy { findViewById<TextView>(R.id.title) }
    val etName: EditText by lazy { findViewById<EditText>(R.id.et_name) }
    val etTel: EditText by lazy { findViewById<EditText>(R.id.et_tel) }
    val etAccount: EditText by lazy { findViewById<EditText>(R.id.et_account) }
    val etPsw: EditText by lazy { findViewById<EditText>(R.id.et_psw) }
    val btSure: Button by lazy { findViewById<Button>(R.id.bt_sure) }

    val disposable: CompositeDisposable by lazy { CompositeDisposable() }
    val retrofit: HttpApiStores by lazy { HttpApiClinet.retrofit() }

    var waiterId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_waiter)
        title.text = if (isEdit) "编辑营业员" else "新建营业员"
        btSure.text = if (isEdit) "保存修改" else "确认新建"
        findViewById<View>(R.id.bt_return).setOnClickListener {
            onBackPressed()
        }

        if (isEdit) {
            //初始化要修改的
            val waiterInfo = intent.getSerializableExtra("waiter") as WaiterInfo
            etName.setText(waiterInfo.nickname)
            etTel.setText(waiterInfo.mobile)
            etAccount.setText(waiterInfo.account)
            etPsw.setText(waiterInfo.real_psw)
            waiterId = waiterInfo.id
        }
        btSure.setOnClickListener {
            if (StringUtils.isNullOrEmpty(arrayOf(etName.text.toString(), etAccount.text.toString()
                            , etTel.text.toString(), etPsw.text.toString()))) {
                Toast.makeText(this@EditWaiterActivity, "输入项不能为空", Toast.LENGTH_SHORT).show()
            } else {
                retrofit.updateWaiter(Cookies.getShopId(), waiterId, etName.text.toString(), etAccount.text.toString()
                        , etTel.text.toString(), etPsw.text.toString(), Cookies.getShopName())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object :Observer<HttpResult<Any>>{
                            override fun onComplete() {
                            }

                            override fun onSubscribe(d: Disposable?) {
                                disposable.add(d)
                            }

                            override fun onNext(value: HttpResult<Any>?) {
                                when (value?.status) {
                                    1 -> {
                                        Toast.makeText(this@EditWaiterActivity, value?.msg, Toast.LENGTH_SHORT).show()
                                        this@EditWaiterActivity.finish()
                                    }
                                    else -> Toast.makeText(this@EditWaiterActivity, value?.msg, Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onError(e: Throwable?) {
                                e?.printStackTrace()
                                Toast.makeText(this@EditWaiterActivity,"修改营业员信息失败，请稍后重试",Toast.LENGTH_SHORT).show()
                            }

                        })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
