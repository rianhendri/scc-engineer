package com.sccengineer;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.sccengineer.Chat.Adapterchat.download;
import static com.sccengineer.Chat.Adapterchat.download2;
import static com.sccengineer.ListChat.sessionnya;

public class DownloadBroadcastReceiver extends BroadcastReceiver {
    public static String pathnya = "";
    public static String key="0";
    public static String urinya = "";
    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            //Show a notification
            HashMap hashMap = new HashMap();
            if (urinya.equals("me")){
                hashMap.put("myuri",pathnya);
            }else {
                hashMap.put("youruri",pathnya);
            }

            if (key.equals("0")) {
                download=true;
            }else {
                databaseReference2.child("chat").child(sessionnya).child("listchat").child(key).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "File berhasil didownload", Toast.LENGTH_SHORT).show();
                        Log.d("hasmapnya", hashMap.toString());
                        download=true;
                        download2=true;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        download=true;
                        download2=true;
                        Toast.makeText(context, "ggl", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
