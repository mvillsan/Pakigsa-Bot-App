package com.example.pakigsabot.RestaurantAndCafe.Models.Cafe;

//Model
public class CafePromoAndDealsModel {
    String cafePDID;
    String cafePDName;
    String cafePDDesc;
    String cafePDStartDate;
    String cafePDEndDate;

    public CafePromoAndDealsModel() {
        //empty constructor needed
    }

    public CafePromoAndDealsModel(String cafePDID, String cafePDName, String cafePDDesc, String cafePDStartDate, String cafePDEndDate) {
        this.cafePDID = cafePDID;
        this.cafePDName = cafePDName;
        this.cafePDDesc = cafePDDesc;
        this.cafePDStartDate = cafePDStartDate;
        this.cafePDEndDate = cafePDEndDate;
    }

    public String getCafePDID() {
        return cafePDID;
    }

    public void setCafePDID(String cafePDID) {
        this.cafePDID = cafePDID;
    }

    public String getCafePDName() {
        return cafePDName;
    }

    public void setCafePDName(String cafePDName) {
        this.cafePDName = cafePDName;
    }

    public String getCafePDDesc() {
        return cafePDDesc;
    }

    public void setCafePDDesc(String cafePDDesc) {
        this.cafePDDesc = cafePDDesc;
    }

    public String getCafePDStartDate() {
        return cafePDStartDate;
    }

    public void setCafePDStartDate(String cafePDStartDate) {
        this.cafePDStartDate = cafePDStartDate;
    }

    public String getCafePDEndDate() {
        return cafePDEndDate;
    }

    public void setCafePDEndDate(String cafePDEndDate) {
        this.cafePDEndDate = cafePDEndDate;
    }
}
