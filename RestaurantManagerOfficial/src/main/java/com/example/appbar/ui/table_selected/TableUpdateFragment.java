package com.example.appbar.ui.table_selected;

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
import com.example.appbar.data.TablesData;
import com.example.appbar.databinding.FragmentTableUpdateBinding;
import com.example.appbar.ui.tables.TablesFragment;

@SuppressWarnings("FieldCanBeLocal")
public class TableUpdateFragment extends Fragment {

    private FragmentTableUpdateBinding binding;
    private DataBase dataBase;
    private TablesData table;
    private String currentNumTableString;
    private String currentCapacityTableString;
    private Button updateOkButton;
    private EditText updateNumTableEditText, updateCapacityEditText;
    private TextView getNumTableTextView, getCapacityTextView;
    private boolean reservedBool;

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
        currentNumTableString = TablesFragment.currentNumTableString;
        currentCapacityTableString = TablesFragment.currentCapacityTableString;
        reservedBool = TablesFragment.currentReservedTableBool;
        updateOkButton = view.findViewById(R.id.updateOkButton);
        updateNumTableEditText  = view.findViewById(R.id.updateNumTableEditText);
        updateCapacityEditText = view.findViewById(R.id.updateCapacityEditText);
        getNumTableTextView = view.findViewById(R.id.getNumTableTextView);
        getCapacityTextView = view.findViewById(R.id.getCapacityTextView);
        getNumTableTextView.setText("Mesa número " + currentNumTableString);
        getCapacityTextView.setText("Capacidad de la mesa: " + currentCapacityTableString + " personas");

        updateOkButton.setOnClickListener(v -> {

            String numTable = updateNumTableEditText.getText().toString();
            String capacity = updateCapacityEditText.getText().toString();

            if (numTable.isEmpty() || capacity.isEmpty()) {
                updateNumTableEditText.setError("Campo obligatorio");
                updateCapacityEditText.setError("Campo obligatorio");
            } else {
                table = new TablesData(numTable, capacity, reservedBool);
                String userUID = dataBase.getCurrentUser().getUid();
                String currentPk = TablesFragment.currentNumTableString;
                dataBase.getDatabaseReference()
                        .child(userUID)
                        .child(dataBase.PARENT_TABLES())
                        .child(currentPk).removeValue();
                dataBase.getDatabaseReference()
                        .child(userUID)
                        .child(dataBase.PARENT_TABLES())
                        .child(numTable)
                        .setValue(table);
                Toast.makeText(getContext(), "Mesa modificada", Toast.LENGTH_LONG)
                        .show();
                Navigation.findNavController(v).navigate(R.id.nav_tables);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}