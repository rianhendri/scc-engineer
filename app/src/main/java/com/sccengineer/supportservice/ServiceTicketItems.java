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


    public ServiceTicketItems(String serviceTicketCd, String formRequestCd, String formRequestPhotoURL, String formRequestPhotoThumbURL, String date, String pressGuid, String pressName, String description, String status, int statusName, String statusColorCode, String createdBy, String createdDateTime) {
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

    public int getStatusName() {
        return statusName;
    }

    public void setStatusName(int statusName) {
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
    private int statusName;
    @Expose
    @SerializedName("statusColorCode")
    private String statusColorCode;
    @Expose
    @SerializedName("createdBy")
    private String createdBy;

    @Expose
    @SerializedName("createdDateTime")
    private String createdDateTime;


    public ServiceTicketItems() {
    }

}



