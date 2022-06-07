package com.example.pakigsabot.InternetCafeBO.Model;

public class InternetCafeServices {
    String cafeServId, cafeServName, cafeServDesc, cafeServRate, cafeServImgUrl;

    //required empty constructor
    public InternetCafeServices() {
    }

    public InternetCafeServices(String cafeServId, String cafeServName, String cafeServDesc, String cafeServRate, String cafeServImgUrl) {
        this.cafeServId = cafeServId;
        this.cafeServName = cafeServName;
        this.cafeServDesc = cafeServDesc;
        this.cafeServRate = cafeServRate;
        this.cafeServImgUrl = cafeServImgUrl;
    }

    public String getCafeServId() {
        return cafeServId;
    }

    public void setCafeServId(String cafeServId) {
        this.cafeServId = cafeServId;
    }

    public String getCafeServName() {
        return cafeServName;
    }

    public void setCafeServName(String cafeServName) {
        this.cafeServName = cafeServName;
    }

    public String getCafeServDesc() {
        return cafeServDesc;
    }

    public void setCafeServDesc(String cafeServDesc) {
        this.cafeServDesc = cafeServDesc;
    }

    public String getCafeServRate() {
        return cafeServRate;
    }

    public void setCafeServRate(String cafeServRate) {
        this.cafeServRate = cafeServRate;
    }

    public String getCafeServImgUrl() {
        return cafeServImgUrl;
    }

    public void setCafeServImgUrl(String cafeServImgUrl) {
        this.cafeServImgUrl = cafeServImgUrl;
    }
}

