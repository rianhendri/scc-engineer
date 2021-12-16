package com.sccengineer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sccengineer.apihelper.IRetrofit;
import com.sccengineer.apihelper.ServiceGenerator;
import com.sccengineer.dailyreportadd.AddDailyActionAdapter;
import com.sccengineer.dailyreportadd.AddDailyActionAdaptera;
import com.sccengineer.dailyreportadd.AddDailyActionItem;
import com.sccengineer.dailyreportadd.AddDailyFollowAdapter;
import com.sccengineer.dailyreportadd.AddDailyFollowItem;
import com.sccengineer.dailyreportadd.AddDailyTemuanAdapter;
import com.sccengineer.dailyreportadd.AddDailyTemuanItem;
import com.sccengineer.dailyreportadd.DetailsDailyAdapter4;
import com.sccengineer.dailyreportadd.DetailsDailyItem4;
import com.sccengineer.spartsend.SendSparepart_adapter;
import com.sccengineer.spartsend.SendSparepart_item;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sccengineer.DetailsST.dialog;
import static com.sccengineer.DetailsST.jsonarayitem;
import static com.sccengineer.DetailsST.msendpartlist;
import static com.sccengineer.DetailsST.myCustomArray;
import static com.sccengineer.DetailsST.sendsparepart_adapter;
import static com.sccengineer.DetailsST.sendsparepart_items;
import static com.sccengineer.ServiceTicket.valuefilter;
import static com.sccengineer.apihelper.ServiceGenerator.baseurl;

public class DailyReportAdd extends AppCompatActivity {
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    public static AddDailyTemuanAdapter addFormAdapterAdapter1;
    public static AddDailyActionAdapter addFormAdapterAdapter2;
    public  static AddDailyFollowAdapter addFormAdapterAdapter3;
    DetailsDailyAdapter4 addFormAdapterAdapter4;
    AddDailyTemuanAdapter addFormAdapterAdapter1a;
    AddDailyActionAdaptera addFormAdapterAdapter2a;
    AddDailyFollowAdapter addFormAdapterAdapter3a;
    DetailsDailyAdapter4 addFormAdapterAdapter4a;
    public static AddDailyTemuanItem tambahpart1;
    public static AddDailyActionItem tambahpart2;
    public static AddDailyFollowItem tambahpart3;
    public static ArrayList<AddDailyTemuanItem> listpoact1 = new ArrayList<AddDailyTemuanItem>();
    public static ArrayList<AddDailyActionItem> listpoact2 = new ArrayList<AddDailyActionItem>();
    public static ArrayList<AddDailyFollowItem> listpoact3= new ArrayList<AddDailyFollowItem>();

    boolean internet = true;
    private LinearLayoutManager linearLayoutManager,linearLayoutManager2,linearLayoutManager3,linearLayoutManager4,linearLayoutManager5;
    public static ArrayList<AddDailyTemuanItem> temuanlist;
    public static ArrayList<AddDailyActionItem> actionlist;
    public static ArrayList<AddDailyFollowItem> followlist;
    public static ArrayList<DetailsDailyItem4> sperpartlist;
    public static JsonArray mytemuan;
    String mytemuankirim="";
    String myactionkirim="";
    String myfollowkirim="";
    public static String jsontemuan = "";
    public static JsonArray myaction;
    public static String jsonaction = "";
    public static JsonArray myfollow;
    public static String jsonfollow = "";

    JsonArray listformreq,listformreq2,listformreq3,listformreq4,listformreq5;
    List<String> listnamestatus = new ArrayList();
    JsonArray liststatus;
    List<String> listvalue = new ArrayList();
    LinearLayout maddform;
    LinearLayout mback;
    ProgressBar mfooterload;
    private LinearLayoutManager mlinear;
    NestedScrollView mnested;
    TextView mrecord,mempetyreq,msend;
    Spinner mstatus_spin;
    public static RecyclerView place_temuan,place_action,place_follow,place_sperpart,myitem_place5;
    int page = 1;
    int pos = 0;
    int pos2 = 0;
    boolean refreshscroll = true;
    String sesionid_new = "";
    String noreq = "";
    String home = "";
    String guid = "";
    String username = "";
    String noticket = "";
    String scrollnya = "";
    Integer xhori = 0;
    Integer yverti = 0;
    TextView mcaseid,mreportdate,mpresssn,mticketlink,mservicetype;
    Spinner mcaseprogress,mpressstatus;
    EditText minputtemuan,minputaction,minputfollow;
    ImageView mplustemuan, mplusaction, mplusfollow;
    ProgressDialog loading;
    List<String> caseprogspin = new ArrayList();
    List<String> caseprogvalue = new ArrayList();
    List<String> pressvalue = new ArrayList();
    List<String> pressspin = new ArrayList();
    String caseprog="";
    String pressstatus="";

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report_add);
        mback = findViewById(R.id.backbtn);
        msend = findViewById(R.id.send);
        minputtemuan = findViewById(R.id.inputtemuan);
        minputaction = findViewById(R.id.inputaction);
        minputfollow = findViewById(R.id.inputfollow);
        mplustemuan = findViewById(R.id.tambahtemuan);
        mplusaction = findViewById(R.id.tambahaction);
        mplusfollow = findViewById(R.id.tambahfollow);
        place_temuan = findViewById(R.id.temuanlist);
        place_action = findViewById(R.id.tindakanlist);
        place_follow = findViewById(R.id.langkahlanjutanlist);
        place_sperpart = findViewById(R.id.sperpartdaily);
//        myitem_place5 = findViewById(R.id.createdby);

        mcaseid = findViewById(R.id.caseid);
        mreportdate = findViewById(R.id.reportdate);
        mpresssn = findViewById(R.id.presssn);
        mcaseprogress = findViewById(R.id.caseprogress);
        mpressstatus = findViewById(R.id.pressstatus);
        mticketlink = findViewById(R.id.ticketlink);
        mservicetype = findViewById(R.id.servicetype);

        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            noreq = bundle2.getString("id");
            home = bundle2.getString("home");
            guid = bundle2.getString("guid");
            username = bundle2.getString("user");
            noticket = bundle2.getString("id");
            valuefilter = bundle2.getString("pos");
            scrollnya =   bundle2.getString("scrolbawah");
            xhori=bundle2.getInt("xhori");
            yverti=bundle2.getInt("yverti");

        }
        String string2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        mreportdate.setText((CharSequence)string2);
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(DailyReportAdd.this, LinearLayout.VERTICAL,false);
        linearLayoutManager2 = new LinearLayoutManager(DailyReportAdd.this, LinearLayout.VERTICAL,false);
        linearLayoutManager3 = new LinearLayoutManager(DailyReportAdd.this, LinearLayout.VERTICAL,false);
        linearLayoutManager4 = new LinearLayoutManager(DailyReportAdd.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager5 = new LinearLayoutManager(DailyReportAdd.this, LinearLayout.VERTICAL,false);

        place_temuan.setLayoutManager(linearLayoutManager);
        place_temuan.setHasFixedSize(true);
        place_action.setLayoutManager(linearLayoutManager2);
        place_action.setHasFixedSize(true);
        place_follow.setLayoutManager(linearLayoutManager3);
        place_follow.setHasFixedSize(true);
        place_sperpart.setLayoutManager(linearLayoutManager4);
        place_sperpart.setHasFixedSize(true);
//        myitem_place5.setLayoutManager(linearLayoutManager5);
//        myitem_place5.setHasFixedSize(true);
        temuanlist = new ArrayList<AddDailyTemuanItem>();
        actionlist= new ArrayList<AddDailyActionItem>();
        followlist = new ArrayList<AddDailyFollowItem>();
        sperpartlist = new ArrayList<DetailsDailyItem4>();
//        list5 = new ArrayList<DetailsDailyItem5>();

        getSessionId();
        cekInternet();
        if (internet){
            preparedaily();
        }else {

        }
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mplustemuan.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                if (minputtemuan.length()==0){
                    Toast.makeText(DailyReportAdd.this, "Text tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else {

                        tambahpart1 = new AddDailyTemuanItem();
                        tambahpart1.setFinding(minputtemuan.getText().toString());
//                        tambahpart1.setText(minputtemuan.getText().toString());
                        listpoact1.add(tambahpart1);
                        temuanlist.addAll(listpoact1);
                        Gson gson = new GsonBuilder().create();
                        mytemuan = gson.toJsonTree(temuanlist).getAsJsonArray();
                        mytemuankirim = mytemuan.toString();
                        listpoact1.clear();
                        Log.d("sizecart_11", String.valueOf(temuanlist.size()));
                        Log.d("mytemuankirim", String.valueOf(mytemuankirim));
////////////////////// adapter di masukan ke recyler//
                        addFormAdapterAdapter1 = new AddDailyTemuanAdapter(DailyReportAdd.this, temuanlist);
                        place_temuan.setAdapter(addFormAdapterAdapter1);

                     }
                minputtemuan.setText("");


            }
        });
        mplusaction.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                if (minputaction.length()==0){
                    Toast.makeText(DailyReportAdd.this, "Text tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else {

                    tambahpart2 = new AddDailyActionItem();
                    tambahpart2.setActionTaken(minputaction.getText().toString());
//                    tambahpart2.setText(minputaction.getText().toString());
                    listpoact2.add(tambahpart2);
                    actionlist.addAll(listpoact2);
                    Gson gson = new GsonBuilder().create();
                    myaction = gson.toJsonTree(actionlist).getAsJsonArray();
                    myactionkirim = myaction.toString();
                    listpoact2.clear();
//                    Log.d("sizecart_11", String.valueOf(temuanlist.size()));
                    Log.d("mytemuankirim", String.valueOf(myactionkirim));
////////////////////// adapter di masukan ke recyler//
                    addFormAdapterAdapter2 = new AddDailyActionAdapter(DailyReportAdd.this, actionlist);
                    place_action.setAdapter(addFormAdapterAdapter2);

                }
                minputaction.setText("");


            }
        });
        mplusfollow.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                if (minputfollow.length()==0){
                    Toast.makeText(DailyReportAdd.this, "Text tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else {
                    tambahpart3 = new AddDailyFollowItem();
                    tambahpart3.setFollowUp(minputfollow.getText().toString());
//                    tambahpart3.setText(minputfollow.getText().toString());
                    listpoact3.add(tambahpart3);
                    followlist.addAll(listpoact3);
                    Gson gson = new GsonBuilder().create();
                    myfollow = gson.toJsonTree(followlist).getAsJsonArray();
                    myfollowkirim = myfollow.toString();
                    listpoact3.clear();
                    Log.d("sizecart_11", String.valueOf(followlist.size()));
                    Log.d("mytemuankirim", String.valueOf(myfollowkirim));
////////////////////// adapter di masukan ke recyler//
                    addFormAdapterAdapter3 = new AddDailyFollowAdapter(DailyReportAdd.this, followlist);
                    place_follow.setAdapter(addFormAdapterAdapter3);

                }
                minputfollow.setText("");


            }
        });
        msend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogrupdate();
            }
        });
        //spinner
        caseprogvalue.add("null");
        caseprogvalue.add("OnProgress");
        caseprogvalue.add("Solved");
        caseprogspin.add("-pilih-");
        caseprogspin.add("Masih Dalam Pemeriksaan");
        caseprogspin.add("Masalah Sudah Selesai");
        pressvalue.add("null");
        pressvalue.add("Printing");
        pressvalue.add("Down");
        pressspin.add("-pilih-");
        pressspin.add("Mesin Tetap Produksi");
        pressspin.add("Mesin Mati");
        final ArrayAdapter<String> kategori = new ArrayAdapter<String>(DailyReportAdd.this, R.layout.spinstatus_layout,
                caseprogspin);
        kategori.setDropDownViewResource(R.layout.spinkategori);
        kategori.notifyDataSetChanged();
        mcaseprogress.setAdapter(kategori);
        mcaseprogress.setSelection(pos);
        final ArrayAdapter<String> kategori2 = new ArrayAdapter<String>(DailyReportAdd.this, R.layout.spinstatus_layout,
                pressspin);
        kategori2.setDropDownViewResource(R.layout.spinkategori);
        kategori2.notifyDataSetChanged();
        mpressstatus.setAdapter(kategori2);
        mpressstatus.setSelection(pos2);
        mcaseprogress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                operatorname.clear();
//                operatorcd.clear();
                caseprog = caseprogvalue.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mpressstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                operatorname.clear();
//                operatorcd.clear();
                pressstatus = pressvalue.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void preparedaily(){
//        page=1;
//        mfooterload.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("serviceTicketCd",noreq);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.preparedaily(jsonObject);
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

                    mcaseid.setText(data.get("caseID").getAsString());
//                    mreportdate.setText(data.get("caseID").getAsString());
                    mpresssn.setText(data.get("pressSN").getAsString()+"("+data.get("pressType").getAsString()+")");
//                    mcaseprogress.setText(data.get("caseProgress").getAsString());
//                    mpressstatus.setText(data.get("pressStatus").getAsString());
                    mticketlink.setText(data.get("ticketUntuk").getAsString());
                    mservicetype.setText(data.get("serviceType").getAsString());

                    if (data.getAsJsonArray("temuanList")!=null){
                        listformreq = data.getAsJsonArray("temuanList");
                    }else {

                    }
                    if (data.getAsJsonArray("tindakanList")!=null){
                        listformreq2 = data.getAsJsonArray("tindakanList");
                    }else {

                    }
                    if (data.getAsJsonArray("langkahLanjutanList")!=null){
                        listformreq3 = data.getAsJsonArray("langkahLanjutanList");
                    }else {

                    }
                    if (data.getAsJsonArray("sparePartList")!=null){
                        listformreq4 = data.getAsJsonArray("sparePartList");
                    }else {

                    }


                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<AddDailyTemuanItem>>() {
                    }.getType();
                    Type listType2 = new TypeToken<ArrayList<AddDailyActionItem>>() {
                    }.getType();
                    Type listType3 = new TypeToken<ArrayList<AddDailyFollowItem>>() {
                    }.getType();
                    Type listType4 = new TypeToken<ArrayList<DetailsDailyItem4>>() {
                    }.getType();
                    Type listType5 = new TypeToken<ArrayList<DetailsDailyItem4>>() {
                    }.getType();
                    temuanlist = gson.fromJson(listformreq.toString(), listType);
                    actionlist = gson.fromJson(listformreq2.toString(), listType2);
                    followlist = gson.fromJson(listformreq3.toString(), listType3);
                    sperpartlist = gson.fromJson(listformreq4.toString(), listType4);
//                    list5 = gson.fromJson(listformreq4.toString(), listType5);
                    addFormAdapterAdapter1 = new AddDailyTemuanAdapter(DailyReportAdd.this, temuanlist);
                    addFormAdapterAdapter2 = new AddDailyActionAdapter(DailyReportAdd.this, actionlist);
                    addFormAdapterAdapter3 = new AddDailyFollowAdapter(DailyReportAdd.this, followlist);
                    addFormAdapterAdapter4 = new DetailsDailyAdapter4(DailyReportAdd.this, sperpartlist);

                    place_temuan.setAdapter(addFormAdapterAdapter1);
                    place_temuan.setVisibility(View.VISIBLE);
                    place_action.setAdapter(addFormAdapterAdapter2);
                    place_action.setVisibility(View.VISIBLE);
                    place_follow.setAdapter(addFormAdapterAdapter3);
                    place_follow.setVisibility(View.VISIBLE);
                    place_sperpart.setAdapter(addFormAdapterAdapter4);
                    place_sperpart.setVisibility(View.VISIBLE);

                    Gson gsonact = new GsonBuilder().create();
                    myaction = gsonact.toJsonTree(actionlist).getAsJsonArray();
                    jsonaction = myaction.toString();
                    Gson gsonfollow = new GsonBuilder().create();
                    myfollow = gsonfollow.toJsonTree(followlist).getAsJsonArray();
                    jsonfollow = myfollow.toString();
                    Gson gsontemuan = new GsonBuilder().create();
                    mytemuan = gsontemuan.toJsonTree(temuanlist).getAsJsonArray();
                    jsontemuan = mytemuan.toString();

                }else {
                    sesionid();
                    mfooterload.setVisibility(View.GONE);
                    Toast.makeText(DailyReportAdd.this, errornya,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DailyReportAdd.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                mfooterload.setVisibility(View.GONE);

            }
        });
        Log.d("reqlistfr",jsonObject.toString());
    }
    public void adddailireq(){
//        page=1;
//        mfooterload.setVisibility(View.VISIBLE);
        loading = ProgressDialog.show(DailyReportAdd.this, "", "loading...", true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("serviceTicketCd",noreq);
        jsonObject.addProperty("caseProgress",caseprog);
        jsonObject.addProperty("pressStatus",pressstatus);
        jsonObject.add("findings",mytemuan);
        jsonObject.add("actions",myaction);
        jsonObject.add("followups",myfollow);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.adddailireq(jsonObject);
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
                    String message = data.get("message").getAsString();
                    Toast.makeText(DailyReportAdd.this, message,Toast.LENGTH_LONG).show();
                    onBackPressed();
//                    loadData();
                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(DailyReportAdd.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(DetailsST.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DailyReportAdd.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                mfooterload.setVisibility(View.GONE);

            }
        });
        Log.d("adddailyzss",jsonObject.toString());
    }
    private void showDialogrupdate() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Add Daily Report");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage(getString(R.string.title_submitadddailymessage))
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.title_yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        adddailireq();
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
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) DailyReportAdd.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(DailyReportAdd.this, NoInternet.class);
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
//                Intent gotoupdate = new Intent(DailyReportAdd.this, UpdateActivity.class);
//                startActivity(gotoupdate);
//                finish();
            }

        }else {
            startActivity(new Intent(DailyReportAdd.this, Login.class));
            finish();
            Toast.makeText(DailyReportAdd.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(DailyReportAdd.this, DetailsST.class);
        back.putExtra("id", noreq);
        back.putExtra("home", home);
        back.putExtra("guid", guid);
        back.putExtra("user", username);
        back.putExtra("id", noticket);
        back.putExtra("pos", valuefilter);
        back.putExtra("scrolbawah", scrollnya);
        back.putExtra("xhori", xhori);
        back.putExtra("yverti", yverti);
        startActivity(back);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
}