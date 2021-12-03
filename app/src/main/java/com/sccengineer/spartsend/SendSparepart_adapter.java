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
package com.sccengineer.spartsend;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sccengineer.DetailsST;
import com.sccengineer.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.sccengineer.DetailsST.dialog;
import static com.sccengineer.DetailsST.jsonarayitem;
import static com.sccengineer.DetailsST.mpartlist;
import static com.sccengineer.DetailsST.msendpartlist;
import static com.sccengineer.DetailsST.myCustomArray;
import static com.sccengineer.DetailsST.sendsparepart_adapter;
import static com.sccengineer.DetailsST.sendsparepart_items;


public class SendSparepart_adapter
extends RecyclerView.Adapter<SendSparepart_adapter.Myviewholder> {
    ArrayList<SendSparepart_item> addFoclistitem;
    Context context;
    ImageView mimgpopup;
    String namaitemedit="";
    boolean dateedit = true;
    boolean stsedit = true;
    String  statuspar = "";
    String statuscolor="";
    String installdateedit="";
    String orderdatedit="";
    String caseidedit="";
    String reasonedit="";
    int qtyedit=0;
    Integer nonreserv=null;
    Integer qtystc=null;
     Dialog dialogedit;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
    TextView mnamesparedit,mqtysperedit2,mcaseidedit2,mreasonedit2,morderdateedit,minstalldateedit, mstatusedit,mstockres;
    LinearLayout minstalldateedit2;
    final Calendar myCalendar = Calendar.getInstance();
    int posedit=0;
    EditText mqtysperedit,mreasonedit,mcaseidedit;
    LinearLayout mupdateeditedit;
    boolean stsS = true;
    boolean usingMatrix = true;
    public SendSparepart_adapter(Context context, ArrayList<SendSparepart_item> addFoclistitem) {
        this.context = context;
        this.addFoclistitem = addFoclistitem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_sparepart2b,
                viewGroup, false));

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        if (addFoclistitem.get(i).getStatusName().equals("-")){
            myviewholder.mstatussper.setText("-");
        }else {
            myviewholder.mstatussper.setText(addFoclistitem.get(i).getStatusName());
            myviewholder.mstatussper.setTextColor(Color.parseColor("#"+addFoclistitem.get(i).getStatusTextColor()));
        }
        if (addFoclistitem.get(i).isStsAllowDelete()){
            myviewholder.mdelete2.setVisibility(View.GONE);
            myviewholder.mdelete.setVisibility(View.VISIBLE);
        }else {
            myviewholder.mdelete.setVisibility(View.GONE);
            myviewholder.mdelete2.setVisibility(View.VISIBLE);
        }
        if (addFoclistitem.get(i).isStsAllowEdit()){
            myviewholder.mlayoutsperpart.setBackgroundColor(Color.parseColor("#ffffff"));
        }else {
            myviewholder.mlayoutsperpart.setBackgroundColor(Color.parseColor("#FFEFEFEF"));
        }

        if (addFoclistitem.get(i).isStsAllowUpdateInstallDate()){
            myviewholder.minstalldate.setBackground(ContextCompat.getDrawable(context,R.drawable.underline));
        }else {
            myviewholder.minstalldate.setBackgroundColor(android.R.color.transparent);
            myviewholder.minstalldate.setTextColor(Color.parseColor("#6A6A6A"));

        }
        if (addFoclistitem.get(i).getSparePartName()!=null){
            addFoclistitem.get(i).setName(addFoclistitem.get(i).getSparePartName());
        }
        if (addFoclistitem.get(i).getSparePartCd().equals("")){
            String s = "<b>"+addFoclistitem.get(i).getManualSparePartCd()+"</b>"+" ("+addFoclistitem.get(i).getManualSparePartName()+")";
            myviewholder.mnamespar.setText(Html.fromHtml(s));
        }else {
            String s = "<b>"+addFoclistitem.get(i).getSparePartCd()+"</b>"+" ("+addFoclistitem.get(i).getName()+")";
            myviewholder.mnamespar.setText(Html.fromHtml(s));
        }
        myviewholder.mno.setText(String.valueOf(i+1));
        myviewholder.mcd.setText(addFoclistitem.get(i).getSparePartCd());
        if (addFoclistitem.get(i).getOrderDate()==null){
            myviewholder.morderdate.setText("-");
        }else {
            SimpleDateFormat datefor = new SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault());
            String estima = addFoclistitem.get(i).getOrderDate();
            String estimadate = "";
            try {

                estimadate = datefor.format(simpleDateFormat.parse(estima));
                System.out.println(estimadate);
                Log.e((String)"Date", (String)estimadate);
                myviewholder.morderdate.setText(estimadate);
            }
            catch (ParseException parseException) {
                parseException.printStackTrace();
            }

        }
        if (addFoclistitem.get(i).getInstallDate()==null){
            myviewholder.minstalldate.setText("-");
        }else {
            if (addFoclistitem.get(i).getInstallDate().length()<19){
                myviewholder.minstalldate.setText(addFoclistitem.get(i).getInstallDate());
            }else {
                SimpleDateFormat datefor = new SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault());
                String estima = addFoclistitem.get(i).getInstallDate();
                String estimadate2 = "";
                try {

                    estimadate2 = datefor.format(simpleDateFormat.parse(estima));
                    System.out.println(estimadate2);
                    Log.e((String)"Date", (String)estimadate2);
                    myviewholder.minstalldate.setText(estimadate2);
                }
                catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }




        }

        myviewholder.mcaseid.setText(addFoclistitem.get(i).getCaseID());
        myviewholder.mreason.setText(addFoclistitem.get(i).getReason());
        myviewholder.mqtysper.setText(String.valueOf(addFoclistitem.get(i).getQuantity()));
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nonreserv = addFoclistitem.get(i).getNonReservedStock();
                qtystc = addFoclistitem.get(i).getQuantity();
//                Toast.makeText(context, String.valueOf(nonreserv), Toast.LENGTH_SHORT).show();

                stsedit = addFoclistitem.get(i).isStsAllowEdit();
                if (addFoclistitem.get(i).getStatusName().equals("-")){
                        statuspar="-";
                }else {
                    statuspar=addFoclistitem.get(i).getStatusName();
                    statuscolor=addFoclistitem.get(i).getStatusTextColor();
                }

                dateedit = addFoclistitem.get(i).isStsAllowUpdateInstallDate();
                if (addFoclistitem.get(i).getSparePartCd().equals("")){
                    namaitemedit = "<b>"+addFoclistitem.get(i).getManualSparePartCd()+"</b>"+" ("+addFoclistitem.get(i).getManualSparePartName()+")";

                }else {
                    namaitemedit = "<b>"+addFoclistitem.get(i).getSparePartCd()+"</b>"+" ("+addFoclistitem.get(i).getName()+")";

                }
                qtyedit=addFoclistitem.get(i).getQuantity();
                reasonedit=addFoclistitem.get(i).getReason();
                caseidedit=addFoclistitem.get(i).getCaseID();
                installdateedit=addFoclistitem.get(i).getInstallDate();
                orderdatedit = addFoclistitem.get(i).getOrderDate();
                posedit = i;
//                msparenaem.setText(addFoclistitem.get(i).getName());
//                dialog.dismiss();
                if (addFoclistitem.get(i).isStsAllowEdit()){
                    dialogspar();
                }
//                if (addFoclistitem.get(i).isStsAllowUpdateInstallDate()){
//
//                }
            }
        });
        myviewholder.mdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sendsparepart_items.size() >= 0) {
                    sendsparepart_items.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i, sendsparepart_items.size());
                    Gson gson = new GsonBuilder().create();
                    myCustomArray = gson.toJsonTree(sendsparepart_items).getAsJsonArray();
                    jsonarayitem = myCustomArray.toString();
                    Log.d("sizecart_22", String.valueOf(jsonarayitem));
//                    totalqty = 0;
//                    totalprice = 0.0;
                    for (int x = 0 ; x < sendsparepart_items.size(); x++) {


                    }
//                    mtotalitem.setText(String.valueOf(addFoclistreq.size()));
//                    grandTotalplus = 0;
//                    intSum = 0;
//                    for (int i = 0; i < list.size(); i++) {
//                        grandTotalplus = grandTotalplus + list.get(i).getTotal();
//                    }
                    if (sendsparepart_items.size()==0){
                        msendpartlist.setVisibility(View.GONE);
                    }



                }else {
//
                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return addFoclistitem.size();
    }

    public void filterList(ArrayList<SendSparepart_item> filteredList) {
        addFoclistitem = filteredList;
        notifyDataSetChanged();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder{
        LinearLayout mlayoutsperpart;
        TextView mnamespar;
        ImageView mdelete,mdelete2;
        TextView mno,mcd,morderdate,minstalldate,mstockres;
        TextView mqtysper,mreason,mcaseid,mstatussper;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mnamespar = itemView.findViewById(R.id.namespar);

            mlayoutsperpart = itemView.findViewById(R.id.layoutsperpart);
            mdelete = itemView.findViewById(R.id.deletelist);
            mdelete2 = itemView.findViewById(R.id.deletelist2);
            mstatussper = itemView.findViewById(R.id.statussper);
            mno = itemView.findViewById(R.id.nospart);
            mcd = itemView.findViewById(R.id.codecd);
            morderdate = itemView.findViewById(R.id.orderdate);
            minstalldate = itemView.findViewById(R.id.installdate);
            mqtysper = itemView.findViewById(R.id.qtysper);
            mreason = itemView.findViewById(R.id.reason);
            mcaseid = itemView.findViewById(R.id.caseid);
        }
    }
    @SuppressLint("WrongConstant")
    public void dialogspar(){
        dialogedit = new Dialog(   context);
        dialogedit.setContentView(R.layout.dialogspar2);
//        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
//        msearch = dialog.findViewById(R.id.searchitem);
//        mpartlist = dialog.findViewById(R.id.listadditemfoc);
        mnamesparedit = dialogedit.findViewById(R.id.namespar);
//        mqtysperedit2 = dialogedit.findViewById(R.id.qtysper2);
        mqtysperedit = dialogedit.findViewById(R.id.qtysper);
        mreasonedit = dialogedit.findViewById(R.id.reason);
//        mreasonedit2 = dialogedit.findViewById(R.id.reason2);
        mcaseidedit = dialogedit.findViewById(R.id.caseid);
//        mcaseidedit2 = dialogedit.findViewById(R.id.caseid2);
        morderdateedit = dialogedit.findViewById(R.id.orderdate);
        minstalldateedit = dialogedit.findViewById(R.id.installdate);
        minstalldateedit2 = dialogedit.findViewById(R.id.installdate2);
        mupdateeditedit = dialogedit.findViewById(R.id.updateedit);
        mstatusedit = dialogedit.findViewById(R.id.statusspernya);
        mstockres = dialogedit.findViewById(R.id.stockres);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
//        Toast.makeText(context, String.valueOf(nonreserv), Toast.LENGTH_SHORT).show();
        if (nonreserv==null){
            mstockres.setText("-");
        }else {
            if (nonreserv<qtystc){
                mstockres.setText("Order");
            }else {
                if (nonreserv>qtystc){
                    mstockres.setText("Reserve");
                }
            }

        }
        minstalldateedit2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        if (stsedit){
            mqtysperedit.setEnabled(true);
//            mreasonedit.setEnabled(true);
//            mcaseidedit.setEnabled(true);
//            mqtysperedit2.setVisibility(View.GONE);
//            mqtysperedit.setVisibility(View.VISIBLE);
//
//            mreasonedit2.setVisibility(View.GONE);
//            mreasonedit.setVisibility(View.VISIBLE);
//
//            mcaseidedit2.setVisibility(View.GONE);
//            mcaseidedit.setVisibility(View.VISIBLE);

        }else {
            mqtysperedit.setEnabled(false);
//            mreasonedit.setEnabled(false);
//            mcaseidedit.setEnabled(false);

//            mqtysperedit2.setVisibility(View.VISIBLE);
//            mqtysperedit2.setTextColor(Color.parseColor("#6A6A6A"));
//            mqtysperedit2.setText(String.valueOf(qtyedit));
//            mqtysperedit.setVisibility(View.GONE);
//
//            mreasonedit2.setVisibility(View.VISIBLE);
//            mreasonedit2.setTextColor(Color.parseColor("#6A6A6A"));
//            mreasonedit2.setText(reasonedit);
//            mreasonedit.setVisibility(View.GONE);
//
//            mcaseidedit2.setVisibility(View.VISIBLE);
//            mcaseidedit2.setTextColor(Color.parseColor("#6A6A6A"));
//
//            mcaseidedit2.setText(reasonedit);
//            mcaseidedit.setVisibility(View.GONE);

        }if (dateedit){
//            minstalldateedit.setBackground(ContextCompat.getDrawable(context,R.drawable.underline));
        }else {
            minstalldateedit2.setEnabled(false);
        }
        mnamesparedit.setText(Html.fromHtml(namaitemedit));
//        mnamesparedit.setText(namaitemedit);
        mqtysperedit.setText(String.valueOf(qtyedit));
        mcaseidedit.setText(caseidedit);
        mreasonedit.setText(reasonedit);
        if (statuspar.equals("-")){
            mstatusedit.setText("-");
        }else {
            mstatusedit.setText(statuspar);
            mstatusedit.setTextColor(Color.parseColor("#"+statuscolor));
        }

        if (orderdatedit==null){
            morderdateedit.setText("-");
        }else {
            SimpleDateFormat datefor = new SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault());
            String estima = orderdatedit;
            String estimadate = "";
            try {

                estimadate = datefor.format(simpleDateFormat.parse(estima));
                System.out.println(estimadate);
                Log.e((String)"Date", (String)estimadate);
//                myviewholder.morderdate.setText(estimadate);
                morderdateedit.setText(estimadate);
            }
            catch (ParseException parseException) {
                parseException.printStackTrace();
            }
//            morderdateedit.setText(orderdatedit);
        }
        if (installdateedit==null){
            minstalldateedit.setText("-");
        }else {
            if (installdateedit.length()<19){
                minstalldateedit.setText(installdateedit);
//                myviewholder.minstalldate.setText(addFoclistitem.get(i).getInstallDate());
            }else {
                SimpleDateFormat datefor = new SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault());
                String estima = installdateedit;
                String estimadate2 = "";
                try {

                    estimadate2 = datefor.format(simpleDateFormat.parse(estima));
                    System.out.println(estimadate2);
                    Log.e((String)"Date", (String)estimadate2);
                    minstalldateedit.setText(estimadate2);
                }
                catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }

        }



        mqtysperedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mqtysperedit.length()>0){
                    if (nonreserv==null){
                        mstockres.setText("-");
                    }else {
                        if (nonreserv<Integer.parseInt(mqtysperedit.getText().toString())){
                            mstockres.setText("Order");
//                            Toast.makeText(context, String.valueOf(nonreserv<Integer.parseInt(mqtysperedit.getText().toString())), Toast.LENGTH_SHORT).show();
                        }else {
                            if (nonreserv>Integer.parseInt(mqtysperedit.getText().toString())){
                                mstockres.setText("Reserve");
//                                Toast.makeText(context, String.valueOf(nonreserv<Integer.parseInt(mqtysperedit.getText().toString())), Toast.LENGTH_SHORT).show();

                            }
                        }

                    }
                    if (Integer.parseInt(mqtysperedit.getText().toString())>99){
                        mqtysperedit.setText("99");
                        Toast.makeText(context, "Qty maksimal 99", Toast.LENGTH_SHORT).show();
                    }else {

                    }
                }



            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1 && s.toString().startsWith("0")) {
                    s.clear();
                }
            }
        });
        mupdateeditedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mqtysperedit.length()==0){
                    Toast.makeText(context, "Qty tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else {
                    if (mqtysperedit.getText().toString().equals("0")){
                        Toast.makeText(context, "Qty tidak boleh 0", Toast.LENGTH_SHORT).show();
                    }else {
                        if (mreasonedit.length()==0){
                            Toast.makeText(context, "Reason tidak boleh kosong", Toast.LENGTH_SHORT).show();

                        }else {
                            addFoclistitem.get(posedit).setQuantity(Integer.parseInt(mqtysperedit.getText().toString()));
                            addFoclistitem.get(posedit).setReason(mreasonedit.getText().toString());
                            addFoclistitem.get(posedit).setCaseID(mcaseidedit.getText().toString());
                            if (minstalldateedit.getText().toString().equals("-")) {
                                addFoclistitem.get(posedit).setInstallDate(null);
                            } else {
                                if (dateedit) {
                                    addFoclistitem.get(posedit).setInstallDate(minstalldateedit.getText().toString());
                                } else {

                                }

                            }


                            sendsparepart_adapter = new SendSparepart_adapter(context, sendsparepart_items);
                            msendpartlist.setAdapter(sendsparepart_adapter);
                            Gson gson = new GsonBuilder().create();
                            myCustomArray = gson.toJsonTree(sendsparepart_items).getAsJsonArray();
                            jsonarayitem = myCustomArray.toString();
                            dialogedit.dismiss();
                            Log.d("mycustomarray",myCustomArray.toString());
                        }

                    }

//                    if (mcaseidedit.length()==0){
//                        Toast.makeText(context, "CaseID tidak boleh kosong", Toast.LENGTH_SHORT).show();
//
//                    }else {
//
////                            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });
        dialogedit.show();
    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd "; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        minstalldateedit.setText(sdf.format(myCalendar.getTime()));
    }
}

