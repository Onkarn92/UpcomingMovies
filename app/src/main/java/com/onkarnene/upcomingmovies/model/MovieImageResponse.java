package com.onkarnene.upcomingmovies.model;
/*
 * Created by Onkar Nene on 13-01-2017.
 */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Stores JSON response element for MovieDetail
 */
public class MovieImageResponse {

    @SerializedName("m_id")
    private int m_id;
    @SerializedName("backdrops")
    private ArrayList<MovieImage> m_backdropImages;

    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        this.m_id = id;
    }

    public ArrayList<MovieImage> getBackdropImages() {
        return m_backdropImages;
    }

    public void setBackdropImages(ArrayList<MovieImage> backdropImages) {
        this.m_backdropImages = backdropImages;
    }
}
