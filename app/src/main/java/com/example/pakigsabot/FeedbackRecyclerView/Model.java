package com.example.pakigsabot.FeedbackRecyclerView;

public class Model {
    String name = "";
    String address = "";
    int estImages;

    public Model(String name, String address, int estImages) {
        this.name = name;
        this.address = address;
        this.estImages = estImages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getEstImages() {
        return estImages;
    }

    public void setEstImages(int estImages) {
        this.estImages = estImages;
    }
}
