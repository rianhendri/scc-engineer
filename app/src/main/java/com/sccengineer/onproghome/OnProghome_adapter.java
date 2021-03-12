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
package com.sccengineer.onproghome;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sccengineer.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class OnProghome_adapter
extends RecyclerView.Adapter<OnProghome_adapter.Myviewholder> {
    public static ArrayList<OnProgHome_items> onProgHome_items;
    ArrayList<OnProgHome_items> addFoclistreq2 = new ArrayList<OnProgHome_items>();
    Context context;

    public OnProghome_adapter(Context context, ArrayList<OnProgHome_items> onProgHome_items) {
        this.context = context;
        this.onProgHome_items = onProgHome_items;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_onproghome,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        if (onProgHome_items.get(i).isAssist()){
            myviewholder.mbadgeas.setVisibility(View.VISIBLE);
        }else {
            myviewholder.mbadgeas.setVisibility(View.GONE);
        }
        String newdate = "";
        myviewholder.mscd.setText(onProgHome_items.get(i).getServiceTicketCd());
        myviewholder.mcustname.setText(onProgHome_items.get(i).getCustomerName());
        String oldadate = onProgHome_items.get(i).getSupportStartDateTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            newdate = simpleDateFormat.format(simpleDateFormat.parse(oldadate));
            System.out.println(newdate);
            Log.e((String)"Date", (String)newdate);
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        String[] separated = newdate.split("T");
        separated[0].trim();; // this will contain "Fruit"
        separated[1].trim();;
        myviewholder.mstgl.setText("Start: "+separated[0]+" "+ separated[1]);
        myviewholder.mpress.setText(onProgHome_items.get(i).getPress());
        myviewholder.missue.setText(onProgHome_items.get(i).getIssue());
    }

    @Override
    public int getItemCount() {
        return 
                onProgHome_items.size();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder{
        TextView mscd, mcustname, mstgl, mpress, missue;
        LinearLayout mbadgeas;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mscd = itemView.findViewById(R.id.scd);
            mcustname = itemView.findViewById(R.id.custname);
            mstgl = itemView.findViewById(R.id.stgl);
            mbadgeas = itemView.findViewById(R.id.bagdeassist);
            mpress = itemView.findViewById(R.id.press);
            missue = itemView.findViewById(R.id.issue);


        }
    }
}

