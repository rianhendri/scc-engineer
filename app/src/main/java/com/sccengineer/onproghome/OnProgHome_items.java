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


    public OnProgHome_items(String serviceTicketCd, String customerName, String supportStartDateTime, boolean isAssist, String press, String issue) {
        ServiceTicketCd = serviceTicketCd;
        CustomerName = customerName;
        SupportStartDateTime = supportStartDateTime;
        IsAssist = isAssist;
        Press = press;
        Issue = issue;
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

    public String getSupportStartDateTime() {
        return SupportStartDateTime;
    }

    public void setSupportStartDateTime(String supportStartDateTime) {
        SupportStartDateTime = supportStartDateTime;
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

    @SerializedName("ServiceTicketCd")
    private String ServiceTicketCd;
    @SerializedName("CustomerName")
    private String CustomerName;
    @SerializedName("SupportStartDateTime")
    private String SupportStartDateTime;
    @SerializedName("IsAssist")
    private boolean IsAssist;
    @SerializedName("Press")
    private String Press;
    @SerializedName("Issue")
    private String Issue;
}



