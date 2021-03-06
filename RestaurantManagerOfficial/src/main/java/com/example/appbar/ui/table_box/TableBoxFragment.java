package com.example.appbar.ui.table_box;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbar.R;
import com.example.appbar.data.DataBase;
import com.example.appbar.data.DataFlow;
import com.example.appbar.data.ItemData;
import com.example.appbar.databinding.FragmentTableBoxBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Clase para el manejo de datos dentro de una mesa.
 */
@SuppressWarnings({"FieldCanBeLocal", "ConstantConditions"})
public class TableBoxFragment extends Fragment implements View.OnClickListener {

    private FragmentTableBoxBinding binding;
    private DataBase dataBase;
    private TableBasketAdapter tableBasketAdapter;
    private RecyclerView recyclerView;
    private ArrayList<ItemData> list;
    private String userUID;
    private Context context;
    private Button noButton, modifyButton, addItemTableButton, collectButton, reservedButton;
    private TextView totalAmountTextView;
    private final String currentTableTitle = "MESA " + DataFlow.currentNumTableString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(TableBoxFragment.this)
                        .navigate(R.id.nav_tables);
            }
        };
        requireActivity().getOnBackPressedDispatcher()
                .addCallback(getViewLifecycleOwner(), callback);
        binding = FragmentTableBoxBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DataFlow.comeFromTableBox = false;
        addItemTableButton = view.findViewById(R.id.addItemTableButton);
        noButton = view.findViewById(R.id.noButton);
        modifyButton = view.findViewById(R.id.modifyButton);
        collectButton = view.findViewById(R.id.collectButton);
        reservedButton = view.findViewById(R.id.reservedButton);
        recyclerView = view.findViewById(R.id.itemsTablesRecycler);
        totalAmountTextView = view.findViewById(R.id.totalAmountTextView);
        modifyButton.setOnClickListener(this);
        addItemTableButton.setOnClickListener(this);
        collectButton.setOnClickListener(this);
        reservedButton.setOnClickListener(this);
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

        /**
         * Bot??n que captura los datos del articulo seleccionado y nos dirige a la vista que nos
         * permitir?? a??adir mas unidades de un articulo, quitar unidades o eliminar el articulo.
         */
        tableBasketAdapter.setOnClickListener(v -> {
            DataFlow.currentDescriptionItemString = list.get(
                    recyclerView.getChildAdapterPosition(v)).getDescription();
            DataFlow.currentPriceItemDouble = list.get(
                    recyclerView.getChildAdapterPosition(v)).getPrice();
            DataFlow.currentUnitItemLong = list.get(
                    recyclerView.getChildAdapterPosition(v)).getUnits();
            DataFlow.totalItemAmountPrice = list.get(
                    recyclerView.getChildAdapterPosition(v)).getAmountPrice();
            Navigation.findNavController(v).navigate(R.id.nav_remove_items_basket);
        });

        DatabaseReference ref_items_basket = dataBase.getInstance().getReference(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(DataFlow.currentNumTableString)
                .child("items_basket");

        /**
         * Listener que controla los cambios en la base de datos y los envia al adapter para
         * ajustar los datos mostrados por pantalla.
         */
        ref_items_basket.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    try {
                        ItemData itemData = dataSnapshot.getValue(ItemData.class);
                        list.add(itemData);
                        Log.println(Log.WARN, "***********A??adido item***********",
                                "TableBoxFragment.java, line: 114");
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
                .child(DataFlow.currentNumTableString)
                .child("items_basket")
                .child("basket_amount");

        /**
         * Listener que controla los cambios en la base de datos y actualiza los componentes de la
         * vista.
         */
        ref_basket_amount.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && !list.isEmpty()) {
                    String result = snapshot.getValue().toString().trim();
                    totalAmountTextView.setText(result + " ???");
                } else {
                    totalAmountTextView.setText("0.0 ???");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference ref_reserved = dataBase.getInstance().getReference(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(DataFlow.currentNumTableString)
                .child("reserved");

        /**
         * Listener que controla los cambios en la base de datos y actualiza los componentes de la
         * vista.
         */
        ref_reserved.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    boolean res = (boolean) snapshot.getValue();
                    if (!res) {
                        reservedButton.setBackgroundColor(0xFF54C242);
                        reservedButton.setText("DISP.");
                    } else {
                        reservedButton.setBackgroundColor(Color.RED);
                        reservedButton.setText("RESERV.");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * Bot??n que envia al usuario a la vista correspondiente para a??adir un articulo a
             * la cesta.
             */
            case R.id.addItemTableButton:
                DataFlow.comeFromTableBox = true;
                Navigation.findNavController(v).navigate(R.id.nav_table_items);
                break;
            /**
             * Bot??n que envia al usuario a la vista correspondiente para la modificaci??n de la
             * mesa seleccionada.
             */
            case R.id.modifyButton:
                Navigation.findNavController(v).navigate(R.id.nav_table_selected);
                break;
            /**
             * Bot??n que resetea todos los datos de la mesa.
             */
            case R.id.collectButton:
                dataBase.getDatabaseReference()
                        .child(userUID)
                        .child(dataBase.PARENT_TABLES())
                        .child(DataFlow.currentNumTableString)
                        .child("reserved").setValue(false);
                dataBase.getDatabaseReference()
                        .child(userUID)
                        .child(dataBase.PARENT_TABLES())
                        .child(DataFlow.currentNumTableString)
                        .child("items_basket").removeValue();
                list.clear();
                DataFlow.amountItemBasketDouble = 0.0;
                Toast.makeText(getContext(), "Mesa cobrada", Toast.LENGTH_SHORT)
                        .show();
                break;
            /**
             * Bot??n que permite reservas una mesa.
             */
            case R.id.reservedButton:
                if (reservedButton.getText() == "DISP.") {
                    dataBase.getDatabaseReference()
                            .child(userUID)
                            .child(dataBase.PARENT_TABLES())
                            .child(DataFlow.currentNumTableString)
                            .child("reserved").setValue(true);
                    reservedButton.setBackgroundColor(Color.RED);
                    reservedButton.setText("RESERV.");
                } else {
                    dataBase.getDatabaseReference()
                            .child(userUID)
                            .child(dataBase.PARENT_TABLES())
                            .child(DataFlow.currentNumTableString)
                            .child("reserved").setValue(false);
                    reservedButton.setBackgroundColor(0xFF54C242);
                    reservedButton.setText("DISP.");
                }
                break;
        }
    }
}
