package com.example.appbar.ui.items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.appbar.R;
import com.example.appbar.data.DataBase;
import com.example.appbar.data.ItemData;
import com.example.appbar.databinding.FragmentItemAddBinding;

/**
 * Clase que maneja la creación de un nuevo articulo.
 */
@SuppressWarnings("FieldCanBeLocal")
public class ItemNewFragment extends Fragment {

    private FragmentItemAddBinding binding;
    private final DataBase dataBase = new DataBase();
    private ItemData item;
    private EditText descriptionEditText, priceEditText;
    private Button newItemButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        descriptionEditText = view.findViewById(R.id.descriptionEditText);
        priceEditText = view.findViewById(R.id.priceEditText);
        newItemButton = view.findViewById(R.id.addNewItemButton);

        /**
         * Botón que recupera los datos introducidos por pantalla y crea un articulo con ellos.
         */
        newItemButton.setOnClickListener(v -> {
            String description = descriptionEditText.getText().toString();
            double price = Double.parseDouble(priceEditText.getText().toString());
            if (description.isEmpty() || price < 0) {
                descriptionEditText.setError("Campo obligatorio");
                priceEditText.setError("Campo obligatorio");
            } else {
                item = new ItemData(description, price, 1);
                String userUID = dataBase.getCurrentUser().getUid();
                String currentPk = description.replace(" ", "_");
                dataBase.getDatabaseReference()
                        .child(userUID)
                        .child(dataBase.PARENT_ITEMS())
                        .child(currentPk)
                        .setValue(item);
                Toast.makeText(getContext(), "Articulo añadido", Toast.LENGTH_LONG)
                        .show();
                Navigation.findNavController(v).navigate(R.id.nav_items);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
