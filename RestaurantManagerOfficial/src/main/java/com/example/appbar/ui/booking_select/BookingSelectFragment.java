package com.example.appbar.ui.booking_select;

import static android.widget.Toast.LENGTH_SHORT;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
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


import com.example.appbar.LogInActivity;
import com.example.appbar.R;
import com.example.appbar.data.BookingsData;
import com.example.appbar.databinding.FragmentBookingSelectBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        personas_editText = view.findViewById(R.id.personas_editText);
        alta_button = view.findViewById(R.id.alta_button);
        alta_button.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        BookingsData Datos = new BookingsData();
        if (Comprobar_fecha(fecha_editText.getText().toString().trim())==false){
            fecha_editText.setTextColor(Color.RED);
            Toast.makeText(getContext(), "Formato invalido ingrese el siguiente formato dd/mm/YYYY", LENGTH_SHORT).show();
        }
        else{
            fecha_editText.setTextColor(Color.BLACK);
            if (Comprobar_email(email_editText.getText().toString().trim())==false){
                email_editText.setTextColor(Color.RED);
                Toast.makeText(getContext(), "Ingrese un formato Valido de email", LENGTH_SHORT).show();
            }
            else {
                email_editText.setTextColor(Color.BLACK);
                if (Comprobar_Telefono(telefono_editText.getText().toString().trim())==false) {
                    telefono_editText.setTextColor(Color.RED);
                    Toast.makeText(getContext(), "Ingrese un numero de telefono correcto", LENGTH_SHORT).show();
                }
                else {
                    if (Comprobar_personas(personas_editText.getText().toString().trim())==false){
                        personas_editText.setTextColor(Color.RED);
                        Toast.makeText(getContext(), "No hay mesas de mas de 6 personas", LENGTH_SHORT).show();
                    }
                    else {
                        Datos.addBookings(nombre_editText.getText().toString().trim(), email_editText.getText().toString().trim(), telefono_editText.getText().toString().trim(), fecha_editText.getText().toString().trim(), personas_editText.getText().toString().trim());
                        nombre_editText.setText("");
                        email_editText.setText("");
                        telefono_editText.setText("");
                        fecha_editText.setText("");
                        personas_editText.setText("");
                        Navigation.findNavController(view).navigate(R.id.nav_bookings);
                    }
                }
            }
        }
    }

    public boolean Comprobar_fecha(String fecha) {

        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        Date f = null;
        date.setLenient(false);
        try{
            f = date.parse(fecha);
            return true;

        } catch (ParseException e) {
            e.printStackTrace();
            return false;

        }

    }
    public boolean Comprobar_email(String email){
        if ( !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean Comprobar_Telefono(String Telefono){
        if (Telefono.length()>8 && Telefono.length()<12){
            return true;
        }
        else
            return false;

    }
    public boolean Comprobar_personas(String personas) {

        if (personas.length() != 0) {
            int i = Integer.valueOf(personas);

            if (0 < i && i < 7) {
                return true;
            } else {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

}
