package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.adapters.MovieAdapter;
import com.example.flixter.models.Movie;
import com.facebook.stetho.common.ArrayListAccumulator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

import androidx.appcompat.app.ActionBar;

public class MainActivity extends AppCompatActivity {
    // var to get request on currently playing movies
    public static  final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    // ADD TAG IS NAME OF MAIN ACTIVITY TO EASILY LOG DATA
    public  static  final String TAG ="MainActivity";

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // in Activity#onCreate
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);

        setContentView(R.layout.activity_main);
        // define recyclerview
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        //instantiate movie
        movies = new ArrayList<>();
        // Create the adapter, using constructor, use this as activity is
        // instance of context and movies
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        // Set the adapter on the recycler view
        rvMovies.setAdapter(movieAdapter);


        // Set a Layout Manager on the recycler view (how to layout dif views on screen)
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        // create async obj
        AsyncHttpClient client = new AsyncHttpClient();

        // create get request on that url and pass response handler json as movie returns json
        // so after this adding break point remove the app from the emulator and then run it
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                // data is in json object so pull out data from it
                JSONObject jsonObject = json.jsonObject;
                // the key result is an array so we use array
                // we use try and catch as this key may not exist to catch exception
                try {
                    JSONArray results = jsonObject.getJSONArray("results");

                    // if succeeded display results, i is for info
                    Log.i(TAG, "Results: " + results.toString());

                    // call the function from Movie class
                    // right click on fromjsonarray and select local var
                    // it returns list of movie objects
                    // adapter will have refernce to this movie and change this reference here
                    movies.addAll(Movie.fromJsonArray(results));
                    // when data changes behind ada[ter so let adapter know
                    // so it can rerender RV
                    movieAdapter.notifyDataSetChanged();
                    // to check if data is sensible
                    Log.i(TAG, "Movies: " + movies.size());

                } catch (JSONException e) {
                    Log.e(TAG,"Hit json exception", e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }
}