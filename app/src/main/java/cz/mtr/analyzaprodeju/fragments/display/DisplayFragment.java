package cz.mtr.analyzaprodeju.fragments.display;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.fragments.dialogs.DialogDeleteFragment;
import cz.mtr.analyzaprodeju.fragments.dialogs.DialogLoadingFragment;
import cz.mtr.analyzaprodeju.fragments.display.adapter.PrinterPageViewAdapter;
import cz.mtr.analyzaprodeju.fragments.display.orders.OrdersFragment;
import cz.mtr.analyzaprodeju.fragments.display.returns.ReturnsFragment;
import cz.mtr.analyzaprodeju.fragments.home.HomeFragmentDirections;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.models.datastructures.DisplayableArticle;

public class DisplayFragment extends Fragment implements DialogDeleteFragment.OnDeleteConfirmed {

    private static final String TAG = DisplayableArticle.class.getSimpleName();


    private ViewPager mViewPager;
    private FloatingActionButton mDeleteFab, mPrintFab, mExportFab;
    private TabLayout mTabLayout;
    private ReturnsFragment returnsFragment = new ReturnsFragment();
    private OrdersFragment ordersFragment = new OrdersFragment();
    private DisplayViewModel mViewModel;
    private boolean areOrdersEmpty, areReturnsEmpty;


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
        setupFloatingActionButtons(view);
        switchTabToOrdersIfReturnsAreEmpty();
        return view;
    }

    private void setupFloatingActionButtons(View view) {
        mDeleteFab = view.findViewById(R.id.deleteAllFloatingButton);
        mDeleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogDeleteFragment dialog = new DialogDeleteFragment();
                dialog.setTargetFragment(DisplayFragment.this, 1);
                dialog.show(getFragmentManager(), "DisplayFragment");
            }
        });

        mPrintFab = view.findViewById(R.id.printerFob);
        mPrintFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.print(mTabLayout.getSelectedTabPosition());
            }
        });

        mExportFab = view.findViewById(R.id.exportFab);
        mExportFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogLoadingFragment loadingDialog = new DialogLoadingFragment();
                loadingDialog.setCancelable(false);
                loadingDialog.show(getFragmentManager(), "FragmentChangeDialog");
                mViewModel.export(mTabLayout.getSelectedTabPosition(), loadingDialog);
            }
        });
    }

    private void switchTabToOrdersIfReturnsAreEmpty() {
        if (Model.getInstance().getReturns().isEmpty()) {
            mViewPager.setCurrentItem(1);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DisplayViewModel.class);
        mViewModel.getOrderAndReturnsStatus().observe(getViewLifecycleOwner(), new Observer<Boolean[]>() {
            @Override
            public void onChanged(Boolean[] booleans) {
                int position = mTabLayout.getSelectedTabPosition();
                areReturnsEmpty = booleans[0];
                areOrdersEmpty = booleans[1];
                showOrHideButtons(position);
                handleTabSwitchtingAndLeavingFragment();
            }
        });
    }

    private void handleTabSwitchtingAndLeavingFragment() {
        goToHomeFragment();
        if (areOrdersEmpty) {
            mViewPager.setCurrentItem(0);
        } else if (areReturnsEmpty) {
            mViewPager.setCurrentItem(1);
        }
    }

    private void goToHomeFragment() {
        if (areReturnsEmpty && areOrdersEmpty) {
            Navigation.findNavController(getView()).navigate(HomeFragmentDirections.toHome());
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        PrinterPageViewAdapter adapter = new PrinterPageViewAdapter(getChildFragmentManager());
        adapter.addFragment(returnsFragment, "Vratka");
        adapter.addFragment(ordersFragment, "Objednavka");
        viewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                showOrHideButtons(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void showOrHideButtons(int position) {
        if (position == 0 && areReturnsEmpty) {
            hideButtons();
        } else if (position == 1 && areOrdersEmpty) {
            hideButtons();
        } else {
            showButtons();
        }
    }

    private void hideButtons() {
        mPrintFab.hide();
        mDeleteFab.hide();
        mExportFab.hide();
    }

    private void showButtons() {
        mPrintFab.show();
        mDeleteFab.show();
        mExportFab.show();
    }



    @Override
    public void deleteConfirmed() {
        int position = mTabLayout.getSelectedTabPosition();
        if (position == 0) {
            returnsFragment.deleteAllAndRefresh();
        } else if (position == 1) {
            ordersFragment.deleteAllAndRefresh();
        }
    }


}
