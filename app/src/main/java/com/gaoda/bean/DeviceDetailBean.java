package com.gaoda.bean;

import java.io.Serializable;

public class DeviceDetailBean implements Serializable {


    /**
     * status : activate
     * multi_region : false
     * activate_time : 2018-4-10 2:47
     * product_id : sadfas
     * record_time : 2018-4-2 17:37
     * certificate_id : c36292db24fb5697c001c3d37464a236cf4d93fe4ad0608515607ad07bd5993f
     * is_online : false
     * mac : B0F893166D28
     * d_count : 1
     * certificate_time : 2018-4-10 2:47
     * page : 4
     * device_id : 7b6f4880365911e891501008b1d7bcf2
     */

    private String status;
    private boolean multi_region;
    private String activate_time;
    private String product_id;
    private String record_time;
    private String certificate_id;
    private boolean is_online;
    private String mac;
    private int d_count;
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

    public boolean isIs_online() {
        return is_online;
    }

    public void setIs_online(boolean is_online) {
        this.is_online = is_online;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getD_count() {
        return d_count;
    }

    public void setD_count(int d_count) {
        this.d_count = d_count;
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
