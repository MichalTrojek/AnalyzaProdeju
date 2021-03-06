package cz.mtr.analyzaprodeju.fragments.scraper.stores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cz.mtr.analyzaprodeju.R;

public class ScraperItemAdapter extends RecyclerView.Adapter<ScraperItemAdapter.ItemHolder> {
    public final static String TAG = ScraperItemAdapter.class.getSimpleName();
    private List<WebItem> displayItems = new ArrayList<>();


    public ScraperItemAdapter() {

    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scraper_stores_list, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        WebItem item = displayItems.get(position);
        holder.mNameTextView.setText(item.getName());
        holder.mStoreTextView.setText("Sklad1: "+item.getStore());
        holder.mStorePriceView.setText("Cena: " + item.getPrice());
        holder.mRegalTextView.setText("Regál: " + item.getRegal());
    }

    @Override
    public int getItemCount() {
        return displayItems.size();
    }

    public void setItems(List<WebItem> displayItems) {
        this.displayItems = displayItems;
        notifyDataSetChanged();
    }


    class ItemHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private TextView mStoreTextView;
        private TextView mStorePriceView;
        private TextView mRegalTextView;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.scaperNameTextView);
            mStoreTextView = itemView.findViewById(R.id.scraperStoreTextView);
            mRegalTextView = itemView.findViewById(R.id.scraperRegalTextView);
            mStorePriceView = itemView.findViewById(R.id.scraperPriceTextView);
        }
    }


}
