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

import com.example.appbar.R;
import com.example.appbar.data.ItemData;
import com.example.appbar.databinding.FragmentItemAddUpdateBinding;

import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class ItemAddUpdateFragment extends Fragment {

    private FragmentItemAddUpdateBinding binding;
    public  static String currentPkItemString;
    private Button addUpdateOkButton;
    private EditText updateDescriptionEditText, updatePriceEditText;
    private TextView beforeTextView, afterTextView, getDescriptionTextView, getPriceTextView;
    private ArrayList<ItemData> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemAddUpdateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null) {
            String currentPkItemString = bundle.getString("currentItemID");
            Toast.makeText(getContext(), currentPkItemString, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Error Bundle", Toast.LENGTH_LONG).show();
        }



        updateDescriptionEditText = view.findViewById(R.id.updatePriceEditText);
        updatePriceEditText = view.findViewById(R.id.updateDescriptionEditText);
        beforeTextView = view.findViewById(R.id.beforeTextView);
        afterTextView = view.findViewById(R.id.afterTextView);
        getDescriptionTextView = view.findViewById(R.id.getDescriptionTextView);
        getPriceTextView = view.findViewById(R.id.getPriceTextView);

        //getDescriptionTextView.setText();

        //beforeTextView.setText(currentPkItemString);


        addUpdateOkButton = view.findViewById(R.id.addUpdateOkButton);
        addUpdateOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
