package com.sccengineer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.rtt.CivicLocationKeys;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sccengineer.apihelper.IRetrofit;
import com.sccengineer.apihelper.ServiceGenerator;
import com.sccengineer.menuhome.MenuAdapter;
import com.sccengineer.menuhome.MenuItem;
import com.sccengineer.messagecloud.check;
import com.sccengineer.notificationclock.NotifclockAdapter;
import com.sccengineer.notificationclock.NotifclockItems;
import com.sccengineer.notifikasihome.NotifhomeAdapter;
import com.sccengineer.notifikasihome.NotifhomeItems;
import com.sccengineer.onproghome.OnProgHome_items;
import com.sccengineer.onproghome.OnProghome_adapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sccengineer.apihelper.ServiceGenerator.baseurl;

public class ClockInActivity extends AppCompatActivity {
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
    String address =""; // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
    String city = "";
    String state = "";
    String country = "";
    String postalCode = "";
    String knownName = "";
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView mlatesclock,mnotif2;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_in);
        mlogout = findViewById(R.id.logout);
        mnotif2 = findViewById(R.id.notif2);
        mrefresh = findViewById(R.id.refresh);
        mcheck = findViewById(R.id.checkclock);
        mlatesclock = findViewById(R.id.latesclock);
        myitem_place = findViewById(R.id.notificationconfig);
        mclockin = findViewById(R.id.clockin);
        mnewnotif = findViewById(R.id.newnotif);
        linearLayoutManager3 = new LinearLayoutManager(ClockInActivity.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        myitem_place.setLayoutManager(linearLayoutManager3);
        myitem_place.setHasFixedSize(true);
        rolelist.add("-Pilih Role-");
        rolecvalue.add("-");
        cekInternet();
        getSessionId();
        check.checknotif=1;
        if (internet){
//            if (ActivityCompat.checkSelfPermission(ClockInActivity.this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                    && ActivityCompat.checkSelfPermission(ClockInActivity.this
//                    ,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                if (internet){
//                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ClockInActivity.this);
//                    getCurrentLocation();
//                }
//
////                lempar = false;
//            }else {
//                showGPSDisabledAlertToUser();
//            }
            reqApi();
//            appInstalledOrNot("com.whatsapp");
//            appInstalledOrNot2("com.whatsapp.w4b");
        }else {
        }
        mclockin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ClockInActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(ClockInActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ClockInActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    return;
                }else{
                    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ClockInActivity.this);
                        getCurrentLocation();
                        DialogForm();
//                    Toast.makeText(ClockInActivity.this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
                    }else{
                        showGPSDisabledAlertToUser();
                    }
                    // Write you code here if permission already given.

                }


//                if (ActivityCompat.checkSelfPermission(ClockInActivity.this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                        && ActivityCompat.checkSelfPermission(ClockInActivity.this
//                        ,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    if (internet){
//                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ClockInActivity.this);
//                        getCurrentLocation();
//                        DialogForm();
//                    }
//
////                lempar = false;
//                }else {
//                    showGPSDisabledAlertToUser();
//                }
//                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ClockInActivity.this);
//                    getCurrentLocation();
//                    DialogForm();
////                    Toast.makeText(ClockInActivity.this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
//                }else{
//                    showGPSDisabledAlertToUser();
//                }

            }
        });
        mrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqApi();
            }
        });
        mnotif2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gohome = new Intent(ClockInActivity.this,NotificationList.class);
                gohome.putExtra("clockin", "yes");
                startActivity(gohome);
                finish();
            }
        });
        mlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekInternet();
                if (internet){
                    logout();
                }else {

                }
            }
        });
        }
    public void logout(){
        loading = ProgressDialog.show(ClockInActivity.this, "", "", true);
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
                    Intent gotologin = new Intent(ClockInActivity.this,Login.class);
                    startActivity(gotologin);
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    finish();
                    loading.dismiss();

                }else {
                    sesionid();
                    loading.dismiss();
                    if (MsessionExpired.equals("true")) {
                        Toast.makeText(ClockInActivity.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ClockInActivity.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 100 && grantResults.length>0 && (grantResults[0]+grantResults[1]
                == PackageManager.PERMISSION_GRANTED)){
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ClockInActivity.this);
            getCurrentLocation();
//            if (reopen){
//                showDialogreopen();
//            }else {
//                showDialogrupdate();
//            }

        }else {
//            if (lempar){
//
//                Intent back = new Intent(ClockInActivity.this,Home.class);
////            back.putExtra("pos",valuefilter);
//                startActivity(back);
//                overridePendingTransition(R.anim.left_in, R.anim.right_out);
//                finish();
//            }else {
//
//            }
            Toast.makeText(this, "Akese Lokasi Diperlukan", Toast.LENGTH_LONG).show();

        }
    }
    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Lokasi perlu diaktifkan")
                .setCancelable(false)
                .setPositiveButton("Go to GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                ActivityCompat.requestPermissions(ClockInActivity.this
                                        , new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                                        },100);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
//        alertDialogBuilder.setTitle(getString(R.string.title_reopendialod));

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Anda yakin ingin Clockin?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.title_yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        Intent gohome = new Intent(ClockInActivity.this,Home.class);
                        startActivity(gohome);
                        finish();
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

    private void DialogForm() {
        dialog = new AlertDialog.Builder(ClockInActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_clockin, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Pilih Role");
        mrole = dialogView.findViewById(R.id.rolenya);
        String[] arraySpinner = new String[]{
                "-", "Role1"," Role2"
        };

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinstatus_layout, rolelist);
        arrayAdapter.setDropDownViewResource(R.layout.spinkategori);
        arrayAdapter.notifyDataSetChanged();
        mrole.setAdapter(arrayAdapter);
//        txt_nama    = (EditText) dialogView.findViewById(R.id.txt_nama);

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
//        kosong();

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (valerole.equals("-")){
                    Toast.makeText(ClockInActivity.this, "Wajib Pilih Role", Toast.LENGTH_SHORT).show();
                }else {
                    clockinnya();
//                    if (postalCode.equals("")){
//
//                    }else {
//                        getCurrentLocation();
//                        Toast.makeText(ClockInActivity.this, "mohon periksa koneksi anda", Toast.LENGTH_SHORT).show();
//                    }
                   ;

                }



                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<android.location.Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    android.location.Location location = task.getResult();
                    if (location != null) {
                        lati=String.valueOf(location.getLatitude());
                        longi = String.valueOf(location.getLongitude());
                        Geocoder geocoder;
                        List<Address> addresses;
                        geocoder = new Geocoder(ClockInActivity.this, Locale.getDefault());

                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            city = addresses.get(0).getLocality();
                            state = addresses.get(0).getAdminArea();
                            country = addresses.get(0).getCountryName();
                            postalCode = addresses.get(0).getPostalCode();
                            knownName = addresses.get(0).getFeatureName();
                            Log.d("alamatnya",city+"/"+state+"/"+country+"/"+postalCode+"/"+knownName);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                        Log.d("long2i",lati+longi);
//                        mlati.setText(String.valueOf(location.getLatitude()));
//                        mlongi.setText(String.valueOf(location.getLongitude()));
                    } else {
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(1000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                android.location.Location location1 = locationResult.getLastLocation();
//                                mlati.setText(String.valueOf(location1.getLatitude()));
//                                mlongi.setText(String.valueOf(location1.getLongitude()));
                                lati=String.valueOf(location1.getLatitude());
                                longi = String.valueOf(location1.getLongitude());
                                Log.d("long2i",lati+"-"+longi);
                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                                locationCallback, Looper.myLooper());
                    }

                }
            });

        } else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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
            startActivity(new Intent(ClockInActivity.this, Login.class));
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
        ConnectivityManager connectivityManager = (ConnectivityManager)ClockInActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(ClockInActivity.this,NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

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
                    mrefresh.setVisibility(View.GONE);
                    mcheck.setVisibility(View.GONE);
                    Log.d("configeng",homedata.toString());
                    loading.dismiss();
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    mnewnotif.setText("("+String.valueOf(data.get("newNotification").getAsInt())+")");
                    boolean clocksts = data.get("alreadyClockIn").getAsBoolean();
                    if (data.get("latestClockOut").toString().equals("null")){
                        mlatesclock.setText("");

                    }else {
                        String string2 = data.get("latestClockOut").getAsString();;


                        String string7 = data.get("latestClockOut").getAsString();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());
                        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                        String string5 = "";
                        String string6="";
                        try {
                            string6 = simpleDateFormat2.format(simpleDateFormat.parse(string7));
                            string5 = simpleDateFormat.format(simpleDateFormat.parse(string7));
                            String[] separated = string5.split("T");
                            String time = separated[1];
                            String date = separated[0];
                            mlatesclock.setText("Latest Clock Out: "+string6+" "+time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                    if (clocksts){
                        startActivity(new Intent(ClockInActivity.this, Home.class));
                        finish();
                        mcheck.setVisibility(View.VISIBLE);
                    }else {
                        prefcllock();

                    }
                    //Notifikasi list
                    listformreq2 = data.getAsJsonArray("notificationList");
                    Gson gson2 = new Gson();
                    Type listType2 = new TypeToken<ArrayList<NotifclockItems>>() {
                    }.getType();
                    notifhomeItems = gson2.fromJson(listformreq2.toString(), listType2);
                    notifhomeAdapter = new NotifclockAdapter(ClockInActivity.this, notifhomeItems);
                    myitem_place.setAdapter(notifhomeAdapter);
                    myitem_place.setVisibility(View.VISIBLE);
                }else {
                    mrefresh.setVisibility(View.VISIBLE);
                    mcheck.setVisibility(View.GONE);
                    sesionid();
                    //// error message
                    loading.dismiss();
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ClockInActivity.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                mrefresh.setVisibility(View.VISIBLE);
                mcheck.setVisibility(View.GONE);
                Toast.makeText(ClockInActivity.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();
            }
        });

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
                        JsonObject jsonObject3 = (JsonObject)rolejson.get(i);
                        String string3 = jsonObject3.getAsJsonObject().get("Value").getAsString();
                        String string4 = jsonObject3.getAsJsonObject().get("Text").getAsString();
                        rolecvalue.add(string3);
                        rolelist.add(string4);
                        for (int j = 0; j < rolecvalue.size(); ++j) {
//                            if (rolecvalue.get(i).equals(valuefilter)){
//                                pos=j;
//                            }
                        }

                    }
                    mrefresh.setVisibility(View.GONE);
                    mcheck.setVisibility(View.GONE);
                }else {
                    mrefresh.setVisibility(View.VISIBLE);
                    mcheck.setVisibility(View.GONE);
                    sesionid();
                    //// error message
                    loading.dismiss();
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ClockInActivity.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ClockInActivity.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();
                mrefresh.setVisibility(View.VISIBLE);
                mcheck.setVisibility(View.GONE);
            }
        });
    }
    public void clockinnya() {
        loading = ProgressDialog.show(this, "", "", true);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        jsonObject.addProperty("roleCd",valerole);
        jsonObject.addProperty("longitude",longi);
        jsonObject.addProperty("latitude",lati);
        jsonObject.addProperty("location",city+" "+state+" "+country+" "+postalCode+" "+knownName);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.clockin(jsonObject);
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
                    loading.dismiss();
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    Intent gohome = new Intent(ClockInActivity.this,Home.class);
                    startActivity(gohome);
                    finish();
                }else {

                    sesionid();
                    //// error message
                    loading.dismiss();
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ClockInActivity.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ClockInActivity.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();
            }
        });
        Log.d("clockinjs",jsonObject.toString());
    }
}