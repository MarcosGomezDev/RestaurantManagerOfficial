package com.example.appbar.ui.items;

import android.content.Context;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbar.R;
import com.example.appbar.data.DataBase;
import com.example.appbar.data.ItemData;
import com.example.appbar.databinding.FragmentItemsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemsFragment extends Fragment {

    private FragmentItemsBinding binding;
    private DataBase dataBase;
    private DatabaseReference myRef;
    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;
    private ArrayList<ItemData> list;
    private String userUID;
    private Context context;
    private FloatingActionButton addItemButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ItemsViewModel cashHistoryViewModel =
                new ViewModelProvider(this).get(ItemsViewModel.class);

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


        addItemButton = view.findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_table_selected);
            }
        });


        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        list = new ArrayList<>();
        itemAdapter = new ItemAdapter(context, list);
        recyclerView.setAdapter(itemAdapter);



        myRef.addValueEventListener(new ValueEventListener() {
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

    public void listItems(){

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userUID = currentUser.getUid();
        dataBase.getDatabaseReference().child(userUID).child(dataBase.PARENT_ITEMS()).getDatabase();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
