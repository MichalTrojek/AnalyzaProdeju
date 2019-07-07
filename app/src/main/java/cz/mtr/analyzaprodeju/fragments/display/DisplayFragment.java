package cz.mtr.analyzaprodeju.fragments.display;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.fragments.display.adapter.PrinterPageViewAdapter;
import cz.mtr.analyzaprodeju.fragments.display.orders.OrdersFragment;
import cz.mtr.analyzaprodeju.fragments.display.returns.ReturnsFragment;

public class DisplayFragment extends Fragment {


    private ViewPager mViewPager;


    public static DisplayFragment newInstance() {
        return new DisplayFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frament_printer, container, false);

        mViewPager = view.findViewById(R.id.viewPager);
        setupViewPager(mViewPager);


        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    private void setupViewPager(ViewPager viewPager) {
        PrinterPageViewAdapter adapter = new PrinterPageViewAdapter(getChildFragmentManager());
        adapter.addFragment(new ReturnsFragment(), "Vratka");
        adapter.addFragment(new OrdersFragment(), "Objednavka");
        viewPager.setAdapter(adapter);
    }


}
