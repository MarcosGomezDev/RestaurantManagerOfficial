package com.example.appbar.ui.staff_signin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appbar.databinding.FragmentStaffSigninBinding;

public class StaffSignInFragment extends Fragment {

    private FragmentStaffSigninBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StaffSignInViewModel staffSignInViewModel =
                new ViewModelProvider(this).get(StaffSignInViewModel.class);

        binding = FragmentStaffSigninBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textFichar;
        staffSignInViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}