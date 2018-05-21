package mobiwall.entwickler.pro.com.mobiwall;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

import mobiwall.entwickler.pro.com.mobiwall.paginator.Paginator;
import mobiwall.entwickler.pro.com.mobiwall.paginator.Paginator_search;


@SuppressLint("ValidFragment")
public class SearchFragment extends Fragment {
//    PullToLoadView pullToLoadView;
    RecyclerView pullToLoadView;
    EditText string;
    Button go;

    String search_string;

    public SearchFragment() {
    }

    public SearchFragment(String search_string) {
        this.search_string = search_string;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_search, container, false);
        pullToLoadView = view.findViewById(R.id.wallpaper_search);
        string = view.findViewById(R.id.search_text);
        go = view.findViewById(R.id.btn_go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string1 = string.getText().toString();
                new Paginator_search(getContext(),pullToLoadView,string1);
            }
        });
        new Paginator_search(getContext(),pullToLoadView,search_string);

       /* RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        String URL="http://themeelite.com/ananta/cate_data";*//*?page_size=20&last_item_id=0&device_uid=c699fde86c24b1c&category_id=&category_type=Hot&color_code=&count=0%22";*//*
        Log.e("Grid service url", URL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("IN ASYNC TASK search", response);
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("category");

                   *//* for (int i = 0; i < array.length(); i++) {
                        JSONObject o = (JSONObject) array.get(i);
                        Grid_model_search grid_model = new Grid_model_search();
                        grid_model.setId(o.getInt("id"));
                        grid_model.setimg_url(o.getString("category_name"));
                        grid_model.setimg_url(o.getString("image_url"));

                        adapter.add(grid_model);

                    }*//*

//                    adapter = new RecyclerAdapterSearch(getContext(), new ArrayList<Grid_model_search>());
                    RecyclerAdapterSearch adapter = new RecyclerAdapterSearch(getContext(), array,
                            getFragmentManager());
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

        requestQueue.add(stringRequest);*/

        return view;
    }

}
