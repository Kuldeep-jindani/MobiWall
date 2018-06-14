package mobiwall.entwickler.pro.com.mobiwall;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.srx.widget.PullToLoadView;

import java.util.Objects;

import mobiwall.entwickler.pro.com.mobiwall.paginator.Paginator;


/**
 * A simple {@link Fragment} subclass.
 */
public class DailyFragment extends Fragment {
PullToLoadView pullToLoadView;
 NativeExpressAdView nativeExpressAdView;


    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            new Paginator(getContext(),pullToLoadView);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveedInstanceState) {
        // Inflate the layout for this fragmnt
        View view = inflater.inflate(R.layout.fragment_daily, container, false);


        AdView adView = view.findViewById(R.id.adView);
        MobileAds.initialize(getContext(),"ca-app-pub-3940256099942544/6300978111");
        AdRequest adRequest = new AdRequest.Builder().build();
       adView.loadAd(adRequest);


        pullToLoadView = view.findViewById(R.id.wallpaper_grid);

        new Paginator(getContext(),pullToLoadView);

        return view;
    }



}




