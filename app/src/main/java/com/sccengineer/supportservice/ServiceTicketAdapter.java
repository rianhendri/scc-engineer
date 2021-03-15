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
package com.sccengineer.supportservice;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.sccengineer.DetailsFormActivity;
import com.sccengineer.DetailsST;
import com.sccengineer.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.sccengineer.ServiceTicket.valuefilter;

//import static com.smartcarecenter.FormActivity.valuefilter;

public class ServiceTicketAdapter
extends RecyclerView.Adapter<ServiceTicketAdapter.Myviewholder> {
    ArrayList<ServiceTicketItems> addFromItem;
    Context context;
    ImageView mimgpopup;

    public ServiceTicketAdapter(Context context, ArrayList<ServiceTicketItems> addFromItem) {
        this.context = context;
        this.addFromItem = addFromItem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_form,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        String newdate = "";
        if (addFromItem.get(i).getFormRequestPhotoThumbURL()!=null){
            Picasso.with(context).load(addFromItem.get(i).getFormRequestPhotoThumbURL()).into(myviewholder.xgambar_item);
        }else {
            Picasso.with(context).load(R.drawable.noimgg).into(myviewholder.xgambar_item);
        }
        myviewholder.mcdst.setText("#"+ addFromItem.get(i).getServiceTicketCd());
        if (addFromItem.get(i).getServiceTicketCd()!=null){
            myviewholder.mcdst.setText("#"+ addFromItem.get(i).getServiceTicketCd());
        }else {
            myviewholder.mcdst.setText("-");
        }
        if (addFromItem.get(i).getFormRequestCd()!=null){
            myviewholder.mfrnya.setText("FR: "+addFromItem.get(i).getFormRequestCd());
        }else {
            myviewholder.mfrnya.setText("FR: -");
        }
        myviewholder.mptnya.setText(addFromItem.get(i).getCustomerName());
        String oldadate = addFromItem.get(i).getAssignedDateTime();
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
        myviewholder.mdescrip.setText(addFromItem.get(i).getDescription());
        myviewholder.mpress.setText(addFromItem.get(i).getPressName());
        myviewholder.mdate.setText(separated[0]+" "+ separated[1]);
        myviewholder.massst.setText(addFromItem.get(i).getAssignmentStatusName());
        myviewholder.massst.setTextColor(Color.parseColor("#"+addFromItem.get(i).getAssignmentStatusColorCode()));
        myviewholder.mcurrentst.setText(addFromItem.get(i).getStatusName());
        myviewholder.mcurrentst.setTextColor(Color.parseColor("#"+addFromItem.get(i).getStatusColorCode()));
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsST.class);
                intent.putExtra("id", (addFromItem.get(i).getFormRequestCd()));
                intent.putExtra("home", "homesa");
                intent.putExtra("filter", valuefilter);
                intent.putExtra("noticket", (addFromItem.get(i)).getServiceTicketCd());
                intent.putExtra("pos", valuefilter);
                intent.putExtra("user", addFromItem.get(i).getCreatedBy());
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                ((Activity)context).finish();

            }
        });
        myviewholder.xgambar_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context, R.style.TransparentDialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.popupfoto);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                mimgpopup = dialog.findViewById(R.id.imagepopup);
                if (addFromItem.get(i).getFormRequestPhotoURL()!=null){
                    Picasso.with(context).load(addFromItem.get(i).getFormRequestPhotoURL()).into(mimgpopup);

                }else {
                    Picasso.with(context).load(R.drawable.noimgg).into(mimgpopup);

                }
//                Picasso.with(context).load(addFromItem.get(i).getFormRequestPhotoURL()).into(mimgpopup);
                dialog.show();
            }
        });
        if (addFromItem.get(i).isStsAssist()){
            myviewholder.massist.setVisibility(View.VISIBLE);
        }else {
            myviewholder.massist.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return addFromItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView mcdst, mdate, mfrnya, mptnya, mpress, mdescrip, massst, mcurrentst;
        LinearLayout massist;
        ImageView xgambar_item;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            xgambar_item = itemView.findViewById(R.id.xpic);
            mcdst = itemView.findViewById(R.id.cdst);
            mdate = itemView.findViewById(R.id.date);
            mfrnya = itemView.findViewById(R.id.frnya);
            mptnya = itemView.findViewById(R.id.ptnya);
            mpress = itemView.findViewById(R.id.press);
            mdescrip = itemView.findViewById(R.id.descrip);
            massst = itemView.findViewById(R.id.assst);
            mcurrentst = itemView.findViewById(R.id.currentst);
            massist = itemView.findViewById(R.id.assist);

        }
    }
    public interface CallBackUs {
        void addCartItemView();
    }
    // this interface creates for call the invalidateoptionmenu() for refresh the menu item
    public interface HomeCallBack {
        void updateCartCount(Context context);
    }
    public void clear() {
        addFromItem.clear();
        notifyDataSetChanged();
    }
    public void setAddFromItem(ArrayList<ServiceTicketItems> addFromItem)
    {
        for (ServiceTicketItems im : addFromItem)
        {
            addFromItem.add(im);
        }
        notifyDataSetChanged();

    }
}

