/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.Html
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.RatingBar
 *  android.widget.TextView
 *  androidx.recyclerview.widget.RecyclerView
 *  androidx.recyclerview.widget.RecyclerView$Adapter
 *  androidx.recyclerview.widget.RecyclerView$ViewHolder
 *  java.io.PrintStream
 *  java.lang.CharSequence
 *  java.lang.Float
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
package com.sccengineer.approval;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.sccengineer.ApprovalActivity;
import com.sccengineer.DetailApproved;
import com.sccengineer.R;
import com.sccengineer.serviceticket.STSendSparepart_adapter;
import com.sccengineer.serviceticket.STSendSparepart_item;
import com.sccengineer.serviceticket.STType_item;
import com.sccengineer.serviceticket.StType_adapter;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.sccengineer.DetailsST.seconds;
import static com.sccengineer.DetailsST.usetime;


public class ApprovalAdapter
extends RecyclerView.Adapter<ApprovalAdapter.Myviewholder> {
    boolean solved = true;
    boolean showprogress = true;
    Context context;
    ArrayList<ApprovalItem> myItem;
    public static int positem = 0;
    ImageView mimgpopup;
    //timer
    long MillisecondTime, StartTime, UpdateTime = 0L ;
    long TimeBuff = seconds ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds, Jam ;
    boolean showmore = true;
    JsonArray mjsonspar;
    ArrayList<STSendSparepart_item> listsparser;
    ArrayList<STType_item> stType_items;
    STSendSparepart_adapter ticketadapter;
    StType_adapter sttype;
    LinearLayoutManager linearLayoutManager1,linearLayoutManagertyp;
    public ApprovalAdapter(Context c, ArrayList<ApprovalItem> p){
        context = c;
        myItem = p;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_approval,
                viewGroup, false));


    }


    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {

    myviewholder.mengineername.setText(myItem.get(i).getRequestFromEngineerName());
    myviewholder.mfrom.setText("From: "+myItem.get(i).getFromRoleName());
    myviewholder.mto.setText("To: "+(myItem.get(i).getToRoleName()));
    myviewholder.mno.setText(String.valueOf(myItem.get(i).getRequestNo()));
        String string7 = myItem.get(i).getRequestDateTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String string5 = "";
        String string6="";
        try {
            string6 = simpleDateFormat2.format(simpleDateFormat.parse(string7));
            string5 = simpleDateFormat.format(simpleDateFormat.parse(string7));
            String[] separated = string5.split("T");
            String time = separated[1];
            String date = separated[0];
            myviewholder.mdate.setText("Request: "+string6+" "+time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotonews = new Intent(context, DetailApproved.class);
                gotonews.putExtra("nameeng", myItem.get(i).getRequestFromEngineerName());
                gotonews.putExtra("guid", myItem.get(i).getGuid());
                gotonews.putExtra("from", myItem.get(i).getFromRoleName());
                gotonews.putExtra("fromcd", myItem.get(i).getFromRoleCd());
                gotonews.putExtra("to", myItem.get(i).getToRoleName());
                gotonews.putExtra("tocd", myItem.get(i).getToRoleCd());
                gotonews.putExtra("reqdate",myviewholder.mdate.getText().toString());

//                    gotonews.putExtra("showaddform",showaddform);
                context.startActivity(gotonews);
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

    public static class Myviewholder extends RecyclerView.ViewHolder{

        TextView mfrom, mto, mdate, mengineername, mno;
        public Myviewholder(@NonNull View view) {
            super(view);
            mfrom = view.findViewById(R.id.fromapprov);
            mto = view.findViewById(R.id.toapprov);
            mdate = view.findViewById(R.id.dateapprov);
            mengineername = view.findViewById(R.id.engineername);
            mno = view.findViewById(R.id.no);

        }
    }

}

