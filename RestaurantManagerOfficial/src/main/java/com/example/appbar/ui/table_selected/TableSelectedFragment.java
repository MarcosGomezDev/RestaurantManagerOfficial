package com.example.appbar.ui.table_selected;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.appbar.R;
import com.example.appbar.databinding.FragmentTableSelectedBinding;
import com.example.appbar.ui.items.ItemsFragment;
import com.example.appbar.ui.tables.TablesFragment;

public class TableSelectedFragment extends Fragment implements View.OnClickListener{

    private FragmentTableSelectedBinding binding;
    private Button cashButton;

    public View onCreateView (@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TableSelectedViewModel galleryViewModel =
                new ViewModelProvider(this).get(TableSelectedViewModel.class);

        binding = FragmentTableSelectedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (TablesFragment.navItem) {
            ((AppCompatActivity)getActivity()).getSupportActionBar()
                    .setTitle("Productos");
        }

        cashButton = view.findViewById(R.id.cashButton);
        cashButton.setOnClickListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cashButton:
                //Navigation.findNavController(v).navigate(R.id.nav_items);
                break;
        }
    }
}