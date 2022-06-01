package com.example.pakigsabot.NavigationFragments;

public class DistanceModel {
    private String name;
    private String distance;

    public DistanceModel(String name, String distance) {
        this.name = name;
        this.distance = distance;
    }
    public DistanceModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
