package cz.mtr.analyzaprodeju.fragments.printer.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.fragments.printer.other.PrinterItem;

public class PrinterItemAdapter extends RecyclerView.Adapter<PrinterItemAdapter.ItemHolder> {
    public final static String TAG = PrinterItemAdapter.class.getSimpleName();
    private List<PrinterItem> printerItems = new ArrayList<>();

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.printer_list_item, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        PrinterItem item = printerItems.get(position);
        holder.mNameTextView.setText(item.getName());
        Log.d(TAG, "In orders " + item.getAmount());
        holder.mAmountTextView.setText(item.getAmount());

    }

    @Override
    public int getItemCount() {
        return printerItems.size();
    }

    public void setPrinterItems(List<PrinterItem> printerItems) {
        this.printerItems = printerItems;
        notifyDataSetChanged();
    }


    class ItemHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private TextView mAmountTextView;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.itemNameTextView);
            mAmountTextView = itemView.findViewById(R.id.itemAmountTextView);

        }
    }


}
