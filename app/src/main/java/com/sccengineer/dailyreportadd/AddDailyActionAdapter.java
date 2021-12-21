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
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sccengineer.BuildConfig;
import com.sccengineer.Login;
import com.sccengineer.R;
import com.sccengineer.SplashScreen;
import com.sccengineer.apihelper.IRetrofit;
import com.sccengineer.apihelper.ServiceGenerator;
import com.sccengineer.spartsend.SendSparepart_adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sccengineer.DailyReportAdd.addFormAdapterAdapter2;
import static com.sccengineer.DailyReportAdd.jsonaction;
import static com.sccengineer.DailyReportAdd.myaction;
import static com.sccengineer.DailyReportAdd.actionlist;
import static com.sccengineer.DailyReportAdd.place_action;
import static com.sccengineer.DetailsST.jsonarayitem;
import static com.sccengineer.DetailsST.msendpartlist;
import static com.sccengineer.DetailsST.myCustomArray;
import static com.sccengineer.DetailsST.sendsparepart_adapter;
import static com.sccengineer.DetailsST.sendsparepart_items;
import static com.sccengineer.apihelper.ServiceGenerator.baseurl;


public class AddDailyActionAdapter
extends RecyclerView.Adapter<AddDailyActionAdapter.Myviewholder> {
   public static ArrayList<AddDailyActionItem> addFromItem;
    public static Dialog dialogedit;
    EditText mketerangan;
    LinearLayout mupdateedit;
    int iii=0;
    String mtextnya = "";
    Context context;
    ImageView mimgpopup;
    public static boolean edit = true;
    public AddDailyActionAdapter(Context context, ArrayList<AddDailyActionItem> addFromItem) {
        this.context = context;
        this.addFromItem = addFromItem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_addaction,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        String newdate = "";
        myviewholder.minput.setText(addFromItem.get(i).getActionTaken());
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

                if (actionlist.size() >= 0) {
                    actionlist.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i, actionlist.size());
                    Gson gson = new GsonBuilder().create();
                    myaction = gson.toJsonTree(actionlist).getAsJsonArray();
                    jsonaction = myaction.toString();
                    Log.d("sizecart_22", String.valueOf(jsonaction));

                    for (int x = 0 ; x < actionlist.size(); x++) {


                    }

//                    if (actionlist.size()==0){
//                        place_action.setVisibility(View.GONE);
//                    }

                    place_action.setAdapter(addFormAdapterAdapter2);

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
                    String old = addFromItem.get(i).getActionTaken();
                    String Newny = addFromItem.get(i-1).getActionTaken();
                    addFromItem.get(i).setActionTaken(Newny);
                    addFromItem.get(i-1).setActionTaken(old);


                    Gson gson = new GsonBuilder().create();
                    myaction = gson.toJsonTree(actionlist).getAsJsonArray();
                    jsonaction = myaction.toString();
                    Log.d("rrre", String.valueOf(jsonaction));
                    Log.d("rrre2",old +"/"+Newny);
//                notifyItemMoved(i, i-1);
                    addFormAdapterAdapter2.notifyDataSetChanged();

                }


            }
        });
        myviewholder.mdowin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("posddsi",String.valueOf(i)+"jumlah : "+addFromItem.size());
                if (i==addFromItem.size()-1){

                }else {
                    String old = addFromItem.get(i).getActionTaken();
                    String Newny = addFromItem.get(i+1).getActionTaken();
                    addFromItem.get(i).setActionTaken(Newny);
                    addFromItem.get(i+1).setActionTaken(old);


                    Gson gson = new GsonBuilder().create();
                    myaction = gson.toJsonTree(actionlist).getAsJsonArray();
                    jsonaction = myaction.toString();
                    Log.d("rrre", String.valueOf(jsonaction));
                    Log.d("rrre2",old +"/"+Newny);
//                notifyItemMoved(i, i-1);
                    addFormAdapterAdapter2.notifyDataSetChanged();

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
                addFromItem.get(iii).setActionTaken(mketerangan.getText().toString());
                Gson gson = new GsonBuilder().create();
                myaction = gson.toJsonTree(actionlist).getAsJsonArray();
                jsonaction = myaction.toString();
                Log.d("rrre", String.valueOf(jsonaction));
                addFormAdapterAdapter2.notifyDataSetChanged();
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

