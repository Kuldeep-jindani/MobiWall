package mobiwall.entwickler.pro.com.mobiwall;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage,txt;
    ImageView imageView,img_search;
    String App_ID = "ca-app-pub-3940256099942544/6300978111";
    String App_ID_Interstitialad = "ca-app-pub-3940256099942544/1033173712";
    AdView adView;
    ImageView edt_search;
    InterstitialAd mInterstitialAd;
    FragmentTransaction fragmentTransaction;
    private boolean  loadFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          /*  Window window = getWindow();
            Drawable background = getResources().getColor(R.color.colorAccent);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);*/

            Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setContentView(R.layout.activity_main);
       /* adView = findViewById(R.id.adView);
        MobileAds.initialize(this,App_ID);
        AdRequest adRequest = new AdRequest.Builder().build();
       adView.loadAd(adRequest);*/

/*
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(App_ID_Interstitialad);
        interstitialAd.loadAd(new AdRequest.Builder().build());
*/



        mInterstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId( getString(R.string.interstial));

        AdRequest adRequest1 = new AdRequest.Builder()
                .build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest1);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });



        txt = findViewById(R.id.txt_daily);
        img_search = findViewById(R.id.search_bar);

        /*img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);

            }
        });*/

        edt_search = findViewById(R.id.search);
        edt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("Dashboard");
                fragmentTransaction.replace(R.id.fragment_container, new SearchFragment()).commit();
            }
        });
        imageView = findViewById(R.id.seting_vector);

        imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
//        Intent intent =  new Intent(MainActivity.this,SettingsActivity.class);
//        startActivity(intent);
    }
});
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setSelectedItemId(R.id.navigation_daily);

    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
             mInterstitialAd.show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            /*
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
           // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);*/

        //   getWindow().setStatusBarColor(Color.TRANSPARENT);// SDK21
        switch (item.getItemId()) {
            case R.id.navigation_daily:
                fragment = new DailyFragment();
                txt.setText("Recent");
                fragmentTransaction.replace(R.id.fragment_container, new DailyFragment()).commit();
                break;

          case R.id.navigation_hot:
                fragment = new HotFragment();
                txt.setText("Popular");
                    fragmentTransaction.replace(R.id.fragment_container, new HotFragment()).commit();
                break;




              case R.id.navigation_categories:
                fragment = new CategoriesFragment();
                txt.setText("Category");
                fragmentTransaction.replace(R.id.fragment_container, new CategoriesFragment()).commit();
                break;


        }
        return loadFragment(fragment);
    }
}
