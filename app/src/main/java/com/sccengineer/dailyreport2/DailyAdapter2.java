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
package com.sccengineer.dailyreport2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sccengineer.DetailsDailyReport;
import com.sccengineer.DetailsDailyReport2;
import com.sccengineer.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.sccengineer.DailyReportList.enddate;
import static com.sccengineer.DailyReportList.hide;
import static com.sccengineer.DailyReportList.startdate;
import static com.sccengineer.DailyReportList.valuefilter;

//import static com.sccengineer.FormActivity.valuefilter;

public class DailyAdapter2
extends RecyclerView.Adapter<DailyAdapter2.Myviewholder> {
    ArrayList<DailyItem2> addFromItem;
    Context context;
    ImageView mimgpopup;
    public static String username = "";
    public static String valuefilter="";
    public static String noreq = "";
    public DailyAdapter2(Context context, ArrayList<DailyItem2> addFromItem) {
        this.context = context;
        this.addFromItem = addFromItem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_daily,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        String newdate = "";

        myviewholder.msttitledaily.setText(addFromItem.get(i).getServiceTicketCd());
        String oldadate = addFromItem.get(i).getReportDateTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        try {
            newdate = simpleDateFormat2.format(simpleDateFormat.parse(oldadate));
            System.out.println(newdate);
            Log.e((String)"Datexs", (String)newdate);
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
        }

        myviewholder.mdatedaily.setText(newdate);
        myviewholder.mpresstypedaily.setText(addFromItem.get(i).getPressSN()+" ("+addFromItem.get(i).getPressTypeName()+")");
        myviewholder.mpressstatudaily.setText(addFromItem.get(i).getPressStatusName());
        if (addFromItem.get(i).getPressStatusName().equals("Mesin Tetap Produksi")){
            myviewholder.mpressstatudaily.setTextColor(Color.parseColor("#0890cc"));
        }else {
            myviewholder.mpressstatudaily.setTextColor(Color.parseColor("#FF0000"));
        }
//        myviewholder.mpressstatudaily.setTextColor(Color.parseColor("#"+addFromItem.get(i).getStatusColorCode()));
        myviewholder.msndaily.setText(": "+addFromItem.get(i).getPressSN());
//        myviewholder.mcaseiddaily.setText(addFromItem.get(i).getCaseProgressName());
        myviewholder.mcaseprogressdaily.setText(addFromItem.get(i).getCaseProgressName());
//        if (addFromItem.get(i).isFlag()){
//            myviewholder.mdot.setVisibility(View.GONE);
//        }else {
//            myviewholder.mdot.setVisibility(View.GONE);
//        }
        myviewholder.mcustomername.setText(addFromItem.get(i).getCustomerName());
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsDailyReport2.class);
                intent.putExtra("home", "homesa");
                intent.putExtra("pos", valuefilter);
                intent.putExtra("noticket", noreq);
                intent.putExtra("pos", valuefilter);
                intent.putExtra("user", username);
//                intent.putExtra("scrolbawah", scrollnya);
//                intent.putExtra("xhori", xhori);
//                intent.putExtra("yverti", yverti);
                intent.putExtra("hide","yes");

                intent.putExtra("id", (addFromItem.get(i).getReportCd()));
                intent.putExtra("noticket", (addFromItem.get(i)).getServiceTicketCd());
                intent.putExtra("pos", valuefilter);
                intent.putExtra("user", addFromItem.get(i).getCustomerName());
                intent.putExtra("scrolbawah", "yes");
                intent.putExtra("startd", startdate);
                intent.putExtra("endd", enddate);
                intent.putExtra("hide", hide);
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                ((Activity)context).finish();

            }
        });

//        if (addFromItem.get(i).getAdditionalTextHtml()!=null){
//            myviewholder.mhtml.setVisibility(View.VISIBLE);
//            if (Build.VERSION.SDK_INT >= 24) {
//                myviewholder.mhtml.setText((CharSequence) Html.fromHtml((String)addFromItem.get(i).getAdditionalTextHtml(), Html.FROM_HTML_MODE_COMPACT));
//                myviewholder.mhtml.setMovementMethod(LinkMovementMethod.getInstance());
//            } else {
//                myviewholder.mhtml.setText((CharSequence)Html.fromHtml((String)addFromItem.get(i).getAdditionalTextHtml()));
//                myviewholder.mhtml.setMovementMethod(LinkMovementMethod.getInstance());
//
//            }
//        }else {
//            myviewholder.mhtml.setVisibility(View.GONE);
//        }

    }

    @Override
    public int getItemCount() {
        return addFromItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView mdatedaily, msttitledaily,mpresstypedaily,mpressstatudaily,mhtml,msndaily,mcaseiddaily,mcaseprogressdaily,mcustomername;
        ImageView mdot;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mdatedaily = itemView.findViewById(R.id.datedaily);
            msttitledaily = itemView.findViewById(R.id.sttitledaily);
            mpresstypedaily = itemView.findViewById(R.id.presstypedaily);
            mpressstatudaily = itemView.findViewById(R.id.pressstatusdaily);
            msndaily = itemView.findViewById(R.id.sndaily);
//            mcaseiddaily = itemView.findViewById(R.id.caseiddaily);
            mcaseprogressdaily = itemView.findViewById(R.id.caseprogressdaily);
            mhtml = itemView.findViewById(R.id.textcntn);
            mcustomername = itemView.findViewById(R.id.customername);
            mdot = itemView.findViewById(R.id.dot);

        }
    }
    public void clear() {
        addFromItem.clear();
        notifyDataSetChanged();
    }
    public void setAddFromItem(ArrayList<DailyItem2> addFromItem)
    {
        for (DailyItem2 im : addFromItem)
        {
            addFromItem.add(im);
        }
        notifyDataSetChanged();

    }
}

