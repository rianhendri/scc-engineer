package com.sccengineer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.sccengineer.notifikasihome.NotifhomeAdapter;
import com.sccengineer.notifikasihome.NotifhomeItems;
import com.sccengineer.onproghome.OnProgHome_items;
import com.sccengineer.onproghome.OnProghome_adapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.Permissions;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sccengineer.apihelper.ServiceGenerator.baseurl;
import static com.sccengineer.apihelper.ServiceGenerator.ver;

public class Home extends AppCompatActivity {
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    Boolean internet = false;
    Boolean exit = false;
    String sesionid_new = "";
    String notifications_new = "";
    public static String news_new = "";
    boolean notes = true;
    boolean survey = true;
    LinearLayout mclockout;
    public static ArrayList<OnProgHome_items> onProgHome_items;
    ArrayList<NotifhomeItems> notifhomeItems;
    OnProghome_adapter onProghome_adapter;
    NotifhomeAdapter notifhomeAdapter;
    JsonArray listformreq,listformreq2;
    RecyclerView myitem_place;
    private LinearLayoutManager linearLayoutManager, linearLayoutManager2, linearLayoutManager3;
    RecyclerView mymenu, mnotificationconfig;
    ArrayList<MenuItem> menuItemlist;
    MenuAdapter addmenu;
    TextView mchangerole,mclockintime,mrolehome,mnotif2, mversion, mtotalprog,mtotalassigned,mtotalhistory, mnameprog,mnameasigned,mnamehistory, mnameeng, mnewnotif;
    LinearLayout loading;
    SwipeRefreshLayout mswip;
    LinearLayout mnotif1;
    LinearLayout mstonprod, mstass, mstdone;

    boolean changests = true;
    String rolname="";
    String longi = "";
    String lati = "";
    FusedLocationProviderClient fusedLocationProviderClient;
    // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
    String city2 = "";
    String state2 = "";
    String country2 = "";
    String postalCode2 = "";
    String knownName2 = "";
    String alamat ="";
    boolean clok = false;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loading = findViewById(R.id.mloading);
        mclockout = findViewById(R.id.clockout);
        mchangerole = findViewById(R.id.changerole);
        mclockintime = findViewById(R.id.clockintime);
        mrolehome = findViewById(R.id.rolehome);
        mstonprod = findViewById(R.id.stonprog);
        mstass = findViewById(R.id.stass);
        mstdone = findViewById(R.id.stdone);
        myitem_place = findViewById(R.id.assignmentprogress);
        mtotalprog = findViewById(R.id.totalprog);
        mtotalassigned = findViewById(R.id.totalass);
        mtotalhistory = findViewById(R.id.totalhis);
        mnameprog = findViewById(R.id.nameprog);
        mnameasigned = findViewById(R.id.nameass);
        mnamehistory = findViewById(R.id.namehis);
        mnameeng = findViewById(R.id.nameeng);
        mnewnotif = findViewById(R.id.newnotif);
        mymenu = findViewById(R.id.menuhome);
        mversion = findViewById(R.id.version_name);
        mswip= findViewById(R.id.swiprefresh);
        mnotif1 = findViewById(R.id.notifikasi);
        mnotif2 = findViewById(R.id.notif2);
        mnotificationconfig = findViewById(R.id.notificationconfig);
        linearLayoutManager = new LinearLayoutManager(Home.this, LinearLayout.HORIZONTAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        myitem_place.setLayoutManager(linearLayoutManager);
        myitem_place.setHasFixedSize(true);
        onProgHome_items = new ArrayList<OnProgHome_items>();
        linearLayoutManager3 = new LinearLayoutManager(Home.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        mnotificationconfig.setLayoutManager(linearLayoutManager3);
        mnotificationconfig.setHasFixedSize(true);
        notifhomeItems = new ArrayList<NotifhomeItems>();
        cekInternet();
        getSessionId();
        check.checknotif=1;
        if (internet){
            reqApi();
//            appInstalledOrNot("com.whatsapp");
//            appInstalledOrNot2("com.whatsapp.w4b");
        }else {

        }
        mrolehome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Home.this, String.valueOf(changests), Toast.LENGTH_SHORT).show();
                if (changests){
                    Intent gotonews = new Intent(Home.this, ChangeRole.class);
                    gotonews.putExtra("role", rolname);
                    startActivity(gotonews);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    finish();
                }else {

                }

            }
        });
        mclockout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    return;
                }else{
                    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Home.this);
                    getCurrentLocation();
                    clockout();
//                    Toast.makeText(ClockInActivity.this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
                }else{
                    showGPSDisabledAlertToUser();
                }
                    // Write you code here if permission already given.

                }


////                lempar = false;
//                }else {
//                    showGPSDisabledAlertToUser();
//                }
//                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Home.this);
//                    getCurrentLocation();
//                    clockout();
////                DialogForm();
////                    Toast.makeText(ClockInActivity.this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
//                }else{
//                    showGPSDisabledAlertToUser();
//                }

            }
        });
        String versionName = BuildConfig.VERSION_NAME;
        mversion.setText("SCC Engineer - "+" Version: "+ versionName);

        int color = getResources().getColor(R.color.colorPrimary);
        mswip.setColorSchemeColors(color);
        mswip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cekInternet();

                if (internet){

                    new Handler().postDelayed(new Runnable() {
                        @Override public void run() {

                            // Berhenti berputar/refreshing

                            mswip.setRefreshing(false);
                            reqApi();

                            // fungsi-fungsi lain yang dijalankan saat refresh berhenti

                        }
                    }, 500);

                }else {
//                    mswip.setEnabled(false);
//                    mswip.setRefreshing(false);
//                    mcontent.setVisibility(View.GONE);


                }
            }
        });

        mnotif1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotonews = new Intent(Home.this, NotificationList.class);
                gotonews.putExtra("clockin", "home");
                    startActivity(gotonews);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    finish();
            }
        });
        mnotif2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotonews = new Intent(Home.this, NotificationList.class);
                startActivity(gotonews);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                finish();
            }
        });
        mstdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotonews = new Intent(Home.this, ServiceTicket.class);
                gotonews.putExtra("filter", "Assigned");
                gotonews.putExtra("home", "homes");
                startActivity(gotonews);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                finish();
            }
        });
        mstass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotonews = new Intent(Home.this, ServiceTicket.class);
                gotonews.putExtra("filter", "OnProgress");
                gotonews.putExtra("home", "homes");
                startActivity(gotonews);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                finish();
            }
        });
        mstonprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotonews = new Intent(Home.this, ServiceTicket.class);
                gotonews.putExtra("filter", "ALLWAITING");
                gotonews.putExtra("home", "homes");
                startActivity(gotonews);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                finish();
            }
        });
    }
//    public boolean appInstalledOrNot(String string2) {
//        PackageManager packageManager = this.getPackageManager();
//        try {
//            packageManager.getPackageInfo(string2, packageManager.GET_ACTIVITIES);
//            installed = true;
//        }
//        catch (PackageManager.NameNotFoundException nameNotFoundException) {
//            installed = false;
//
//        }
//        return installed;
//    }
//    public boolean appInstalledOrNot2(String bisnis) {
//        PackageManager packageManager = this.getPackageManager();
//        try {
//            packageManager.getPackageInfo(bisnis, packageManager.GET_ACTIVITIES);
//            installed2 = true;
//        }
//        catch (PackageManager.NameNotFoundException nameNotFoundException) {
//            installed2 = false;
//
//        }
//        return installed2;
//    }
final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                       @NonNull int[] grantResults) {
//    switch (requestCode) {
//        case REQUEST_CODE_ASK_PERMISSIONS:
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Home.this);
//                getCurrentLocation();
//            } else {
//                // Permission Denied
//                Toast.makeText(this, "Akses Lokasi Diperlukan", Toast.LENGTH_LONG).show();
//
//            }
//            break;
//        default:
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Home.this);
        getCurrentLocation();
    } else {
        // Permission Denied
        Toast.makeText(this, "Akses Lokasi Diperlukan", Toast.LENGTH_LONG).show();

    }
//    if (requestCode == 100 && grantResults.length>0 && (grantResults[0]+grantResults[1]
//            == PackageManager.PERMISSION_GRANTED)){
//
//
//
//    }else {
//
//
//    }
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
            startActivity(new Intent(Home.this, Login.class));
            finish();
//            Toast.makeText(Home.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");
        Log.d("session",sesionid_new);

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
                        geocoder = new Geocoder(Home.this, Locale.getDefault());

                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            city2 = addresses.get(0).getLocality();
                            state2 = addresses.get(0).getAdminArea();
                            country2 = addresses.get(0).getCountryName();
                            postalCode2 = addresses.get(0).getPostalCode();
                            knownName2 = addresses.get(0).getFeatureName();
                            alamat=city2+" "+state2+" "+country2+" "+postalCode2+" "+knownName2;
                            Log.d("alamatnya22",alamat);
//                            if (clok){
//                                clockout();
//                            }else {
//
//                            }
//                            clok=false;
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
    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Lokasi perlu diaktifkan")
                .setCancelable(false)
                .setPositiveButton("Go to GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
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
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager)Home.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(Home.this,NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void reqApi() {
        loading .setVisibility(View.VISIBLE);

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
                Log.d("configeng",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
//                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")) {
                    loading .setVisibility(View.GONE);
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    boolean clocksts = data.get("alreadyClockIn").getAsBoolean();
                    if (clocksts){

                    }else {
                        startActivity(new Intent(Home.this, ClockInActivity.class));
                        finish();


                    }
                    if (data.get("alreadyClockIn").getAsBoolean()){
                        String string2 = data.get("clockInDateTime").getAsString();;


                        String string7 = data.get("clockInDateTime").getAsString();
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
                            mclockintime.setText("Clock In: "+string6+" "+time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (data.get("showChangeRole").getAsBoolean()){
                            changests = true;
                            String html = "<p>Role: "+data.get("roleName").getAsString()+"   "+"<u>"+data.get("changeRoleText").getAsString()+"</u></p>";
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                mrolehome.setEnabled(true);
                                mrolehome.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT));
                        } else {
                                mrolehome.setEnabled(false);
                                mrolehome.setText(Html.fromHtml(html));

                        }


                        }else {
                            changests = false;
                            mrolehome.setText("Role: "+data.get("roleName").getAsString()+"   \n"+data.get("changeRoleText").getAsString());
                        }
                        rolname = data.get("roleName").getAsString();

                    }else {

                    }

                    JsonObject dataprog = data.getAsJsonObject("waitingBadge");
                    JsonObject dataassigned = data.getAsJsonObject("onProgressBadge");
                    JsonObject datahistory = data.getAsJsonObject("assignedBadge");
                    listformreq = dataprog.getAsJsonArray("List");
//                    mrecord.setText("Record: "+totalrec);
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<OnProgHome_items>>() {
                    }.getType();
                    onProgHome_items = gson.fromJson(listformreq.toString(), listType);
                    onProghome_adapter = new OnProghome_adapter(Home.this, onProgHome_items);
                    myitem_place.setAdapter(onProghome_adapter);
                    myitem_place.setVisibility(View.VISIBLE);
                    String totalrec = String.valueOf(dataprog.get("Total").getAsInt()) ;
                    mtotalprog.setText(totalrec);
                    mnameprog.setText(dataprog.get("Name").getAsString());
                    String colorname = "#"+dataprog.get("NameTextColor").getAsString();
                    mnameprog.setTextColor(Color.parseColor(colorname));

                    String colorassgi = "#"+dataassigned.get("NameTextColor").getAsString();
                    mnameasigned.setText(dataassigned.get("Name").getAsString());
                    mnameasigned.setTextColor(Color.parseColor(colorassgi));
                    String totalasig = String.valueOf(dataassigned.get("Total").getAsInt()) ;
                    mtotalassigned.setText(totalasig);

                    String colorhistoy = "#"+datahistory.get("NameTextColor").getAsString();
                    mnamehistory.setText(datahistory.get("Name").getAsString());
                    mnamehistory.setTextColor(Color.parseColor(colorhistoy));
                    String totalshis = String.valueOf(datahistory.get("Total").getAsInt()) ;
                    mtotalhistory.setText(totalshis);

                    mnameeng.setText(data.get("engineerName").getAsString());
                    mnewnotif.setText(String.valueOf(data.get("newNotification").getAsInt()));

                    linearLayoutManager2 = new GridLayoutManager(Home.this, 2);
                    mymenu.setLayoutManager(linearLayoutManager2);
                    Configuration orientation = new Configuration();

                    mymenu.setHasFixedSize(true);
                    MenuItem menuItem = new MenuItem();
                    MenuItem menuItem2 = new MenuItem();
                    MenuItem menuItem3 = new MenuItem();
                    MenuItem menuItem4 = new MenuItem();
                    MenuItem menuItem5 = new MenuItem();
                    MenuItem menuItem6 = new MenuItem();
                    new MenuItem();
                    MenuItem menuItem7 = new MenuItem();
                    menuItemlist = new ArrayList();
                    String mshowServiceTicket = data.get("showServiceTicket").toString();
                    String mshowAttendance = data.get("showAttendance").toString();
                    String mshowSettings = data.get("showSettings").toString();
                    MenuAdapter.counter = data.get("showApprovalCounter").getAsInt();
                    if (mshowServiceTicket.equals("true")){
                        menuItem.setMenuname("Assignment");
                        menuItem.setImg(R.drawable.ticket);
                        menuItem.setShow(mshowServiceTicket);
                        menuItemlist.add(menuItem);
                    }

//                    if (mshowPurchaseOrderPO.equals("false") && mshowPurchaseOrderFOC.equals("false")){
//
//                    }else{
//                        menuItem2.setMenuname(getString(R.string.title_purchase_order));
//                        menuItem2.setImg(R.drawable.purchase);
//                        menuItem2.setShow("true");
//                        menuItemlist.add(menuItem2);
//                    }

                    if (mshowAttendance.equals("true")){
                        menuItem3.setMenuname("Attendance");
                        menuItem3.setImg(R.drawable.scheduling);
                        menuItem3.setShow(mshowAttendance);
                        menuItemlist.add(menuItem3);
                    }
                    if (mshowSettings.equals("true")){
                        menuItem4.setMenuname(getString(R.string.title_Setting));
                        menuItem4.setImg(R.drawable.settings);
                        menuItem4.setShow(mshowSettings);
                        menuItemlist.add(menuItem4);
                    }
                    if (data.get("showApproval").getAsBoolean()){
                        menuItem2.setMenuname("Change Role Request");
                        menuItem2.setImg(R.drawable.check);
                        menuItem2.setShow(mshowServiceTicket);
                        menuItemlist.add(menuItem2);
                    }
//                    if (mshowSurvey.equals("true")){
//                        menuItem4.setMenuname(getString(R.string.title_survei));
//                        menuItem4.setImg(R.drawable.survey);
//                        menuItem4.setShow(mshowSurvey);
//                        menuItemlist.add(menuItem4);
//                    }
//                    if (mshowNews.equals("true")){
//                        menuItem5.setMenuname(getString(R.string.title_News));
//                        menuItem5.setImg(R.drawable.news);
//                        menuItem5.setShow(mshowNews);
//                        menuItemlist.add(menuItem5);
//                    }
//                    if (mshowLiveChat.equals("true")){
//                        menuItem6.setMenuname(getString(R.string.title_live_chat));
//                        menuItem6.setImg(R.drawable.wa);
//                        menuItem6.setShow(mshowLiveChat);
//                        menuItemlist.add(menuItem6);
//                    }
//                    if (mshowSettings.equals("true")){
//                        menuItem7.setMenuname(getString(R.string.title_Setting));
//                        menuItem7.setImg(R.drawable.settings);
//                        menuItem7.setShow(mshowSettings);
//                        menuItemlist.add(menuItem7);
//                    }\
                    addmenu = new MenuAdapter(Home.this, menuItemlist);
                    mymenu.setAdapter(addmenu);
                    float width3 = getResources().getDimension(R.dimen.height3);
                    float width4 = getResources().getDimension(R.dimen.height4);
                    float width5 = getResources().getDimension(R.dimen.height5);
                    float width6 = getResources().getDimension(R.dimen.height6);
                    if(mymenu.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        if (menuItemlist.size()>4)
                        { mymenu.getLayoutParams().height = (int) width3;
                        }
                        else if( menuItemlist.size()>6) {
                            mymenu.getLayoutParams().height = (int) width4;
                        }else if (menuItemlist.size()>8) {
                            mymenu.getLayoutParams().height = (int) width5;
                        }else if( menuItemlist.size()>10) {
                            mymenu.getLayoutParams().height = (int) width6;
                        }
                        mymenu.setNestedScrollingEnabled(false);
                        mymenu.setLayoutManager(new GridLayoutManager(Home.this, 2));
                    } else if (mymenu.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        mymenu.setNestedScrollingEnabled(true);
                        mymenu.setLayoutManager(new GridLayoutManager(Home.this, 3));
                        ViewGroup.LayoutParams params=mymenu.getLayoutParams();
                        params.height=700;
                        mymenu.setLayoutParams(params);
                    }


                    //Notifikasi list
                    listformreq2 = data.getAsJsonArray("notificationList");
                    Gson gson2 = new Gson();
                    Type listType2 = new TypeToken<ArrayList<NotifhomeItems>>() {
                    }.getType();
                    notifhomeItems = gson2.fromJson(listformreq2.toString(), listType2);
                    notifhomeAdapter = new NotifhomeAdapter(Home.this, notifhomeItems);
                    mnotificationconfig.setAdapter(notifhomeAdapter);
                    mnotificationconfig.setVisibility(View.VISIBLE);
                }else {
                    sesionid();
                    //// error message
                    loading .setVisibility(View.GONE);
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Home.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading .setVisibility(View.GONE);

            }
        });
    }
    public void onBackPressed(){
        if (exit) {
            this.finish();

        } else {
            Toast.makeText(this, getString(R.string.title_exit),
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }



    }
    private void clockout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
//        alertDialogBuilder.setTitle(getString(R.string.title_reopendialod));

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Anda yakin ingin Clock Out?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.title_yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        clockoutnya();

                        // jika tombol diklik, maka akan menutup activity ini
//                        if (alamat.equals("")){
//                            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//                            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//                                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Home.this);
//                                getCurrentLocation();
////                                clockout();
////                    Toast.makeText(ClockInActivity.this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
//                            }else{
//                                showGPSDisabledAlertToUser();
//                            }
//                            Toast.makeText(Home.this, "mohon periksa koneksi anda", Toast.LENGTH_SHORT).show();
////                            Toast.makeText(Home.this, alamat, Toast.LENGTH_SHORT).show();
//
//                        }else {
//                            clockoutnya();
//                        }


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
    public void clockoutnya(){
        loading .setVisibility(View.VISIBLE);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        jsonObject.addProperty("longitude",longi);
        jsonObject.addProperty("latitude",lati);
        jsonObject.addProperty("location",alamat);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.clockout(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("CLOCKOUTNYA",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
//                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")) {
                    loading .setVisibility(View.GONE);

                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    Intent gohome = new Intent(Home.this,ClockOutActivity.class);
                    startActivity(gohome);
                    finish();
                }else {

                    sesionid();
                    //// error message
                    loading .setVisibility(View.GONE);

//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Home.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading .setVisibility(View.GONE);

            }
        });
        Log.d("reqclockout",jsonObject.toString());
    }

}