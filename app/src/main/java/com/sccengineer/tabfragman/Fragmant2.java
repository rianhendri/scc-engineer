package com.sccengineer.tabfragman;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sccengineer.AttendanceActivity;
import com.sccengineer.FormCuti;
import com.sccengineer.R;
import com.sccengineer.jadwal.JadwalAdapter;
import com.sccengineer.leaveform.LeaveAdapter;
import com.sccengineer.leaveform.LeaveItems;

import java.util.ArrayList;

public class Fragmant2 extends Fragment {
    RecyclerView mlistcuti;
    Spinner mstatus;
    ArrayList<LeaveItems> listpoact = new ArrayList<LeaveItems>();
    LeaveAdapter leaveAdapter;
    LeaveItems leaveItems;
    LinearLayoutManager linearLayoutManager, linearLayoutManager2, linearLayoutManager3;
    LinearLayout maddrequset;
    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmant2, container, false);
        mlistcuti = view.findViewById(R.id.listleave);
        mstatus = view.findViewById(R.id.spinstatus);
        maddrequset = view.findViewById(R.id.addrequset);
        leaveItems= new LeaveItems();
        leaveItems.setClockIn("08-06-2021");
        leaveItems.setClockOut("10-06-2021");
        leaveItems.setDate("Istri Lahiran");
        leaveItems.setRoleCd("Approved");
        listpoact.add(leaveItems);

        linearLayoutManager3 = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        mlistcuti.setLayoutManager(linearLayoutManager3);
        mlistcuti.setHasFixedSize(true);

        leaveAdapter = new LeaveAdapter(getActivity(), listpoact);
        mlistcuti.setAdapter(leaveAdapter);

        maddrequset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotos = new Intent(getActivity(), FormCuti.class);
                startActivity(gotos);
                getActivity().finish();
            }
        });
        return view;
    }
}
