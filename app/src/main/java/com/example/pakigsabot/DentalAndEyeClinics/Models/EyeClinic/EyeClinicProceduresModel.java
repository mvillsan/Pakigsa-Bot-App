package com.example.pakigsabot.DentalAndEyeClinics.Models.EyeClinic;

public class EyeClinicProceduresModel {

    String opticalPRId;
    String opticalPRName;
    String opticalPRDesc;
    String opticalPRRate;
    String opticalPRImgUrl;
    String estId;


    public EyeClinicProceduresModel() {
        //empty constructor needed
    }



    public EyeClinicProceduresModel(String opticalPRId, String opticalPRName, String opticalPRDesc, String opticalPRRate, String opticalPRImgUrl, String estId) {
        this.opticalPRId = opticalPRId;
        this.opticalPRName = opticalPRName;
        this.opticalPRDesc = opticalPRDesc;
        this.opticalPRRate = opticalPRRate;
        this.opticalPRImgUrl = opticalPRImgUrl;
        this.estId = estId;
    }
    public String getOpticalPRId() {
        return opticalPRId;
    }

    public void setOpticalPRId(String opticalPRId) {
        this.opticalPRId = opticalPRId;
    }

    public String getOpticalPRName() {
        return opticalPRName;
    }

    public void setOpticalPRName(String opticalPRName) {
        this.opticalPRName = opticalPRName;
    }

    public String getOpticalPRDesc() {
        return opticalPRDesc;
    }

    public void setOpticalPRDesc(String opticalPRDesc) {
        this.opticalPRDesc = opticalPRDesc;
    }

    public String getOpticalPRRate() {
        return opticalPRRate;
    }

    public void setOpticalPRRate(String opticalPRRate) {
        this.opticalPRRate = opticalPRRate;
    }

    public String getOpticalPRImgUrl() {
        return opticalPRImgUrl;
    }

    public void setOpticalPRImgUrl(String opticalPRImgUrl) {
        this.opticalPRImgUrl = opticalPRImgUrl;
    }

    public String getEstId() {
        return estId;
    }

    public void setEstId(String estId) {
        this.estId = estId;
    }

}
