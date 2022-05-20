package com.example.appbar.ui.booking_select;

import android.annotation.SuppressLint;
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
import androidx.navigation.Navigation;


import com.example.appbar.R;
import com.example.appbar.data.BookingsData;
import com.example.appbar.data.DataBase;
import com.example.appbar.data.DataFlow;
import com.example.appbar.databinding.FragmentBookingDeleteBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookingDeleteFragment extends Fragment implements View.OnClickListener{
    private FragmentBookingDeleteBinding binding;
    private final DataBase dataBase = new DataBase();
    private TextView nombre_deleteTextView,telefono_deleteTextView,email_deleteTextView,fecha_deleteTextView,personas_deletetextView;
    private Button Deletebutton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookingDeleteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nombre_deleteTextView = view.findViewById(R.id.nombre_deleteTextView);
        telefono_deleteTextView = view.findViewById(R.id.telefono_deleteTextView);
        fecha_deleteTextView = view.findViewById(R.id.fecha_deleteTextView);
        personas_deletetextView = view.findViewById(R.id.personas_deletetextView);
        email_deleteTextView = view.findViewById(R.id.email_deleteTextView);
        Deletebutton = view.findViewById(R.id.Deletebutton);
        Deletebutton.setOnClickListener(this);

        nombre_deleteTextView.setText(DataFlow.getNombre);
        telefono_deleteTextView.setText(DataFlow.getTelefono);
        fecha_deleteTextView.setText(DataFlow.getFecha);
        personas_deletetextView.setText(DataFlow.getPersonas);
        email_deleteTextView.setText(DataFlow.getEmail);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onClick(View view) {
        String userUID = dataBase.getCurrentUser().getUid();
        dataBase.getDatabaseReference().child(userUID).child(dataBase.PARENT_BOOKING()).child(nombre_deleteTextView.getText().toString())
                .removeValue();
        Toast.makeText(getContext(), "Reserva eliminada", Toast.LENGTH_LONG)
                .show();
        Navigation.findNavController(view).navigate(R.id.nav_bookings);
    }

}

