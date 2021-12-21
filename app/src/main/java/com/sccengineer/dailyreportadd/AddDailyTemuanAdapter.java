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
package com.sccengineer.dailyreportadd;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sccengineer.R;

import java.util.ArrayList;

import static com.sccengineer.DailyReportAdd.actionlist;
import static com.sccengineer.DailyReportAdd.addFormAdapterAdapter1;
import static com.sccengineer.DailyReportAdd.addFormAdapterAdapter2;
import static com.sccengineer.DailyReportAdd.addFormAdapterAdapter3;
import static com.sccengineer.DailyReportAdd.followlist;
import static com.sccengineer.DailyReportAdd.jsonaction;
import static com.sccengineer.DailyReportAdd.jsonfollow;
import static com.sccengineer.DailyReportAdd.jsontemuan;
import static com.sccengineer.DailyReportAdd.myaction;
import static com.sccengineer.DailyReportAdd.myfollow;
import static com.sccengineer.DailyReportAdd.mytemuan;
import static com.sccengineer.DailyReportAdd.place_action;
import static com.sccengineer.DailyReportAdd.place_follow;
import static com.sccengineer.DailyReportAdd.place_temuan;
import static com.sccengineer.DailyReportAdd.temuanlist;

public class AddDailyTemuanAdapter
extends RecyclerView.Adapter<AddDailyTemuanAdapter.Myviewholder> {
    ArrayList<AddDailyTemuanItem> addFromItem;
    Context context;
    ImageView mimgpopup;
    public static Dialog dialogedit;
    int iii=0;
    String mtextnya = "";
    EditText mketerangan;
    LinearLayout mupdateedit;
    public AddDailyTemuanAdapter(Context context, ArrayList<AddDailyTemuanItem> addFromItem) {
        this.context = context;
        this.addFromItem = addFromItem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_addtemuan,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        String newdate = "";
        myviewholder.minput.setText(addFromItem.get(i).getFinding());
        myviewholder.minput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iii = i;
                mtextnya = myviewholder.minput.getText().toString();
                dialogspar();
            }
        });
        myviewholder.mno.setText(String.valueOf(i+1));
        myviewholder.mdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (temuanlist.size() >= 0) {
                    temuanlist.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i, temuanlist.size());
                    Gson gson = new GsonBuilder().create();
                    mytemuan = gson.toJsonTree(temuanlist).getAsJsonArray();
                    jsontemuan = mytemuan.toString();
                    Log.d("rrre", String.valueOf(jsontemuan));


                    for (int x = 0 ; x < actionlist.size(); x++) {


                    }

//                    if (temuanlist.size()==0){
//                        place_temuan.setVisibility(View.GONE);
//                    }

                    place_temuan.setAdapter(addFormAdapterAdapter1);

                }else {
//
                }

            }
        });
        myviewholder.mupin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("posddsi",String.valueOf(i)+"jumlah : "+addFromItem.size());
                if (i==0){

                }else {
                    String old = addFromItem.get(i).getFinding();
                    String Newny = addFromItem.get(i-1).getFinding();
                    addFromItem.get(i).setFinding(Newny);
                    addFromItem.get(i-1).setFinding(old);

                    Gson gson = new GsonBuilder().create();
                    mytemuan = gson.toJsonTree(temuanlist).getAsJsonArray();
                    jsontemuan = mytemuan.toString();
                    Log.d("rrre", String.valueOf(jsontemuan));
                    addFormAdapterAdapter1.notifyDataSetChanged();

                }


            }
        });
        myviewholder.mdowin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("posddsi",String.valueOf(i)+"jumlah : "+addFromItem.size());
                if (i==addFromItem.size()-1){

                }else {
                    String old = addFromItem.get(i).getFinding();
                    String Newny = addFromItem.get(i+1).getFinding();
                    addFromItem.get(i).setFinding(Newny);
                    addFromItem.get(i+1).setFinding(old);

                    Gson gson = new GsonBuilder().create();
                    mytemuan = gson.toJsonTree(temuanlist).getAsJsonArray();
                    jsontemuan = mytemuan.toString();
                    Log.d("rrre", String.valueOf(jsontemuan));
                    addFormAdapterAdapter1.notifyDataSetChanged();

                }


            }
        });
    }
    @SuppressLint("WrongConstant")
    public void dialogspar(){
        dialogedit = new Dialog(   context);
        dialogedit.setContentView(R.layout.dialogactiontaken);
        // set the custom dialog components - text, image and button
        mketerangan = dialogedit.findViewById(R.id.keterangan);
        mupdateedit = dialogedit.findViewById(R.id.updateedit);
        mketerangan.setText(mtextnya);
        mupdateedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFromItem.get(iii).setFinding(mketerangan.getText().toString());
                Gson gson = new GsonBuilder().create();
                mytemuan = gson.toJsonTree(temuanlist).getAsJsonArray();
                jsontemuan = mytemuan.toString();
                Log.d("rrre", String.valueOf(jsontemuan));
                addFormAdapterAdapter1.notifyDataSetChanged();
                dialogedit.dismiss();
            }
        });
        dialogedit.show();
    }

    @Override
    public int getItemCount() {
        return addFromItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        ImageView mdelete,mupin,mdowin;
        TextView mno,minput;
        //        EditText minput;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mdelete = itemView.findViewById(R.id.deletelist);
            mno = itemView.findViewById(R.id.no);
            minput = itemView.findViewById(R.id.textnya);
            mupin = itemView.findViewById(R.id.upin);
            mdowin = itemView.findViewById(R.id.dowin);


        }
    }
    public void clear() {
        addFromItem.clear();
        notifyDataSetChanged();
    }
    public void setAddFromItem(ArrayList<AddDailyTemuanItem> addFromItem)
    {
        for (AddDailyTemuanItem im : addFromItem)
        {
            addFromItem.add(im);
        }
        notifyDataSetChanged();

    }
}

