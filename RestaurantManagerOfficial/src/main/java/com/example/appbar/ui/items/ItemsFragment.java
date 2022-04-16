package com.example.appbar.ui.items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appbar.Data.DataBase;
import com.example.appbar.databinding.FragmentItemsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ItemsFragment extends Fragment {

    private FragmentItemsBinding binding;
    private DataBase dataBase = new DataBase();
    private DatabaseReference myRef = dataBase.getInstance().getReference();

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






    }

    public void listItems(){

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userUID = currentUser.getUid();
        myRef.child(userUID).child(dataBase.PARENT_ITEMS()).getDatabase();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
