package com.example.appbar.ui.tables;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbar.R;
import com.example.appbar.data.DataBase;
import com.example.appbar.data.DataFlow;
import com.example.appbar.data.TablesData;
import com.example.appbar.databinding.FragmentTablesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Clase para el manejo de las mesas.
 */
@SuppressWarnings("FieldCanBeLocal")
public class TablesFragment extends Fragment {

    private FragmentTablesBinding binding;
    private DataBase dataBase;
    private DatabaseReference myRef;
    private TableAdapter tableAdapter;
    private RecyclerView recyclerView;
    private ArrayList<TablesData> list;
    private Context context;
    private String userUID;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTablesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataBase = new DataBase();
        recyclerView = view.findViewById(R.id.table_RecyclerView);
        userUID = dataBase.getCurrentUser().getUid();
        myRef = dataBase.getInstance().getReference(userUID).child(dataBase.PARENT_TABLES());
        context = this.getActivity();
        list = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        tableAdapter = new TableAdapter(context, list);
        recyclerView.setAdapter(tableAdapter);

        /**
         * Listener que captura los datos de la mesa seleccionada y envia al usuario a la vista
         * correspondiente para la gestion de dicha mesa.
         */
        tableAdapter.setOnClickListener(v -> {
            DataFlow.currentNumTableString = list.get(
                    recyclerView.getChildAdapterPosition(v)).getNumTable();
            DataFlow.currentCapacityTableString = list.get(
                    recyclerView.getChildAdapterPosition(v)).getNumPeople();
            DataFlow.currentReservedTableBool = list.get(
                    recyclerView.getChildAdapterPosition(v)).isReserved();
            Navigation.findNavController(v).navigate(R.id.nav_table_box);
        });

        /**
         * Listener que controla los cambios en la base de datos y actualiza el RecyclerView.
         */
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TablesData tableData = dataSnapshot.getValue(TablesData.class);
                    list.add(tableData);
                }
                tableAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}