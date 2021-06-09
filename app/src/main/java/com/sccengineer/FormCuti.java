package com.sccengineer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import static com.sccengineer.TabAct.positab;

public class FormCuti extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cuti);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, TabAct.class));
        positab = 1;
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
}