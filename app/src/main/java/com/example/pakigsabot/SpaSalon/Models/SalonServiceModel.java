package com.example.pakigsabot.SpaSalon.Models;

public class SalonServiceModel {
    String id;
    String name;
    String desc;
    String rate;
    String imgurl;
    String estId;

    public SalonServiceModel() {
        //empty constructor needed
    }

    public SalonServiceModel(String id, String name, String desc, String rate, String imgurl, String estId) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.rate = rate;
        this.imgurl = imgurl;
        this.estId = estId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getEstId() {
        return estId;
    }

    public void setEstId(String estId) {
        this.estId = estId;
    }
}
