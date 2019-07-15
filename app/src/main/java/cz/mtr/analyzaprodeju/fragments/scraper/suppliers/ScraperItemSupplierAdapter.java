package cz.mtr.analyzaprodeju.fragments.scraper.suppliers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cz.mtr.analyzaprodeju.R;

public class ScraperItemSupplierAdapter extends RecyclerView.Adapter<ScraperItemSupplierAdapter.ItemHolder> {
    public final static String TAG = ScraperItemSupplierAdapter.class.getSimpleName();
    private List<WebItemSuppliers> displayItems = new ArrayList<>();


    public ScraperItemSupplierAdapter() {

    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scraper_suppliers_list, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        WebItemSuppliers item = displayItems.get(position);
        holder.mNameTextView.setText(item.getName());
        holder.mScraperAvailability.setText(item.getAvailability());
        holder.mPriceTextView.setText(item.getPrice());
        holder.mDateTextView.setText(item.getDate());

    }

    @Override
    public int getItemCount() {
        return displayItems.size();
    }

    public void setItems(List<WebItemSuppliers> displayItems) {
        this.displayItems = displayItems;
        notifyDataSetChanged();
    }


    class ItemHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private TextView mScraperAvailability;
        private TextView mPriceTextView;
        private TextView mDateTextView;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.scaperNameTextView);
            mScraperAvailability = itemView.findViewById(R.id.scraperSupplierTextView);
            mPriceTextView = itemView.findViewById(R.id.scraperStorePriceTextView);
            mDateTextView = itemView.findViewById(R.id.scraperDateTextView);

        }
    }


}
