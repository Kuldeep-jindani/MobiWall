package mobiwall.entwickler.pro.com.mobiwall;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.messaging.FirebaseMessaging;

import mobiwall.entwickler.pro.com.mobiwall.Bean.DrawerModel;
import mobiwall.entwickler.pro.com.mobiwall.firebase.Config;
import mobiwall.entwickler.pro.com.mobiwall.firebase.NotificationUtils;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,NavigationView.OnNavigationItemSelectedListener {

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

    DrawerLayout drawer;
    private Handler mHandler;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

      /*  mInterstitialAd = new InterstitialAd(this);

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
        });*/



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
//                txt.setText("Search");
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



        DrawerModel[] drawerItem = new DrawerModel[7];
        drawerItem[0] = new DrawerModel(R.drawable.setting, "Settings");
        drawerItem[1] = new DrawerModel(R.drawable.heart, "Favourite");
        drawerItem[2] = new DrawerModel(R.drawable.like, "Rate the app");
        drawerItem[3] = new DrawerModel(R.drawable.share, "Share this app");
        drawerItem[4] = new DrawerModel(R.drawable.facebook, "Follow us on Facebook");
        drawerItem[5] = new DrawerModel(R.drawable.twitter, "Follow us on Twitter");
        drawerItem[6] = new DrawerModel(R.drawable.insta, "Follow us on Instagram");
        mHandler = new Handler();


        ListView listView = findViewById(R.id.nav_listview);
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.nav_textview, drawerItem);
//        ArrayAdapter arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.nav_textview,nav_list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new DrawerItemClickListener());


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                }
            }
        };

        displayFirebaseRegId();

    }
    private FrameLayout viewPager;
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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

        public void selectItem(int position) {

            Fragment fragment = null;
            switch (position) {
                case 0:
                    break;

                case 1:
                    break;

                case 2:
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    // To count with Play market backstack, After pressing back button,
                    // to taken back to our application, we need to add following flags to intent.
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    try {
                        startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                    }
                    break;

                case 3:

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Hey! Check this my app at:");
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "https://play.google.com/store/apps/details?id=" + getPackageName());
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                    break;
                case 4:
                    SharingToSocialMedia("com.facebook.katana");
                    break;

                case 5:
                    SharingToSocialMedia("com.twitter.android");
                    break;

                case 6:
                    SharingToSocialMedia("com.instagram.android");
                    break;

                case 7:
                    break;
            }
            if (fragment != null) {


                final Fragment finalFragment = fragment;
                Runnable mPendingRunnable = new Runnable() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void run() {
                        // update the main content by replacing fragments
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.set_out_right, R.anim.set_out_right);
                        fragmentTransaction.replace(R.id.fragment_container, finalFragment);
//
                        fragmentTransaction.commitAllowingStateLoss();
                    }
                };

                // If mPendingRunnable is not null, then add to the message queue
                if (mPendingRunnable != null) {
                    mHandler.post(mPendingRunnable);
                }


                drawer.closeDrawers();

                invalidateOptionsMenu();

//                setTitle(mNavigationDrawerItemTitles[position]);

            } else {
                Log.e("MainActivity", "Error in creating fragment");
            }
        }
    }

    public void SharingToSocialMedia(String application) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, "https://play.google.com/store/apps/details?id=com.project.netflix.netflix123");

        boolean installed = checkAppInstall(application);
        if (installed) {
            intent.setPackage(application);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            try {
                getApplicationContext().startActivity(intent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.this, "Install application first", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(),
                    "Installed application first", Toast.LENGTH_LONG).show();
        }

    }


    private boolean checkAppInstall(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e("MAin Activity", "Firebase reg id: " + regId);

       /* if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
