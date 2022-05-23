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
package com.sccengineer.reclockin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sccengineer.DetailReclock;
import com.sccengineer.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

//import com.sccengineer.DetailsFormActivity;

//import static com.smartcarecenter.FormActivity.valuefilter;

public class ReClockInAdapter
extends RecyclerView.Adapter<ReClockInAdapter.Myviewholder> {
    ArrayList<ReclockinItems> addFromItem;
    Context context;
    String engas = "";
    ImageView mimgpopup;
    String string2="";
    boolean toastnya = false;
    public ReClockInAdapter(Context context, ArrayList<ReclockinItems> addFromItem) {
        this.context = context;
        this.addFromItem = addFromItem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_reclock,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        myviewholder.mreqfrom.setText(addFromItem.get(i).getGuRequestFromEngineerNameid());
//        myviewholder.mreqdate.setText(addFromItem.get(i).getRequestDateTime());
        myviewholder.mstatus.setText(addFromItem.get(i).getStatus());
        myviewholder.mlocation.setText(addFromItem.get(i).getLocation());

        String string3 = addFromItem.get(i).getRequestDateTime();
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
        myviewholder.mreqdate.setText((CharSequence)string2);
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotonews = new Intent(context, DetailReclock.class);
                gotonews.putExtra("name",addFromItem.get(i).getGuRequestFromEngineerNameid());
                gotonews.putExtra("guide",addFromItem.get(i).getGuid());
                gotonews.putExtra("date",addFromItem.get(i).getRequestDateTime());
                gotonews.putExtra("role",addFromItem.get(i).getRoleCd());
                gotonews.putExtra("location",addFromItem.get(i).getLocation());
                context.startActivity(gotonews);
                ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                ((Activity)context).finish();
            }
        });


    }


    @Override
    public int getItemCount() {
        return addFromItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView mreqfrom, mreqdate, mstatus, mlocation, mpress, mdescrip, massst, mcurrentst;
        LinearLayout massist;
        ImageView xgambar_item;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mreqfrom = itemView.findViewById(R.id.reqfrom);
            mreqdate = itemView.findViewById(R.id.reqdate);
            mstatus = itemView.findViewById(R.id.statusreq);
            mlocation = itemView.findViewById(R.id.location);
        }
    }
    public interface CallBackUs {
        void addCartItemView();
    }
    // this interface creates for call the invalidateoptionmenu() for refresh the menu item
    public interface HomeCallBack {
        void updateCartCount(Context context);
    }
    public void clear() {
        addFromItem.clear();
        notifyDataSetChanged();
    }
    public void setAddFromItem(ArrayList<ReclockinItems> addFromItem)
    {
        for (ReclockinItems im : addFromItem)
        {
            addFromItem.add(im);
        }
        notifyDataSetChanged();

    }

}

