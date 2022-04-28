package com.example.appbar.ui.items;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbar.R;
import com.example.appbar.data.DataBase;
import com.example.appbar.data.ItemData;
import com.example.appbar.databinding.FragmentItemsBinding;
import com.example.appbar.ui.item_add_update.ItemAddUpdateFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemsFragment extends Fragment {

    private FragmentItemsBinding binding;
    private DataBase dataBase;
    private DatabaseReference myRef;
    public static String currentItemString;
    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;
    private ArrayList<ItemData> list;
    private String userUID;
    private Context context;
    private FloatingActionButton addItemButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.itemListRecyclerView);
        dataBase = new DataBase();

        userUID = dataBase.getCurrentUser().getUid();
        myRef = dataBase.getInstance().getReference(userUID).child(dataBase.PARENT_ITEMS());
        context = this.getActivity();
        list = new ArrayList<>();

        addItemButton = view.findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_table_selected);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        itemAdapter = new ItemAdapter(context, list);
        recyclerView.setAdapter(itemAdapter);

        itemAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                ItemAddUpdateFragment.currentPkItemString =
//                        list.get(recyclerView.getChildAdapterPosition(v)).getPK();
//                Toast.makeText(context, "Seleccionado " +
//                                list.get(recyclerView.getChildAdapterPosition(v)).getPK(),
//                        Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("currentItemID", list.get(recyclerView.getChildAdapterPosition(v))
                        .getPK());
                Fragment fragment = new ItemAddUpdateFragment();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_item_add_update, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                Navigation.findNavController(v).navigate(R.id.nav_item_add_update);


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
                itemAdapter.notifyDataSetChanged();
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
