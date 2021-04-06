package com.sccengineer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.sccengineer.listsparepart.Sparepart_adapter;
import com.sccengineer.listsparepart.Sparepart_item;
import com.sccengineer.messagecloud.check;
import com.sccengineer.notification.NotificationAdapter;
import com.sccengineer.notification.NotificationItem;
import com.sccengineer.serviceticket.ServiceTicketAdapter;
import com.sccengineer.serviceticket.ServicesTicketItem;
import com.sccengineer.spartsend.SendSparepart_adapter;
import com.sccengineer.spartsend.SendSparepart_item;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sccengineer.ServiceTicket.list2;
import static com.sccengineer.ServiceTicket.refresh;
import static com.sccengineer.ServiceTicket.valuefilter;
import static com.sccengineer.apihelper.ServiceGenerator.baseurl;
import static com.sccengineer.apihelper.ServiceGenerator.ver;

public class DetailsST extends AppCompatActivity {
    boolean inforeopen = true;
    EditText mreasonnya;
    public static String noreq = "";
    String servicetypenya = "";
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    boolean internet = true;
    public static LinearLayoutManager linearLayoutManager,linearLayoutManager2;
    ArrayList<ServicesTicketItem> listticket;
    ServiceTicketAdapter ticketadapter;
    ProgressDialog loading;
    String mallowToCancel = "";
    String mallowtoconfirm = "";
    ImageView mbanner;
    LinearLayout mcancel, mconfirm, mcs, mbackgroundalert,mback, mreopenbtn;
    TextView mcreatedate, mdate, mdeskription, missu, moperator, mreqno, mservicetype, msn, mstatusdetail,
            mstid, mtitle, munitcategory, mlocation, mtextalert, mrequestby, mreopeninfo;
    String mdateapi = "";
    String mdeskriptionapi = "";
    String mformRequestCd = "";
    String mreopen = "";
    ImageView mimgpopup;
    LinearLayout mlayoutticket,mlayoutunit1, mlayoutunit2, mlayoutunit3, mreinfolay,mspar;
    private LinearLayoutManager mlinear;
    String mphotoURL = "";
    String mpressGuid = "";
    String mpressName = "";
    String mrequestedBy = "";
    String mrequestedDateTime = "";
    String mserviceTicketCd = "";
    String xlocation = "";
    JsonArray mserviceTicketHistory;
    JsonArray massistengineer, massistengineer2;
    RecyclerView mservice_layout;
    String mstatus = "";
    String mstatusColorCode = "";
    String mstatusName = "";
    String noticket = "";
    String sesionid_new = "";
    String guid = "";
    String mreason="";
    public static String username = "";
    boolean installed= true;
    //timer
    public static String assist="";
    public static int seconds = 0;
    public static String usetime="";
    String usetimea="";
    private boolean running;
    //timer
    private static int START_TIME_IN_MILLIS = 0;
    private TextView mtimerconfirm;
    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    public static String Nowaform = "0";
    //updatepanel;
    ImageView mprosbarr;
    TextView msupport,mbar1,mbar2,mbar3,mbar4,mactionprogress,mestimasi,mstarttime,mendtime,massigndate,mengineer,masengineer, mstatustick, mtimer;
    EditText mlastimpresiST, mdescripst;
    Spinner mservicetypeST, mstatusST;
    LinearLayout mlayestima,mstartprogress, mupdatebtn, mlayoutimpress, mlayoutnote, mlayoutstatus, mlayoutservicest, mlayoutupdatepanel;
    JsonArray listsn;
    List<String> snid = new ArrayList();
    List<String> snname = new ArrayList();
    String mpressId = "";
    JsonArray listsna;
    List<String> snida = new ArrayList();
    List<String> snnamea = new ArrayList();
    String mpressIda = "";
    String home="homesa";
    long MillisecondTime, StartTime, UpdateTime = 0L ;
    long TimeBuff = seconds ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds, Jam ;
    FusedLocationProviderClient fusedLocationProviderClient;
    boolean reopen = true;
    boolean lempar = true;

    ProgressBar mloadpart;
    public static RecyclerView mpartlist, msendpartlist;
    EditText msearch;
    public static TextView msparenaem;
    String sear = "";
    Sparepart_adapter sparepart_adapter;
    ArrayList<Sparepart_item> sparepart_items;
    public static SendSparepart_adapter sendsparepart_adapter;
    public static ArrayList<SendSparepart_item> sendsparepart_items;
    public static JsonArray listnotif, sendspart;
    boolean load = true;
    public static JsonArray myCustomArray;
    public static String jsonarayitem = "";
    public static Dialog dialog;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_s_t);
        //panelupdate
        msendpartlist = findViewById(R.id.listsper);
        msparenaem = findViewById(R.id.sparename);
        mspar = findViewById(R.id.searchsper);
        mstatustick = findViewById(R.id.statustick);
        mtimer = findViewById(R.id.timer);
        msupport = findViewById(R.id.support);
        mprosbarr = findViewById(R.id.posbar);
        mlayoutimpress = findViewById(R.id.layoutimpress);
        mlayestima = findViewById(R.id.layEstima);
        mlayoutupdatepanel = findViewById(R.id.layoutupdatepanel);
        mlayoutnote = findViewById(R.id.layoutnote);
        mlayoutstatus =findViewById(R.id.layoutstatus);
        mlayoutservicest = findViewById(R.id.layoutservicest);
        mbar1 = findViewById(R.id.bar1a);
        mbar2 = findViewById(R.id.bar2a);
        mbar3 = findViewById(R.id.bar3a);
        mbar4 = findViewById(R.id.bar4a);
        mactionprogress = findViewById(R.id.actionprogress);
        mestimasi = findViewById(R.id.estimasi);
        mstarttime = findViewById(R.id.starttime);
        mendtime = findViewById(R.id.endtime);
        massigndate = findViewById(R.id.assigndate);
        mengineer = findViewById(R.id.engineer);
        masengineer = findViewById(R.id.asengineer);
        mlastimpresiST = findViewById(R.id.lastimpress);
        mdescripst = findViewById(R.id.descripst);
        mservicetypeST = findViewById(R.id.servicetypeST);
        mstatusST = findViewById(R.id.statusST);
        mstartprogress = findViewById(R.id.startprogress);
        mupdatebtn = findViewById(R.id.updatebtn);
        //
        missu = findViewById(R.id.issucategroy);
        mservicetype = findViewById(R.id.servicetype);
        mcreatedate =findViewById(R.id.createdate);
        mdate=findViewById(R.id.datereq);
        mdeskription=findViewById(R.id.descrip);
        moperator=findViewById(R.id.operator);
        mreqno=findViewById(R.id.request_no);
        msn=findViewById(R.id.sn);
        mstatusdetail=findViewById(R.id.statusdetail);
        mstid=findViewById(R.id.stid);
        mtitle=findViewById(R.id.title);
        munitcategory=findViewById(R.id.unitcategory);
        mcancel=findViewById(R.id.laycancel);
//        mconfirm=findViewById(R.id.confirm);
//        mcs=findViewById(R.id.chatcspo);
        mback=findViewById(R.id.backbtn);
        mbanner=findViewById(R.id.imgbanner);
        mlayoutunit1=findViewById(R.id.layoutunit1);
        mlayoutunit2=findViewById(R.id.layoutunit2);
        mlayoutunit3=findViewById(R.id.layoutunit3);
        mlayoutticket=findViewById(R.id.layoutticket);
        mservice_layout=findViewById(R.id.serviceticket);
        mlocation = findViewById(R.id.locationsn);
        mtextalert = findViewById(R.id.textalert);
        mbackgroundalert = findViewById(R.id.backgroundalert);
        dialog = new Dialog(   DetailsST.this);
//        mtimerconfirm = findViewById(R.id.timer);
//        mreopenbtn = findViewById(R.id.reopen);
        mrequestby = findViewById(R.id.requestby);
//        mreinfolay = findViewById(R.id.reinfolay);
//        mreopeninfo = findViewById(R.id.reopeninfo);
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(DetailsST.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        mservice_layout.setLayoutManager(linearLayoutManager);
        mservice_layout.setHasFixedSize(true);
        listticket = new ArrayList();
        linearLayoutManager2 = new LinearLayoutManager(DetailsST.this, LinearLayout.VERTICAL,false);
        msendpartlist.setLayoutManager(linearLayoutManager2);
        msendpartlist.setHasFixedSize(true);
        sendsparepart_items = new ArrayList();
        if (ActivityCompat.checkSelfPermission(DetailsST.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(DetailsST.this
                ,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            getCurrentLocation();
            lempar = false;
        }else {
            ActivityCompat.requestPermissions(DetailsST.this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                    },100);
        }
        //getsessionId
        seconds=0;
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            noreq = bundle2.getString("noticket");
            home = bundle2.getString("home");
            guid = bundle2.getString("guid");
            username = bundle2.getString("user");
            noticket = bundle2.getString("noticket");
            valuefilter = bundle2.getString("pos");

        }

        getSessionId();
        cekInternet();
        if (internet){
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(DetailsST.this);
            loadData();
            if (guid==null){

            }else {
                ReadNotif();
            }
        }else {

        }
        String TAG = "FirebaseMessaging";
        Log.d(TAG,"noreq:"+noreq);



        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mstartprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(DetailsST.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(DetailsST.this
                        ,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    getCurrentLocation();
                    showDialogreopen();
                }else {
                    ActivityCompat.requestPermissions(DetailsST.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                            },100);
                }

            }
        });
        mupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(DetailsST.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(DetailsST.this
                        ,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    getCurrentLocation();
                    showDialogrupdate();
                }else {
                    ActivityCompat.requestPermissions(DetailsST.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                            },100);
                }
            }
        });
//        mcs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                appInstalledOrNot("com.whatsapp");
//                if (installed) {
//                    String message = "Hi Support, "+getString(R.string.title_tanyacs)+noreq;
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(android.net.Uri.parse(
//                            String.format("https://api.whatsapp.com/send?phone=%s&text=%s", Nowaform, message)));
//                    startActivity(intent);
//                }else {
//                    Toast.makeText(DetailsFormActivity.this,"Whatsapp blum di instal", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        mcancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cekInternet();
//                if (internet){
//                    showDialog();
//                }else {
//                }
//            }
//        });
//        mconfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cekInternet();
//                if (internet){
//                    Intent gotorating = new Intent(DetailsST.this, RatingStar.class);
//                    gotorating.putExtra("id", noreq);
//                    gotorating.putExtra("noticket", noticket);
//                    gotorating.putExtra("user", username);
//                    startActivity(gotorating);
//                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                    finish();
//                }else {
//
//                }
//            }
//        });
        mbanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(DetailsST.this, R.style.TransparentDialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.popupfoto);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                mimgpopup = dialog.findViewById(R.id.imagepopup);
                if (mphotoURL.equals("")){
                    Picasso.with(DetailsST.this).load(R.drawable.nonefoto).into(mimgpopup);

                }else {
                    Picasso.with(DetailsST.this).load(mphotoURL).into(mimgpopup);
                }

                dialog.show();
            }
        });
        //REOPEN
//        mreopenbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent gotorating = new Intent(DetailsFormActivity.this, ReopenCase.class);
//                gotorating.putExtra("id", noreq);
//                gotorating.putExtra("noticket", noticket);
//                gotorating.putExtra("user", username);
//                startActivity(gotorating);
//                overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                finish();
////                showDialogreopen();
//            }
//        });
//        updateCountDownText();
        mservicetypeST.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                operatorname.clear();
//                operatorcd.clear();
                mpressId = snid.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mstatusST.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                operatorname.clear();
//                operatorcd.clear();
                mpressIda = snida.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        startTimer();
        mspar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogspar();
            }
        });
    }
//    private void showDialog() {
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                this);
//
//        // set title dialog
//        alertDialogBuilder.setTitle(getString(R.string.title_deleteReq));
//        final EditText input = new EditText(this);
//// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        View v = getLayoutInflater().inflate(R.layout.item_cancel, null);
//        mreasonnya=v.findViewById(R.id.reasondes);
//        mreasonnya.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mreason = mreasonnya.getText().toString();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        // set pesan dari dialog
//        AlertDialog d = new AlertDialog.Builder(DetailsFormActivity.this)
//                .setView(v)
//                .setMessage(R.string.title_canelreq)
//                .setPositiveButton(getString(R.string.title_yes), null) //Set to null. We override the onclick
//                .setNegativeButton(getString(R.string.title_no), null)
//                .create();
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////            alertDialogBuilder
////                    .setMessage(getString(R.string.title_canelreq))
////                    .setView(v)
////                    .setIcon(R.mipmap.ic_launcher)
////                    .setCancelable(false)
////                    .setPositiveButton(getString(R.string.title_yes),null)
////                    .setNegativeButton(getString(R.string.title_no),new DialogInterface.OnClickListener() {
////                        public void onClick(DialogInterface dialog, int id) {
////                            // jika tombol ini diklik, akan menutup dialog
////                            // dan tidak terjadi apa2
////                            dialog.cancel();
////                        }
////                    });
////        }
//
//        // membuat alert dialog dari builder
////        AlertDialog alertDialog = alertDialogBuilder.create();
////
////        // menampilkan alert dialog
//        d.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
//                Button C = d.getButton(AlertDialog.BUTTON_NEGATIVE);
//                b.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        if (mreasonnya.length()==0){
//                            Toast.makeText(DetailsST.this, getString(R.string.title_reasonrequired),Toast.LENGTH_LONG).show();
//                        }else {
//                            cancelreq();
//                            d.dismiss();
//                        }
//                    }
//                });
//                C.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        d.dismiss();
//                    }
//                });
//            }
//        });
//        d.show();
//
//    }
    private void showDialogreopen() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle(getString(R.string.title_reopendialod));

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage(getString(R.string.title_dialogreopen))
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.title_yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        reopenreq();
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
    private void showDialogrupdate() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle(getString(R.string.title_update));

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage(getString(R.string.title_andaupdate))
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.title_yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        updatea();
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
        ConnectivityManager connectivityManager = (ConnectivityManager) DetailsST.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(DetailsST.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void loadData(){
        loading = ProgressDialog.show(DetailsST.this, "", "loading...", true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("serviceTicketCd",noreq);
        jsonObject.addProperty("ver",ver);
//        Toast.makeText(DetailsFormActivity.this,jsonObject.toString(), Toast.LENGTH_SHORT).show();
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.getservice(jsonObject);
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


//                    inforeopen = data.get("allowToReopenCase").getAsBoolean();
//                    if (inforeopen){
//                        mreinfolay.setVisibility(View.VISIBLE);
//                        mreopeninfo.setText(data.get("reopenCaseInformation").getAsString());
//                    }else {
//                        mreinfolay.setVisibility(View.GONE);
//                    }
                    String showalert = data.get("showMessage").toString();
//                    if (data.get("updatePanelUseTimer").getAsBoolean()){
//                        int hour = data.get("updatePanelTimerStartHours").getAsInt();
//                        int minutes = data.get("updatePanelTimerStartMinutes").getAsInt();
//                        int secondss = data.get("updatePanelTimerStartSeconds").getAsInt();
//                        int totalsecond = (hour*60*60*1000)+(minutes*60*1000)+(secondss*1000);
//                        START_TIME_IN_MILLIS = totalsecond;
//                        mTimeLeftInMillis=START_TIME_IN_MILLIS;
//                        //timer
////                        Toast.makeText(DetailsFormActivity.this, String.valueOf(mTimeLeftInMillis),Toast.LENGTH_LONG).show();
//                        startTimer();
//                        updateCountDownText();
//                    }else{
//
//                    }
                    //check alert
                    if (showalert.equals("true")){

                        String text = data.get("messageText").getAsString();
                        String textcolor = data.get("messageTextColor").getAsString();
                        String bgcolor = data.get("messageBackgroundColor").getAsString();

                        GradientDrawable shape =  new GradientDrawable();
                        shape.setCornerRadius( 15 );
                        shape.setColor(Color.parseColor("#"+bgcolor));

                        mbackgroundalert.setVisibility(View.VISIBLE);
                        mtextalert.setTextColor(Color.parseColor("#"+textcolor));
                        mbackgroundalert.setBackground(shape);
                        if (Build.VERSION.SDK_INT >= 24) {
                            mtextalert.setText((CharSequence) Html.fromHtml((String)text, Html.FROM_HTML_MODE_COMPACT));
                            mtextalert.setMovementMethod(LinkMovementMethod.getInstance());
                        } else {
                            mtextalert.setText((CharSequence)Html.fromHtml((String)text));
                            mtextalert.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    }else {
                        mbackgroundalert.setVisibility(View.GONE);
                    }
                    //timer
//                    usetime = data.get("useTimer").toString();
//                    int hours = data.get("timerStartHours").getAsInt();
//                    int minute = data.get("timerStartMinutes").getAsInt();
//                    int second = data.get("timerStartSeconds").getAsInt();
//                    seconds = (hours*60*60*1000)+(minute*60*1000)+(second*1000);
//                    Toast.makeText(DetailsFormActivity.this, String.valueOf(seconds),Toast.LENGTH_LONG).show();
                    if (data.get("formRequestCd").toString().equals("null")){
                        mformRequestCd = "-";
                    }else {
                        mformRequestCd = data.get("formRequestCd").getAsString();
                    }

//                    mreopen = data.get("allowToReopenCase").toString();
//                    if (mreopen.equals("true")){
//                        mreopenbtn.setVisibility(View.VISIBLE);
//                    }else {
//                        mreopenbtn.setVisibility(View.GONE);
//                    }
                    mserviceTicketCd = data.get("serviceTicketCd").toString();
                    mdateapi = data.get("requestedDateTime").getAsString();
                    mpressGuid = data.get("pressGuid").getAsString();
                    mpressName = data.get("pressName").getAsString();
                    if (data.get("photoURL").toString().equals("null")){

                    }else {
                        mphotoURL = data.get("photoURL").getAsString();
                    }

                    mstatus = data.get("status").getAsString();
                    mstatusName = data.get("statusName").getAsString();
                    mstatusColorCode = data.get("statusColorCode").getAsString();
                    mdeskriptionapi = data.get("description").getAsString();
                    mrequestby.setText(data.get("createdBy").getAsString());
                    moperator.setText(data.get("operatorName").getAsString());
                    mrequestedDateTime = data.get("requestedDateTime").getAsString();
                    mstid.setText(": "+data.get("serviceTicketCd").getAsString());
//                    mallowToCancel = data.get("allowToCancel").toString();
//                    mallowtoconfirm = data.get("allowToConfirm").toString();
                    xlocation = data.get("locationName").getAsString();
                    mlocation.setText(xlocation);
                    if (mserviceTicketCd.equals("null")){
                        mlayoutticket.setVisibility(View.GONE);
                    }else {
                        mlayoutticket.setVisibility(View.VISIBLE);
                        mserviceTicketHistory = data.getAsJsonArray("serviceTicketHistory");

                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<ServicesTicketItem>>(){}.getType();
                        listticket = gson.fromJson(mserviceTicketHistory.toString(), type);
                        ticketadapter = new ServiceTicketAdapter(DetailsST.this,listticket);
                        mservice_layout.setAdapter(ticketadapter);
                        mservice_layout.setVisibility(View.VISIBLE);
                        for (int i = 0; i < mserviceTicketHistory.size(); ++i) {
                            String string6 = (mserviceTicketHistory.get(0)).getAsJsonObject().get("ServiceTicketCd").getAsString();
                            mstid.setText(string6);
                            String asist = "";
                            JsonObject ass = mserviceTicketHistory.get(i).getAsJsonObject();
                            massistengineer = ass.getAsJsonArray("Assists");
                            for (int x = 0; x < massistengineer.size(); ++x){
                                JsonObject assobj = massistengineer.get(x).getAsJsonObject();
                                asist += assobj.get("Name").getAsString();
                                asist += "\n";
                                listticket.get(i).setAssist(asist);

                            }
                        }

                        String string7 = data.get("serviceTicketCreated").getAsString();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        String string5 = null;
                        String string6="";
                        try {
                            string6 = simpleDateFormat2.format(simpleDateFormat.parse(string7));
                            string5 = simpleDateFormat.format(simpleDateFormat.parse(string7));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String[] separated = string5.split("T");
                        separated[0].trim();; // this will contain "Fruit"
                        separated[1].trim();;
                        mcreatedate.setText(separated[0]+" "+ separated[1]);
                    }
                    if (data.get("unitCategoryName") == null) {
                        mlayoutunit2.setVisibility(View.GONE);
                    } else {
                        munitcategory.setText(data.get("unitCategoryName").getAsString());
                        mlayoutunit2.setVisibility(View.VISIBLE);
                    }
                    if (data.get("issueCategoryName") == null) {
                        mlayoutunit3.setVisibility(View.GONE);
                    } else {
                        missu.setText(data.get("issueCategoryName").getAsString());
                        mlayoutunit3.setVisibility(View.VISIBLE);
                    }
                    if (data.get("serviceTypeName").toString().equals("null")) {
                        mlayoutunit1.setVisibility(View.GONE);
                    } else {
                        mservicetype.setText(data.get("serviceTypeName").getAsString());
                        mlayoutunit1.setVisibility(View.VISIBLE);
                    }
//                    if (mallowtoconfirm.equals("true")) {
//                        mconfirm.setVisibility(View.VISIBLE);
//                    } else {
//                        //RATINGVISIBLE
//                        mconfirm.setVisibility(View.GONE);
//                    }
//                    if (mallowToCancel.equals("true")) {
//                        mcancel.setVisibility(View.VISIBLE);
//                    } else {
//                        mcancel.setVisibility(View.GONE);
//                    }
                    loading.dismiss();
                    ////set
                    if (mphotoURL.equals("")){
                        Picasso.with(DetailsST.this).load(R.drawable.noimgg).into(mbanner);
                    }else {
                        Picasso.with(DetailsST.this).load(mphotoURL).into(mbanner);
                    }

                    mtitle.setText("#"+noticket);
                    mreqno.setText(mformRequestCd);
                    String datenew = "";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                    SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    try {
                        datenew = simpleDateFormat.format(simpleDateFormat.parse(mdateapi));
                        System.out.println(datenew);
                        Log.e((String)"Date",datenew);
                    }
                    catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    String[] separated = datenew.split("T");
                    separated[0].trim();; // this will contain "Fruit"
                    separated[1].trim();;
                    mdate.setText(separated[0]+" "+ separated[1]);
                    msn.setText(mpressName);

                    mdeskription.setText(mdeskriptionapi);
                    mstatusdetail.setText(mstatusName);
                    mstatusdetail.setTextColor(Color.parseColor("#"+mstatusColorCode));

                    // updatepanel ST
                    if (data.get("showUpdatePanel").getAsBoolean()){
                        mlayoutupdatepanel.setVisibility(View.VISIBLE);
                    }else {
                        mlayoutupdatepanel.setVisibility(View.GONE);
                    }
                    if (data.get("updatePanelLatestAction").toString().equals("null")){

                    }else {
                        JsonObject updatepanel = data.getAsJsonObject("updatePanelLatestAction");
                        //sparepartupdatepanel
                        myCustomArray = updatepanel.getAsJsonArray("SpareParts");
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<SendSparepart_item>>() {
                        }.getType();
                        sendsparepart_items = gson.fromJson(myCustomArray.toString(), listType);
                        sendsparepart_adapter = new SendSparepart_adapter(DetailsST.this, sendsparepart_items);
                        msendpartlist.setAdapter(sendsparepart_adapter);
                        //timer Update

//                        usetimea = updatepanel.getAsString()
                        if (data.get("updatePanelUseTimer").getAsBoolean()){
                            msupport.setVisibility(View.GONE);
                            mtimer.setVisibility(View.VISIBLE);
                            int hours = data.get("updatePanelTimerStartHours").getAsInt();
                            int minute = data.get("updatePanelTimerStartMinutes").getAsInt();
                            int second = data.get("updatePanelTimerStartSeconds").getAsInt();
                            seconds = (hours*60*60*1000)+(minute*60*1000)+(second*1000);
                            TimeBuff = seconds;
                            UpdateTime = TimeBuff + MillisecondTime;
                            startTimer();

                        }else {
                            msupport.setVisibility(View.VISIBLE);
                            mtimer.setVisibility(View.GONE);
                        }
                        //Nomor Assigment
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("#");
                        stringBuilder.append(String.valueOf(updatepanel.get("Position").getAsInt()));
                        mstatustick.setText((CharSequence)stringBuilder.toString());
                        //date
                        SimpleDateFormat simpleDateFormatx = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());

                        String startdate = "";
                        String enddate = "";
                        String assigndate = "";

                        //estimated
                        if (updatepanel.get("WaitingEstimationDate").toString().equals("null")){
                            mlayestima.setVisibility(View.GONE);
                        }else {
                            mlayestima.setVisibility(View.VISIBLE);
                            SimpleDateFormat datefor = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            String estima = updatepanel.get("WaitingEstimationDate").getAsString();
                            String estimadate = "";
                            try {
                                estimadate = datefor.format(simpleDateFormat.parse(estima));
                                System.out.println(estimadate);
                                Log.e((String)"Date", (String)estimadate);
                            }
                            catch (ParseException parseException) {
                                parseException.printStackTrace();
                            }
                            mestimasi.setText(estimadate);

                        }
                        //startend
                        if (updatepanel.get("SupportStartDateTime").toString().equals("null")){

                        }else {
                            if (updatepanel.get("SupportEndDateTime").toString().equals("null")){

                            }else {
                                String oladend = updatepanel.get("SupportEndDateTime").getAsString();
                                try {
                                    enddate = simpleDateFormat2.format(simpleDateFormat.parse(oladend));
                                    System.out.println(enddate);
                                    Log.e((String)"Date", (String)startdate);
                                }
                                catch (ParseException parseException) {
                                    parseException.printStackTrace();
                                }
                                mendtime.setText(enddate);
                            }
                            String oladstart = updatepanel.get("SupportStartDateTime").getAsString();

                            try {
                                startdate = simpleDateFormat2.format(simpleDateFormat.parse(oladstart));
                                System.out.println(startdate);
                                Log.e((String)"Date", (String)startdate);
                            }
                            catch (ParseException parseException) {
                                parseException.printStackTrace();
                            }
                            mstarttime.setText(startdate);
                        }
                        //assigen
                        if (updatepanel.get("AssignedDateTime").toString().equals("null")){

                        }else {
                            String oldass = updatepanel.get("AssignedDateTime").getAsString();
                            try {
                                assigndate = simpleDateFormat2.format(simpleDateFormat.parse(oldass));
                                System.out.println(enddate);
                                Log.e((String)"Date", (String)startdate);
                            }
                            catch (ParseException parseException) {
                                parseException.printStackTrace();
                            }
                            massigndate.setText(assigndate);
                        }
                        //posbar
                        String string8 = updatepanel.get("Bar1Text").getAsString();
                        String string9 =updatepanel.get("Bar2Text").getAsString() ;
                        String string10 =updatepanel.get("Bar3Text").getAsString() ;
                        String string11 = updatepanel.get("Bar4Text").getAsString();
                        if (Build.VERSION.SDK_INT >= 24) {
                            mbar1.setText((CharSequence) Html.fromHtml((String)string8, Html.FROM_HTML_MODE_COMPACT));
                            mbar2.setText((CharSequence) Html.fromHtml((String)string9, Html.FROM_HTML_MODE_COMPACT));
                            mbar3.setText((CharSequence) Html.fromHtml((String)string10, Html.FROM_HTML_MODE_COMPACT));
                            mbar4.setText((CharSequence) Html.fromHtml((String)string11, Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            mbar1.setText((CharSequence) Html.fromHtml((String)string8));
                            mbar2.setText((CharSequence) Html.fromHtml((String)string9));
                            mbar3.setText((CharSequence) Html.fromHtml((String)string10));
                            mbar4.setText((CharSequence) Html.fromHtml((String)string11));
                        }
                        if (updatepanel.get("BarPosition").getAsInt()==1){
                            mprosbarr.setImageResource(R.drawable.asign);
                            mbar1.setTextColor(getResources().getColor(R.color.black));
                        } else if (updatepanel.get("BarPosition").getAsInt() == 2) {
                            mprosbarr.setImageResource(R.drawable.onprogress);
                            mbar2.setTextColor(getResources().getColor(R.color.black));

                        }else if (updatepanel.get("BarPosition").getAsInt() == 3){
                            mprosbarr.setImageResource(R.drawable.waiting);

                            mbar2.setTextColor(getResources().getColor(R.color.black));
                            mbar3.setTextColor(getResources().getColor(R.color.black));

                        }else if (updatepanel.get("Bar4Red").getAsBoolean()){
                            mprosbarr.setImageResource(R.drawable.unsolved);

                            mbar2.setTextColor(getResources().getColor(R.color.black));
                            mbar3.setTextColor(getResources().getColor(R.color.black));
                            mbar4.setTextColor(getResources().getColor(R.color.black));

                        }
                        else {
                            mprosbarr.setImageResource(R.drawable.complete);

                            mbar2.setTextColor(getResources().getColor(R.color.black));
                            mbar3.setTextColor(getResources().getColor(R.color.black));
                            mbar4.setTextColor(getResources().getColor(R.color.black));

                        }
                        //panel atas by defaultnya
                        mengineer.setText(updatepanel.get("EngineerName").getAsString());
                        //assist
//                    massistengineer2 = updatepanel.getAsJsonArray("Assists");
//                    String asist2 = "";
//                    for (int x = 0; x < massistengineer.size(); ++x){
//                        JsonObject assobj = massistengineer.get(x).getAsJsonObject();
//                        asist2 += assobj.get("Name").getAsString();
//                        asist2 += "\n";
////                        listticket.get(i).setAssist(asist);
//
//                    }
                        //bbutton
                        if (data.get("allowToStartProgress").getAsBoolean()){
                            mstartprogress.setVisibility(View.VISIBLE);
                            reopen = true;
                        }else {
                            mstartprogress.setVisibility(View.GONE);
                            reopen = false;
                        }
                        if (data.get("allowToUpdate").getAsBoolean()){
                            mupdatebtn.setVisibility(View.VISIBLE);
                        }else {
                            mupdatebtn.setVisibility(View.GONE);
                        }
                        //serviceType
                        listsn=data.getAsJsonArray("updatePanelServiceTypeOptions");
                        mpressId = updatepanel.get("ServiceTypeCd").getAsString();
                        int pos = 0;
                        for (int i = 0; i < listsn.size(); ++i) {
                            JsonObject jsonObject2 = (JsonObject)listsn.get(i);
                            String string4 = jsonObject2.getAsJsonObject().get("Text").getAsString();
                            String string5 = "";
                            if (jsonObject2.getAsJsonObject().get("Value").toString().equals("null")){
                                string5 = "-";
                            }else {
                                string5 = jsonObject2.getAsJsonObject().get("Value").getAsString();
                            }

//                        Integer previmpress = jsonObject2.getAsJsonObject().get("previousImpression").getAsInt();
                            snname.add(string4);
                            snid.add(string5);

                            for (int j = 0; j < snid.size(); ++j) {
                                if (snid.get(i).equals(mpressId)){
                                    pos=j;
                                }
                            }
//                        previmpression.add(previmpress);
                            ArrayAdapter arrayAdapter = new ArrayAdapter(DetailsST.this, R.layout.spinstatus_layout, snname);
                            arrayAdapter.setDropDownViewResource(R.layout.spinkategori);
                            arrayAdapter.notifyDataSetChanged();
                            mservicetypeST.setAdapter(arrayAdapter);
                            mservicetypeST.setSelection(pos);
                            loading.dismiss();
                        }
                        //status
                        if (!data.get("updatePanelShowStatusOptions").getAsBoolean()){
                            mlayoutstatus.setVisibility(View.GONE);
                        }else {
                            mlayoutstatus.setVisibility(View.VISIBLE);
                            listsna=data.getAsJsonArray("updatePanelStatusOptions");
                            mpressIda = updatepanel.get("Status").getAsString();
                            int posa = 0;
                            for (int i = 0; i < listsna.size(); ++i) {
                                JsonObject jsonObject2 = (JsonObject)listsna.get(i);
                                String string4 = jsonObject2.getAsJsonObject().get("Text").getAsString();
                                String string5 = "";
                                if (jsonObject2.getAsJsonObject().get("Value").toString().equals("null")){
                                    string5 = "-";
                                }else {
                                    string5 = jsonObject2.getAsJsonObject().get("Value").getAsString();
                                }

//                        Integer previmpress = jsonObject2.getAsJsonObject().get("previousImpression").getAsInt();
                                snnamea.add(string4);
                                snida.add(string5);

                                for (int j = 0; j < snida.size(); ++j) {
                                    if (snida.get(i).equals(mpressIda)){
                                        posa=j;
                                    }
                                }
//                        previmpression.add(previmpress);
                                ArrayAdapter arrayAdaptera = new ArrayAdapter(DetailsST.this, R.layout.spinstatus_layout, snnamea);
                                arrayAdaptera.setDropDownViewResource(R.layout.spinkategori);
                                arrayAdaptera.notifyDataSetChanged();
                                mstatusST.setAdapter(arrayAdaptera);
                                mstatusST.setSelection(posa);
                                loading.dismiss();
                            }
                        }
                        //NOTES
                        if (data.get("updatePanelNotes").toString().equals("null")){
//                        mlayoutnote.setVisibility(View.GONE);
                        }else {
//                        mlayoutnote.setVisibility(View.VISIBLE);

                        }
                        if (data.get("updatePanelNotes").toString().equals("null")){
                            mdescripst.setText("");
                        }else {
                            mdescripst.setText(data.get("updatePanelNotes").getAsString());
                        }
                        //lastimpres
                        if (!data.get("updatePanelShowLastImpression").getAsBoolean()){
                            mlayoutimpress.setVisibility(View.GONE);
                        }else {
                            mlayoutimpress.setVisibility(View.VISIBLE);

                        }
                        if (updatepanel.get("LastImpression").toString().equals("null")){
                            mlastimpresiST.setText("");
                        }else {
                            mlastimpresiST.setText(String.valueOf(updatepanel.get("LastImpression").getAsInt()));
                        }
                        //
                        if (updatepanel.get("LastImpression").toString().equals("null")){
                            mlastimpresiST.setText("");
                        }else {
                            mlastimpresiST.setText(String.valueOf(updatepanel.get("LastImpression").getAsInt()));
                        }
                    }


                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(DetailsST.this, errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsST.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
    }
    public void loadpartnya(){
        mloadpart.setVisibility(View.VISIBLE);
//        loading = ProgressDialog.show(DetailsST.this, "", "loading...", true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("search",sear);
        jsonObject.addProperty("page",1);
        jsonObject.addProperty("ver",ver);
//        Toast.makeText(DetailsFormActivity.this,jsonObject.toString(), Toast.LENGTH_SHORT).show();
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.getpart(jsonObject);
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
                    mloadpart.setVisibility(View.GONE);
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    listnotif = data.getAsJsonArray("sparePartList");
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Sparepart_item>>() {
                    }.getType();
                    sparepart_items = gson.fromJson(listnotif.toString(), listType);
                    sparepart_adapter = new Sparepart_adapter(DetailsST.this, sparepart_items);
                    mpartlist.setAdapter(sparepart_adapter);
                    Log.d("data4",homedata.toString());
                    loading.dismiss();
                    load=true;

                }else {
                    load=true;
                    mloadpart.setVisibility(View.GONE);
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(DetailsST.this, errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsST.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();
                load=true;
                mloadpart.setVisibility(View.GONE);

            }
        });
    }
//    public void cancelreq(){
//        loading = ProgressDialog.show(DetailsFormActivity.this, "", getString(R.string.title_loading), true);
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("sessionId",sesionid_new);
//        jsonObject.addProperty("formRequestCd",noreq);
//        jsonObject.addProperty("reason",mreason);
//        jsonObject.addProperty("ver",ver);
//        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
//        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONcancelform(jsonObject);
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
//                sesionid();
//                if (statusnya.equals("OK")){
//                    JsonObject data = homedata.getAsJsonObject("data");
//                    String message = data.get("message").getAsString();
//                    Toast.makeText(DetailsFormActivity.this, message,Toast.LENGTH_LONG).show();
//                    onBackPressed();
//                }else {
//                    sesionid();
//                    loading.dismiss();
//                    Toast.makeText(DetailsFormActivity.this,errornya,Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(DetailsFormActivity.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
//                cekInternet();
//                loading.dismiss();
//
//            }
//        });
//    }
    public void reopenreq(){
        loading = ProgressDialog.show(DetailsST.this, "", "loading...", true);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("serviceTicketCd",noreq);
        jsonObject.addProperty("serviceTypeCd",mpressId);
        jsonObject.addProperty("notes",mdescripst.getText().toString());
        jsonObject.addProperty("ver",ver);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.startprog(jsonObject);
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
                    String message = data.get("message").getAsString();
                    Toast.makeText(DetailsST.this, message,Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(DetailsST.this,errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsST.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
    }
    public void updatea(){
        loading = ProgressDialog.show(DetailsST.this, "", "loading...", true);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("serviceTicketCd",noreq);
        jsonObject.addProperty("serviceTypeCd",mpressId);
        jsonObject.addProperty("statusCd",mpressIda);
        jsonObject.addProperty("lastImpression",mlastimpresiST.getText().toString());
        jsonObject.addProperty("notes",mdescripst.getText().toString());
        jsonObject.add("sparePart",myCustomArray);
        jsonObject.addProperty("ver",ver);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.updatea(jsonObject);
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
                    String message = data.get("message").getAsString();
                    Toast.makeText(DetailsST.this, message,Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(DetailsST.this,errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsST.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });

        Log.d("jsonnya",jsonObject.toString());
    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");



    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
//                Intent gotoupdate = new Intent(DetailsST.this, UpdateActivity.class);
//                startActivity(gotoupdate);
//                finish();
            }

        }else {
            startActivity(new Intent(DetailsST.this, Login.class));
            finish();
            Toast.makeText(DetailsST.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    //    @Override

    public boolean appInstalledOrNot(String string2) {
        PackageManager packageManager = this.getPackageManager();

        try {
            packageManager.getPackageInfo(string2, packageManager.GET_ACTIVITIES);
            installed = true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            installed = false;

        }
        return installed;
    }
    private void startTimer() {
        if (seconds==0){

        }else {
            handler = new Handler() ;
            StartTime = SystemClock.uptimeMillis();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MillisecondTime = SystemClock.uptimeMillis() - StartTime;
//            mest.setText(String.valueOf(MillisecondTime));


                    Seconds = (int) (UpdateTime / 1000);
                    Jam = (Seconds/60/60);
                    if (TimeBuff>=3600000){
                        Minutes = (Seconds/60)-(Jam*60);
                    }else {
                        Minutes = Seconds / 60;
                    }
                    Seconds = Seconds % 60;
                    MilliSeconds = (int) (UpdateTime % 1000);

                    mtimer.setText(String.format("%02d", Jam) + ":"
                            + String.format("%02d", Minutes) + ":"
                            + String.format("%02d", Seconds));



                    handler.postDelayed(this, 1000);
                }
            }, 2000);
        }

//        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                mTimeLeftInMillis = millisUntilFinished;
////                updateCountDownText();
//            }
//
//            @Override
//            public void onFinish() {
////                mtimerconfirm.setText("00:00:00");
//
//            }
//        }.start();

    }
    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d",hours, minutes, seconds);
        mtimerconfirm.setText(getString(R.string.title_confirm)+" ("+timeLeftFormatted+")");
    }
    @SuppressLint("WrongConstant")
    public void dialogspar(){
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                this);
//
//        // set title dialog
//        alertDialogBuilder.setTitle(getString(R.string.title_update));
//
//        // set pesan dari dialog
//        alertDialogBuilder
//                .setMessage(getString(R.string.title_andaupdate))
//                .setIcon(R.mipmap.ic_launcher)
//                .setCancelable(false)
//                .setPositiveButton(getString(R.string.title_yes),new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int id) {
//                        // jika tombol diklik, maka akan menutup activity ini
//                        updatea();
//                    }
//                })
//                .setNegativeButton(getString(R.string.title_no),new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // jika tombol ini diklik, akan menutup dialog
//                        // dan tidak terjadi apa2
//                        dialog.cancel();
//                    }
//                });
//
//        // membuat alert dialog dari builder
//        AlertDialog alertDialog = alertDialogBuilder.create();
//
//        // menampilkan alert dialog
//        alertDialog.show();

        dialog.setContentView(R.layout.dialogspar);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        msearch = dialog.findViewById(R.id.searchitem);
        mpartlist = dialog.findViewById(R.id.listadditemfoc);
        mloadpart = dialog.findViewById(R.id.loadingfooter);
        linearLayoutManager = new LinearLayoutManager(DetailsST.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        mpartlist.setLayoutManager(linearLayoutManager);
        mpartlist.setHasFixedSize(true);
        sparepart_items = new ArrayList();
        msearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (load){
                    if (msearch.length()>=2){
                        load=false;
                        sear = msearch.getText().toString();

                       loadpartnya();

                    }
                }else {

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        TextView text = (TextView) dialog.findViewById(R.id.text);
//        text.setText("Android custom dialog example!");
//        ImageView image = (ImageView) dialog.findViewById(R.id.image);
//        image.setImageResource(R.drawable.ic_checked_data);



        dialog.show();
    }
    public void ReadNotif(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("guid",guid);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.Read(jsonObject);
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
//                    String message = data.get("message").getAsString();
//                    Toast.makeText(DetailsFormActivity.this, message,Toast.LENGTH_LONG).show();

                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(DetailsST.this,errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsST.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
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
//            getCurrentLocation();
//            if (reopen){
//                showDialogreopen();
//            }else {
//                showDialogrupdate();
//            }

        }else {
            if (lempar){

                Intent back = new Intent(DetailsST.this,Home.class);
//            back.putExtra("pos",valuefilter);
                startActivity(back);
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                finish();
            }else {

            }
            Toast.makeText(this, "Akese Lokasi Diperlukan", Toast.LENGTH_LONG).show();

        }
    }

    @SuppressLint("MissingPermission")
//    private void getCurrentLocation() {
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//                @Override
//                public void onComplete(@NonNull Task<Location> task) {
//
//                    android.location.Location location = task.getResult();
//                    if (location != null) {
////                        mlati.setText(String.valueOf(location.getLatitude()));
////                        mlongi.setText(String.valueOf(location.getLongitude()));
//                    } else {
//                        LocationRequest locationRequest = new LocationRequest()
//                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//                                .setInterval(1000)
//                                .setFastestInterval(1000)
//                                .setNumUpdates(1);
//                        LocationCallback locationCallback = new LocationCallback() {
//                            @Override
//                            public void onLocationResult(@NonNull LocationResult locationResult) {
//                                android.location.Location location1 = locationResult.getLastLocation();
////                                mlati.setText(String.valueOf(location1.getLatitude()));
////                                mlongi.setText(String.valueOf(location1.getLongitude()));
//                            }
//                        };
//                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
//                                locationCallback, Looper.myLooper());
//                    }
//
//                }
//            });
//        } else {
//            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//        }
//    }
    public void onBackPressed() {
//        super.onBackPressed();
        if (home.equals("homes")){
            Intent back = new Intent(DetailsST.this,Home.class);
//            back.putExtra("pos",valuefilter);
            startActivity(back);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
        }else {
            if (check.checknotif==1){
                if (username==null){
                    if (check.checklistform==1){
                        list2.clear();
                        refresh=true;
                    }
                    super.onBackPressed();
                    finish();

                }else {
                    super.onBackPressed();
//            refresh=true;
                    Intent back = new Intent(DetailsST.this,ServiceTicket.class);
                    back.putExtra("pos",valuefilter);
                    startActivity(back);
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    finish();
                }
            }else {
                Intent back = new Intent(DetailsST.this,Home.class);
                back.putExtra("pos",valuefilter);
                startActivity(back);
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                finish();
            }
        }



    }
}
