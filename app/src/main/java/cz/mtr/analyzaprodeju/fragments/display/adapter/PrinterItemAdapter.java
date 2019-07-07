package cz.mtr.analyzaprodeju.fragments.display.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cz.mtr.analyzaprodeju.Interfaces.OnItemClickListener;
import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.fragments.display.other.PrinterItem;

public class PrinterItemAdapter extends RecyclerView.Adapter<PrinterItemAdapter.ItemHolder> {
    public final static String TAG = PrinterItemAdapter.class.getSimpleName();
    private List<PrinterItem> printerItems = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;


    public PrinterItemAdapter(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.printer_list_item, parent, false);
        return new ItemHolder(itemView, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        PrinterItem item = printerItems.get(position);
        holder.mNameTextView.setText(item.getName());
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


    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private TextView mAmountTextView;
        private OnItemClickListener mOnItemClickListener;


        public ItemHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.itemNameTextView);
            mAmountTextView = itemView.findViewById(R.id.itemAmountTextView);
            mOnItemClickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "TEST CLICK");
            mOnItemClickListener.onItemClick(getAdapterPosition());
        }
    }


}
