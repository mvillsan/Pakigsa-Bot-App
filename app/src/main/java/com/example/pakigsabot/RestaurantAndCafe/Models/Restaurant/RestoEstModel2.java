package com.example.pakigsabot.RestaurantAndCafe.Models.Restaurant;

public class RestoEstModel2 {
    String est_id;
    String est_Name;
    String est_address;
    String est_image;
    String est_phoneNum;
    String estLatitude;
    String estLongitude;
    String overallRating;
    String ratingCounter;
    int completedReservations;

    public RestoEstModel2() {
        //Empty Constructor Needed
    }

    public RestoEstModel2(String est_id, String est_Name, String est_address, String est_image, String est_phoneNum,
                          String estLatitude, String estLongitude, String overallRating, String ratingCounter,
                          int completedReservations) {
        this.est_id = est_id;
        this.est_Name = est_Name;
        this.est_address = est_address;
        this.est_image = est_image;
        this.est_phoneNum = est_phoneNum;
        this.estLatitude = estLatitude;
        this.estLongitude = estLongitude;
        this.overallRating = overallRating;
        this.ratingCounter = ratingCounter;
        this.completedReservations = completedReservations;
    }

    public String getEst_id() {
        return est_id;
    }

    public void setEst_id(String est_id) {
        this.est_id = est_id;
    }

    public String getEst_Name() {
        return est_Name;
    }

    public void setEst_Name(String est_Name) {
        this.est_Name = est_Name;
    }

    public String getEst_address() {
        return est_address;
    }

    public void setEst_address(String est_address) {
        this.est_address = est_address;
    }

    public String getEst_image() {
        return est_image;
    }

    public void setEst_image(String est_image) {
        this.est_image = est_image;
    }

    public String getEst_phoneNum() {
        return est_phoneNum;
    }

    public void setEst_phoneNum(String est_phoneNum) {
        this.est_phoneNum = est_phoneNum;
    }

    public String getEstLatitude() {
        return estLatitude;
    }

    public void setEstLatitude(String estLatitude) {
        this.estLatitude = estLatitude;
    }

    public String getEstLongitude() {
        return estLongitude;
    }

    public void setEstLongitude(String estLongitude) {
        this.estLongitude = estLongitude;
    }

    public String getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(String overallRating) {
        this.overallRating = overallRating;
    }

    public String getRatingCounter() {
        return ratingCounter;
    }

    public void setRatingCounter(String ratingCounter) {
        this.ratingCounter = ratingCounter;
    }

    public int getCompletedReservations() {
        return completedReservations;
    }

    public void setCompletedReservations(int completedReservations) {
        this.completedReservations = completedReservations;
    }
}
