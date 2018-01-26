package com.meishipintu.fucaiShopNew.models;


public class ServerUrlConstants {

	private static String getWebServerUrl() {
		return "http://a.milaipay.com";
	}

	private static String getFucaiServerUrl() {
		return "http://fucai.milaipay.com";
	}

	public static String getRegUrl() {
		return getWebServerUrl() + "/merchant/waiter/register";
	}

	public static String getLoginUrl() {
		return getWebServerUrl() + "/merchant/waiter/login";
	}

	public static String getResetPwdUrl() {
		return getWebServerUrl() + "/merchant/waiter/forgetpwd";
	}

	public static String getChgPwdUrl() {
		return getWebServerUrl() + "/merchant/waiter/resetpwd";
	}

	public static String getSendVCodeUrl() {
		return getWebServerUrl() + "/merchant/waiter/verifymob";
	}

	public static String getAddOrderMenuUrl() {
		return getWebServerUrl() + "/merchant/order/add";
	}

	public static String getOrderDishUpdateUrl() {
		return getWebServerUrl() + "/merchant/order/update";
	}

	public static String getOrderDishUpdateStatusUrl() {
		return getWebServerUrl() + "/merchant/order/update";// updatestatus
	}

	public static String getOrderDishDetailUrl() {
		return getWebServerUrl() + "/merchant/order/detail";
	}

	public static String getSubmitedOrderDishUrl() {
		return getWebServerUrl() + "/merchant/order/submitted";
	}

	public static String getTakeawayUrl() {
		return getWebServerUrl() + "/merchant/order/takeaway";
	}

	public static String getPaymentUrl() {
		return getWebServerUrl() + "/merchant/payment/myPay";// merchant/order/payment
	}

	public static String getGetMenuUrl() {
		return getWebServerUrl() + "/merchant/dish/getdish";
	}

	public static String getGetTalbeListUrl() {
		return getWebServerUrl() + "/merchant/dish/gettableno";
	}

	public static String getBindShopUrl() {
		return getWebServerUrl() + "/merchant/waiter/bindshop";
	}

	public static String getTopRecmndUrl() {
		return getWebServerUrl() + "/merchant/system/gethomeconfig";
	}

	public static String getCityListUrl() {
		return getWebServerUrl() + "/merchant/system/getcity";
	}

	public static String getNetPrintUrl() {
		return getWebServerUrl() + "/merchant/queue/add";
	}

	public static String getPaySignUrl() {
		return getWebServerUrl() + "/merchant/payment/sign";// printOrder
	}

	public static String getPayRefund() {
		return getWebServerUrl() + "/merchant/payment/refund";// hcs
	}

	public static String getMPosPayRefundSign() {
		return getWebServerUrl() + "/merchant/payment/refundSign";// hcs
	}

	public static String getAlipayScaner() {
		return getWebServerUrl() + "/merchant/payment/querystate";// hcs
	}

	public static String getCouponVerifyUrl() {
		return getWebServerUrl() + "/merchant/coupon/getcouponsn";// hcs
	}

	public static String getVerifyQrcode() {
		return getWebServerUrl() + "/merchant/order/verifyqrcode";// hcs
	}

	public static String getSignCash() {
		return getWebServerUrl() + "/merchant/payment/notifyurl";// hcs
	}

	public static String getPrintOrder() {
		return getWebServerUrl() + "/merchant/order/printorder";// hcs
	}

	public static String getVerifyTelUrl() {
		return getWebServerUrl() + "/merchant/member/show";// hcs
	}

	public static String getUseCouponResult() {
		return getWebServerUrl() + "/merchant/coupon/usecouponsn";// hcs
	}

	public static String getUseCouponSnsUrl() {
		return getWebServerUrl() + "/merchant/coupon/sendsns";// hcs
	}

	public static String getMemberDetail() {
		return getWebServerUrl() + "/merchant/member/getDetail";// hcs
	}

	public static String updateMemberDetail() {
		return getWebServerUrl() + "/merchant/member/updateDetail";// hcs
	}

	public static String getNetTeamNumReduce() {
		return getWebServerUrl() + "/merchant/queue/take";
	}

	public static String getNetTeamNumClear() {
		return getWebServerUrl() + "/merchant/queue/clear";
	}

	public static String getCouponUsedInfo() {
		return getFucaiServerUrl() + "/Home/Index/getShopCoupon";
	}

	public static String getRegisterPeople() {
		return getFucaiServerUrl() + "/Home/Index/getTotal";
	}
}
