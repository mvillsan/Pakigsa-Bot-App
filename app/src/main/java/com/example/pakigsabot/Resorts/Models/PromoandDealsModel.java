package com.example.pakigsabot.Resorts.Models;

//Model
public class PromoandDealsModel {
    String promoDealsID;
    String promoDealsName;
    String promoDealsDesc;
    String promoDealsStartDate;
    String promoDealsEndDate;

    public PromoandDealsModel() {
        //empty constructor needed
    }

    public PromoandDealsModel(String promoDealsID, String promoDealsName, String promoDealsDesc, String promoDealsStartDate, String promoDealsEndDate) {
        this.promoDealsID = promoDealsID;
        this.promoDealsName = promoDealsName;
        this.promoDealsDesc = promoDealsDesc;
        this.promoDealsStartDate = promoDealsStartDate;
        this.promoDealsEndDate = promoDealsEndDate;
    }

    public String getPromoDealsID() {
        return promoDealsID;
    }

    public void setPromoDealsID(String promoDealsID) {
        this.promoDealsID = promoDealsID;
    }

    public String getPromoDealsName() {
        return promoDealsName;
    }

    public void setPromoDealsName(String promoDealsName) {
        this.promoDealsName = promoDealsName;
    }

    public String getPromoDealsDesc() {
        return promoDealsDesc;
    }

    public void setPromoDealsDesc(String promoDealsDesc) {
        this.promoDealsDesc = promoDealsDesc;
    }

    public String getPromoDealsStartDate() {
        return promoDealsStartDate;
    }

    public void setPromoDealsStartDate(String promoDealsStartDate) {
        this.promoDealsStartDate = promoDealsStartDate;
    }

    public String getPromoDealsEndDate() {
        return promoDealsEndDate;
    }

    public void setPromoDealsEndDate(String promoDealsEndDate) {
        this.promoDealsEndDate = promoDealsEndDate;
    }
}
