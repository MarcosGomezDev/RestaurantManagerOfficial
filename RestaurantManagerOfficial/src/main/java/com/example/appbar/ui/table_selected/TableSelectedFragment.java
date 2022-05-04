package com.example.appbar.ui.table_selected;

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
import com.example.appbar.databinding.FragmentTableSelectedBinding;
import com.example.appbar.ui.tables.TablesFragment;

@SuppressWarnings("FieldCanBeLocal")
public class TableSelectedFragment extends Fragment {

    private FragmentTableSelectedBinding binding;
    private final DataBase dataBase = new DataBase();
    private TablesData table = new TablesData();
    private String currentNumTableString;
    private String currentCapacityTableString;
    private Button updateOkButton, removeButton;
    private EditText updateNumTableEditText, updateCapacityEditText;
    private TextView getNumTableTextView, getCapacityTextView;
    private boolean reservedBool;

    public View onCreateView (@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTableSelectedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentNumTableString = TablesFragment.currentNumTableString;
        currentCapacityTableString = TablesFragment.currentCapacityTableString;
        reservedBool = TablesFragment.currentReservedTableBool;
        updateOkButton = view.findViewById(R.id.updateOkButton);
        removeButton = view.findViewById(R.id.removeButton);
        updateNumTableEditText  = view.findViewById(R.id.updateNumTableEditText);
        updateCapacityEditText = view.findViewById(R.id.updateCapacityEditText);
        getNumTableTextView = view.findViewById(R.id.getNumTableTextView);
        getCapacityTextView = view.findViewById(R.id.getCapacityTextView);
        getNumTableTextView.setText(currentNumTableString);
        getCapacityTextView.setText(currentCapacityTableString);

        updateOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numTable = updateNumTableEditText.getText().toString();
                String capacity = updateCapacityEditText.getText().toString();
                if (numTable.isEmpty() || capacity.isEmpty()) {
                    updateNumTableEditText.setError("Campo obligatorio");
                    updateCapacityEditText.setError("Campo obligatorio");
                } else {
                    table = new TablesData(numTable, capacity, reservedBool);
                    String userUID = dataBase.getCurrentUser().getUid();
                    String currentPk = numTable;
                    dataBase.getDatabaseReference()
                            .child(userUID)
                            .child(dataBase.PARENT_TABLES())
                            .child(currentPk).removeValue();
                    dataBase.getDatabaseReference()
                            .child(userUID)
                            .child(dataBase.PARENT_TABLES())
                            .child(currentPk)
                            .setValue(table);
                    Toast.makeText(getContext(), "Mesa modificada", Toast.LENGTH_LONG)
                            .show();
                    Navigation.findNavController(v).navigate(R.id.nav_tables);
                }
            }
        });
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String userUID = dataBase.getCurrentUser().getUid();
//                String currentPk = currentNumTableString;
//                dataBase.getDatabaseReference()
//                        .child(userUID)
//                        .child(dataBase.PARENT_TABLES())
//                        .child(currentPk).removeValue();
//                Toast.makeText(getContext(), "Mesa Eliminada", Toast.LENGTH_LONG)
//                        .show();
//                Navigation.findNavController(v).navigate(R.id.nav_tables);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}