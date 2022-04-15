package com.example.appbar.ui.booking_select;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appbar.databinding.FragmentBookingSelectBinding;


public class BookingSelectFragment extends Fragment {
    private FragmentBookingSelectBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BookingSelectViewModel ficharViewModel =
                new ViewModelProvider(this).get(BookingSelectViewModel.class);

        binding = FragmentBookingSelectBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
