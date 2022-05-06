package com.example.appbar.ui.tables;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbar.R;
import com.example.appbar.data.DataBase;
import com.example.appbar.data.TablesData;
import com.example.appbar.databinding.FragmentTablesBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TablesFragment extends Fragment implements View.OnClickListener{

    private FragmentTablesBinding binding;
    private DataBase dataBase;
    private DatabaseReference myRef;
    private TableAdapter tableAdapter;
    private RecyclerView recyclerView;
    private ArrayList<TablesData> list;
    private Context context;
    private String userUID;

    public static String currentNumTableString;
    public static String currentCapacityTableString;
    public static boolean currentReservedTableBool;
    public static int countTable;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTablesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
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
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        tableAdapter = new TableAdapter(context, list);
        recyclerView.setAdapter(tableAdapter);

        tableAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNumTableString = list.get(
                        recyclerView.getChildAdapterPosition(v)).getNumTable();
                currentCapacityTableString = list.get(
                        recyclerView.getChildAdapterPosition(v)).getNumPeople();
                currentReservedTableBool = list.get(
                        recyclerView.getChildAdapterPosition(v)).isReserved();
                Navigation.findNavController(v).navigate(R.id.nav_table_box);
            }
        });

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

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.editTebleeButton) {
//            Navigation.findNavController(v).navigate(R.id.nav_table_selected);
//        }
    }
}