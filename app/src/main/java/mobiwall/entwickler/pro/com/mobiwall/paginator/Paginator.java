package mobiwall.entwickler.pro.com.mobiwall.paginator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Paginator {

    Context c;
    PullToLoadView pullToLoadView;
    RecyclerView rv;
    MyRecyclerViewAdapter adapter;
    boolean isLoading = false;
    boolean hasLoadAll = false;
    int nextPage;

    int id;
    public Paginator(Context c, PullToLoadView pullToLoadView) {
        this.c = c;
        this.pullToLoadView = pullToLoadView;

        rv = pullToLoadView.getRecyclerView();
        rv.setLayoutManager(new GridLayoutManager(c, 2));

        adapter = new MyRecyclerViewAdapter(c, new ArrayList<Grid_model>());
        rv.setAdapter(adapter);

        initializePagination();
    }


    String search = "";

    public Paginator(Context c, PullToLoadView pullToLoadView, String search) {
        this.search = search;
        this.c = c;
        this.pullToLoadView = pullToLoadView;

        rv = pullToLoadView.getRecyclerView();
        rv.setLayoutManager(new GridLayoutManager(c, 2));

        adapter = new MyRecyclerViewAdapter(c, new ArrayList<Grid_model>());
        rv.setAdapter(adapter);

        initializePagination();
    }


    public void initializePagination() {

        pullToLoadView.isLoadMoreEnabled(true);
        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                LoadData(nextPage);
            }

            @Override
            public void onRefresh() {
                adapter.clear();
                hasLoadAll = false;
                LoadData(0);
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return hasLoadAll;
            }
        });

        pullToLoadView.initLoad();
    }


    public void LoadData(final int page) {

        isLoading = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(c);
                @SuppressLint("HardwareIds") //String URL="http://www.charmhdwallpapers.com/wallpaper/imagelist?page_size=20&last_item_id=0&device_uid=c699fde86c24b1c&category_id=&category_type=Daily&color_code=&count=0";

                        String URL = "";
                if (search.equals(""))
                    URL = "http://themeelite.com/ananta/image_data?last_image_id=" + id+ "&device_id=" + Settings.Secure.getString(c.getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                else URL = "http://themeelite.com/ananta/search?search=" + search+ "&device_id=" + Settings.Secure.getString(c.getContentResolver(),
                            Settings.Secure.ANDROID_ID);

                Log.e("Grid service url", URL);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("page IN ASYNC TASK paginator", response);
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray array = jsonObject.getJSONArray("photoupload");


                            if (array.length()>0) {
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject o = (JSONObject) array.get(i);
                                    Grid_model grid_model = new Grid_model();
                                    grid_model.setId(o.getInt("id"));
                                    id = o.getInt("id");
                                    grid_model.setimg_url("http://themeelite.com/ananta/public/uploads/" + o.getString("photo"));
                                   /* grid_model.setcategory_id(o.getString("category_id"));
                                    grid_model.setfavourite_no(o.getString("favourite_no"));
                                    grid_model.settype(o.getString("type"));*/
                                    grid_model.setismyfavourite(o.getString("isLiked"));
                                    grid_model.setLikes(o.getString("likes"));

                                    adapter.add(grid_model);

                                }
                            }else if (array.length()==0){
                                pullToLoadView.setComplete();
                                isLoading = false;
                                pullToLoadView.isLoadMoreEnabled(false);
                                hasLoadAll=true;
                                pullToLoadView.setLoadMoreOffset(0
                                );

                            }
                            pullToLoadView.setComplete();
                            isLoading = false;
                            nextPage = page + 1;


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
            }
        }, 10);
    }
}
