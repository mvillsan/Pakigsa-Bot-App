package com.example.pakigsabot.CoworkingSpaceBO.Rules;

public class CoworkingSpaceRulesModel {
    String coSpaceRuleID;
    String coSpaceRuleDesc;

    public CoworkingSpaceRulesModel() {
    }

    public CoworkingSpaceRulesModel(String coSpaceRuleID, String coSpaceRuleDesc) {
        this.coSpaceRuleID = coSpaceRuleID;
        this.coSpaceRuleDesc = coSpaceRuleDesc;
    }

    public String getCoSpaceRuleID() {
        return coSpaceRuleID;
    }

    public void setCoSpaceRuleID(String coSpaceRuleID) {
        this.coSpaceRuleID = coSpaceRuleID;
    }

    public String getCoSpaceRuleDesc() {
        return coSpaceRuleDesc;
    }

    public void setCoSpaceRuleDesc(String coSpaceRuleDesc) {
        this.coSpaceRuleDesc = coSpaceRuleDesc;
    }
}
