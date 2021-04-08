/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.sccengineer.serviceticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class STType_item {

    public STType_item() {
    }

    public STType_item(String serviceTypeName, String startDateText, String endDateText, String position) {
        ServiceTypeName = serviceTypeName;
        StartDateText = startDateText;
        EndDateText = endDateText;
        Position = position;
    }

    @Expose
    @SerializedName("ServiceTypeName")
    private String ServiceTypeName;

    public String getServiceTypeName() {
        return ServiceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        ServiceTypeName = serviceTypeName;
    }

    public String getStartDateText() {
        return StartDateText;
    }

    public void setStartDateText(String startDateText) {
        StartDateText = startDateText;
    }

    public String getEndDateText() {
        return EndDateText;
    }

    public void setEndDateText(String endDateText) {
        EndDateText = endDateText;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    @Expose
    @SerializedName("StartDateText")
    private String StartDateText;
    @Expose
    @SerializedName("EndDateText")
    private String EndDateText;
    @Expose
    @SerializedName("Position")
    private String Position;







}



