package com.example.appbar.ui.table_box;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbar.R;
import com.example.appbar.data.DataBase;
import com.example.appbar.data.ItemData;
import com.example.appbar.databinding.FragmentTableBoxBinding;
import com.example.appbar.ui.items.ItemsFragment;
import com.example.appbar.ui.tables.TablesFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import kotlin.contracts.Returns;

@SuppressWarnings("FieldCanBeLocal")
public class TableBoxFragment extends Fragment implements View.OnClickListener{

    private FragmentTableBoxBinding binding;
    private DataBase dataBase;
    private TableBasketAdapter tableBasketAdapter;
    private RecyclerView recyclerView;
    private ArrayList<ItemData> list;
    private String userUID;
    private Context context;
    public static boolean comeFromTableBox;
    public static double totalAmountDouble;
    private Button noButton, modifyButton, addItemTableButton;
    private TextView totalAmountTextView;
    private final String currentTable = TablesFragment.currentNumTableString;
    private final String currentTableTitle = "MESA " + currentTable;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTableBoxBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        comeFromTableBox = false;
        addItemTableButton = view.findViewById(R.id.addItemTableButton);
        noButton = view.findViewById(R.id.noButton);
        modifyButton = view.findViewById(R.id.modifyButton);
        recyclerView = view.findViewById(R.id.itemsTablesRecycler);
        totalAmountTextView = view.findViewById(R.id.totalAmountTextView);
        modifyButton.setOnClickListener(this);
        addItemTableButton.setOnClickListener(this);
        noButton.setText(currentTableTitle);

        dataBase = new DataBase();
        userUID = dataBase.getCurrentUser().getUid();
        context = this.getActivity();
        list = new ArrayList<>();
        tableBasketAdapter = new TableBasketAdapter(context, list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                        recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(tableBasketAdapter);

        tableBasketAdapter.setOnClickListener(v -> {
            ItemsFragment.currentDescriptionItemString = list.get(
                    recyclerView.getChildAdapterPosition(v)).getDescription();
            ItemsFragment.currentPriceItemDouble = list.get(
                    recyclerView.getChildAdapterPosition(v)).getPrice();
            ItemsFragment.currentUnitItemLong = list.get(
                    recyclerView.getChildAdapterPosition(v)).getUnits();
            Navigation.findNavController(v).navigate(R.id.nav_remove_items_basket);
        });

        DatabaseReference ref_items_basket = dataBase.getInstance().getReference(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(currentTable)
                .child("items_basket");
        ref_items_basket.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    try {
                        ItemData itemData = dataSnapshot.getValue(ItemData.class);
                        list.add(itemData);
                    } catch (Exception e) {
                        Log.println(Log.WARN, "***********Fallo***********",
                                "TableBoxFragment.java, line: 118");
                    }
                }
                tableBasketAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        DatabaseReference ref_basket_amount = dataBase.getInstance().getReference(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(currentTable)
                .child("items_basket")
                .child("basket_amount");
        ref_basket_amount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String res = snapshot.getValue().toString().trim();
                    totalAmountTextView.setText(res + " €");
                } else {
                    totalAmountTextView.setText("0 €");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addItemTableButton:
                comeFromTableBox = true;
                Navigation.findNavController(v).navigate(R.id.nav_table_items);
                break;
            case R.id.modifyButton:
                Navigation.findNavController(v).navigate(R.id.nav_table_selected);
                break;
        }
    }
}
