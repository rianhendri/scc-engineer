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
package com.sccengineer.serviceticket;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sccengineer.R;

import java.util.ArrayList;

import static com.sccengineer.DetailsST.jsonarayitem;
import static com.sccengineer.DetailsST.msendpartlist;
import static com.sccengineer.DetailsST.myCustomArray;
import static com.sccengineer.DetailsST.sendsparepart_items;


public class StType_adapter
extends RecyclerView.Adapter<StType_adapter.Myviewholder> {
    ArrayList<STType_item> addFoclistitem;
    Context context;
    ImageView mimgpopup;
    boolean stsS = true;
    boolean usingMatrix = true;
    public StType_adapter(Context context, ArrayList<STType_item> addFoclistitem) {
        this.context = context;
        this.addFoclistitem = addFoclistitem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_type,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {

        myviewholder.mnametype.setText(addFoclistitem.get(i).getServiceTypeName());
        myviewholder.mno.setText(String.valueOf(i+1));
        myviewholder.mstarttype.setText(addFoclistitem.get(i).getStartDateText());
        myviewholder.mendtype.setText(addFoclistitem.get(i).getEndDateText());
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                msparenaem.setText(addFoclistitem.get(i).getName());
//                dialog.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return addFoclistitem.size();
    }

    public void filterList(ArrayList<STType_item> filteredList) {
        addFoclistitem = filteredList;
        notifyDataSetChanged();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder{

        TextView mnametype, mstarttype, mendtype;
//        ImageView mdelete;
        TextView mno,mcd;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mnametype = itemView.findViewById(R.id.nametype);
//            mdelete = itemView.findViewById(R.id.deletelist);
            mno = itemView.findViewById(R.id.nospart);
            mstarttype = itemView.findViewById(R.id.starttype);
            mendtype = itemView.findViewById(R.id.endtype);

        }
    }
}

