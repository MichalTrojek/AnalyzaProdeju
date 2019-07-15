package cz.mtr.analyzaprodeju.fragments.scraper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.fragments.scraper.stores.ScraperStoreFragment;
import cz.mtr.analyzaprodeju.fragments.scraper.suppliers.ScraperSuppliersFragment;

public class ScraperFragment extends Fragment {

    private ScraperViewModel mViewModel;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;


    public ScraperFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scraper, container, false);

        mViewPager = view.findViewById(R.id.scraperViewPager);
        setupViewPager(mViewPager);


        mTabLayout = view.findViewById(R.id.scraperTabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ScraperPageViewAdapter adapter = new ScraperPageViewAdapter(getChildFragmentManager());
        adapter.addFragment(new ScraperStoreFragment(), "Prodejny");
        adapter.addFragment(new ScraperSuppliersFragment(), "Dodavatelé");
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ScraperViewModel.class);

    }


}
