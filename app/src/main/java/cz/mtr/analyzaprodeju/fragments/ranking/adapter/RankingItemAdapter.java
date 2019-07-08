package cz.mtr.analyzaprodeju.fragments.ranking.adapter;

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
import cz.mtr.analyzaprodeju.fragments.ranking.item.RankingItem;

public class RankingItemAdapter extends RecyclerView.Adapter<RankingItemAdapter.ItemHolder> {

    private List<RankingItem> rankingItems = new ArrayList<>();
    private OnItemClickListener mOnItemListener;


    public RankingItemAdapter(OnItemClickListener onItemClickListener) {
        mOnItemListener = onItemClickListener;
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking_list, parent, false);
        return new ItemHolder(itemView, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        RankingItem currentRankingItem = rankingItems.get(position);
        holder.mNameTextView.setText(currentRankingItem.getName());
        holder.mRankTextView.setText(currentRankingItem.getRank() + ".");
        holder.mRevenueTextView.setText(currentRankingItem.getRevenue() + " Kč");

    }

    @Override
    public int getItemCount() {
        return rankingItems.size();
    }

    public void setRankingItems(List<RankingItem> rankingItems) {
        this.rankingItems = rankingItems;
        notifyDataSetChanged();
    }


    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView, mRankTextView, mRevenueTextView;
        OnItemClickListener mOnItemListener;

        public ItemHolder(@NonNull View itemView, OnItemClickListener onItemListener) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.itemNameTextView);
            mRankTextView = itemView.findViewById(R.id.itemRankTextView);
            mRevenueTextView = itemView.findViewById(R.id.revenueTextView);
            this.mOnItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            mOnItemListener.onItemClick(getAdapterPosition());
        }
    }


}
