package com.example.pakigsabot.Resorts.Models;

//Model
public class CancellationModel {
    String resortCPolID;
    String resortCPolDesc;

    public CancellationModel() {
        //empty constructor needed
    }

    public CancellationModel(String resortCPolID, String resortCPolDesc) {
        this.resortCPolID = resortCPolID;
        this.resortCPolDesc = resortCPolDesc;
    }

    public String getResortCPolID() {
        return resortCPolID;
    }

    public void setResortCPolID(String resortCPolID) {
        this.resortCPolID = resortCPolID;
    }

    public String getResortCPolDesc() {
        return resortCPolDesc;
    }

    public void setResortCPolDesc(String resortCPolDesc) {
        this.resortCPolDesc = resortCPolDesc;
    }
}
