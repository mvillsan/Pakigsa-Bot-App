package com.example.pakigsabot.SpaBO.Rules;

public class SpaRulesModel {
    String spaRuleID;
    String spaRuleDesc;

    public SpaRulesModel() {
    }

    public String getSpaRuleID() {
        return spaRuleID;
    }

    public void setSpaRuleID(String spaRuleID) {
        this.spaRuleID = spaRuleID;
    }

    public String getSpaRuleDesc() {
        return spaRuleDesc;
    }

    public void setSpaRuleDesc(String spaRuleDesc) {
        this.spaRuleDesc = spaRuleDesc;
    }

    public SpaRulesModel(String spaRuleID, String spaRuleDesc) {
        this.spaRuleID = spaRuleID;
        this.spaRuleDesc = spaRuleDesc;
    }

}
