package com.example.appbar.ui.tables;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbar.R;
import com.example.appbar.data.DataBase;
import com.example.appbar.data.ItemData;
import com.example.appbar.databinding.FragmentTableItemsAddBinding;
import com.example.appbar.ui.items.ItemAdapter;
import com.example.appbar.ui.table_box.TableBoxFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class TableItemsAdd extends Fragment {

    private FragmentTableItemsAddBinding binding;
    private DataBase dataBase;
    private ItemData item;
    private String descriptionItemString;
    private double priceItemDouble;
    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;
    private ArrayList<ItemData> list;
    private String userUID;
    private Context context;
    private String currentTable;
    private long newUnitSub;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTableItemsAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.tableItemListRecyclerView);
        currentTable = TablesFragment.currentNumTableString;
        dataBase = new DataBase();
        userUID = dataBase.getCurrentUser().getUid();

        context = this.getActivity();
        list = new ArrayList<>();
        itemAdapter = new ItemAdapter(context, list);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        itemAdapter.setOnClickListener(v -> {
            descriptionItemString = list.get(
                    recyclerView.getChildAdapterPosition(v)).getDescription();
            priceItemDouble = list.get(
                    recyclerView.getChildAdapterPosition(v)).getPrice();

            item = new ItemData(descriptionItemString, priceItemDouble,
                    1, priceItemDouble);

            String userUID = dataBase.getCurrentUser().getUid();
            String currentItemPK = descriptionItemString
                    .replace(" ", "_");

            dataBase.getDatabaseReference()
                    .child(userUID)
                    .child(dataBase.PARENT_TABLES())
                    .child(currentTable)
                    .child("items_basket")
                    .child(currentItemPK)
                    .setValue(item);

            TableBoxFragment.totalAmountDouble += priceItemDouble;
            TableBoxFragment.totalAmountDouble = Math
                    .round(TableBoxFragment.totalAmountDouble * 100d) / 100d;

            dataBase.getDatabaseReference()
                    .child(userUID)
                    .child(dataBase.PARENT_TABLES())
                    .child(currentTable)
                    .child("items_basket")
                    .child("basket_amount")
                    .setValue(TableBoxFragment.totalAmountDouble);
            Toast.makeText(getContext(), "Articulo añadido", Toast.LENGTH_LONG).show();

            Navigation.findNavController(v).navigate(R.id.nav_table_box);
        });

        DatabaseReference myRef = dataBase.getInstance().getReference(userUID)
                .child(dataBase.PARENT_ITEMS());
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ItemData itemData = dataSnapshot.getValue(ItemData.class);
                    list.add(itemData);
                }
                itemAdapter.notifyDataSetChanged();
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
