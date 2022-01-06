/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.sccengineer.loca;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Locati {

    public Locati() {
    }


    public Locati(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Expose
    @SerializedName("latitude:")
    private String latitude;
    @Expose
    @SerializedName("longitude")
    private String longitude;
}



