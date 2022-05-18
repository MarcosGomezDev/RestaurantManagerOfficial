package com.example.appbar.ui.item_add_update;

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
import com.example.appbar.data.ItemData;
import com.example.appbar.databinding.FragmentItemAddUpdateBinding;
import com.example.appbar.ui.items.ItemsFragment;

@SuppressWarnings("FieldCanBeLocal")
public class ItemUpdateFragment extends Fragment {

    private FragmentItemAddUpdateBinding binding;
    private final DataBase dataBase = new DataBase();
    private ItemData item = new ItemData();
    //private String currentDescriptionItemString;
    private double currentPriceItemDouble;
    private Button addUpdateOkButton, removeButton;
    private EditText updateDescriptionEditText, updatePriceEditText;
    private TextView getDescriptionTextView, getPriceTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (ItemsFragment.navItem) {
//            ((AppCompatActivity)getActivity()).getSupportActionBar()
//                    .setTitle("Modificación de producto");
//        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemAddUpdateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateDescriptionEditText = view.findViewById(R.id.updatePriceEditText);
        updatePriceEditText = view.findViewById(R.id.updateDescriptionEditText);
        getDescriptionTextView = view.findViewById(R.id.getDescriptionTextView);
        getPriceTextView = view.findViewById(R.id.getPriceTextView);
        //currentDescriptionItemString = ItemsFragment.currentDescriptionItemString;
        currentPriceItemDouble = ItemsFragment.currentPriceItemDouble;
        addUpdateOkButton = view.findViewById(R.id.addUpdateOkButton);
        removeButton = view.findViewById(R.id.removeButton);
        getDescriptionTextView.setText(DataFlow.currentDescriptionItemString);
        getPriceTextView.setText(String.valueOf(currentPriceItemDouble));

        addUpdateOkButton.setOnClickListener(v -> {
            String description = updateDescriptionEditText.getText().toString();
            double price = Double.parseDouble(updatePriceEditText.getText().toString());
            if (description.isEmpty() || price < 0) {
                updateDescriptionEditText.setError("Campo obligatorio");
                updatePriceEditText.setError("Campo obligatorio");
            } else {
                item = new ItemData(description, price, 1);
                String userUID = dataBase.getCurrentUser().getUid();
                String currentPk = DataFlow.currentDescriptionItemString
                        .replace(" ", "_");
                dataBase.getDatabaseReference()
                        .child(userUID)
                        .child(dataBase.PARENT_ITEMS())
                        .child(currentPk).removeValue();
                dataBase.getDatabaseReference()
                        .child(userUID)
                        .child(dataBase.PARENT_ITEMS())
                        .child(description.replace(" ", "_"))
                        .setValue(item);
                Toast.makeText(getContext(), "Articulo modificado", Toast.LENGTH_LONG)
                        .show();
                Navigation.findNavController(v).navigate(R.id.nav_items);
            }
        });

        removeButton.setOnClickListener(v -> {
            String userUID = dataBase.getCurrentUser().getUid();
            String currentPk = DataFlow.currentDescriptionItemString
                    .replace(" ", "_");
            dataBase.getDatabaseReference()
                    .child(userUID)
                    .child(dataBase.PARENT_ITEMS())
                    .child(currentPk)
                    .removeValue();
            Toast.makeText(getContext(), "Articulo eliminado", Toast.LENGTH_LONG)
                    .show();
            Navigation.findNavController(v).navigate(R.id.nav_items);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
