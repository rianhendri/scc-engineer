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
package com.sccengineer.jadwal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sccengineer.AttendanceActivity;
import com.sccengineer.DetailsNotification;
import com.sccengineer.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.sccengineer.AttendanceActivity.posisinya;


public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.Myviewholder> {
    private LinearLayoutManager linearLayoutManager;
    Context context;
    ArrayList<JadwalItems> myItem;
    public static int positem = 0;
    public static RecyclerView mchatdialog;
    public JadwalAdapter(Context c, ArrayList<JadwalItems> p){
        context = c;
        myItem = p;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_jadwal,
                viewGroup, false));

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        String date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        SimpleDateFormat simpleDateFormatc = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateStr = myItem.get(i).getDate();
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat curFormaterA = new SimpleDateFormat("dd EEE");
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(dateStr);
            String newDateStr = curFormaterA.format(dateObj);
            myviewholder.mtgl.setText(newDateStr);
            Log.d("newdate",newDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        try {
            String date2 = simpleDateFormatc.format(simpleDateFormatc.parse(myItem.get(i).getDate()));
            Log.d("daSaa",date2);
            if (date1.equals(date2)){
                posisinya=myviewholder.getAdapterPosition();
                myviewholder.mbghariini.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_cornerblue));
                myviewholder.mhours.setTextColor(Color.parseColor("#ffffff"));
                myviewholder.mclockin.setTextColor(Color.parseColor("#ffffff"));
                myviewholder.mclockout.setTextColor(Color.parseColor("#ffffff"));
                myviewholder.mclockintext.setTextColor(Color.parseColor("#ffffff"));
                myviewholder.mclockouttext.setTextColor(Color.parseColor("#ffffff"));
                myviewholder.mhourstext.setTextColor(Color.parseColor("#ffffff"));


            }else {
                myviewholder.mbghariini.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_cornergrey2));
                myviewholder.mhours.setTextColor(Color.parseColor("#000000"));
                myviewholder.mclockin.setTextColor(Color.parseColor("#000000"));
                myviewholder.mclockout.setTextColor(Color.parseColor("#000000"));
                myviewholder.mclockintext.setTextColor(Color.parseColor("#000000"));
                myviewholder.mclockouttext.setTextColor(Color.parseColor("#000000"));
                myviewholder.mhourstext.setTextColor(Color.parseColor("#000000"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (myItem.get(i).isDataExist()){
            myviewholder.mlayot.setVisibility(View.VISIBLE);
            myviewholder.mnodata.setVisibility(View.GONE);

            myviewholder.mhours.setText(myItem.get(i).getTotalHoursText());

            String string7 = myItem.get(i).getClockIn();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            String string5 = "";
            String string6="";
            try {
                string6 = simpleDateFormat2.format(simpleDateFormat.parse(string7));
                string5 = simpleDateFormat.format(simpleDateFormat.parse(string7));
                String[] separated = string5.split("T");
                String time = separated[1];
                String date = separated[0];
                myviewholder.mclockin.setText(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (myItem.get(i).getClockOut()!=null){
                String string7a = myItem.get(i).getClockOut();
                String string7c = myItem.get(i).getClockIn();
                SimpleDateFormat simpleDateFormata = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());
                SimpleDateFormat simpleDateFormat2a = new SimpleDateFormat("dd", Locale.getDefault());
                SimpleDateFormat curFormaterAc = new SimpleDateFormat("dd");
                String string5a = "";
                String string6a="";
                Integer hariplus=0;
                Date dateObja = null;
                Date dateObjac = null;
                try {
                    dateObja = curFormater.parse(string7a);
                    String newDateStr = curFormaterAc.format(dateObja);
                    dateObjac = curFormater.parse(string7c);
                    String newDateStrc = curFormaterAc.format(dateObjac);
                    string5a = simpleDateFormata.format(simpleDateFormata.parse(string7a));
                    Integer hariini =  Integer.parseInt(newDateStr);
                    Integer harikemarin = Integer.parseInt(newDateStrc);
                    if (hariini>harikemarin){
                        hariplus = hariini-harikemarin;
                        Log.d("liat",String.valueOf(hariini)+"-"+String.valueOf(harikemarin));
                    }else {
                       hariplus=0;
                    }
                    String[] separated = string5a.split("T");
                    String timea = separated[1];
                    String date = separated[0];
                    if (hariplus==0){
                        myviewholder.mclockout.setText(timea);

                    }else {
                        myviewholder.mclockout.setText(timea+"("+"+"+String.valueOf(hariplus)+")");

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else {
                myviewholder.mclockout.setText("-");
            }

        }else {
            myviewholder.mlayot.setVisibility(View.GONE);
            myviewholder.mnodata.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return
                myItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView mtgl,mclockin,mclockintext,mclockout,mclockouttext,mhours,mhourstext,mnodata;
        ImageView mimg_menu;
        ProgressBar mporg;
        ImageView mdot;
        LinearLayout mlayot,mbghariini;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mtgl = itemView.findViewById(R.id.tanggal);
            mlayot = itemView.findViewById(R.id.layoutnya);
            mbghariini = itemView.findViewById(R.id.bghriini);
            mnodata = itemView.findViewById(R.id.nodata);
            mclockin = itemView.findViewById(R.id.clockinjadwal);
            mclockout = itemView.findViewById(R.id.clockoutjadwal);
            mhours = itemView.findViewById(R.id.hoursjadwal);
            mclockintext = itemView.findViewById(R.id.clockinjadwaltext);
            mclockouttext = itemView.findViewById(R.id.clockoutjadwaltext);
            mhourstext = itemView.findViewById(R.id.hoursjadwaltext);

        }
    }
}
