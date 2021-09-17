package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {

    // paste youtube API key
    private static final String YOUTUBE_API_KEY = "AIzaSyBh4344XKGst-3JgfOThn3dZpkib9y-fcY";
    public static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    // pull out reference to the elements in xml in activity detail
    TextView tvtTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // define elements here after setting content view
        tvtTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        ratingBar = findViewById(R.id.ratingBar);
        youTubePlayerView = findViewById(R.id.player);

        // String title is returned from intent
        // retrieve data according to that key
        String title = getIntent().getStringExtra("title");

        // retrieve whole movie object from intent bundle
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        // set title that we got from intent on textview
        //tvtTitle.setText(title);
        tvtTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        // down cast to double as getrating return double while ratingBar expect float
        ratingBar.setRating((float) movie.getRating());

        // Make request to get all movie video urls
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                // parse results that we get from this json object that are video trailers url and extract youtube key
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    // check the result array if its empty return nothing
                    if (results.length() == 0) {
                        return;
                    }
                    // else get first object at position 0 and get the key value
                    // this returns youtubekey
                    String youtubeKey = results.getJSONObject(0).getString("key");

                    // log youtubekey
                    Log.d("DetailActivity", youtubeKey);
                    // after success initialize the youtube key by creating a method
                    // so right click on initializeYouTube and do create method-> select DetailActivitymethod
                    initializeYouTube(youtubeKey);
                } catch (JSONException e) {
                    Log.e("DetailActivity", "Failed to parse JSON", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });

    }

    private void initializeYouTube(final String youtubeKey) {

        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // for debugging
                Log.d("DetailActivity","onInitializationSuccess");

                // do any work here to cue video, play video, etc snd pass youtubekey
                youTubePlayer.cueVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("DetailActivity","onInitializationFailure");
            }
        });



    }
}