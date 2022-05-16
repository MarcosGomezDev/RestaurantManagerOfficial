package com.example.appbar.ui.table_box;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbar.R;
import com.example.appbar.data.ItemData;

import java.util.ArrayList;

public class TableBasketAdapter extends RecyclerView.Adapter<TableBasketAdapter.MyViewHolder>
        implements View.OnClickListener {

    private final Context context;
    private final ArrayList<ItemData> list;
    private View.OnClickListener listener;

    public TableBasketAdapter(Context context, ArrayList<ItemData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TableBasketAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.table_view_items,parent,false);
        v.setOnClickListener(this);
        return  new TableBasketAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TableBasketAdapter.MyViewHolder holder, int position) {
        ItemData item = list.get(position);
        holder.descriptionTextView.setText(item.getDescription());
        holder.priceTextView.setText(String.valueOf(item.getPrice()));
        holder.actualUnitsTextView.setText(String.valueOf(item.getUnits()));
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
        TextView descriptionTextView, priceTextView, actualUnitsTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            actualUnitsTextView = itemView.findViewById(R.id.actualUnitsTextView);
        }
    }
}
