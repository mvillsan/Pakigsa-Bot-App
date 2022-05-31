package com.example.pakigsabot.RestaurantAndCafe.Models.Restaurant;

//Model
public class RestaurantPromoAndDealsModel {
    String restoPDID;
    String restoPDName;
    String restoPDDesc;
    String restoPDStartDate;
    String restoPDEndDate;

    public RestaurantPromoAndDealsModel() {
        //empty onstructor needed
    }

    public RestaurantPromoAndDealsModel(String restoPDID, String restoPDName, String restoPDDesc,
                                        String restoPDStartDate, String restoPDEndDate) {
        this.restoPDID = restoPDID;
        this.restoPDName = restoPDName;
        this.restoPDDesc = restoPDDesc;
        this.restoPDStartDate = restoPDStartDate;
        this.restoPDEndDate = restoPDEndDate;
    }

    public String getRestoPDID() {
        return restoPDID;
    }

    public void setRestoPDID(String restoPDID) {
        this.restoPDID = restoPDID;
    }

    public String getRestoPDName() {
        return restoPDName;
    }

    public void setRestoPDName(String restoPDName) {
        this.restoPDName = restoPDName;
    }

    public String getRestoPDDesc() {
        return restoPDDesc;
    }

    public void setRestoPDDesc(String restoPDDesc) {
        this.restoPDDesc = restoPDDesc;
    }

    public String getRestoPDStartDate() {
        return restoPDStartDate;
    }

    public void setRestoPDStartDate(String restoPDStartDate) {
        this.restoPDStartDate = restoPDStartDate;
    }

    public String getRestoPDEndDate() {
        return restoPDEndDate;
    }

    public void setRestoPDEndDate(String restoPDEndDate) {
        this.restoPDEndDate = restoPDEndDate;
    }
}
