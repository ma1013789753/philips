package com.gaoda.bean;

public class PushDownBean {

    /**
     * Switch : 0
     * WindSpeed : 0
     * Countdown : 1
     * childLock : 0
     * UILight : 0
     * AQILight : 0
     * WorkMode : 0
     */

    private String Switch;
    private String WindSpeed;
    private String Countdown;
    private String childLock;
    private String UILight;
    private String AQILight;
    private String WorkMode;

    public String getSwitch() {
        return Switch;
    }

    public void setSwitch(String Switch) {
        this.Switch = Switch;
    }

    public String getWindSpeed() {
        return WindSpeed;
    }

    public void setWindSpeed(String WindSpeed) {
        this.WindSpeed = WindSpeed;
    }

    public String getCountdown() {
        return Countdown;
    }

    public void setCountdown(String Countdown) {
        this.Countdown = Countdown;
    }

    public String getChildLock() {
        return childLock;
    }

    public void setChildLock(String childLock) {
        this.childLock = childLock;
    }

    public String getUILight() {
        return UILight;
    }

    public void setUILight(String UILight) {
        this.UILight = UILight;
    }

    public String getAQILight() {
        return AQILight;
    }

    public void setAQILight(String AQILight) {
        this.AQILight = AQILight;
    }

    public String getWorkMode() {
        return WorkMode;
    }

    public void setWorkMode(String WorkMode) {
        this.WorkMode = WorkMode;
    }
}
