/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.sccengineer.detailsdailyreport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailsDailyItem4 {


    public DetailsDailyItem4(String sparePartName, String sparePartCd, String quantity, String price, String total) {
        SparePartName = sparePartName;
        SparePartCd = sparePartCd;
        Quantity = quantity;
        Price = price;
        Total = total;
    }

    public String getSparePartName() {
        return SparePartName;
    }

    public void setSparePartName(String sparePartName) {
        SparePartName = sparePartName;
    }

    public String getSparePartCd() {
        return SparePartCd;
    }

    public void setSparePartCd(String sparePartCd) {
        SparePartCd = sparePartCd;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    @Expose
    @SerializedName("SparePartName")
    private String SparePartName;
    @Expose
    @SerializedName("SparePartCd")
    private String SparePartCd;
    @Expose
    @SerializedName("Quantity")
    private String Quantity;
    @Expose
    @SerializedName("Price")
    private String Price;
    @Expose
    @SerializedName("Total")
    private String Total;

    public DetailsDailyItem4() {

    }

}



