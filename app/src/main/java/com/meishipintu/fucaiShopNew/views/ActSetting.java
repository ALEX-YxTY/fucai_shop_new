package com.meishipintu.fucaiShopNew.views;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.meishipintu.fucaiShopNew.Cookies;
import com.meishipintu.fucaiShopNew.MyApplication;
import com.meishipintu.fucaiShopNew.R;
import com.meishipintu.fucaiShopNew.custom.CustomChooseDialog;
import com.meishipintu.fucaiShopNew.models.NetCallBack;
import com.meishipintu.fucaiShopNew.models.NetDataHelper;
import com.meishipintu.fucaiShopNew.models.PostGetTask;
import com.meishipintu.fucaiShopNew.models.bean.VersionInfo;
import com.meishipintu.fucaiShopNew.utils.ConstUtil;
import com.meishipintu.fucaiShopNew.utils.DownloadMgr;
import com.meishipintu.fucaiShopNew.utils.MyDialogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ActSetting extends FragmentActivity {

	public long mTableId = 0;
	public String mTableName = null;
	public static String LOGIN_INFO_FILE = "account_info";
	private CheckBox check_table = null;
	private SharedPreferences settings = null;
	private TextView setting_table = null;
	private Editor mEditor = null;
	private int mWaitorType = -1;
	private int mBoundResult = 0;
	private EditText mEtPassword = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settting);
		settings = MyApplication.instance.getSharedPreferences(
				LOGIN_INFO_FILE, Context.MODE_PRIVATE);
		mEditor = settings.edit();
		mWaitorType = Cookies.getWaitorType();
		TextView tv = findViewById(R.id.tv_title);
		tv.setText(getString(R.string.settings));
		Button bt = findViewById(R.id.btn_back);
		bt.setOnClickListener(ll);

		findViewById(R.id.rl_clear).setOnClickListener(ll);
		findViewById(R.id.rl_check_update).setOnClickListener(ll);
		findViewById(R.id.rl_change_pwd).setOnClickListener(ll);
	}


	private OnClickListener ll = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch (v.getId()) {


			case R.id.btn_back:
				finishAndAni();
				break;
			case R.id.rl_clear:
				showDialogClear();
				break;
			case R.id.rl_change_pwd:
				if (Cookies.isMaster()) {
					intent.setClass(ActSetting.this, ActChangePwd.class);
					startActivity(intent);
					overridePendingTransition(R.anim.right_in, R.anim.left_out);
				} else {
					//弹窗提示
					CustomChooseDialog dialog = new CustomChooseDialog(ActSetting.this, R.style.CustomDialog, 1);
					dialog.show();
				}

				break;

			case R.id.rl_check_update:
				doCheckUpdate();
				break;


			default:
				break;
			}

		}
	};

	protected void showDialogClear() {

		MyDialogUtil closeDialog = new MyDialogUtil(ActSetting.this) {

			@Override
			public void onClickPositive() {
				clearCache();
			}

			@Override
			public void onClickNagative() {

			}
		};
		closeDialog.showCustomMessage(getString(R.string.notice),
				getString(R.string.prompt_clear_cache),
				getString(R.string.confirm), getString(R.string.cancel));
	}

	protected void clearCache() {
		new PostGetTask<Void>(this, R.string.clearing_cache,
				R.string.clear_cache_failed, true, true, false) {

			@Override
			protected Void doBackgroudJob() throws Exception {
				try {
					File cacheDir = new File(
							DownloadMgr.getDownloadCacheDirPath(MyApplication.instance));
					if (cacheDir.exists())
						if (cacheDir.isDirectory())
							for (File child : cacheDir.listFiles())
								if (child.isFile())
									child.delete();
					File cacheDir2 = new File(ConstUtil.getUploadCacheDirPath());
					if (cacheDir2.exists())
						if (cacheDir2.isDirectory())
							for (File child : cacheDir2.listFiles())
								if (child.isFile())
									child.delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void doPostJob(Exception exception, Void result) {
				Toast.makeText(ActSetting.this,
						R.string.clear_cache_successfully, Toast.LENGTH_SHORT)
						.show();
			}
		}.execute();
	}

	private void doCheckUpdate() {
		checkVersion();
	}

	private void checkVersion() {
		NetDataHelper helper = NetDataHelper.getInstance(this);

		class MyAsy extends AsyncTask<String, Integer, File>{

			ProgressDialog mDialog = new ProgressDialog(ActSetting.this);

			@Override
			protected void onPreExecute() {
				mDialog.setTitle("下载进度");
				mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				mDialog.setCancelable(false);
				mDialog.show();
			}

			@Override
			protected void onPostExecute(final File file) {
				if (mDialog != null) {
					mDialog.dismiss();
				}
				AlertDialog.Builder builder=new AlertDialog.Builder(ActSetting.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
						.setTitle("下载完成").setMessage("新版本已经下载完成，是否安装？")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								installApk(ActSetting.this, file);
								dialog.dismiss();
							}
						}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
				builder.show();
			}

			private void installApk(Context context, File file) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
				context.startActivity(intent);
			}

			@Override
			protected File doInBackground(String... strings) {
				final String fileName = "fcb.apk";
				File tmpFile = new File("/sdcard/njfc");
				if (!tmpFile.exists()) {
					tmpFile.mkdir();
				}
				final File file = new File("/sdcard/njfc/" + fileName);

				try {
					URL url = new URL(strings[0]);

					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(1500);
					conn.setReadTimeout(1500);

					if (conn.getResponseCode() == 200) {
						InputStream is = conn.getInputStream();
						FileOutputStream fos = new FileOutputStream(file);
						try {
							byte[] buffer = new byte[1024];
							int len;
							int total = 0;
							final int lengthOfFile = conn.getContentLength();
                                /*
                                    设置最大值，必须运行在UI线程，doInBackground运行在工作线程
                                 */
							ActSetting.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									mDialog.setMax((int) (lengthOfFile / 1024.0/1024.0));
								}
							});

							float lenOfFielInMb = lengthOfFile / 1024.0f / 1024.0f;

							while ((len = is.read(buffer)) != -1) {
								fos.write(buffer, 0, len);
								fos.flush();
								total += len;
								mDialog.setProgress(total);
								float totalInMb = total / 1024.0f / 1024.0f;
								mDialog.setProgressNumberFormat(
										String.format("%.2fMb/%.2fMb", totalInMb, lenOfFielInMb)
								);
							}
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							try {
								fos.close();
								is.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					} else {
						Toast.makeText(ActSetting.this, "连接出错", Toast.LENGTH_SHORT).show();
					}
					conn.disconnect();

				} catch (Exception e) {
					e.printStackTrace();
					mDialog.dismiss();
				}
				return file;
			}

		}
		helper.getVersion(new NetCallBack<VersionInfo>() {
			@Override
			public void onSuccess(final VersionInfo data) {
				AlertDialog.Builder builder = new AlertDialog.Builder(ActSetting.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
				int currentCode = getVersionCode();
				if (currentCode < Integer.parseInt(data.getApp_version())) {
					builder.setTitle("发现新版本");
					builder.setMessage("最新版本：" + data.getApp_version_name()
							+ "\n更新内容：" + data.getApp_update_desc());
					builder.setPositiveButton("现在下载", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

							new MyAsy().execute(data.getApp_file());
						}
					});
					builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					builder.show();
				} else{
					builder.setMessage("当前版本是："+getVersionName()+",已是最新版本");
					builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							dialogInterface.dismiss();
						}
					});
					builder.show();
				}

			}

			@Override
			public void onError(String error) {
				Toast.makeText(ActSetting.this, "连接错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private int getVersionCode() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
			int versionCode = pi.versionCode;
			return versionCode;
		} catch (NameNotFoundException e) {
			return -1;
		}
	}

	private String getVersionName() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
			String versionName = pi.versionName;
			return versionName;
		} catch (NameNotFoundException e) {
			return null;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finishAndAni();
	}

	protected void finishAndAni() {
		finish();
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		switch (arg1) {
		case RESULT_OK: {
			try {
				mTableName = arg2.getStringExtra("tableName");
				mTableId = arg2.getLongExtra("tableNoId", 0);
				mEditor = settings.edit();

				if (mTableName != null && mTableId != 0) {
					mEditor.putBoolean("settable", true);
					mEditor.putLong("tableNoId", mTableId);
					mEditor.putString("tableName", mTableName);
					mEditor.commit();
					setting_table.setText("已绑定"
							+ settings.getString("tableName", "未绑定桌号"));
					check_table.setChecked(true);
				} else {
					mEditor.putBoolean("settable", false);
					mEditor.putLong("tableNoId", 0);
					mEditor.putString("tableName", null);
					mEditor.commit();
					check_table.setChecked(false);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			break;
		default:
			mEditor.putBoolean("settable", false);
			mEditor.putLong("tableNoId", 0);
			mEditor.putString("tableName", null);
			check_table.setChecked(false);
			mEditor.commit();
			break;
		}
	}

}
