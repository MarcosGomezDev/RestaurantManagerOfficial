package com.example.appbar.ui.table_remove_item_basket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appbar.R;
import com.example.appbar.databinding.FragmentRemoveItemBasketBinding;
import com.example.appbar.ui.items.ItemsFragment;

@SuppressWarnings("FieldCanBeLocal")
public class TableRemoveItemBasket extends Fragment {

    private FragmentRemoveItemBasketBinding binding;
    private ImageButton addItemBasketButton, subtractItemBasketButton;
    private Button removeItemBasketButton;
    private TextView descriptionItemBasketTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRemoveItemBasketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addItemBasketButton = view.findViewById(R.id.addItemBasketButton);
        subtractItemBasketButton = view.findViewById(R.id.subtractItemBasketButton);
        removeItemBasketButton = view.findViewById(R.id.removeItemBasketButton);
        descriptionItemBasketTextView =view.findViewById(R.id.descriptionItemBasketTextView);

        descriptionItemBasketTextView.setText(ItemsFragment.currentDescriptionItemString);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
