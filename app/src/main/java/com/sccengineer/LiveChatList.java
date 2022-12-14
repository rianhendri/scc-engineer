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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.datatransport.BuildConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sccengineer.Chat.Itemchat;
import com.sccengineer.apihelper.IRetrofit;
import com.sccengineer.apihelper.ServiceGenerator;
import com.sccengineer.approval.ApprovalAdapter;
import com.sccengineer.approval.ApprovalItem;
import com.sccengineer.livechatlist.DetailsDate;
import com.sccengineer.livechatlist.DetailsDate2;
import com.sccengineer.livechatlist.ListLiveChatAdapter;
import com.sccengineer.livechatlist.ListLiveChatItem;
import com.sccengineer.messagecloud.check;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sccengineer.DetailsST.jsonarayitem;
import static com.sccengineer.DetailsST.myCustomArray;
import static com.sccengineer.DetailsST.sendsparepart_items;
import static com.sccengineer.apihelper.ServiceGenerator.baseurl;
import static com.sccengineer.apihelper.ServiceGenerator.getchatnya;
import static com.sccengineer.menuhome.MenuAdapter.countSC;
import static com.sccengineer.messagecloud.check.tokennya2;

public class LiveChatList extends AppCompatActivity {
    int pos1 = 0;
    String Scs="";
    public static String titlenya = "List Live Chat";
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    Query lastQuery;
    public static ArrayList<DetailsDate>  itemchat = new ArrayList<DetailsDate>();
    public static ArrayList<DetailsDate2>  itemchat3 = new ArrayList<DetailsDate2>();
    JsonArray myCustomArray;
    String jsonarayitem = "";
    boolean load = true;
    private static final String TAG = "FormActivity";
    public static boolean refresh = false;
    public static String valuefilter = "-";
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    ListLiveChatAdapter addFormAdapterAdapter;
    boolean internet = true;
    private LinearLayoutManager linearLayoutManager;
    public static ArrayList<ListLiveChatItem> list2;
    JsonObject homedata2;
    JSONObject jsonchat;
    public static ArrayList<ListLiveChatItem> list3;
    JsonArray listformreq;
    List<String> listnamestatus = new ArrayList();
    JsonArray liststatus;
    List<String> listvalue = new ArrayList();
    LinearLayout maddform;
    LinearLayout mback;
    public static ProgressBar mfooterload;
    private LinearLayoutManager mlinear;
    NestedScrollView mnested;
    TextView mrecord,mempetyreq,mtitle_list;
    Spinner mstatus_spin;
    public static RecyclerView myitem_place;
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
        setContentView(R.layout.activity_live_chat_list);
        mtitle_list = findViewById(R.id.title_list);
        mrecord = findViewById(R.id.record);
        mfooterload = findViewById(R.id.footerload);
        mback = findViewById(R.id.backbtn);
        myitem_place = findViewById(R.id.listapproval);
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
            valuefilter = bundle2.getString("filter");
            Scs = bundle2.getString("cs");
            titlenya = bundle2.getString("titlelist");
//            Toast.makeText(DetailsNotification.this, guid,Toast.LENGTH_LONG).show();
        }
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(LiveChatList.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        myitem_place.setLayoutManager(linearLayoutManager);
        myitem_place.setHasFixedSize(true);
        list2 = new ArrayList<ListLiveChatItem>();
        getSessionId();
        cekInternet();
//        refreshnotif();
        if (internet){
            reqApi();
            Log.d("sccs",Scs);
            if (Scs.equals("no")){
                mtitle_list.setText(titlenya);
                loadData();

            }else {
                mtitle_list.setText(titlenya);
                loadScc();
            }

//            loadSpin();
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
//                                            pagination();
                                        }else {
                                            mfooterload.setVisibility(View.GONE);
                                            refreshscroll=false;
                                        }
                                    }
                                },500);

                            }

                        }else {
                            Toast.makeText(LiveChatList.this, "Data Sudah di tampilkan semua", Toast.LENGTH_SHORT).show();
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
                        if (Scs.equals("no")){
                            loadData();
                        }else {
                            loadScc();
                        }
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
                            if (Scs.equals("no")){
                                loadData();
                            }else {
                                loadScc();
                            }

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
//        getpostmant();
    }
    public void loadData(){
        itemchat.clear();
        list2.clear();
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
        jsonObject.addProperty("ver", BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.livechastlist(jsonObject);
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
                    totalpage = 0;
                    listformreq = data.getAsJsonArray("chatList");
                    totalrec = "0";
                    mrecord.setText("Record: "+totalrec);
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<ListLiveChatItem>>() {
                    }.getType();
                    list2 = gson.fromJson(listformreq.toString(), listType);
                    addFormAdapterAdapter = new ListLiveChatAdapter(LiveChatList.this, list2);
//                    addFormAdapterAdapter.notifyDataSetChanged();

                        if (listformreq.toString().equals("[]")){
                            mfooterload.setVisibility(View.GONE);
                        }else {

                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("sessionId","");
//        Toast.makeText(DetailsFormActivity.this,jsonObject.toString(), Toast.LENGTH_SHORT).show();
                            IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, getchatnya);
                            Call<JsonObject> panggilkomplek = jsonPostService.getjsonchat();
                            panggilkomplek.enqueue(new Callback<JsonObject>() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    homedata2=response.body();
                                    if (list2!=null){
                                        for(int x = 0; x<list2.size(); x++){
                                            pos1+=1;
                                            String errornya = "";

                                            JsonObject data = homedata2.getAsJsonObject(list2.get(x).getLiveChatID());
                                            String nameme = list2.get(x).getUserName();
                                            try {
                                                if (data!=null){
                                                    jsonchat = new JSONObject(data.get("listchat").toString());
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            if (data!=null){
                                                for(int k = 0; k<jsonchat.names().length(); k++){

                                                    try {
                                                        if (k==jsonchat.names().length()){

                                                        }
                                                        list2.get(x).setDatea(jsonchat.getJSONObject(jsonchat.names().getString(jsonchat.names().length()-1)).getString("date"));
                                                        list2.get(x).setTime(jsonchat.getJSONObject(jsonchat.names().getString(jsonchat.names().length()-1)).getString("time"));
                                                        list2.get(x).setDetails(jsonchat.getJSONObject(jsonchat.names().getString(jsonchat.names().length()-1)).getString("message"));
                                                        list2.get(x).setRead(jsonchat.getJSONObject(jsonchat.names().getString(jsonchat.names().length()-1)).getString("read"));
                                                        list2.get(x).setPengirim(jsonchat.getJSONObject(jsonchat.names().getString(jsonchat.names().length()-1)).getString("name"));

//
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                        Log.d("TAGet", e.toString());
                                                    }

                                                }
                                            }
                                            if (pos1==list2.size()){
                                                for(int u = 0; u<list2.size(); u++){
                                                    Log.d("datetimessx",list2.get(u).getDatea()+list2.get(u).getTime());
                                                }

                                                Log.d("jumlahin",String.valueOf(pos1)+"///"+String.valueOf(list2.size()));
                                                            Collections.sort(list2, new Comparator<ListLiveChatItem>() {
                                                                Locale local = Locale.ENGLISH;
                                                                DateFormat f = new SimpleDateFormat("d MMM yyyy HH:mm",local);
                                                                @Override
                                                                public int compare(ListLiveChatItem lhs, ListLiveChatItem rhs) {

                                                                    try {
                                                                        Log.d("compare","successs");
                                                                        return f.parse(lhs.getDatea()+" "+lhs.getTime()).compareTo(f.parse(rhs.getDatea()+" "+rhs.getTime()));

                                                                    } catch (ParseException e) {
                                                                        Log.d("compare",e.toString());
                                                                        throw new IllegalArgumentException(e);
                                                                    }
                                                                }
                                                            });

                                                Collections.reverse(list2);
                                                mfooterload.setVisibility(View.GONE);
                                                pos1 =0;
                                                myitem_place.setAdapter(addFormAdapterAdapter);
                                                myitem_place.setVisibility(View.VISIBLE);

                                            }
                                        }
                                    }


                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(DetailsST.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                                    cekInternet();
                                    mfooterload.setVisibility(View.GONE);
//                            loading.dismiss();

                                }
                            });
                            Log.d("loadDetailst",jsonObject.toString());
//                            lastQuery = databaseReference.child("chat").child(list2.get(pos1).getLiveChatID()).child("listchat").orderByKey().limitToLast(1);
//                            lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
////                                dataSnapshot.exists();
//                                    if (dataSnapshot.exists()){
//                                        Log.d("sss","success");
//                                        for(DataSnapshot ds: dataSnapshot.getChildren())
//                                        {
//                                            DetailsDate fetchDatalist=ds.getValue(DetailsDate.class);
////                        fetchDatalist.setKey(ds.getKey());
//                                            itemchat.add(fetchDatalist);
//
//                                            if (list2.size()==pos1+1){
//                                                list2.get(pos1).setDatea(itemchat.get(pos1).getDate());
//                                                list2.get(pos1).setTime(itemchat.get(pos1).getTime());
//                                                Collections.sort(list2, new Comparator<ListLiveChatItem>() {
//                                                    DateFormat f = new SimpleDateFormat("d MMM yyyy HH:mm");
//                                                    @Override
//                                                    public int compare(ListLiveChatItem lhs, ListLiveChatItem rhs) {
//                                                        try {
//                                                            Log.d("compare","successs");
//
//                                                            return f.parse(lhs.getDatea()+" "+lhs.getTime()).compareTo(f.parse(rhs.getDatea()+" "+rhs.getTime()));
//
//                                                        } catch (ParseException e) {
//                                                            Log.d("compare",e.toString());
//                                                            throw new IllegalArgumentException(e);
//                                                        }
//                                                    }
//                                                });
//                                                Collections.sort(itemchat, new Comparator<DetailsDate>() {
//                                                    DateFormat f = new SimpleDateFormat("d MMM yyyy HH:mm");
//                                                    @Override
//                                                    public int compare(DetailsDate lhs, DetailsDate rhs) {
//                                                        try {
//                                                            Log.d("compare","successs");
//
//                                                            return f.parse(lhs.getDate()+" "+lhs.getTime()).compareTo(f.parse(rhs.getDate()+" "+rhs.getTime()));
//
//                                                        } catch (ParseException e) {
//                                                            Log.d("compare",e.toString());
//                                                            throw new IllegalArgumentException(e);
//                                                        }
//                                                    }
//                                                });
//                                                Collections.reverse(list2);
//                                                Collections.reverse(itemchat);
//                                                pos1 =0;
//                                                myitem_place.setAdapter(addFormAdapterAdapter);
//                                                myitem_place.setVisibility(View.VISIBLE);
//                                                mfooterload.setVisibility(View.GONE);
//                                            }else {
//                                                list2.get(pos1).setDatea(itemchat.get(pos1).getDate());
//                                                list2.get(pos1).setTime(itemchat.get(pos1).getTime());
//                                                pos1 +=1;
//                                                showdetail();
//
//                                            }
//
//                                        }
////                recyclerView.scrollToPosition(adapterchat.getItemCount());
//                                    }else{
//                                        Log.d("sss","gagal");
//                                        if (list2.size()==pos1+1){
//                                            pos1 =0;
//                                            myitem_place.setAdapter(addFormAdapterAdapter);
//                                            myitem_place.setVisibility(View.VISIBLE);
//                                            mfooterload.setVisibility(View.GONE);
//                                        }else {
//                                            pos1 +=1;
//                                            showdetail();
//
//                                        }
//                                        mfooterload.setVisibility(View.GONE);
//                                    }
////                String message = dataSnapshot.child("message").getValue().toString();
////                myItem.get(i).setDetails(message);
////                myviewholder.mdetailchat.setText(myItem.get(i).getDetails());
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//                                    // Handle possible errors.
//                                    mfooterload.setVisibility(View.GONE);
//                                }
//                            });
                        }


                }else {
                    sesionid();
                    mfooterload.setVisibility(View.GONE);
                    if (MsessionExpired.equals("true")) {
                        Toast.makeText(LiveChatList.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(LiveChatList.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                mfooterload.setVisibility(View.GONE);

            }
        });
        Log.d("livechatlistreq",jsonObject.toString());
    }
    public void loadScc(){
        itemchat.clear();
        list2.clear();
        page=1;
        if (load){
            load = false;
        }else {
//            mfooterload.setVisibility(View.VISIBLE);
            load = true;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
//        jsonObject.addProperty("page",page);
//        jsonObject.addProperty("status",valuefilter);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.livecslist(jsonObject);
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
                    totalpage = 0;
                    listformreq = data.getAsJsonArray("chatList");
                    totalrec = "0";
                    mrecord.setText("Record: "+totalrec);
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<ListLiveChatItem>>() {
                    }.getType();
                    list2 = gson.fromJson(listformreq.toString(), listType);
                    addFormAdapterAdapter = new ListLiveChatAdapter(LiveChatList.this, list2);
//                    addFormAdapterAdapter.notifyDataSetChanged();

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("sessionId","");
//        Toast.makeText(DetailsFormActivity.this,jsonObject.toString(), Toast.LENGTH_SHORT).show();
                    IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, getchatnya);
                    Call<JsonObject> panggilkomplek = jsonPostService.getjsonchat();
                    panggilkomplek.enqueue(new Callback<JsonObject>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            homedata2=response.body();
                            if (list2!=null){


                                for(int x = 0; x<list2.size(); x++){
                                    pos1+=1;
                                    String errornya = "";

                                    JsonObject data = homedata2.getAsJsonObject(list2.get(x).getLiveChatID());
                                    String nameme = list2.get(x).getUserName();
                                    try {
                                        if (data!=null){
                                            jsonchat = new JSONObject(data.get("listchat").toString());
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (data!=null){
                                        for(int k = 0; k<jsonchat.names().length(); k++){

                                            try {
                                                if (k==jsonchat.names().length()){

                                                }
                                                list2.get(x).setDatea(jsonchat.getJSONObject(jsonchat.names().getString(jsonchat.names().length()-1)).getString("date"));
                                                list2.get(x).setTime(jsonchat.getJSONObject(jsonchat.names().getString(jsonchat.names().length()-1)).getString("time"));
                                                list2.get(x).setDetails(jsonchat.getJSONObject(jsonchat.names().getString(jsonchat.names().length()-1)).getString("message"));
                                                list2.get(x).setRead(jsonchat.getJSONObject(jsonchat.names().getString(jsonchat.names().length()-1)).getString("read"));
                                                list2.get(x).setPengirim(jsonchat.getJSONObject(jsonchat.names().getString(jsonchat.names().length()-1)).getString("name"));

//
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Log.d("TAGet", e.toString());
                                            }

                                        }
                                    }
                                    if (pos1==list2.size()){
                                        for(int u = 0; u<list2.size(); u++){
                                            Log.d("datetimessx",list2.get(u).getDatea()+list2.get(u).getTime());
                                        }

                                        Log.d("jumlahin",String.valueOf(pos1)+"///"+String.valueOf(list2.size()));
                                        Collections.sort(list2, new Comparator<ListLiveChatItem>() {
                                            Locale local = Locale.ENGLISH;
                                            DateFormat f = new SimpleDateFormat("d MMM yyyy HH:mm",local);
                                            @Override
                                            public int compare(ListLiveChatItem lhs, ListLiveChatItem rhs) {

                                                try {
                                                    Log.d("compare","successs");
                                                    return f.parse(lhs.getDatea()+" "+lhs.getTime()).compareTo(f.parse(rhs.getDatea()+" "+rhs.getTime()));

                                                } catch (ParseException e) {
                                                    Log.d("compare",e.toString());
                                                    throw new IllegalArgumentException(e);
                                                }
                                            }
                                        });

                                        Collections.reverse(list2);

                                        pos1 =0;
                                        mfooterload.setVisibility(View.GONE);
                                        myitem_place.setAdapter(addFormAdapterAdapter);
                                        myitem_place.setVisibility(View.VISIBLE);

                                    }
                                }
                            }else {

                            }


                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(DetailsST.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                            cekInternet();
                            mfooterload.setVisibility(View.GONE);
//                            loading.dismiss();

                        }
                    });
                    Log.d("loadDetailst",jsonObject.toString());

//                    lastQuery = databaseReference.child("chat").child(list2.get(pos1).getLiveChatID()).child("listchat").orderByKey().limitToLast(1);
//                    lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
////                                dataSnapshot.exists();
//                            if (dataSnapshot.exists()){
//                                Log.d("sss","success");
//                                for(DataSnapshot ds: dataSnapshot.getChildren())
//                                {
//                                    DetailsDate fetchDatalist=ds.getValue(DetailsDate.class);
////                        fetchDatalist.setKey(ds.getKey());
//                                    itemchat.add(fetchDatalist);
//
//                                    if (list2.size()==pos1+1){
//                                        list2.get(pos1).setDatea(itemchat.get(pos1).getDate());
//                                        list2.get(pos1).setTime(itemchat.get(pos1).getTime());
//                                        Collections.sort(list2, new Comparator<ListLiveChatItem>() {
//                                            DateFormat f = new SimpleDateFormat("d MMM yyyy HH:mm");
//                                            @Override
//                                            public int compare(ListLiveChatItem lhs, ListLiveChatItem rhs) {
//                                                try {
//                                                    Log.d("compare","successs");
//
//                                                    return f.parse(lhs.getDatea()+" "+lhs.getTime()).compareTo(f.parse(rhs.getDatea()+" "+rhs.getTime()));
//
//                                                } catch (ParseException e) {
//                                                    Log.d("compare",e.toString());
//                                                    throw new IllegalArgumentException(e);
//                                                }
//                                            }
//                                        });
//                                        Collections.sort(itemchat, new Comparator<DetailsDate>() {
//                                            DateFormat f = new SimpleDateFormat("d MMM yyyy HH:mm");
//                                            @Override
//                                            public int compare(DetailsDate lhs, DetailsDate rhs) {
//                                                try {
//                                                    Log.d("compare","successs");
//
//                                                    return f.parse(lhs.getDate()+" "+lhs.getTime()).compareTo(f.parse(rhs.getDate()+" "+rhs.getTime()));
//
//                                                } catch (ParseException e) {
//                                                    Log.d("compare",e.toString());
//                                                    throw new IllegalArgumentException(e);
//                                                }
//                                            }
//                                        });
//                                        Collections.reverse(list2);
//                                        Collections.reverse(itemchat);
//                                        pos1 =0;
//                                        myitem_place.setAdapter(addFormAdapterAdapter);
//                                        myitem_place.setVisibility(View.VISIBLE);
//                                        mfooterload.setVisibility(View.GONE);
//                                    }else {
//                                        list2.get(pos1).setDatea(itemchat.get(pos1).getDate());
//                                        list2.get(pos1).setTime(itemchat.get(pos1).getTime());
//                                        pos1 +=1;
//                                        showdetail();
//
//                                    }
//
//                                }
////                recyclerView.scrollToPosition(adapterchat.getItemCount());
//                            }else{
//                                Log.d("sss","gagal");
//                                if (list2.size()==pos1+1){
//                                    pos1 =0;
//                                    myitem_place.setAdapter(addFormAdapterAdapter);
//                                    myitem_place.setVisibility(View.VISIBLE);
//                                    mfooterload.setVisibility(View.GONE);
//                                }else {
//                                    pos1 +=1;
//                                    showdetail();
//
//                                }
//                                mfooterload.setVisibility(View.GONE);
//                            }
////                String message = dataSnapshot.child("message").getValue().toString();
////                myItem.get(i).setDetails(message);
////                myviewholder.mdetailchat.setText(myItem.get(i).getDetails());
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            // Handle possible errors.
//                            mfooterload.setVisibility(View.GONE);
//                        }
//                    });

                }else {
                    sesionid();
                    mfooterload.setVisibility(View.GONE);
                    if (MsessionExpired.equals("true")) {
                        Toast.makeText(LiveChatList.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(LiveChatList.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                mfooterload.setVisibility(View.GONE);

            }
        });
        Log.d("livechatlistreq",jsonObject.toString());
    }
    public void getpostmant(){
        itemchat.clear();
        list2.clear();
        page=1;
        if (load){
            load = false;
        }else {
            mfooterload.setVisibility(View.VISIBLE);
            load = true;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
//        jsonObject.addProperty("page",page);
//        jsonObject.addProperty("status",valuefilter);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, getchatnya);
        Call<JsonObject> panggilkomplek = jsonPostService.getchat(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {

                }

                if (statusnya.equals("OK")){
                   Log.d("responget",homedata.toString());

                }else {
                    sesionid();
                    mfooterload.setVisibility(View.GONE);
                    if (MsessionExpired.equals("true")) {
                        Toast.makeText(LiveChatList.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {


            }
        });
        Log.d("livechatlistreq",jsonObject.toString());
    }
    public void showdetail(){
        lastQuery = databaseReference.child("chat").child(list2.get(pos1).getLiveChatID()).child("listchat").orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.exists();
                if (dataSnapshot.exists()){
                    for(DataSnapshot ds: dataSnapshot.getChildren())
                    {
                        DetailsDate fetchDatalist=ds.getValue(DetailsDate.class);
//                        fetchDatalist.setKey(ds.getKey());
                        itemchat.add(fetchDatalist);

                        if (list2.size()==pos1+1){
                            list2.get(pos1).setDatea(itemchat.get(pos1).getDate());
                            list2.get(pos1).setTime(itemchat.get(pos1).getTime());
                            pos1 =0;
                            Collections.sort(list2, new Comparator<ListLiveChatItem>() {
                                DateFormat f = new SimpleDateFormat("d MMM yyyy HH:mm");
                                @Override
                                public int compare(ListLiveChatItem lhs, ListLiveChatItem rhs) {
                                    try {
                                        Log.d("compare","successs");

                                        return f.parse(lhs.getDatea()+" "+lhs.getTime()).compareTo(f.parse(rhs.getDatea()+" "+rhs.getTime()));

                                    } catch (ParseException e) {
                                        Log.d("compare",e.toString());
                                        throw new IllegalArgumentException(e);
                                    }
                                }
                            });
                            Collections.sort(itemchat, new Comparator<DetailsDate>() {
                                DateFormat f = new SimpleDateFormat("d MMM yyyy HH:mm");
                                @Override
                                public int compare(DetailsDate lhs, DetailsDate rhs) {
                                    try {
                                        Log.d("compare","successs");

                                        return f.parse(lhs.getDate()+" "+lhs.getTime()).compareTo(f.parse(rhs.getDate()+" "+rhs.getTime()));

                                    } catch (ParseException e) {
                                        Log.d("compare",e.toString());
                                        throw new IllegalArgumentException(e);
                                    }
                                }
                            });
                            Collections.reverse(list2);
                            Collections.reverse(itemchat);
                            myitem_place.setAdapter(addFormAdapterAdapter);
                            myitem_place.setVisibility(View.VISIBLE);
                            mfooterload.setVisibility(View.GONE);
                            Gson gson = new GsonBuilder().create();
                            myCustomArray = new JsonArray();
                            myCustomArray = gson.toJsonTree(itemchat).getAsJsonArray();
                            jsonarayitem = myCustomArray.toString();
                            Log.d("sizecart_22", String.valueOf(jsonarayitem));
                            Log.d("artaw", myCustomArray.toString());
                        }else {
                            if (pos1==list2.size()){


                            }else {
                                list2.get(pos1).setDatea(itemchat.get(pos1).getDate());
                                list2.get(pos1).setTime(itemchat.get(pos1).getTime());
                                pos1 +=1;
                                showdetail();
                            }



                        }

                    }
//                recyclerView.scrollToPosition(adapterchat.getItemCount());
                }
//                String message = dataSnapshot.child("message").getValue().toString();
//                myItem.get(i).setDetails(message);
//                myviewholder.mdetailchat.setText(myItem.get(i).getDetails());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }
    //    public void loadSpin(){
//        mfooterload.setVisibility(View.VISIBLE);
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("sessionId",sesionid_new);
//        jsonObject.addProperty("page",page);
//        jsonObject.addProperty("status","-");
//        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
//        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
//        Call<JsonObject> panggilkomplek = jsonPostService.ListST(jsonObject);
//        panggilkomplek.enqueue(new Callback<JsonObject>() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//
//                String errornya = "";
//                JsonObject homedata=response.body();
//                String statusnya = homedata.get("status").getAsString();
//                if (homedata.get("errorMessage").toString().equals("null")) {
//
//                }else {
//                    errornya = homedata.get("errorMessage").getAsString();
//                }
//                MhaveToUpdate = homedata.get("haveToUpdate").toString();
//                MsessionExpired = homedata.get("sessionExpired").toString();
//                if (statusnya.equals("OK")){
//                    loadData();
//                    sesionid();
//                    JsonObject data = homedata.getAsJsonObject("data");
//                    listformreq = data.getAsJsonArray("frList");
//                    liststatus = data.getAsJsonArray("statusList");
//                    for (int i = 0; i < liststatus.size(); ++i) {
//                        JsonObject jsonObject3 = (JsonObject)liststatus.get(i);
//                        String string3 = jsonObject3.getAsJsonObject().get("Value").getAsString();
//                        String string4 = jsonObject3.getAsJsonObject().get("Text").getAsString();
//                        listvalue.add(string3);
//                        listnamestatus.add(string4);
//                        for (int j = 0; j < listvalue.size(); ++j) {
//                            if (listvalue.get(i).equals(valuefilter)){
//                                pos=j;
//                            }
//                        }
//                        final ArrayAdapter<String> kategori = new ArrayAdapter<String>(ApprovalActivity.this, R.layout.spinstatus_layout,
//                                listnamestatus);
//                        kategori.setDropDownViewResource(R.layout.spinkategori);
//                        kategori.notifyDataSetChanged();
//                        mstatus_spin.setAdapter(kategori);
//                        mstatus_spin.setSelection(pos);
//                    }
//                } else {
//                    sesionid();
////                    if (MsessionExpired.equals("true")) {
//////                        Toast.makeText(ServiceTicket.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//////                    }
//                    Toast.makeText(ApprovalActivity.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(ApprovalActivity.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
//                cekInternet();
//                mfooterload.setVisibility(View.GONE);
//
//            }
//        });
//    }
//    public void pagination(){
//        mfooterload.setVisibility(View.VISIBLE);
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("sessionId",sesionid_new);
//        jsonObject.addProperty("page",page);
//        jsonObject.addProperty("status",valuefilter);
//        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
//        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
//        Call<JsonObject> panggilkomplek = jsonPostService.ListST(jsonObject);
//        panggilkomplek.enqueue(new Callback<JsonObject>() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                String errornya = "";
//                JsonObject homedata=response.body();
//                String statusnya = homedata.get("status").getAsString();
//                if (homedata.get("errorMessage").toString().equals("null")) {
//
//                }else {
//                    errornya = homedata.get("errorMessage").getAsString();
//                }
//                MhaveToUpdate = homedata.get("haveToUpdate").toString();
//                MsessionExpired = homedata.get("sessionExpired").toString();
//                sesionid();
//                if (statusnya.equals("OK")){
//                    JsonObject data = homedata.getAsJsonObject("data");
//                    totalpage = data.get("totalPage").getAsInt();
//                    listformreq = data.getAsJsonArray("frList");
//                    totalrec = data.get("totalRec").toString();
//                    mrecord.setText("Record: "+totalrec);
//                    Gson gson = new Gson();
//                    Type listType = new TypeToken<ArrayList<ServiceTicketItems>>() {
//                    }.getType();
//                    ArrayList<ServiceTicketItems> list;
//                    list=new ArrayList<>();
//                    list = gson.fromJson(listformreq.toString(), listType);
//                    list2.addAll(list);
//                    addFormAdapterAdapter = new ServiceTicketAdapter(ApprovalActivity.this, list2);
//                    myitem_place.setAdapter(addFormAdapterAdapter);
//                    myitem_place.setVisibility(View.VISIBLE);
//                    mfooterload.setVisibility(View.GONE);
//                    if (totalpage == 1) {
//                        mfooterload.setVisibility(View.GONE);
//                    }
//                    if (totalpage == 0) {
//                        mfooterload.setVisibility(View.GONE);
//                    } else if (list2 != null) {
//                        list2.size();
//                    }
//                    mfooterload.setVisibility(View.GONE);
////                    page++;
//                    refreshscroll=true;
//                }else {
//
//                    mfooterload.setVisibility(View.GONE);
////                    if (MsessionExpired.equals("true")) {
////                        Toast.makeText(ServiceTicket.this, errornya.toString(), Toast.LENGTH_SHORT).show();
////                    }
//                    Toast.makeText(ApprovalActivity.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(ApprovalActivity.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
//                cekInternet();
//                mfooterload.setVisibility(View.GONE);
//
//            }
//        });
//    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) LiveChatList.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(LiveChatList.this, NoInternet.class);
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
            startActivity(new Intent(LiveChatList.this, Login.class));
            finish();
//            Toast.makeText(ServiceTicket.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        itemchat.clear();
        itemchat3.clear();
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

                    if (Scs.equals("no")){
                        loadData();
                    }else {
                        loadScc();
                    }
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
                        startActivity(new Intent(LiveChatList.this, ClockInActivity.class));
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
                    Toast.makeText(LiveChatList.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
//                mrefresh.setVisibility(View.VISIBLE);
//                mcheck.setVisibility(View.GONE);
                Toast.makeText(LiveChatList.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading.dismiss();
            }
        });

    }
}