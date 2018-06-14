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
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
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

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context c;
    JSONArray array;
    FragmentManager fragmentManager;
    ArrayList<Grid_model> grid_models;

    int TYPE_IMG = 1;
    int TYPE_AD = 0;

    private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    private static final String ADMOB_APP_ID = "ca-app-pub-8051557645259039~4576384540";

    public MyRecyclerViewAdapter(Context c, ArrayList<Grid_model> grid_models) {
        this.c = c;
        this.grid_models = grid_models;
    }

    public void clear() {
        grid_models.clear();
    }

    public void add(Grid_model grid_model) {
        grid_models.add(grid_model);
        notifyDataSetChanged();
    }

    public void addList(ArrayList<Grid_model> grid_models1) {
        for (int i = 0; i < grid_models1.size(); i++) {
            Grid_model grid_model = grid_models1.get(i);
            grid_models.add(grid_model);

        }
//        grid_models.
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Grid_model grid_model = grid_models.get(position);
        if (grid_model.getViewType() == 1) {
            return TYPE_AD;
        }
        return TYPE_IMG;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_AD) {
            LayoutInflater inflater = LayoutInflater.from(c);
            View view = inflater.inflate(R.layout.ad_unified, parent, false);
            return new MyRecyclerViewAdapter.AdViewHolder(view);
        }
        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.dailylist, parent, false);
        return new MyRecyclerViewAdapter.ImageViewHolder(view);

    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Grid_model grid_model = grid_models.get(position);
        if (getItemViewType(position) == TYPE_AD) {
            MobileAds.initialize(c, ADMOB_APP_ID);
            final AdViewHolder holder1 = (AdViewHolder) holder;

           /* holder1.refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View unusedView) {
                    refreshAd(holder1);
                }
            });*/

//            MobileAds.initialize(getContext(),"ca-app-pub-7796828333997958/4152584076");
           /* AdRequest adRequest = new AdRequest.Builder().addTestDevice("ca-app-pub-3940256099942544/6300978111").build();
            holder1.adView.loadAd(adRequest);*/
//            refreshAd(holder1);

            AdRequest request = new AdRequest.Builder()


                    .build();



            holder1.adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    holder1.adView.setVisibility(View.VISIBLE);
                }
            });
            holder1.adView.loadAd(request);
        } else if (getItemViewType(position) == TYPE_IMG) {
            ImageViewHolder holder1 = (ImageViewHolder) holder;
            Picasso.get().load(grid_model.getImg_url())
//                .placeholder(c.getResources().getColor(R.color.colorBlack))
                    .resize(300, 500)
//                .error(R.drawable.ic_launcher_background)
                    .into(holder1.imageView);


            holder1.textView.setText(grid_model.getLikes());
            holder1.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Preview_daily.class);
//                intent.putExtra("img_url", grid_model.getImg_url());

                    intent.putExtra("grid", grid_models);
                    intent.putExtra("pos", position);
                    c.startActivity(intent);
                }
            });

        }
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


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        LinearLayout gride_wallpaper;
        RelativeLayout ads_layout;
        FrameLayout image_layout;

        private ImageViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.img_gride);
            textView = v.findViewById(R.id.count);
//            ads_layout= v.findViewById(R.id.ads_layout);
            image_layout = v.findViewById(R.id.image_layout);
            gride_wallpaper = v.findViewById(R.id.gride_wallpaper);
        }

    }

    public class AdViewHolder extends RecyclerView.ViewHolder {
     /*   private Button refresh;
        private CheckBox startVideoAdsMuted;
        private TextView videoStatus;*/
//        FrameLayout frameLayout;

        NativeExpressAdView adView;
        private AdViewHolder(View v) {
            super(v);
          /*  refresh = v.findViewById(R.id.btn_refresh);
            startVideoAdsMuted = v.findViewById(R.id.cb_start_muted);
            videoStatus = v.findViewById(R.id.tv_video_status);*/
           /* frameLayout =
                    v.findViewById(R.id.gride_wallpaper);*/
            adView = v.findViewById(R.id.adView);
        }

    }

    /*private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView, final AdViewHolder holder) {
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
//                holder.refresh.setEnabled(true);
//                holder.videoStatus.setText("Video status: Video playback has ended.");
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
            *//*holder.videoStatus.setText(String.format(Locale.getDefault(),
                    "Video status: Ad contains a %.2f:1 video asset.",
                    vc.getAspectRatio()));*//*
        } else {
            adView.setImageView(mainImageView);
            mediaView.setVisibility(View.GONE);

            // At least one image is guaranteed.
            List<NativeAd.Image> images = nativeAd.getImages();
            mainImageView.setImageDrawable(images.get(0).getDrawable());

//            holder.refresh.setEnabled(true);
//            holder.videoStatus.setText("Video status: Ad does not contain a video asset.");
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
     */
   /* private void refreshAd(final AdViewHolder holder) {
//        holder.refresh.setEnabled(false);

        AdLoader.Builder builder = new AdLoader.Builder(c, ADMOB_AD_UNIT_ID);

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            // OnUnifiedNativeAdLoadedListener implementation.
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {


                UnifiedNativeAdView adView = (UnifiedNativeAdView) LayoutInflater.from(c)
                        .inflate(R.layout.ad_unified, null);
                populateUnifiedNativeAdView(unifiedNativeAd, adView,holder);
//               holder.frameLayout.removeAllViews();
//                holder.frameLayout.addView(adView);
            }

        });

        VideoOptions videoOptions = new VideoOptions.Builder()
//                .setStartMuted(holder.startVideoAdsMuted.isChecked())
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
//                holder.refresh.setEnabled(true);
                Toast.makeText(c, "Failed to load native ad: "
                        + errorCode, Toast.LENGTH_SHORT).show();
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());

//        holder.videoStatus.setText("");
    }*/


}
