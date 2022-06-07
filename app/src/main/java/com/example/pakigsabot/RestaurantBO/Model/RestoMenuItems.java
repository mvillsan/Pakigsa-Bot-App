package com.example.pakigsabot.RestaurantBO.Model;

public class RestoMenuItems {

    String restoFIId;
    String restoFIName;
    String restoFICategory;
    String restoFIAvailability;
    String restoFIPrice;
    String restoFIImgUrl;


    public RestoMenuItems() {
        //empty constructor needed
    }

    public RestoMenuItems(String restoFIId, String restoFIName, String restoFICategory, String restoFIDesc, String restoFIAvailability, String restoFIPrice, String restoFIImgUrl) {
        this.restoFIId = restoFIId;
        this.restoFIName = restoFIName;
        this.restoFICategory = restoFICategory;
        this.restoFIAvailability = restoFIAvailability;
        this.restoFIPrice = restoFIPrice;
        this.restoFIImgUrl = restoFIImgUrl;
    }

    //required value for fetching the data from Firestore
    public String getRestoFIId() { return restoFIId;
    }

    public void setRestoFIId(String restoFIId) { this.restoFIId = restoFIId;
    }

    public String getRestoFIName() { return restoFIName;
    }

    public void setRestoFIName(String restoFIName) { this.restoFIName = restoFIName;
    }

    public String getRestoFICategory() { return restoFICategory;
    }

    public void setRestoFICategory(String restoFICategory) { this.restoFICategory = restoFICategory;
    }

    public String getRestoFIAvailability() { return restoFIAvailability;
    }

    public void setRestoFIAvailability(String restoFIAvailability) { this.restoFIAvailability = restoFIAvailability;
    }

    public String getRestoFIPrice() { return restoFIPrice;
    }

    public void setRestoFIPrice(String restoFIPrice) { this.restoFIPrice = restoFIPrice;
    }

    public String getRestoFIImgUrl() { return restoFIImgUrl;
    }

    public void setRestoFIImgUrl(String restoFIImgUrl) { this.restoFIImgUrl = restoFIImgUrl;
    }


}
