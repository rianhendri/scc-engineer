package com.sccengineer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sccengineer.apihelper.IRetrofit;
import com.sccengineer.apihelper.ServiceGenerator;
import com.sccengineer.notification.NotificationAdapter;
import com.sccengineer.notification.NotificationItem;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sccengineer.apihelper.ServiceGenerator.baseurl;
import static com.sccengineer.apihelper.ServiceGenerator.ver;

public class NotificationList extends AppCompatActivity {
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    String id = "";
    boolean internet = true;
    private LinearLayoutManager linearLayoutManager;
    JsonArray listnotif;
    LinearLayout mback;
    private LinearLayoutManager mlinear;
    RecyclerView mlistnotif;
    TextView mnonotif;
    TextView mtitle, mreadall;
    NotificationAdapter notificationAdapter;
    ArrayList<NotificationItem> notiflist;
    String sesionid_new = "";
    public static String clockin1 = "";

    ProgressDialog loading;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
     mback = findViewById(R.id.backbtn);
    mtitle = findViewById(R.id.title);
    mlistnotif = findViewById(R.id.listnotif);
    mnonotif = findViewById(R.id.nonotif);
    mreadall = findViewById(R.id.readall);
    linearLayoutManager = new LinearLayoutManager(NotificationList.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        mlistnotif.setLayoutManager(linearLayoutManager);
        mlistnotif.setHasFixedSize(true);
    notiflist = new ArrayList();

    Bundle bundle2 = this.getIntent().getExtras();
        if (bundle2 != null) {
            clockin1 = bundle2.getString("clockin");
        this.id = bundle2.getString("id");
    }
    getSessionId();
    cekInternet();
        if (internet){
//            reqApi();
        loadNotiflist();
    }else {

    }
        mback.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    });
        mreadall.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ReadAll();

        }
    });
}
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) NotificationList.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(NotificationList.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void loadNotiflist(){
        loading = ProgressDialog.show(NotificationList.this, "", "loading...", true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.notifications(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                if (statusnya.equals("OK")){
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    //HEADER
                    listnotif = data.getAsJsonArray("engineerNotificationList");
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<NotificationItem>>() {
                    }.getType();
                    notiflist = gson.fromJson(listnotif.toString(), listType);
                    notificationAdapter = new NotificationAdapter(NotificationList.this, notiflist);
                    mlistnotif.setAdapter(notificationAdapter);
                    if (listnotif.size() == 0) {
                        mnonotif.setVisibility(View.VISIBLE);
                        mlistnotif.setVisibility(View.GONE);

                    }else {
                        mnonotif.setVisibility(View.GONE);
                        mlistnotif.setVisibility(View.VISIBLE);
                    }
                    loading.dismiss();

                }else {
                    sesionid();
                    loading.dismiss();
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(NotificationList.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(NotificationList.this, errornya.toString(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(NotificationList.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");

    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
//                Intent gotoupdate = new Intent(NotificationList.this, UpdateActivity.class);
//                startActivity(gotoupdate);
//                finish();
            }

        }else {
            startActivity(new Intent(NotificationList.this, Login.class));
            finish();
//            Toast.makeText(NotificationList.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (clockin1.equals("yes")) {
            startActivity(new Intent((Context)this, ClockInActivity.class));
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
        }else {
            startActivity(new Intent((Context)this, Home.class));
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
        }

    }
    public void ReadAll(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.ReadAll(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                sesionid();
                if (statusnya.equals("OK")){
                    loadNotiflist();

                }else {
                    sesionid();
                    loading.dismiss();
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(NotificationList.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(NotificationList.this, errornya.toString(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(NotificationList.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
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
                        startActivity(new Intent(NotificationList.this, ClockInActivity.class));
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
                    Toast.makeText(NotificationList.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
//                mrefresh.setVisibility(View.VISIBLE);
//                mcheck.setVisibility(View.GONE);
                Toast.makeText(NotificationList.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading.dismiss();
            }
        });

    }
//    private void showDialogreadall() {
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                this);
//
//        // set title dialog
//        alertDialogBuilder.setTitle(getString(R.string.title_readAll));
//
//        // set pesan dari dialog
//        alertDialogBuilder
//                .setCancelable(false)
//                .setPositiveButton(getString(R.string.title_yes),new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int id) {
//                        // jika tombol diklik, maka akan menutup activity ini
//                        ReadAll();
//                    }
//                })
//                .setNegativeButton(getString(R.string.title_no),new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // jika tombol ini diklik, akan menutup dialog
//                        // dan tidak terjadi apa2
//                        dialog.cancel();
//                    }
//                });
//
//        // membuat alert dialog dari builder
//        AlertDialog alertDialog = alertDialogBuilder.create();
//
//        // menampilkan alert dialog
//        alertDialog.show();
//    }
}