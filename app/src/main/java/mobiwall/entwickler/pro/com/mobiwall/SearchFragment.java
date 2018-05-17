package mobiwall.entwickler.pro.com.mobiwall;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.srx.widget.PullToLoadView;

import mobiwall.entwickler.pro.com.mobiwall.paginator.Paginator;
import mobiwall.entwickler.pro.com.mobiwall.paginator.Paginator_search;


public class SearchFragment extends Fragment {
    PullToLoadView pullToLoadView;
    EditText string;
    Button go;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_search, container, false);
        pullToLoadView = view.findViewById(R.id.wallpaper_grid_search);
        string = view.findViewById(R.id.search_text);
        go = view.findViewById(R.id.btn_go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string1 = string.getText().toString();
                new Paginator_search(getContext(),pullToLoadView,string1);
            }
        });

        return view;
    }

}
