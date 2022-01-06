package com.sccengineer;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sccengineer.apihelper.IRetrofit;
import com.sccengineer.apihelper.ServiceGenerator;
import com.sccengineer.loca.Locati;
import com.sccengineer.notificationclock.NotifclockAdapter;
import com.sccengineer.notificationclock.NotifclockItems;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.sccengineer.apihelper.ServiceGenerator.baseurl;

public class MyLocation extends BroadcastReceiver {
    public static String sesionid_new = "";
    boolean clokin=true;
    DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference();;
    Locati locati;
    public static final String ACTION_PROCESS_UPDATE =
            "edmt.dev.googleloactionbackground.UPDATE_LOCATION";
    @Override
    public void onReceive(Context context, Intent intent) {
        if ( intent != null){
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATE.equals(action)){
                LocationResult result = LocationResult.extractResult(intent);
                if (result!=null){
                    Location location = result.getLastLocation();
                    String locanya = new StringBuilder(""+location.getLatitude())
                            .append("/")
                            .append(location.getLongitude())
                            .toString();

                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(context, Locale.getDefault());

                    try{
//                        MainActivity.getInstance().updateloca(locanya);
                        locati= new Locati();
                        locati.setLatitude(String.valueOf(location.getLatitude()));
                        locati.setLongitude(String.valueOf(location.getLongitude()));
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();
                        SharedPreferences sharedPreferences = context.getSharedPreferences("SESSION_ID",MODE_PRIVATE);
                        sesionid_new = sharedPreferences.getString("session_id", "");
                        Log.d("session",sesionid_new);
                        reqApi();
                        if (clokin){
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("sessionId",sesionid_new);
                            jsonObject.addProperty("longitude",String.valueOf(location.getLongitude()));
                            jsonObject.addProperty("latitude",String.valueOf(location.getLatitude()));
                            jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
                            IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
                            Call<JsonObject> panggilkomplek = jsonPostService.sendinfolocation(jsonObject);
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
//                                MhaveToUpdate = homedata.get("haveToUpdate").toString();
//                                MsessionExpired = homedata.get("sessionExpired").toString();
//                jsonObject.addProperty("ver",ver);
                                    if (statusnya.equals("OK")) {
                                        JsonObject data = homedata.getAsJsonObject("data");
                                        Log.d("lokasinyaok",String.valueOf(location.getLongitude())+"//"+String.valueOf(location.getLatitude()));

                                    }else {
                                        Log.d("lokasinya",String.valueOf(location.getLongitude())+"//"+String.valueOf(location.getLatitude()));
                                        Log.d("lokasinyadata",homedata.toString());
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Log.d("lokasinya",String.valueOf(location.getLongitude())+"//"+String.valueOf(location.getLatitude()));
                                }
                            });
                            Log.d("reqlokasi",jsonObject.toString());
                            Log.d("lokasi2",city+"-"+state);
                        }else {
                            Log.d("lokasi3","clokout");
                        }

                    } catch (Exception e) {

                    }
                }
            }
        }
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
//                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")) {
                    JsonObject data = homedata.getAsJsonObject("data");

                    clokin= data.get("alreadyClockIn").getAsBoolean();

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
//                mrefresh.setVisibility(View.VISIBLE);
//                mcheck.setVisibility(View.GONE);
//                Toast.makeText(ClockInActivity.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
//                cekInternet();
//                loading.dismiss();
            }
        });

    }
    public void getSessionId(){



    }
}
