package com.example.appbar.ui.bookings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbar.R;
import com.example.appbar.data.BookingsData;
import com.example.appbar.data.ItemData;

import java.util.ArrayList;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<BookingsData> list;

    public BookingsAdapter(Context context, ArrayList<BookingsData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.booking_view,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        BookingsData bookings = list.get(position);
        holder.fechaTextView.setText(bookings.getFecha());
        holder.emailTextView.setText(bookings.getEmail());
        holder.telefonoTextView.setText(bookings.getTelefono());
        holder.nombreTextView.setText(bookings.getNombre());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nombreTextView,telefonoTextView,emailTextView,fechaTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            telefonoTextView = itemView.findViewById(R.id.telefonoTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            fechaTextView = itemView.findViewById(R.id.fechaTextView);
        }
    }
}
