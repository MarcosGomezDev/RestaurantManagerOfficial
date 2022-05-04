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

import java.util.ArrayList;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.MyViewHolder> implements View.OnClickListener{

    private Context context;
    private ArrayList<BookingsData> list;
    private View.OnClickListener listener;

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
        holder.telefonoTextView.setText(bookingsData.getTelefono());
        holder.emailTextView.setText(bookingsData.getEmail());
        holder.fechaTextView.setText(bookingsData.getFecha());
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
        TextView nombreTextView, telefonoTextView,emailTextView,fechaTextView;
        public MyViewHolder(@NonNull View bookingView) {
            super(bookingView);
            nombreTextView = bookingView.findViewById(R.id.nombreTextView);
            telefonoTextView = bookingView.findViewById(R.id.tableTextView);
            fechaTextView = bookingView.findViewById(R.id.fechaact_textView);
            emailTextView = bookingView.findViewById(R.id.capacityTextView);
        }
    }
}
