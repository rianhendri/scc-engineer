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
package com.sccengineer.listsparepart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sccengineer.DetailsST;
import com.sccengineer.R;
import com.sccengineer.spartsend.SendSparepart_adapter;
import com.sccengineer.spartsend.SendSparepart_item;

import java.util.ArrayList;

import static com.sccengineer.DetailsST.dialog;
import static com.sccengineer.DetailsST.linearLayoutManager2;
import static com.sccengineer.DetailsST.msendpartlist;
import static com.sccengineer.DetailsST.msparenaem;
import static com.sccengineer.DetailsST.myCustomArray;
import static com.sccengineer.DetailsST.sendsparepart_adapter;
import static com.sccengineer.DetailsST.sendsparepart_items;

import static com.sccengineer.DetailsST.jsonarayitem;
public class Sparepart_adapter
extends RecyclerView.Adapter<Sparepart_adapter.Myviewholder> {
    ArrayList<Sparepart_item> addFoclistitem;
    SendSparepart_item tambahpart;
    public static ArrayList<SendSparepart_item> listpoact = new ArrayList<SendSparepart_item>();
    Context context;
    ImageView mimgpopup;
    boolean stsS = true;
    boolean usingMatrix = true;
    public Sparepart_adapter(Context context, ArrayList<Sparepart_item> addFoclistitem) {
        this.context = context;
        this.addFoclistitem = addFoclistitem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_sparepart,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        if (addFoclistitem.get(i).getSparePartName()!=null){
            addFoclistitem.get(i).setName(addFoclistitem.get(i).getSparePartName());
        }
        myviewholder.mnamespar.setText(addFoclistitem.get(i).getName());

        myviewholder.mcd.setText(addFoclistitem.get(i).getSparePartCd());
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                msendpartlist.setVisibility(View.VISIBLE);
//                msparenaem.setText(addFoclistitem.get(i).getName());
//                linearLayoutManager2 = new LinearLayoutManager(context, LinearLayout.VERTICAL,false);
//                msendpartlist.setLayoutManager(linearLayoutManager2);
//                msendpartlist.setHasFixedSize(true);
//                sendsparepart_items = new ArrayList();
                tambahpart = new SendSparepart_item();
                tambahpart.setName(addFoclistitem.get(i).getName());
                tambahpart.setSparePartCodeAndName(addFoclistitem.get(i).getSparePartCodeAndName());
                tambahpart.setSparePartCd(addFoclistitem.get(i).getSparePartCd());
                listpoact.add(tambahpart);

//                for (int i = 0; i < listpoact.size(); i++) {
//                    for (int j = i + 1; j < listpoact.size(); j++) {
//                        if (listpoact.get(i).getSparePartCd().equals(listpoact.get(j).getSparePartCd())) {
////                    listpoact.get(i).setQuantity(listpoact.get(j).getQuantity());
////                    listpoact.get(i).setHarga(listpoact.get(j).getHarga());
//                            listpoact.remove(j);
//                            j--;
////                    Log.d("remove", String.valueOf(cartModels.size()));
//
//                        }
//                    }
//
//                }

                sendsparepart_items.addAll(listpoact);
                Gson gson = new GsonBuilder().create();
                myCustomArray = gson.toJsonTree(sendsparepart_items).getAsJsonArray();
                jsonarayitem = myCustomArray.toString();

                listpoact.clear();
                Log.d("sizecart_11", String.valueOf(sendsparepart_items.size()));
                Log.d("sizecart_22", String.valueOf(jsonarayitem));
////////////////////// adapter di masukan ke recyler//
                sendsparepart_adapter = new SendSparepart_adapter(context, sendsparepart_items);
                msendpartlist.setAdapter(sendsparepart_adapter);
                dialog.dismiss();

            }
        });
    }

    @Override
    public int getItemCount() {
        return addFoclistitem.size();
    }

    public void filterList(ArrayList<Sparepart_item> filteredList) {
        addFoclistitem = filteredList;
        notifyDataSetChanged();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder{

        TextView mnamespar,mno,mcd;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mnamespar = itemView.findViewById(R.id.namespar);
            mcd = itemView.findViewById(R.id.codecd);

        }
    }
}

