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

public class AddDailyTemuanItem {

    public AddDailyTemuanItem(String finding, String reportCd, String text) {
        Finding = finding;
//        ReportCd = reportCd;
//        Text = text;
    }

    public String getFinding() {
        return Finding;
    }

    public void setFinding(String finding) {
        Finding = finding;
    }

//    public String getReportCd() {
//        return ReportCd;
//    }
//
//    public void setReportCd(String reportCd) {
//        ReportCd = reportCd;
//    }
//
//    public String getText() {
//        return Text;
//    }
//
//    public void setText(String text) {
//        Text = text;
//    }

    @Expose
    @SerializedName("Finding")
    private String Finding;
//    @Expose
//    @SerializedName("ReportCd")
//    private String ReportCd;
//    @SerializedName("Text")
//    private String Text;

    public AddDailyTemuanItem() {
    }

}



