package com.sccengineer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
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
import com.google.firebase.messaging.FirebaseMessagingService;
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
    int PERMISSION_CODE = 1000;
    boolean gallery = true;
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
    TelephonyManager telephonyManager;
    String imeiHp="";
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
//        if (ActivityCompat.checkSelfPermission(Login.this,
//                Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
//            telephonyManager = (TelephonyManager) getSystemService(Login.this.TELEPHONY_SERVICE);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                imeiHp = android.provider.Settings.Secure.getString(
//                        Login.this.getContentResolver(),
//                        android.provider.Settings.Secure.ANDROID_ID);
//            } else {
//                imeiHp = telephonyManager.getDeviceId();
//            }
////            imeiHp=telephonyManager.getDeviceId();
//            Log.d("imei",imeiHp);
//        }else {
//            ActivityCompat.requestPermissions(Login.this
//                    , new String[]{Manifest.permission.READ_PHONE_STATE},100);
//        }
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
//                if (internet){
//                    loginApi();
//                }else {
//                    cekInternet();
//                }
                if (ActivityCompat.checkSelfPermission(Login.this,
                        Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    telephonyManager = (TelephonyManager) getSystemService(Login.this.TELEPHONY_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        imeiHp = android.provider.Settings.Secure.getString(
                                Login.this.getContentResolver(),
                                android.provider.Settings.Secure.ANDROID_ID);
                    } else {
                        imeiHp = telephonyManager.getDeviceId();
                    }
//                    Log.i("OmSai ", "Single or Dula Sim "+telephonyManager.getPhoneCount());
//                    Log.i("OmSai ", "Defualt device ID "+telephonyManager.getDeviceId());
//                    Log.i("OmSai ", "Single 1 "+telephonyManager.getDeviceId(0));
//                    Log.i("OmSai ", "Single 2 "+telephonyManager.getDeviceId(1));
                    if ((ContextCompat.checkSelfPermission(Login.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

                        ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
                        return;

                    }else {
                        Log.d("imei",imeiHp);
                        Log.d("token1",token);
                        if (internet){
                            loginApi();
                        }else {
                            cekInternet();
                        }
                    }

                }else {
                    ActivityCompat.requestPermissions(Login.this
                            , new String[]{Manifest.permission.READ_PHONE_STATE},100);
                }

//                Toast.makeText(LoginActivity.this, osHp+"-"+ModelHp, Toast.LENGTH_SHORT).show();

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
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        jsonObject.addProperty("model",ModelHp);
        jsonObject.addProperty("osversion",osHp);
        jsonObject.addProperty("imei",imeiHp);
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
                Log.d("jsonlogin1",post.toString());
                ////////////
                if (statusnya.equals("OK")){
                    loading.dismiss();
                    JsonObject data = post.getAsJsonObject("data");
                    language = data.get("languageCd").getAsString();
                    String sessionId = data.get("sessionId").getAsString();
                    String user = data.get("username").getAsString();
                    SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("session_id", sessionId);
                    editor.putString("user",user);
                    editor.apply();
                    setLocale(language);
//                    Toast.makeText(Login.this, sessionId, Toast.LENGTH_SHORT).show();

                    Intent gohome = new Intent(Login.this,ClockInActivity.class);
                    startActivity(gohome);
                    finish();
                }else{

                    //// error message
                    loading.dismiss();
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Login.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                    Toast.makeText(EditProfile.this, sesionid_new.toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Login.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                cekInternet();
                Toast.makeText(Login.this, t.toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        Log.d("jsonlogin",jsonObject.toString());

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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 100 && grantResults.length>0 && (grantResults[0]
                == PackageManager.PERMISSION_GRANTED)){

            if ((ContextCompat.checkSelfPermission(Login.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

                ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
                return;

            }else {

            }
        }else {
            if (ActivityCompat.checkSelfPermission(Login.this,
                    Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                telephonyManager = (TelephonyManager) getSystemService(Login.this.TELEPHONY_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    imeiHp = android.provider.Settings.Secure.getString(
                            Login.this.getContentResolver(),
                            android.provider.Settings.Secure.ANDROID_ID);
                } else {

                    if (requestCode == 1000 && grantResults.length>0 && (grantResults[0]
                            == PackageManager.PERMISSION_GRANTED)){



                    }else {
                        Toast.makeText(this, "Akses Internal di perlukan", Toast.LENGTH_LONG).show();
                    }
                }
//

            }else {
                Toast.makeText(this, "Akses Telpon Wajib", Toast.LENGTH_LONG).show();

            }




        }
    }

}