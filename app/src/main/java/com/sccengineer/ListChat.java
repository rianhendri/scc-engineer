package com.sccengineer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sccengineer.Chat.Adapterchat;
import com.sccengineer.Chat.Itemchat;
import com.sccengineer.Chat.Itemchat2;
import com.sccengineer.Chat.Itemchat4a;
import com.sccengineer.SendNotificationPack.APIService;
import com.sccengineer.SendNotificationPack.Client;
import com.sccengineer.SendNotificationPack.Data;
import com.sccengineer.SendNotificationPack.MyResponse;
import com.sccengineer.SendNotificationPack.NotificationSender;
import com.sccengineer.apihelper.IRetrofit;
import com.sccengineer.apihelper.ServiceGenerator;
import com.sccengineer.generatechat.Adaptergeneratechat;
import com.sccengineer.generatechat.Itemgeneratechat;
import com.sccengineer.livechatlist.DetailsDate;
import com.sccengineer.livechatlist.ListLiveChatAdapter;
import com.sccengineer.livechatlist.ListLiveChatItem;
import com.sccengineer.menuhome.MenuAdapter;
import com.sccengineer.menuhome.MenuItem;
import com.sccengineer.messagecloud.check;
import com.sccengineer.notifikasihome.NotifhomeAdapter;
import com.sccengineer.notifikasihome.NotifhomeItems;
import com.sccengineer.onproghome.OnProgHome_items;
import com.sccengineer.onproghome.OnProghome_adapter;
import com.sccengineer.serviceticket.ServiceTicketAdapter;
import com.sccengineer.serviceticket.ServicesTicketItem;
import com.sccengineer.spartsend.SendSparepart_adapter;
import com.sccengineer.spartsend.SendSparepart_item;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.sccengineer.Chat.Adapterchat.addFoclistreq;
import static com.sccengineer.DetailsST.jsonarayitem;
import static com.sccengineer.DetailsST.mcustname;
import static com.sccengineer.DetailsST.mformRequestCd;
import static com.sccengineer.DetailsST.noreq;
import static com.sccengineer.DetailsST.username;
import static com.sccengineer.DetailsST.xhori;
import static com.sccengineer.DetailsST.yverti;
import static com.sccengineer.LiveChatList.mfooterload;
import static com.sccengineer.LiveChatList.myitem_place;
import static com.sccengineer.apihelper.ServiceGenerator.baseurl;
import static com.sccengineer.apihelper.ServiceGenerator.fcmbase;
import static com.sccengineer.apihelper.ServiceGenerator.getchatnya;
import static com.sccengineer.menuhome.MenuAdapter.countSC;
import static com.sccengineer.messagecloud.check.tokennya2;
import static com.sccengineer.LiveChatList.itemchat;

public class ListChat extends AppCompatActivity {

    //
    String scs = "";
    Boolean liveChatRepor=false;
    Boolean internet = false;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    String sesionid_new = "";
    String idnotif = "";
    RecyclerView recyclerView,mchatgenerate;
    Itemchat2 itemchat2 ;
    ArrayList<Itemchat> itemchat3;
    ArrayList<Itemchat4a> itemchat4a;
    Adapterchat adapterchat;
    ArrayList<Itemgeneratechat> generatechat;
    JsonArray listformreq;
    Adaptergeneratechat generateadapter;
    Itemgeneratechat addgenerate,addgenerate2,addgenerate3,addgenerate4;
    public static DatabaseReference databaseReference,databaseReference3,databaseReference4,databaseReference5;
    DatabaseReference dfr;
    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReferencechat = FirebaseDatabase.getInstance().getReference();
    LinearLayoutManager linearLayoutManager,linearLayoutManager2;
    LinearLayout mlayketk;
    public static String name="";
    String module = "";
    String ModuleTransactionNo = "";
    String engas = "";
    String sendto="";
    public static EditText sendtext;
    ImageView mpaperclip;
    public static ImageView msend,mdelet,mcopy;
    String show="no";
    String showid="-";
    private static final int PICK_PDF_REQUEST = 1000;
    private String pdfPath="";
    public static String keynya1 = "";
    String url = "-";
    String showurl ="-";
    String message = "";
    String myuri = "-";
    String youuri = "-";
    StorageReference ref = FirebaseStorage.getInstance().getReference();
    public static File imagefile = null;
    String mimeType="";
    String mimeType2="-";
    public static LinearLayout mdelcop,mback, mcopylay;
    ProgressDialog loading;
    public static String user = "admin";
    String idhcat="admin1";
    public static String levelStatus="admin";
    public static String sessionnya = "";

    final int REQUEST_IMAGE_GALLERY = 2;
    Uri photo_location;
   public static  boolean chatin = false;
    String noreq = "";
    String id = "";
    int ping=0;
    String username = "";
    public static String modultrans="";
    TextView mfrnya, mstnya;
    String page = "-";
    String custnmae="";
    String tokennya = "";
    String nofr = "";
    String titlenya= "";
    private APIService apiService;
    int PERMISSION_CODE = 100;
    int xhori = 0;
    int yverti = 0;
    String scrollnya = "-";
    int tokenpos=0;
    String titlelist = "";
    String stnya="";
    ProgressBar mloadingchat;
    LinearLayout mmnodatas;
    String homes = "";
    String chats = "";
    boolean kirim = true;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chat);
        mloadingchat = findViewById(R.id.loadingchat);
        mmnodatas = findViewById(R.id.nodatas);
        mchatgenerate = findViewById(R.id.chatgenerate);
        mfrnya = findViewById(R.id.frnya);
        mstnya = findViewById(R.id.stnya);
        mpaperclip = findViewById(R.id.paperclip);
        recyclerView=findViewById(R.id.lischat);
        sendtext = findViewById(R.id.textsend);
        msend = findViewById(R.id.send);
        mback = findViewById(R.id.backbtn);
        mdelcop = findViewById(R.id.delcop);
        mcopylay = findViewById(R.id.cop);
        mdelet = findViewById(R.id.delete);
        mcopy = findViewById(R.id.copy);
        mlayketk = findViewById(R.id.layketk);
        getSessionId();
        Bundle bundle2 = getIntent().getExtras();

        ///
        if (bundle2 != null) {
            if (bundle2.getString("chats")!=null){
                chats = "yes";
            }else {
                chats = "no";
            }
            sessionnya = bundle2.getString("sessionnya");
            name = bundle2.getString("name");
            noreq = bundle2.getString("id");
            scs = bundle2.getString("cs");
            id = bundle2.getString("page");
            ping=bundle2.getInt("ping");
            stnya = bundle2.getString("stnya");
            username = bundle2.getString("user");
            chatin = bundle2.getBoolean("chat");
            liveChatRepor = bundle2.getBoolean("liveChatRepor");
            custnmae = bundle2.getString("engname");
            tokennya = bundle2.getString("tokennya");
            nofr = bundle2.getString("nofr");
            xhori=bundle2.getInt("xhori");
            yverti=bundle2.getInt("yverti");
            titlenya=bundle2.getString("titlenya");
            titlelist=bundle2.getString("titlelist");
            modultrans=bundle2.getString("moduletrans");
            module=bundle2.getString("module");
            scrollnya =   bundle2.getString("scrolbawah");
            homes =   bundle2.getString("home");
            Log.d("idnya",id);
//            Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
//            mfrnya.setText(noreq+" (Customer: "+custnmae+")");
            mfrnya.setText(titlenya);
            mloadingchat.setVisibility(View.VISIBLE);
//                scs="no";
//            modultrans="";
            if (id==null){
                String currentString = noreq;
                String[] separated = currentString.split("-");
                modultrans = separated[1];
                module = separated[2];
                idnotif = separated[0];
            }else {

            }


//                separated[0]; // this will contain "Fruit"
//                separated[1];
            Log.d("idchat",idnotif);
        }

        getEngas();
        getShowid();
            reqApi();
        if (modultrans.equals("kosong")){
            mstnya.setVisibility(GONE);
        }else {
            if (module.equals("ServiceSupport")){
                mstnya.setText(Html.fromHtml("<u>"+getString(R.string.title_detailchatst)+"</u>"));
                mstnya.setVisibility(View.VISIBLE);
            }else {
                if (module.equals("FOC")){
                    mstnya.setText(Html.fromHtml("<u>"+"View detail"+"</u>"));
                    mstnya.setVisibility(View.VISIBLE);
                }else {
                    if (module.equals("Chargeable")) {
                        mstnya.setText(Html.fromHtml("<u>" + "View detail"+ "</u>"));
                        mstnya.setVisibility(View.VISIBLE);
                    }else {
                        mstnya.setVisibility(GONE);
                    }
                }

            }

        }

        if (chatin){
            String aString =sessionnya;
            String cutString = aString.substring(0, 2);
            if (cutString.equals("ST")){
                loadgenerate();
            }

            mlayketk.setVisibility(View.VISIBLE);

        }else {
            mlayketk.setVisibility(GONE);
        }
        linearLayoutManager = new LinearLayoutManager(ListChat.this, LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setHasFixedSize(true);
        itemchat3 = new ArrayList<Itemchat>();
        itemchat4a = new ArrayList<Itemchat4a>();
        itemchat2= new Itemchat2();


        if (id==null){
            if (module.equals("ChatWithSupport")){
                reqnotif2();
                scs="yes";
                titlelist="Support Live Chat List";
            }else {
                reqnotif();
                scs="no";
                titlelist="List Live Chat";
            }


        }else {
            databaseReference= FirebaseDatabase.getInstance().getReference().child("chat").child(sessionnya).child("listchat");
            dfr= FirebaseDatabase.getInstance().getReference().child("akunregist");
            databaseReference3= FirebaseDatabase.getInstance().getReference().child("chat").child(sessionnya).child("listchat");
            loadchat();
//            lchat();
        }
//        databaseReference5= FirebaseDatabase.getInstance().getReference().child("chat");
//        loadchat2();
        mstnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.checknotif=1;
                if (module.equals("ServiceSupport")){
                    Intent gotonews = new Intent(ListChat.this, DetailsST.class);
                    gotonews.putExtra("name",username);
                    gotonews.putExtra("sessionnya",sessionnya);
                    gotonews.putExtra("chat",chatin);
                    gotonews.putExtra("user",name);
                    gotonews.putExtra("id",modultrans);
                    gotonews.putExtra("titlelist",titlelist);
                    gotonews.putExtra("titlenya",titlenya);
                    gotonews.putExtra("viewdetails",noreq);
                    gotonews.putExtra("chats","yes");
//                gotonews.putExtra("tokennya",token);
                    gotonews.putExtra("engname", mcustname);
                    gotonews.putExtra("nofr", mformRequestCd);
                    gotonews.putExtra("xhori", xhori);
                    gotonews.putExtra("yverti", yverti);
                    gotonews.putExtra("scrolbawah","-");
                    startActivity(gotonews);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    finish();
                }else {
                    if (module.equals("FOC")){
                        Intent gotonews = new Intent(ListChat.this, DetailsOrderFoc.class);
                        gotonews.putExtra("name",username);
                        gotonews.putExtra("sessionnya",sessionnya);
                        gotonews.putExtra("chat",chatin);
                        gotonews.putExtra("user",name);
                        gotonews.putExtra("id",modultrans);
                        gotonews.putExtra("titlelist",titlelist);
                        gotonews.putExtra("titlenya",titlenya);
                        gotonews.putExtra("viewdetails",noreq);
                        gotonews.putExtra("chats","yes");
//                gotonews.putExtra("tokennya",token);
                        gotonews.putExtra("engname", mcustname);
                        gotonews.putExtra("nofr", mformRequestCd);
                        gotonews.putExtra("xhori", xhori);
                        gotonews.putExtra("yverti", yverti);
                        gotonews.putExtra("scrolbawah","-");
                        startActivity(gotonews);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        finish();
                    }else {
                        if (module.equals("Chargeable")) {
                            Intent gotonews = new Intent(ListChat.this, DetailsOrderCharge.class);
                            gotonews.putExtra("name",username);
                            gotonews.putExtra("sessionnya",sessionnya);
                            gotonews.putExtra("chat",chatin);
                            gotonews.putExtra("user",name);
                            gotonews.putExtra("id",modultrans);
                            gotonews.putExtra("titlelist",titlelist);
                            gotonews.putExtra("titlenya",titlenya);
                            gotonews.putExtra("viewdetails",noreq);
                            gotonews.putExtra("chats","yes");
//                gotonews.putExtra("tokennya",token);
                            gotonews.putExtra("engname", mcustname);
                            gotonews.putExtra("nofr", mformRequestCd);
                            gotonews.putExtra("xhori", xhori);
                            gotonews.putExtra("yverti", yverti);
                            gotonews.putExtra("scrolbawah","-");
                            startActivity(gotonews);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            finish();
                        }else {
                            mstnya.setVisibility(GONE);
                        }
                    }

                }

            }
        });
        msend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message=sendtext.getText().toString();
                showurl="-";
                myuri = "-";
//                if (liveChatRepor){
//                    ping();
//                }else {
//
//                }
                if (sendtext.length()==0){

                }else {
                    mback.setVisibility(View.VISIBLE);
                    mdelcop.setVisibility(View.GONE);
                    String date = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH).format(new Date());
                    getShowid();
                    if (showid.equals(date)){
                        show="no";
                    }else {
                        show="yes";
                    }
                    SharedPreferences sharedPreferences = getSharedPreferences("SHOW_ID", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("show_id", date);
                    editor.apply();
                    sendDb();
                    loadchat();
                    sendtext.setText("");
                }

            }
        });
        mpaperclip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ContextCompat.checkSelfPermission(ListChat.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

                    ActivityCompat.requestPermissions(ListChat.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
                    return;

                }else {
                    gallery();
                }
//                launchPicker();
            }
        });


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 100 && grantResults.length>0 && (grantResults[0]
                == PackageManager.PERMISSION_GRANTED)){


        }else {
            Toast.makeText(this, "Storage Wajib", Toast.LENGTH_LONG).show();

        }
    }
    public void sendDb(){
//        int posinya = 0;
//        if ((addFoclistreq!=null)){
//            posinya = 0;
//        }else{
//            posinya = addFoclistreq.size()+1;
//        }
        String date = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH).format(new Date());
        String time = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date());
        itemchat2.setDate(date);
        itemchat2.setName(name);
        itemchat2.setShowDate(show);
        itemchat2.setTime(time);
        itemchat2.setRead("no");
        itemchat2.setLevel(engas);
        itemchat2.setThumb("-");
        itemchat2.setType(mimeType2);
//        itemchat2.setSendto(sendto);
        itemchat2.setUrl(url);
        itemchat2.setMyuri(myuri);
        itemchat2.setYoururi(youuri);
//        itemchat2.setPosition(posinya);
        itemchat2.setShowUrl(showurl);
        itemchat2.setMessage(message);
        databaseReference2.child("chat").child(sessionnya).child("listchat").push().setValue(itemchat2).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (liveChatRepor){
                    ping();
                }else {

                }
               sendnotifchat();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        mimeType2 = "-";
    }

    public void reqnotif() {
        loading = ProgressDialog.show(ListChat.this, "", "", true);
//        loading .setVisibility(View.VISIBLE);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("liveChatID",idnotif);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.getlivechat(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("configeng",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
//                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")) {
                    loading.dismiss();
//                    loading .setVisibility(View.GONE);
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    JsonObject data2 = data.getAsJsonObject("details");
                    if (data2.get("OthersFirebaseToken").toString().equals("null")){
                        tokennya = "-";
                    }else {
                        tokennya2.clear();
                        JsonArray tokeny = data2.getAsJsonArray("OthersFirebaseToken");
                        for (int c = 0; c < tokeny.size(); ++c) {
                            JsonObject assobj2 = tokeny.get(c).getAsJsonObject();
                            tokennya2.add(assobj2.get("Token").getAsString());
                        }

                        Log.d("listToken", tokennya2.toString());
                    }
                    id="listchat";
                    titlenya = data2.get("Title").getAsString();
                    name = data2.get("UserName").getAsString();
                    module = data2.get("Module").getAsString();
                    ModuleTransactionNo = data2.get("ModuleTransactionNo").getAsString();
                    chatin = data2.get("AllowToChat").getAsBoolean();
                    String iduser = data2.get("UserID").getAsString();
                    sessionnya = data2.get("LiveChatID").getAsString();
                    databaseReference= FirebaseDatabase.getInstance().getReference().child("chat").child(sessionnya).child("listchat");
                    databaseReference3= FirebaseDatabase.getInstance().getReference().child("chat").child(sessionnya).child("listchat");
                    modultrans = ModuleTransactionNo;
                    noreq=titlenya;
                    Log.d("moduletrasn",name+"-"+noreq+"/"+iduser);
                    if (modultrans.equals("")){
                        mstnya.setVisibility(GONE);
                    }else {
                        if (module.equals("ServiceSupport")){
                            mstnya.setText(Html.fromHtml("<u>"+getString(R.string.title_detailchatst)+"</u>"));
                            mstnya.setVisibility(View.VISIBLE);
                            Log.d("moduletrasn",modultrans);
                        }else {
                            mstnya.setVisibility(GONE);
                        }

                    }
                    if (chatin){
                        mlayketk.setVisibility(View.VISIBLE);
                    }else {
                        mlayketk.setVisibility(GONE);
                    }
                    mfrnya.setText(titlenya);
                    loadchat();
                }else {
                    mloadingchat.setVisibility(GONE);
                    sesionid();
                    loading.dismiss();
                    //// error message
//                    loading .setVisibility(View.GONE);
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ListChat.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                mloadingchat.setVisibility(GONE);
                Toast.makeText(ListChat.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading .setVisibility(View.GONE);
                loading.dismiss();

            }
        });
        Log.d("reqnotifnya",jsonObject.toString());
    }
    public void reqnotif2() {
        loading = ProgressDialog.show(ListChat.this, "", "", true);
//        loading .setVisibility(View.VISIBLE);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("liveChatID",idnotif);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.getlivechatcs(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("configeng",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
//                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")) {
                    loading.dismiss();
//                    loading .setVisibility(View.GONE);
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    JsonObject data2 = data.getAsJsonObject("details");
                    if (data2.get("OthersFirebaseToken").toString().equals("null")){
                        tokennya = "-";
                    }else {
                        tokennya2.clear();
                        JsonArray tokeny = data2.getAsJsonArray("OthersFirebaseToken");
                        for (int c = 0; c < tokeny.size(); ++c) {
                            JsonObject assobj2 = tokeny.get(c).getAsJsonObject();
                            tokennya2.add(assobj2.get("Token").getAsString());
                        }

                        Log.d("listToken", tokennya2.toString());
                    }
                    id="listchat";
                    titlenya = data2.get("Title").getAsString();
                    name = data2.get("UserName").getAsString();
                    module = data2.get("Module").getAsString();
                    ModuleTransactionNo = data2.get("ModuleTransactionNo").getAsString();
                    chatin = data2.get("AllowToChat").getAsBoolean();
                    String iduser = data2.get("UserID").getAsString();
                    sessionnya = data2.get("LiveChatID").getAsString();
                    databaseReference= FirebaseDatabase.getInstance().getReference().child("chat").child(sessionnya).child("listchat");
                    databaseReference3= FirebaseDatabase.getInstance().getReference().child("chat").child(sessionnya).child("listchat");
                    modultrans = ModuleTransactionNo;
                    noreq=titlenya;
                    Log.d("moduletrasn",name+"-"+noreq+"/"+iduser);
                    if (modultrans.equals("")){
                        mstnya.setVisibility(GONE);
                    }else {
                        if (module.equals("ServiceSupport")){
                            mstnya.setText(Html.fromHtml("<u>"+getString(R.string.title_detailchatst)+"</u>"));
                            mstnya.setVisibility(View.VISIBLE);
                            Log.d("moduletrasn",modultrans);
                        }else {
                            mstnya.setVisibility(GONE);
                        }

                    }
                    if (chatin){
                        mlayketk.setVisibility(View.VISIBLE);
                    }else {
                        mlayketk.setVisibility(GONE);
                    }
                    mfrnya.setText(titlenya);
                    loadchat();
                }else {
                    mloadingchat.setVisibility(GONE);
                    sesionid();
                    loading.dismiss();
                    //// error message
//                    loading .setVisibility(View.GONE);
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ListChat.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                mloadingchat.setVisibility(GONE);
                Toast.makeText(ListChat.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading .setVisibility(View.GONE);
                loading.dismiss();

            }
        });
        Log.d("reqnotifnya",jsonObject.toString());
    }
    public void ping() {
        loading = ProgressDialog.show(ListChat.this, "", "", true);
//        loading .setVisibility(View.VISIBLE);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("serviceTicketCd",sessionnya);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.ping(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("configeng",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
//                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")) {
                    loading.dismiss();
//                    loading .setVisibility(View.GONE);
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    Log.d("rekping","successeng");
                }else {
                    mloadingchat.setVisibility(GONE);
                    sesionid();
                    loading.dismiss();
                    //// error message
//                    loading .setVisibility(View.GONE);
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ListChat.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ListChat.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading .setVisibility(View.GONE);
                loading.dismiss();
                mloadingchat.setVisibility(GONE);

            }
        });
        Log.d("reqnotifnya",jsonObject.toString());
    }
    public void loadchat(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemchat3.clear();
                if (dataSnapshot.exists()){
                    for(DataSnapshot ds: dataSnapshot.getChildren())
                    {
                        Itemchat fetchDatalist=ds.getValue(Itemchat.class);
                        fetchDatalist.setKey(ds.getKey());
                        itemchat3.add(fetchDatalist);
                    }

                    adapterchat=new Adapterchat(ListChat.this, itemchat3);
                    recyclerView.setAdapter(adapterchat);
                    recyclerView.scrollToPosition(adapterchat.getItemCount()-1);
//                recyclerView.scrollToPosition(adapterchat.getItemCount());
                    mmnodatas.setVisibility(GONE);
                mloadingchat.setVisibility(GONE);
                readchat ();
                }else {
                    mloadingchat.setVisibility(GONE);
                    mmnodatas.setVisibility(View.VISIBLE);
                }

//                Log.d("posi",String.valueOf(recyclerView.getAdapter().getItemCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mloadingchat.setVisibility(GONE);
                mmnodatas.setVisibility(View.VISIBLE);
                Toast.makeText(ListChat.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void lchat(){
        dfr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemchat4a.clear();
                if (dataSnapshot.exists()){
                    for(DataSnapshot ds: dataSnapshot.getChildren())
                    {
                        Itemchat4a fetchDatalist=ds.getValue(Itemchat4a.class);
                        itemchat4a.add(fetchDatalist);
                    }
                    String assobj12 ="";
                    for (int c = 0; c < itemchat4a.size(); ++c) {
                        String assobj2 = itemchat4a.get(c).getEmail()+"\n";
                        assobj12 += assobj2;
                        Log.d("emailchat",assobj2);
                    }
                    sendtext.setText(assobj12);
//                    adapterchat=new Adapterchat(ListChat.this, itemchat3);
//                    recyclerView.setAdapter(adapterchat);
//                    recyclerView.scrollToPosition(adapterchat.getItemCount()-1);
//                recyclerView.scrollToPosition(adapterchat.getItemCount());
                    mmnodatas.setVisibility(GONE);
                    mloadingchat.setVisibility(GONE);
                }else {
                    mloadingchat.setVisibility(GONE);
                    mmnodatas.setVisibility(View.VISIBLE);
                }

//                Log.d("posi",String.valueOf(recyclerView.getAdapter().getItemCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mloadingchat.setVisibility(GONE);
                mmnodatas.setVisibility(View.VISIBLE);
                Toast.makeText(ListChat.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void loadgenerate(){
        loading = ProgressDialog.show(ListChat.this, "", "loading...", true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
//        Toast.makeText(DetailsFormActivity.this,jsonObject.toString(), Toast.LENGTH_SHORT).show();
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.generatelist(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @SuppressLint("WrongConstant")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("configeng",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
//                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")) {
                    loading.dismiss();
//                    loading .setVisibility(View.GONE);
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    listformreq = data.getAsJsonArray("list");
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Itemgeneratechat>>() {
                    }.getType();
                    generatechat = gson.fromJson(listformreq.toString(), listType);
                    generateadapter = new Adaptergeneratechat(ListChat.this, generatechat);
                    //generate chat
//                    generatechat = new ArrayList<Itemgeneratechat>();
//                    addgenerate= new Itemgeneratechat();
//                    addgenerate2= new Itemgeneratechat();
//                    addgenerate3= new Itemgeneratechat();
//                    addgenerate4= new Itemgeneratechat();
//                    addgenerate.setTextgenerate("Text Generate satu (1)");
//                    generatechat.add(addgenerate);
//                    addgenerate2.setTextgenerate("Text Generate dua (2)");
//                    generatechat.add(addgenerate2);
//                    addgenerate3.setTextgenerate("Text Generate tiga (3)");
//                    generatechat.add(addgenerate3);
//                    addgenerate4.setTextgenerate("Text Generate empat (4)");
//                    generatechat.add(addgenerate4);
                    linearLayoutManager2 = new LinearLayoutManager(ListChat.this, LinearLayout.HORIZONTAL,false);
                    mchatgenerate.setLayoutManager(linearLayoutManager2);
                    generateadapter=new Adaptergeneratechat(ListChat.this, generatechat);
                    mchatgenerate.setAdapter(generateadapter);
                }else {
                    mloadingchat.setVisibility(GONE);
                    sesionid();
                    loading.dismiss();
                    //// error message
//                    loading .setVisibility(View.GONE);
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ListChat.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(DetailsST.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
        Log.d("loadDetailst",jsonObject.toString());
    }
    public void loadchat2(){
//        loading = ProgressDialog.show(DetailsST.this, "", "loading...", true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId","");
//        Toast.makeText(DetailsFormActivity.this,jsonObject.toString(), Toast.LENGTH_SHORT).show();
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, getchatnya);
        Call<JsonObject> panggilkomplek = jsonPostService.getjsonchat();
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                JsonObject data = homedata.getAsJsonObject("SO210914004");
                JSONObject rolejson = null;
                try {
                    rolejson = new JSONObject(data.get("listchat").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for(int i = 0; i<rolejson.names().length(); i++){
                    try {
//                        Log.d("TestJson",rolejson.names().getJSONObject(i).getString("message"));
                        Log.d("TAGet", "key = " + rolejson.names().getString(i) + " value = " + rolejson.getJSONObject(rolejson.names().getString(i)).getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("TAGet", e.toString());
                    }
                }
//                Log.d("jsonchatt",outputJsonArray.toString());

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(DetailsST.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
        Log.d("loadDetailst",jsonObject.toString());
    }
    public void getShowid(){

        SharedPreferences sharedPreferences = getSharedPreferences("SHOW_ID",MODE_PRIVATE);
        showid = sharedPreferences.getString("show_id", "");

    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager)ListChat.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(ListChat.this,NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    private void dialogfile() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("kirim:"+imagefile.getName())
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        int position = 1;

                        if (itemchat3.toString().equals("[]")){
                            Log.e("listkosong",itemchat3.toString());


                        }else {
//                            Log.e("listkosong",itemchat.toString());
                            position = addFoclistreq.size()+1;


                        }
//                        loading = ProgressDialog.show(ListChat.this, "", "Uploading...", true);
                        // jika tombol diklik, maka akan menutup activity ini
                        showurl="yes";

                        message = imagefile.getName();
                        myuri = pdfPath;
                        String date = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH).format(new Date());
                        String time = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date());
                        itemchat2.setDate(date);
                        itemchat2.setName(name);
                        itemchat2.setShowDate(show);
                        itemchat2.setTime(time);
                        itemchat2.setRead("no");
                        itemchat2.setSendto(sendto);
                        itemchat2.setUrl(url);
                        itemchat2.setLevel(engas);
                        itemchat2.setMyuri(myuri);
                        itemchat2.setThumb("-");
                        itemchat2.setType(mimeType2);
                        itemchat2.setYoururi(youuri);
                        itemchat2.setPosition(position);
                        itemchat2.setShowUrl(showurl);
                        itemchat2.setMessage(message);
                        databaseReference2.child("chat").child(sessionnya).child("listchat").push().setValue(itemchat2).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if (liveChatRepor){
                                    ping();
                                }else {

                                }
                                sendnotifchat();
                                int posi = addFoclistreq.size()-1;
                                InputStream stream = null;
                                try {
                                    stream = new FileInputStream(new File(imagefile.toString()));
                                    Log.d("strm",stream.toString());
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }


//                        UploadTask uploadTask = ref.child("images").putStream(stream);

//                                ref.child("chat"+"/"+sessionnya+"/"+"listchat"+"/"+addFoclistreq.get(posi).getKey()+"/"+message).putFile(Uri.fromFile(imagefile)).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e)
//                                    {
//                                        String message = e.toString();
//                                        loading.dismiss();
//                                        Log.d("erorupload",message);
////                                loadingBar.dismiss();
//                                    }
//                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                                    @Override
//                                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                                        double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
//                                        prog = (int) progress;
//                                        Log.d("progress", String.valueOf(prog));
//
//                                    }
//                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                    @Override
//                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
//                                    {
//                                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
//                                        while (!urlTask.isSuccessful());
//                                        Uri downloadUrl = urlTask.getResult();
//                                        Log.d("getdownload",downloadUrl.toString());
//                                        HashMap hashMap = new HashMap();
//                                        hashMap.put("url",downloadUrl.toString());
//                                        hashMap.put("showUrl","done");
//                                        databaseReference3.child(addFoclistreq.get(posi).getKey()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                loading.dismiss();
//                                                Toast.makeText(ListChat.this, "uploaded Successfully...", Toast.LENGTH_SHORT).show();
                                //sdfksdfh sf sdf h            gkhg gdudfg hdgif sjfgfg fgd fg dfg tert et  dge ge e
//
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                loading.dismiss();
//                                            }
//                                        });
//                                    }
//                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
//                            loading.dismiss();
                            }
                        });
                        mimeType2 = "-";
                    }

                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,  resultCode,  data);
//        Log.d("photolocation",photo_location.toString());
        if (requestCode==REQUEST_IMAGE_GALLERY) {
            if (data!=null){
                photo_location = data.getData();
            }


            if (photo_location!=null){
//                    Toast.makeText(AddRequest.this, photo_location.toString(),Toast.LENGTH_LONG).show();
                if (data!=null){
                    photo_location = data.getData();
                    String yourRealPath ="";
                    try {
                        File file;

                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(photo_location, filePathColumn, null, null, null);
                        if(cursor.moveToFirst()){
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            yourRealPath = cursor.getString(columnIndex);
                            pdfPath = yourRealPath;
                            imagefile =new File(yourRealPath);
                            String fileExtension= MimeTypeMap.getFileExtensionFromUrl(imagefile.toString());
                            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
                            String[] separated = mimeType.split("/");
                            separated[0].trim();; // this will contain "Fruit"
                            separated[1].trim();;
                            mimeType2=separated[0];
                            Log.d("pathgalery",fileExtension+"="+mimeType2);
                            dialogfile();
                        } else {
                            //boooo, cursor doesn't have rows ...
                        }


                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                        Toast.makeText(ListChat.this, exception.toString(),Toast.LENGTH_LONG).show();
                    }

                }

            }

        }else {

        }

    }
    public void gallery( ) {
        Intent mediaChooser = new Intent(Intent.ACTION_PICK);

        mediaChooser.setType("video/*, image/*");
        startActivityForResult(mediaChooser, REQUEST_IMAGE_GALLERY);


        photo_location=mediaChooser.getData();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        itemchat.clear();
        itemchat3.clear();
            if (id.equals("listchat")){
                Intent back = new Intent(ListChat.this,LiveChatList.class);
                back.putExtra("cs", scs);
                back.putExtra("titlelist", titlelist);
                startActivity(back);
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                finish();
            }else{
                if (ping==1) {
                    Intent back = new Intent(ListChat.this, DetailsST.class);
                    back.putExtra("name", name);
                    back.putExtra("id", stnya);
                    back.putExtra("user", username);
                    back.putExtra("home", homes);
                    back.putExtra("xhori", xhori);
                    back.putExtra("yverti", yverti);
                    back.putExtra("scrolbawah", "-");
                    startActivity(back);
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    finish();
                }else {
                    if (ping==2) {
                        Intent back = new Intent(ListChat.this, DetailsPM.class);
                        back.putExtra("name", name);
                        back.putExtra("id", stnya);
                        back.putExtra("user", username);
                        back.putExtra("home", homes);
                        back.putExtra("xhori", xhori);
                        back.putExtra("yverti", yverti);
                        back.putExtra("scrolbawah", "-");
                        startActivity(back);
                        overridePendingTransition(R.anim.left_in, R.anim.right_out);
                        finish();
                    }
                }
            }



    }
    public void sendnotifchat(){
//        loading = ProgressDialog.show(DetailsFormActivity.this, "", getString(R.string.title_loading), true);

            JsonObject dataid = new JsonObject();
            dataid.addProperty("id",sessionnya+"-"+modultrans+"-"+module);

            JsonObject notifikasidata = new JsonObject();
            notifikasidata.addProperty("title",noreq+"-"+name);
            notifikasidata.addProperty("body",message);
            notifikasidata.addProperty("click_action","notifchat");

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("to",tokennya2.get(tokenpos));
            jsonObject.add("data",dataid);
            jsonObject.add("notification",notifikasidata);
//        Toast.makeText(DetailsFormActivity.this,jsonObject.toString(), Toast.LENGTH_SHORT).show();
            APIService jsonPostService = ServiceGenerator.createService(APIService.class, fcmbase);
            Call<JsonObject> panggilkomplek = jsonPostService.sendNotifcation(jsonObject);
            panggilkomplek.enqueue(new Callback<JsonObject>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.d("tokenplusisze", String.valueOf(tokennya2.size()-1)+"=="+String.valueOf(tokenpos));
                    Log.d("tokenlist",tokennya2.get(tokenpos).toString());
                    Log.d("sizetoken", String.valueOf(tokennya2.size()));
                    if (tokennya2.size()==tokenpos+1){
                        tokenpos =0;
                    }else {
                        tokenpos +=1;
                        sendnotifchat();
                    }
                    String errornya = "";
                    JsonObject homedata = response.body();
                    int statusnya = homedata.get("success").getAsInt();
                    if (statusnya == 1) {
//                    JsonObject data = homedata.getAsJsonObject("data");
                        Log.d("responnotif",homedata.toString());
                    } else {
//                    sesionid();
//                        loading.dismiss();
//                    Toast.makeText(DetailsFormActivity.this, errornya,Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(DetailsFormActivity.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
//                cekInternet();
                    loading.dismiss();
                    mloadingchat.setVisibility(GONE);

                }
            });
            Log.d("requestnotif",jsonObject.toString());




    }
    public void getEngas(){

        SharedPreferences sharedPreferences = getSharedPreferences("LEVEL_ENGINEER",MODE_PRIVATE);
        engas = sharedPreferences.getString("level_engineer", "");
        Log.d("level_engineer",engas);

    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
//                Intent gotoupdate = new Intent(Home.this, UpdateActivity.class);
//                startActivity(gotoupdate);
//                finish();
            }

        }else {
            startActivity(new Intent(ListChat.this, Login.class));
            finish();
//            Toast.makeText(Home.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");
        Log.d("session",sesionid_new);

    }
    public void reqApi() {
//        loading = ProgressDialog.show(this, "", "", true);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONconfig(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("reqapi1",homedata.toString());

                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
//                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")) {

//                    loading.dismiss();
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");

                    boolean clocksts = data.get("alreadyClockIn").getAsBoolean();

                    if (clocksts){
                        ;
                    }else {
                        startActivity(new Intent(ListChat.this, ClockInActivity.class));
                        finish();
//                        mcheck.setVisibility(View.VISIBLE)

                    }
                }else {
//                    mrefresh.setVisibility(View.VISIBLE);
//                    mcheck.setVisibility(View.GONE);
                    sesionid();
                    //// error message
//                    loading.dismiss();
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ListChat.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                    mloadingchat.setVisibility(GONE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
//                mrefresh.setVisibility(View.VISIBLE);
//                mcheck.setVisibility(View.GONE);
                Toast.makeText(ListChat.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                mloadingchat.setVisibility(GONE);
//                loading.dismiss();
            }
        });
        Log.d("reqapi",jsonObject.toString());

    }
    public void readchat (){
        String keyread = "";
        for (int v = 0; v < addFoclistreq.size(); ++v) {
            if (name.equals(addFoclistreq.get(v).getName())){

            }else {
                if (addFoclistreq.get(v).getRead().equals("yes")){

                }else {
                    keyread = addFoclistreq.get(v).getKey();
//                    kirim=false;
                }
            }
        }
        if (kirim){
            kirim=false;
            Log.d("testresdad",keyread);
            if (keyread.equals("")){

            }else {
                HashMap hashMap = new HashMap();
                hashMap.put("read","yes");
                databaseReference2.child("chat").child(sessionnya).child("listchat").child(keyread).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        kirim=true;
                        readchat();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        kirim=true;
                        readchat();
                    }
                });
            }

        }else {

        }
    }
}