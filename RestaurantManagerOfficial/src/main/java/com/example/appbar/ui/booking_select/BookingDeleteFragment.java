package com.example.appbar.ui.booking_select;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.appbar.R;
import com.example.appbar.data.BookingsData;
import com.example.appbar.databinding.FragmentBookingDeleteBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookingDeleteFragment {
    private FragmentBookingDeleteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookingDeleteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }


}
