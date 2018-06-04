package mobiwall.entwickler.pro.com.mobiwall;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mobiwall.entwickler.pro.com.mobiwall.paginator.Grid_model;
import mobiwall.entwickler.pro.com.mobiwall.paginator.MyRecyclerViewAdapter;
import mobiwall.entwickler.pro.com.mobiwall.paginator.Paginator;
import mobiwall.entwickler.pro.com.mobiwall.paginator.Paginator_favorite;

/**
 * Created by Kuldeep_jindani on 5/27/2018.
 */

public class Favorite_fragment extends Fragment {

    RecyclerView pullToLoadView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_favorite,container,false);

        pullToLoadView = v.findViewById(R.id.wallpaper_grid_fav);

        pullToLoadView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        final ArrayList<Grid_model> grid_models=new ArrayList<>();
//        new Paginator_favorite(getContext(),pullToLoadView);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        @SuppressLint("HardwareIds") //String URL="http://www.charmhdwallpapers.com/wallpaper/imagelist?page_size=20&last_item_id=0&device_uid=c699fde86c24b1c&category_id=&category_type=Daily&color_code=&count=0";

                String URL = "";
        URL = "http://themeelite.com/ananta/list_of_favourites?"+ "device_id=" + Settings.Secure.getString(getContext().getContentResolver(),

                Settings.Secure.ANDROID_ID);
        Log.e("Grid service url", URL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("IN ASYNC TASK fav", response);
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("list_favourite");

                    if (array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject o = (JSONObject) array.get(i);
                            Grid_model grid_model = new Grid_model();
                            grid_model.setId(o.getInt("id"));
                            grid_model.setimg_url("http://themeelite.com/ananta/public/uploads/" + o.getString("photo"));
                                   /* grid_model.setcategory_id(o.getString("category_id"));
                                    grid_model.setfavourite_no(o.getString("favourite_no"));
                                    grid_model.settype(o.getString("type"));*/
                            grid_model.setismyfavourite(o.getString("isLiked"));
                            grid_model.setLikes(o.getString("likes"));

                          grid_models.add(grid_model);

                        }
                        MyRecyclerViewAdapter  adapter = new MyRecyclerViewAdapter(getContext(), grid_models);
                        pullToLoadView.setAdapter(adapter);

//                        pullToLoadView.setComplete();
//                        isLoading = false;
//                        nextPage = page + 1;
                    }else {
                        Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                    }

                } catch(JSONException e){
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(stringRequest);
        return v;
    }
}
