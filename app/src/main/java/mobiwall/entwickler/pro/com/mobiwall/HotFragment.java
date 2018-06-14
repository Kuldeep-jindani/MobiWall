package mobiwall.entwickler.pro.com.mobiwall;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mobiwall.entwickler.pro.com.mobiwall.paginator.Grid_model;
import mobiwall.entwickler.pro.com.mobiwall.paginator.MyRecyclerViewAdapter;
import mobiwall.entwickler.pro.com.mobiwall.paginator.Paginator;
import mobiwall.entwickler.pro.com.mobiwall.paginator.Paginator_popular;
import mobiwall.entwickler.pro.com.mobiwall.paginator.Paginator_search;
import mobiwall.entwickler.pro.com.mobiwall.paginator_hot.Paginator_hot;


/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends Fragment {
    PullToLoadView pullToLoadView;


    public HotFragment() {
        // Required empty public constructor
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
//           apiPopular();
            new Paginator_popular(getContext(),pullToLoadView);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hot, container, false);

        pullToLoadView = view.findViewById(R.id.wallpaper_grid_hot);

        new Paginator_popular(getContext(),pullToLoadView);

//apiPopular();

       /* AdView adView = view.findViewById(R.id.adView);
        MobileAds.initialize(getContext(),"ca-app-pub-7796828333997958/4152584076");
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("ca-app-pub-7796828333997958/4152584076").build();
        adView.loadAd(adRequest);*/
        AdView adView = view.findViewById(R.id.adView);
        MobileAds.initialize(getContext(),"ca-app-pub-3940256099942544/6300978111");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        return  view;
    }

   /* public void apiPopular(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        @SuppressLint("HardwareIds") //String URL="http://www.charmhdwallpapers.com/wallpaper/imagelist?page_size=20&last_item_id=0&device_uid=c699fde86c24b1c&category_id=&category_type=Daily&color_code=&count=0";

                String URL = "";

        URL = "http://themeelite.com/ananta/popular_images?device_id=" + Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.e("Grid service url", URL);

        final ArrayList<Grid_model> grid_models=new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("page IN ASYNC TASK hot", response);
                    JSONObject jsonObject = new JSONObject(response);

                        JSONArray array = jsonObject.getJSONArray("data");

                    if (array.length()>0) {

                        for (int i = 0; i <= array.length(); i++) {
                            if (i == array.length()) {
                                Grid_model grid_model = new Grid_model();
                                grid_model.setViewType(1);
                                grid_models.add(grid_model);

                            } else {
                                JSONObject o = (JSONObject) array.get(i);
                                Grid_model grid_model = new Grid_model();
                                grid_model.setId(o.getInt("id"));
                                grid_model.setimg_url("http://themeelite.com/ananta/public/uploads/" + o.getString("photo"));
                                   *//* grid_model.setcategory_id(o.getString("category_id"));
                                    grid_model.setfavourite_no(o.getString("favourite_no"));
                                    grid_model.settype(o.getString("type"));*//*
                                grid_model.setismyfavourite(o.getString("isLiked"));
                                grid_model.setLikes(o.getString("likes"));

                                grid_models.add(grid_model);

                            }
                        }
                    }
                    int count=array.length()/20;
                    final GridLayoutManager layoutManager=new GridLayoutManager(getContext(), 2);
                    pullToLoadView.setLayoutManager(layoutManager);

                    final MyRecyclerViewAdapter  adapter = new MyRecyclerViewAdapter(getContext(),grid_models);
                    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            switch (adapter.getItemViewType(position)) {
                                case 1:
                                    return 1;
                                case 0:
                                    return layoutManager.getSpanCount();
                                default:
                                    return -1;
                            }
                        }
                    });
pullToLoadView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(stringRequest);
    }*/

}
