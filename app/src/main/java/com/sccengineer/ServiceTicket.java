package com.sccengineer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sccengineer.apihelper.IRetrofit;
import com.sccengineer.apihelper.ServiceGenerator;
import com.sccengineer.messagecloud.check;
import com.sccengineer.supportservice.ServiceTicketAdapter;
import com.sccengineer.supportservice.ServiceTicketItems;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sccengineer.DetailsST.myCustomArray;
import static com.sccengineer.apihelper.ServiceGenerator.baseurl;
import static com.sccengineer.apihelper.ServiceGenerator.ver;

public class ServiceTicket extends AppCompatActivity {
    boolean load = true;
    private static final String TAG = "FormActivity";
    public static boolean refresh = false;
    public static String valuefilter = "-";
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    ServiceTicketAdapter addFormAdapterAdapter;
    boolean internet = true;
    private LinearLayoutManager linearLayoutManager;
    public static ArrayList<ServiceTicketItems> list2;
    JsonArray listformreq;
    List<String> listnamestatus = new ArrayList();
    JsonArray liststatus;
    List<String> listvalue = new ArrayList();
    LinearLayout maddform;
    LinearLayout mback;
    ProgressBar mfooterload;
    private LinearLayoutManager mlinear;
    NestedScrollView mnested;
    TextView mrecord,mempetyreq;
    Spinner mstatus_spin;
    RecyclerView myitem_place;
    int page = 1;
    int pos = 0;
    boolean refreshscroll = true;
    String sesionid_new = "";
    int spin = 0;
    List<String> spinstatus = new ArrayList();
    int totalpage = 0;
    String totalrec = "";
    SwipeRefreshLayout mswip;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_ticket);
        mrecord = findViewById(R.id.record);
        mfooterload = findViewById(R.id.footerload);
        mback = findViewById(R.id.backbtn);
        myitem_place = findViewById(R.id.formlist);
        maddform = findViewById(R.id.addform);
        mstatus_spin = findViewById(R.id.spinstatus);
        mnested = findViewById(R.id.nestedscrol);
        mempetyreq = findViewById(R.id.norequest);
        mswip = findViewById(R.id.swiprefresh);
        check.checklistform=1;
        //showadd
//        if (showaddform.equals("false")){
//            maddform.setVisibility(View.GONE);
//            myitem_place.setPadding(0,0,0,0);
//        }else {
//            maddform.setVisibility(View.VISIBLE);
//            myitem_place.setPadding(0,0,0,120);
//
//        }
        Bundle bundle2 = this.getIntent().getExtras();
        if (bundle2 != null) {
            valuefilter = bundle2.getString("pos");

//            Toast.makeText(DetailsNotification.this, guid,Toast.LENGTH_LONG).show();
        }
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(ServiceTicket.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        myitem_place.setLayoutManager(linearLayoutManager);
        myitem_place.setHasFixedSize(true);
        list2 = new ArrayList<ServiceTicketItems>();
        getSessionId();
        cekInternet();
//        refreshnotif();
        if (internet){
            reqApi();
            loadSpin();
        }else {

        }
        mnested.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {

                if(nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1) != null)
                {
                    if ((i1 >= (nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1)
                            .getMeasuredHeight() - nestedScrollView.getMeasuredHeight()))
                            && i1 > i3)
                    {
                        cekInternet();
                        if (internet){
                            if (refreshscroll){
                                page++;
                                mfooterload.setVisibility(View.VISIBLE);
                                refreshscroll=false;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable()
                                {
                                    @Override
                                    public void run() {
                                        if (page <=totalpage){
                                            myitem_place.setLayoutFrozen(true);
                                            pagination();
                                        }else {
                                            mfooterload.setVisibility(View.GONE);
                                            refreshscroll=false;
                                        }
                                    }
                                },500);

                            }

                        }else {
                                    Toast.makeText(ServiceTicket.this, "Data Sudah di tampilkan semua", Toast.LENGTH_SHORT).show();
//                                    mfooterload.setVisibility(View.GONE);
//                                    mdatahabis.setVisibility(View.GONE);
//                                    mrefreshcoba.setVisibility(View.VISIBLE);

                        }





                    }
                }

            }
        });
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mstatus_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshscroll = true;
                page=1;
                cekInternet();
                for (int i = 0; i < listvalue.size(); ++i) {
                    valuefilter = listvalue.get(position);
                    if (internet) {
                        loadData();
                    }else {

                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        maddform.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent gotonews = new Intent(ServiceTicket.this, AddRequest.class);
//                startActivity(gotonews);
//                finish();
//                overridePendingTransition(R.anim.right_in, R.anim.left_out);
//            }
//        });
        int color = getResources().getColor(R.color.colorPrimary);
        mswip.setColorSchemeColors(color);
        mswip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                cekInternet();

                if (internet){

                    new Handler().postDelayed(new Runnable() {
                        @Override public void run() {

                            // Berhenti berputar/refreshing

                            mswip.setRefreshing(false);
                            loadData();

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
    }
    public void loadData(){
        page=1;
        if (load){
            load = false;
        }else {
            mfooterload.setVisibility(View.VISIBLE);
            load = true;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("page",page);
        jsonObject.addProperty("status",valuefilter);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.ListST(jsonObject);
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
                    totalpage = data.get("totalPage").getAsInt();
                    listformreq = data.getAsJsonArray("frList");
                    totalrec = data.get("totalRec").toString();
                    mrecord.setText("Record: "+totalrec);
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<ServiceTicketItems>>() {
                    }.getType();
                    list2 = gson.fromJson(listformreq.toString(), listType);
                    addFormAdapterAdapter = new ServiceTicketAdapter(ServiceTicket.this, list2);
//                    addFormAdapterAdapter.notifyDataSetChanged();
                    myitem_place.setAdapter(addFormAdapterAdapter);
                    myitem_place.setVisibility(View.VISIBLE);
                    mfooterload.setVisibility(View.GONE);
                    if (totalpage == 1) {
                        mfooterload.setVisibility(View.GONE);
                    }
                    if (totalpage == 0) {
                        mfooterload.setVisibility(View.GONE);
                    } else if (list2 != null) {
                        list2.size();
                    }
                    if (listformreq.size() == 0) {
                        myitem_place.setVisibility(View.GONE);
                        mempetyreq.setVisibility(View.VISIBLE);

                    }else {
                        myitem_place.setVisibility(View.VISIBLE);
                        mempetyreq.setVisibility(View.GONE);
                    }
                    mfooterload.setVisibility(View.GONE);
                }else {
                    sesionid();
                    mfooterload.setVisibility(View.GONE);
                    if (MsessionExpired.equals("true")) {
                        Toast.makeText(ServiceTicket.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ServiceTicket.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                mfooterload.setVisibility(View.GONE);

            }
        });
        Log.d("listst",jsonObject.toString());
    }
    public void loadSpin(){
        mfooterload.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("page",page);
        jsonObject.addProperty("status","-");
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.ListST(jsonObject);
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
                    loadData();
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    listformreq = data.getAsJsonArray("frList");
                    liststatus = data.getAsJsonArray("statusList");
                    for (int i = 0; i < liststatus.size(); ++i) {
                        JsonObject jsonObject3 = (JsonObject)liststatus.get(i);
                        String string3 = jsonObject3.getAsJsonObject().get("Value").getAsString();
                        String string4 = jsonObject3.getAsJsonObject().get("Text").getAsString();
                        listvalue.add(string3);
                        listnamestatus.add(string4);
                        for (int j = 0; j < listvalue.size(); ++j) {
                            if (listvalue.get(i).equals(valuefilter)){
                                pos=j;
                            }
                        }
                        final ArrayAdapter<String> kategori = new ArrayAdapter<String>(ServiceTicket.this, R.layout.spinstatus_layout,
                                listnamestatus);
                        kategori.setDropDownViewResource(R.layout.spinkategori);
                        kategori.notifyDataSetChanged();
                        mstatus_spin.setAdapter(kategori);
                        mstatus_spin.setSelection(pos);
                    }
                } else {
                    sesionid();
//                    if (MsessionExpired.equals("true")) {
////                        Toast.makeText(ServiceTicket.this, errornya.toString(), Toast.LENGTH_SHORT).show();
////                    }
                    Toast.makeText(ServiceTicket.this, errornya.toString(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ServiceTicket.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                mfooterload.setVisibility(View.GONE);

            }
        });
        Log.d("reqapilista",jsonObject.toString());
    }
    public void pagination(){
        mfooterload.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("page",page);
        jsonObject.addProperty("status",valuefilter);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.ListST(jsonObject);
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
                sesionid();
                if (statusnya.equals("OK")){
                    JsonObject data = homedata.getAsJsonObject("data");
                    totalpage = data.get("totalPage").getAsInt();
                    listformreq = data.getAsJsonArray("frList");
                    totalrec = data.get("totalRec").toString();
                    mrecord.setText("Record: "+totalrec);
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<ServiceTicketItems>>() {
                    }.getType();
                    ArrayList<ServiceTicketItems> list;
                    list=new ArrayList<>();
                    list = gson.fromJson(listformreq.toString(), listType);
                    list2.addAll(list);
                    addFormAdapterAdapter = new ServiceTicketAdapter(ServiceTicket.this, list2);
                    myitem_place.setAdapter(addFormAdapterAdapter);
                    myitem_place.setVisibility(View.VISIBLE);
                    mfooterload.setVisibility(View.GONE);
                    if (totalpage == 1) {
                        mfooterload.setVisibility(View.GONE);
                    }
                    if (totalpage == 0) {
                        mfooterload.setVisibility(View.GONE);
                    } else if (list2 != null) {
                        list2.size();
                    }
                    mfooterload.setVisibility(View.GONE);
//                    page++;
                    refreshscroll=true;
                }else {

                    mfooterload.setVisibility(View.GONE);
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(ServiceTicket.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ServiceTicket.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ServiceTicket.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                mfooterload.setVisibility(View.GONE);

            }
        });
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) ServiceTicket.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(ServiceTicket.this, NoInternet.class);
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
            startActivity(new Intent(ServiceTicket.this, Login.class));
            finish();
//            Toast.makeText(ServiceTicket.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, Home.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
    public  void refreshnotif() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!refresh){
                    refreshnotif();
//
//
                }else {

                    loadData();
//                    Toast.makeText(FormActivity.this, "true",Toast.LENGTH_SHORT).show();
                    refresh=false;
                }

            }
        }, 100);

    }
    public void reqApi() {
//        loading = ProgressDialog.show(this, "", "", true);

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

//                    loading.dismiss();
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");

                    boolean clocksts = data.get("alreadyClockIn").getAsBoolean();

                    if (clocksts){
                        ;
                    }else {
                        startActivity(new Intent(ServiceTicket.this, ClockInActivity.class));
                        finish();
//                        mcheck.setVisibility(View.VISIBLE)

                    }
                }else {
//                    mrefresh.setVisibility(View.VISIBLE);
//                    mcheck.setVisibility(View.GONE);
                    sesionid();
                    //// error message
//                    loading.dismiss();
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ServiceTicket.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
//                mrefresh.setVisibility(View.VISIBLE);
//                mcheck.setVisibility(View.GONE);
                Toast.makeText(ServiceTicket.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading.dismiss();
            }
        });
        Log.d("reqapilist",jsonObject.toString());

    }
}