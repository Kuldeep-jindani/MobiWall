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
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.io.File;

import mobiwall.entwickler.pro.com.mobiwall.Bean.DrawerModel;
import mobiwall.entwickler.pro.com.mobiwall.firebase.Config;
import mobiwall.entwickler.pro.com.mobiwall.firebase.NotificationUtils;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage, txt;
    ImageView imageView, img_search;
    String App_ID = "ca-app-pub-8051557645259039/3121786353";
    String App_ID_Interstitialad = "ca-app-pub-8051557645259039/5056564996";
    AdView adView;
    ImageView edt_search, go, cancel_icon;
    InterstitialAd mInterstitialAd;
    FragmentTransaction fragmentTransaction;

    ActionBarDrawerToggle toggle;
    private boolean mToolBarNavigationListenerIsRegistered = false;

    private boolean loadFragment(Fragment fragment) {

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
    int search_bit = 0;
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

        mInterstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstial));

        AdRequest adRequest1 = new AdRequest.Builder()
                .build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest1);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {

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
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setSelectedItemId(R.id.navigation_daily);

        edt_search = findViewById(R.id.search_icon);
        cancel_icon = findViewById(R.id.cancel_icon);
        final EditText search_edTxt = findViewById(R.id.search);
        final LinearLayout search_layout = findViewById(R.id.search_layout);
        go = findViewById(R.id.go_icon);
        edt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_bit == 0) {
                   /* txt.setText("Search");
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack("Dashboard");
                    fragmentTransaction.replace(R.id.fragment_container, new SearchFragment()).commit();*/

                    Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left);
                    edt_search.setVisibility(View.GONE);
                    search_layout.setVisibility(View.VISIBLE);
                    search_layout.setAnimation(slideUp);


                    search_bit = 1;
                } else {
                    navigation.setSelectedItemId(R.id.navigation_daily);
                    search_bit = 0;
                }
            }
        });
        cancel_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_out_right);
                edt_search.setVisibility(View.VISIBLE);
                search_layout.setVisibility(View.GONE);
                search_layout.setAnimation(slideUp);
                navigation.setSelectedItemId(R.id.navigation_daily);
                search_bit = 0;
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("Dashboard");
                fragmentTransaction.replace(R.id.fragment_container, new SearchFragment(search_edTxt.getText().toString())).commit();
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
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        FirebaseMessaging.getInstance().subscribeToTopic("globle");

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
      /*  if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }*/
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
//                edt_search.setVisibility(View.VISIBLE);

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
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (position) {
                case 0:
                    txt.setText("Settings");
                    fragmentTransaction.addToBackStack("dashboard");
                    fragmentTransaction.replace(R.id.fragment_container, new Setting_Fragment()).commit();
                    enableViews(true);
                    break;

                case 1:
                    txt.setText("Favorite");
                    fragment = new Favorite_fragment();

//                    fragmentTransaction.replace(R.id.fragment_container, new Favorite_fragment()).commit();

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
//                    SharingToSocialMedia("com.facebook.katana");
                    Intent browserIntentfb = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/mobiwallapp/"));
                    startActivity(browserIntentfb);
                    break;

                case 5:
//                    SharingToSocialMedia("com.twitter.android");
                    Intent browserIntentin = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/mobiwall1"));
                    startActivity(browserIntentin);
                    break;

                case 6:
//                    SharingToSocialMedia("com.instagram.android");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/mobiwallapp/"));
                    startActivity(browserIntent);
                  /*  String type = "image/*";
                    String filename = "/myPhoto.jpg";
                    String mediaPath = Environment.getExternalStorageDirectory() + filename;



                    // Create the new Intent using the 'Send' action.
                    Intent share = new Intent(Intent.ACTION_SEND);

                    // Set the MIME type
                    share.setType(type);

                    // Create the URI from the media
                    File media = new File(mediaPath);
                    Uri ur = Uri.fromFile(media);

                    // Add the URI to the Intent.
                    share.putExtra(Intent.EXTRA_STREAM, ur);

                    // Broadcast the Intent.
                    startActivity(Intent.createChooser(share, "Share to"));*/

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
                        fragmentTransaction.addToBackStack("dashboard");
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

    private void enableViews(boolean enable) {

        // To keep states of ActionBar and ActionBarDrawerToggle synchronized,
        // when you enable on one, you disable on the other.
        // And as you may notice, the order for this operation is disable first, then enable - VERY VERY IMPORTANT.
        if (enable) {
//You may not want to open the drawer on swipe from the left in this case
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
// Remove hamburger
            toggle.setDrawerIndicatorEnabled(false);
            // Show back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // when DrawerToggle is disabled i.e. setDrawerIndicatorEnabled(false), navigation icon
            // clicks are disabled i.e. the UP button will not work.
            // We need to add a listener, as in below, so DrawerToggle will forward
            // click events to this listener.
            if (!mToolBarNavigationListenerIsRegistered) {
                toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Doesn't have to be onBackPressed
                        onBackPressed();
                        enableViews(false);

                    }
                });

                mToolBarNavigationListenerIsRegistered = true;
            }

        } else {
//You must regain the power of swipe for the drawer.
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

// Remove back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // Show hamburger
            toggle.setDrawerIndicatorEnabled(true);
            // Remove the/any drawer toggle listener
            toggle.setToolbarNavigationClickListener(null);
            mToolBarNavigationListenerIsRegistered = false;
        }

        // So, one may think "Hmm why not simplify to:
        // .....
        // getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
        // mDrawer.setDrawerIndicatorEnabled(!enable);
        // ......
        // To re-iterate, the order in which you enable and disable views IS important #dontSimplify.
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        showInterstitial();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() != 0) {
            showInterstitial();
        }
    }
}
