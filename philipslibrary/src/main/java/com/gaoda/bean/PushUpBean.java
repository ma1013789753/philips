package com.gaoda.bean;

import java.io.Serializable;

public class PushUpBean implements Serializable{

    /**
     * ErrorCode : 0
     * Notify : 0
     * PM25 : 128
     * Switch : 1
     * WindSpeed : 2
     * Countdown : 7
     * Prompt : 1
     * childLock : 0
     * FilterLife1 : 4728
     * FilterLife2 : 65535
     * FilterType0 : 342
     * UILigth : 2
     * FilterType : 3
     * FilterType1 : 0
     * FilterType2 : 0
     * AQILight : 1
     * WorkMode : 420
     * Runtime : 1000
     */
    /**
     *
     */
    private String ErrorCode;
    /**
     * Notify		[0,1,2,3]	0(无通知) 1(请清洁预过滤网。)2(请更换纳米级劲护滤网。 如果没有及时更换滤网，产品将被锁定停止运行。)  3(请更换活性炭滤网。如果没有及时更换滤网，产品将被锁定停止运行。)
     */
    private String Notify;
    /**
     *PM25		[0, 500]	取值范围：0-500，间距：1, 单位：null

     */
    private String PM25;
    /**
     *Switch	下发	[0,1]	枚举值：0（关闭） 1（开启）
     */
    private String Switch;
    /**
     *WindSpeed	下发	[0,1,2,3,4]	枚举值：0（静音） 1（低档） 2（中档） 3（高档） 4（最高档）

     */
    private String WindSpeed;
    /**
     *倒计时关机	Countdown	下发	[1, 12]	取值范围：1-12，间距：1

     */
    private String Countdown;
    /**
     *报警提示Prompt		[0,1]	枚举值：0（需要重启） 1（需要清洗）
     */
    private String Prompt;
    /**
     *童锁	childLock	下发	[0,1]	枚举值：0（关） 1（开）

     */
    private String childLock;
    /**
     *滤网1寿命	FilterLife1		0~10200	取值范围：0-10200，间距：1, 单位：小时
     */
    private String FilterLife1;
    /**
     *滤网2寿命	FilterLife2		0~10200	取值范围：0-10200，间距：1, 单位：小时
     */
    private String FilterLife2;
    /**
     *预过滤网寿命	FilterType0		0~360	取值范围：0-360，间距：1, 单位：小时
     */
    private String FilterType0;
    /**
     *面板灯	UILight	下发	[0,1,2]	枚举值：0（off） 1（dim） 2（on）
     */
    private String UILight;
    /**
     *
     */
    private String FilterType;
    /**
     *
     */
    private String FilterType1;
    /**
     *
     */
    private String FilterType2;
    /**
     *灯环	AQILight	下发	[0,1,2,3,4]	枚举值：0（0%） 1（25%） 2（50%） 3（75%） 4（100%）
     */
    private String AQILight;
    /**
     *工作模式	WorkMode	下发	[0,1,2,3]	枚举值：1（手动） 2（颗粒物净化） 3（过敏源净化） 4（细菌净化）
     */
    private String WorkMode;
    /**
     *
     */
    private String Runtime;

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public String getNotify() {
        return Notify;
    }

    public void setNotify(String Notify) {
        this.Notify = Notify;
    }

    public String getPM25() {
        return PM25;
    }

    public void setPM25(String PM25) {
        this.PM25 = PM25;
    }

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

    public String getPrompt() {
        return Prompt;
    }

    public void setPrompt(String Prompt) {
        this.Prompt = Prompt;
    }

    public String getChildLock() {
        return childLock;
    }

    public void setChildLock(String childLock) {
        this.childLock = childLock;
    }

    public String getFilterLife1() {
        return FilterLife1;
    }

    public void setFilterLife1(String FilterLife1) {
        this.FilterLife1 = FilterLife1;
    }

    public String getFilterLife2() {
        return FilterLife2;
    }

    public void setFilterLife2(String FilterLife2) {
        this.FilterLife2 = FilterLife2;
    }

    public String getFilterType0() {
        return FilterType0;
    }

    public void setFilterType0(String FilterType0) {
        this.FilterType0 = FilterType0;
    }

    public String getUILigth() {
        return UILight;
    }

    public void setUILigth(String UILigth) {
        this.UILight = UILigth;
    }

    public String getFilterType() {
        return FilterType;
    }

    public void setFilterType(String FilterType) {
        this.FilterType = FilterType;
    }

    public String getFilterType1() {
        return FilterType1;
    }

    public void setFilterType1(String FilterType1) {
        this.FilterType1 = FilterType1;
    }

    public String getFilterType2() {
        return FilterType2;
    }

    public void setFilterType2(String FilterType2) {
        this.FilterType2 = FilterType2;
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

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String Runtime) {
        this.Runtime = Runtime;
    }
}
