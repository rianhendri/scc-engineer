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
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        String s = "<b>"+addFoclistitem.get(i).getSparePartCd()+"</b>"+" ("+addFoclistitem.get(i).getName()+")";
        myviewholder.mnamespar.setText(Html.fromHtml(s));
//        myviewholder.mnamespar.setText(addFoclistitem.get(i).getSparePartCd()+" ("+addFoclistitem.get(i).getName()+")");

        myviewholder.mcd.setText(addFoclistitem.get(i).getSparePartCd());
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                if (myviewholder.mqtysper.length()==0){
                    Toast.makeText(context, "Qty tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else {
                    if (myviewholder.mqtysper.getText().toString().equals("0")){
                        Toast.makeText(context, "Qty tidak boleh 0", Toast.LENGTH_SHORT).show();
                    }else {
                        if (myviewholder.mreason.length()==0){
                            Toast.makeText(context, "Reason tidak boleh kosong", Toast.LENGTH_SHORT).show();

                        }else {
                            msendpartlist.setVisibility(View.VISIBLE);
                            tambahpart = new SendSparepart_item();
                            tambahpart.setName(addFoclistitem.get(i).getName());
                            tambahpart.setSparePartCodeAndName(addFoclistitem.get(i).getSparePartCodeAndName());
                            tambahpart.setSparePartCd(addFoclistitem.get(i).getSparePartCd());
                            tambahpart.setInstallDate(null);
                            tambahpart.setOrderDate(null);
                            tambahpart.setStsAllowEdit(true);
                            tambahpart.setStsAllowUpdateInstallDate(true);
                            tambahpart.setStsAllowDelete(true);
                            tambahpart.setStatusName("-");
                            tambahpart.setCaseID(myviewholder.mcaseid.getText().toString());
                            tambahpart.setReason(myviewholder.mreason.getText().toString());
                            tambahpart.setQuantity(Integer.parseInt(myviewholder.mqtysper.getText().toString()));

                            listpoact.add(tambahpart);
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

                    }

                }


            }
        });
        myviewholder.mqtysper.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (myviewholder.mqtysper.length()>0){

                    if (Integer.parseInt(myviewholder.mqtysper.getText().toString())>99){
                        myviewholder.mqtysper.setText("99");
                        Toast.makeText(context, "Qty maksimal 99", Toast.LENGTH_SHORT).show();
                    }else {

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1 && s.toString().startsWith("0")) {
                    s.clear();
                }
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
        EditText mqtysper,mreason,mcaseid;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mnamespar = itemView.findViewById(R.id.namespar);
            mcd = itemView.findViewById(R.id.codecd);
            mqtysper = itemView.findViewById(R.id.qtysper);
            mreason = itemView.findViewById(R.id.reason);
            mcaseid = itemView.findViewById(R.id.caseid);

        }
    }
}

