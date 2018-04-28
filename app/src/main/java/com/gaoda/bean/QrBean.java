package com.gaoda.bean;

public class QrBean {

    private String UserId;
    private String DeviceId;
    private String Time;
    private String vercode;

    public String getVercode() {
        return vercode;
    }

    public void setVercode(String vercode) {
        this.vercode = vercode;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
