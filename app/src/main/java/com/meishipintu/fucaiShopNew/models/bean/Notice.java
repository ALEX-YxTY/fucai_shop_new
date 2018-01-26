package com.meishipintu.fucaiShopNew.models.bean;

/**
 * Created by Administrator on 2017/3/6.
 * <p>
 * 主要功能：
 */

public class Notice {
    private String url;
    private String id;
    private String push_content;
    private String push_time;
    private String push_uid;
    private String shop_id;
    private String push_title;
    private String push_type;
    private String push_show;

    private String username;
    private String tel;
    private String money;
    private String name;
    private String address;
    private String oid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPush_content() {
        return push_content;
    }

    public void setPush_content(String push_content) {
        this.push_content = push_content;
    }

    public String getPush_time() {
        return push_time;
    }

    public void setPush_time(String push_time) {
        this.push_time = push_time;
    }

    public String getPush_uid() {
        return push_uid;
    }

    public void setPush_uid(String push_uid) {
        this.push_uid = push_uid;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }
    public String getPush_title() {
        return push_title;
    }

    public void setPush_title(String push_title) {
        this.push_title = push_title;
    }
    public String getPush_type() {
        return push_type;
    }

    public String getPush_show() {
        return push_show;
    }

    public void setPush_show(String push_show) {
        this.push_show = push_show;
    }

    public void setPush_type(String push_type) {
        this.push_type = push_type;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "url='" + url + '\'' +
                ", id='" + id + '\'' +
                ", push_content='" + push_content + '\'' +
                ", push_time='" + push_time + '\'' +
                ", push_uid='" + push_uid + '\'' +
                ", shop_id='" + shop_id + '\'' +
                ", push_title='" + push_title + '\'' +
                ", push_type='" + push_type + '\'' +
                ", push_show='" + push_show + '\'' +
                ", username='" + username + '\'' +
                ", tel='" + tel + '\'' +
                ", money='" + money + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", oid='" + oid + '\'' +
                '}';
    }
}
