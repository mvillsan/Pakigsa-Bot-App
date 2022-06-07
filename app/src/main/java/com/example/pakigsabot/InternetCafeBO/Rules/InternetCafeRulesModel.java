package com.example.pakigsabot.InternetCafeBO.Rules;

public class InternetCafeRulesModel {
    String iCafeRuleID;
    String iCafeRuleDesc;

    public InternetCafeRulesModel() {
    }

    public InternetCafeRulesModel(String iCafeRuleID, String iCafeRuleDesc) {
        this.iCafeRuleID = iCafeRuleID;
        this.iCafeRuleDesc = iCafeRuleDesc;
    }

    public String getiCafeRuleID() {
        return iCafeRuleID;
    }

    public void setiCafeRuleID(String iCafeRuleID) {
        this.iCafeRuleID = iCafeRuleID;
    }

    public String getiCafeRuleDesc() {
        return iCafeRuleDesc;
    }

    public void setiCafeRuleDesc(String iCafeRuleDesc) {
        this.iCafeRuleDesc = iCafeRuleDesc;
    }
}
