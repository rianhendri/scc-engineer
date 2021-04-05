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

    public Sparepart_item(String sparePartCodeAndName, String sparePartCd, String name, String sparePartName) {
        SparePartCodeAndName = sparePartCodeAndName;
        SparePartCd = sparePartCd;
        Name = name;
        SparePartName = sparePartName;
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






}



