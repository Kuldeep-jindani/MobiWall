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


     /*   AdView adView = view.findViewById(R.id.adView);
        MobileAds.initialize(getContext(),"ca-app-pub-3940256099942544/6300978111");
        AdRequest adRequest = new AdRequest.Builder().build();
       adView.loadAd(adRequest);*/
/*
        nativeExpressAdView = view.findViewById(R.id.native_add);
        nativeExpress//adView.loadAd(new AdRequest.Builder().build());*/

/*
        MobileAds.initialize(getContext(),"ca-app-pub-3940256099942544/6300978111");
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("ca-app-pub-3940256099942544/6300978111").build();
        nativeExpress//adView.loadAd(adRequest);*/

        pullToLoadView = view.findViewById(R.id.wallpaper_grid);

        new Paginator(getContext(),pullToLoadView);/*
//        new Paginator_categoryimage(getContext(),pullToLoadView,"7").initializePagination();
       AdView adView = view.findViewById(R.id.adView);
        MobileAds.initialize(getContext(),"ca-app-pub-3940256099942544/6300978111");
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("ca-app-pub-3940256099942544/6300978111").build();
       adView.loadAd(adRequest);*/
        /*final AdLoader adLoader = new AdLoader.Builder(getContext(), "ca-app-pub-3940256099942544/2247696110")
                .forAppInstallAd(new NativeAppInstallAd.OnAppInstallAdLoadedListener() {
                    @Override
                    public void onAppInstallAdLoaded(NativeAppInstallAd appInstallAd) {


                    }

                })
                .forContentAd(new NativeContentAd.OnContentAdLoadedListener() {
                    @Override
                    public void onContentAdLoaded(NativeContentAd contentAd) {
                        // Show the content ad.
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();

        */



/*
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {

                    final InterstitialAd  mInterstitialAd = new InterstitialAd(getContext());

                    // set the ad unit ID
                    mInterstitialAd.setAdUnitId( getString(R.string.interstial));

                    AdRequest adRequest1 = new AdRequest.Builder()
                            .build();

                    // Load ads into Interstitial Ads
                    mInterstitialAd.loadAd(adRequest1);

                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {
                            mInterstitialAd.show();
                        }
                    });
                    return true;
                }
                return false;
            }
        });
*/

        return view;
    }



}




