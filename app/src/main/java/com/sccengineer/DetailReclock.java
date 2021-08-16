package com.sccengineer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sccengineer.apihelper.IRetrofit;
import com.sccengineer.apihelper.ServiceGenerator;
import com.sccengineer.reclockin.ReClockInAdapter;
import com.sccengineer.reclockin.ReclockinItems;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sccengineer.apihelper.ServiceGenerator.baseurl;

public class DetailReclock extends AppCompatActivity {
    TextView mnameeng, mrole, mlocation, mreqdate, mapprove, mreject;
    String name ="";
    String role = "";
    String guid = "";
    String date = "";
    String location = "";
    String Content = "";
    String status = "";
    int page = 1;
    String string2 = "";
    int pos = 0;
    boolean internet = true;
    boolean refreshscroll = true;
    String sesionid_new = "";
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    boolean approved = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reclock);
        mnameeng = findViewById(R.id.nameeng);
        mrole = findViewById(R.id.curentrole);
        mlocation = findViewById(R.id.location);
        mreqdate = findViewById(R.id.reqdate);
        mreject = findViewById(R.id.reject);
        mapprove = findViewById(R.id.approve);

        getSessionId();
        cekInternet();
        Bundle bundle2 = this.getIntent().getExtras();
        if (bundle2 != null) {
            name = bundle2.getString("name");
            guid = bundle2.getString("guide");
            role = bundle2.getString("role");
            date = bundle2.getString("date");
            location = bundle2.getString("location");

            Log.d("guide",guid);
            mnameeng.setText(name);
            mrole.setText(role);
            String string3 = date;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
            try {
                string2 = simpleDateFormat2.format(simpleDateFormat.parse(string3));
                System.out.println(string2);
                Log.e((String)"Date", (String)string2);
            }
            catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            mreqdate.setText((CharSequence)string2);
//            mreqdate.setText(date);
            mlocation.setText(location);
//            Toast.makeText(DetailsNotification.this, guid,Toast.LENGTH_LONG).show();
        }
        mapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internet) {
                    status = "Berhasil approve request";
                    approved = true;
                    reqclockin();
                } else {

                }

            }
        });
        mreject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internet) {
                    status = "Berhasil reject request";
                    approved = false;
                    reqclockin();
                } else {

                }
            }
        });

    }
    public void reqclockin(){


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("guid",guid);
        jsonObject.addProperty("approved",approved);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.approvreclock(jsonObject);
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
                    JsonObject data = homedata.getAsJsonObject("data");

                    Toast.makeText(DetailReclock.this, status, Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }else {
                    sesionid();
//                    mfooterload.setVisibility(View.GONE);
                    if (MsessionExpired.equals("true")) {
                        Toast.makeText(DetailReclock.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailReclock.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                mfooterload.setVisibility(View.GONE);

            }
        });
        Log.d("reqloc",jsonObject.toString());
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) DetailReclock.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(DetailReclock.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");
        SharedPreferences show = getSharedPreferences("Show",MODE_PRIVATE);
//        showaddpo = show.getString("showaddpo", "");
//        showaddfoc = show.getString("showaddfoc", "");
//        showaddform = show.getString("showaddform", "");
//        mshowPurchaseOrderPO = show.getString("mshowPurchaseOrderPO", "");
//        mshowPurchaseOrderFOC = show.getString("mshowPurchaseOrderFOC", "");


    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
//                Intent gotoupdate = new Intent(ServiceTicket.this, UpdateActivity.class);
//                startActivity(gotoupdate);
//                finish();
            }

        }else {
            startActivity(new Intent(DetailReclock.this, Login.class));
            finish();
//            Toast.makeText(ServiceTicket.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, ClockinApprovList.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
}