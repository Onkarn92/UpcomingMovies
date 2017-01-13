package com.onkarnene.upcomingmovies.views;
/*
 * Created by Onkar Nene on 12-01-2017.
 */

import com.onkarnene.upcomingmovies.model.MovieImageResponse;
import com.onkarnene.upcomingmovies.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMovieDBService {

    @GET("upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("{id}/images")
    Call<MovieImageResponse> getMovieImages(@Path("id") int movieID, @Query("api_key") String apiKey);
}
