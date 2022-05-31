package com.example.pakigsabot.Resorts.Models;

//Model
public class FacilityModel {
    String resortFID;
    String resortFName;
    String resortFCapac;
    String resortFDesc;
    String resortFRate;
    String resortFImgUrl;

    public FacilityModel() {
        //empty constructor needed
    }

    public FacilityModel(String resortFID, String resortFName, String resortFCapac, String resortFDesc, String resortFRate, String resortFImgUrl) {
        this.resortFID = resortFID;
        this.resortFName = resortFName;
        this.resortFCapac = resortFCapac;
        this.resortFDesc = resortFDesc;
        this.resortFRate = resortFRate;
        this.resortFImgUrl = resortFImgUrl;
    }

    public String getResortFID() {
        return resortFID;
    }

    public void setResortFID(String resortFID) {
        this.resortFID = resortFID;
    }

    public String getResortFName() {
        return resortFName;
    }

    public void setResortFName(String resortFName) {
        this.resortFName = resortFName;
    }

    public String getResortFCapac() {
        return resortFCapac;
    }

    public void setResortFCapac(String resortFCapac) {
        this.resortFCapac = resortFCapac;
    }

    public String getResortFDesc() {
        return resortFDesc;
    }

    public void setResortFDesc(String resortFDesc) {
        this.resortFDesc = resortFDesc;
    }

    public String getResortFRate() {
        return resortFRate;
    }

    public void setResortFRate(String resortFRate) {
        this.resortFRate = resortFRate;
    }

    public String getResortFImgUrl() {
        return resortFImgUrl;
    }

    public void setResortFImgUrl(String resortFImgUrl) {
        this.resortFImgUrl = resortFImgUrl;
    }
}
