package com.example.pakigsabot.EstablishmentRulesBO.DentalClinic;

public class DentalRulesModel {
    String dentalRuleId;
    String dentalRuleDesc;
    String estId;

    public DentalRulesModel() {
    }

    public DentalRulesModel(String dentalRuleId, String dentalRuleDesc, String estId) {
        this.dentalRuleId = dentalRuleId;
        this.dentalRuleDesc = dentalRuleDesc;
        this.estId = estId;
    }

    public String getDentalRuleId() {
        return dentalRuleId;
    }

    public void setDentalRuleId(String dentalRuleId) {
        this.dentalRuleId = dentalRuleId;
    }

    public String getDentalRuleDesc() {
        return dentalRuleDesc;
    }

    public void setDentalRuleDesc(String dentalRuleDesc) {
        this.dentalRuleDesc = dentalRuleDesc;
    }

    public String getEstId() {
        return estId;
    }

    public void setEstId(String estId) {
        this.estId = estId;
    }


}
