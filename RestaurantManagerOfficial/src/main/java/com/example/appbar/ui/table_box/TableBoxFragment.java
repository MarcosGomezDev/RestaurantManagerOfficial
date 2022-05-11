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
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
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
    private Button noButton, modifyButton, addItemTableButton;

    private final String currentTableTitle = "MESA " + TablesFragment.currentNumTableString;
    private String currentTable = TablesFragment.currentNumTableString;

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
        noButton = view.findViewById(R.id.noButton);
        modifyButton = view.findViewById(R.id.modifyButton);
        recyclerView = view.findViewById(R.id.itemsTablesRecycler);
        noButton.setText(currentTableTitle);

        dataBase = new DataBase();
        userUID = dataBase.getCurrentUser().getUid();
        myRef = dataBase.getInstance().getReference(userUID)
                .child(dataBase.PARENT_TABLES())
                .child(TablesFragment.currentNumTableString)
                .child("items_basket");
        context = this.getActivity();
        list = new ArrayList<>();
        tableBasketAdapter = new TableBasketAdapter(context, list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(
                        recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(tableBasketAdapter);

        tableBasketAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemsFragment.currentDescriptionItemString = list.get(
                        recyclerView.getChildAdapterPosition(v)).getDescription();
                ItemsFragment.currentPriceItemString = String.valueOf(list.get(
                        recyclerView.getChildAdapterPosition(v)).getPrice());
                ItemsFragment.currentUnitItemLong = list.get(
                        recyclerView.getChildAdapterPosition(v)).getUnits();
                Navigation.findNavController(v).navigate(R.id.nav_remove_items_basket);
            }
        });

        addItemTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comeFromTableBox = true;
                Navigation.findNavController(v).navigate(R.id.nav_table_items);
            }
        });

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_table_selected);
            }
        });

        readBasket();

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

//    public void removeItemBasket() {
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView,
//                                  @NonNull RecyclerView.ViewHolder viewHolder,
//                                  @NonNull RecyclerView.ViewHolder target) { return false; }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                //ItemData deletedCourse = list.get(viewHolder.getAbsoluteAdapterPosition());
//                //int position = viewHolder.getAbsoluteAdapterPosition();
//                //list.remove(position);
//
//                String PK = list.get(viewHolder.getBindingAdapterPosition()).getDescription()
//                        .replace(" ", "_");
//
//                dataBase.getDatabaseReference()
//                        .child(userUID)
//                        .child(dataBase.PARENT_TABLES())
//                        .child(currentTable)
//                        .child("items_basket")
//                        .child(PK)
//                        .removeValue();
//
//                tableBasketAdapter.notifyItemRemoved(viewHolder.getBindingAdapterPosition());
//
//            }
//        }).attachToRecyclerView(recyclerView);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
