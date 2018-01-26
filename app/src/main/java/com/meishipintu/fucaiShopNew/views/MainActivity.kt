package com.meishipintu.fucaiShopNew.views

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.view.KeyEvent
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.githang.statusbar.StatusBarCompat
import com.meishipintu.fucaiShopNew.Cookies
import com.meishipintu.fucaiShopNew.R
import com.meishipintu.fucaiShopNew.models.NetCallBack
import com.meishipintu.fucaiShopNew.models.NetDataHelper
import com.meishipintu.fucaiShopNew.models.bean.VersionInfo
import com.meishipintu.fucaiShopNew.utils.DialogUtils
import com.meishipintu.fucaiShopNew.utils.MyDialogUtil
import com.meishipintu.fucaiShopNew.utils.PackageUtils
import com.meishipintu.fucaiShopNew.views.fragments.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity(),StartCameraListener {

    val REQUEST_CAMERA_PERMISSION: Int = 100 //请求存储权限


    private var fragmentManager: FragmentManager? = null
    private val vipFrag:Fragment by lazy{ C0_VipFrag.createInstance()}

    private val mTvCityName: TextView? = null
    private var mCityId = 0

    private var netDataHelper: NetDataHelper? = null
    private var mProgressDialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarCompat.setStatusBarColor(this, -0x85b9)
        setContentView(R.layout.activity_main)
        netDataHelper = NetDataHelper.getInstance(this)
        //获取版本信息及最新版本
        val info = PackageUtils.getPackageInfo(this)
        netDataHelper!!.getVersion(object : NetCallBack<VersionInfo> {
            override fun onSuccess(data: VersionInfo) {
                //下载新版本
                if (Integer.parseInt(data.app_version) > info!!.versionCode) {

                    val builder = AlertDialog.Builder(this@MainActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("版本更新")
                            .setMessage("最新版本：" + data.app_version_name + "\n更新内容：" + data.app_update_desc)
                            .setPositiveButton("确定") { dialogInterface, _ ->
                                MyAsy(this@MainActivity).execute("http://fucai.milaipay.com/download/fcb.apk")
                                dialogInterface.dismiss() }
                            .setNegativeButton("取消") { dialogInterface, _ -> dialogInterface.dismiss() }
                    builder.show()
                }
            }

            override fun onError(error: String) {
                Toast.makeText(this@MainActivity, "连接错误", Toast.LENGTH_SHORT).show()
            }
        })
        //获取商户信息是否审核通过
        if (!Cookies.isInfoPass(Cookies.getShopId())) {
            netDataHelper!!.isUserInfoChecked(Cookies.getShopId(), object : NetCallBack<Boolean> {
                override fun onSuccess(data: Boolean?) {
                    if (data!!) {
                        Cookies.hasInfoPassed(Cookies.getShopId())
                    }
                }

                override fun onError(error: String) {}
            })
        }
        setContentView(R.layout.activity_main)

        fragmentManager = supportFragmentManager
        val radioGroup = findViewById<RadioGroup>(R.id.rg_tab)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_home -> showFrag("newhome")
                R.id.rb_vip -> showFrag("vip")
                R.id.rb_mine -> showFrag("mine")
                R.id.rb_reward -> showFrag("reward")
            }
        }
        radioGroup.check(R.id.rb_vip)
    }

    private fun showFrag(frag: String) {
        val transaction = fragmentManager!!.beginTransaction()
        var fragment: Fragment? = null
        when (frag) {
            "newhome" -> {
                fragment = NoticFragment.createInstance()
                StatusBarCompat.setStatusBarColor(this, 0x55000000)
            }
            "vip" -> {
                fragment = vipFrag
                StatusBarCompat.setStatusBarColor(this, -0x85b9)
            }
            "mine" -> {
                fragment = D0_MineFrag.createInstance()
                StatusBarCompat.setStatusBarColor(this, 0x55000000)
            }
            "reward" -> {
                //			if (Cookies.isInfoPass(Cookies.getShopId())) {
                //审核已通过
                fragment = E0_RewardFrag.createInstance()
                //			} else {
                //还未通过审核
                //				fragment = F0_AddInfoFrag.createInstance();
                //			}
                StatusBarCompat.setStatusBarColor(this, 0x55000000)
            }
        }
        transaction.replace(R.id.content, fragment)
        transaction.commit()
    }

    //vip页面请求相机权限
    override fun onCameraStart() {
        val hasStoragePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {        //未授权
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {                    //系统申请权限框不再弹出
                DialogUtils.showCustomDialog(this, "本应用需要获取相机的权限", { dialog, _ ->
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA)
                            , REQUEST_CAMERA_PERMISSION)
                    dialog.dismiss()
                }) { dialog, _ -> dialog.dismiss() }
                return
            }
            //系统框弹出时直接申请
            ActivityCompat.requestPermissions(this, arrayOf(android
                    .Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            return
        }
        //已有授权
        val intent = Intent(this, ActCaptureTicket::class.java)
        intent.putExtra("CHECK_CODE", 4)//
        vipFrag.startActivityForResult(intent, 50)
    }

    //退出程序对话框
    fun showDialogQuit() {
        val qDialog = object : MyDialogUtil(this@MainActivity) {
            override fun onClickPositive() {
                Cookies.clearLogin()
            }
            override fun onClickNagative() {}
        }
        qDialog.showCustomMessage(getString(R.string.notice),
                getString(R.string.prompts_quit), getString(R.string.confirm),
                getString(R.string.cancel))
    }

    override fun onKeyDown(keyCode: Int, event: android.view.KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showDialogQuit()
        }
        return false
    }

    override fun onResume() {
        super.onResume()
        if (mCityId != Cookies.getCityId()) {
            mTvCityName!!.text = Cookies.getCity()
            mCityId = Cookies.getCityId()
        }
    }

    /* 安装apk */
    fun installApk(context: Context, file: File) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        context.startActivity(intent)
    }

    //下载文件AsyncTask
    private inner class MyAsy(val ctx:Context) : AsyncTask<String, String, File>() {
        //点击调用界面
        override fun onPreExecute() {
            mProgressDialog = ProgressDialog(ctx, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
            mProgressDialog!!.setMessage("下载文件...")
            mProgressDialog!!.setTitle("下载进度")
            mProgressDialog!!.isIndeterminate = false
            mProgressDialog!!.max = 100
            mProgressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            mProgressDialog!!.setCancelable(false)
            mProgressDialog!!.show()
        }

        //点击调用后界面
        override fun onPostExecute(file: File) {
            mProgressDialog!!.dismiss()

            val builder = AlertDialog.Builder(this@MainActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("下载完成").setMessage("新版本已经下载完成，是否安装？")
                    .setPositiveButton("确定") { dialog, _ ->
                        installApk(this@MainActivity, file)
                        dialog.dismiss() }
                    .setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
            builder.show()
        }

        override fun doInBackground(vararg params: String): File {
            val fileName = "fcb.apk"
            val tmpFile = File("/sdcard/njfc")
            if (!tmpFile.exists()) {
                tmpFile.mkdir()
            }
            val file = File("/sdcard/njfc/" + fileName)

            try {
                val url = URL(params[0])
                val conn = url.openConnection() as HttpURLConnection
                conn.connectTimeout = 1500
                conn.readTimeout = 1500

                //连接成功
                if (conn.responseCode == 200) {

                    val `is` = conn.inputStream
                    val fos = FileOutputStream(file)

                    val buf = ByteArray(1024)
                    var readLen: Int
                    var total = 0
                    val lengthOfFile = conn.contentLength
                    mProgressDialog!!.max = lengthOfFile
                    val lenOfFielInMb = lengthOfFile.toFloat() / 1024.0f / 1024.0f
                    try {
                        while (`is`.read(buf) != -1) {
                            readLen = `is`.read(buf)
                            fos.write(buf, 0, readLen)
                            fos.flush()
                            total += readLen
                            mProgressDialog!!.progress = total
                            val totalInMb = total.toFloat() / 1024.0f / 1024.0f
                            /*
                                通过设置显示字符串格式取代publishProgress
                                setProgress只支持int类型，无法显示小数，这里用setProgressNumberFormat直接
                                设置字符串形式，直接控制右下角字符，使得setProgress和setMax只控制进度条
                                测试setProgressNumberFormat可以在工作线程调用，但为保险起见，可使用runOnUIThread
                             */
                            mProgressDialog!!.setProgressNumberFormat(String.format("%.2fMb/%.2fMb", totalInMb, lenOfFielInMb))

                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    } finally {
                        `is`.close()
                        fos.close()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "连接出错", Toast.LENGTH_SHORT).show()
                }
                conn.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return file
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION ->{
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //拒绝授权
                    Toast.makeText(this, "没有相机权限，将有部分功能无法使用，请在系统设置中增加应用的相应授权", Toast.LENGTH_SHORT)
                            .show()
                } else {
                    //已有授权
                    val intent = Intent(this, ActCaptureTicket::class.java)
                    intent.putExtra("CHECK_CODE", 4)//
                    vipFrag.startActivityForResult(intent, 50)
                }
            }
        }
    }
}
