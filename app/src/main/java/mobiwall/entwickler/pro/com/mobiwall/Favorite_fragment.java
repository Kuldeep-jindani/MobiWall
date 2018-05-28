package mobiwall.entwickler.pro.com.mobiwall;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srx.widget.PullToLoadView;

import mobiwall.entwickler.pro.com.mobiwall.paginator.Paginator;
import mobiwall.entwickler.pro.com.mobiwall.paginator.Paginator_favorite;

/**
 * Created by Kuldeep_jindani on 5/27/2018.
 */

public class Favorite_fragment extends Fragment {

    PullToLoadView pullToLoadView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_hot,container,false);

        pullToLoadView = v.findViewById(R.id.wallpaper_grid_hot);

        new Paginator_favorite(getContext(),pullToLoadView);

        return v;
    }
}
