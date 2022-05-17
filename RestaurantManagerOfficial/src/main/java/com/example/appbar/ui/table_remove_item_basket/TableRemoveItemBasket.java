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
import androidx.navigation.fragment.NavHostFragment;

import com.example.appbar.R;
import com.example.appbar.data.DataBase;
import com.example.appbar.databinding.FragmentRemoveItemBasketBinding;
import com.example.appbar.ui.items.ItemsFragment;
import com.example.appbar.ui.table_box.TableBoxFragment;
import com.example.appbar.ui.tables.TablesFragment;

@SuppressWarnings("FieldCanBeLocal")
public class TableRemoveItemBasket extends Fragment implements View.OnClickListener {

    private FragmentRemoveItemBasketBinding binding;
    private DataBase dataBase;
    private ImageButton addItemBasketButton, subtractItemBasketButton;
    private Button removeItemBasketButton;
    private TextView descriptionItemBasketTextView, unitsTextView, totalAmountTextView;
    private String currentDescriptionItemString;
    private String userUID;
    private String currentTablePk;
    private String currentItemPk;
    private long currentUnits;
    private long newUnitSub;
    private double currentPriceItemDouble;
    private double totalItemAmount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRemoveItemBasketBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataBase = new DataBase();
        userUID = dataBase.getCurrentUser().getUid();

        currentDescriptionItemString = ItemsFragment.currentDescriptionItemString;
        currentPriceItemDouble = ItemsFragment.currentPriceItemDouble;
        totalItemAmount = TableBoxFragment.totalItemAmount;
        currentUnits = ItemsFragment.currentUnitItemLong;
        currentTablePk = TablesFragment.currentNumTableString;
        currentItemPk = currentDescriptionItemString
                .replace(" ", "_");

        addItemBasketButton = view.findViewById(R.id.addItemBasketButton);
        subtractItemBasketButton = view.findViewById(R.id.subtractItemBasketButton);
        removeItemBasketButton = view.findViewById(R.id.removeItemBasketButton);
        descriptionItemBasketTextView = view.findViewById(R.id.descriptionItemBasketTextView);
        descriptionItemBasketTextView.setText(ItemsFragment.currentDescriptionItemString);
        totalAmountTextView = view.findViewById(R.id.totalAmountTextView);
        unitsTextView = view.findViewById(R.id.unitsTextView);
        unitsTextView.setText(String.valueOf(currentUnits));
        removeItemBasketButton.setOnClickListener(this);
        subtractItemBasketButton.setOnClickListener(this);
        addItemBasketButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.removeItemBasketButton:
                removeItemBasket();
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

    public void addItemBasket() {
        newUnitSub = ItemsFragment.currentUnitItemLong + 1;
        dataBase.getDatabaseReference()
                .child(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(currentTablePk)
                .child("items_basket")
                .child(currentItemPk)
                .child("units")
                .setValue(newUnitSub);
        ItemsFragment.currentUnitItemLong = newUnitSub;
        unitsTextView.setText(String.valueOf(newUnitSub));

        totalItemAmount = totalItemAmount + currentPriceItemDouble;
        totalItemAmount = Math.round(totalItemAmount * 100d) / 100d;
        dataBase.getDatabaseReference()
                .child(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(currentTablePk)
                .child("items_basket")
                .child(currentItemPk)
                .child("amountPrice")
                .setValue(totalItemAmount);
        TableBoxFragment.totalAmountDouble += currentPriceItemDouble;
        TableBoxFragment.totalAmountDouble = Math
                .round(TableBoxFragment.totalAmountDouble * 100d) / 100d;

        dataBase.getDatabaseReference()
                .child(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(currentTablePk)
                .child("items_basket")
                .child("basket_amount")
                .setValue(TableBoxFragment.totalAmountDouble);
        Toast.makeText(getContext(), "Articulo añadido", Toast.LENGTH_SHORT)
                .show();
    }

    public void subtractItemBasket() {
        long oldUnitSub = ItemsFragment.currentUnitItemLong;
        if (oldUnitSub >= 1) {
            newUnitSub = oldUnitSub - 1;
            dataBase.getDatabaseReference()
                    .child(userUID)
                    .child(dataBase.PARENT_TABLES())
                    .child(currentTablePk)
                    .child("items_basket")
                    .child(currentItemPk)
                    .child("units")
                    .setValue(newUnitSub);

            totalItemAmount = totalItemAmount - currentPriceItemDouble;
            totalItemAmount = Math.round(totalItemAmount * 100d) / 100d;
            dataBase.getDatabaseReference()
                    .child(userUID)
                    .child(dataBase.PARENT_TABLES())
                    .child(currentTablePk)
                    .child("items_basket")
                    .child(currentItemPk)
                    .child("amountPrice")
                    .setValue(totalItemAmount);

            TableBoxFragment.totalAmountDouble -= currentPriceItemDouble;
            TableBoxFragment.totalAmountDouble = Math
                    .round(TableBoxFragment.totalAmountDouble * 100d) / 100d;

            dataBase.getDatabaseReference()
                    .child(userUID)
                    .child(dataBase.PARENT_TABLES())
                    .child(currentTablePk)
                    .child("items_basket")
                    .child("basket_amount")
                    .setValue(TableBoxFragment.totalAmountDouble);

            Toast.makeText(getContext(), "Articulo eliminado", Toast.LENGTH_SHORT)
                    .show();
            ItemsFragment.currentUnitItemLong = newUnitSub;
            //unitsTextView.setText(String.valueOf(newUnitSub));

           // totalAmountTextView.setText(String.valueOf(TableBoxFragment.totalAmountDouble));
        }
        if (newUnitSub == 0) {


            removeItemBasket();

        }
    }

    public void removeItemBasket() {
        TableBoxFragment.totalAmountDouble -= currentPriceItemDouble;
        TableBoxFragment.totalAmountDouble = Math
                .round(TableBoxFragment.totalAmountDouble * 100d) / 100d;

        dataBase.getDatabaseReference()
                .child(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(currentTablePk)
                .child("items_basket")
                .child(currentItemPk)
                .removeValue();
        Toast.makeText(getContext(), "Articulo eliminado", Toast.LENGTH_SHORT)
                .show();
        NavHostFragment.findNavController(this).navigate(R.id.nav_table_box);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
