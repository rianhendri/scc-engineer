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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sccengineer.apihelper.IRetrofit;
import com.sccengineer.apihelper.ServiceGenerator;
import com.sccengineer.notificationclock.NotifclockAdapter;
import com.sccengineer.notificationclock.NotifclockItems;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sccengineer.apihelper.ServiceGenerator.baseurl;
import static com.sccengineer.apihelper.ServiceGenerator.ver;

public class ChangePassword extends AppCompatActivity {
    EditText moldpassword, mnewpassword, mretypepassword;
    LinearLayout mback;
    TextView msave;
    boolean internet = true;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    String sesionid_new = "";
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mnewpassword = findViewById(R.id.newpassword);
        moldpassword = findViewById(R.id.oldpassword);
        mretypepassword = findViewById(R.id.repassword);
        msave = findViewById(R.id.save);
        mback = findViewById(R.id.backbtn);
        cekInternet();
        getSessionId();


        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        msave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moldpassword.length()==0){
                    moldpassword.setError(getString(R.string.empetypassword));
                    if (mnewpassword.length()==0){
                        mnewpassword.setError(getString(R.string.empetypassword));
                        if (mretypepassword.length()==0){
                            mretypepassword.setError(getString(R.string.empetypassword));
                        }
                    }
                }else {
                    if (mnewpassword.length()==0){
                        mnewpassword.setError(getString(R.string.empetypassword));
                        if (moldpassword.length()==0){
                            moldpassword.setError(getString(R.string.empetypassword));
                            if (mretypepassword.length()==0){
                                mretypepassword.setError(getString(R.string.empetypassword));
                            }
                        }
                    }else {
                        if (mretypepassword.length()==0){
                            mretypepassword.setError(getString(R.string.empetypassword));
                            if (moldpassword.length()==0){
                                moldpassword.setError(getString(R.string.empetypassword));
                                if (mnewpassword.length()==0){
                                    mnewpassword.setError(getString(R.string.empetypassword));
                                }
                            }
                        }else {
                            if (mnewpassword.length()<=5){
                                mnewpassword.setError(getString(R.string.title_passwordRequired));

                            }else {
                                if (mretypepassword.length()<=5){
                                    mretypepassword.setError(getString(R.string.title_passwordRequired));

                                }else {
                                    if (mnewpassword.getText().toString().equals(mretypepassword.getText().toString())){
                                        cekInternet();
                                        if (internet){
                                            changepassword();
                                        }else {
                                            cekInternet();
                                        }
                                    }else {

                                    }
                                }
                            }
                        }
                    }
                }


            }
        });
        mretypepassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mretypepassword.getText().toString().equals(mnewpassword.getText().toString())){
//                    msave.setEnabled(true);
                }else {
                    mretypepassword.setError(getString(R.string.title_Passwordwrong));


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        reqApi();
    }

    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) ChangePassword.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(ChangePassword.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void changepassword(){
        loading = ProgressDialog.show(ChangePassword.this, "", "loading...", true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("oldPassword", moldpassword.getText().toString());
        jsonObject.addProperty("newPassword", mnewpassword.getText().toString());
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.changepassword(jsonObject);
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
                    Toast.makeText(ChangePassword.this, getString(R.string.title_changepass_succsess),Toast.LENGTH_LONG).show();
                    Intent gohome = new Intent(ChangePassword.this,Home.class);
                    startActivity(gohome);
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    finish();
                    loading.dismiss();

                }else {
                    sesionid();
                    loading.dismiss();
                    if (errornya.equals((Object)"null")) {
                        cekInternet();
                    }else {
                        if (MsessionExpired.equals("true")) {
                            Toast.makeText(ChangePassword.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                        }
                        cekInternet();
                        Toast.makeText(ChangePassword.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ChangePassword.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
        Log.d("changepasswords",jsonObject.toString());
    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");

    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
//                Intent gotoupdate = new Intent(ChangePassword.this, UpdateActivity.class);
//                startActivity(gotoupdate);
//                finish();
            }

        }else {
            startActivity(new Intent(ChangePassword.this, Login.class));
            finish();
            Toast.makeText(ChangePassword.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, Settings.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
    public void reqApi() {
        loading = ProgressDialog.show(this, "", "", true);

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

                    loading.dismiss();
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");

                    boolean clocksts = data.get("alreadyClockIn").getAsBoolean();

                    if (clocksts){
                    ;
                    }else {
                        startActivity(new Intent(ChangePassword.this, ClockInActivity.class));
                        finish();
//                        mcheck.setVisibility(View.VISIBLE)

                    }
                }else {
//                    mrefresh.setVisibility(View.VISIBLE);
//                    mcheck.setVisibility(View.GONE);
                    sesionid();
                    //// error message
                    loading.dismiss();
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ChangePassword.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
//                mrefresh.setVisibility(View.VISIBLE);
//                mcheck.setVisibility(View.GONE);
                Toast.makeText(ChangePassword.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();
            }
        });

    }
}
