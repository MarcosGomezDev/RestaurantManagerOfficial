package com.example.appbar.ui.staff_signin;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import com.example.appbar.data.StaffData;
import com.example.appbar.databinding.FragmentStaffSigninBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.*;
/**
   Clase StaffSingInFragment
   Esta clase se encarga de la gestion de fichajes de los empleados
 */
@SuppressWarnings("SpellCheckingInspection")
public class StaffSignInFragment extends Fragment implements View.OnClickListener {

    private FragmentStaffSigninBinding binding;
    private final DataBase dataBase = new DataBase();
    private String userUID;
    private StaffData data;
    private Button horaini_button, horafin_button, fecha_button, comporbar_button, fichar_button, terminar_button;
    private TextView horaini_textView, horafin_textView, fecha_textView;
    private EditText dni_editText;
    private String dia = "dd";
    private String mes = "MM";
    private String anno = "yyyy";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStaffSigninBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Asignacion de elementos de layaout
        horafin_button = view.findViewById(R.id.horafin_button);
        horaini_button = view.findViewById(R.id.horaini_button);
        fecha_button = view.findViewById(R.id.fecha_button);
        comporbar_button = view.findViewById(R.id.comprobar_button);
        fichar_button = view.findViewById(R.id.fichar_button);
        terminar_button = view.findViewById(R.id.terminar_button);
        horaini_textView = view.findViewById(R.id.horaini_textView);
        horafin_textView = view.findViewById(R.id.horafin_textView);
        fecha_textView = view.findViewById(R.id.fecha_textView);
        dni_editText = view.findViewById(R.id.dni_editText);
        // listener asignado al evento OnClick
        horafin_button.setOnClickListener(this);
        horaini_button.setOnClickListener(this);
        fecha_button.setOnClickListener(this);
        comporbar_button.setOnClickListener(this);
        fichar_button.setOnClickListener(this);
        terminar_button.setOnClickListener(this);
        // Deshabilitacion de botones
        horaini_button.setEnabled(false);
        horafin_button.setEnabled(false);
        fichar_button.setEnabled(false);
        terminar_button.setEnabled(false);
        fecha_button.setEnabled(false);

        StaffData data = new StaffData();
        //Obtenemos dia , mes ,a??o
        dia = data.fechadia();
        mes = data.fechames();
        anno = data.fechaanno();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    /*
        @param View view
        Este metodo Gestiona el click de los botones
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.horaini_button:
                horaini_textView.setText(hora());
                break;
            case R.id.horafin_button:
                horafin_textView.setText(hora());
                break;
            case R.id.fecha_button:
                fecha_textView.setText(fecha());
                break;
            case R.id.comprobar_button:
                if (dni_editText.getText().length()==9) {
                    Search();
                } else {
                    Toast.makeText(getContext(), "Introduzca un empleado valido ", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fichar_button:
                data = new StaffData();
                data.addBookings(dni_editText.getText().toString().trim(),
                        horaini_textView.getText().toString().trim(),
                        horafin_textView.getText().toString(),
                        fecha_textView.getText().toString().trim());
                Navigation.findNavController(view).navigate(R.id.nav_home);
                break;
            case R.id.terminar_button:

                Update();
                Navigation.findNavController(view).navigate(R.id.nav_home);
                break;
        }
    }
    /*
     Este metodo devuelve la hora los minutos y los segundos
     @retunr Datetime
     */
    public String hora() {
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm:ss");
        return (dtf2.format(LocalTime.now()));
    }
    /*
     Este metodo devuelve la fecha actual con un formato determinado
     @return date
     */
    public String fecha() {
        @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        return date;
    }
    /*
        Este metodo busca dentro de la base de datos el campo introducido en dni_editText , si este campo
        no se encuentra en la base de datos se activan unos determinados botones como son los de la fecha
         la hora de entrada y fichar , en caso de que este campo si se encuentre en la base de datos se recupean
         los datos y se activa en terminar turno en caso que ya halla terminado el turno aparacera un mesage de error
     */
    public void Search() {
        userUID = dataBase.getCurrentUser().getUid();
        dataBase.getDatabaseReference().child(userUID).child("staff").child((dni_editText.getText().toString() + " " + dia + mes + anno)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    data = snapshot.getValue(StaffData.class);
                    assert data != null;
                    if (data.getHorafin().length() == 0) {
                        if (data.getFecha().compareTo(fecha()) == 0) {
                            horaini_textView.setText(data.getHoraini());
                            fecha_textView.setText(data.getFecha());
                            terminar_button.setEnabled(true);
                            horafin_button.setEnabled(true);

                        }
                    }
                    else {
                        dni_editText.setTextColor(Color.RED);
                        dni_editText.setText("TURNO YA ACABADO");
                        horaini_textView.setText("");
                        fecha_textView.setText("");
                        horafin_textView.setText("");

                    }
                } else {
                    comporbar_button.setEnabled(false);
                    fichar_button.setEnabled(true);
                    horaini_button.setEnabled(true);
                    fecha_button.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
/*
    Este metodo Actualiza el campo horafin perteneciente al registro del dni correspondiente
 */
    public void Update() {

        userUID = dataBase.getCurrentUser().getUid();
        String Cadena = dni_editText.getText().toString() + " " +dia+mes+anno;
        String hora = horafin_textView.getText().toString();

       dataBase.getDatabaseReference().child(userUID).child(dataBase.PARENT_STAFF()).child(Cadena).child("horafin").setValue(hora());


    }

}