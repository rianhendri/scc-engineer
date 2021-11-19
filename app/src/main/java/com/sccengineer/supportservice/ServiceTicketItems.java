/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.sccengineer.supportservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceTicketItems {


    public ServiceTicketItems(String serviceTicketCd, String formRequestCd, String formRequestPhotoURL, String formRequestPhotoThumbURL, String date, String pressGuid, String pressName, String description, String status, String statusName, String statusColorCode, String createdBy, String createdDateTime, boolean stsAssist, String assignmentStatusName, String assignmentStatusColorCode, String assignedDateTime, String additionalTextHtml, String customerName) {
        this.serviceTicketCd = serviceTicketCd;
        this.formRequestCd = formRequestCd;
        this.formRequestPhotoURL = formRequestPhotoURL;
        this.formRequestPhotoThumbURL = formRequestPhotoThumbURL;
        this.date = date;
        this.pressGuid = pressGuid;
        this.pressName = pressName;
        this.description = description;
        this.status = status;
        this.statusName = statusName;
        this.statusColorCode = statusColorCode;
        this.createdBy = createdBy;
        this.createdDateTime = createdDateTime;
        this.stsAssist = stsAssist;
        this.assignmentStatusName = assignmentStatusName;
        this.assignmentStatusColorCode = assignmentStatusColorCode;
        this.assignedDateTime = assignedDateTime;
        this.additionalTextHtml = additionalTextHtml;
        this.customerName = customerName;
    }

    public String getServiceTicketCd() {
        return serviceTicketCd;
    }

    public void setServiceTicketCd(String serviceTicketCd) {
        this.serviceTicketCd = serviceTicketCd;
    }

    public String getFormRequestCd() {
        return formRequestCd;
    }

    public void setFormRequestCd(String formRequestCd) {
        this.formRequestCd = formRequestCd;
    }

    public String getFormRequestPhotoURL() {
        return formRequestPhotoURL;
    }

    public void setFormRequestPhotoURL(String formRequestPhotoURL) {
        this.formRequestPhotoURL = formRequestPhotoURL;
    }

    public String getFormRequestPhotoThumbURL() {
        return formRequestPhotoThumbURL;
    }

    public void setFormRequestPhotoThumbURL(String formRequestPhotoThumbURL) {
        this.formRequestPhotoThumbURL = formRequestPhotoThumbURL;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPressGuid() {
        return pressGuid;
    }

    public void setPressGuid(String pressGuid) {
        this.pressGuid = pressGuid;
    }

    public String getPressName() {
        return pressName;
    }

    public void setPressName(String pressName) {
        this.pressName = pressName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusColorCode() {
        return statusColorCode;
    }

    public void setStatusColorCode(String statusColorCode) {
        this.statusColorCode = statusColorCode;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public boolean isStsAssist() {
        return stsAssist;
    }

    public void setStsAssist(boolean stsAssist) {
        this.stsAssist = stsAssist;
    }

    public String getAssignmentStatusName() {
        return assignmentStatusName;
    }

    public void setAssignmentStatusName(String assignmentStatusName) {
        this.assignmentStatusName = assignmentStatusName;
    }

    public String getAssignmentStatusColorCode() {
        return assignmentStatusColorCode;
    }

    public void setAssignmentStatusColorCode(String assignmentStatusColorCode) {
        this.assignmentStatusColorCode = assignmentStatusColorCode;
    }

    public String getAssignedDateTime() {
        return assignedDateTime;
    }

    public void setAssignedDateTime(String assignedDateTime) {
        this.assignedDateTime = assignedDateTime;
    }

    public String getAdditionalTextHtml() {
        return additionalTextHtml;
    }

    public void setAdditionalTextHtml(String additionalTextHtml) {
        this.additionalTextHtml = additionalTextHtml;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Expose
    @SerializedName("serviceTicketCd")
    private String serviceTicketCd;
    @Expose
    @SerializedName("formRequestCd")
    private String formRequestCd;
    @Expose
    @SerializedName("formRequestPhotoURL")
    private String formRequestPhotoURL;
    @Expose
    @SerializedName("formRequestPhotoThumbURL")
    private String formRequestPhotoThumbURL;
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("pressGuid")
    private String pressGuid;
    @Expose
    @SerializedName("pressName")
    private String pressName;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("statusName")
    private String statusName;
    @Expose
    @SerializedName("statusColorCode")
    private String statusColorCode;
    @Expose
    @SerializedName("createdBy")
    private String createdBy;
    @Expose
    @SerializedName("createdDateTime")
    private String createdDateTime;
    @Expose
    @SerializedName("stsAssist")
    private boolean stsAssist;
    @Expose
    @SerializedName("assignmentStatusName")
    private String assignmentStatusName;
    @Expose
    @SerializedName("assignmentStatusColorCode")
    private String assignmentStatusColorCode;
    @Expose
    @SerializedName("assignedDateTime")
    private String assignedDateTime;

    @Expose
    @SerializedName("additionalTextHtml")
    private String additionalTextHtml;
    @Expose
    @SerializedName("customerName")
    private String customerName;

    public ServiceTicketItems() {
    }

}



