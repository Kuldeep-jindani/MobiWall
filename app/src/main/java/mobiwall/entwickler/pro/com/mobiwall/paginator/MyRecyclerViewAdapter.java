package mobiwall.entwickler.pro.com.mobiwall.paginator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mobiwall.entwickler.pro.com.mobiwall.MainActivity;
import mobiwall.entwickler.pro.com.mobiwall.Preview_daily;
import mobiwall.entwickler.pro.com.mobiwall.R;


/**
 * Created by Payal on 4/9/2018.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>  {

    private Context c;
    JSONArray array;
    FragmentManager fragmentManager;
    ArrayList<Grid_model> grid_models;


    public MyRecyclerViewAdapter(Context c, ArrayList<Grid_model> grid_models) {
        this.c = c;
        this.grid_models=grid_models;
    }

    public  void  clear()
    {
        grid_models.clear();
    }
    public  void add(Grid_model grid_model)
    {
        grid_models.add(grid_model);
        notifyDataSetChanged();
    }

    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.dailylist, parent, false);
        return new MyRecyclerViewAdapter.ViewHolder(view);

    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull final MyRecyclerViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Grid_model grid_model = grid_models.get(position);
        Picasso.get().load(grid_model.getImg_url())
//                .placeholder(c.getResources().getColor(R.color.colorBlack))
                .resize(300,500)
//                .error(R.drawable.ic_launcher_background)
                .into(holder.imageView);





        holder.textView.setText(grid_model.getLikes());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Preview_daily.class);
//                intent.putExtra("img_url", grid_model.getImg_url());

                intent.putExtra("grid",grid_models);
                intent.putExtra("pos",position);
                c.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return grid_models.size();
    }

/*
    @Override
    public int getCount() {
        return grid_models.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);

        }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(c);
        int padding = c.getResources().getDimensionPixelSize(R.dimen.padding_medium);
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
       // imageView.setImageResource(grid_models[position]);
        imageView.setImageResource(position);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
*/




    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        CardView gride_wallpaper;

        private ViewHolder(View v){
            super(v);
            imageView = v.findViewById(R.id.img_gride);
            textView = v.findViewById(R.id.count);
            gride_wallpaper = v.findViewById(R.id.gride_wallpaper);
        }

    }
    private Button refresh;
    private CheckBox startVideoAdsMuted;
    private TextView videoStatus;
   /* private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getVideoController();

        // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
        // VideoController will call methods on this object when events occur in the video
        // lifecycle.
        vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
            public void onVideoEnd() {
                // Publishers should allow native ads to complete video playback before refreshing
                // or replacing them with another ad in the same UI location.
                refresh.setEnabled(true);
                videoStatus.setText("Video status: Video playback has ended.");
                super.onVideoEnd();
            }
        });

        MediaView mediaView = adView.findViewById(R.id.ad_media);
        ImageView mainImageView = adView.findViewById(R.id.ad_image);

        // Apps can check the VideoController's hasVideoContent property to determine if the
        // NativeAppInstallAd has a video asset.
        if (vc.hasVideoContent()) {
            adView.setMediaView(mediaView);
            mainImageView.setVisibility(View.GONE);
            videoStatus.setText(String.format(Locale.getDefault(),
                    "Video status: Ad contains a %.2f:1 video asset.",
                    vc.getAspectRatio()));
        } else {
            adView.setImageView(mainImageView);
            mediaView.setVisibility(View.GONE);

            // At least one image is guaranteed.
            List<NativeAd.Image> images = nativeAd.getImages();
            mainImageView.setImageDrawable(images.get(0).getDrawable());

            refresh.setEnabled(true);
            videoStatus.setText("Video status: Ad does not contain a video asset.");
        }

        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // Some assets are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);
    }*/

    /**
     * Creates a request for a new native ad based on the boolean parameters and calls the
     * corresponding "populate" method when one is successfully returned.
     *
     */
  /*  private void refreshAd() {
        refresh.setEnabled(false);

        AdLoader.Builder builder = new AdLoader.Builder(c, "ca-app-pub-3940256099942544/2247696110");

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            // OnUnifiedNativeAdLoadedListener implementation.
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                ViewHolder viewHolder=new ViewHolder()
                FrameLayout frameLayout =
                        findViewById(R.id.gride_wallpaper);
                UnifiedNativeAdView adView = (UnifiedNativeAdView) LayoutInflater.from(c)
                        .inflate(R.layout.ad_unified, null);
                populateUnifiedNativeAdView(unifiedNativeAd, adView);
               frameLayout.removeAllViews();
               frameLayout.addView(adView);
            }

        });

        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(startVideoAdsMuted.isChecked())
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                refresh.setEnabled(true);
                Toast.makeText(c, "Failed to load native ad: "
                        + errorCode, Toast.LENGTH_SHORT).show();
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());

        videoStatus.setText("");
    }*/

}
