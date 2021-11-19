/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.sccengineer.Chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Itemchat4a {

    public Itemchat4a() {

    }

    public Itemchat4a(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Expose
    @SerializedName("email")
    private String email;
}



