package com.example.flixter.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.flixter.DetailActivity;
import com.example.flixter.R;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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
        // Reference to relative layout
        RelativeLayout container;

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
            // Reference to container
            container = itemView.findViewById(R.id.container);

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
            // Glide to load image and make image corners rounded
            Glide.with(context).load(imageUrl).transform(new CenterInside(), new RoundedCorners(24)).into(ivPoster);



            // 1. Register click listener on the whole row(container)
            // Reference to RelativeLayout.
            // for this get reference to container element which contains image etc
            // thats in itemmoviexml


            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2. Navigate to a new activity on tap , use intent to navigate
                    // to detail movie.
                    // register a clicklistner when we want to navigate new activity(detailActivity)

                    // intent to move to detail activity
                    Intent i = new Intent(context, DetailActivity.class);

                    // Display relevant data for the movie
                    //i.putExtra("title", movie.getTitle());

                    // pull out whole movie object from intent bundle
                    // make movie class into parceleable type because our movie
                    // is not primitive like int, short etc. so we use parcel data type
                    // for this purpose
                    i.putExtra("movie", Parcels.wrap(movie));

                    // start activty is method on context object
                    context.startActivity(i);

                    // for debugging use toast when you click on movie title
                    // a toaster shows up at bottom with movie name
                    //Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();


                }
            });
        }
    }

}
