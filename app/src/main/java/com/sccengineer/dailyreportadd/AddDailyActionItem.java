/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.sccengineer.dailyreportadd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDailyActionItem {


    public AddDailyActionItem(String actionTaken, String reportCd, String text) {
        ActionTaken = actionTaken;
//        ReportCd = reportCd;
//        Text = text;
    }

    public String getActionTaken() {
        return ActionTaken;
    }

    public void setActionTaken(String actionTaken) {
        ActionTaken = actionTaken;
    }

//    public String getReportCd() {
//        return ReportCd;
//    }
//
//    public void setReportCd(String reportCd) {
//        ReportCd = reportCd;
//    }

//    public String getText() {
//        return Text;
//    }
//
//    public void setText(String text) {
//        Text = text;
//    }

    @Expose
    @SerializedName("ActionTaken")
    private String ActionTaken;
//    @Expose
//    @SerializedName("ReportCd")
//    private String ReportCd;
//    @SerializedName("Text")
//    private String Text;

    public AddDailyActionItem() {
    }

}



