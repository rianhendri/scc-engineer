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

public class AddDailyFollowItem {


    public AddDailyFollowItem(String followUp, String reportCd, String text) {
        FollowUp = followUp;
//        ReportCd = reportCd;
//        Text = text;
    }

    public String getFollowUp() {
        return FollowUp;
    }

    public void setFollowUp(String followUp) {
        FollowUp = followUp;
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
    @SerializedName("FollowUp")
    private String FollowUp;
//    @Expose
//    @SerializedName("ReportCd")
//    private String ReportCd;
//    @SerializedName("Text")
//    private String Text;

    public AddDailyFollowItem() {
    }

}



