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
import static com.sccengineer.DailyReportAdd.followlist;
import static com.sccengineer.DailyReportAdd.jsonaction;
import static com.sccengineer.DailyReportAdd.jsonfollow;
import static com.sccengineer.DailyReportAdd.myaction;
import static com.sccengineer.DailyReportAdd.myfollow;
import static com.sccengineer.DailyReportAdd.place_action;
import static com.sccengineer.DailyReportAdd.place_follow;

public class AddDailyFollowAdapter
extends RecyclerView.Adapter<AddDailyFollowAdapter.Myviewholder> {
    ArrayList<AddDailyFollowItem> addFromItem;
    Context context;
    ImageView mimgpopup;

    public AddDailyFollowAdapter(Context context, ArrayList<AddDailyFollowItem> addFromItem) {
        this.context = context;
        this.addFromItem = addFromItem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_addfollow,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
//        followlist.get(i).setText(addFromItem.get(i).getFollowUp());
        myviewholder.minput.setText(addFromItem.get(i).getFollowUp());
        myviewholder.mno.setText(String.valueOf(i+1));
        myviewholder.mdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followlist.size() >= 0) {
                    followlist.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i, followlist.size());
                    Gson gson = new GsonBuilder().create();
                    myfollow = gson.toJsonTree(followlist).getAsJsonArray();
                    jsonfollow = myfollow.toString();
                    Log.d("sizecart_21", String.valueOf(jsonfollow));

                    for (int x = 0 ; x < followlist.size(); x++) {


                    }

                    if (followlist.size()==0){
                        place_follow.setVisibility(View.GONE);
                    }



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

