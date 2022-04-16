package com.example.appbar.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appbar.Data.DataBase;
import com.example.appbar.Data.ItemsData;
import com.example.appbar.R;
import com.example.appbar.databinding.FragmentHomeBinding;
import com.google.firebase.database.DatabaseReference;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DataBase dataBase = new DataBase();
    private DatabaseReference myRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Este text va fuera
        Button logOutButton = view.findViewById(R.id.logOutButton);
        ItemsData item = new ItemsData();

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //item.listItems();



            }
        });


    }

}