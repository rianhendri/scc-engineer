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


    public OnProgHome_items(String serviceTicketCd, String customerName, String supportStartDateTime) {
        ServiceTicketCd = serviceTicketCd;
        CustomerName = customerName;
        SupportStartDateTime = supportStartDateTime;
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

    @SerializedName("ServiceTicketCd")
    private String ServiceTicketCd;
    @SerializedName("CustomerName")
    private String CustomerName;
    @SerializedName("SupportStartDateTime")
    private String SupportStartDateTime;


}



