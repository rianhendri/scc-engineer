/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Color
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 *  androidx.recyclerview.widget.RecyclerView
 *  androidx.recyclerview.widget.RecyclerView$Adapter
 *  androidx.recyclerview.widget.RecyclerView$ViewHolder
 *  com.squareup.picasso.Picasso
 *  com.squareup.picasso.RequestCreator
 *  java.io.PrintStream
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.text.ParseException
 *  java.text.SimpleDateFormat
 *  java.util.ArrayList
 *  java.util.Date
 *  java.util.Locale
 */
package com.sccengineer.generatechat;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sccengineer.R;

import java.util.ArrayList;

import static com.sccengineer.ListChat.sendtext;


//import static com.e.chatforscctest.ListChat.idhcat;


public class Adaptergeneratechat
extends RecyclerView.Adapter<Adaptergeneratechat.Myviewholder>  {
    public static ArrayList<Itemgeneratechat> addFoclistreq;
    Context context;
    public Adaptergeneratechat(Context context, ArrayList<Itemgeneratechat> addFoclistitem) {
        this.context = context;
        this.addFoclistreq = addFoclistitem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_generatechat,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {

        //setting posisi chat dan visible file
        myviewholder.mtext.setText(addFoclistreq.get(i).getText());
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendtext.setText(addFoclistreq.get(i).getText());
                int position = addFoclistreq.get(i).getText().length();
                Editable etext = sendtext.getText();
                Selection.setSelection(etext, position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return 
                addFoclistreq.size();
    }


    public static class Myviewholder extends RecyclerView.ViewHolder{

        TextView mtext;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mtext = itemView.findViewById(R.id.generatetext);

        }
    }


}

