package com.example.appbar.ui.staff_signin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appbar.R;
import com.example.appbar.data.StaffData;
import com.example.appbar.databinding.FragmentStaffSigninBinding;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.*;

public class StaffSignInFragment extends Fragment implements View.OnClickListener {
Button horaini_button,horafin_button,fecha_button,comporbar_button,fichar_button,terminar_button;
TextView horaini_textView,horafin_textView,fecha_textView;
EditText dni_editText;

    private FragmentStaffSigninBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StaffSignInViewModel staffSignInViewModel =
                new ViewModelProvider(this).get(StaffSignInViewModel.class);

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

        horafin_button.setEnabled(false);
        fichar_button.setEnabled(false);
        terminar_button.setEnabled(false);



    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.horaini_button:

                horaini_textView.setText(hora());
                horafin_button.setEnabled(true);

                break;
            case R.id.horafin_button:

                horafin_textView.setText(hora());
                fichar_button.setEnabled(true);

                break;
            case R.id.fecha_button:

                fecha_textView.setText(fecha());

                break;
            case R.id.comprobar_button:
                break;
            case R.id.fichar_button:

                StaffData data = new StaffData(dni_editText.getText().toString().trim(),horaini_textView.getText().toString().trim(),horafin_textView.getText().toString(),fecha_textView.getText().toString().trim());
                break;
            case R.id.terminar_button:
                fichar_button.setEnabled(true);
                break;
        }

    }
    public  String hora(){
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("HH:mm:ss");
   return (dtf1.format(LocalDateTime.now()));
    }
    public String fecha(){
        String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    return date;
    }
}