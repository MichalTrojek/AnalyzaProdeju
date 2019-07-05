package cz.mtr.analyzaprodeju.fragments.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.repository.room.ItemFts;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.ItemHolder> {
    public final static String TAG = SearchItemAdapter.class.getSimpleName();
    private List<ItemFts> mSearchItems = new ArrayList<>();

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        ItemFts item = mSearchItems.get(position);
        holder.mNameTextView.setText(item.getName());
        holder.mEanTextView.setText(item.getEan());


    }

    @Override
    public int getItemCount() {
        return mSearchItems.size();
    }

    public void setSearchItems(List<ItemFts> searchItems) {
        this.mSearchItems = searchItems;
        notifyDataSetChanged();
    }


    class ItemHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private TextView mEanTextView;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.itemNameTextView);
            mEanTextView = itemView.findViewById(R.id.itemEanTextView);

        }
    }


}
