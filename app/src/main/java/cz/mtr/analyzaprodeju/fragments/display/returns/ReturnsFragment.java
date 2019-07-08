package cz.mtr.analyzaprodeju.fragments.display.returns;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cz.mtr.analyzaprodeju.Interfaces.OnInputSelected;
import cz.mtr.analyzaprodeju.Interfaces.OnItemClickListener;
import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.fragments.dialogs.DialogChangeFragment;
import cz.mtr.analyzaprodeju.fragments.display.adapter.PrinterItemAdapter;
import cz.mtr.analyzaprodeju.models.Model;

public class ReturnsFragment extends Fragment implements OnItemClickListener, OnInputSelected {

    private ReturnsViewModel mViewModel;
    private RecyclerView mPrinterRecyclerView;
    private PrinterItemAdapter mAdapter;
    private int mPosition;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        mPrinterRecyclerView = view.findViewById(R.id.recyclerView);
        mPrinterRecyclerView.setHasFixedSize(true);
        mPrinterRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new PrinterItemAdapter(this);
        mPrinterRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ReturnsViewModel.class);
        mAdapter.setPrinterItems(mViewModel.getReturns());
    }


    @Override
    public void onItemClick(int position) {
        mPosition = position;
        DialogChangeFragment dialog = new DialogChangeFragment();
        dialog.setTargetFragment(ReturnsFragment.this, 1);
        dialog.show(getFragmentManager(), "DialogChangeFragment");


    }

    @Override
    public void sendAmount(String amount) {
        mViewModel.changeAmount(mPosition, amount);
        mAdapter.setPrinterItems(mViewModel.getReturns());
        mAdapter.notifyItemChanged(mPosition);
    }

    @Override
    public void deleteItem() {
        mViewModel.delete(mPosition);
        mAdapter.setPrinterItems(mViewModel.getReturns());
        mAdapter.notifyItemChanged(mPosition);
    }


    public void deleteAllAndRefresh() {
        Model.getInstance().getReturns().clear();
        Model.getInstance().saveOrdersAndReturns();
        mAdapter.setPrinterItems(mViewModel.getReturns());
        mAdapter.notifyDataSetChanged();
    }
}
