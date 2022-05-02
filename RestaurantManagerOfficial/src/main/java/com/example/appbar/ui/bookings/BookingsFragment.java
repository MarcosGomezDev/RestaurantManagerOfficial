package com.example.appbar.ui.bookings;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbar.R;
import com.example.appbar.data.BookingsData;
import com.example.appbar.data.DataBase;
import com.example.appbar.data.ItemData;
import com.example.appbar.databinding.FragmentBookingsBinding;
import com.example.appbar.ui.items.ItemAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookingsFragment extends Fragment implements View.OnClickListener {

    private FragmentBookingsBinding binding;
    private TextView tv_fecha,textView5;
    private ListView lista;
    FloatingActionButton fa_annadir;

    private DataBase dataBase = new DataBase();
    private String userUID;
    private BookingsData data;

    ArrayAdapter<String> adapter;
    ArrayList<BookingsData> mlista = new ArrayList<>();
    ArrayList<String> mlistaString = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBookingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
       // lista = root.findViewById(R.id.lista);
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_fecha = view.findViewById(R.id.tv_fecha);
        //lista =view.findViewById(R.id.lista);
        fa_annadir = view.findViewById(R.id.fa_annadir);


        fa_annadir.setOnClickListener(this);
        fecha();
        reservas();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void fecha(){

        String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        tv_fecha.setText(date);
    }
    public void reservas(){

        userUID = dataBase.getCurrentUser().getUid();
        dataBase.getDatabaseReference().child(userUID).child("booking").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        Navigation.findNavController(v).navigate(R.id.nav_booking_selected);
    }
}
