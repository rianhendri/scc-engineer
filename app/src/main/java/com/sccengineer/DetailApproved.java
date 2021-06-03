package com.sccengineer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;
import com.sccengineer.apihelper.IRetrofit;
import com.sccengineer.apihelper.ServiceGenerator;
import com.sccengineer.messagecloud.check;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sccengineer.apihelper.ServiceGenerator.baseurl;

public class DetailApproved extends AppCompatActivity {
    LinearLayout mback;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    Boolean internet = false;
    Boolean exit = false;
    String sesionid_new = "";
    TextView mnameeng, mcurrent, mchange, msubmit, mapprov, mreject, mreqdate;
    Spinner mapproved;
    String nameeng="";
    String guid="";
    String fromrole="";
    String torole="";
    String fromrolecd="";
    String torolecd="";
    String reqdate="";
    List<String> rolelist = new ArrayList();
    List<Boolean> rolecvalue = new ArrayList();
    String approvalsts ="";
    Boolean sts = true;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_approved);
        mreqdate = findViewById(R.id.reqdate);
        mback = findViewById(R.id.backbtn);
        mnameeng = findViewById(R.id.nameeng);
        mcurrent = findViewById(R.id.curentrole);
        mchange = findViewById(R.id.changerole);
        msubmit = findViewById(R.id.save);
        mapprov = findViewById(R.id.approve);
        mreject = findViewById(R.id.reject);
//        mapproved = findViewById(R.id.stsaprov);

        Bundle bundle2 = this.getIntent().getExtras();
        if (bundle2 != null) {
            nameeng = bundle2.getString("nameeng");
            reqdate = bundle2.getString("reqdate").replace("Request: ","");
            guid = bundle2.getString("guid");
            fromrole = bundle2.getString("from");
            torole = bundle2.getString("to");
            fromrolecd = bundle2.getString("fromcd");
            torolecd = bundle2.getString("tocd");
            mnameeng.setText(nameeng);
            mcurrent.setText(fromrole);
            mchange.setText(torole);
            mreqdate.setText(reqdate);
//            Toast.makeText(DetailsNotification.this, guid,Toast.LENGTH_LONG).show();
        }
        cekInternet();
        getSessionId();
//        check.checknotif=1;
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mapprov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (internet) {
                    approvalsts = " Approved request ini?";
                    sts = true;
                    submitdialo();
                } else {

                }
            }});
        mreject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internet){
                    approvalsts = " Reject request ini?";
                    sts = false;
                    submitdialo();
//            appInstalledOrNot("com.whatsapp");
//            appInstalledOrNot2("com.whatsapp.w4b");
                }else {

                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, ApprovalActivity.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
    private void submitdialo() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
//        alertDialogBuilder.setTitle(getString(R.string.title_reopendialod));

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Anda yakin ingin"+approvalsts)
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.title_yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
//                        clockoutnya();
                        sendrespon();
                    }
                })
                .setNegativeButton(getString(R.string.title_no),new DialogInterface.OnClickListener() {
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
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");
        Log.d("session",sesionid_new);

    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager)DetailApproved.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(DetailApproved.this,NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

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
            startActivity(new Intent(DetailApproved.this, Login.class));
            finish();
//            Toast.makeText(Home.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    public void sendrespon(){
        loading = ProgressDialog.show(this, "", "Send request...");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        jsonObject.addProperty("guid",guid);
        jsonObject.addProperty("fromRoleCd",fromrolecd);
        jsonObject.addProperty("toRoleCd",torolecd);
        jsonObject.addProperty("approve",sts);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.responsea(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("responnya",homedata.toString());
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
                    Intent gohome = new Intent(DetailApproved.this,Home.class);
                    startActivity(gohome);
                    finish();
                }else {

                    sesionid();
                    //// error message
                    loading.dismiss();

//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(DetailApproved.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailApproved.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
        Log.d("reqclockout",jsonObject.toString());
    }
}