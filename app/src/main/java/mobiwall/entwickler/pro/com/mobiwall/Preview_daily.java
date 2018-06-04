package mobiwall.entwickler.pro.com.mobiwall;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import mobiwall.entwickler.pro.com.mobiwall.paginator.Grid_model;

public class Preview_daily extends AppCompatActivity {
    Button btn_set;
    ImageView imageView;
    ViewPager viewPager;
    String App_ID = "ca-app-pub-3940256099942544/6300978111";
    //AdView adView;

    ImageView download, set_as_bcgrnd, unliked;

    int click = 0;

    InterstitialAd mInterstitialAd;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_preview_daily);
        viewPager = findViewById(R.id.view_pager);
        set_as_bcgrnd = findViewById(R.id.set_as_bcgrnd);
        unliked = findViewById(R.id.like);
        download = findViewById(R.id.download);


        Intent i = getIntent();
        final ArrayList<Grid_model> grid_models = (ArrayList<Grid_model>) i.getSerializableExtra("grid");
        final ImageAdapter adapter = new ImageAdapter(this, grid_models);


     /*   Grid_model grid_model=grid_models.get(i.getIntExtra("pos",0));

        if (grid_model.getIsmyfavourite().equals("0"))
            unliked.setImageDrawable(getDrawable(R.drawable.unliked));
else
            unliked.setImageDrawable(getDrawable(R.drawable.liked));
*/
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


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Grid_model grid_model = grid_models.get(viewPager.getCurrentItem());
                new DownloadFile(grid_model).execute(grid_model.getImg_url());
                Log.e("click bit", String.valueOf(click));
                if (click == 0) {
                    showInterstitial();
                    click++;
                }
            }
        });
        set_as_bcgrnd.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                Log.e("click bit", String.valueOf(click));
                if (click == 0) {
                    showInterstitial();
                    click++;
                }
                final Grid_model grid_model = grid_models.get(viewPager.getCurrentItem());
                final KProgressHUD hud = KProgressHUD.create(Preview_daily.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setCancellable(true)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show();
                new AsyncTask<Void, Void, ImageView>() {

                    protected void onPreExecute() {
                    }

                    protected ImageView doInBackground(Void... unused) {
                        WallpaperManager myWallpaperManager
                                = WallpaperManager.getInstance(getApplicationContext());
                        try {

                            Log.e("data", grid_model.getImg_url());
                            Bitmap result;
                            result = Picasso.get()
                                    .load(grid_model.getImg_url())
                                    .get();

                            myWallpaperManager.setBitmap(result);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        hud.dismiss();

                        return set_as_bcgrnd;
                    }

                    @Override
                    protected void onPostExecute(ImageView imageView) {
                        super.onPostExecute(imageView);
                        Toast.makeText(getApplicationContext(), "Image set as wallpaper.", Toast.LENGTH_SHORT).show();
                        Volley.newRequestQueue(getApplicationContext()).add(new StringRequest(Request.Method.POST, "http://themeelite.com/ananta/set_as_wallpaper?image_id=" + grid_model.getId() + "&device_id=" + Settings.Secure.getString(getContentResolver(),
                                Settings.Secure.ANDROID_ID), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Preview_daily.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                            }
                        }));
                    }
                }.execute();
            }
        });



        unliked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Preview_daily.this, "like clicked", Toast.LENGTH_SHORT).show();
                if (click == 0) {

                    showInterstitial();

                    click++;
                }
                Grid_model grid_model = grid_models.get(viewPager.getCurrentItem());
                if (grid_model.getIsmyfavourite().equalsIgnoreCase("0")){
                    unliked.setImageDrawable(getResources().getDrawable(R.drawable.liked));

                    like(grid_model,0);
                    grid_model.setismyfavourite("1");

                }
                else if (grid_model.getIsmyfavourite().equalsIgnoreCase("1")) {
                    unliked.setImageDrawable(getResources().getDrawable(R.drawable.unliked));
                    like(grid_model,1);
                    grid_model.setismyfavourite("0");
                }
            }
        });


        /*adView = findViewById(R.id.adView);
        MobileAds.initialize(this,App_ID);
        AdRequest adRequest = new AdRequest.Builder().build();
       adView.loadAd(adRequest);*/

        //viewPager = findViewById(R.id.img_preview);


        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(i.getIntExtra("pos", 0));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                Grid_model grid_model=grid_models.get(viewPager.getCurrentItem());
                if (grid_model.getIsmyfavourite().equalsIgnoreCase("0")){
                    unliked.setImageDrawable(getResources().getDrawable(R.drawable.unliked));

//                    like(grid_model,0);
                }
                else if (grid_model.getIsmyfavourite().equalsIgnoreCase("1")) {
                    unliked.setImageDrawable(getResources().getDrawable(R.drawable.liked));
//                    like(grid_model,1);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
/*
        AdView adView = findViewById(R.id.adView);
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-3940256099942544/6300978111");
        AdRequest adRequest = new AdRequest.Builder().build();
       adView.loadAd(adRequest);*/





       /* Picasso.get().load(getIntent().getStringExtra("img_url"))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into((Target) imageView);
        getIntent().getStringExtra("count");


        btn_set = findViewById(R.id.set_as_bcgrnd);
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final KProgressHUD hud = KProgressHUD.create(Preview_daily.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setCancellable(true)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show();

                new AsyncTask<Void, Void, Button>() {
                    protected void onPreExecute() {
                    }

                    protected Button doInBackground(Void... unused) {
                        WallpaperManager myWallpaperManager
                                = WallpaperManager.getInstance(getApplicationContext());
                        try {
                            Bitmap result;
                            result = Picasso.get()
                                    .load(getIntent().getStringExtra("img_url"))
                                    .get();

                            myWallpaperManager.setBitmap(result);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        hud.dismiss();

                        return btn_set;
                    }
                    protected void onPostExecute(Button unused) {
                        Toast.makeText(getApplicationContext(), "Image set as wallpaper.", Toast.LENGTH_SHORT).show();
                    }
                }.execute();
            }
        });*/
    }

    private void showInterstitial() {
      /*  if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }*/
    }

    public void like(Grid_model grid_model, int i) {

//        final String url="http://charmhdwallpapers.com/wallpaper/fav?favourite="+i+"&image_id="+grid_model.getId()+"&device_id="+ Settings.Secure.getString(this.getContentResolver(),
//                Settings.Secure.ANDROID_ID);

//        Toast.makeText(this, "in likes ", Toast.LENGTH_SHORT).show();
        final String url = "http://themeelite.com/ananta/likes?image_id="+ grid_model.getId()+"&device_id="+ Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID)+"&bit="+i;
        Volley.newRequestQueue(getApplicationContext()).add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("like url", url);
                Log.e("like response", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }


    class DownloadFile extends AsyncTask<String, Integer, Long> {
        //        ProgressDialog mProgressDialog = new ProgressDialog(ImageDetails.this);// Change Mainactivity.this with your activity name.
        String strFolderName;
        KProgressHUD hud;

        Grid_model grid_model;

        public DownloadFile(Grid_model grid_model) {
            this.grid_model = grid_model;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  mProgressDialog.setMessage("Downloading");
//            mProgressDialog.setMax(100);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.show(); */
            hud = KProgressHUD.create(Preview_daily.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setDetailsLabel("Downloading data")
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
        }

        @Override
        protected Long doInBackground(String... aurl) {
            int count;
            try {

                URL url = new URL((String) aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                String targetFileName = System.currentTimeMillis() + ".jpg";//Change name and subname
                int lenghtOfFile = conexion.getContentLength();
                String PATH = Environment.getExternalStorageDirectory() + "/MobiWall/";
                File folder = new File(PATH);

               /* File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "MyDirName");

                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {
                        Log.d("App", "failed to create directory");
                    }
                }
                */


                if (!folder.exists()) {
                    folder.mkdir();//If there is no folder it will be created.
                }
                Log.e("url", aurl[0]);
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(PATH + targetFileName);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress((int) (total * 100 / lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
//                mProgressDialog.dismiss();
                hud.dismiss();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            hud.dismiss();
            Toast.makeText(getApplicationContext(), "Image Downloaded", Toast.LENGTH_SHORT).show();
            Volley.newRequestQueue(getApplicationContext()).add(new StringRequest(Request.Method.POST, "http://themeelite.com/ananta/download_count?image_id=" + grid_model.getId() + "&device_id=" + Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Preview_daily.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                }
            }));
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}

