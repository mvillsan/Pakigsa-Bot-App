package com.example.pakigsabot.SpaSalon.Models;

public class SalonPromoDealsModels {
    String salonPADId;
    String salonPADName;
    String salonPADDesc;
    String salonPADStartDate;
    String salonPADEndDate;

    public SalonPromoDealsModels() {
        //empty constructor needed
    }

    public SalonPromoDealsModels(String salonPADId, String salonPADName, String salonPADDesc, String salonPADStartDate, String salonPADEndDate) {
        this.salonPADId = salonPADId;
        this.salonPADName = salonPADName;
        this.salonPADDesc = salonPADDesc;
        this.salonPADStartDate = salonPADStartDate;
        this.salonPADEndDate = salonPADEndDate;
    }

    public String getSalonPADId() {
        return salonPADId;
    }

    public void setSalonPADId(String salonPADId) {
        this.salonPADId = salonPADId;
    }

    public String getSalonPADName() {
        return salonPADName;
    }

    public void setSalonPADName(String salonPADName) {
        this.salonPADName = salonPADName;
    }

    public String getSalonPADDesc() {
        return salonPADDesc;
    }

    public void setSalonPADDesc(String salonPADDesc) {
        this.salonPADDesc = salonPADDesc;
    }

    public String getSalonPADStartDate() {
        return salonPADStartDate;
    }

    public void setSalonPADStartDate(String salonPADStartDate) {
        this.salonPADStartDate = salonPADStartDate;
    }

    public String getSalonPADEndDate() {
        return salonPADEndDate;
    }

    public void setSalonPADEndDate(String salonPADEndDate) {
        this.salonPADEndDate = salonPADEndDate;
    }
}
