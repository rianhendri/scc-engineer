/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 */
package com.sccengineer.leaveform;

import com.google.gson.annotations.SerializedName;

public class LeaveItems {



    public LeaveItems() {
    }

    public LeaveItems(String guid, String engineerCd, String roleCd, String date, String clockIn, String clockInLongitude, String clockInLatitude, String clockOut, String clockOutLongitude, String clockOutLatitude, boolean dataExist, String totalHoursText) {
        Guid = guid;
        EngineerCd = engineerCd;
        RoleCd = roleCd;
        Date = date;
        ClockIn = clockIn;
        ClockInLongitude = clockInLongitude;
        ClockInLatitude = clockInLatitude;
        ClockOut = clockOut;
        ClockOutLongitude = clockOutLongitude;
        ClockOutLatitude = clockOutLatitude;
        DataExist = dataExist;
        TotalHoursText = totalHoursText;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String guid) {
        Guid = guid;
    }

    public String getEngineerCd() {
        return EngineerCd;
    }

    public void setEngineerCd(String engineerCd) {
        EngineerCd = engineerCd;
    }

    public String getRoleCd() {
        return RoleCd;
    }

    public void setRoleCd(String roleCd) {
        RoleCd = roleCd;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getClockIn() {
        return ClockIn;
    }

    public void setClockIn(String clockIn) {
        ClockIn = clockIn;
    }

    public String getClockInLongitude() {
        return ClockInLongitude;
    }

    public void setClockInLongitude(String clockInLongitude) {
        ClockInLongitude = clockInLongitude;
    }

    public String getClockInLatitude() {
        return ClockInLatitude;
    }

    public void setClockInLatitude(String clockInLatitude) {
        ClockInLatitude = clockInLatitude;
    }

    public String getClockOut() {
        return ClockOut;
    }

    public void setClockOut(String clockOut) {
        ClockOut = clockOut;
    }

    public String getClockOutLongitude() {
        return ClockOutLongitude;
    }

    public void setClockOutLongitude(String clockOutLongitude) {
        ClockOutLongitude = clockOutLongitude;
    }

    public String getClockOutLatitude() {
        return ClockOutLatitude;
    }

    public void setClockOutLatitude(String clockOutLatitude) {
        ClockOutLatitude = clockOutLatitude;
    }

    public boolean isDataExist() {
        return DataExist;
    }

    public void setDataExist(boolean dataExist) {
        DataExist = dataExist;
    }

    public String getTotalHoursText() {
        return TotalHoursText;
    }

    public void setTotalHoursText(String totalHoursText) {
        TotalHoursText = totalHoursText;
    }

    @SerializedName("Guid")
    private String Guid;
    @SerializedName("EngineerCd")
    private String EngineerCd;
    @SerializedName("RoleCd")
    private String RoleCd;
    @SerializedName("Date")
    private String Date;
    @SerializedName("ClockIn")
    private String ClockIn;
    @SerializedName("ClockInLongitude")
    private String ClockInLongitude;
    @SerializedName("ClockInLatitude")
    private String ClockInLatitude;
    @SerializedName("ClockOut")
    private String ClockOut;
    @SerializedName("ClockOutLongitude")
    private String ClockOutLongitude;
    @SerializedName("ClockOutLatitude")
    private String ClockOutLatitude;
    @SerializedName("DataExist")
    private boolean DataExist;
    @SerializedName("TotalHoursText")
    private String TotalHoursText;

}

