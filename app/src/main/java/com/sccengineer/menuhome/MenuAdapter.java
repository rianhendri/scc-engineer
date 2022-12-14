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
package com.sccengineer.menuhome;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sccengineer.ApprovalActivity;
import com.sccengineer.AttendanceActivity;
import com.sccengineer.ClockinApprovList;
import com.sccengineer.DailyReportList;
import com.sccengineer.LiveChatList;
import com.sccengineer.Location;
import com.sccengineer.MapsActivity;
import com.sccengineer.PmList;
import com.sccengineer.R;
import com.sccengineer.ServiceTicket;
import com.sccengineer.Settings;
import com.sccengineer.TabAct;
import com.sccengineer.approval.ApprovalAdapter;
import com.sccengineer.jadwal.JadwalAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.sccengineer.TabAct.positab;


public class MenuAdapter  extends RecyclerView.Adapter<MenuAdapter.Myviewholder> {
    private LinearLayoutManager linearLayoutManager;
    public static Integer counter = 0;
    public static Integer counter2 = 0;
    public static Integer counter3 = 0;
    public static Integer countSC = 0;
    public static Integer counterpm = 0;
    Context context;
    ArrayList<MenuItem> myItem;
    public static int positem = 0;
    public static RecyclerView mchatdialog;
    public MenuAdapter(Context c, ArrayList<MenuItem> p){
        context = c;
        myItem = p;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_menu,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        String namemenu = myItem.get(i).getMenuname();
        if (namemenu.equals("Assignment")){
            myviewholder.mdot.setVisibility(View.GONE);

        }
        if (namemenu.equals(context.getString(R.string.title_pmticket))){
            if (counterpm==0){
                myviewholder.mdot.setVisibility(View.GONE);
            }else {
                myviewholder.mdot.setVisibility(View.VISIBLE);
                myviewholder.mcount.setText(String.valueOf(counterpm));
            }


        }
        if (namemenu.equals("Chat With Support List")){
            if (countSC==0){
                myviewholder.mdot.setVisibility(View.GONE);
            }else {
                myviewholder.mdot.setVisibility(View.VISIBLE);
                myviewholder.mcount.setText(String.valueOf(countSC));
            }


        }
        if (namemenu.equals("Change Role Request")){
            if (counter==0){
                myviewholder.mdot.setVisibility(View.GONE);
            }else {
                myviewholder.mdot.setVisibility(View.VISIBLE);
                myviewholder.mcount.setText(String.valueOf(counter));
            }


        }
        if (namemenu.equals("Clockin Approval")){
            if (counter2==0){
                myviewholder.mdot.setVisibility(View.GONE);
            }else {
                myviewholder.mdot.setVisibility(View.VISIBLE);
                myviewholder.mcount.setText(String.valueOf(counter2));
            }
        }
        if (namemenu.equals("Live Chat List")){
            if (counter3==0){
                myviewholder.mdot.setVisibility(View.GONE);
            }else {
                myviewholder.mdot.setVisibility(View.VISIBLE);
                myviewholder.mcount.setText(String.valueOf(counter3));
            }
        }
        if (namemenu.equals("Attendance")){
            myviewholder.mdot.setVisibility(View.GONE);



        }
        if (namemenu.equals(context.getString(R.string.title_Setting))){
            myviewholder.mdot.setVisibility(View.GONE);


        }
        if (namemenu.equals(context.getString(R.string.title_dailyreport))){
            myviewholder.mdot.setVisibility(View.GONE);


        }
        int sd = R.drawable.ic_check;
        myviewholder.mnama_menu.setText(myItem.get(i).getMenuname());
        myviewholder.mimg_menu.setImageResource(myItem.get(i).getImg());
//        Picasso.with(context).load(myItem.get(i).getImg()).into(myviewholder.mimg_menu);
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                String namemenu = myItem.get(i).getMenuname();
                if (namemenu.equals("Chat With Support List")){
                    Intent gotonews = new Intent(context, LiveChatList.class);
                    gotonews.putExtra("home", "homes");
                    gotonews.putExtra("cs", "yes");
                    gotonews.putExtra("titlelist", "Chat With Support List");
//                    gotonews.putExtra("showaddform",showaddform);
                    context.startActivity(gotonews);
                    ((Activity)context).finish();
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                    Toast.makeText(context, "ticket", Toast.LENGTH_SHORT).show();
                }
                if (namemenu.equals("Live Chat List")){
//                    myviewholder.mdot.setVisibility(View.GONE);
                    Intent gotonews = new Intent(context, LiveChatList.class);
                    gotonews.putExtra("home", "homes");
                    gotonews.putExtra("cs", "no");
                    gotonews.putExtra("titlelist", "List Live Chat");
//                    gotonews.putExtra("showaddform",showaddform);
                    context.startActivity(gotonews);
                    ((Activity)context).finish();
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                    Toast.makeText(context, "ticket", Toast.LENGTH_SHORT).show();
                }
                if (namemenu.equals("Assignment")){
//                    myviewholder.mdot.setVisibility(View.GONE);
                    Intent gotonews = new Intent(context, ServiceTicket.class);
                    gotonews.putExtra("home", "homes");
//                    gotonews.putExtra("showaddform",showaddform);
                    context.startActivity(gotonews);
                    ((Activity)context).finish();
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                    Toast.makeText(context, "ticket", Toast.LENGTH_SHORT).show();
                }
                if (namemenu.equals("Change Role Request")){
//                    myviewholder.mdot.setVisibility(View.VISIBLE);
                    Intent gotonews = new Intent(context, ApprovalActivity.class);
                    gotonews.putExtra("home", "homes");
//                    gotonews.putExtra("showaddform",showaddform);
                    context.startActivity(gotonews);
                    ((Activity)context).finish();
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                    Toast.makeText(context, "ticket", Toast.LENGTH_SHORT).show();
                }
                if (namemenu.equals("Attendance")){
//                    myviewholder.mdot.setVisibility(View.GONE);
                    myviewholder.mcount.setText(String.valueOf(counter));
                    Intent gotonews = new Intent(context, AttendanceActivity.class);
//                    gotonews.putExtra("mshowPurchaseOrderPO",mshowPurchaseOrderPO);
//                    gotonews.putExtra("mshowPurchaseOrderPO",mshowPurchaseOrderFOC);
//                    gotonews.putExtra("showaddpo",showaddpo);
//                    gotonews.putExtra("showaddfoc",showaddfoc);
                    positab = 0;
                    context.startActivity(gotonews);
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    ((Activity)context).finish();
//                    Toast.makeText(context, "attendance", Toast.LENGTH_SHORT).show();


                }
                if (namemenu.equals(context.getString(R.string.title_dailyreport))){
                    Intent gotonews = new Intent(context, DailyReportList.class);
                    gotonews.putExtra("hide","no");
//                    gotonews.putExtra("mshowPurchaseOrderPO",mshowPurchaseOrderFOC);
//                    gotonews.putExtra("showaddpo",showaddpo);
//                    gotonews.putExtra("showaddfoc",showaddfoc);
                    positab = 0;
                    context.startActivity(gotonews);
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    ((Activity)context).finish();
//                    Toast.makeText(context, "attendanc

                }
                if (namemenu.equals(context.getString(R.string.title_Setting))){
//                    myviewholder.mdot.setVisibility(View.GONE);
                    Intent gotosurvey = new Intent(context, Settings.class);
                    context.startActivity(gotosurvey);
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    ((Activity)context).finish();
//                    Toast.makeText(context, "setting", Toast.LENGTH_SHORT).show();

                }
                if (namemenu.equals(context.getString(R.string.title_pmticket))){
                    myviewholder.mdot.setVisibility(View.GONE);
                    Intent gotonews = new Intent(context, PmList.class);
                    context.startActivity(gotonews);
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    ((Activity)context).finish();

                }
//                if (namemenu.equals(context.getString(R.string.title_News))){
//                    Intent gotonews = new Intent(context, CategoryNews.class);
//                    context.startActivity(gotonews);
//                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                    ((Activity)context).finish();
//
//                }
//                if (namemenu.equals(context.getString(R.string.title_live_chat))){
//                    if (installed) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//                        builder.setTitle("Live Chat:");
//                        // set the custom layout
//                        final View customLayout = LayoutInflater.from(context).inflate(R.layout.chatdialoghome, null);
//                        mchatdialog = (RecyclerView) customLayout.findViewById(R.id.chatdialog);
//                        linearLayoutManager = new LinearLayoutManager(context, LinearLayout.VERTICAL,false);
////        linearLayoutManager.setReverseLayout(true);
////        linearLayoutManager.setStackFromEnd(true);
//                        mchatdialog.setLayoutManager(linearLayoutManager);
//                        mchatdialog.setHasFixedSize(true);
//                        addFormAdapterAdapter = new ChatAdapter(context, list2);
//                        mchatdialog.setAdapter(addFormAdapterAdapter);
//                        builder.setView(customLayout);
//                        // add a button
//
//                        // create and show the alert dialog
//                        AlertDialog dialog = builder.create();
//                        dialog.show();
//                    }else {
//                        if (installed2){
//                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//                            builder.setTitle("Live Chat:");
//                            // set the custom layout
//                            final View customLayout = LayoutInflater.from(context).inflate(R.layout.chatdialoghome, null);
//                            mchatdialog = (RecyclerView) customLayout.findViewById(R.id.chatdialog);
//                            linearLayoutManager = new LinearLayoutManager(context, LinearLayout.VERTICAL,false);
////        linearLayoutManager.setReverseLayout(true);
////        linearLayoutManager.setStackFromEnd(true);
//                            mchatdialog.setLayoutManager(linearLayoutManager);
//                            mchatdialog.setHasFixedSize(true);
//                            addFormAdapterAdapter = new ChatAdapter(context, list2);
//                            mchatdialog.setAdapter(addFormAdapterAdapter);
//                            builder.setView(customLayout);
//                            // add a button
//
//                            // create and show the alert dialog
//                            AlertDialog dialog = builder.create();
//                            dialog.show();
//                        }else {
//                            Toast.makeText(context,"Whatsapp blum di instal", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//
//                }
                if (namemenu.equals("Clockin Approval")){
                    Intent gotosetting = new Intent(context, ClockinApprovList.class);
                    context.startActivity(gotosetting);
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    ((Activity)context).finish();

                }
//
            }
        });


    }

    @Override
    public int getItemCount() {
        return
                myItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView mnama_menu,mnews_new, mcount;
        ImageView mimg_menu;
        ProgressBar mporg;
        LinearLayout mdot;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mnama_menu = itemView.findViewById(R.id.namemenu);
            mimg_menu = itemView.findViewById(R.id.menuimg);
            mdot = itemView.findViewById(R.id.dot);
            mcount = itemView.findViewById(R.id.newnotif);

        }
    }

}
