package mobiwall.entwickler.pro.com.mobiwall.paginator_hot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;

import mobiwall.entwickler.pro.com.mobiwall.Preview_daily;
import mobiwall.entwickler.pro.com.mobiwall.R;

/**
 * Created by Payal on 4/9/2018.
 */

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context c;
    JSONArray array;
    FragmentManager fragmentManager;
    ArrayList<Grid_model_hot> grid_models;


    public RecyclerViewAdapter(Context c, ArrayList<Grid_model_hot> grid_models) {
        this.c = c;
        this.grid_models=grid_models;
    }

    public  void  clear()
    {
        grid_models.clear();
    }
    public  void add(Grid_model_hot grid_model)
    {
        grid_models.add(grid_model);
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.hotlist, parent, false);
        return new RecyclerViewAdapter.ViewHolder(view);


    }




    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, final int position) {
        final Grid_model_hot grid_model = grid_models.get(position);
        Picasso.get().load(grid_model.getImg_url())
//                .placeholder(c.getResources().getColor(R.color.colorBlack))
//                .error(R.drawable.ic_launcher_background)
                .into(holder.imageView);
        holder.textView.setText(grid_model.getFavourite_no());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Preview_daily.class);
                /*intent.putExtra("img_url", grid_model.getImg_url());
                //intent.putExtra("count",grid_model.getFavourite_no());
                c.startActivity(intent);*/
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        private ViewHolder(View v){

            super(v);
            imageView = v.findViewById(R.id.img_gride_hot);
            textView = v.findViewById(R.id.count_hot);

        }

    }

}
