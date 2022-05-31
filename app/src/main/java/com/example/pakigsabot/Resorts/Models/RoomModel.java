package com.example.pakigsabot.Resorts.Models;

//Model
public class RoomModel {
    String resortRFID;
    String resortRFName;
    String resortCapac;
    String resortDesc;
    String resortRate;
    String resortImgUrl;
    String establishmentID;

    public RoomModel() {
        //empty constructor needed
    }

    public RoomModel(String resortRFID, String resortRFName, String resortCapac,
                     String resortDesc, String resortRate, String resortImgUrl, String establishmentID) {
        this.resortRFID = resortRFID;
        this.resortRFName = resortRFName;
        this.resortCapac = resortCapac;
        this.resortDesc = resortDesc;
        this.resortRate = resortRate;
        this.resortImgUrl = resortImgUrl;
        this.establishmentID = establishmentID;
    }

    public String getResortRFID() {
        return resortRFID;
    }

    public void setResortRFID(String resortRFID) {
        this.resortRFID = resortRFID;
    }

    public String getResortRFName() {
        return resortRFName;
    }

    public void setResortRFName(String resortRFName) {
        this.resortRFName = resortRFName;
    }

    public String getResortCapac() {
        return resortCapac;
    }

    public void setResortCapac(String resortCapac) {
        this.resortCapac = resortCapac;
    }

    public String getResortDesc() {
        return resortDesc;
    }

    public void setResortDesc(String resortDesc) {
        this.resortDesc = resortDesc;
    }

    public String getResortRate() {
        return resortRate;
    }

    public void setResortRate(String resortRate) {
        this.resortRate = resortRate;
    }

    public String getResortImgUrl() {
        return resortImgUrl;
    }

    public void setResortImgUrl(String resortImgUrl) {
        this.resortImgUrl = resortImgUrl;
    }

    public String getEstablishmentID() {
        return establishmentID;
    }

    public void setEstablishmentID(String establishmentID) {
        this.establishmentID = establishmentID;
    }
}
