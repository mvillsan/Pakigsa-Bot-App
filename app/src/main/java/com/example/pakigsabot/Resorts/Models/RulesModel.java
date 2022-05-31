package com.example.pakigsabot.Resorts.Models;

//Model
public class RulesModel {
    String ruleID;
    String desc;

    public RulesModel() {
        //empty constructor
    }

    public RulesModel(String ruleID, String desc) {
        this.ruleID = ruleID;
        this.desc = desc;
    }

    public String getRuleID() {
        return ruleID;
    }

    public void setRuleID(String ruleID) {
        this.ruleID = ruleID;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
