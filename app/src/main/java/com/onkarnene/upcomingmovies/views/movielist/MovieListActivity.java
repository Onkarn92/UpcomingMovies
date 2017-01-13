package com.onkarnene.upcomingmovies.views.movielist;
/*
 * Created by Onkar Nene on 12-01-2017.
 *
 * MIT License
 * Copyright (c) 2017 Onkar Nene
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.onkarnene.upcomingmovies.R;
import com.onkarnene.upcomingmovies.adapter.MovieListAdapter;
import com.onkarnene.upcomingmovies.model.Movie;
import com.onkarnene.upcomingmovies.model.MovieResponse;
import com.onkarnene.upcomingmovies.utils.APIConfig;
import com.onkarnene.upcomingmovies.utils.NetworkConfig;
import com.onkarnene.upcomingmovies.views.IMovieDBService;
import com.onkarnene.upcomingmovies.views.info.InfoActivity;
import com.onkarnene.upcomingmovies.views.moviedetail.MovieDetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieListActivity extends AppCompatActivity implements MovieListAdapter.IMovieCallback {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView m_recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar m_progressBar;

    private MovieListAdapter m_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);
        this.m_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.m_adapter = new MovieListAdapter(this);
        this.m_adapter.setMovieCallback(this);

        if (NetworkConfig.isNetworkAvailable(this)) {
            // Network is available
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIConfig.MOVIE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            IMovieDBService service = retrofit.create(IMovieDBService.class);
            Call<MovieResponse> movieResponseCall = service.getUpcomingMovies(APIConfig.API_KEY);
            movieResponseCall.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    try {
                        if (response.body() == null) {
                            m_showSnackBar(getResources().getString(R.string.authentication_error));
                            return;
                        }
                        if (!response.body().getMovies().isEmpty()) {
                            m_progressBar.setVisibility(View.GONE);
                            m_recyclerView.setVisibility(View.VISIBLE);
                            m_adapter.setMovies(response.body().getMovies());
                            m_recyclerView.setAdapter(m_adapter);
                        } else {
                            m_showSnackBar(getResources().getString(R.string.no_movies));
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        m_showSnackBar(getResources().getString(R.string.service_fail));
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.e(TAG, "Service Failed : " + t.getLocalizedMessage());
                    m_showSnackBar(getResources().getString(R.string.service_fail));
                }
            });
        } else {
            m_showSnackBar(getResources().getString(R.string.no_internet));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_info) {
            startActivity(new Intent(this, InfoActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_list, menu);
        return true;
    }

    /**
     * Show snackbar on activity
     * @param msg to be shown with snackbar
     */
    private void m_showSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getResources().getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        snackbar.show();
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }
}
