package com.example.appbar.ui.bookings;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbar.R;
import com.example.appbar.data.BookingsData;
import com.example.appbar.data.DataBase;
import com.example.appbar.data.DataFlow;
import com.example.appbar.databinding.FragmentBookingsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
   Clase BookingsFragment
   Esta clase lista las reservas de la fecha selecionda , atraves de esta clase se accede a dar
   de altas nuevas reservas y a eliminar las reservas .

 */
public class BookingsFragment extends Fragment implements View.OnClickListener {

    private FragmentBookingsBinding binding;
    private DataBase dataBase;
    private DatabaseReference myRef;
    private BookingsAdapter bookingsAdapter;
    private RecyclerView recyclerView;
    public static String currentDescriptionItemString;
    private ArrayList<BookingsData> list;
    private String userUID;
    private Context context;
    private FloatingActionButton fa_fecha,fa_annadir;
    private Button Delete;
    private TextView fecha2Textview,diatextView,mestextView,annotextView;
    private ImageButton diaimagenButtom,mesimagenButton,annoimagenButton,ldiaimagenButtom, lmesimagenButton,lannoimagenButton,calendarioimageButton;
    private int i;
    private String dia= "dd";
    private String mes ="MM";
    private String anno = "yyyy";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Asignacion de los elementos del layaout
        fa_annadir = view.findViewById(R.id.fa_annadir);
        recyclerView = view.findViewById(R.id.r);
        diatextView = view.findViewById(R.id.diatextView);
        mestextView = view.findViewById(R.id.mestextView);
        annotextView = view.findViewById(R.id.annotextView);
        diaimagenButtom = view.findViewById(R.id.pdiaimageButton);
        mesimagenButton = view.findViewById(R.id.pmesimageButton);
        annoimagenButton = view.findViewById(R.id.pannoimageButton);
        ldiaimagenButtom = view.findViewById(R.id.ldiaimageButton);
        lmesimagenButton = view.findViewById(R.id.lmesimageButton);
        lannoimagenButton = view.findViewById(R.id.lannoimageButton);
        calendarioimageButton = view.findViewById(R.id.calendarioimageButton);
        Delete= view.findViewById((R.id.Deletebutton));
        //Escribimos en los Textview la fecha actual
        diatextView.setText(fechadia());
        mestextView.setText(fechames());
        annotextView.setText(fechaanno());
        //listener de evento Onclick a los botones
        diaimagenButtom.setOnClickListener(this);
        mesimagenButton.setOnClickListener(this);
        annoimagenButton.setOnClickListener(this);
        fa_annadir.setOnClickListener(this);
        ldiaimagenButtom.setOnClickListener(this);
        lmesimagenButton.setOnClickListener(this);
        lannoimagenButton.setOnClickListener(this);
        calendarioimageButton.setOnClickListener(this);

        dataBase = new DataBase();
        reservas();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    /**
        Este metodo delvuele el dia Actual
        @return date
     */
    public String fechadia(){
        String date = new SimpleDateFormat(dia).format(new Date());
        return date;
    }
    /**
        Este metodo devuelve el mes actual
        @return date
     */
    public String fechames(){
        String date = new SimpleDateFormat(mes).format(new Date());
        return date;
    }
    /**
    Este metodo devuelve el a??o actual
   @return date
    */
    public String fechaanno(){
        String date = new SimpleDateFormat(anno).format(new Date());
        return date;
    }
    /**
        Este metodo lista las Reservas del dia selecionado , y en caso de hacer click sobre la
        reserva se accedera nav_booking_delete y podra eliminar la reserva seleccionada
     */
    public void reservas(){
        userUID = dataBase.getCurrentUser().getUid();
        context = this.getActivity();
        list = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        bookingsAdapter = new BookingsAdapter(context, list);
        recyclerView.setAdapter(bookingsAdapter);

        myRef = dataBase.getInstance().getReference(userUID).child(dataBase.PARENT_BOOKING());
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BookingsData bookingsData = dataSnapshot.getValue(BookingsData.class);
                    if (bookingsData.getFecha().compareTo((diatextView.getText().toString().trim())+"/"+(mestextView.getText().toString().trim())+"/"+(annotextView.getText().toString().trim()))==0) {
                        list.add(bookingsData);
                    }
                }
                bookingsAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        bookingsAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataFlow.getNombre = list.get(recyclerView.getChildAdapterPosition(v)).getNombre();
                DataFlow.getEmail = list.get(recyclerView.getChildAdapterPosition(v)).getEmail();
                DataFlow.getFecha = list.get(recyclerView.getChildAdapterPosition(v)).getFecha();
                DataFlow.getPersonas = list.get(recyclerView.getChildAdapterPosition(v)).getPersonas();
                DataFlow.getTelefono = list.get(recyclerView.getChildAdapterPosition(v)).getTelefono();
                Log.println(Log.WARN, "Clic", "Intentando navegar.");
                Navigation.findNavController(v).navigate(R.id.nav_booking_delete);
            }
        });
    }
    /**
        @param View view
        Este metodo controla los botones para poder listar las reservas por fecha , con este metodo
        se puede aumentar el dia , el mes y el a??o para poder ver las reservas de la fecha seleccionada
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calendarioimageButton:
                diatextView.setText(fechadia());
                mestextView.setText(fechames());
                annotextView.setText(fechaanno());
                reservas();
                break;
            case R.id.fa_annadir:
                Navigation.findNavController(view).navigate(R.id.nav_booking_selected);
                break;

            case R.id.pdiaimageButton:
                i = Integer.valueOf(diatextView.getText().toString().trim());
                i = i +1;
                diatextView.setText(String.valueOf(i));
                reservas();
                break;
            case R.id.pmesimageButton:
                i = Integer.valueOf(mestextView.getText().toString().trim());
                i = i +1;
                mestextView.setText(String.valueOf(i));
                reservas();
                break;
            case R.id.pannoimageButton:
                i = Integer.valueOf(annotextView.getText().toString().trim());
                i = i +1;
                annotextView.setText(String.valueOf(i));
                reservas();
                break;
            case R.id.ldiaimageButton:
                i = Integer.valueOf(diatextView.getText().toString().trim());
                i = i -1;
                diatextView.setText(String.valueOf(i));
                reservas();
                break;
            case R.id.lmesimageButton:
                i = Integer.valueOf(mestextView.getText().toString().trim());
                i = i -1;
                mestextView.setText(String.valueOf(i));
                reservas();
                break;
            case R.id.lannoimageButton:
                i = Integer.valueOf(annotextView.getText().toString().trim());
                i = i-1;
                annotextView.setText(String.valueOf(i));
                reservas();
                break;
        }
    }
}