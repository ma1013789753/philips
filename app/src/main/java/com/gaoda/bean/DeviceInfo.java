package com.gaoda.bean;

import java.io.Serializable;

public class DeviceInfo implements Serializable{
    /**
     * status : activate
     * multi_region : false
     * activate_time : 2018-04-02-07
     * product_id : b6ac9e30330111e8a357f8cab81d2c1a
     * record_time : 2018-03-28-09
     * certificate_id : 439d68874fc79c5a41bf37d3edd092a6a8288082d73706262e4f80ba4e398d55
     * d_count : 1
     * mac : test_13
     * is_online : false
     * certificate_time : 2018-04-02-07
     * page : 4
     * device_id : ce381530322911e88a8c1008b1d7bcf2
     */

    private String status;
    private boolean multi_region;
    private String activate_time;
    private String product_id;
    private String record_time;
    private String certificate_id;
    private int d_count;
    private String mac;
    private boolean is_online;
    private String certificate_time;
    private int page;
    private String device_id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isMulti_region() {
        return multi_region;
    }

    public void setMulti_region(boolean multi_region) {
        this.multi_region = multi_region;
    }

    public String getActivate_time() {
        return activate_time;
    }

    public void setActivate_time(String activate_time) {
        this.activate_time = activate_time;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getRecord_time() {
        return record_time;
    }

    public void setRecord_time(String record_time) {
        this.record_time = record_time;
    }

    public String getCertificate_id() {
        return certificate_id;
    }

    public void setCertificate_id(String certificate_id) {
        this.certificate_id = certificate_id;
    }

    public int getD_count() {
        return d_count;
    }

    public void setD_count(int d_count) {
        this.d_count = d_count;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public boolean isIs_online() {
        return is_online;
    }

    public void setIs_online(boolean is_online) {
        this.is_online = is_online;
    }

    public String getCertificate_time() {
        return certificate_time;
    }

    public void setCertificate_time(String certificate_time) {
        this.certificate_time = certificate_time;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }



}
