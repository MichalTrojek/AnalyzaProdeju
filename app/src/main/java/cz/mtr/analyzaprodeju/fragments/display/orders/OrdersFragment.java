package cz.mtr.analyzaprodeju.fragments.display.orders;

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

import cz.mtr.analyzaprodeju.Interfaces.OnItemClickListener;
import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.fragments.display.adapter.PrinterItemAdapter;
import cz.mtr.analyzaprodeju.fragments.display.dialog.FragmentChangeDialog;

public class OrdersFragment extends Fragment implements OnItemClickListener, FragmentChangeDialog.OnInputSelected {

    private static final String TAG = OrdersFragment.class.getSimpleName();

    private OrdersViewModel mViewModel;
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
        mViewModel = ViewModelProviders.of(this).get(OrdersViewModel.class);
        mAdapter.setPrinterItems(mViewModel.getOrders());
    }


    @Override
    public void onItemClick(int position) {
        mPosition = position;
        FragmentChangeDialog dialog = new FragmentChangeDialog();
        dialog.setTargetFragment(OrdersFragment.this, 1);
        dialog.show(getFragmentManager(), "FragmentChangeDialog");


    }

    @Override
    public void sendAmount(String amount) {
        mViewModel.changeAmount(mPosition, amount);
        mAdapter.setPrinterItems(mViewModel.getOrders());
        mAdapter.notifyItemChanged(mPosition);
    }

    @Override
    public void deleteItem() {
        mViewModel.delete(mPosition);
        mAdapter.setPrinterItems(mViewModel.getOrders());
        mAdapter.notifyItemChanged(mPosition);
    }
}
