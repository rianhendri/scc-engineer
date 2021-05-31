/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.sccengineer.approval;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sccengineer.serviceticket.STSendSparepart_item;
import com.sccengineer.serviceticket.STType_item;

import java.util.ArrayList;

public class ApprovalItem {

    public ApprovalItem() {
    }

    public ApprovalItem(String guid, int requestNo, String fromRoleCd, String toRoleCd, String requestDateTime, String approved, String approvalBy, String approvalDateTime, String approveEngineerUsename, String approveEngineerName, String fromRoleName, String toRoleName, String requestFromEngineerUsername, String requestFromEngineerName) {
        Guid = guid;
        RequestNo = requestNo;
        FromRoleCd = fromRoleCd;
        ToRoleCd = toRoleCd;
        RequestDateTime = requestDateTime;
        Approved = approved;
        ApprovalBy = approvalBy;
        ApprovalDateTime = approvalDateTime;
        ApproveEngineerUsename = approveEngineerUsename;
        ApproveEngineerName = approveEngineerName;
        FromRoleName = fromRoleName;
        ToRoleName = toRoleName;
        RequestFromEngineerUsername = requestFromEngineerUsername;
        RequestFromEngineerName = requestFromEngineerName;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String guid) {
        Guid = guid;
    }

    public int getRequestNo() {
        return RequestNo;
    }

    public void setRequestNo(int requestNo) {
        RequestNo = requestNo;
    }

    public String getFromRoleCd() {
        return FromRoleCd;
    }

    public void setFromRoleCd(String fromRoleCd) {
        FromRoleCd = fromRoleCd;
    }

    public String getToRoleCd() {
        return ToRoleCd;
    }

    public void setToRoleCd(String toRoleCd) {
        ToRoleCd = toRoleCd;
    }

    public String getRequestDateTime() {
        return RequestDateTime;
    }

    public void setRequestDateTime(String requestDateTime) {
        RequestDateTime = requestDateTime;
    }

    public String getApproved() {
        return Approved;
    }

    public void setApproved(String approved) {
        Approved = approved;
    }

    public String getApprovalBy() {
        return ApprovalBy;
    }

    public void setApprovalBy(String approvalBy) {
        ApprovalBy = approvalBy;
    }

    public String getApprovalDateTime() {
        return ApprovalDateTime;
    }

    public void setApprovalDateTime(String approvalDateTime) {
        ApprovalDateTime = approvalDateTime;
    }

    public String getApproveEngineerUsename() {
        return ApproveEngineerUsename;
    }

    public void setApproveEngineerUsename(String approveEngineerUsename) {
        ApproveEngineerUsename = approveEngineerUsename;
    }

    public String getApproveEngineerName() {
        return ApproveEngineerName;
    }

    public void setApproveEngineerName(String approveEngineerName) {
        ApproveEngineerName = approveEngineerName;
    }

    public String getFromRoleName() {
        return FromRoleName;
    }

    public void setFromRoleName(String fromRoleName) {
        FromRoleName = fromRoleName;
    }

    public String getToRoleName() {
        return ToRoleName;
    }

    public void setToRoleName(String toRoleName) {
        ToRoleName = toRoleName;
    }

    public String getRequestFromEngineerUsername() {
        return RequestFromEngineerUsername;
    }

    public void setRequestFromEngineerUsername(String requestFromEngineerUsername) {
        RequestFromEngineerUsername = requestFromEngineerUsername;
    }

    public String getRequestFromEngineerName() {
        return RequestFromEngineerName;
    }

    public void setRequestFromEngineerName(String requestFromEngineerName) {
        RequestFromEngineerName = requestFromEngineerName;
    }

    @Expose
    @SerializedName("Guid")
    private String Guid;
    @Expose
    @SerializedName("RequestNo")
    private int RequestNo;
    @Expose
    @SerializedName("FromRoleCd")
    private String FromRoleCd;
    @Expose
    @SerializedName("ToRoleCd")
    private String ToRoleCd;
    @Expose
    @SerializedName("RequestDateTime")
    private String RequestDateTime;
    @Expose
    @SerializedName("Approved")
    private String Approved;
    @Expose
    @SerializedName("ApprovalBy")
    private String ApprovalBy;
    @Expose
    @SerializedName("ApprovalDateTime")
    private String ApprovalDateTime;
    @Expose
    @SerializedName("ApproveEngineerUsename")
    private String ApproveEngineerUsename;
    @Expose
    @SerializedName("ApproveEngineerName")
    private String ApproveEngineerName;
    @Expose
    @SerializedName("FromRoleName")
    private String FromRoleName;
    @Expose
    @SerializedName("ToRoleName")
    private String ToRoleName;
    @Expose
    @SerializedName("RequestFromEngineerUsername")
    private String RequestFromEngineerUsername;
    @Expose
    @SerializedName("RequestFromEngineerName")
    private String RequestFromEngineerName;
}

