/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
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
 *  java.io.PrintStream
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.text.ParseException
 *  java.text.SimpleDateFormat
 *  java.util.ArrayList
 *  java.util.Date
 *  java.util.Locale
 */
package com.sccengineer.notification;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sccengineer.DetailsNotification;
import com.sccengineer.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class NotificationAdapter
extends RecyclerView.Adapter<NotificationAdapter.Myviewholder> {

    Context context;
    ArrayList<NotificationItem> myItem;
    public static int positem = 0;

    public NotificationAdapter(Context c, ArrayList<NotificationItem> p){
        context = c;
        myItem = p;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_notifikasi,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {

        String string2 = "";
        Typeface type = Typeface.createFromAsset(context.getAssets(),"font/segoeuib.ttf");
        Typeface type2 = Typeface.createFromAsset(context.getAssets(),"font/segoeui.ttf");

        if (!myItem.get(i).isStsRead()){
            myviewholder.mdot.setVisibility(View.VISIBLE);
            myviewholder.mcontent.setTypeface(type);
            myviewholder.mtitle.setTypeface(type);
        }else {
            myviewholder.mdot.setVisibility(View.GONE);
            myviewholder.mcontent.setTypeface(type2);
            myviewholder.mtitle.setTypeface(type2);
        }
//        if (myItem.get(i).getStsRead()==null){
//            myviewholder.mdot.setAlpha(100);
//            Typeface face =  ResourcesCompat.getFont(context, R.font.segoeuib);
//            myviewholder.mcontent.setTypeface(face);
//            myviewholder.mtitle.setTypeface(face);
//        }else {
//            myviewholder.mdot.setAlpha(0);
//            Typeface face =  ResourcesCompat.getFont(context, R.font.segoeui);
//            myviewholder.mcontent.setTypeface(face);
//            myviewholder.mtitle.setTypeface(face);
//        }
        myviewholder.mtitle.setText(myItem.get(i).getTitle());
        myviewholder.mcontent.setText(myItem.get(i).getContent());
        String string3 = myItem.get(i).getPostedDateTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        try {
            string2 = simpleDateFormat2.format(simpleDateFormat.parse(string3));
            System.out.println(string2);
            Log.e((String)"Date", (String)string2);
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        myviewholder.mdate.setText((CharSequence)string2);
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(context, DetailsNotification.class);
                intent.putExtra("home", "homea");
                intent.putExtra("id", (myItem.get(i)).getGuid());
                intent.putExtra("username", (myItem.get(i)).getTitle());
                intent.putExtra("guid", (myItem.get(i)).getGuid());
                intent.putExtra("title", (myItem.get(i)).getTitle());
                intent.putExtra("body", (myItem.get(i)).getContent());
                intent.putExtra("home", "no");
                context.startActivity(intent);
                ((Activity)context).finish();
                ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

    }

    @Override
    public int getItemCount() {
        return
                myItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView mcontent;
        TextView mdate;
        TextView mtitle;
        ImageView mdot;

        public Myviewholder(@NonNull View view) {
            super(view);
            mtitle = (TextView)view.findViewById(R.id.title);
            mcontent = (TextView)view.findViewById(R.id.content);
            mdate = (TextView)view.findViewById(R.id.tgl);
            mdot = view.findViewById(R.id.dot);
        }
    }

}

