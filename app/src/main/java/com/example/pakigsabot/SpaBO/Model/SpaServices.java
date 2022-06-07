package com.example.pakigsabot.SpaBO.Model;

public class SpaServices {
    String spaServiceId, spaServiceName, spaServiceDesc, spaServiceRate, spaServiceImg;

    public SpaServices() {
    }

    public SpaServices(String spaServiceId, String spaServiceName, String spaServiceDesc, String spaServiceRate, String spaServiceImg) {
        this.spaServiceId = spaServiceId;
        this.spaServiceName = spaServiceName;
        this.spaServiceDesc = spaServiceDesc;
        this.spaServiceRate = spaServiceRate;
        this.spaServiceImg = spaServiceImg;
    }

    public String getSpaServiceId() {
        return spaServiceId;
    }

    public void setSpaServiceId(String spaServiceId) {
        this.spaServiceId = spaServiceId;
    }

    public String getSpaServiceName() {
        return spaServiceName;
    }

    public void setSpaServiceName(String spaServiceName) {
        this.spaServiceName = spaServiceName;
    }

    public String getSpaServiceDesc() {
        return spaServiceDesc;
    }

    public void setSpaServiceDesc(String spaServiceDesc) {
        this.spaServiceDesc = spaServiceDesc;
    }

    public String getSpaServiceRate() {
        return spaServiceRate;
    }

    public void setSpaServiceRate(String spaServiceRate) {
        this.spaServiceRate = spaServiceRate;
    }

    public String getSpaServiceImg() {
        return spaServiceImg;
    }

    public void setSpaServiceImg(String spaServiceImg) {
        this.spaServiceImg = spaServiceImg;
    }





}
