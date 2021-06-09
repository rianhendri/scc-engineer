/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 *  androidx.recyclerview.widget.RecyclerView
 *  androidx.recyclerview.widget.RecyclerView$Adapter
 *  androidx.recyclerview.widget.RecyclerView$ViewHolder
 *  com.squareup.picasso.Picasso
 *  com.squareup.picasso.RequestCreator
 *  java.lang.CharSequence
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 */
package com.sccengineer.leaveform;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sccengineer.FormCutiDetails;
import com.sccengineer.R;
import com.sccengineer.TabAct;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.sccengineer.AttendanceActivity.posisinya;


public class LeaveAdapter extends RecyclerView.Adapter<LeaveAdapter.Myviewholder> {
    private LinearLayoutManager linearLayoutManager;
    Context context;
    ArrayList<LeaveItems> myItem;
    public static int positem = 0;
    public static RecyclerView mchatdialog;
    public LeaveAdapter(Context c, ArrayList<LeaveItems> p){
        context = c;
        myItem = p;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_leave,
                viewGroup, false));

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
       myviewholder.mfrom.setText(myItem.get(i).getClockIn());
        myviewholder.mto.setText(myItem.get(i).getClockOut());
        myviewholder.mnote.setText(myItem.get(i).getDate());
        myviewholder.mstatus.setText(myItem.get(i).getRoleCd());

        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotonews = new Intent(context, FormCutiDetails.class);
//                    gotonews.putExtra("mshowPurchaseOrderPO",mshowPurchaseOrderPO);
//                    gotonews.putExtra("mshowPurchaseOrderPO",mshowPurchaseOrderFOC);
//                    gotonews.putExtra("showaddpo",showaddpo);
//                    gotonews.putExtra("showaddfoc",showaddfoc);
                context.startActivity(gotonews);
                ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                ((Activity)context).finish();
            }
        });


    }

    @Override
    public int getItemCount() {
        return
                myItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView mfrom,mto,mnote,mstatus,mclockouttext,mhours,mhourstext,mnodata;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mfrom = itemView.findViewById(R.id.fromdate);
            mto = itemView.findViewById(R.id.todate);
            mnote = itemView.findViewById(R.id.notes);
            mstatus = itemView.findViewById(R.id.statusleav);


        }
    }
}
