package com.example.pakigsabot.Reservations.Model;

//Model
public class ReservationsModel {
    String autoReserveID;
    String rStatus_default;
    String totalPax;
    String rName;
    String checkINDate;
    String checkOUTDate;
    String checkINTime;
    String cust_ID;
    String estID;
    String estName;
    String estEmailAddress;
    String custFName;
    String custLName;
    String custPhoneNum;
    String custEmailAdd;
    String adultPax;
    String childPax;
    String infantPax;
    String petPax;
    String fee;
    String dateOfTransaction;
    String notes;
    String preOrders;
    String totalAmt;

    public ReservationsModel() {
        //empty constructor needed
    }

    public ReservationsModel(String autoReserveID, String rStatus_default, String totalPax, String rName, String checkINDate,
                             String checkOUTDate, String checkINTime, String cust_ID, String estID, String estName,
                             String estEmailAddress, String custFName, String custLName, String custPhoneNum, String custEmailAdd,
                             String adultPax, String childPax, String infantPax, String petPax, String fee, String dateOfTransaction,
                             String notes, String preOrders, String totalAmt) {
        this.autoReserveID = autoReserveID;
        this.rStatus_default = rStatus_default;
        this.totalPax = totalPax;
        this.rName = rName;
        this.checkINDate = checkINDate;
        this.checkOUTDate = checkOUTDate;
        this.checkINTime = checkINTime;
        this.cust_ID = cust_ID;
        this.estID = estID;
        this.estName = estName;
        this.estEmailAddress = estEmailAddress;
        this.custFName = custFName;
        this.custLName = custLName;
        this.custPhoneNum = custPhoneNum;
        this.custEmailAdd = custEmailAdd;
        this.adultPax = adultPax;
        this.childPax = childPax;
        this.infantPax = infantPax;
        this.petPax = petPax;
        this.fee = fee;
        this.dateOfTransaction = dateOfTransaction;
        this.notes = notes;
        this.preOrders = preOrders;
        this.totalAmt = totalAmt;
    }

    public String getAutoReserveID() {
        return autoReserveID;
    }

    public void setAutoReserveID(String autoReserveID) {
        this.autoReserveID = autoReserveID;
    }

    public String getrStatus_default() {
        return rStatus_default;
    }

    public void setrStatus_default(String rStatus_default) {
        this.rStatus_default = rStatus_default;
    }

    public String getTotalPax() {
        return totalPax;
    }

    public void setTotalPax(String totalPax) {
        this.totalPax = totalPax;
    }

    public String getRName() {
        return rName;
    }

    public void setRName(String rName) {
        this.rName = rName;
    }

    public String getCheckINDate() {
        return checkINDate;
    }

    public void setCheckINDate(String checkINDate) {
        this.checkINDate = checkINDate;
    }

    public String getCheckOUTDate() {
        return checkOUTDate;
    }

    public void setCheckOUTDate(String checkOUTDate) {
        this.checkOUTDate = checkOUTDate;
    }

    public String getCheckINTime() {
        return checkINTime;
    }

    public void setCheckINTime(String checkINTime) {
        this.checkINTime = checkINTime;
    }

    public String getCust_ID() {
        return cust_ID;
    }

    public void setCust_ID(String cust_ID) {
        this.cust_ID = cust_ID;
    }

    public String getEstID() {
        return estID;
    }

    public void setEstID(String estID) {
        this.estID = estID;
    }

    public String getEstName() {
        return estName;
    }

    public void setEstName(String estName) {
        this.estName = estName;
    }

    public String getEstEmailAddress() {
        return estEmailAddress;
    }

    public void setEstEmailAddress(String estEmailAddress) {
        this.estEmailAddress = estEmailAddress;
    }

    public String getCustFName() {
        return custFName;
    }

    public void setCustFName(String custFName) {
        this.custFName = custFName;
    }

    public String getCustLName() {
        return custLName;
    }

    public void setCustLName(String custLName) {
        this.custLName = custLName;
    }

    public String getAdultPax() {
        return adultPax;
    }

    public void setAdultPax(String adultPax) {
        this.adultPax = adultPax;
    }

    public String getChildPax() {
        return childPax;
    }

    public void setChildPax(String childPax) {
        this.childPax = childPax;
    }

    public String getInfantPax() {
        return infantPax;
    }

    public void setInfantPax(String infantPax) {
        this.infantPax = infantPax;
    }

    public String getPetPax() {
        return petPax;
    }

    public void setPetPax(String petPax) {
        this.petPax = petPax;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(String dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPreOrders() {
        return preOrders;
    }

    public void setPreOrders(String preOrders) {
        this.preOrders = preOrders;
    }

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getCustPhoneNum() {
        return custPhoneNum;
    }

    public void setCustPhoneNum(String custPhoneNum) {
        this.custPhoneNum = custPhoneNum;
    }

    public String getCustEmailAdd() {
        return custEmailAdd;
    }

    public void setCustEmailAdd(String custEmailAdd) {
        this.custEmailAdd = custEmailAdd;
    }
}


