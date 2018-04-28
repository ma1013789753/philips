package com.gaoda.bean;

import java.io.Serializable;

public class DeviceBean implements Serializable{

    private String enduser_id;
    private String binding_time;
    private String binding_role;
    private String app_id;
    private String binding_type;
    private String device_alias;
    private DeviceInfo device_info;

    public String getDevice_alias() {
        return device_alias;
    }

    public void setDevice_alias(String device_alias) {
        this.device_alias = device_alias;
    }

    public DeviceInfo getDevice_info() {
        return device_info;
    }

    public void setDevice_info(DeviceInfo device_info) {
        this.device_info = device_info;
    }

    public String getEnduser_id() {
        return enduser_id;
    }

    public void setEnduser_id(String enduser_id) {
        this.enduser_id = enduser_id;
    }

    public String getBinding_time() {
        return binding_time;
    }

    public void setBinding_time(String binding_time) {
        this.binding_time = binding_time;
    }

    public String getBinding_role() {
        return binding_role;
    }

    public void setBinding_role(String binding_role) {
        this.binding_role = binding_role;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getBinding_type() {
        return binding_type;
    }

    public void setBinding_type(String binding_type) {
        this.binding_type = binding_type;
    }

}
