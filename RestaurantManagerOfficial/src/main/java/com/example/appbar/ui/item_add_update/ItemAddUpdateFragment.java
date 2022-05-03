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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.appbar.R;
import com.example.appbar.data.DataBase;
import com.example.appbar.data.ItemData;
import com.example.appbar.databinding.FragmentItemAddUpdateBinding;
import com.example.appbar.ui.items.ItemsFragment;
import com.example.appbar.ui.tables.TablesFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.DescriptorProtos;

import java.util.ArrayList;
import java.util.Objects;

@SuppressWarnings("FieldCanBeLocal")
public class ItemAddUpdateFragment extends Fragment {

    private FragmentItemAddUpdateBinding binding;
    private DataBase dataBase = new DataBase();
    private ItemData item = new ItemData();
    private String currentDescriptionItemString;
    private String currentPriceItemString;
    private Button addUpdateOkButton;
    private EditText updateDescriptionEditText, updatePriceEditText;
    private TextView beforeTextView, afterTextView, getDescriptionTextView, getPriceTextView;
    private ArrayList<ItemData> list;

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
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateDescriptionEditText = view.findViewById(R.id.updatePriceEditText);
        updatePriceEditText = view.findViewById(R.id.updateDescriptionEditText);
        beforeTextView = view.findViewById(R.id.beforeTextView);
        afterTextView = view.findViewById(R.id.afterTextView);
        getDescriptionTextView = view.findViewById(R.id.getDescriptionTextView);
        getPriceTextView = view.findViewById(R.id.getPriceTextView);
        currentDescriptionItemString = ItemsFragment.currentDescriptionItemString;
        currentPriceItemString = ItemsFragment.currentPriceItemString;

        getDescriptionTextView.setText(currentDescriptionItemString);
        getPriceTextView.setText(currentPriceItemString);

        addUpdateOkButton = view.findViewById(R.id.addUpdateOkButton);
        addUpdateOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = updateDescriptionEditText.getText().toString();
                String price = updatePriceEditText.getText().toString();
                if (description.isEmpty() || price.isEmpty()) {
                    updateDescriptionEditText.setError("Campo obligatorio");
                    updatePriceEditText.setError("Campo obligatorio");
                } else {
                    item = new ItemData(description, price);
                    String userUID = dataBase.getCurrentUser().getUid();
                    String currentPk = currentDescriptionItemString
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
            }
        });
    }

    public void updateItem(String PK, String description, String price) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
