package com.example.appbar.ui.tables;

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
import com.example.appbar.data.DataFlow;
import com.example.appbar.databinding.FragmentRemoveItemBasketBinding;

@SuppressWarnings("FieldCanBeLocal")
public class TableRemoveItemBasketFragment extends Fragment implements View.OnClickListener {

    private FragmentRemoveItemBasketBinding binding;
    private DataBase dataBase;
    private ImageButton addItemBasketButton, subtractItemBasketButton;
    private Button removeItemBasketButton;
    private TextView descriptionItemBasketTextView, unitsTextView;
    private String userUID;
    private String currentItemPk;
    private long newUnitSub;

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
        currentItemPk = DataFlow.currentDescriptionItemString
                .replace(" ", "_");

        addItemBasketButton = view.findViewById(R.id.addItemBasketButton);
        subtractItemBasketButton = view.findViewById(R.id.subtractItemBasketButton);
        removeItemBasketButton = view.findViewById(R.id.removeItemBasketButton);
        descriptionItemBasketTextView = view.findViewById(R.id.descriptionItemBasketTextView);
        descriptionItemBasketTextView.setText(DataFlow.currentDescriptionItemString);
        unitsTextView = view.findViewById(R.id.unitsTextView);
        unitsTextView.setText(String.valueOf(DataFlow.currentUnitItemLong));

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
        newUnitSub = DataFlow.currentUnitItemLong + 1;
        dataBase.getDatabaseReference()
                .child(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(DataFlow.currentNumTableString)
                .child("items_basket")
                .child(currentItemPk)
                .child("units")
                .setValue(newUnitSub);
        DataFlow.currentUnitItemLong = newUnitSub;
        unitsTextView.setText(String.valueOf(newUnitSub));

        DataFlow.totalItemAmountPrice = DataFlow.totalItemAmountPrice + DataFlow.currentPriceItemDouble;
        DataFlow.totalItemAmountPrice = Math.round(DataFlow.totalItemAmountPrice * 100d) / 100d;
        if (DataFlow.totalItemAmountPrice < 0) DataFlow.totalItemAmountPrice = 0.0;
        dataBase.getDatabaseReference()
                .child(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(DataFlow.currentNumTableString)
                .child("items_basket")
                .child(currentItemPk)
                .child("amountPrice")
                .setValue(DataFlow.totalItemAmountPrice);

        DataFlow.amountItemBasketDouble += DataFlow.currentPriceItemDouble;
        DataFlow.amountItemBasketDouble = Math
                .round(DataFlow.amountItemBasketDouble * 100d) / 100d;
        if (DataFlow.amountItemBasketDouble < 0) DataFlow.amountItemBasketDouble = 0.0;
        dataBase.getDatabaseReference()
                .child(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(DataFlow.currentNumTableString)
                .child("items_basket")
                .child("basket_amount")
                .setValue(DataFlow.amountItemBasketDouble);
        Toast.makeText(getContext(), "Articulo añadido", Toast.LENGTH_SHORT)
                .show();
    }

    public void subtractItemBasket() {
        long oldUnitSub = DataFlow.currentUnitItemLong;
        if (oldUnitSub > 1) {
            newUnitSub = oldUnitSub - 1;
            dataBase.getDatabaseReference()
                    .child(userUID)
                    .child(dataBase.PARENT_TABLES())
                    .child(DataFlow.currentNumTableString)
                    .child("items_basket")
                    .child(currentItemPk)
                    .child("units")
                    .setValue(newUnitSub);

            DataFlow.totalItemAmountPrice = DataFlow.totalItemAmountPrice - DataFlow.currentPriceItemDouble;
            DataFlow.totalItemAmountPrice = Math.round(DataFlow.totalItemAmountPrice * 100d) / 100d;
            if (DataFlow.totalItemAmountPrice < 0) DataFlow.totalItemAmountPrice = 0.0;
            dataBase.getDatabaseReference()
                    .child(userUID)
                    .child(dataBase.PARENT_TABLES())
                    .child(DataFlow.currentNumTableString)
                    .child("items_basket")
                    .child(currentItemPk)
                    .child("amountPrice")
                    .setValue(DataFlow.totalItemAmountPrice);

            DataFlow.amountItemBasketDouble = DataFlow.amountItemBasketDouble - DataFlow.currentPriceItemDouble;
            DataFlow.amountItemBasketDouble = Math
                    .round(DataFlow.amountItemBasketDouble * 100d) / 100d;
            if (DataFlow.amountItemBasketDouble < 0) DataFlow.amountItemBasketDouble = 0.0;
            dataBase.getDatabaseReference()
                    .child(userUID)
                    .child(dataBase.PARENT_TABLES())
                    .child(DataFlow.currentNumTableString)
                    .child("items_basket")
                    .child("basket_amount")
                    .setValue(DataFlow.amountItemBasketDouble);
            Toast.makeText(getContext(), "Articulo eliminado", Toast.LENGTH_SHORT)
                    .show();
            DataFlow.currentUnitItemLong = newUnitSub;
        } else {
            removeItemBasket();
        }
    }

    public void removeItemBasket() {
        DataFlow.amountItemBasketDouble -= DataFlow.currentPriceItemDouble;
        DataFlow.amountItemBasketDouble = Math
                .round(DataFlow.amountItemBasketDouble * 100d) / 100d;
        if (DataFlow.amountItemBasketDouble < 0) DataFlow.amountItemBasketDouble = 0.0;
        dataBase.getDatabaseReference()
                .child(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(DataFlow.currentNumTableString)
                .child("items_basket")
                .child("basket_amount")
                .setValue(DataFlow.amountItemBasketDouble);

        dataBase.getDatabaseReference()
                .child(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(DataFlow.currentNumTableString)
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
