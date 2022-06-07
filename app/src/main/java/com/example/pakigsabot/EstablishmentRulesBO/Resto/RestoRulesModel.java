package com.example.pakigsabot.EstablishmentRulesBO.Resto;

public class RestoRulesModel {
    String restoRuleId;
    String restoRuleDesc;
    String estId;


    public RestoRulesModel() {
    }

    public String getRestoRuleId() {
        return restoRuleId;
    }


    public RestoRulesModel(String restoRuleId, String restoRuleDesc, String estId) {
        this.restoRuleId = restoRuleId;
        this.restoRuleDesc = restoRuleDesc;
        this.estId = estId;
    }

    public void setRestoRuleId(String restoRuleId) {
        this.restoRuleId = restoRuleId;
    }

    public String getRestoRuleDesc() {
        return restoRuleDesc;
    }

    public void setRestoRuleDesc(String restoRuleDesc) {
        this.restoRuleDesc = restoRuleDesc;
    }

    public String getEstId() {
        return estId;
    }

    public void setEstId(String estId) {
        this.estId = estId;
    }


}
