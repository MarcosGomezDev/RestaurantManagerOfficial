package com.example.appbar.ui.tables;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbar.R;
import com.example.appbar.data.TablesData;

import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.MyHolder>
        implements View.OnClickListener {

    private final Context context;
    private final ArrayList<TablesData> list;
    private View.OnClickListener listener;

    public TableAdapter(Context context, ArrayList<TablesData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.table_view, parent, false);
        v.setOnClickListener(this);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        TablesData table = list.get(position);
        holder.tableTextView.setText(table.getNumTable());
        holder.capacityTextView.setText(table.getNumPeople());
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

        TextView tableTextView, capacityTextView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tableTextView = itemView.findViewById(R.id.tableTextView);
            capacityTextView = itemView.findViewById(R.id.capacityTextView);
        }
    }
}
