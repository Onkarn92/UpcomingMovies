package com.onkarnene.upcomingmovies.adapter;
/*
 * Created by Onkar Nene on 13-01-2017.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.onkarnene.upcomingmovies.R;
import com.onkarnene.upcomingmovies.model.MovieImage;
import com.onkarnene.upcomingmovies.utils.APIConfig;

import java.util.ArrayList;

public class ImageViewPagerAdapter extends PagerAdapter {
    private Context m_context;
    private ArrayList<MovieImage> m_movieImages;

    /**
     * Constructor function
     * @param context of the activity
     */
    public ImageViewPagerAdapter(Context context) {
        this.m_context = context;
    }

    /**
     * Set list of MovieImage model
     * @param movieImages contains data
     */
    public void setMovieImages(@NonNull ArrayList<MovieImage> movieImages) {
        this.m_movieImages = movieImages;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (this.m_movieImages == null) return 0;

        // Set image limit to viewpager, Maximum 5 images to be load
        if (this.m_movieImages.size() >= 5) {
            return 5;
        } else {
            return this.m_movieImages.size();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(this.m_context).inflate(R.layout.item_movie_image, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.movieImage);
        String imageURL = APIConfig.IMAGE_API_URL + APIConfig.BACKDROP_SIZE_500 + this.m_movieImages.get(position).getFilePath();
        Glide.with(this.m_context)
                .load(imageURL)
                .placeholder(R.drawable.place_holder)
                .crossFade()
                .into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
