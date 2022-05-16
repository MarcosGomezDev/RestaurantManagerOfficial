package com.example.appbar.ui.tables;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.example.appbar.databinding.FragmentTableItemsBinding;
import com.example.appbar.ui.items.ItemAdapter;
import com.example.appbar.ui.table_box.TableBoxFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class TableItemsSelected extends Fragment {

    private FragmentTableItemsBinding binding;
    private DataBase dataBase;
    private ItemData item;
    private DatabaseReference myRef;
    public static String currentDescriptionItemString;
    public static double currentPriceItemDouble;
    private ItemAdapter tableItemAdapter;
    private RecyclerView recyclerView;
    private ArrayList<ItemData> list;
    private String userUID;
    private Context context;
    private TextView totalAmountTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTableItemsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.tableItemListRecyclerView);
        totalAmountTextView = view.findViewById(R.id.totalAmountTextView);
        dataBase = new DataBase();
        userUID = dataBase.getCurrentUser().getUid();
        myRef = dataBase.getInstance().getReference(userUID).child(dataBase.PARENT_ITEMS());
        context = this.getActivity();
        list = new ArrayList<>();
        tableItemAdapter = new ItemAdapter(context, list);
        recyclerView.setAdapter(tableItemAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));



        tableItemAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDescriptionItemString = list.get(
                        recyclerView.getChildAdapterPosition(v)).getDescription();
                currentPriceItemDouble = list.get(
                        recyclerView.getChildAdapterPosition(v)).getPrice();
                item = new ItemData(currentDescriptionItemString, currentPriceItemDouble, 1);
                String userUID = dataBase.getCurrentUser().getUid();
                String currentTablePk = TablesFragment.currentNumTableString;
                String currentItemPK = currentDescriptionItemString
                        .replace(" ", "_");

                if(!currentItemPK.isEmpty()) {
                    Toast.makeText(getContext(), "El articulo ya existe", Toast.LENGTH_LONG)
                            .show();
                } else {
                    dataBase.getDatabaseReference()
                            .child(userUID)
                            .child(dataBase.PARENT_TABLES())
                            .child(currentTablePk)
                            .child("items_basket")
                            .child(currentItemPK)
                            .setValue(item);
                    TableBoxFragment.totalAmountDouble += currentPriceItemDouble;
                    dataBase.getDatabaseReference()
                            .child(userUID)
                            .child(dataBase.PARENT_TABLES())
                            .child(currentTablePk)
                            .child("items_basket")
                            .child("basket_amount")
                            .setValue(TableBoxFragment.totalAmountDouble);
                    Toast.makeText(getContext(), "Articulo añadido", Toast.LENGTH_LONG).show();
                }
                Navigation.findNavController(v).navigate(R.id.nav_table_box);
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ItemData itemData = dataSnapshot.getValue(ItemData.class);
                    list.add(itemData);
                }
                tableItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
