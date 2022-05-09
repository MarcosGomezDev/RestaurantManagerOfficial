package com.example.appbar.ui.bookings;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbar.R;
import com.example.appbar.data.BookingsData;
import com.example.appbar.data.DataBase;
import com.example.appbar.databinding.FragmentBookingsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.StringValue;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookingsFragment extends Fragment implements View.OnClickListener {

    private FragmentBookingsBinding binding;
    private DataBase dataBase;
    private DatabaseReference myRef;
    private BookingsAdapter bookingsAdapter;
    private RecyclerView recyclerView;
    private ArrayList<BookingsData> list;
    private String userUID;
    private Context context;
    private FloatingActionButton fa_fecha,fa_annadir;
    private TextView fecha2Textview,diatextView,mestextView,annotextView;
    private ImageButton diaimagenButtom,mesimagenButton,annoimagenButton;
    private int i;

    private String dia= "dd";
    private String mes ="MM";
    private String anno = "yyyy";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fa_annadir = view.findViewById(R.id.fa_annadir);
        //fecha2Textview = view.findViewById(R.id.fecha2textView);
        recyclerView = view.findViewById(R.id.r);
        diatextView = view.findViewById(R.id.diatextView);
        mestextView = view.findViewById(R.id.mestextView);
        annotextView = view.findViewById(R.id.annotextView);
        diaimagenButtom = view.findViewById(R.id.diaimageButton);
        mesimagenButton = view.findViewById(R.id.mesimageButton);
        annoimagenButton = view.findViewById(R.id.annoimageButton);
        diatextView.setText(fechadia());
        mestextView.setText(fechames());
        annotextView.setText(fechaanno());
        diaimagenButtom.setOnClickListener(this);
        mesimagenButton.setOnClickListener(this);
        annoimagenButton.setOnClickListener(this);
        fa_annadir.setOnClickListener(this);
        dataBase = new DataBase();
        reservas();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public String fechadia(){
        String date = new SimpleDateFormat(dia).format(new Date());
        return date;
    }
    public String fechames(){
        String date = new SimpleDateFormat(mes).format(new Date());
        return date;
    }
    public String fechaanno(){
        String date = new SimpleDateFormat(anno).format(new Date());
        return date;
    }

    public void reservas(){
        userUID = dataBase.getCurrentUser().getUid();
        myRef = dataBase.getInstance().getReference(userUID).child(dataBase.PARENT_BOOKING());
        context = this.getActivity();
        list = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        bookingsAdapter = new BookingsAdapter(context, list);
        recyclerView.setAdapter(bookingsAdapter);


        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BookingsData bookingsData = dataSnapshot.getValue(BookingsData.class);
                    if (bookingsData.getFecha().compareTo((diatextView.getText().toString().trim())+"/"+(mestextView.getText().toString().trim())+"/"+(annotextView.getText().toString().trim()))==0) {
                        list.add(bookingsData);
                    }
                }
                bookingsAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fa_annadir:
                Navigation.findNavController(view).navigate(R.id.nav_booking_selected);
                break;
            case R.id.diaimageButton:
                  i = Integer.valueOf(diatextView.getText().toString().trim());
                 i = i +1;
                 diatextView.setText(String.valueOf(i));
                reservas();
            break;
            case R.id.mesimageButton:
                i = Integer.valueOf(mestextView.getText().toString().trim());
                i = i +1;
                mestextView.setText(String.valueOf(i));
                reservas();
                break;
            case R.id.annoimageButton:
                i = Integer.valueOf(annotextView.getText().toString().trim());
                i = i +1;
                annotextView.setText(String.valueOf(i));
                reservas();
                break;
        }
    }

}
