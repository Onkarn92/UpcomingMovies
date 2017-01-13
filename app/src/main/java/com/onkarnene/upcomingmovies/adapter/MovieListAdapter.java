package com.onkarnene.upcomingmovies.adapter;
/*
 * Created by Onkar Nene on 12-01-2017.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onkarnene.upcomingmovies.R;
import com.onkarnene.upcomingmovies.model.Movie;
import com.onkarnene.upcomingmovies.utils.APIConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private ArrayList<Movie> m_movies;
    private Context m_context;
    private IMovieCallback m_movieCallback;

    /**
     * Constructor function
     * @param context of the activity
     */
    public MovieListAdapter(Context context) {
        this.m_context = context;
    }

    /**
     * Set list of Movie model
     * @param movies contains data
     */
    public void setMovies(@NonNull ArrayList<Movie> movies) {
        this.m_movies = movies;
    }

    /**
     * @param movieCallback function to be register
     */
    public void setMovieCallback(IMovieCallback movieCallback) {
        this.m_movieCallback = movieCallback;
    }

    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            String imageURL = APIConfig.IMAGE_API_URL + APIConfig.POSTER_SIZE_92 + this.m_movies.get(position).getPosterPath();
            Glide.with(this.m_context)
                    .load(imageURL)
                    .centerCrop()
                    .placeholder(R.drawable.place_holder)
                    .crossFade()
                    .into(holder.thumbnail);

            holder.setMovie(this.m_movies.get(position));
            holder.movieTitle.setText(this.m_movies.get(position).getMovieTitle());
            if (this.m_movies.get(position).getAdultStatus()) {
                holder.adultStatus.setText(this.m_context.getResources().getString(R.string.adult));
            } else {
                holder.adultStatus.setText(this.m_context.getResources().getString(R.string.ua));
            }
            String dateData = this.m_movies.get(position).getReleaseDate();
            holder.releaseDate.setText(dateData);
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateData);
            this.m_movies.get(position).setReleaseDate(new SimpleDateFormat("yyyy-MM-dd").format(date));
        } catch (NullPointerException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (this.m_movies == null) {
            return 0;
        } else {
            return this.m_movies.size();
        }
    }

    /**
     * Callback interface
     * Listens item click event
     */
    public interface IMovieCallback {
        public void onItemClick(Movie movie);
    }

    /**
     * Holds the view for respective ListItem
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.thumbnail)
        ImageView thumbnail;

        @BindView(R.id.releaseDate)
        TextView releaseDate;

        @BindView(R.id.title)
        TextView movieTitle;

        @BindView(R.id.adultStatus)
        TextView adultStatus;

        private Movie m_movie;

        /**
         * Constructor function
         * @param itemView contains respective ListItem
         */
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setMovie(Movie movie) {
            this.m_movie = movie;
        }

        @OnClick(R.id.card_view)
        public void onListItemClickListener(View view) {
            if (m_movieCallback != null) {
                m_movieCallback.onItemClick(this.m_movie);
            }
        }
    }
}
