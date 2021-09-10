package com.example.flixter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// class to parse from results data the movie data and turn them into movie objects.
public class Movie {
    // data we need
    String backdropPath;
    String posterPath;
    String title;
    String overview;

    // constructor takes a json obj and construct movie obj.
    public Movie(JSONObject jsonObject) throws JSONException {

        // add axception wehn right click on getSTring
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
    }
    // take in json array and turn them inoto list of movies
    // create static method
    // return list of movie and name is fromJsonArray
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        // iterate through json array and construct movie for each element in json array
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++) {
            // add movie at each position of array
            // add exception to getJsonObject
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        // return list of movies
        return movies;

    }
    // take data out of movie objects
    // right click here on blank and click on generate->getters-> select all 3 data members


    public String getPosterPath() {
        // make poster path useful for the image size
        // %s in url means we want to replace this place with poster path(2nd param)
        // size is here 342
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    // constructor for backdropPath
    public String getBackdropPath() {
        // make backdropPath useful for the image size
        // %s in url means we want to replace this place with poster path(2nd param)
        // size is here 342
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
