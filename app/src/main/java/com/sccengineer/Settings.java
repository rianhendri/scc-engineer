package com.sccengineer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sccengineer.apihelper.IRetrofit;
import com.sccengineer.apihelper.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sccengineer.apihelper.ServiceGenerator.baseurl;
import static com.sccengineer.apihelper.ServiceGenerator.ver;

public class Settings extends AppCompatActivity {
    LinearLayout mmyprofile, mchangepass, mlanguage, mlogout;
    LinearLayout mback;
    boolean internet = true;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    String sesionid_new = "";
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mmyprofile = findViewById(R.id.myprofile);
        mchangepass = findViewById(R.id.changepassword);
        mlanguage = findViewById(R.id.multilanguage);
        mlogout = findViewById(R.id.logout);
        mback = findViewById(R.id.backbtn);
        //get Session Id
        getSessionId();
        mmyprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekInternet();
                if (internet){
                    Intent gototoprofile = new Intent(Settings.this,Myprofile.class);
                    startActivity(gototoprofile);
                    finish();
                }else {

                }
            }
        });
        mchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekInternet();
                if (internet){
                    Intent gototoprofile = new Intent(Settings.this,ChangePassword.class);
                    startActivity(gototoprofile);
                    finish();
                }else {

                }
            }
        });
        mlanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekInternet();
                if (internet){
                    Intent gototoprofile = new Intent(Settings.this,Langueage.class);
                    startActivity(gototoprofile);
                    finish();
                }else {

                }
            }
        });
        mlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekInternet();
                if (internet){
                    logout();
                }else {

                }
            }
        });
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) Settings.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(Settings.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void logout(){
        loading = ProgressDialog.show(Settings.this, "", "", true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONlogout(jsonObject);
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
                    Intent gotologin = new Intent(Settings.this,Login.class);
                    startActivity(gotologin);
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    finish();
                    loading.dismiss();

                }else {
                    sesionid();
                    loading.dismiss();
                    if (MsessionExpired.equals("true")) {
                        Toast.makeText(Settings.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Settings.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
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
//                Intent gotoupdate = new Intent(Settings.this, UpdateActivity.class);
//                startActivity(gotoupdate);
//                finish();
            }

        }else {
            startActivity(new Intent(Settings.this, Login.class));
            finish();
            Toast.makeText(Settings.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, Home.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
}