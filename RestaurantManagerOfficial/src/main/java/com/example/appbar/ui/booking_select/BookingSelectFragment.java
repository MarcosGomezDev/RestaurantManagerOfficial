package com.example.appbar.ui.booking_select;

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
import androidx.lifecycle.ViewModelProvider;


import com.example.appbar.R;
import com.example.appbar.data.BookingsData;
import com.example.appbar.databinding.FragmentBookingSelectBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookingSelectFragment extends Fragment implements View.OnClickListener {

    private FragmentBookingSelectBinding binding;
    private EditText nombre_editText, email_editText, telefono_editText, fecha_editText, personas_editText;
    private Button alta_button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookingSelectBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nombre_editText = view.findViewById(R.id.nombre_editText);
        email_editText = view.findViewById(R.id.email_editText);
        telefono_editText = view.findViewById(R.id.telefono_editText);
        fecha_editText = view.findViewById(R.id.fecha_editText);
        alta_button = view.findViewById(R.id.alta_button);
        alta_button.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        BookingsData Datos = new BookingsData();
        Datos.addBookings(nombre_editText.getText().toString().trim(), email_editText.getText().toString().trim(), telefono_editText.getText().toString().trim(), fecha_editText.getText().toString().trim());
        nombre_editText.setText("");
        email_editText.setText("");
        telefono_editText.setText("");
        fecha_editText.setText("");
    }
}
