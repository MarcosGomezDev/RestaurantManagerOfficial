package com.example.appbar.ui.cash_history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appbar.databinding.FragmentCashHistoryBinding;

public class CashHistoryFragment extends Fragment {

    private FragmentCashHistoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CashHistoryViewModel cashHistoryViewModel =
                new ViewModelProvider(this).get(CashHistoryViewModel.class);

        binding = FragmentCashHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
