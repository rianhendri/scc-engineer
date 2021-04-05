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


public class STSendSparepart_adapter
extends RecyclerView.Adapter<STSendSparepart_adapter.Myviewholder> {
    ArrayList<STSendSparepart_item> addFoclistitem;
    Context context;
    ImageView mimgpopup;
    boolean stsS = true;
    boolean usingMatrix = true;
    public STSendSparepart_adapter(Context context, ArrayList<STSendSparepart_item> addFoclistitem) {
        this.context = context;
        this.addFoclistitem = addFoclistitem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_sparepart2,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        if (addFoclistitem.get(i).getSparePartName()!=null){
            addFoclistitem.get(i).setName(addFoclistitem.get(i).getSparePartName());
        }
        myviewholder.mnamespar.setText(addFoclistitem.get(i).getName());
        myviewholder.mno.setText(String.valueOf(i+1));
        myviewholder.mcd.setText(addFoclistitem.get(i).getSparePartCd());
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                msparenaem.setText(addFoclistitem.get(i).getName());
//                dialog.dismiss();
            }
        });
        myviewholder.mdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sendsparepart_items.size() >= 0) {
                    sendsparepart_items.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i, sendsparepart_items.size());
                    Gson gson = new GsonBuilder().create();
                    myCustomArray = gson.toJsonTree(sendsparepart_items).getAsJsonArray();
                    jsonarayitem = myCustomArray.toString();
                    Log.d("sizecart_22", String.valueOf(jsonarayitem));
//                    totalqty = 0;
//                    totalprice = 0.0;
                    for (int x = 0 ; x < sendsparepart_items.size(); x++) {


                    }
//                    mtotalitem.setText(String.valueOf(addFoclistreq.size()));
//                    grandTotalplus = 0;
//                    intSum = 0;
//                    for (int i = 0; i < list.size(); i++) {
//                        grandTotalplus = grandTotalplus + list.get(i).getTotal();
//                    }
                    if (sendsparepart_items.size()==0){
                        msendpartlist.setVisibility(View.GONE);
                    }



                }else {
//
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return addFoclistitem.size();
    }

    public void filterList(ArrayList<STSendSparepart_item> filteredList) {
        addFoclistitem = filteredList;
        notifyDataSetChanged();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder{

        TextView mnamespar;
        ImageView mdelete;
        TextView mno,mcd;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mnamespar = itemView.findViewById(R.id.namespar);
            mdelete = itemView.findViewById(R.id.deletelist);
            mno = itemView.findViewById(R.id.nospart);
            mcd = itemView.findViewById(R.id.codecd);

        }
    }
}

