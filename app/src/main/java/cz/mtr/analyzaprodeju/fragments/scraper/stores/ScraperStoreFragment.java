package cz.mtr.analyzaprodeju.fragments.scraper.stores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import cz.mtr.analyzaprodeju.R;
import cz.mtr.analyzaprodeju.fragments.dialogs.DialogLargerImage;
import cz.mtr.analyzaprodeju.fragments.scraper.ScraperViewModel;
import cz.mtr.analyzaprodeju.models.Model;

public class ScraperStoreFragment extends Fragment {

    private static final String TAG = ScraperStoreFragment.class.getSimpleName();

    private ScraperViewModel mViewModel;
    private RecyclerView mPrinterRecyclerView;
    private ScraperItemAdapter mAdapter;
    private ImageView mImageView;
    private TextView mTitleNameTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scraper_store, container, false);
        mTitleNameTextView = view.findViewById(R.id.titleNameTextView);
        mImageView = view.findViewById(R.id.itemPreview);
        mPrinterRecyclerView = view.findViewById(R.id.scraperStoreRecyclerView);
        mPrinterRecyclerView.setHasFixedSize(true);
        mPrinterRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ScraperItemAdapter();
        mPrinterRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ScraperViewModel.class);
        mAdapter.setItems(mViewModel.getItems());
        Picasso.get().load(Model.getInstance().getImageLink()).into(mImageView);
        mTitleNameTextView.setText(Model.getInstance().getTitleName());


        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogLargerImage dialog = new DialogLargerImage();
                dialog.setCancelable(true);
                dialog.show(getFragmentManager(), "DialogLargerImage");

            }
        });
    }


}
