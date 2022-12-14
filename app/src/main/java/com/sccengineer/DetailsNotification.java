package com.sccengineer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sccengineer.apihelper.IRetrofit;
import com.sccengineer.apihelper.ServiceGenerator;
import com.sccengineer.messagecloud.check;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sccengineer.apihelper.ServiceGenerator.baseurl;
import static com.sccengineer.apihelper.ServiceGenerator.ver;

public class DetailsNotification extends AppCompatActivity {
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    String id = "";
    String guid = "";
    boolean internet = true;
    private LinearLayoutManager linearLayoutManager;
    LinearLayout mback;
    TextView mcontent;
    private LinearLayoutManager mlinear;
    RecyclerView mlistnotif;
    TextView mtitle;
    String sesionid_new = "";
    String username = "";
    String Title = "";
    String Content = "";
    String home="-";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_notification);
        mback = findViewById(R.id.backbtn);
        mtitle = (TextView)this.findViewById(R.id.title);
        mcontent = (TextView)this.findViewById(R.id.content);
        Bundle bundle2 = this.getIntent().getExtras();
        if (bundle2 != null) {
            home = bundle2.getString("home");
            id = bundle2.getString("id");
            guid = bundle2.getString("guid");
            username = bundle2.getString("username");
            Title = bundle2.getString("title");
            Content = bundle2.getString("body");
            Log.d("guide",guid);
//            Toast.makeText(DetailsNotification.this, guid,Toast.LENGTH_LONG).show();
        }
        getSessionId();
        cekInternet();
        if (internet){
//            reqApi();
            ReadNotif();
            mtitle.setText(Title);
            mcontent.setText(Content);
        }
        else {

        }
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
//                if (username.length()==0){
//                    startActivity(new Intent(DetailsNotification.this, Dashboard.class));
//                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
//                    finish();
//                }else {
//                    onBackPressed();
//
//                }
            }
        });
    }
    public void loadNotif(){
        cekInternet();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("guid", guid);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONgetnotifget(jsonObject);
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
                    String string3 = data.get("title").getAsString();
                    String string4 = data.get("message").getAsString();
                    mtitle.setText((CharSequence)string3);
                    mcontent.setText((CharSequence)string4);

                }else {
                    sesionid();
                    if (MsessionExpired.equals("true")) {
                        Toast.makeText(DetailsNotification.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsNotification.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();


            }
        });
        Log.d("nottifreq",jsonObject.toString());
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) DetailsNotification.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(DetailsNotification.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");

    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
//                Intent gotoupdate = new Intent(DetailsNotification.this, UpdateActivity.class);
//                startActivity(gotoupdate);
//                finish();
            }

        }else {
            startActivity(new Intent(DetailsNotification.this, Login.class));
            finish();
            Toast.makeText(DetailsNotification.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        if (home.equals("home")){
            Intent back = new Intent(DetailsNotification.this,Home.class);
//            back.putExtra("pos",valuefilter);
            startActivity(back);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
        } else if (home.equals("yes")) {
            Intent back = new Intent(DetailsNotification.this,ClockInActivity.class);
//            back.putExtra("pos",valuefilter);
            startActivity(back);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
        } else {
            if (check.checknotif == 1) {
                if (username == null) {
                    if (check.checklistform == 1) {
//                    list2.clear();
//                    refresh=true;
                    }
                    super.onBackPressed();
                    finish();

                } else {
                    super.onBackPressed();
//            refresh=true;
                    Intent back = new Intent(DetailsNotification.this, NotificationList.class);
//                back.putExtra("pos",valuefilter);
                    startActivity(back);
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    finish();
                }
            } else {
                Intent back = new Intent(DetailsNotification.this, Home.class);
//            back.putExtra("pos",valuefilter);
                startActivity(back);
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                finish();
            }
        }


    }
    public void ReadNotif(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("guid",guid);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.Read(jsonObject);
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
                    sesionid();
                    loadNotif();
                    JsonObject data = homedata.getAsJsonObject("data");
//                    String message = data.get("message").getAsString();
//                    Toast.makeText(DetailsNotification.this, message,Toast.LENGTH_LONG).show();

                }else {
                    sesionid();
//                    loading.dismiss();
                    if (MsessionExpired.equals("true")) {
                        Toast.makeText(DetailsNotification.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsNotification.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading.dismiss();

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
                        startActivity(new Intent(DetailsNotification.this, ClockInActivity.class));
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
                    Toast.makeText(DetailsNotification.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
//                mrefresh.setVisibility(View.VISIBLE);
//                mcheck.setVisibility(View.GONE);
                Toast.makeText(DetailsNotification.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading.dismiss();
            }
        });

    }
}