package com.example.pakigsabot.RestaurantAndCafe.Models.Cafe;

//Model
public class CafeMenuItemsModel {
    String cafeFIID;
    String cafeFIName;
    String cafeFICategory;
    String cafeFIPrice;
    String cafeFIImgUrl;
    String cafeFIAvail;
    String estId;
    String quantity;

    public CafeMenuItemsModel() {
        //empty constructor needed
    }

    public CafeMenuItemsModel(String cafeFIID, String cafeFIName, String cafeFICategory, String cafeFIPrice, String cafeFIImgUrl,
                              String cafeFIAvail, String estId, String quantity) {
        this.cafeFIID = cafeFIID;
        this.cafeFIName = cafeFIName;
        this.cafeFICategory = cafeFICategory;
        this.cafeFIPrice = cafeFIPrice;
        this.cafeFIImgUrl = cafeFIImgUrl;
        this.cafeFIAvail = cafeFIAvail;
        this.estId = estId;
        this.quantity = quantity;
    }

    public String getCafeFIID() {
        return cafeFIID;
    }

    public void setCafeFIID(String cafeFIID) {
        this.cafeFIID = cafeFIID;
    }

    public String getCafeFIName() {
        return cafeFIName;
    }

    public void setCafeFIName(String cafeFIName) {
        this.cafeFIName = cafeFIName;
    }

    public String getCafeFICategory() {
        return cafeFICategory;
    }

    public void setCafeFICategory(String cafeFICategory) {
        this.cafeFICategory = cafeFICategory;
    }

    public String getCafeFIPrice() {
        return cafeFIPrice;
    }

    public void setCafeFIPrice(String cafeFIPrice) {
        this.cafeFIPrice = cafeFIPrice;
    }

    public String getCafeFIImgUrl() {
        return cafeFIImgUrl;
    }

    public void setCafeFIImgUrl(String cafeFIImgUrl) {
        this.cafeFIImgUrl = cafeFIImgUrl;
    }

    public String getCafeFIAvail() {
        return cafeFIAvail;
    }

    public void setCafeFIAvail(String cafeFIAvail) {
        this.cafeFIAvail = cafeFIAvail;
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
