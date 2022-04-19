package com.example.appbar.ui.tables;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.appbar.R;
import com.example.appbar.databinding.FragmentTablesBinding;
import com.example.appbar.ui.table_selected.TableSelectedFragment;

public class TablesFragment extends Fragment implements View.OnClickListener{

    private FragmentTablesBinding binding;
    private Button table1Button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TablesViewModel galleryViewModel =
                new ViewModelProvider(this).get(TablesViewModel.class);

        binding = FragmentTablesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        table1Button = view.findViewById(R.id.table1Button);
        table1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_table_selected);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.table1Button:
                Navigation.findNavController(v).navigate(R.id.nav_table_selected);
            break;
        }
    }
}