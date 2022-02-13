/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.sccengineer.listsparepart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sparepart_item {

    public Sparepart_item() {
    }

    public Sparepart_item(String sparePartCodeAndName, String sparePartCd, String name, String sparePartName, String manualSparePartCd, String manualSparePartName, Integer nonReservedStock, String price) {
        SparePartCodeAndName = sparePartCodeAndName;
        SparePartCd = sparePartCd;
        Name = name;
        SparePartName = sparePartName;
        ManualSparePartCd = manualSparePartCd;
        ManualSparePartName = manualSparePartName;
        NonReservedStock = nonReservedStock;
        Price = price;
    }

    public String getSparePartCodeAndName() {
        return SparePartCodeAndName;
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

    public String getManualSparePartCd() {
        return ManualSparePartCd;
    }

    public void setManualSparePartCd(String manualSparePartCd) {
        ManualSparePartCd = manualSparePartCd;
    }

    public String getManualSparePartName() {
        return ManualSparePartName;
    }

    public void setManualSparePartName(String manualSparePartName) {
        ManualSparePartName = manualSparePartName;
    }

    public Integer getNonReservedStock() {
        return NonReservedStock;
    }

    public void setNonReservedStock(Integer nonReservedStock) {
        NonReservedStock = nonReservedStock;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    @Expose
    @SerializedName("SparePartCodeAndName")
    private String SparePartCodeAndName;
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
    @SerializedName("ManualSparePartCd")
    private String ManualSparePartCd;
    @Expose
    @SerializedName("ManualSparePartName")
    private String ManualSparePartName;

    @Expose
    @SerializedName("NonReservedStock")
    private Integer NonReservedStock;

    @Expose
    @SerializedName("Price")
    private String Price;

}



