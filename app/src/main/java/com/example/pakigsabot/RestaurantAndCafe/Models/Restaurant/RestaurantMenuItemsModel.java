package com.example.pakigsabot.RestaurantAndCafe.Models.Restaurant;

//Model
public class RestaurantMenuItemsModel {
    String restoFIID;
    String restoFIName;
    String restoFICategory;
    String restoFIPrice;
    String restoFIImgUrl;
    String restoFIAvail;
    String estId;
    String quantity;

    public RestaurantMenuItemsModel() {
        //Empty constructor needed
    }

    public RestaurantMenuItemsModel(String restoFIID, String restoFIName, String restoFICategory, String restoFIPrice,
                                    String restoFIImgUrl, String restoFIAvail, String estId, String quantity) {
        this.restoFIID = restoFIID;
        this.restoFIName = restoFIName;
        this.restoFICategory = restoFICategory;
        this.restoFIPrice = restoFIPrice;
        this.restoFIImgUrl = restoFIImgUrl;
        this.restoFIAvail = restoFIAvail;
        this.estId = estId;
        this.quantity = quantity;
    }

    public String getRestoFIID() {
        return restoFIID;
    }

    public void setRestoFIID(String restoFIID) {
        this.restoFIID = restoFIID;
    }

    public String getRestoFIName() {
        return restoFIName;
    }

    public void setRestoFIName(String restoFIName) {
        this.restoFIName = restoFIName;
    }

    public String getRestoFICategory() {
        return restoFICategory;
    }

    public void setRestoFICategory(String restoFICategory) {
        this.restoFICategory = restoFICategory;
    }

    public String getRestoFIPrice() {
        return restoFIPrice;
    }

    public void setRestoFIPrice(String restoFIPrice) {
        this.restoFIPrice = restoFIPrice;
    }

    public String getRestoFIImgUrl() {
        return restoFIImgUrl;
    }

    public void setRestoFIImgUrl(String restoFIImgUrl) {
        this.restoFIImgUrl = restoFIImgUrl;
    }

    public String getRestoFIAvail() {
        return restoFIAvail;
    }

    public void setRestoFIAvail(String restoFIAvail) {
        this.restoFIAvail = restoFIAvail;
    }

    public String getEstId() {
        return estId;
    }

    public void setEstId(String estId) {
        this.estId = estId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
