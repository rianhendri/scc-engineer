package com.sccengineer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sccengineer.messagecloud.check;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.sccengineer.ServiceTicket.list2;
import static com.sccengineer.ServiceTicket.refresh;
import static com.sccengineer.ServiceTicket.valuefilter;

public class ClockOutActivity extends AppCompatActivity {
    TextView mbackto,mclockouttime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_out);
        mbackto = findViewById(R.id.clockin);
        mclockouttime = findViewById(R.id.clockouttime);

        SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
        Date myDate = new Date();
        String filename = timeStampFormat.format(myDate);
        mclockouttime.setText(filename);
        mbackto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void onBackPressed() {
//        super.onBackPressed();
        Intent back = new Intent(ClockOutActivity.this,ClockInActivity.class);
//            back.putExtra("pos",valuefilter);
        startActivity(back);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();

    }
}