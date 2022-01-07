package com.sccengineer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sccengineer.Add_Po_Rewuest_View.Add_po_req_adapterView;
import com.sccengineer.Add_Po_Rewuest_View.Add_po_req_itemView;
import com.sccengineer.Chat.Adapterchat;
import com.sccengineer.Chat.Itemchat;
import com.sccengineer.apihelper.IRetrofit;
import com.sccengineer.apihelper.ServiceGenerator;

import java.io.File;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.sccengineer.apihelper.ServiceGenerator.baseurl;

public class DetailsOrderCharge extends AppCompatActivity {
    FirebaseAuth mAuth;
    LinearLayout mdot;
    TextView mnotif;
    int total1 = 0;
    public static String name="";
    LinearLayout mchactclik;
    String tokennya = "-";
    ArrayList<Itemchat> itemchat;
    Itemchat itemchat2;
    DatabaseReference databaseReference5;
    Adapterchat adapterchat;
    private static final int PERMISSION_CODE = 1000;
    String linkPo = "";
    boolean showprep = true;
    String colortextrep = "";
    String textprep="";
    String bgprep = "";
    public static JsonArray listsn;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    public static String matrixlabel = "";
    String akunid = "";
    double tax = 0.0;
    String taxname = "";
    Boolean internet = false;
    boolean installed= true;
    ProgressDialog loading;
    LinearLayout mback,mbgalert, mlayinvpo, mlaypo,mlayinv;
    public static LinearLayout mlaytotal;
    public static TextView mdate,mstartimpresi,moperator,mno_order,mtotalitem,msend,mtotalqty,
            mnoitem,mpono,mstatus, mtotalprice,mtax,mgrandtotal,mtitle,mlabeltax, mnotes;
    String grandtotal="";
    String nopo = "";
    String mpressId = "";
    String mpressId2 = "";
    Integer previmpressvlaue = 100;
    LinearLayout madd_item,mchat, mcopy;
    TextView msn,mtextalert, mdeskrip, mlinktext, mpayment,mpayment2;
    DatabaseReference reference;
    public static RecyclerView mlistitem_foc;
    String sesionid_new = "";
    List<String> snid = new ArrayList();
    List<String> snname = new ArrayList();
    List<Integer> previmpression = new ArrayList();
    //list item add
    public static ArrayList<Add_po_req_itemView> reitem;
    Add_po_req_adapterView req_adapter;
    private LinearLayoutManager linearLayoutManager;
    String jsonarayitem = "";
    JsonArray myCustomArray;
    String noOrder="";
    String pressid= "";
    Gson gson;
    String guid = "";
    String username = "";
    String sessionnya="";
    boolean chatin = false;
    String viewdetails="";
    String titlenya = "";
    public  static String mcustname="";
    String titlelist = "";
    public  static String mformRequestCd = "";
    String modultrans="";
    public static String Nowpo = "0";
    private ClipboardManager myClipboard;
    private ClipData myClip;
    Boolean showlinkdownload = true;
    String mmustUpload = "yes";
    String chats = "";
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_order_charge);
        mpayment = findViewById(R.id.payment);
        mchactclik = findViewById(R.id.chatclik);
        mdot = findViewById(R.id.dot);
        mnotif = findViewById(R.id.newnotif);
        mpayment2 = findViewById(R.id.payment2);
        mlistitem_foc = findViewById(R.id.listitemfoc);
        mdate = findViewById(R.id.datefoc);
        mno_order = findViewById(R.id.noorder);
        mnoitem = findViewById(R.id.noitem);
        msend = findViewById(R.id.submit);
        msn = findViewById(R.id.sn);
        moperator = findViewById(R.id.operator);
        mpono = findViewById(R.id.pono);
        mstartimpresi = findViewById(R.id.startimpresi);
        mback = findViewById(R.id.backbtn);
        mtotalitem = findViewById(R.id.totalitempo);
        mtotalqty = findViewById(R.id.totalqtypo);
        mlaytotal = findViewById(R.id.totallay);
        madd_item = findViewById(R.id.btnadditem_po);
        mstatus = findViewById(R.id.status);
        mchat = findViewById(R.id.chatcspo);
        mtotalprice = findViewById(R.id.totalpricepo);
        mtax =findViewById(R.id.totaltax);
        mgrandtotal = findViewById(R.id.grantotalpo);
        mtitle = findViewById(R.id.title);
        mlabeltax = findViewById(R.id.labeltax);
        mnotes = findViewById(R.id.mnotes);
        mbgalert = findViewById(R.id.backgroundalert);
        mtextalert = findViewById(R.id.textalert);
        mlayinvpo = findViewById(R.id.laypoinv);
        mlayinv = findViewById(R.id.downloadinvoice);
        mlaypo = findViewById(R.id.downloadpo);
        mdeskrip = findViewById(R.id.descrip);
        mcopy =findViewById(R.id.copylink);
        mlinktext = findViewById(R.id.textlink);
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {

            noOrder = bundle2.getString("id");
            guid = bundle2.getString("guid");
//            valuefilter = bundle2.getString("pos");
            username = bundle2.getString("username");

            if (bundle2.getString("chats")!=null){
                chats = "yes";
            }else {
                chats = "no";
            }
            if (bundle2.getString("chat")!=null){
                chatin = bundle2.getBoolean("chat");
            }else {
//                chats = false;
            }
            chatin = bundle2.getBoolean("chat");
            if (bundle2.getString("sessionnya")!=null){
                sessionnya = bundle2.getString("sessionnya");
            }else {
//                chats = false;
            }
            titlelist=bundle2.getString("titlelist");
            modultrans = bundle2.getString("id");
            viewdetails = bundle2.getString("viewdetails");
//            noreq = bundle2.getString("id");
//            home = bundle2.getString("home");
            guid = bundle2.getString("guid");
            username = bundle2.getString("user");
//            noticket = bundle2.getString("id");
//            valuefilter = bundle2.getString("pos");
//            scrollnya =   bundle2.getString("scrolbawah");
//            xhori=bundle2.getInt("xhori");
//            yverti=bundle2.getInt("yverti");
            titlenya=bundle2.getString("titlenya");
            noOrder = bundle2.getString("id");
//            valuefilter = bundle2.getString("pos");
            guid = bundle2.getString("guid");
//            username = bundle2.getString("username");
            mmustUpload = bundle2.getString("pdfyes");
        }
        mtitle.setText("View Chargeable #"+noOrder);
        cekInternet();
        getSessionId();
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(DetailsOrderCharge.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        mlistitem_foc.setLayoutManager(linearLayoutManager);
        mlistitem_foc.setHasFixedSize(true);
        reitem = new ArrayList<Add_po_req_itemView>();
        if (internet){
//            LoadPress();
            LoadData();
//            prepform();
            if (guid==null){

            }else {
//                ReadNotif();
            }
        }else {

        }

        mlaypo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestImage();
            }
        });
//        msend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cekInternet();
//                if (internet){
//                    showDialog();
//                }else {
//
//                }
//            }
//        });
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    onBackPressed();


            }
        });

        mcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String text;
                text = mlinktext.getText().toString();

                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);

                Toast.makeText(getApplicationContext(), "Link Copied",Toast.LENGTH_SHORT).show();
            }
        });
//        mpayment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                noOrder = bundle2.getString("id");
//                valuefilter = bundle2.getString("pos");
//                guid = bundle2.getString("guid");
//                username = bundle2.getString("username");
//                mmustUpload = bundle2.getString("pdfyes");
//                Intent gotoaddfoc = new Intent(AddDetailsPoView.this, PaymentAct.class);
//                gotoaddfoc.putExtra("grandtotal",grandtotal);
//                gotoaddfoc.putExtra("id",noOrder);
//                gotoaddfoc.putExtra("guid",guid);
//                gotoaddfoc.putExtra("username",username);
//                gotoaddfoc.putExtra("pdfyes",mmustUpload);
//                gotoaddfoc.putExtra("pos",valuefilter);
//                gotoaddfoc.putExtra("nopo",nopo);
//                gotoaddfoc.putExtra("items",myCustomArray.toString());
//                startActivity(gotoaddfoc);
//                overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                finish();
//                SharedPreferences sharedPreferences = getSharedPreferences("ITEMS_CART", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("items_cart", myCustomArray.toString());
//                editor.apply();
//            }
//        });
//        mpayment2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                noOrder = bundle2.getString("id");
//                valuefilter = bundle2.getString("pos");
//                guid = bundle2.getString("guid");
//                username = bundle2.getString("username");
//                mmustUpload = bundle2.getString("pdfyes");
//                Intent gotoaddfoc = new Intent(DetailsOrderCharge.this, HistoryPayment.class);
//                gotoaddfoc.putExtra("grandtotal",grandtotal);
//                gotoaddfoc.putExtra("id",noOrder);
//                gotoaddfoc.putExtra("guid",guid);
//                gotoaddfoc.putExtra("username",username);
//                gotoaddfoc.putExtra("pdfyes",mmustUpload);
//                gotoaddfoc.putExtra("pos",valuefilter);
//                gotoaddfoc.putExtra("nopo",nopo);
//                startActivity(gotoaddfoc);
//                overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                finish();
//            }
//        });
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) DetailsOrderCharge.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(DetailsOrderCharge.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");
        SharedPreferences taxes = getSharedPreferences("Tax",MODE_PRIVATE);
        tax = taxes.getInt("tax", 0);
        taxname = taxes.getString("taxname","");

    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
//                Intent gotoupdate = new Intent(DetailsOrderCharge.this, UpdateActivity.class);
//                startActivity(gotoupdate);
//                finish();
            }

        }else {
            startActivity(new Intent(DetailsOrderCharge.this, Login.class));
            finish();
            Toast.makeText(DetailsOrderCharge.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//            refresh=true;
        Intent back = new Intent(DetailsOrderCharge.this,ListChat.class);
        back.putExtra("name",username);
        back.putExtra("sessionnya",sessionnya);
        back.putExtra("chat",chatin);
        back.putExtra("user",name);
        back.putExtra("page","listchat");
        back.putExtra("chats","yes");
        back.putExtra("id",viewdetails);
        back.putExtra("cs","no");
        back.putExtra("titlenya", titlenya);
        back.putExtra("module","ServiceSupport");
//                gotonews.putExtra("tokennya",token);
        back.putExtra("engname", mcustname);
        back.putExtra("titlelist", titlelist);
        back.putExtra("nofr", mformRequestCd);
//        back.putExtra("xhori", xhori);
//        back.putExtra("yverti", yverti);
        back.putExtra("scrolbawah","-");
        back.putExtra("moduletrans", modultrans);
        startActivity(back);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();



    }
    public void LoadData(){
        gson = new Gson();
        loading = ProgressDialog.show(DetailsOrderCharge.this, "", "Loading..", true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("orderNo",noOrder);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.viewchargeable(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loading.dismiss();
                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                if (statusnya.equals("OK")) {
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    //chat baru pasang
//                    if(data.get("liveChatShowButton").getAsBoolean()){
//
//                        itemchat = new ArrayList<Itemchat>();
//                        itemchat2 = new Itemchat();
//                        databaseReference5= FirebaseDatabase.getInstance().getReference().child("chat").child(data.get("liveChatID").getAsString()).child("listchat");
//                        //
//                        name=data.get("liveChatUserName").getAsString();
//                        mchactclik.setVisibility(View.VISIBLE);
//
//                        if (data.get("liveChatOthersFirebaseToken").toString().equals("null")){
//                            tokennya = "-";
//                        }else {
////                            tokennya2.clear();
////                            JsonArray tokeny = data.getAsJsonArray("liveChatOthersFirebaseToken");
////                            for (int c = 0; c < tokeny.size(); ++c) {
////                                JsonObject assobj2 = tokeny.get(c).getAsJsonObject();
////                                tokennya2.add(assobj2.get("Token").getAsString());
////                            }
////
////                            Log.d("listToken", tokennya2.toString());
//                        }
//                        databaseReference5.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                itemchat.clear();
//                                total1 = 0;
//                                if (dataSnapshot.exists()){
//                                    for(DataSnapshot ds: dataSnapshot.getChildren())
//                                    {
//                                        Itemchat fetchDatalist=ds.getValue(Itemchat.class);
//                                        fetchDatalist.setKey(ds.getKey());
//                                        itemchat.add(fetchDatalist);
//                                    }
//
//                                    adapterchat=new Adapterchat(DetailsOrderCharge.this, itemchat);
//                                    for (int i = 0; i < itemchat.size(); i++) {
//                                        if (itemchat.get(i).getName().equals(name)){
//                                            mdot.setVisibility(View.GONE);
//                                        }else {
//
//                                            if (itemchat.get(i).getRead().equals("yes")){
//                                                mdot.setVisibility(View.GONE);
//                                            }else {
//                                                total1 +=1;
//                                                mdot.setVisibility(View.VISIBLE);
//                                                mnotif.setText(String.valueOf(total1));
//                                            }
//                                        }
//                                    }
//
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//
////                        engas = "";
//                    }else {
//                        mchactclik.setVisibility(View.GONE);
//
//                    }



//                    if (data.get("showProcessPaymentButton").getAsBoolean()){
//                        mpayment.setVisibility(VISIBLE);
//                        mpayment.setText(data.get("ProcessPaymentButtonText").getAsString());
//                    }else{
//                        mpayment.setVisibility(GONE);
//
//                    }
                    ///payment
//                    if (data.get("showPaymentHistoryButton").getAsBoolean()){
//                        mpayment2.setVisibility(VISIBLE);
//                        mpayment2.setText(data.get("paymentHistoryButtonText").getAsString());
//                    }else{
//                        mpayment2.setVisibility(GONE);
//
//                    }
//                    showlinkdownload = data.get("showUploadPOLink").getAsBoolean();
//                    if (showlinkdownload){
//                        mcopy.setVisibility(VISIBLE);
//                        String linkpdf = data.get("uploadPOLink").getAsString();
//                        mlinktext.setText(linkpdf);
//                    }else {
//                        mcopy.setVisibility(GONE);
//                    }
                    mdeskrip.setText(data.get("custNotes").getAsString());
//                    //laydownloadpoinv
//                    if (data.get("poDownloadURL").toString().equals("null")){
//                        mlayinvpo.setVisibility(GONE);
//                        mlaypo.setVisibility(GONE);
//
//                    }else {
//                        mlayinvpo.setVisibility(VISIBLE);
//                        mlaypo.setVisibility(VISIBLE);
//                        linkPo = data.get("poDownloadURL").getAsString();
//
//                    }
                    String showalert = data.get("showMessage").toString();
                    if (showalert.equals("true")){

                        String text = data.get("messageText").getAsString();
                        String textcolor = data.get("messageTextColor").getAsString();
                        String bgcolor = data.get("messageBackgroundColor").getAsString();

                        GradientDrawable shape =  new GradientDrawable();
                        shape.setCornerRadius( 15 );
                        shape.setColor(Color.parseColor("#"+bgcolor));

                        mbgalert.setVisibility(View.VISIBLE);
                        mtextalert.setTextColor(Color.parseColor("#"+textcolor));
                        mbgalert.setBackground(shape);
                        if (Build.VERSION.SDK_INT >= 24) {
                            mtextalert.setText((CharSequence) Html.fromHtml((String)text, Html.FROM_HTML_MODE_COMPACT));
                            mtextalert.setMovementMethod(LinkMovementMethod.getInstance());
                        } else {
                            mtextalert.setText((CharSequence)Html.fromHtml((String)text));
                            mtextalert.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    }else {
                        mbgalert.setVisibility(View.GONE);
                    }
                    String pressname = data.get("pressTypeName").getAsString();
                    //setnotes
                    String note = data.get("notes").getAsString();
                    mnotes.setText(note);
                    mnotes.setTextColor(Color.parseColor("#"+data.get("notesTextColor").getAsString()));

                    msn.setText(pressname);
                    tax = data.get("totalTax").getAsDouble();
                    Locale localeID = new Locale("in", "ID");
                    final DecimalFormat formatRupiah = new DecimalFormat("###,###,###,###,###.00");
                    mtax.setText("Rp. "+String.valueOf(formatRupiah.format(tax)));
                    mlabeltax.setText(taxname+":");
                    mtotalprice.setText("Rp. "+String.valueOf(formatRupiah.format(data.get("totalPrice").getAsDouble())));
                    mgrandtotal.setText("Rp. "+String.valueOf(formatRupiah.format(data.get("grandTotal").getAsDouble())));
                    grandtotal = "Rp. "+String.valueOf(formatRupiah.format(data.get("grandTotal").getAsDouble()));
                    pressid = data.get("pressGuid").getAsString();
                    nopo = data.get("poNo").getAsString();
                    mpono.setText(nopo);

                    String orderno = data.get("orderNo").getAsString();
                    String date = data.get("createdDateTime").getAsString();
//                    String pressstart = data.get("previousImpression").getAsString();
//                    String presimpres = data.get("presentImpression").getAsString();
                    String status = data.get("statusName").getAsString();
                    String statuscolor = data.get("statusColorCode").getAsString();
                    String operator = data.get("username").getAsString();
//                    String cancelshow = data.get("allowToCancel").toString();
//                    if (cancelshow.equals("true")){
//                        msend.setVisibility(View.VISIBLE);
//                        mchat.setVisibility(View.GONE);
//
//                    }else {
//                        msend.setVisibility(View.GONE);
//                        mchat.setVisibility(View.GONE);
//                    }
//                    mstartimpresi.setText(pressstart);
                    mno_order.setText(orderno);
//                    mlastimpresi.setText(presimpres);
                    mstatus.setText(status);
                    mstatus.setTextColor(Color.parseColor("#"+statuscolor));
                    String newdate = "";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    try {
                        newdate = simpleDateFormat.format(simpleDateFormat.parse(date));
                        System.out.println(newdate);
                        Log.e((String)"Date", (String)newdate);
                    }
                    catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    String[] separated = newdate.split("T");
                    separated[0].trim();; // this will contain "Fruit"
                    separated[1].trim();;
                    mdate.setText(separated[0]+" "+ separated[1]);
                    moperator.setText(operator);
                    if (data.get("itemList").toString().equals("null")){
                        madd_item.setVisibility(GONE);
                        mlaytotal.setVisibility(GONE);
                    }else {

                        myCustomArray = data.getAsJsonArray("itemList");
                        madd_item.setVisibility(VISIBLE);
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<Add_po_req_itemView>>() {
                        }.getType();
                        reitem = gson.fromJson(myCustomArray.toString(), listType);
                        req_adapter = new Add_po_req_adapterView(DetailsOrderCharge.this, reitem);
                        mlistitem_foc.setAdapter(req_adapter);
                        mlistitem_foc.setVisibility(View.VISIBLE);
                        mlaytotal.setVisibility(VISIBLE);
                    }
//                    if (data.get("showPaymentHistoryButton").getAsBoolean()){
//                        mpayment2.setVisibility(VISIBLE);
//                        mpayment2.setText(data.get("paymentHistoryButtonText").getAsString());
//                    }else{
//                        mpayment2.setVisibility(GONE);
//
//                    }

                }else {
                    cekInternet();
                    sesionid();
                    Toast.makeText(DetailsOrderCharge.this, errornya.toString(),Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(DetailsOrderCharge.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();


            }
        });
        Log.d("getpoview",jsonObject.toString());
    }

    private void setRequestImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) this, Manifest.permission.CAMERA)
                    && ActivityCompat.shouldShowRequestPermissionRationale((Activity) this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //Show permission dialog
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity)this, new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);


            }

        }else {
            startDownload();
        }
    }
    public void startDownload() {
        File direct = new File(Environment.getExternalStorageDirectory()
                + "/dhaval_files");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager mgr = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(linkPo);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

//        request.setDestinationInExternalPublicDir("/SmartcareCenter", downloadUri.getLastPathSegment());
//        Long referese = dm.enqueue(request);
        request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, downloadUri.getLastPathSegment());
        mgr.enqueue(request);
        Toast.makeText(getApplicationContext(), "Download File", Toast.LENGTH_SHORT).show();

//        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(linkdownloadnya));
//
//        queid = downloadManager.enqueue(request);
    }
}