/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Color
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 *  androidx.recyclerview.widget.RecyclerView
 *  androidx.recyclerview.widget.RecyclerView$Adapter
 *  androidx.recyclerview.widget.RecyclerView$ViewHolder
 *  com.squareup.picasso.Picasso
 *  com.squareup.picasso.RequestCreator
 *  java.io.PrintStream
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.text.ParseException
 *  java.text.SimpleDateFormat
 *  java.util.ArrayList
 *  java.util.Date
 *  java.util.Locale
 */
package com.sccengineer.generatechat;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sccengineer.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static com.sccengineer.DownloadBroadcastReceiver.key;
import static com.sccengineer.DownloadBroadcastReceiver.pathnya;
import static com.sccengineer.DownloadBroadcastReceiver.urinya;
import static com.sccengineer.ListChat.databaseReference3;
import static com.sccengineer.ListChat.mback;
import static com.sccengineer.ListChat.mcopy;
import static com.sccengineer.ListChat.mcopylay;
import static com.sccengineer.ListChat.mdelcop;
import static com.sccengineer.ListChat.mdelet;
import static com.sccengineer.ListChat.name;
import static com.sccengineer.ListChat.sendtext;
import static com.sccengineer.ListChat.sessionnya;


//import static com.e.chatforscctest.ListChat.idhcat;


public class Adaptergeneratechat
extends RecyclerView.Adapter<Adaptergeneratechat.Myviewholder>  {
    public static ArrayList<Itemgeneratechat> addFoclistreq;
    Context context;
    public Adaptergeneratechat(Context context, ArrayList<Itemgeneratechat> addFoclistitem) {
        this.context = context;
        this.addFoclistreq = addFoclistitem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_generatechat,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {

        //setting posisi chat dan visible file
        myviewholder.mtext.setText(addFoclistreq.get(i).getText());
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendtext.setText(addFoclistreq.get(i).getText());
                int position = addFoclistreq.get(i).getText().length();
                Editable etext = sendtext.getText();
                Selection.setSelection(etext, position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return 
                addFoclistreq.size();
    }


    public static class Myviewholder extends RecyclerView.ViewHolder{

        TextView mtext;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mtext = itemView.findViewById(R.id.generatetext);

        }
    }


}

