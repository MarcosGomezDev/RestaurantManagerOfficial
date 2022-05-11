package com.example.appbar.ui.table_remove_item_basket;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.appbar.R;
import com.example.appbar.data.DataBase;
import com.example.appbar.data.ItemData;
import com.example.appbar.databinding.FragmentRemoveItemBasketBinding;
import com.example.appbar.ui.items.ItemsFragment;
import com.example.appbar.ui.tables.TablesFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

@SuppressWarnings("FieldCanBeLocal")
public class TableRemoveItemBasket extends Fragment implements View.OnClickListener {

    private FragmentRemoveItemBasketBinding binding;
    private DataBase dataBase;
    private DatabaseReference myRef;
    private ImageButton addItemBasketButton, subtractItemBasketButton;
    private Button removeItemBasketButton;
    private TextView descriptionItemBasketTextView;
    private String currentDescriptionItemString;
    private String currentPriceItemString;
    private String userUID;
    private String currentTablePk;
    private String currentItemPk;
    private String addDescription = "";
    private  int count;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRemoveItemBasketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentDescriptionItemString = ItemsFragment.currentDescriptionItemString;
        currentPriceItemString = ItemsFragment.currentPriceItemString;
        addItemBasketButton = view.findViewById(R.id.addItemBasketButton);
        subtractItemBasketButton = view.findViewById(R.id.subtractItemBasketButton);
        removeItemBasketButton = view.findViewById(R.id.removeItemBasketButton);
        descriptionItemBasketTextView = view.findViewById(R.id.descriptionItemBasketTextView);
        descriptionItemBasketTextView.setText(ItemsFragment.currentDescriptionItemString);

        removeItemBasketButton.setOnClickListener(this);
        subtractItemBasketButton.setOnClickListener(this);
        addItemBasketButton.setOnClickListener(this);

        userUID = dataBase.getCurrentUser().getUid();
        currentTablePk = TablesFragment.currentNumTableString;
        dataBase = new DataBase();
        myRef = dataBase.getInstance()
                .getReference(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(currentTablePk)
                .child("items_basket");




    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.removeItemBasketButton:
                currentItemPk = currentDescriptionItemString
                        .replace(" ", "_");
                dataBase.getDatabaseReference()
                        .child(userUID)
                        .child(dataBase.PARENT_TABLES())
                        .child(currentTablePk)
                        .child("items_basket")
                        .child(currentItemPk)
                        .removeValue();
                Toast.makeText(getContext(), "Articulo eliminado", Toast.LENGTH_LONG)
                        .show();
                Navigation.findNavController(v).navigate(R.id.nav_table_box);
                break;
            case R.id.addItemBasketButton:

                final long[] units = new long[1];
                myRef.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            ItemData itemData = dataSnapshot.getValue(ItemData.class);
                            assert itemData != null;
                            units[0] = itemData.getUnits();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });



                Toast.makeText(getContext(), (int) units[0], Toast.LENGTH_LONG)
                        .show();

//                if (Objects.equals(addDescription, currentDescriptionItemString)) {
//                    count++;
//                    addDescription = currentDescriptionItemString + " " + String.valueOf(count);
//                }
//
//                ItemData item = new ItemData(addDescription, currentPriceItemString);
//
//
//                addDescription = addDescription.replace(" ", "_");
//
//
//                dataBase.getDatabaseReference()
//                        .child(userUID)
//                        .child(dataBase.PARENT_TABLES())
//                        .child(currentTablePk)
//                        .child("items_basket")
//                        .child(currentItemPk)
//                        .setValue(item);
//                Toast.makeText(getContext(), "Articulo añadido", Toast.LENGTH_LONG)
//                        .show();
//                Navigation.findNavController(v).navigate(R.id.nav_table_box);

                break;



            case R.id.subtractItemBasketButton:


                break;

        }
    }

    public void getUnits() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
