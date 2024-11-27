package com.example.onlinereno;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {
    private Context context;
    private List<OrderHistory> orderHistoryList;
    private DatabaseHelper databaseHelper;

    public OrderHistoryAdapter(Context context, List<OrderHistory> orderHistoryList) {
        this.context = context;
        this.orderHistoryList = orderHistoryList;
        this.databaseHelper = new DatabaseHelper(context); // Inisialisasi DatabaseHelper
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderHistory order = orderHistoryList.get(position);
        holder.fruitNameTextView.setText(order.getFruitName());
        holder.priceTextView.setText(String.format("$%.2f", order.getPricePerKg()));
        holder.quantityTextView.setText(String.format("%.1f kg", order.getQuantity()));
        holder.totalPriceTextView.setText(String.format("Total: $%.2f", order.getTotalPrice()));

        // Fungsi hapus data
        holder.deleteButton.setOnClickListener(v -> {
            boolean isDeleted = databaseHelper.deleteOrderById(order.getId());
            if (isDeleted) {
                Toast.makeText(context, "Data deleted!", Toast.LENGTH_SHORT).show();
                orderHistoryList.remove(position); // Hapus item dari daftar
                notifyItemRemoved(position); // Update RecyclerView
            } else {
                Toast.makeText(context, "Failed to delete data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderHistoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fruitNameTextView, priceTextView, quantityTextView, totalPriceTextView;
        ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fruitNameTextView = itemView.findViewById(R.id.fruitNameTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            totalPriceTextView = itemView.findViewById(R.id.totalPriceTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton); // Tombol hapus
        }
    }
}
