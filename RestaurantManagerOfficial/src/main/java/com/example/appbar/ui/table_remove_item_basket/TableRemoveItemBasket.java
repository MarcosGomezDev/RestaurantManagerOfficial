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
    private TextView descriptionItemBasketTextView, unitsTextView;
    private String currentDescriptionItemString;
    private String userUID;
    private String currentTablePk;
    private String currentItemPk;
    private long currentUnits;
    private long newUnitSub;

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
        addItemBasketButton = view.findViewById(R.id.addItemBasketButton);
        subtractItemBasketButton = view.findViewById(R.id.subtractItemBasketButton);
        removeItemBasketButton = view.findViewById(R.id.removeItemBasketButton);
        descriptionItemBasketTextView = view.findViewById(R.id.descriptionItemBasketTextView);
        descriptionItemBasketTextView.setText(ItemsFragment.currentDescriptionItemString);
        unitsTextView = view.findViewById(R.id.unitsTextView);

        currentUnits = ItemsFragment.currentUnitItemLong;
        unitsTextView.setText(String.valueOf(currentUnits));


        removeItemBasketButton.setOnClickListener(this);
        subtractItemBasketButton.setOnClickListener(this);
        addItemBasketButton.setOnClickListener(this);

        dataBase = new DataBase();
        userUID = dataBase.getCurrentUser().getUid();

        currentTablePk = TablesFragment.currentNumTableString;
        currentItemPk = currentDescriptionItemString
                .replace(" ", "_");
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
                removeItemBasket();
                Navigation.findNavController(v).navigate(R.id.nav_table_box);
                break;
            case R.id.addItemBasketButton:
                addItemBasket();
                unitsTextView.setText(String.valueOf(newUnitSub));
                break;
            case R.id.subtractItemBasketButton:
                subtractItemBasket();
                unitsTextView.setText(String.valueOf(newUnitSub));
                break;
        }
    }

    public void subtractItemBasket() {
        long oldUnitSub = ItemsFragment.currentUnitItemLong;
        if (oldUnitSub > 1) {
            newUnitSub = oldUnitSub - 1;
            dataBase.getDatabaseReference()
                    .child(userUID)
                    .child(dataBase.PARENT_TABLES())
                    .child(currentTablePk)
                    .child("items_basket")
                    .child(currentItemPk)
                    .child("units")
                    .setValue(newUnitSub);
            Toast.makeText(getContext(), "Articulo añadido", Toast.LENGTH_SHORT)
                    .show();
            ItemsFragment.currentUnitItemLong = newUnitSub;
            unitsTextView.setText(String.valueOf(newUnitSub));
        } else {
            removeItemBasket();
        }
    }

    public void addItemBasket() {
        long oldUnitAdd = ItemsFragment.currentUnitItemLong;
        newUnitSub = oldUnitAdd + 1;
        dataBase.getDatabaseReference()
                .child(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(currentTablePk)
                .child("items_basket")
                .child(currentItemPk)
                .child("units")
                .setValue(newUnitSub);
        Toast.makeText(getContext(), "Articulo añadido", Toast.LENGTH_SHORT)
                .show();
        ItemsFragment.currentUnitItemLong = newUnitSub;
        unitsTextView.setText(String.valueOf(newUnitSub));
    }

    public void removeItemBasket() {
        dataBase.getDatabaseReference()
                .child(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(currentTablePk)
                .child("items_basket")
                .child(currentItemPk)
                .removeValue();
        Toast.makeText(getContext(), "Articulo eliminado", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
