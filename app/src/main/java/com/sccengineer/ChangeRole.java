package com.sccengineer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.database.core.view.Change;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sccengineer.apihelper.IRetrofit;
import com.sccengineer.apihelper.ServiceGenerator;
import com.sccengineer.notificationclock.NotifclockAdapter;
import com.sccengineer.notificationclock.NotifclockItems;
import com.sccengineer.onproghome.OnProghome_adapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sccengineer.apihelper.ServiceGenerator.baseurl;

public class ChangeRole extends AppCompatActivity {
    String rolname = "";
    TextView mcurrentrole;

    ProgressDialog loading;
    LinearLayout mcheck, mrefresh, mlogout;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    Boolean internet = false;
    Boolean exit = false;
    String sesionid_new = "";
    TextView mclockin,mnewnotif;
    ArrayList<NotifclockItems> notifhomeItems;
    OnProghome_adapter onProghome_adapter;
    NotifclockAdapter notifhomeAdapter;
    JsonArray listformreq,listformreq2;
    RecyclerView myitem_place;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    Spinner mrole;
    LinearLayoutManager linearLayoutManager, linearLayoutManager2, linearLayoutManager3;

    JsonArray rolejson;
    List<String> rolelist = new ArrayList();
    List<String> rolecvalue = new ArrayList();
    String valerole = "";
    String longi = "";
    String lati = "";
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView mlatesclock,mnotif2, msubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_role);
        mcurrentrole=findViewById(R.id.curentrole);
        mrole =findViewById(R.id.rolenya);
        msubmit = findViewById(R.id.save);
        Bundle bundle2 = this.getIntent().getExtras();
        if (bundle2 != null) {
            rolname = bundle2.getString("role");
            mcurrentrole.setText(rolname);
//
//            Toast.makeText(DetailsNotification.this, guid,Toast.LENGTH_LONG).show();
        }
        rolelist.add("-Pilih Role-");
        rolecvalue.add("-");
        cekInternet();
        getSessionId();
        if (internet){
            prefcllock();

        }else {
        }
 mrole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
     @Override
     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         cekInternet();
         for (int i = 0; i < rolecvalue.size(); ++i) {
             valerole = rolecvalue.get(position);

         }
     }

     @Override
     public void onNothingSelected(AdapterView<?> parent) {

     }
 });
        msubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changerole();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, Home.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
    public void prefcllock() {
        loading = ProgressDialog.show(this, "", "", true);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.prefclock(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("prefclock",homedata.toString());
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
                    rolejson = data.getAsJsonArray("engineerRoles");
                    for (int i = 0; i < rolejson.size(); ++i) {
                        JsonObject jsonObject3 = (JsonObject) rolejson.get(i);
                        String string3 = jsonObject3.getAsJsonObject().get("Value").getAsString();
                        String string4 = jsonObject3.getAsJsonObject().get("Text").getAsString();
                        if (rolname.equals(string4)){

                        }else {
                            rolecvalue.add(string3);
                            rolelist.add(string4);
                        }

                        for (int j = 0; j < rolecvalue.size(); ++j) {
//                            if (rolecvalue.get(i).equals(valuefilter)){
//                                pos=j;
//                            }
                        }

                    }
//                    mrefresh.setVisibility(View.GONE);
//                    mcheck.setVisibility(View.GONE);

                    ArrayAdapter arrayAdapter = new ArrayAdapter(ChangeRole.this, R.layout.spinstatus_layout, rolelist);
                    arrayAdapter.setDropDownViewResource(R.layout.spinkategori);
                    arrayAdapter.notifyDataSetChanged();
                    mrole.setAdapter(arrayAdapter);
//        txt_nama    = (EditText) dialogView.findViewById(R.id.txt_nama);

                }else {
//                    mrefresh.setVisibility(View.VISIBLE);
//                    mcheck.setVisibility(View.GONE);
                    sesionid();
                    //// error message
                    loading.dismiss();
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ChangeRole.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ChangeRole.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();
//                mrefresh.setVisibility(View.VISIBLE);
//                mcheck.setVisibility(View.GONE);
            }
        });
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
            startActivity(new Intent(ChangeRole.this, Login.class));
            finish();
//            Toast.makeText(Home.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");
        Log.d("session",sesionid_new);

    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager)ChangeRole.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(ChangeRole.this,NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    private void changerole() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
//        alertDialogBuilder.setTitle(getString(R.string.title_reopendialod));

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Anda yakin ingin Ganti Role?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.title_yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
//                        clockoutnya();
                        changerolenya();
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
    public void changerolenya(){
        loading = ProgressDialog.show(this, "", "", true);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        jsonObject.addProperty("newRoleCd",valerole);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.changerole(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("changerole22",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
//                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")) {
                    loading.dismiss();
                    JsonObject data = homedata.getAsJsonObject("data");
                    Intent gohome = new Intent(ChangeRole.this,Home.class);
                    startActivity(gohome);
                    finish();
                    Toast.makeText(ChangeRole.this, data.get("message").getAsString(), Toast.LENGTH_SHORT).show();

                }else {

                    sesionid();
                    //// error message
                    loading.dismiss();
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ChangeRole.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ChangeRole.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();
            }
        });
        Log.d("reqclockout",jsonObject.toString());
    }
}