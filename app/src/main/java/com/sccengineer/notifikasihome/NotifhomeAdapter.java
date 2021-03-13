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
package com.sccengineer.notifikasihome;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
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

import com.sccengineer.DetailsNotification;
import com.sccengineer.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class NotifhomeAdapter extends RecyclerView.Adapter<NotifhomeAdapter.Myviewholder> {
    private LinearLayoutManager linearLayoutManager;
    Context context;
    ArrayList<NotifhomeItems> myItem;
    public static int positem = 0;
    public static RecyclerView mchatdialog;
    public NotifhomeAdapter(Context c, ArrayList<NotifhomeItems> p){
        context = c;
        myItem = p;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_notifhome,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        String oldadate = "";
        Typeface type = Typeface.createFromAsset(context.getAssets(),"font/segoeuib.ttf");
        Typeface type2 = Typeface.createFromAsset(context.getAssets(),"font/segoeui.ttf");

        if (!myItem.get(i).isStsRead()){
            myviewholder.mdot.setVisibility(View.VISIBLE);
            myviewholder.mdes.setTypeface(type);
            myviewholder.mnama_menu.setTypeface(type);
        }else {
            myviewholder.mdot.setVisibility(View.GONE);
            myviewholder.mdes.setTypeface(type2);
            myviewholder.mnama_menu.setTypeface(type2);
        }
        myviewholder.mnama_menu.setText(myItem.get(i).getTitle());
        myviewholder.mdes.setText(myItem.get(i).getContent());
        if (myItem.get(i).getPostedDateTime()!=null){
            String newdate = "";
            oldadate = myItem.get(i).getPostedDateTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            try {
                newdate = simpleDateFormat.format(simpleDateFormat.parse(oldadate));
                System.out.println(newdate);
                Log.e((String)"Date", (String)newdate);
            }
            catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            String[] separated = newdate.split("T");
            separated[0].trim();; // this will contain "Fruit"
            separated[1].trim();;
            myviewholder.mstgl.setText(separated[0]+" "+ separated[1]);
        }else {

        }

        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsNotification.class);
                intent.putExtra("home", "homes");
                intent.putExtra("id", (myItem.get(i)).getGuid());
                intent.putExtra("username", (myItem.get(i)).getTitle());
                intent.putExtra("guid", (myItem.get(i)).getGuid());
                intent.putExtra("title", (myItem.get(i)).getTitle());
                intent.putExtra("body", (myItem.get(i)).getContent());
                context.startActivity(intent);
                ((Activity)context).finish();
                ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

//        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("WrongConstant")
//            @Override
//            public void onClick(View v) {
//                String namemenu = myItem.get(i).getMenuname();
//                if (namemenu.equals("Service Ticket")){
////                    Intent gotonews = new Intent(context, FormActivity.class);
////                    gotonews.putExtra("showaddform",showaddform);
////                    context.startActivity(gotonews);
////                    ((Activity)context).finish();
////                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                    Toast.makeText(context, "ticket", Toast.LENGTH_SHORT).show();
//                }
//                if (namemenu.equals("Attendance")){
////                    Intent gotonews = new Intent(context, PurchaseMenu.class);
////                    gotonews.putExtra("mshowPurchaseOrderPO",mshowPurchaseOrderPO);
////                    gotonews.putExtra("mshowPurchaseOrderPO",mshowPurchaseOrderFOC);
////                    gotonews.putExtra("showaddpo",showaddpo);
////                    gotonews.putExtra("showaddfoc",showaddfoc);
////                    context.startActivity(gotonews);
////                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
////                    ((Activity)context).finish();
//                    Toast.makeText(context, "attendance", Toast.LENGTH_SHORT).show();
//
//
//                }
//                if (namemenu.equals("Settings")){
////                    Intent gotosurvey = new Intent(context, SurveyList_Activity.class);
////                    context.startActivity(gotosurvey);
////                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
////                    ((Activity)context).finish();
//                    Toast.makeText(context, "setting", Toast.LENGTH_SHORT).show();
//
//                }
////                if (namemenu.equals(context.getString(R.string.title_News))){
////                    Intent gotonews = new Intent(context, CategoryNews.class);
////                    context.startActivity(gotonews);
////                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
////                    ((Activity)context).finish();
////
////                }
////                if (namemenu.equals(context.getString(R.string.title_live_chat))){
////                    if (installed) {
////                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
////                        builder.setTitle("Live Chat:");
////                        // set the custom layout
////                        final View customLayout = LayoutInflater.from(context).inflate(R.layout.chatdialoghome, null);
////                        mchatdialog = (RecyclerView) customLayout.findViewById(R.id.chatdialog);
////                        linearLayoutManager = new LinearLayoutManager(context, LinearLayout.VERTICAL,false);
//////        linearLayoutManager.setReverseLayout(true);
//////        linearLayoutManager.setStackFromEnd(true);
////                        mchatdialog.setLayoutManager(linearLayoutManager);
////                        mchatdialog.setHasFixedSize(true);
////                        addFormAdapterAdapter = new ChatAdapter(context, list2);
////                        mchatdialog.setAdapter(addFormAdapterAdapter);
////                        builder.setView(customLayout);
////                        // add a button
////
////                        // create and show the alert dialog
////                        AlertDialog dialog = builder.create();
////                        dialog.show();
////                    }else {
////                        if (installed2){
////                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
////                            builder.setTitle("Live Chat:");
////                            // set the custom layout
////                            final View customLayout = LayoutInflater.from(context).inflate(R.layout.chatdialoghome, null);
////                            mchatdialog = (RecyclerView) customLayout.findViewById(R.id.chatdialog);
////                            linearLayoutManager = new LinearLayoutManager(context, LinearLayout.VERTICAL,false);
//////        linearLayoutManager.setReverseLayout(true);
//////        linearLayoutManager.setStackFromEnd(true);
////                            mchatdialog.setLayoutManager(linearLayoutManager);
////                            mchatdialog.setHasFixedSize(true);
////                            addFormAdapterAdapter = new ChatAdapter(context, list2);
////                            mchatdialog.setAdapter(addFormAdapterAdapter);
////                            builder.setView(customLayout);
////                            // add a button
////
////                            // create and show the alert dialog
////                            AlertDialog dialog = builder.create();
////                            dialog.show();
////                        }else {
////                            Toast.makeText(context,"Whatsapp blum di instal", Toast.LENGTH_SHORT).show();
////                        }
////
////                    }
////
////                }
////                if (namemenu.equals(context.getString(R.string.title_Setting))){
////                    Intent gotosetting = new Intent(context, SettingActivity.class);
////                    context.startActivity(gotosetting);
////                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
////                    ((Activity)context).finish();
////
////                }
////
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return
                myItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView mnama_menu,mstgl,mdes;
        ImageView mimg_menu;
        ProgressBar mporg;
        ImageView mdot;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mnama_menu = itemView.findViewById(R.id.namemenu);
            mstgl = itemView.findViewById(R.id.stgl);
            mdes = itemView.findViewById(R.id.des);
            mdot = itemView.findViewById(R.id.readnotifhome);

        }
    }
}
