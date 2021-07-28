/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.sccengineer.spartsend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendSparepart_item {

    public SendSparepart_item() {
    }

    public String getSparePartCodeAndName() {
        return SparePartCodeAndName;
    }


    @Expose
    @SerializedName("SparePartCodeAndName")
    private String SparePartCodeAndName;

    public SendSparepart_item(String sparePartCodeAndName, String sparePartCd, String name, String sparePartName, int quantity, String reason, String caseID, String installDate, String orderDate, boolean stsAllowEdit,
                              boolean stsAllowUpdateInstallDate, String statusName, String statusTextColor, boolean stsAllowDelete) {
        SparePartCodeAndName = sparePartCodeAndName;
        SparePartCd = sparePartCd;
        Name = name;
        SparePartName = sparePartName;
        Quantity = quantity;
        Reason = reason;
        CaseID = caseID;
        InstallDate = installDate;
        OrderDate = orderDate;
        StsAllowEdit = stsAllowEdit;
        StsAllowUpdateInstallDate = stsAllowUpdateInstallDate;
        StatusName = statusName;
        StatusTextColor = statusTextColor;
        StsAllowDelete = stsAllowDelete;
    }

    public void setSparePartCodeAndName(String sparePartCodeAndName) {
        SparePartCodeAndName = sparePartCodeAndName;
    }

    public String getSparePartCd() {
        return SparePartCd;
    }

    public void setSparePartCd(String sparePartCd) {
        SparePartCd = sparePartCd;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSparePartName() {
        return SparePartName;
    }

    public void setSparePartName(String sparePartName) {
        SparePartName = sparePartName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getCaseID() {
        return CaseID;
    }

    public void setCaseID(String caseID) {
        CaseID = caseID;
    }

    public String getInstallDate() {
        return InstallDate;
    }

    public void setInstallDate(String installDate) {
        InstallDate = installDate;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public boolean isStsAllowEdit() {
        return StsAllowEdit;
    }

    public void setStsAllowEdit(boolean stsAllowEdit) {
        StsAllowEdit = stsAllowEdit;
    }

    public boolean isStsAllowUpdateInstallDate() {
        return StsAllowUpdateInstallDate;
    }

    public void setStsAllowUpdateInstallDate(boolean stsAllowUpdateInstallDate) {
        StsAllowUpdateInstallDate = stsAllowUpdateInstallDate;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    public String getStatusTextColor() {
        return StatusTextColor;
    }

    public void setStatusTextColor(String statusTextColor) {
        StatusTextColor = statusTextColor;
    }

    public boolean isStsAllowDelete() {
        return StsAllowDelete;
    }

    public void setStsAllowDelete(boolean stsAllowDelete) {
        StsAllowDelete = stsAllowDelete;
    }

    @Expose
    @SerializedName("SparePartCd")
    private String SparePartCd;
    @Expose
    @SerializedName("Name")
    private String Name;
    @Expose
    @SerializedName("SparePartName")
    private String SparePartName;

    @Expose
    @SerializedName("Quantity")
    private int Quantity;

    @Expose
    @SerializedName("Reason")
    private String Reason;

    @Expose
    @SerializedName("CaseID")
    private String CaseID;
    @Expose
    @SerializedName("InstallDate")
    private String InstallDate;
    @Expose
    @SerializedName("OrderDate")
    private String OrderDate;
    @Expose
    @SerializedName("StsAllowEdit")
    private boolean StsAllowEdit;
    @Expose
    @SerializedName("StsAllowUpdateInstallDate")
    private boolean StsAllowUpdateInstallDate;
    @Expose
    @SerializedName("StatusName")
    private String StatusName;
    @Expose
    @SerializedName("StatusTextColor")
    private String StatusTextColor;
    @Expose
    @SerializedName("StsAllowDelete")
    private boolean StsAllowDelete;

}



