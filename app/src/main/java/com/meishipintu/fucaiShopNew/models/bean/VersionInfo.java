package com.meishipintu.fucaiShopNew.models.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/25 0025.
 */
public class VersionInfo implements Serializable {
    private String id;
    private String app_name;
    private String app_file;
    private String app_version;
    private String app_version_name;
    private String app_deman;
    private String app_update_desc;
    private String app_platform;
    private String app_type;
    private String app_type_key;
    private String create_time;
    private String update_time;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getApp_type_key() {
        return app_type_key;
    }

    public void setApp_type_key(String app_type_key) {
        this.app_type_key = app_type_key;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getApp_type() {
        return app_type;
    }

    public void setApp_type(String app_type) {
        this.app_type = app_type;
    }

    public String getApp_platform() {
        return app_platform;
    }

    public void setApp_platform(String app_platform) {
        this.app_platform = app_platform;
    }

    public String getApp_update_desc() {
        return app_update_desc;
    }

    public void setApp_update_desc(String app_update_desc) {
        this.app_update_desc = app_update_desc;
    }

    public String getApp_deman() {
        return app_deman;
    }

    public void setApp_deman(String app_deman) {
        this.app_deman = app_deman;
    }

    public String getApp_version_name() {
        return app_version_name;
    }

    public void setApp_version_name(String app_version_name) {
        this.app_version_name = app_version_name;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getApp_file() {
        return app_file;
    }

    public void setApp_file(String app_file) {
        this.app_file = app_file;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }


}
