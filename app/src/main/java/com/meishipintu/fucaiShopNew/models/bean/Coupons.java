package com.meishipintu.fucaiShopNew.models.bean;

import org.json.JSONObject;

import java.io.Serializable;

public class Coupons implements Serializable{

	private long id;
	private String sn;
	private String couponName;
	private String couponValue;
	private String minPrice;
	
	
	private long startTime;
	private long endTime;
	private String shopName;
	private String couponDetail;
	
	public Coupons(JSONObject j) throws Exception
	{
		this.id=j.getLong("coupon_id");//
		this.sn=j.getString("coupon_sn");//
		this.couponName=j.getString("coupon_name");//
		this.couponValue=j.getString("coupon_value");//
		this.minPrice=j.getString("min_price");//
		this.shopName=j.getString("shop_names");
		this.endTime=j.getInt("end_time");
		this.startTime=j.getInt("start_time");
		this.couponDetail=j.getString("desc");
	}
	
	//id
	public long getId()
	{
		return this.id;
	}
	public void setId(long id)
	{
		this.id=id;
	}
	
	public void setCouponDetail(String detail)
	{
		this.couponDetail=detail;
	}
	public String getCouponDetail()
	{
		return this.couponDetail;
	}
	
	public String getCouponSn()
	{
		return this.sn;
	}
	public void setCouponSn(String sn)
	{
		this.sn=sn;
	}
	//coupon name
	public String getCouponName()
	{
		return this.couponName;
	}
	public void setCouponName(String name)
	{
		this.couponName=name;
	}
	//value
	public String getCouponValue()
	{
		return this.couponValue;
	}
	public void setCouponValue(String value)
	{
		this.couponValue=value;
	}
	//minprice
	public String getMinPrice()
	{
		return this.minPrice;
	}
	public void setMinPrice(String minPrice)
	{
		this.minPrice=minPrice;
	}
	
	public String getShopName()
	{
		return this.shopName;
	}
	public void setShopName(String shopName)
	{
		this.shopName=shopName;
	}
	
	public long getStartTime()
	{
		return this.startTime;
	}
	public void setStartTime(int startTime)
	{
		this.startTime=startTime;
	}
	
	public long getEndTime()
	{
		return this.endTime;
	}
	public void setEndTime(long endTime)
	{
		this.endTime=endTime;
	}
}
