package com.example.appbar.ui.table_selected;

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
import androidx.navigation.Navigation;

import com.example.appbar.R;
import com.example.appbar.data.DataBase;
import com.example.appbar.data.TablesData;
import com.example.appbar.databinding.FragmentTableSelectedBinding;
import com.example.appbar.ui.tables.TableAdapter;
import com.example.appbar.ui.tables.TablesFragment;

import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class TableSelectedFragment extends Fragment {

    private FragmentTableSelectedBinding binding;
    private DataBase dataBase;
    private TablesData table;
    private TableAdapter tableAdapter;
    private ArrayList<TablesData> list;
    private Context context;
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
        dataBase = new DataBase();
        context = this.getActivity();
        list = new ArrayList<>();
        tableAdapter = new TableAdapter(context, list);
        table = new TablesData();

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

        TablesFragment.countTable = tableAdapter.getItemCount();

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