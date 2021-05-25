package com.sccengineer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sccengineer.apihelper.IRetrofit;
import com.sccengineer.apihelper.ServiceGenerator;
import com.sccengineer.jadwal.JadwalAdapter;
import com.sccengineer.jadwal.JadwalItems;
import com.sccengineer.messagecloud.check;
import com.sccengineer.notificationclock.NotifclockAdapter;
import com.sccengineer.notificationclock.NotifclockItems;
import com.sccengineer.onproghome.OnProghome_adapter;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sccengineer.apihelper.ServiceGenerator.baseurl;

public class AttendanceActivity extends AppCompatActivity {
    LinearLayout mback;
    ProgressBar loading;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    Boolean internet = false;
    Boolean exit = false;
    String sesionid_new = "";
    String periodnya = "";
    TextView mbulanprev,mbulanutama,mbulannext,mtahun;

    ArrayList<JadwalItems> notifhomeItems;
    OnProghome_adapter onProghome_adapter;
    public static JadwalAdapter notifhomeAdapter;
    public  static int posisinya=0;
    JsonArray jsonttendance;
    public static RecyclerView myitem_place;
    LinearLayoutManager linearLayoutManager, linearLayoutManager2, linearLayoutManager3;

    int bulanini = 0;
    int bulanprev = 0;
    int bulannext = 0;
    ImageView mnexticn,mprevicn;
    LinearLayout mbtnprev,mnextbtn;
    String date1="";
    String datereq="";
    int posi=0;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        mback=findViewById(R.id.backbtn);
        loading = findViewById(R.id.progressbar);
        mnextbtn = findViewById(R.id.nextbtn);
        mbtnprev = findViewById(R.id.prefbtn);
        mnexticn = findViewById(R.id.nexticn);
        mprevicn = findViewById(R.id.previcn);
        mbulanutama = findViewById(R.id.bulanutama);
        mbulanprev = findViewById(R.id.bulanprev);
        mbulannext = findViewById(R.id.bulannext);
        mtahun = findViewById(R.id.tahun);
//        mcheck = findViewById(R.id.checkclock);
//        mlatesclock = findViewById(R.id.latesclock);
        myitem_place = findViewById(R.id.listjadwal);
//        mclockin = findViewById(R.id.clockin);
//        mnewnotif = findViewById(R.id.newnotif);
        linearLayoutManager3 = new LinearLayoutManager(AttendanceActivity.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        myitem_place.setLayoutManager(linearLayoutManager3);
        myitem_place.setHasFixedSize(true);
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        date1 = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());

        cekInternet();
        getSessionId();
//        check.checknotif=1;
        if (internet){

            bulanini = Integer.parseInt(date1);
            bulanprev = Integer.parseInt(date1)-1;
            bulannext = Integer.parseInt(date1)+1;
            datereq = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
            periodnya = datereq+"-0"+bulanini+"-01 00:00:00";
            Log.d("bulan",String.valueOf(bulanini));
            loadbulan();
            loadAttendance();

        }else {
        }
        mnextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bulanini==Integer.parseInt(date1)){
                    mnexticn.setImageDrawable(ContextCompat.getDrawable(AttendanceActivity.this, R.drawable.ic_right_arrowgrey));
                }else {
                    mnexticn.setImageDrawable(ContextCompat.getDrawable(AttendanceActivity.this, R.drawable.ic_right_arrow));

                    bulanini += 1;
                    bulanprev += 1;
                    bulannext += 1;
                    if (bulanini==Integer.parseInt(date1)){
                        mnexticn.setImageDrawable(ContextCompat.getDrawable(AttendanceActivity.this, R.drawable.ic_right_arrowgrey));
                    }
                    loadbulan();
                    if (String.valueOf(bulanini).equals("1")){
                        mprevicn.setImageDrawable(ContextCompat.getDrawable(AttendanceActivity.this, R.drawable.ic_right_arrowgrey));
                    }else {
                        mprevicn.setImageDrawable(ContextCompat.getDrawable(AttendanceActivity.this, R.drawable.ic_right_arrow));

                    }
                    periodnya = datereq+"-0"+bulanini+"-01 00:00:00";
                    loadAttendance();

                }

            }
        });
        mbtnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(bulanini).equals("1")){
                    mprevicn.setImageDrawable(ContextCompat.getDrawable(AttendanceActivity.this, R.drawable.ic_right_arrowgrey));
                }else {

                    bulanini -= 1;
                    bulanprev -= 1;
                    bulannext -= 1;
                    mprevicn.setImageDrawable(ContextCompat.getDrawable(AttendanceActivity.this, R.drawable.ic_right_arrow));
                    if (String.valueOf(bulanini).equals("1")){
                        mprevicn.setImageDrawable(ContextCompat.getDrawable(AttendanceActivity.this, R.drawable.ic_right_arrowgrey));
                    }
                    loadbulan();
                    if (bulanini==Integer.parseInt(date1)){
                        mnexticn.setImageDrawable(ContextCompat.getDrawable(AttendanceActivity.this, R.drawable.ic_right_arrowgrey));
                    }else {
                        mnexticn.setImageDrawable(ContextCompat.getDrawable(AttendanceActivity.this, R.drawable.ic_right_arrow));

                    }
                    periodnya = datereq+"-0"+bulanini+"-01 00:00:00";
                    loadAttendance();


                }

            }
        });
    }
    public void loadbulan(){
        if (bulanini==1){mbulanutama.setText("Januari");
        }
        if (bulanini==2){mbulanutama.setText("Februari");
        }
        if (bulanini==3){mbulanutama.setText("Maret");
        }
        if (bulanini==4){mbulanutama.setText("April");
        }
        if (bulanini==5){mbulanutama.setText("Mei");
        }
        if (bulanini==6){ mbulanutama.setText("Juni");
        }
        if (bulanini==7){mbulanutama.setText("Juli");
        }
        if (bulanini==8){mbulanutama.setText("Agustus");
        }
        if (bulanini==9){mbulanutama.setText("September");
        }
        if (bulanini==10){mbulanutama.setText("Oktober");
        }
        if (bulanini==11){mbulanutama.setText("November");
        }
        if (bulanini==12){mbulanutama.setText("Desember");
        }
        if (bulanprev==0){mbulanprev.setText("");
        }
        if (bulanprev==1){mbulanprev.setText("Januari");
        }
        if (bulanprev==2){mbulanprev.setText("Februari");
        }
        if (bulanprev==3){mbulanprev.setText("Maret");
        }
        if (bulanprev==4){mbulanprev.setText("April");
        }
        if (bulanprev==5){mbulanprev.setText("Mei");
        }
        if (bulanprev==6){ mbulanprev.setText("Juni");
        }
        if (bulanprev==7){mbulanprev.setText("Juli");
        }
        if (bulanprev==8){mbulanprev.setText("Agustus");
        }
        if (bulanprev==9){mbulanprev.setText("September");
        }
        if (bulanprev==10){mbulanprev.setText("Oktober");
        }
        if (bulanprev==11){mbulanprev.setText("November");
        }
        if (bulanprev==12){mbulanprev.setText("Desember");
        }

        if (bulannext==2){mbulannext.setText("Februari");
        }
        if (bulannext==3){mbulannext.setText("Maret");
        }
        if (bulannext==4){mbulannext.setText("April");
        }
        if (bulannext==5){mbulannext.setText("Mei");
        }
        if (bulannext==6){ mbulannext.setText("Juni");
        }
        if (bulannext==7){mbulannext.setText("Juli");
        }
        if (bulannext==8){mbulannext.setText("Agustus");
        }
        if (bulannext==9){mbulannext.setText("September");
        }
        if (bulannext==10){mbulannext.setText("Oktober");
        }
        if (bulannext==11){mbulannext.setText("November");
        }
        if (bulannext==12){mbulannext.setText("Desember");
        }
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
            startActivity(new Intent(AttendanceActivity.this, Login.class));
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
        ConnectivityManager connectivityManager = (ConnectivityManager)AttendanceActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(AttendanceActivity.this,NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void loadAttendance() {
       loading.setVisibility(View.VISIBLE);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        jsonObject.addProperty("period",periodnya);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.attendancelist(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("clockinnya",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
//                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")) {
                    loading.setVisibility(View.GONE);
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    jsonttendance = data.getAsJsonArray("attendanceList");
                    Gson gson2 = new Gson();
                    Type listType2 = new TypeToken<ArrayList<JadwalItems>>() {
                    }.getType();
                    notifhomeItems = gson2.fromJson(jsonttendance.toString(), listType2);
                    notifhomeAdapter = new JadwalAdapter(AttendanceActivity.this, notifhomeItems);
                    myitem_place.setAdapter(notifhomeAdapter);
                    myitem_place.setVisibility(View.VISIBLE);
                    String date1a = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
                    if (bulanini==Integer.parseInt(date1)){
                         posi = Integer.parseInt(date1a)-1;
                        myitem_place.scrollToPosition(posi);
                    }
                    else {
                        posi = 0;
                        myitem_place.scrollToPosition(posi);
                    }



                }else {

                    sesionid();
                    //// error message
                    loading.setVisibility(View.GONE);

//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(AttendanceActivity.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(AttendanceActivity.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.setVisibility(View.GONE);

            }
        });
        Log.d("clockinjs",jsonObject.toString());
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, Home.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
}