package com.meishipintu.fucaiShopNew;

import android.content.Context;
import android.content.SharedPreferences;

import com.meishipintu.fucaiShopNew.utils.DateUtils;
import com.meishipintu.fucaiShopNew.utils.Des2;


public class Cookies {

	public static String LOGIN_INFO_FILE = "account_info";

	public static void saveUserInfo(String userId, String token, String pwd, String tel, String nick, String shopId,
									String code, String shopName, int waitorType, String shopType, int shopCategory) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		String pwdDes = "";
		try {
			pwdDes = Des2.encode("meishipintu", pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		editor.putString("userId", userId);
		editor.putString("token", token);
		editor.putString("password", pwdDes);
		editor.putString("account", tel);
		editor.putString("nick", nick);
		editor.putString("shopCode", code);
		editor.putString("shopName", shopName);
		editor.putString("shopid", shopId);
		editor.putInt("waitorType", waitorType);
		if (shopType.contains("0")) {
			shopType = "bfu,cas,mil,wx1,al1,al2,wx2,uni,icc,oth";
		}
		editor.putString("shopType", shopType);
		editor.putInt("shopCategory", shopCategory);
		editor.commit();
	}

	// ----------------------hcs-----------------
	public static void setTable(boolean setTable) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("settable", setTable);
		editor.commit();
	}

	public static boolean getTable() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		boolean setTable = settings.getBoolean("settable", false);
		return setTable;
	}

	public static String getTableName() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String tableName = settings.getString("tableName", null);
		return tableName;
	}

	public static long getTableId() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		long tableId = settings.getLong("tableNoId", -1);
		return tableId;
	}

	// ------------hcs---------设置商户备注---//
	public static void setShopComment(String shopComment) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("shopComment", shopComment);
		editor.commit();
	}

	public static String getShopComment() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String shopComment = settings.getString("shopComment", "");
		return shopComment;
	}

	public static void setShopType(String shopType) {
		if (shopType.contains("0")) {
			shopType = "bfu,cas,mil,wx1,al1,al2,wx2,uni,icc,oth";
		}
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("shopType", shopType);
		editor.commit();
	}

	// 1.建行店铺，3.联迪店铺
	public static String getShopType() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String shopType = settings.getString("shopType", "");
		// shopType="bfu,cas,mil";//"bfu,cas,mil,wx1,al1,al2,wx2,uni,icc"
		//shopType = shopType.replace("lkl", "");
		return shopType;
	}

	// ------------hcs----------
	public static void setWaitorType(int waitorType) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("waitorType", waitorType);
		editor.commit();
	}

	public static int getWaitorType() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		int waitorType = settings.getInt("waitorType", 3);
		return waitorType;
	}

	// 是否记住密码
	public static void setRemenber(int isRemenber) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("isRemenber", isRemenber);
		editor.commit();
	}

	// 是否记住密码
	public static int getRemenber() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		int isRemenber = settings.getInt("isRemenber", 0);
		return isRemenber;
	}

	public static String getShopCode() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String s = settings.getString("shopCode", "");
		return s;
	}

	public static String getUserId() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String userId = settings.getString("userId", "");
		return userId;
	}

	public static String getPassword() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String password = settings.getString("password", "");
		return password;
	}

	public static String getToken() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String token = settings.getString("token", "");
		return token;
	}

	public static String getAccount() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String account = settings.getString("account", "");
		return account;
	}

	public static void clearLogin() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("userId", "");
		editor.putString("token", "");
		editor.putString("password", "");
		// editor.putString("account", "");
		editor.putString("nick", "");
		editor.remove("shopid");
		editor.commit();
	}

	public static void saveFileSvr(String url) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("fileServer", url);
		editor.commit();
	}

	public static String getFileSvr() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		return settings.getString("fileServer", MyApplication.instance.getString(R.string.file_server_url));
	}

	public static void saveWebSvr(String url) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("webServer", url);
		editor.commit();
	}

	public static String getWebSvr() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		return settings.getString("webServer", MyApplication.instance.getString(R.string.web_server_url));
	}

	public static String getShopId() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String s = settings.getString("shopid", "");
		return s;
	}

	public static String getShopName() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String shop = settings.getString("shopName", "");
		return shop;
	}

	public static String getShopAddr() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String a = settings.getString("shopAddr", "");
		return a;
	}

	public static String getShopTel() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String t = settings.getString("shopTel", "");
		return t;
	}

	public static double getShopLat() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		double t = settings.getFloat("shopLat", 0);
		return t;
	}

	public static double getShopLon() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		double t = settings.getFloat("shopLon", 0);
		return t;
	}

	public static void saveCity(String city) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("savedCity", city);
		editor.commit();
	}

	public static String getCity() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String account = settings.getString("savedCity", "");
		return account;
	}

	public static void saveCityId(int cityId) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("cityId", cityId);
		editor.commit();
	}

	public static int getCityId() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		int cityId = settings.getInt("cityId", 0);
		return cityId;
	}

	public static void saveLoc(float lat, float lon) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putFloat("myLat", lat);
		editor.putFloat("myLon", lon);
		editor.commit();
	}

	public static float getMyLat() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		float lat = settings.getFloat("myLat", 0);
		return lat;
	}

	public static float getMyLon() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		float lon = settings.getFloat("myLon", 0);
		return lon;
	}

	public static String getD200Ver() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String ver = settings.getString("D200Ver", "SXXVer140402-1");
		return ver;
	}

	public static String getMonitorVer() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String ver = settings.getString("MonitorVer", "1.04");
		return ver;
	}

	public static void saveMPosVer(String d200Ver, String monitorVer) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("D200Ver", d200Ver);
		editor.putString("MonitorVer", monitorVer);
		editor.commit();
	}

	public static String getBluetoothName() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String ver = settings.getString("bluetoothName", "btn");
		return ver;
	}

	public static String getBluetoothMac() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String ver = settings.getString("bluetoothMac", "btm");
		return ver;
	}

	public static void saveBluetoothAddr(String name, String mac) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("bluetoothName", name);
		editor.putString("bluetoothMac", mac);
		editor.commit();
	}

	public static void saveShopCategory(int shopCategray) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("shopCategory", shopCategray);
		editor.commit();
	}

	public static int getShopCategory() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		int ver = settings.getInt("shopCategory", 1);
		return ver;
	}

	public static void setPayType(int payType) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("payType", payType);
		editor.commit();
	}

	public static int getPayType() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		int ver = settings.getInt("payType", -1);
		return ver;
	}

	public static boolean showGuide() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		boolean g = settings.getBoolean("guide_2.3.8", true);
		return g;
	}

	public static void saveShowGuide(boolean f) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("guide_2.3.8", f);
		editor.commit();
	}

	public static String getStarttime() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String start = settings.getString("start_time", DateUtils.DateUtilSecond(System.currentTimeMillis()) + " 00:00");
		return start;
	}

	public static String getEndtime() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String end = settings.getString("end_time", DateUtils.DateUtilSecond(System.currentTimeMillis()) + " 23:59");
		return end;
	}

	public static void saveStarttime(String start) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("start_time", start);
		editor.commit();
	}

	public static void saveEndtime(String end) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("end_time", end);
		editor.commit();
	}

	public static String getStart() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String start = settings.getString("start", DateUtils.DateUtilSecond(System.currentTimeMillis()));
		return start;
	}

	public static String getEnd() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String end = settings.getString("end", DateUtils.DateUtilSecond(System.currentTimeMillis()));
		return end;
	}

	public static void saveStart(String start) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("start", start);
		editor.commit();
	}

	public static void saveEnd(String end) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("end", end);
		editor.commit();
	}

	public static void savePayway(String payway) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("payway", payway);
		editor.commit();
	}

	public static String getPayway() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		String payway = settings.getString("payway", "1,2,3,4,5,6,7,8,9,10,11,12,13");
		return payway;
	}

	public static void saveAllpay(boolean flag) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("allpay", flag);
		editor.commit();
	}

	public static boolean getAllpay() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		boolean flag = settings.getBoolean("allpay", false);
		return flag;
	}

	public static void saveAlipay(boolean flag) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("alipay", flag);
		editor.commit();
	}

	public static boolean getAlipay() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		boolean flag = settings.getBoolean("alipay", false);
		return flag;
	}

	public static void saveWechat(boolean flag) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("wechat", flag);
		editor.commit();
	}

	public static boolean getWechat() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		boolean flag = settings.getBoolean("wechat", false);
		return flag;
	}

	public static void saveCup(boolean flag) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("cup", flag);
		editor.commit();
	}

	public static boolean getCup() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		boolean flag = settings.getBoolean("cup", false);
		return flag;
	}

	public static void savePaymentcard(boolean flag) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("paymentcard", flag);
		editor.commit();
	}

	public static boolean getPaymentcard() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		boolean flag = settings.getBoolean("paymentcard", false);
		return flag;
	}

	public static void saveCashpayment(boolean flag) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("cashpayment", flag);
		editor.commit();
	}

	public static boolean getCashpayment() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		boolean flag = settings.getBoolean("cashpayment", false);
		return flag;
	}

	public static void saveBaiduwallet(boolean flag) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("baiduwallet", flag);
		editor.commit();
	}

	public static boolean getBaiduwallet() {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		boolean flag = settings.getBoolean("baiduwallet", false);
		return flag;
	}

	//判断是否已经提交审核信息
	public static boolean isCommitInfo(String shopId) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		boolean flag = settings.getBoolean("isCommitInfo" + shopId, false);
		return flag;
	}

	//保存已经提交信息
	public static void hasCommitInfo(String shopId) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("isCommitInfo" + shopId, true);
		editor.commit();
	}

	//判断是否审核信息已经通过
	public static boolean isInfoPass(String shopId) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		boolean flag = settings.getBoolean("isInfoPass" + shopId, false);
		return flag;
	}

	//保存已经审核通过信息
	public static void hasInfoPassed(String shopId) {
		SharedPreferences settings = MyApplication.instance.getSharedPreferences(LOGIN_INFO_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("isInfoPass" + shopId, true);
		editor.commit();
	}

}
