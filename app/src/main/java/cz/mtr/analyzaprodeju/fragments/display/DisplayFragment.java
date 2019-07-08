package cz.mtr.analyzaprodeju.fragments.display;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.fragments.dialogs.DialogDeleteFragment;
import cz.mtr.analyzaprodeju.fragments.display.adapter.PrinterPageViewAdapter;
import cz.mtr.analyzaprodeju.fragments.display.orders.OrdersFragment;
import cz.mtr.analyzaprodeju.fragments.display.returns.ReturnsFragment;
import cz.mtr.analyzaprodeju.models.datastructures.DisplayableArticle;

public class DisplayFragment extends Fragment implements DialogDeleteFragment.OnDeleteConfirmed {

    private static final String TAG = DisplayableArticle.class.getSimpleName();


    private ViewPager mViewPager;
    private FloatingActionButton mDeleteFab;
    private TabLayout mTabLayout;
    private ReturnsFragment returnsFragment = new ReturnsFragment();
    private OrdersFragment ordersFragment = new OrdersFragment();


    public static DisplayFragment newInstance() {
        return new DisplayFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frament_display, container, false);

        mViewPager = view.findViewById(R.id.viewPager);
        setupViewPager(mViewPager);


        mTabLayout = view.findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);


        mDeleteFab = view.findViewById(R.id.deleteAllFloatingButton);
        mDeleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogDeleteFragment dialog = new DialogDeleteFragment();
                dialog.setTargetFragment(DisplayFragment.this, 1);
                dialog.show(getFragmentManager(), "DisplayFragment");


            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void setupViewPager(ViewPager viewPager) {
        PrinterPageViewAdapter adapter = new PrinterPageViewAdapter(getChildFragmentManager());
        adapter.addFragment(returnsFragment, "Vratka");
        adapter.addFragment(ordersFragment, "Objednavka");
        viewPager.setAdapter(adapter);
    }


    @Override
    public void deleteConfirmed() {
        if (mTabLayout.getSelectedTabPosition() == 0) {
            returnsFragment.deleteAllAndRefresh();
        } else if (mTabLayout.getSelectedTabPosition() == 1) {
            ordersFragment.deleteAllAndRefresh();
        }
    }
}
