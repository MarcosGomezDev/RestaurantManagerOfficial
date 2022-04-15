package com.example.appbar.ui.bookings;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.appbar.R;
import com.example.appbar.databinding.FragmentBookingsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingsFragment extends Fragment {

    private FragmentBookingsBinding binding;
    private ListView lista;
    //private FloatingActionButton fa_annadir,fa_fecha;
    private TextView tv_fecha;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BookingsViewmodel ficharViewModel =
                new ViewModelProvider(this).get(BookingsViewmodel.class);

        binding = FragmentBookingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_fecha = view.findViewById(R.id.tv_fecha);
       //fa_annadir = view.findViewById(R.id.fa_annadir);
       //fa_fecha = view.findViewById(R.id.tv_fecha);
        lista = view.findViewById(R.id.lista);

        fecha();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void fecha(){

        String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        tv_fecha.setText(date);
    }

}
