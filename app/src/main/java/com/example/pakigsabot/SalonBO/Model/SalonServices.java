package com.example.pakigsabot.SalonBO.Model;

public class SalonServices {
    String salonServiceId, salonServiceName, salonServiceDesc, salonServiceRate, salonServiceImgUrl;

    //required empty constructor
    public SalonServices() {
    }

    public SalonServices(String salonServId, String salonServName, String salonServDesc, String salonServRate, String salonServImgUrl) {
        this.salonServiceId = salonServId;
        this.salonServiceName = salonServName;
        this.salonServiceDesc = salonServDesc;
        this.salonServiceRate = salonServRate;
        this.salonServiceImgUrl = salonServImgUrl;
    }

    //required value for fetching the data from Firestore
    public String getSalonServiceId() {
        return salonServiceId;
    }

    public void setSalonServiceId(String salonServId) {
        this.salonServiceId = salonServId;
    }

    public String getSalonServiceName() {
        return salonServiceName;
    }

    public void setSalonServiceName(String salonServName) {
        this.salonServiceName = salonServName;
    }

    public String getSalonServiceDesc() {
        return salonServiceDesc;
    }

    public void setSalonServiceDesc(String salonServDesc) {
        this.salonServiceDesc = salonServDesc;
    }

    public String getSalonServiceRate() {
        return salonServiceRate;
    }

    public void setSalonServiceRate(String salonServRate) {
        this.salonServiceRate = salonServRate;
    }

    public String getSalonServiceImgUrl() {
        return salonServiceImgUrl;
    }

    public void setSalonServiceImgUrl(String salonServImgUrl) {
        this.salonServiceImgUrl = salonServImgUrl;
    }




}
