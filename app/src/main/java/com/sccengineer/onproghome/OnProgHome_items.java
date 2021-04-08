/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.sccengineer.onproghome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnProgHome_items {



    public OnProgHome_items() {
    }

    public OnProgHome_items(String serviceTicketCd, String customerName, String waitingDueDate, boolean isAssist, String press, String issue, String statusName) {
        ServiceTicketCd = serviceTicketCd;
        CustomerName = customerName;
        WaitingDueDate = waitingDueDate;
        IsAssist = isAssist;
        Press = press;
        Issue = issue;
        StatusName = statusName;
    }

    public String getServiceTicketCd() {
        return ServiceTicketCd;
    }

    public void setServiceTicketCd(String serviceTicketCd) {
        ServiceTicketCd = serviceTicketCd;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getWaitingDueDate() {
        return WaitingDueDate;
    }

    public void setWaitingDueDate(String waitingDueDate) {
        WaitingDueDate = waitingDueDate;
    }

    public boolean isAssist() {
        return IsAssist;
    }

    public void setAssist(boolean assist) {
        IsAssist = assist;
    }

    public String getPress() {
        return Press;
    }

    public void setPress(String press) {
        Press = press;
    }

    public String getIssue() {
        return Issue;
    }

    public void setIssue(String issue) {
        Issue = issue;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    @SerializedName("ServiceTicketCd")
    private String ServiceTicketCd;
    @SerializedName("CustomerName")
    private String CustomerName;
    @SerializedName("WaitingDueDate")
    private String WaitingDueDate;
    @SerializedName("IsAssist")
    private boolean IsAssist;
    @SerializedName("Press")
    private String Press;
    @SerializedName("Issue")
    private String Issue;
    @SerializedName("StatusName")
    private String StatusName;
}



