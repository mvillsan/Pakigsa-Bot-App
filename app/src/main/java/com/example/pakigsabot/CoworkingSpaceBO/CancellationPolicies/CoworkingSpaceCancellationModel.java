package com.example.pakigsabot.CoworkingSpaceBO.CancellationPolicies;

public class CoworkingSpaceCancellationModel {
    String coSpaceCancelID;
    String coSpaceCancelDesc;

    public CoworkingSpaceCancellationModel() {
    }

    public CoworkingSpaceCancellationModel(String coSpaceCancelID, String coSpaceCancelDesc) {
        this.coSpaceCancelID = coSpaceCancelID;
        this.coSpaceCancelDesc = coSpaceCancelDesc;
    }
    public String getCoSpaceCancelID() {
        return coSpaceCancelID;
    }

    public void setCoSpaceCancelID(String coSpaceCancelID) {
        this.coSpaceCancelID = coSpaceCancelID;
    }

    public String getCoSpaceCancelDesc() {
        return coSpaceCancelDesc;
    }

    public void setCoSpaceCancelDesc(String coSpaceCancelDesc) {
        this.coSpaceCancelDesc = coSpaceCancelDesc;
    }




}
