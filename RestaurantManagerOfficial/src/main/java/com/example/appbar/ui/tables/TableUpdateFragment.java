package com.example.appbar.ui.tables;

import android.annotation.SuppressLint;
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
import androidx.navigation.Navigation;

import com.example.appbar.R;
import com.example.appbar.data.DataBase;
import com.example.appbar.data.DataFlow;
import com.example.appbar.data.TablesData;
import com.example.appbar.databinding.FragmentTableUpdateBinding;

@SuppressWarnings("FieldCanBeLocal")
public class TableUpdateFragment extends Fragment {

    private FragmentTableUpdateBinding binding;
    private DataBase dataBase;
    private TablesData table;
    private Button updateOkButton;
    private EditText updateNumTableEditText, updateCapacityEditText;
    private TextView getNumTableTextView, getCapacityTextView;

    public View onCreateView (@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTableUpdateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataBase = new DataBase();
        updateOkButton = view.findViewById(R.id.updateOkButton);
        updateNumTableEditText  = view.findViewById(R.id.updateNumTableEditText);
        updateCapacityEditText = view.findViewById(R.id.updateCapacityEditText);
        getNumTableTextView = view.findViewById(R.id.getNumTableTextView);
        getCapacityTextView = view.findViewById(R.id.getCapacityTextView);
        getNumTableTextView.setText("Mesa número " + DataFlow.currentNumTableString);
        getCapacityTextView.setText("Capacidad de la mesa: " +
                DataFlow.currentCapacityTableString + " personas");

        updateOkButton.setOnClickListener(v -> {

            String numTable = updateNumTableEditText.getText().toString();
            String capacity = updateCapacityEditText.getText().toString();

            if (numTable.isEmpty() || capacity.isEmpty()) {
                updateNumTableEditText.setError("Campo obligatorio");
                updateCapacityEditText.setError("Campo obligatorio");
            } else {
                if (numTable.length() > 2 || capacity.length() > 2) {
                    updateNumTableEditText.setError("2 caracteres como máximo");
                    updateCapacityEditText.setError("2 caracteres como máximo");
                } else {
                    table = new TablesData(numTable, capacity, DataFlow.currentReservedTableBool);
                    String userUID = dataBase.getCurrentUser().getUid();
                    dataBase.getDatabaseReference()
                            .child(userUID)
                            .child(dataBase.PARENT_TABLES())
                            .child(DataFlow.currentNumTableString)
                            .removeValue();
                    dataBase.getDatabaseReference()
                            .child(userUID)
                            .child(dataBase.PARENT_TABLES())
                            .child(numTable)
                            .setValue(table);
                    Toast.makeText(getContext(), "Mesa modificada", Toast.LENGTH_LONG)
                            .show();
                    Navigation.findNavController(v).navigate(R.id.nav_tables);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}