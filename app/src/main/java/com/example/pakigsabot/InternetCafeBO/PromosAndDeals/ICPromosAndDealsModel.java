package com.example.pakigsabot.InternetCafeBO.PromosAndDeals;

public class ICPromosAndDealsModel {
    String internetCafePADId;
    String internetCafePADName;
    String internetCafePADDesc;
    String internetCafePADStartDate;
    String internetCafePADEndDate;

    public ICPromosAndDealsModel() {
    }

    public ICPromosAndDealsModel(String internetCafePADId, String internetCafePADName, String internetCafePADDesc, String internetCafePADStartDate, String internetCafePADEndDate) {
        this.internetCafePADId = internetCafePADId;
        this.internetCafePADName = internetCafePADName;
        this.internetCafePADDesc = internetCafePADDesc;
        this.internetCafePADStartDate = internetCafePADStartDate;
        this.internetCafePADEndDate = internetCafePADEndDate;
    }

    public String getInternetCafePADId() {
        return internetCafePADId;
    }

    public void setInternetCafePADId(String internetCafePADId) {
        this.internetCafePADId = internetCafePADId;
    }

    public String getInternetCafePADName() {
        return internetCafePADName;
    }

    public void setInternetCafePADName(String internetCafePADName) {
        this.internetCafePADName = internetCafePADName;
    }

    public String getInternetCafePADDesc() {
        return internetCafePADDesc;
    }

    public void setInternetCafePADDesc(String internetCafePADDesc) {
        this.internetCafePADDesc = internetCafePADDesc;
    }

    public String getInternetCafePADStartDate() {
        return internetCafePADStartDate;
    }

    public void setInternetCafePADStartDate(String internetCafePADStartDate) {
        this.internetCafePADStartDate = internetCafePADStartDate;
    }

    public String getInternetCafePADEndDate() {
        return internetCafePADEndDate;
    }

    public void setInternetCafePADEndDate(String internetCafePADEndDate) {
        this.internetCafePADEndDate = internetCafePADEndDate;
    }

}