package com.example.appbar.ui.bookings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbar.R;
import com.example.appbar.data.BookingsData;
import com.example.appbar.data.DataBase;

import java.util.ArrayList;
/**
    adaptador es un mecanismo de Android que hace de puente entre nuestros datos y las vistas contenidas
    en un ListView o Recicler. Dado que es muy frecuente usar bases de datos e nuestras
     aplicaciones, existen adaptadores específicos para este caso usaremos un ReciclerView.

 */
public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.MyViewHolder> implements View.OnClickListener{

    private Context context;
    private ArrayList<BookingsData> list;
    private View.OnClickListener listener;
    private DataBase dataBase = new DataBase();
    private String userUID;

    public BookingsAdapter(Context context, ArrayList<BookingsData> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.booking_view,parent,false);
        v.setOnClickListener(this);

        return  new MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BookingsData bookingsData = list.get(position);
        holder.nombreTextView.setText(bookingsData.getNombre());
        holder.telefonoTextView.setText((bookingsData.getTelefono()));
        holder.emailTextView.setText((bookingsData.getEmail()));
        holder.fechaTextView.setText((bookingsData.getFecha()));
        holder.personastextView.setText((bookingsData.getPersonas()));

    }

    @Override
    public int getItemCount() { return list.size(); }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null) {
            listener.onClick(v);
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nombreTextView, telefonoTextView, emailTextView, fechaTextView,personastextView;
        Button deleteButton;

        public MyViewHolder(@NonNull View bookView) {
            super(bookView);
            nombreTextView = bookView.findViewById(R.id.nombre_deleteTextView);
            telefonoTextView = bookView.findViewById(R.id.telefono_deleteTextView);
            emailTextView = bookView.findViewById(R.id.email_deleteTextView);
            fechaTextView  = bookView.findViewById(R.id.fecha_deleteTextView);
            personastextView = bookView.findViewById(R.id.personas_deletetextView);

        }
    }


}