package mobiwall.entwickler.pro.com.mobiwall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.srx.widget.PullToLoadView;

import mobiwall.entwickler.pro.com.mobiwall.paginator_categoryimage.Paginator_categoryimage;

public class FeatureImageGrid extends Fragment {

    PullToLoadView pullToLoadView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.feature_image_grid, container, false);

        pullToLoadView = view.findViewById(R.id.linear_feature_image_grid);


        Bundle b=getArguments();
        String id=b.getString("name");

//        new Paginator(getContext(),pullToLoadView).initializePagination();

        new Paginator_categoryimage(getContext(),pullToLoadView,id);

       /* AdView adView = view.findViewById(R.id.adView);
        MobileAds.initialize(getContext(),"ca-app-pub-7796828333997958/4152584076");
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("ca-app-pub-7796828333997958/4152584076").build();
       adView.loadAd(adRequest);*/

        AdView adView = view.findViewById(R.id.adView);
        MobileAds.initialize(getContext(),"ca-app-pub-3940256099942544/6300978111");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        return view;
    }
}
