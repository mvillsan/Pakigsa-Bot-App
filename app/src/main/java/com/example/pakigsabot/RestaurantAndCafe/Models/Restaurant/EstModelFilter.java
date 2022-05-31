package com.example.pakigsabot.RestaurantAndCafe.Models.Restaurant;

import com.example.pakigsabot.RestaurantAndCafe.Models.RestoEstModel;

public class EstModelFilter extends RestoEstModel {
    String distance;

    public EstModelFilter() {

    }

    public EstModelFilter(String est_id, String est_Name, String est_address, String est_image, String est_phoneNum, String estLatitude, String estLongitude, String overallRating, String ratingCounter, String distance) {
        super(est_id, est_Name, est_address, est_image, est_phoneNum, estLatitude, estLongitude, overallRating, ratingCounter);
        this.distance = distance;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
