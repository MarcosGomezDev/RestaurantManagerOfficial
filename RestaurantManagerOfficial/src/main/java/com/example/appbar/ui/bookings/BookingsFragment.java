package com.example.appbar.ui.bookings;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbar.R;
import com.example.appbar.data.BookingsData;
import com.example.appbar.data.DataBase;
import com.example.appbar.data.ItemData;
import com.example.appbar.databinding.FragmentBookingsBinding;
import com.example.appbar.databinding.FragmentItemsBinding;
import com.example.appbar.ui.items.ItemAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookingsFragment extends Fragment {

    private FragmentItemsBinding binding;
    private DataBase dataBase;
    private DatabaseReference myRef;
    public static String currentPkItemString;
    public static String currentDescriptionItemString;
    public static String currentPriceItemString;
    private BookingsAdapter bookingsAdapter;
    private RecyclerView recyclerView;
    private ArrayList<BookingsData> list;
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

        addItemButton = view.findViewById(R.id.addItemButton);
        recyclerView = view.findViewById(R.id.itemListRecyclerView);
        dataBase = new DataBase();
        currentPkItemString = " ";

        userUID = dataBase.getCurrentUser().getUid();
        myRef = dataBase.getInstance().getReference(userUID).child(dataBase.PARENT_ITEMS());
        context = this.getActivity();
        list = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        bookingsAdapter = new BookingsAdapter(context, list);
        recyclerView.setAdapter(bookingsAdapter);


        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BookingsData bookingsData = dataSnapshot.getValue(BookingsData.class);
                    list.add(bookingsData);
                }
                bookingsAdapter.notifyDataSetChanged();
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
