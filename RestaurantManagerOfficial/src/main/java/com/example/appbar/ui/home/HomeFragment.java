package com.example.appbar.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appbar.data.DataBase;
import com.example.appbar.data.ItemData;
import com.example.appbar.R;
import com.example.appbar.data.TablesData;
import com.example.appbar.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("FieldCanBeLocal")
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private final DataBase dataBase = new DataBase();
    private TextView reserva_textView,item_textView,fechaac_textView,empleados_textView;
    private long reservas = 0;
    private long items = 0;
    private long empleados = 0;
    private String userUID;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reserva_textView = view.findViewById(R.id.reserva_textView);
        item_textView = view.findViewById(R.id.item_textView);
        fechaac_textView = view.findViewById(R.id.fechaact_textView);
        empleados_textView = view.findViewById(R.id.empleados_textView);
        fechaac_textView.setText(fecha());
        Reservas();
        empleados_Activos();
    }

    @Override
    public void onStart() {
        super.onStart();
        starDataBase();
    }

    public void starDataBase() {
        String userUID = dataBase.getCurrentUser().getUid();
        dataBase.getDatabaseReference().child(userUID).child(dataBase.PARENT_ITEMS())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ItemData item = new ItemData();
                        items= snapshot.getChildrenCount();
                        String numItemsString = "Productos dados de Alta " + items;
                        item_textView.setText(numItemsString);
                        if(!snapshot.exists()){
                            item.addAllSampleItems();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                }
        );
        dataBase.getDatabaseReference().child(userUID).child(dataBase.PARENT_TABLES())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        TablesData table = new TablesData();
                        if(!snapshot.exists()){
                            table.addInitialTables();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                }
        );
    }

    public void Reservas(){
        userUID = dataBase.getCurrentUser().getUid();
        dataBase.getDatabaseReference().child(userUID).child("booking").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    reservas= snapshot.getChildrenCount();
                    reserva_textView.setText("Reservas Activas  " + reservas);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void empleados_Activos(){
        userUID = dataBase.getCurrentUser().getUid();
        dataBase.getDatabaseReference().child(userUID).child("staff").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (snapshot.exists()) {
                    empleados = snapshot.getChildrenCount();
                    empleados_textView.setText("Empleados activos " + empleados);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public String fecha(){
        @SuppressLint("SimpleDateFormat")
        String date = new SimpleDateFormat("dd MMMM yyyy   HH:mm").format(new Date());
        return date;
    }
}