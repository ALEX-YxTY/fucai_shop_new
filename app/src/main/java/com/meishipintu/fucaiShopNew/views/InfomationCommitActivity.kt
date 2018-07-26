package com.meishipintu.fucaiShopNew.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT

import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.meishipintu.fucaiShopNew.R
import com.meishipintu.fucaiShopNew.models.HttpApiClinet
import com.meishipintu.fucaiShopNew.models.HttpApiStores
import com.meishipintu.fucaiShopNew.utils.StringUtils

import com.meishipintu.fucaiShopNew.Cookies
import com.meishipintu.fucaiShopNew.custom.CustomProgressDialog
import com.meishipintu.fucaiShopNew.models.HttpResultFunc
import com.meishipintu.fucaiShopNew.models.bean.AccountInfo
import com.meishipintu.fucaiShopNew.utils.PicGetUtil
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class InfomationCommitActivity : Activity(), View.OnClickListener, PicGetUtil.SuccessListener {

    val etName: EditText by lazy { findViewById<EditText>(R.id.et_name) }
    val etTel: EditText by lazy { findViewById<EditText>(R.id.et_tel) }
    val etCardNum: EditText by lazy { findViewById<EditText>(R.id.et_card_num) }

    val ivID: ImageView by lazy { findViewById<ImageView>(R.id.iv_ID) }
    val ivFront: ImageView by lazy { findViewById<ImageView>(R.id.iv_front) }
    val ivBack: ImageView by lazy { findViewById<ImageView>(R.id.iv_back) }

    private var bankPicFile: File? = null
    private var frontPicFile: File? = null
    private var backPicFile: File? = null

    private var glide: RequestManager? = null
    private val httpApiStores: HttpApiStores by lazy { HttpApiClinet.retrofit() }
    private val disposable: CompositeDisposable by lazy { CompositeDisposable() }
    private var loadingDialog: CustomProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_commit)
        glide = Glide.with(this)
        findViewById<View>(R.id.bt_return).setOnClickListener(this)
        findViewById<View>(R.id.rl_id).setOnClickListener(this)
        findViewById<View>(R.id.rl_front).setOnClickListener(this)
        findViewById<View>(R.id.rl_back).setOnClickListener(this)
        findViewById<View>(R.id.bt_commit).setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_return -> onBackPressed()
            R.id.rl_id ->
                //启动选择图片
                PicGetUtil.choosePicture(this, this, "identity", CHOOSE_BANK)
            R.id.rl_front ->
                //启动选择图片
                PicGetUtil.choosePicture(this, this, "front", CHOOSE_FRONT)
            R.id.rl_back ->
                //启动选择图片
                PicGetUtil.choosePicture(this, this, "back", CHOOSE_BACK)
            R.id.bt_commit ->
                //提交申请
                if (StringUtils.isNullOrEmpty(arrayOf(etName.text.toString(), etCardNum.text.toString(), etTel.text.toString()))) {
                    Toast.makeText(this@InfomationCommitActivity, "输入项不可为空", Toast.LENGTH_SHORT).show()
                } else if (bankPicFile == null || backPicFile == null || frontPicFile == null) {
                    Toast.makeText(this@InfomationCommitActivity, "请选择照片", Toast.LENGTH_SHORT).show()
                } else {
                    //提交申请
                    val photoRequestBody1 = RequestBody.create(MediaType.parse("image/*"), bankPicFile)
                    val photo1 = MultipartBody.Part.createFormData("bank", "bank.jpg", photoRequestBody1)
                    val photoRequestBody2 = RequestBody.create(MediaType.parse("image/*"), frontPicFile)
                    val photo2 = MultipartBody.Part.createFormData("front", "front.jpg", photoRequestBody2)
                    val photoRequestBody3 = RequestBody.create(MediaType.parse("image/*"), backPicFile)
                    val photo3 = MultipartBody.Part.createFormData("back", "back.jpg", photoRequestBody3)
                    val trueName = RequestBody.create(null, etName.text.toString())
                    val bankAccount = RequestBody.create(null, etCardNum.text.toString())
                    val tel = RequestBody.create(null, etTel.text.toString())
                    val shopId = RequestBody.create(null, Cookies.getShopId())
                    val shopName = RequestBody.create(null, Cookies.getShopName())
                    if (loadingDialog == null) {
                        loadingDialog = CustomProgressDialog(this, "正在上传")
                        loadingDialog?.show()
                    }
                    httpApiStores.commitShopInfo(trueName, tel, bankAccount, shopId, shopName, photo1, photo2, photo3)
                            .subscribeOn(Schedulers.io())
                            .map(HttpResultFunc())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(object : Observer<AccountInfo> {
                                override fun onNext(value: AccountInfo?) {
                                    loadingDialog?.dismiss()
                                    Cookies.hasCommitInfo(Cookies.getShopId())
                                    Toast.makeText(this@InfomationCommitActivity, "信息提交成功", Toast.LENGTH_SHORT).show()
                                    Log.d("test", "result:" + value)
                                    setResult(RESULT_OK)
                                    this@InfomationCommitActivity.finish()
                                }

                                override fun onSubscribe(d: Disposable?) {
                                    disposable.add(d)
                                }

                                override fun onError(e: Throwable?) {
                                    e?.printStackTrace()
                                    loadingDialog?.dismiss()
                                    Toast.makeText(this@InfomationCommitActivity, "上传失败，请稍候重试", LENGTH_SHORT).show()
                                }

                                override fun onComplete() {
                                }
                            })
                }
        }
    }

    override fun onSuccess(file: File, chooseCode: Int) {
        when (chooseCode) {
            CHOOSE_BANK -> {
                //获取身份证照片
                bankPicFile = file
                Log.d("test", "idPic:" + bankPicFile!!)
                glide!!.load(bankPicFile).apply(RequestOptions.skipMemoryCacheOf(true)).into(ivID)
            }
            CHOOSE_FRONT -> {
                //获取银行卡正面
                frontPicFile = file
                Log.d("test", "frontPicFile:" + frontPicFile!!)
                glide!!.load(frontPicFile).apply(RequestOptions.skipMemoryCacheOf(true)).into(ivFront)
            }
            CHOOSE_BACK -> {
                //获取银行卡背面
                backPicFile = file
                Log.d("test", "backPicFile:" + backPicFile!!)
                glide!!.load(backPicFile).apply(RequestOptions.skipMemoryCacheOf(true)).into(ivBack)
            }
        }
    }

    //权限申请回调
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        PicGetUtil.onPermissiionRequestResult(this, requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        PicGetUtil.onActivityResult(this, requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    companion object {
        private val CHOOSE_BANK = 1
        private val CHOOSE_FRONT = 2
        private val CHOOSE_BACK = 3
    }
}
