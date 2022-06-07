package com.example.pakigsabot.SalonBO.Rules;

public class SalonRulesModel {
    String salonRuleID;
    String salonRuleDesc;

    public SalonRulesModel() {
    }

    public SalonRulesModel(String salonRuleID, String salonRuleDesc) {
        this.salonRuleID = salonRuleID;
        this.salonRuleDesc = salonRuleDesc;
    }

    public String getSalonRuleID() {
        return salonRuleID;
    }

    public void setSalonRuleID(String salonRuleID) {
        this.salonRuleID = salonRuleID;
    }

    public String getSalonRuleDesc() {
        return salonRuleDesc;
    }

    public void setSalonRuleDesc(String salonRuleDesc) {
        this.salonRuleDesc = salonRuleDesc;
    }



}
