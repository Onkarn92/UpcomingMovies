package com.onkarnene.upcomingmovies.views.moviedetail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.onkarnene.upcomingmovies.R;
import com.onkarnene.upcomingmovies.adapter.ImageViewPagerAdapter;
import com.onkarnene.upcomingmovies.model.Movie;
import com.onkarnene.upcomingmovies.model.MovieImageResponse;
import com.onkarnene.upcomingmovies.utils.APIConfig;
import com.onkarnene.upcomingmovies.utils.NetworkConfig;
import com.onkarnene.upcomingmovies.views.IMovieDBService;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.title)
    TextView m_movieTitle;
    @BindView(R.id.overview)
    TextView m_overview;
    @BindView(R.id.movieRating)
    RatingBar m_ratingBar;
    @BindView(R.id.view_pager)
    ViewPager m_viewPager;
    @BindView(R.id.circleIndicator)
    CircleIndicator m_circleIndicator;
    @BindView(R.id.toolbar)
    Toolbar m_toolbar;
    @BindView(R.id.imageProgress)
    ProgressBar m_progressBar;

    private Movie m_movie;
    private ImageViewPagerAdapter m_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        this.m_movie = getIntent().getParcelableExtra("movie");
        if (this.m_movie == null) {
            this.m_showSnackBar(getResources().getString(R.string.no_movies));
            return;
        }

        this.m_setupUI();

        if (NetworkConfig.isNetworkAvailable(this)) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIConfig.MOVIE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            IMovieDBService service = retrofit.create(IMovieDBService.class);
            Call<MovieImageResponse> call = service.getMovieImages(this.m_movie.getMovieID(), APIConfig.API_KEY);
            call.enqueue(new Callback<MovieImageResponse>() {
                @Override
                public void onResponse(Call<MovieImageResponse> call, Response<MovieImageResponse> response) {
                    try {
                        if (response.body() == null) {
                            m_showSnackBar(getResources().getString(R.string.authentication_error));
                            return;
                        }
                        if (!response.body().getBackdropImages().isEmpty()) {
                            m_adapter.setMovieImages(response.body().getBackdropImages());
                            m_progressBar.setVisibility(View.GONE);
                            m_viewPager.setVisibility(View.VISIBLE);
                            m_circleIndicator.setVisibility(View.VISIBLE);
                        } else {
                            m_showSnackBar(getResources().getString(R.string.no_movies));
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<MovieImageResponse> call, Throwable t) {
                    Log.e(TAG, "Service Failed : " + t.getLocalizedMessage());
                    m_showSnackBar(getResources().getString(R.string.service_fail));
                }
            });
        }
    }

    /**
     * Initialize UI components
     */
    private void m_setupUI() {
        this.m_toolbar.setTitle(this.m_movie.getMovieTitle());
        this.m_toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        this.m_toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(this.m_toolbar);
        this.m_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        this.m_movieTitle.setText(this.m_movie.getMovieTitle());
        this.m_overview.setText(this.m_movie.getOverview());
        this.m_ratingBar.setRating((float) this.m_movie.getMovieRating());

        this.m_adapter = new ImageViewPagerAdapter(this);
        this.m_viewPager.setAdapter(this.m_adapter);
        this.m_circleIndicator.setViewPager(m_viewPager);
        this.m_adapter.registerDataSetObserver(this.m_circleIndicator.getDataSetObserver());
    }

    /**
     * Show snackbar on activity
     * @param msg to be shown with snackbar
     */
    private void m_showSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);
        snackbar.setAction(getResources().getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        snackbar.show();
    }
}