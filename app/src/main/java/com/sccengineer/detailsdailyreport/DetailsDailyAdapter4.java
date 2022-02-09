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
package com.sccengineer.detailsdailyreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sccengineer.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;

public class DetailsDailyAdapter4
extends RecyclerView.Adapter<DetailsDailyAdapter4.Myviewholder> {
    ArrayList<DetailsDailyItem4> addFromItem;
    Context context;
    ImageView mimgpopup;

    public DetailsDailyAdapter4(Context context, ArrayList<DetailsDailyItem4> addFromItem) {
        this.context = context;
        this.addFromItem = addFromItem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_detaildaily4,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
//        addFromItem.get(0).setTotal("$ 1,400.00");
//        addFromItem.get(1).setTotal("$ 2,100.00");
        myviewholder.msparepartname.setText(addFromItem.get(i).getSparePartCd());
        myviewholder.mqty.setText(addFromItem.get(i).getQuantity());
        myviewholder.mno.setText(String.valueOf(i+1));
//        myviewholder.mtotal.setText(addFromItem.get(i).getTotal());
    }

    @Override
    public int getItemCount() {
        return addFromItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView mprice,mtotal,mdatedaily, msttitledaily,mpresstypedaily,mpressstatudaily,mhtml,msndaily,mcaseiddaily,mcaseprogressdaily,msparepartname,mqty,mno;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            msparepartname = itemView.findViewById(R.id.sparepartname);
            mqty = itemView.findViewById(R.id.qty);
            mno = itemView.findViewById(R.id.nono);
            mprice = itemView.findViewById(R.id.price);
//            mtotal = itemView.findViewById(R.id.total);

        }
    }
    public void clear() {
        addFromItem.clear();
        notifyDataSetChanged();
    }
    public void setAddFromItem(ArrayList<DetailsDailyItem4> addFromItem)
    {
        for (DetailsDailyItem4 im : addFromItem)
        {
            addFromItem.add(im);
        }
        notifyDataSetChanged();

    }
}

