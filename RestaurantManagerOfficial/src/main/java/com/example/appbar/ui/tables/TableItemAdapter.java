package com.example.appbar.ui.tables;

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

public class TableItemAdapter extends RecyclerView.Adapter<TableItemAdapter.MyHolder>
        implements View.OnClickListener {

    private final Context context;
    private final ArrayList<ItemData> list;
    private View.OnClickListener listener;

    public TableItemAdapter(Context context, ArrayList<ItemData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.table_view_items,parent,false);
        v.setOnClickListener(this);
        return  new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ItemData item = list.get(position);
        holder.descriptionTextView.setText(item.getDescription());
        holder.priceTextView.setText(String.valueOf(item.getPrice()));
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

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView descriptionTextView, priceTextView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
        }
    }
}
