package cz.mtr.analyzaprodeju.fragments.ranking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.fragments.ranking.other.rankingItem;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

    private List<rankingItem> rankingItems = new ArrayList<>();

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        rankingItem currentRankingItem = rankingItems.get(position);
        holder.mNameTextView.setText(currentRankingItem.getName());
        holder.mRankTextView.setText(currentRankingItem.getRank());

    }

    @Override
    public int getItemCount() {
        return rankingItems.size();
    }

    public void setRankingItems(List<rankingItem> rankingItems) {
        this.rankingItems = rankingItems;
        notifyDataSetChanged();
    }


    class ItemHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private TextView mRankTextView;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.itemNameTextView);
            mRankTextView = itemView.findViewById(R.id.itemRankTextView);

        }
    }


}
