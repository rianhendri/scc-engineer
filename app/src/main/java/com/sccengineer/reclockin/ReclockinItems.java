/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.sccengineer.reclockin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReclockinItems {

    public ReclockinItems() {
    }
    @Expose
    @SerializedName("Guid")
    private String Guid;
    @Expose
    @SerializedName("RequestFromEngineerName")
    private String GuRequestFromEngineerNameid;

    public ReclockinItems(String guid, String guRequestFromEngineerNameid, String requestFromEngineerUsername,
                          String location, String requestDateTime, String roleCd, String status) {
        Guid = guid;
        GuRequestFromEngineerNameid = guRequestFromEngineerNameid;
        RequestFromEngineerUsername = requestFromEngineerUsername;
        Location = location;
        RequestDateTime = requestDateTime;
        RoleCd = roleCd;
        Status = status;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String guid) {
        Guid = guid;
    }

    public String getGuRequestFromEngineerNameid() {
        return GuRequestFromEngineerNameid;
    }

    public void setGuRequestFromEngineerNameid(String guRequestFromEngineerNameid) {
        GuRequestFromEngineerNameid = guRequestFromEngineerNameid;
    }

    public String getRequestFromEngineerUsername() {
        return RequestFromEngineerUsername;
    }

    public void setRequestFromEngineerUsername(String requestFromEngineerUsername) {
        RequestFromEngineerUsername = requestFromEngineerUsername;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getRequestDateTime() {
        return RequestDateTime;
    }

    public void setRequestDateTime(String requestDateTime) {
        RequestDateTime = requestDateTime;
    }

    public String getRoleCd() {
        return RoleCd;
    }

    public void setRoleCd(String roleCd) {
        RoleCd = roleCd;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Expose
    @SerializedName("RequestFromEngineerUsername")
    private String RequestFromEngineerUsername;

    @Expose
    @SerializedName("Location")
    private String Location;
    @Expose
    @SerializedName("RequestDateTime")
    private String RequestDateTime;
    @Expose
    @SerializedName("RoleCd")
    private String RoleCd;
    @Expose
    @SerializedName("Status")
    private String Status;
}



