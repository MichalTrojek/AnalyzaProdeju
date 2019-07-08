package cz.mtr.analyzaprodeju.fragments.display.adapter;

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
import cz.mtr.analyzaprodeju.fragments.display.other.DisplayItem;

public class PrinterItemAdapter extends RecyclerView.Adapter<PrinterItemAdapter.ItemHolder> {
    public final static String TAG = PrinterItemAdapter.class.getSimpleName();
    private List<DisplayItem> displayItems = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;


    public PrinterItemAdapter(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_display_list, parent, false);
        return new ItemHolder(itemView, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        DisplayItem item = displayItems.get(position);
        holder.mNameTextView.setText(item.getName());
        holder.mAmountTextView.setText(item.getAmount() + "ks");
    }

    @Override
    public int getItemCount() {
        return displayItems.size();
    }

    public void setDisplayItems(List<DisplayItem> displayItems) {
        this.displayItems = displayItems;
        notifyDataSetChanged();
    }


    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private TextView mAmountTextView;
        private TextView mRevenueTextView;
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
            mOnItemClickListener.onItemClick(getAdapterPosition());
        }
    }


}
