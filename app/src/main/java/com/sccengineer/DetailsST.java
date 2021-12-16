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
import android.app.DatePickerDialog;
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
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sccengineer.Chat.Adapterchat;
import com.sccengineer.Chat.Itemchat;
import com.sccengineer.apihelper.IRetrofit;
import com.sccengineer.apihelper.ServiceGenerator;
import com.sccengineer.listsparepart.Sparepart_adapter;
import com.sccengineer.listsparepart.Sparepart_item;
import com.sccengineer.messagecloud.check;
import com.sccengineer.serviceticket.ServiceTicketAdapter;
import com.sccengineer.serviceticket.ServicesTicketItem;
import com.sccengineer.spartsend.SendSparepart_adapter;
import com.sccengineer.spartsend.SendSparepart_item;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sccengineer.ListChat.modultrans;
import static com.sccengineer.ListChat.name;
import static com.sccengineer.LiveChatList.titlenya;
import static com.sccengineer.ServiceTicket.list2;
import static com.sccengineer.ServiceTicket.refresh;
import static com.sccengineer.DetailsST.valuefilter;
import static com.sccengineer.apihelper.ServiceGenerator.baseurl;
import static com.sccengineer.listsparepart.Sparepart_adapter.listpoact;
import static com.sccengineer.listsparepart.Sparepart_adapter.tambahpart;
import static com.sccengineer.messagecloud.check.tokennya2;

public class DetailsST extends AppCompatActivity {
    CheckBox mcheckboxbtn;
    AlertDialog.Builder dialog2,dialog3;
    TextView mdatenya,mchngeesti,mdatechangeesti;
    String dateschedjul="";
    LinearLayout mdatebtn,mlayoutupdateestimate, mlayoutadddaily;
    Calendar myCalendar = Calendar.getInstance();
    Spinner mrole;
    JsonArray rolejson;
    List<String> rolelist = new ArrayList();
    List<String> rolecvalue = new ArrayList();
    String valerole = "";
    LayoutInflater inflater;
    View dialogView;
    //Timer Set New
    Timer timer;
    TimerTask timerTask;
    Double time = 7200.0;
    //
    String engas="";
    ArrayList<Itemchat> itemchat;
    Itemchat itemchat2;
    Adapterchat adapterchat;
    int total1 = 0;
    boolean inforeopen = true;
    EditText mreasonnya;
    public static String noreq = "";
    String servicetypenya = "";
    String MhaveToUpdate = "";
    JsonObject data = null;
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
    public  static String mcustname="";
    boolean chatin = false;
    String sessionnya="";
    String viewdetails="";
    String mdeskriptionapi = "";
    public  static String mformRequestCd = "";
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
    String chats = "";
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
    TextView madddaily,mlistdailyst,mtextmhon,mlinkgenerate,mdatestatus,mlalbeldate,msupport,mbar1,mbar2,mbar3,mbar4,mactionprogress,mestimasi,mstarttime,mendtime,massigndate,mengineer,masengineer, mstatustick, mtimer;
    EditText mlastimpresiST, mdescripst;
    Spinner mservicetypeST, mstatusST;
    LinearLayout mstartprogresssched,mlayountlink,mlayoutdate,mlayoutsper,mlayestima,mstartprogress, mupdatebtn,mcancleassg, mlayoutimpress, mlayoutnote, mlayoutstatus, mlayoutservicest, mlayoutupdatepanel;
    JsonArray listsn;
    List<String> snid = new ArrayList();
    List<String> snname = new ArrayList();
    String mpressId = "";
    JsonArray listsna;
    List<String> snida = new ArrayList();
    List<String> snnamea = new ArrayList();
    List<Boolean> estimatedate = new ArrayList();
    List<Boolean> checkbox = new ArrayList();
    LinearLayout mlayoutcheck;
    String mpressIda = "";
    boolean checkboxsts = false;
    String home="homesa";
    String modultrans="";
    long MillisecondTime, StartTime, UpdateTime = 0L ;
    long TimeBuff = seconds ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds, Jam ;
    FusedLocationProviderClient fusedLocationProviderClient;
    String lati="";
    String longi="";
    boolean reopen = true;
    boolean lempar = true;

    ProgressBar mloadpart;
    public static RecyclerView mpartlist, msendpartlist;
    EditText msearch,mnamespara,mqtyspera,mreasona, mcodemanual;
    ImageView meditebtna;
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
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter2;
    private Calendar newDate= Calendar.getInstance();
    boolean cekdate= false;
    String notesapi = "";
    LinearLayout mchactclik;
    String tokennya = "-";
//    List<String> tokennya2= new ArrayList();
    LinearLayout mdot,mdailyreport,mupdatespertbtn;
    TextView mnotif;
    String scrollnya = "no";
    String titlenya = "";
    String titlelist = "";
    String moduletrans="";
    DatabaseReference databaseReference5;
    ScrollView mscroll;
    public static int yverti=0;
    public static int xhori=0;

    //lokasi
    boolean lokasi = false;

    FirebaseAuth mAuth;
    public static String valuefilter="";
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_s_t);
        mlayoutadddaily = findViewById(R.id.layoutadddaily);
        madddaily = findViewById(R.id.adddaily);
        mlistdailyst = findViewById(R.id.listdailyst);
        mupdatespertbtn =findViewById(R.id.updatespertbtn);
        mcheckboxbtn = findViewById(R.id.checkboxbtn);
        mtextmhon = findViewById(R.id.textmhon);
        mdailyreport = findViewById(R.id.dailyreport);
        mdatechangeesti = findViewById(R.id.datechangeesti);
        mscroll = findViewById(R.id.scrollnya);
        mchactclik = findViewById(R.id.chatclik);
        mcancleassg = findViewById(R.id.canclebtn);
        mlayoutcheck=findViewById(R.id.layoutcheck);
        //panelupdate
        mlayoutupdateestimate=findViewById(R.id.layoutupdateestimate);
        mchngeesti = findViewById(R.id.chngeesti);
        mnotif = findViewById(R.id.newnotif);
        mdot = findViewById(R.id.dot);
        mlayountlink = findViewById(R.id.linkbtn);
        mlinkgenerate = findViewById(R.id.linkgenerate);
        mlayoutdate = findViewById(R.id.layoutdate);
        mdatestatus = findViewById(R.id.datestatus);
        mlalbeldate = findViewById(R.id.labeldate);
        mlayoutsper = findViewById(R.id.layoutsper);
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
        mstartprogresssched = findViewById(R.id.startprogresssched);
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
        modultrans="";
        tokennya2.clear();

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


//        if (ActivityCompat.checkSelfPermission(DetailsST.this,
//                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(DetailsST.this
//                ,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            if (internet){
//                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(DetailsST.this);
//                getCurrentLocation();
//            }
//
//            lempar = false;
//        }else {
//            ActivityCompat.requestPermissions(DetailsST.this
//                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
//                    },100);
//        }
        //getsessionId
        myCustomArray = new JsonArray();
        if (myCustomArray!=null){
            Log.d("customaraynya",myCustomArray.toString());
        }

        seconds=0;
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            if (bundle2.getString("chats")!=null){
                chats = "yes";
            }else {
                chats = "no";
            }
            if (bundle2.getString("chat")!=null){
                chatin = bundle2.getBoolean("chat");
            }else {
//                chats = false;
            }
            chatin = bundle2.getBoolean("chat");
            if (bundle2.getString("sessionnya")!=null){
                sessionnya = bundle2.getString("sessionnya");
            }else {
//                chats = false;
            }
            titlelist=bundle2.getString("titlelist");
            modultrans = bundle2.getString("id");
            viewdetails = bundle2.getString("viewdetails");
            noreq = bundle2.getString("id");
            home = bundle2.getString("home");
            guid = bundle2.getString("guid");
            username = bundle2.getString("user");
            noticket = bundle2.getString("id");
            valuefilter = bundle2.getString("pos");
            scrollnya =   bundle2.getString("scrolbawah");
            xhori=bundle2.getInt("xhori");
            yverti=bundle2.getInt("yverti");
            titlenya=bundle2.getString("titlenya");
//            Log.d("viewdetails",viewdetails);
        }
        rolelist.add(getString(R.string.title_pleasechoose));
        rolecvalue.add("-");
        getSessionId();
        cekInternet();
        if (internet){
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(DetailsST.this);
            reqApi();;
            loadData();
            if (guid==null){

            }else {
                ReadNotif();
            }
        }else {

        }
        String TAG = "FirebaseMessaging";
        Log.d(TAG,"noreq:"+noreq);


        mlayountlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdescripst.setMinLines(15);
                mdescripst.setText(notesapi);
            }
        });
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mdailyreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotodaily = new Intent(DetailsST.this, DetailsDailyReportAct.class);
                startActivity(gotodaily);
            }
        });
        mstartprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code baru
                requestlokasi();
                if (lokasi){
                    getCurrentLocation();
                    showDialogreopen();
                }else {

                }
                //code lama
                if (ActivityCompat.checkSelfPermission(DetailsST.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(DetailsST.this
                        ,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
//                    showDialogreopen();
                }else {
                    ActivityCompat.requestPermissions(DetailsST.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                            },100);
                }

            }
        });
        mstartprogresssched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogsched();
            }
        });
        mupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code baru
                requestlokasi();
                if (lokasi){
                    getCurrentLocation();
                    if (cekdate){
                        if (mdatestatus.getText().toString().equals("-")){
                            Toast.makeText(DetailsST.this, "Tanggal due date wajib diisi", Toast.LENGTH_SHORT).show();
                        }else {
                            showDialogrupdate();
                        }
                    }else {
                        showDialogrupdate();
                    }
                }else {

                }
                // code lama
//                if (ActivityCompat.checkSelfPermission(DetailsST.this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                        && ActivityCompat.checkSelfPermission(DetailsST.this
//                        ,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    getCurrentLocation();
//                    if (cekdate){
//                        if (mdatestatus.getText().toString().equals("-")){
//                            Toast.makeText(DetailsST.this, "Tanggal due date wajib diisi", Toast.LENGTH_SHORT).show();
//                        }else {
//                            showDialogrupdate();
//                        }
//                    }else {
//                        showDialogrupdate();
//                    }
//
//                }else {
//                    ActivityCompat.requestPermissions(DetailsST.this
//                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
//                            },100);
//                }
            }
        });
//        mcancleassg.setVisibility(View.GONE);
        mcancleassg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
DialogForm();
            }
        });

        mlayoutcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mcheckboxbtn.isChecked()){
                    mcheckboxbtn.setChecked(false);
//                    Toast.makeText(DetailsST.this, "true", Toast.LENGTH_SHORT).show();
                }else {
                    mcheckboxbtn.setChecked(true);
//                    Toast.makeText(DetailsST.this, "false", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //update sparepartbtn click
        mupdatespertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogupdatesparepartbtn();
            }
        });
        madddaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsST.this, DailyReportAdd.class);
//              intent.putExtra("id", (addFromItem.get(i).getFormRequestCd()));
                intent.putExtra("home", "homesa");
                intent.putExtra("pos", valuefilter);
                intent.putExtra("id", noreq);
                intent.putExtra("pos", valuefilter);
                intent.putExtra("user", username);
                intent.putExtra("scrolbawah", scrollnya);
                intent.putExtra("xhori", xhori);
                intent.putExtra("yverti", yverti);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                finish();
            }
        });
        mlistdailyst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsST.this, DailyReportList.class);
//              intent.putExtra("id", (addFromItem.get(i).getFormRequestCd()));
                intent.putExtra("home", "homesa");
                intent.putExtra("pos", valuefilter);
                intent.putExtra("noticket", noreq);
                intent.putExtra("pos", valuefilter);
                intent.putExtra("user", username);
                intent.putExtra("scrolbawah", scrollnya);
                intent.putExtra("xhori", xhori);
                intent.putExtra("yverti", yverti);
                intent.putExtra("hide","yes");
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                finish();
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

//        mscroll.setVerticalScrollbarPosition(1949);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mscroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    yverti = scrollY;
                    xhori = scrollX;
                    Log.d("scrollabe", String.valueOf(scrollX)+"/"+String.valueOf(scrollY)+"/");
                }
            });

        }
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

                if (checkbox.get(position).booleanValue()){
                    mlayoutcheck.setVisibility(View.VISIBLE);
                }else {
                    mlayoutcheck.setVisibility(View.GONE);
                }
                if (estimatedate.get(position).booleanValue()){
                    cekdate = true;
                    mlayoutdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDateDialog();
                        }
                    });
                    mlayoutdate.setVisibility(View.VISIBLE);
                    String string2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//                    mdatestatus.setText((CharSequence)string2);
//                    Log.d("date55", String.valueOf(newDate.getTime()));
                }else {
                    mlayoutdate.setVisibility(View.GONE);
                    cekdate = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //timer
        timer = new Timer();
//        startTimernew();
        mspar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogspar();
            }
        });
        mchngeesti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogchangeesti();
            }
        });
    }

    private void dialogchangeesti() {
        dialog3 = new AlertDialog.Builder(DetailsST.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_stschedjul, null);
        dialog3.setView(dialogView);
        dialog3.setCancelable(true);
        dialog3.setIcon(R.mipmap.ic_launcher);
        dialog3.setTitle("Change estimation date");
        mdatenya = dialogView.findViewById(R.id.datenya);
        mdatebtn = dialogView.findViewById(R.id.datebtn);
        myCalendar = Calendar.getInstance();
        //        kosong();
        Date today = Calendar.getInstance().getTime();//getting date
        Calendar tomorroaw = Calendar.getInstance();
        tomorroaw.add(Calendar.DAY_OF_YEAR, 1);//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//formating according to my need
        String date3 = formatter.format(today);
        Date tomorrow = tomorroaw.getTime();
        String date4 = formatter.format(tomorrow);
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat datefor = new SimpleDateFormat("yyyy-MMMM-dd", Locale.getDefault());
        String estima = mdatechangeesti.getText().toString();
        String estimadate = "";
        try {
            estimadate = datefor.format(simpleDateFormat.parse(estima));
            System.out.println(estimadate);
            Log.e((String)"Date", (String)estimadate);
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        mdatenya.setText(estimadate);
//        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel();
//            }
//
//        };
        mdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker2();
                // TODO Auto-generated method stub
//                new DatePickerDialog(DetailsST.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        dialog3.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dateschedjul = mdatenya.getText().toString();
                changeestimatedate();
                dialog.dismiss();
            }
        });

        dialog3.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog3.show();
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

    private void dialogsched() {
        dialog3 = new AlertDialog.Builder(DetailsST.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_stschedjul, null);
        dialog3.setView(dialogView);
        dialog3.setCancelable(true);
        dialog3.setIcon(R.mipmap.ic_launcher);
        dialog3.setTitle("Scheduled Progress");
        mdatenya = dialogView.findViewById(R.id.datenya);
        mdatebtn = dialogView.findViewById(R.id.datebtn);
        myCalendar = Calendar.getInstance();
    //        kosong();
        Date today = Calendar.getInstance().getTime();//getting date
        Calendar tomorroaw = Calendar.getInstance();
        tomorroaw.add(Calendar.DAY_OF_YEAR, 1);//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//formating according to my need
        String date3 = formatter.format(today);
        Date tomorrow = tomorroaw.getTime();
        String date4 = formatter.format(tomorrow);

        mdatenya.setText(date4);
//        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel();
//            }
//
//        };
        mdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker2();
                // TODO Auto-generated method stub
//                new DatePickerDialog(DetailsST.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        dialog3.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dateschedjul = mdatenya.getText().toString();
                startprogressschej();
                dialog.dismiss();
            }
        });

        dialog3.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog3.show();
    }
    private void updateLabel() {
        String myFormat = "dd-MMMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mdatenya.setText(sdf.format(myCalendar.getTime()));
    }
    private void DialogForm() {
        dialog2 = new AlertDialog.Builder(DetailsST.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_cancleassigment, null);
        dialog2.setView(dialogView);
        dialog2.setCancelable(true);
        dialog2.setIcon(R.mipmap.ic_launcher);
        dialog2.setTitle(getString(R.string.title_cancelassignment));
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

        dialog2.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (valerole.equals("-")){
                    Toast.makeText(DetailsST.this, "Wajib Pilih Status", Toast.LENGTH_SHORT).show();
                }else {
                    canclerequest();
    //                clockinnya();
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

        dialog2.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog2.show();
    }
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
    private void canclerequest(){
        loading = ProgressDialog.show(DetailsST.this, "", "loading...", true);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("serviceTicketCd",noreq);
        jsonObject.addProperty("status",valerole);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.canclerequest(jsonObject);
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
                    Toast.makeText(DetailsST.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsST.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
        Log.d("reqcanle",jsonObject.toString());
    }
    private void dialogupdatesparepartbtn() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Update Spareparts");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage(getString(R.string.title_andaupdate))
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.title_yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        updatea2sparepart();
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
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
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
                Log.d("loaddatanya",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                if (statusnya.equals("OK")){

//                    sesionid();
                    Log.d("sessionId",MsessionExpired);
                   data = homedata.getAsJsonObject("data");
                   //showadd daily
                    if (data.get("showAddDailyReport").getAsBoolean()){
                        madddaily.setVisibility(View.VISIBLE);
                        madddaily.setText(data.get("addDailyReportButtonText").getAsString());
                    }else {
                        if (data.get("showViewSTDailyReport").getAsBoolean()){

                        }else {
                            mlayoutadddaily.setVisibility(View.GONE);
                        }
                    }
                    if (data.get("showViewSTDailyReport").getAsBoolean()){
                        mlistdailyst.setVisibility(View.VISIBLE);
                        mlistdailyst.setText(data.get("stDailyReportButtonText").getAsString());
                    }else {

                    }
                   //showhide buttuon update sparepart
                    if(data.get("showUpdateSparePartButton").getAsBoolean()){
                        mupdatespertbtn.setVisibility(View.VISIBLE);
                    }else {
                        mupdatespertbtn.setVisibility(View.GONE);
                    }
                    //SHOW START PROGRESS SCHEDJULE
                    if (data.get("allowToScheduledProgress").getAsBoolean()){
                        mstartprogresssched.setVisibility(View.VISIBLE);
                    }else {
                        mstartprogresssched.setVisibility(View.GONE);
                    }
                    //show hide estimate update
                    if (data.get("allowToUpdateEstimationDate").getAsBoolean()){
                        mlayoutupdateestimate.setVisibility(View.VISIBLE);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                        SimpleDateFormat datefor = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String estima = data.get("defaultEstimationDate").getAsString();
                        String estimadate = "";
                        try {
                            estimadate = datefor.format(simpleDateFormat.parse(estima));
                            System.out.println(estimadate);
                            Log.e((String)"Date", (String)estimadate);
                        }
                        catch (ParseException parseException) {
                            parseException.printStackTrace();
                        }
                        mdatechangeesti.setText(estimadate);

                    }else {
                        mlayoutupdateestimate.setVisibility(View.GONE);
                    }
                    // cancle btn show
                    if (data.get("allowToCancel").getAsBoolean()){
                        mcancleassg.setVisibility(View.VISIBLE);
                        rolejson = data.getAsJsonArray("updatePanelCancellationStatusOptions");
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
                    }else {
                        mcancleassg.setVisibility(View.GONE);
                    }
                    //setnoteupdatepanels
                    if (data.get("generateNotes").toString().equals("null")){
                        mlinkgenerate.setVisibility(View.GONE);
                    }else {
                        notesapi = data.get("generateNotes").getAsString();

                        mlinkgenerate.setVisibility(View.VISIBLE);
                    }

                    // layoutsper
                    if (data.get("showSparePart").getAsBoolean()){
                        mlayoutsper.setVisibility(View.VISIBLE);
                    }else {
                        mlayoutsper.setVisibility(View.GONE);
                    }
                    if (data.get("allowToAddSparePart").getAsBoolean()){
                        mspar.setVisibility(View.VISIBLE);
                        mtextmhon.setVisibility(View.VISIBLE);
                    }else {
                        mspar.setVisibility(View.GONE);
                        mtextmhon.setVisibility(View.GONE);
                    }

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
                    mcustname = data.get("createdBy").getAsString();
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

                    //separpart tanpa updatepanel
                    if (data.get("selectedSparePartList").toString().equals("null")){

                    }else {
                        myCustomArray = data.getAsJsonArray("selectedSparePartList");
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<SendSparepart_item>>() {
                        }.getType();
                        sendsparepart_items = gson.fromJson(myCustomArray.toString(), listType);
                        sendsparepart_adapter = new SendSparepart_adapter(DetailsST.this, sendsparepart_items);
                        msendpartlist.setAdapter(sendsparepart_adapter);
                    }

                    //chat baru pasang
                    if(data.get("liveChatShowButton").getAsBoolean()){
                        itemchat = new ArrayList<Itemchat>();
                        itemchat2 = new Itemchat();
                        databaseReference5= FirebaseDatabase.getInstance().getReference().child("chat").child(data.get("liveChatID").getAsString()).child("listchat");
                        //
                        mchactclik.setVisibility(View.VISIBLE);
                        name=data.get("liveChatUserName").getAsString();
                        if (data.get("liveChatOthersFirebaseToken").toString().equals("null")){
                            tokennya = "-";
                        }else {
                            tokennya2.clear();
                            JsonArray tokeny = data.getAsJsonArray("liveChatOthersFirebaseToken");
                            for (int c = 0; c < tokeny.size(); ++c) {
                                JsonObject assobj2 = tokeny.get(c).getAsJsonObject();
                                tokennya2.add(assobj2.get("Token").getAsString());
                            }

                            Log.d("listToken", tokennya2.toString());
                        }
                        databaseReference5.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemchat.clear();
                                total1 = 0;
                                if (dataSnapshot.exists()){
                                    for(DataSnapshot ds: dataSnapshot.getChildren())
                                    {
                                        Itemchat fetchDatalist=ds.getValue(Itemchat.class);
                                        fetchDatalist.setKey(ds.getKey());
                                        itemchat.add(fetchDatalist);
                                    }

                                    adapterchat=new Adapterchat(DetailsST.this, itemchat);
                                    for (int i = 0; i < itemchat.size(); i++) {
                                        if (itemchat.get(i).getName().equals(name)){
                                            mdot.setVisibility(View.GONE);
                                        }else {

                                            if (itemchat.get(i).getRead().equals("yes")){
                                                mdot.setVisibility(View.GONE);
                                            }else {
                                                total1 +=1;
                                                mdot.setVisibility(View.VISIBLE);
                                                mnotif.setText(String.valueOf(total1));
                                            }
                                        }
                                    }

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        engas = "";
                    }else {
                        mchactclik.setVisibility(View.GONE);

                    }
                    mchactclik.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            loading.show();
//                            Window window = loading.getWindow();
//                            window.setLayout(300, 300);
                            Intent gotonews = new Intent(DetailsST.this, ListChat.class);
                            gotonews.putExtra("name",data.get("liveChatUserName").getAsString());
                            gotonews.putExtra("sessionnya",data.get("liveChatID").getAsString());
                            gotonews.putExtra("chat",data.get("liveChatAllowToChat").getAsBoolean());
                            gotonews.putExtra("titlenya",data.get("liveChatTitle").getAsString());
                            gotonews.putExtra("user",username);
                            gotonews.putExtra("stnya",noreq);
                            gotonews.putExtra("moduletrans", "kosong");
                            gotonews.putExtra("id",data.get("liveChatTitle").getAsString());
                            gotonews.putExtra("liveChatRepor",data.get("liveChatReportWhenUserChat").getAsBoolean());
                            gotonews.putExtra("page","detailst");
                            gotonews.putExtra("tokennya",tokennya);
                            gotonews.putExtra("engname", mcustname);
                            gotonews.putExtra("nofr", mformRequestCd);
                            gotonews.putExtra("home", home);
                            gotonews.putExtra("ping",1);
                            startActivity(gotonews);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            finish();
                            /////

                        }
                    });
                    /////
                    if (data.get("updatePanelLatestAction").toString().equals("null")){

                    }else {

                        JsonObject updatepanel = data.getAsJsonObject("updatePanelLatestAction");
                        //cabut chat
//                                if(updatepanel.get("ShowLiveChat").getAsBoolean()){
//                                    itemchat = new ArrayList<Itemchat>();
//                                    itemchat2 = new Itemchat();
//                                    databaseReference5= FirebaseDatabase.getInstance().getReference().child("chat").child(updatepanel.get("Guid").getAsString()).child("listchat");
//                                    //
//                                    mchactclik.setVisibility(View.VISIBLE);
//                                    if (updatepanel.get("LiveChatFirebaseToken").toString().equals("null")){
//                                        tokennya = "-";
//                                    }else {
//                                        JsonArray tokeny = updatepanel.getAsJsonArray("LiveChatFirebaseToken");
//                                        for (int c = 0; c < tokeny.size(); ++c) {
//                                            JsonObject assobj2 = tokeny.get(c).getAsJsonObject();
//                                            tokennya2.add(assobj2.get("Token").getAsString());
//                                        }
//
//                                        Log.d("listToken", tokennya2.toString());
//                                    }
//                                    databaseReference5.addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            itemchat.clear();
//                                            total1 = 0;
//                                            if (dataSnapshot.exists()){
//                                                for(DataSnapshot ds: dataSnapshot.getChildren())
//                                                {
//                                                    Itemchat fetchDatalist=ds.getValue(Itemchat.class);
//                                                    fetchDatalist.setKey(ds.getKey());
//                                                    itemchat.add(fetchDatalist);
//                                                }
//
//                                                adapterchat=new Adapterchat(DetailsST.this, itemchat);
//                                                for (int i = 0; i < itemchat.size(); i++) {
//                                                    if (itemchat.get(i).getName().equals(name)){
//                                                        mdot.setVisibility(View.GONE);
//                                                    }else {
//
//                                                        if (itemchat.get(i).getRead().equals("yes")){
//                                                            mdot.setVisibility(View.GONE);
//                                                        }else {
//                                                            total1 +=1;
//                                                            mdot.setVisibility(View.VISIBLE);
//                                                            mnotif.setText(String.valueOf(total1));
//                                                        }
//                                                    }
//                                                }
//
//                                            }
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
//
//                                    engas = "";
//                                }else {
//                                    mchactclik.setVisibility(View.GONE);
//
//                                }
//                        mchactclik.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent gotonews = new Intent(DetailsST.this, ListChat.class);
//                                gotonews.putExtra("name",updatepanel.get("LiveChatName").getAsString());
//                                gotonews.putExtra("sessionnya",updatepanel.get("Guid").getAsString());
//                                gotonews.putExtra("chat",updatepanel.get("LiveChatAllowChat").getAsBoolean());
//                                gotonews.putExtra("user",username);
//                                gotonews.putExtra("id",noreq);
//                                gotonews.putExtra("tokennya",tokennya);
//                                gotonews.putExtra("engname", mcustname);
//                                gotonews.putExtra("nofr", mformRequestCd);
//                               startActivity(gotonews);
//                               overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                             finish();
//                            }
//                        });
                        //sparepartupdatepanel
                        if(updatepanel.get("WaitingEstimationLabel").toString().equals("null")){

                        }else {
                            mlalbeldate.setText(updatepanel.get("WaitingEstimationLabel").getAsString());
                        }

//                        if (data.get("selectedSparePartList").toString().equals("null")){
//
//                        }else {
//                            myCustomArray = data.getAsJsonArray("selectedSparePartList");
//                            Gson gson = new Gson();
//                            Type listType = new TypeToken<ArrayList<SendSparepart_item>>() {
//                            }.getType();
//                            sendsparepart_items = gson.fromJson(myCustomArray.toString(), listType);
//                            sendsparepart_adapter = new SendSparepart_adapter(DetailsST.this, sendsparepart_items);
//                            msendpartlist.setAdapter(sendsparepart_adapter);
//                        }


                        //timer Update
//                        usetimea = updatepanel.getAsString()
                        if (data.get("updatePanelUseTimer").getAsBoolean()){
                            msupport.setVisibility(View.GONE);
                            mtimer.setVisibility(View.VISIBLE);
                            int hours = data.get("updatePanelTimerStartHours").getAsInt();
                            int minute = data.get("updatePanelTimerStartMinutes").getAsInt();
                            int second = data.get("updatePanelTimerStartSeconds").getAsInt();
                            seconds = (hours*60*60)+(minute*60)+(second);
                            time = Double.valueOf(seconds);
                            Log.d("secondop",String.valueOf(hours)+":"+String.valueOf(minute)+":"+String.valueOf(second));
                            TimeBuff = seconds;
                            UpdateTime = TimeBuff + MillisecondTime;
                            startTimernew();

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
                    massistengineer2 = updatepanel.getAsJsonArray("Assists");
                    String asist2 = "";
                    for (int i = 0; i < massistengineer2.size(); ++i){
                        JsonObject assobj = massistengineer2.get(i).getAsJsonObject();
                        asist2 += assobj.get("Name").getAsString();
                        asist2 += "\n";
//                        listticket.get(i).setAssist(asist2);
                        masengineer.setText(asist2);
                        Log.d("assist2",asist2);

                    }
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
                                Boolean string7 = jsonObject2.getAsJsonObject().get("ShowEstimateDateInput").getAsBoolean();
                                Boolean chec = jsonObject2.getAsJsonObject().get("ShowReplacementCheckBox").getAsBoolean();
                                String string5 = "";
                                if (jsonObject2.getAsJsonObject().get("Value").toString().equals("null")){
                                    string5 = "-";
                                }else {
                                    string5 = jsonObject2.getAsJsonObject().get("Value").getAsString();
                                }

//                        Integer previmpress = jsonObject2.getAsJsonObject().get("previousImpression").getAsInt();
                                snnamea.add(string4);
                                snida.add(string5);
                                estimatedate.add(string7);
                                checkbox.add(chec);
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

                    if (scrollnya==null){
                        mscroll.scrollTo(0,2029);
                    }else {
                        mscroll.scrollTo(xhori,yverti);
                    }
                    requestlokasi();
                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(DetailsST.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(DetailsST.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsST.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
        Log.d("loadDetailst",jsonObject.toString());
    }
    public void loadpartnya(){
        mloadpart.setVisibility(View.VISIBLE);
//        loading = ProgressDialog.show(DetailsST.this, "", "loading...", true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("search",sear);
        jsonObject.addProperty("page",1);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
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
                    Toast.makeText(DetailsST.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(DetailsST.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
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
        Log.d("sperpartnya",jsonObject.toString());
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
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.startprog(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("responstartprog",homedata.toString());
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
                    Toast.makeText(DetailsST.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsST.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
        Log.d("jsonstart",jsonObject.toString());
    }
    public void startprogressschej(){
        loading = ProgressDialog.show(DetailsST.this, "", "loading...", true);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("serviceTicketCd",noreq);
        jsonObject.addProperty("serviceTypeCd",mpressId);
        jsonObject.addProperty("scheduledDate",dateschedjul);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.startprogsched(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("responesche",homedata.toString());
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
                    Toast.makeText(DetailsST.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsST.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
        Log.d("jsonstart2",jsonObject.toString());
    }
    public void changeestimatedate(){
        loading = ProgressDialog.show(DetailsST.this, "", "loading...", true);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("serviceTicketCd",noreq);
        jsonObject.addProperty("serviceTypeCd",mpressId);
        jsonObject.addProperty("estimationDate",dateschedjul);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.UpdateEsti(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("responesche",homedata.toString());
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
                    Toast.makeText(DetailsST.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsST.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
        Log.d("jsonstart2",jsonObject.toString());
    }
    public void updatea2sparepart(){
        if (mcheckboxbtn.isChecked()){
            checkboxsts=true;
        }else {
            checkboxsts=false;
        }
        loading = ProgressDialog.show(DetailsST.this, "", "loading...", true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("serviceTicketCd",noreq);
        jsonObject.add("sparePart",myCustomArray);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.updateasparepart(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("jsonnya",homedata.toString());
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
//                    loadData();
                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(DetailsST.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(DetailsST.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsST.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });

        Log.d("requpdate",jsonObject.toString());
    }
    public void updatea(){
        if (mcheckboxbtn.isChecked()){
            checkboxsts=true;
        }else {
            checkboxsts=false;
        }
        loading = ProgressDialog.show(DetailsST.this, "", "loading...", true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("serviceTicketCd",noreq);
        jsonObject.addProperty("serviceTypeCd",mpressId);
        jsonObject.addProperty("statusCd",mpressIda);
        jsonObject.addProperty("lastImpression",mlastimpresiST.getText().toString());
        jsonObject.addProperty("notes",mdescripst.getText().toString());
        jsonObject.addProperty("waitingEstimationDate",mdatestatus.getText().toString());
        jsonObject.add("sparePart",myCustomArray);
        jsonObject.addProperty("withReplacement",checkboxsts);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        jsonObject.addProperty("longitude",lati);
        jsonObject.addProperty("latitude",longi);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.updatea(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("jsonnya",homedata.toString());
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
//                    loadData();
                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(DetailsST.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(DetailsST.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsST.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });

        Log.d("requpdate",jsonObject.toString());
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
//            Toast.makeText(DetailsST.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
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

        dialog.setContentView(R.layout.dialogspar);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        msearch = dialog.findViewById(R.id.searchitem);
        mpartlist = dialog.findViewById(R.id.listadditemfoc);
        mloadpart = dialog.findViewById(R.id.loadingfooter);
        mnamespara = dialog.findViewById(R.id.namespara);
        mqtyspera = dialog.findViewById(R.id.qtyspera);
        mreasona = dialog.findViewById(R.id.reasona);
        meditebtna = dialog.findViewById(R.id.editebtna);
        mcodemanual = dialog.findViewById(R.id.codecda);
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
        mqtyspera.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (mqtyspera.length()>0){

                    if (Integer.parseInt(mqtyspera.getText().toString())>99){
                        mqtyspera.setText("99");
                        Toast.makeText(DetailsST.this, "Qty maksimal 99", Toast.LENGTH_SHORT).show();
                    }else {

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1 && s.toString().startsWith("0")) {
                    s.clear();
                }
            }
        });
        meditebtna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahpart = new SendSparepart_item();
                tambahpart.setManualSparePartName(mnamespara.getText().toString());
                tambahpart.setManualSparePartCd(mcodemanual.getText().toString());
                tambahpart.setSparePartCd("");
                tambahpart.setInstallDate(null);
                tambahpart.setName(null);
                tambahpart.setOrderDate(null);
                tambahpart.setStsAllowEdit(true);
                tambahpart.setStsAllowUpdateInstallDate(true);
                tambahpart.setStsAllowDelete(true);
                tambahpart.setStatusName("-");
                tambahpart.setCaseID("-");
                
                tambahpart.setReason(mreasona.getText().toString());
                tambahpart.setQuantity(Integer.parseInt(mqtyspera.getText().toString()));

                listpoact.add(tambahpart);
                sendsparepart_items.addAll(listpoact);
                Gson gson = new GsonBuilder().create();
                myCustomArray = gson.toJsonTree(sendsparepart_items).getAsJsonArray();
                jsonarayitem = myCustomArray.toString();

                listpoact.clear();
                Log.d("sizecart_11", String.valueOf(sendsparepart_items.size()));
                Log.d("sizecart_22", String.valueOf(jsonarayitem));
////////////////////// adapter di masukan ke recyler//
                sendsparepart_adapter = new SendSparepart_adapter(DetailsST.this, sendsparepart_items);
                msendpartlist.setAdapter(sendsparepart_adapter);
                dialog.dismiss();
            }
        });



        dialog.show();
    }
    public void ReadNotif(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("guid",guid);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
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
                    Toast.makeText(DetailsST.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(DetailsST.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsST.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
        Log.d("readnotif",jsonObject.toString());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == 100  && grantResults[0]+grantResults[1]
                == PackageManager.PERMISSION_GRANTED){
            Log.d("requestcodenya",String.valueOf(requestCode)+permissions[0]+
                    permissions[1]+grantResults[0]+grantResults[1]+PackageManager.PERMISSION_GRANTED);
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(DetailsST.this);
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
            Toast.makeText(this, "Akses lokasi diperlukan", Toast.LENGTH_LONG).show();

        }
    }
    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Lokasi perlu diaktifkan")
                .setCancelable(false)
                .setPositiveButton("Go to GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){

                                ActivityCompat.requestPermissions(DetailsST.this
                                        , new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION
                                        },100);

                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        Toast.makeText(DetailsST.this, "Mohon aktifkan lokasi", Toast.LENGTH_LONG).show();
                        dialog.cancel();

                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    private  void requestlokasi(){
        if (ActivityCompat.checkSelfPermission(DetailsST.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(DetailsST.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DetailsST.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            return;
        }else{
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(DetailsST.this);
                getCurrentLocation();
                lokasi=true;
//                DialogForm();
//                    Toast.makeText(ClockInActivity.this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
            }else{
                lokasi=false;
                showGPSDisabledAlertToUser();
            }
            // Write you code here if permission already given.

        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    android.location.Location location = task.getResult();
                    if (location != null) {
                        lati=String.valueOf(location.getLatitude());
                        longi = String.valueOf(location.getLongitude());
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
    public void onBackPressed() {
        Log.d("chekcnotif",String.valueOf(check.checknotif)+"/"+String.valueOf(check.checknotif)+username);
//        super.onBackPressed();
        if (home==null){
            if (check.checknotif==1){
                if (username==null){
                    if (check.checkhome==0){
                        if (check.checklistform==1){
                            list2.clear();
                            refresh=true;
                        }
                        super.onBackPressed();
                        finish();
                    }else {
                        Intent back = new Intent(DetailsST.this,Home.class);
                        back.putExtra("pos",valuefilter);
                        startActivity(back);
                        overridePendingTransition(R.anim.left_in, R.anim.right_out);
                        finish();
                    }


                }else {
                    if (chats.equals("yes")){
                        Intent back = new Intent(DetailsST.this,ListChat.class);
                        back.putExtra("name",username);
                        back.putExtra("sessionnya",sessionnya);
                        back.putExtra("chat",chatin);
                        back.putExtra("user",name);
                        back.putExtra("page","listchat");
                        back.putExtra("chats","yes");
                        back.putExtra("id",viewdetails);
                        back.putExtra("cs","no");
                        back.putExtra("titlenya", titlenya);
                        back.putExtra("module","ServiceSupport");
//                gotonews.putExtra("tokennya",token);
                        back.putExtra("engname", mcustname);
                        back.putExtra("titlelist", titlelist);
                        back.putExtra("nofr", mformRequestCd);
                        back.putExtra("xhori", xhori);
                        back.putExtra("yverti", yverti);
                        back.putExtra("scrolbawah","-");
                        back.putExtra("moduletrans", modultrans);
                        startActivity(back);
                        overridePendingTransition(R.anim.left_in, R.anim.right_out);
                        finish();
                    }else {
                        Intent back = new Intent(DetailsST.this,ServiceTicket.class);
                        back.putExtra("pos",valuefilter);
                        startActivity(back);
                        overridePendingTransition(R.anim.left_in, R.anim.right_out);
                        finish();
                    }

                }
            }else {
                Intent back = new Intent(DetailsST.this,Home.class);
                back.putExtra("pos",valuefilter);
                startActivity(back);
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                finish();
            }
        }else {
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
                        if (chats.equals("yes")){
                            Intent back = new Intent(DetailsST.this,ListChat.class);
                            back.putExtra("name",username);
                            back.putExtra("sessionnya",sessionnya);
                            back.putExtra("chat",chatin);
                            back.putExtra("user",name);
                            back.putExtra("page","listchat");
                            back.putExtra("titlenya", titlenya);
                            back.putExtra("titlelist", titlelist);
                            back.putExtra("chats","yes");
                            back.putExtra("id",viewdetails);
                            back.putExtra("cs","no");
                            back.putExtra("module","ServiceSupport");
//                gotonews.putExtra("tokennya",token);
                            back.putExtra("engname", mcustname);
                            back.putExtra("nofr", mformRequestCd);
                            back.putExtra("xhori", xhori);
                            back.putExtra("yverti", yverti);
                            back.putExtra("scrolbawah","-");
                            back.putExtra("moduletrans", modultrans);
                            startActivity(back);
                            overridePendingTransition(R.anim.left_in, R.anim.right_out);
                            finish();

                        }else {
                            Intent back = new Intent(DetailsST.this,ServiceTicket.class);
                            back.putExtra("pos",valuefilter);
                            startActivity(back);
                            overridePendingTransition(R.anim.left_in, R.anim.right_out);
                            finish();
                        }
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
    private void showDateDialog(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
//                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd");
                mdatestatus.setText(dateFormatter2.format(newDate.getTime()));
                Log.d("date55", String.valueOf(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
    private void showDateDialogupdate(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
//                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd");
                mdatestatus.setText(dateFormatter2.format(newDate.getTime()));
                Log.d("date55", String.valueOf(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
    private void datepicker2(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
//                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih GANTI MMMM
                 */
                dateFormatter2 = new SimpleDateFormat("dd-MMMM-yyyy");
                mdatenya.setText(dateFormatter2.format(newDate.getTime()));
                Log.d("date55", String.valueOf(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()+24*60*60*1000);
        datePickerDialog.show();
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
                Log.d("reqapi1",homedata.toString());

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
                        startActivity(new Intent(DetailsST.this, ClockInActivity.class));
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
                    Toast.makeText(DetailsST.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
//                mrefresh.setVisibility(View.VISIBLE);
//                mcheck.setVisibility(View.GONE);
                Toast.makeText(DetailsST.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading.dismiss();
            }
        });
        Log.d("reqapi",jsonObject.toString());

    }
//timer new up
private void startTimernew()
{
    timerTask = new TimerTask()
    {
        @Override
        public void run()
        {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    time++;
                    mtimer.setText(getTimerText());
                }
            });
        }

    };
    timer.scheduleAtFixedRate(timerTask, 0 ,1000);
}
    private String getTimerText()
    {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours)
    {
        return String.format("%02d",hours) + " : " + String.format("%02d",minutes) + " : " + String.format("%02d",seconds);
    }
}
