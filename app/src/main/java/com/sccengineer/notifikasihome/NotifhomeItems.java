/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 */
package com.sccengineer.notifikasihome;

import com.google.gson.annotations.SerializedName;

public class NotifhomeItems {



    public NotifhomeItems() {
    }


    public NotifhomeItems(String shortContent, String title, String guid, String engineerCd, String content, String postedDateTime, String readDateTime, boolean stsRead) {
        ShortContent = shortContent;
        Title = title;
        Guid = guid;
        EngineerCd = engineerCd;
        Content = content;
        PostedDateTime = postedDateTime;
        ReadDateTime = readDateTime;
        StsRead = stsRead;
    }

    public String getShortContent() {
        return ShortContent;
    }

    public void setShortContent(String shortContent) {
        ShortContent = shortContent;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String guid) {
        Guid = guid;
    }

    public String getEngineerCd() {
        return EngineerCd;
    }

    public void setEngineerCd(String engineerCd) {
        EngineerCd = engineerCd;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getPostedDateTime() {
        return PostedDateTime;
    }

    public void setPostedDateTime(String postedDateTime) {
        PostedDateTime = postedDateTime;
    }

    public String getReadDateTime() {
        return ReadDateTime;
    }

    public void setReadDateTime(String readDateTime) {
        ReadDateTime = readDateTime;
    }

    public boolean isStsRead() {
        return StsRead;
    }

    public void setStsRead(boolean stsRead) {
        StsRead = stsRead;
    }

    @SerializedName("ShortContent")
    private String ShortContent;
    @SerializedName("Title")
    private String Title;
    @SerializedName("Guid")
    private String Guid;
    @SerializedName("EngineerCd")
    private String EngineerCd;
    @SerializedName("Content")
    private String Content;
    @SerializedName("PostedDateTime")
    private String PostedDateTime;
    @SerializedName("ReadDateTime")
    private String ReadDateTime;
    @SerializedName("StsRead")
    private boolean StsRead;

}

