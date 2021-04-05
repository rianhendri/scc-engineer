/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.sccengineer.listsparepartview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sparepart_itemview {

    public Sparepart_itemview() {
    }
    public Sparepart_itemview(String sparePartCodeAndName, String sparePartCd, String name) {
        SparePartCodeAndName = sparePartCodeAndName;
        SparePartCd = sparePartCd;
        Name = name;
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

    @Expose
    @SerializedName("SparePartCodeAndName")
    private String SparePartCodeAndName;
    @Expose
    @SerializedName("SparePartCd")
    private String SparePartCd;
    @Expose
    @SerializedName("Name")
    private String Name;







}



