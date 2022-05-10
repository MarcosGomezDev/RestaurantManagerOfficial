package com.example.appbar.ui.table_box;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbar.R;
import com.example.appbar.data.DataBase;
import com.example.appbar.data.ItemData;
import com.example.appbar.databinding.FragmentTableBoxBinding;
import com.example.appbar.ui.items.ItemAdapter;
import com.example.appbar.ui.tables.TablesFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class TableBoxFragment extends Fragment {

    private FragmentTableBoxBinding binding;
    private DataBase dataBase;
    private DatabaseReference myRef;
    private TableBasketAdapter tableBasketAdapter;
    private RecyclerView recyclerView;
    private ArrayList<ItemData> list;
    private String userUID;
    private Context context;
    public static boolean comeFromTableBox;
    private ImageButton addItemTableButton, removeItemTableButton;
    private Button noButton;
    private Switch reservedSwitch;

    private final String currentTable = "MESA " + TablesFragment.currentNumTableString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTableBoxBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        comeFromTableBox = false;
        addItemTableButton = view.findViewById(R.id.addItemTableButton);
        removeItemTableButton = view.findViewById(R.id.removeItemTableButton);
        noButton = view.findViewById(R.id.noButton);
        recyclerView = view.findViewById(R.id.itemsTablesRecycler);
        reservedSwitch = view.findViewById(R.id.reservedSwitch);
        noButton.setText(currentTable);

        dataBase = new DataBase();
        userUID = dataBase.getCurrentUser().getUid();
        myRef = dataBase.getInstance().getReference(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(TablesFragment.currentNumTableString)
                .child("items_basket");
        context = this.getActivity();
        list = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        tableBasketAdapter = new TableBasketAdapter(context, list);
        recyclerView.setAdapter(tableBasketAdapter);

        readBasket();

        addItemTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comeFromTableBox = true;
                Navigation.findNavController(v).navigate(R.id.nav_table_items);
            }
        });

        removeItemTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reservedSwitch.isChecked()) {

                }
            }
        });




    }

    public void readBasket() {
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ItemData itemData = dataSnapshot.getValue(ItemData.class);
                    list.add(itemData);
                }
                tableBasketAdapter.notifyDataSetChanged();
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

    public Switch getReservedSwitch() {
        return reservedSwitch;
    }

    public void setReservedSwitch(Switch reservedSwitch) {
        this.reservedSwitch = reservedSwitch;
    }
}
