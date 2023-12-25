package com.example.reservation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<Reservation> historyItems;

    public HistoryAdapter(List<Reservation> historyItems) {
        this.historyItems = historyItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservation historyItem = historyItems.get(position);
        holder.bind(historyItem);
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView customerNameTextView;
        private TextView seatingAreaTextView;
        private TextView dateTextView;
        private TextView mealTimeTextView;
        private TextView tableSizeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customerNameTextView = itemView.findViewById(R.id.customerNameTextView);
            seatingAreaTextView = itemView.findViewById(R.id.seatingAreaTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            mealTimeTextView = itemView.findViewById(R.id.mealTimeTextView);
            tableSizeTextView = itemView.findViewById(R.id.tableSizeTextView);
        }

        public void bind(Reservation historyItem) {
            customerNameTextView.setText("Name: " + historyItem.getCustomerName());
            seatingAreaTextView.setText("Seating Area: " + historyItem.getSeatingArea());
            dateTextView.setText("Date: " + historyItem.getDate());
            mealTimeTextView.setText("Meal Time: " + historyItem.getMeal());
            tableSizeTextView.setText("Table Size: " + historyItem.getTableSize());
        }
    }
}