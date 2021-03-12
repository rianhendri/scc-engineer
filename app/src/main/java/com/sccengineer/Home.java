package com.sccengineer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.lang.reflect.Type;
import java.util.ArrayList;

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
    TextView mnotif2, mversion, mtotalprog,mtotalassigned,mtotalhistory, mnameprog,mnameasigned,mnamehistory, mnameeng, mnewnotif;
    ProgressDialog loading;
    SwipeRefreshLayout mswip;
    ConstraintLayout mnotif1;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
            Toast.makeText(Home.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");

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
        loading = ProgressDialog.show(this, "", "", true);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",ver);
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
                    JsonObject dataprog = data.getAsJsonObject("onProgressBadge");
                    JsonObject dataassigned = data.getAsJsonObject("assignedBadge");
                    JsonObject datahistory = data.getAsJsonObject("historyBadge");
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
                    if (mshowServiceTicket.equals("true")){
                        menuItem.setMenuname("Service Ticket");
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
                    //// error message
                    loading.dismiss();
                    Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Home.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();
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
}