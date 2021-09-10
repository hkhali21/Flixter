package com.example.flixter.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.R;
import com.example.flixter.models.Movie;

import java.util.List;

// extend this class and right click and implement the 3 methods
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    //context to where adapter is constructed from
    Context context;

    // data
    List<Movie> movies;

    // create constructor for these two member by right click and generate


    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // static method on layoutInflator class adn takes in context
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        // wrap this view inside of viewHolder
        return new ViewHolder(movieView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter","onBindViewHolder " + position);
        // Get the movie at the position
        Movie movie = movies.get(position);
        // Bind the movie data into the viewHolder,
        // populate elements of VH with data of the movie, invent method bind
        holder.bind(movie);


    }

    //Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // define the inner view holder class
    // create a constructor for it by right clicking it
    // ViewHolder is representation of row in view
    public class ViewHolder extends RecyclerView.ViewHolder {
        // member var for each view in viewHolder
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // define where textviews and image views are coming from
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        public void bind(Movie movie) {
            // use getter on movie nd populate the views
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            //render image using library.. take movie path url and load the context into ivPoster
            Log.e("MovieAdapter", movie.getPosterPath());

            String imageUrl;
            // if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // then imageUrl = backdropPath
                imageUrl = movie.getBackdropPath();
            } else {
                // else imageUrl = poster image
                imageUrl = movie.getPosterPath();
            }
            Glide.with(context).load(imageUrl).into(ivPoster);
        }
    }

}
