/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.sccengineer.generatechat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Itemgeneratechat {

    public Itemgeneratechat() {

    }

    public Itemgeneratechat(String text) {
        Text = text;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    @Expose
    @SerializedName("Text")
    private String Text;
}



