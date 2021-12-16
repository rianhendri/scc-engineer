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

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sccengineer.R;

import java.util.ArrayList;

import static com.sccengineer.DailyReportAdd.actionlist;
import static com.sccengineer.DailyReportAdd.addFormAdapterAdapter1;
import static com.sccengineer.DailyReportAdd.followlist;
import static com.sccengineer.DailyReportAdd.jsonaction;
import static com.sccengineer.DailyReportAdd.jsonfollow;
import static com.sccengineer.DailyReportAdd.jsontemuan;
import static com.sccengineer.DailyReportAdd.myaction;
import static com.sccengineer.DailyReportAdd.myfollow;
import static com.sccengineer.DailyReportAdd.mytemuan;
import static com.sccengineer.DailyReportAdd.place_follow;
import static com.sccengineer.DailyReportAdd.place_temuan;
import static com.sccengineer.DailyReportAdd.temuanlist;

public class AddDailyTemuanAdapter
extends RecyclerView.Adapter<AddDailyTemuanAdapter.Myviewholder> {
    ArrayList<AddDailyTemuanItem> addFromItem;
    Context context;
    ImageView mimgpopup;

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
//        followlist.get(i).setText(addFromItem.get(i).getFinding());
        myviewholder.minput.setText(addFromItem.get(i).getFinding());
        myviewholder.mno.setText(String.valueOf(i+1));

        myviewholder.minput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addFromItem.get(i).setFinding(myviewholder.minput.getText().toString());
                Gson gson = new GsonBuilder().create();
                mytemuan = gson.toJsonTree(temuanlist).getAsJsonArray();
                jsontemuan = mytemuan.toString();
                Log.d("jsonubah", String.valueOf(jsontemuan));
            }
        });
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
                    Log.d("sizecart_21", String.valueOf(jsontemuan));

                    for (int x = 0 ; x < temuanlist.size(); x++) {


                    }

                    if (temuanlist.size()==0){
                        place_follow.setVisibility(View.GONE);
                    }

                    place_temuan.setAdapter(addFormAdapterAdapter1);


                }else {
//
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return addFromItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        ImageView mdelete;
        TextView mno;
        EditText minput;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mdelete = itemView.findViewById(R.id.deletelist);
            mno = itemView.findViewById(R.id.no);
            minput = itemView.findViewById(R.id.textnya);

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

