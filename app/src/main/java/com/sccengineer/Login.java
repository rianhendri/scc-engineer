package com.sccengineer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonObject;
import com.sccengineer.apihelper.IRetrofit;
import com.sccengineer.apihelper.ServiceGenerator;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sccengineer.apihelper.ServiceGenerator.baseurl;
import static com.sccengineer.apihelper.ServiceGenerator.ver;

public class Login extends AppCompatActivity {
    TextView mlogin, mforgotpassword, mversi;
    EditText musername,mpassword;
    String token="";
    String password="";
    String username="";
    String MsessionExpired="";
    String MhaveToUpdate="";
    ProgressDialog loading;
    String  errornya = "";
    String language = "";
    Boolean internet = true;
    String ModelHp= "";
    String osHp = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        musername = findViewById(R.id.username);
        mpassword = findViewById(R.id.password);
        mlogin = findViewById(R.id.login);
        mversi = findViewById(R.id.version_name);
        mforgotpassword = findViewById(R.id.forgotpassword);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getVersionHp();
        cekInternet();
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
//                                    Log.w(TAG, "getInstanceId failed", task.getException());
//                            musername.setText(task.getException().toString());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();
//                        musername.setText(token);


                        // Log and toast
//                                String msg = getString(R.string.msg_token_fmt, token);
//                                Log.d(TAG, msg);
//                                Toast.makeText(Regist3.this, token, Toast.LENGTH_SHORT).show();
//                                malamat.setText(token);
                    }
                });

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("token1",token);
//                Toast.makeText(LoginActivity.this, osHp+"-"+ModelHp, Toast.LENGTH_SHORT).show();
                if (internet){
                    loginApi();
                }else {
                    cekInternet();
                }
            }
        });
        String versionName = BuildConfig.VERSION_NAME;
        mversi.setText("Samafitro Smart Care Center - "+"Version "+ versionName);
    }
    public void getVersionHp(){
        ModelHp = Build.MANUFACTURER + ""+ Build.MODEL;
        osHp=  Build.VERSION.RELEASE
                + " " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager)Login.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
//            Intent noconnection = new Intent(LoginActivity.this,NoInternet.class);
        }
        //// pengecekan internet selesai

    }
    public void loginApi(){
        loading = ProgressDialog.show(Login.this, "", "Loading...", true);
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username",musername.getText().toString());
        jsonObject.addProperty("password",mpassword.getText().toString());
        jsonObject.addProperty("firebaseToken",token);
        jsonObject.addProperty("ver",ver);
        jsonObject.addProperty("model",ModelHp);
        jsonObject.addProperty("osversion",osHp);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> call = jsonPostService.postRawJSONlogin(jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject post=response.body();
                String statusnya = post.get("status").getAsString();
                if (post.get("errorMessage").toString().equals("null")){
                    errornya = post.get("errorMessage").toString();
                }else{
                    errornya = post.get("errorMessage").getAsString();
                }
                MsessionExpired = post.get("sessionExpired").toString();
                MhaveToUpdate = post.get("haveToUpdate").toString();

                ////////////
                if (statusnya.equals("OK")){
                    loading.dismiss();
                    JsonObject data = post.getAsJsonObject("data");
//                    language = data.get("languageCd").getAsString();
                    String sessionId = data.get("sessionId").getAsString();
                    String user = data.get("username").getAsString();
                    SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("session_id", sessionId);
                    editor.putString("user",user);
                    editor.apply();
//                    setLocale(language);
//                    Toast.makeText(LoginActivity.this, sessionId, Toast.LENGTH_SHORT).show();

                    Intent gohome = new Intent(Login.this,Home.class);
                    startActivity(gohome);
                    finish();
                }else{

                    //// error message
                    loading.dismiss();
                    Toast.makeText(Login.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(EditProfile.this, sesionid_new.toString(), Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                cekInternet();
                Toast.makeText(Login.this, "getString(R.string.title_excpetation)", Toast.LENGTH_SHORT).show();
//                Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

}