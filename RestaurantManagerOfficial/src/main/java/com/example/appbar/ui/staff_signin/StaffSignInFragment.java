package com.example.appbar.ui.staff_signin;

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

import com.example.appbar.LogInActivity;
import com.example.appbar.R;
import com.example.appbar.data.DataBase;
import com.example.appbar.data.StaffData;
import com.example.appbar.databinding.FragmentStaffSigninBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.*;

public class StaffSignInFragment extends Fragment implements View.OnClickListener {

Button horaini_button,horafin_button,fecha_button,comporbar_button,fichar_button,terminar_button;
TextView horaini_textView,horafin_textView,fecha_textView;
EditText dni_editText;

    private DataBase dataBase = new DataBase();
    private DatabaseReference myRref;
    private String userUID;
    private StaffData data =null;
    private final String PARENT_STAFF = "staff";
    private FragmentStaffSigninBinding binding;
    private boolean control = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStaffSigninBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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


        horafin_button.setOnClickListener(this);
        horaini_button.setOnClickListener(this);
        fecha_button.setOnClickListener(this);
        comporbar_button.setOnClickListener(this);
        fichar_button.setOnClickListener(this);
        terminar_button.setOnClickListener(this);

        horaini_button.setEnabled(false);
        horafin_button.setEnabled(false);
        fichar_button.setEnabled(false);
        terminar_button.setEnabled(false);
        fecha_button.setEnabled(false);




    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        Context context;
        switch (view.getId()){

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

                if (dni_editText.getText().length()!=0) {
                    Search();
                }
                else {
                    Toast.makeText(getContext(), "Introduzca un empleado valido ", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fichar_button:

                data = new StaffData();
                data.addBookings(dni_editText.getText().toString().trim(),
                        horaini_textView.getText().toString().trim(),
                        horafin_textView.getText().toString(),
                        fecha_textView.getText().toString().trim());
                break;
            case R.id.terminar_button:

                Update();


                break;
        }

    }
    public  String hora(){
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm:ss");
   return (dtf2.format(LocalTime.now()));
    }
    public String fecha(){
        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    return date;
    }

    public void Search(){


        userUID = dataBase.getCurrentUser().getUid();
        dataBase.getDatabaseReference().child(userUID).child("staff").child(dni_editText.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                   String dni = snapshot.child("dni").getValue().toString().trim();
                   data = snapshot.getValue(StaffData.class);
                   if (data.getHorafin().length()==0) {
                       if (data.getFecha().compareTo(fecha()) == 0) {
                           horaini_textView.setText(data.getHoraini());
                           fecha_textView.setText(data.getFecha());
                           terminar_button.setEnabled(true);
                           horafin_button.setEnabled(true);
                       }
                   }
                    else {
                       horaini_textView.setText(data.getHoraini());
                       fecha_textView.setText(data.getFecha());
                       horafin_textView.setText(data.getHorafin());
                        Toast.makeText(getContext(), "Este empleado ya ha fichado su hora de " +
                                "salida para la fecha actual ", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
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
    public void Update(){
        userUID = dataBase.getCurrentUser().getUid();
        dataBase.getDatabaseReference().child(userUID).child("staff").child(dni_editText.getText().toString()).child("horafin").setValue(horafin_textView.getText());


    }
}