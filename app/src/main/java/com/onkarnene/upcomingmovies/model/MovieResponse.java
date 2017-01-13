package com.onkarnene.upcomingmovies.model;
/*
 * Created by Onkar Nene on 12-01-2017.
 */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Stores JSON response element for MovieList
 */
public class MovieResponse {

    @SerializedName("m_page")
    private int m_page;
    @SerializedName("results")
    private ArrayList<Movie> m_movies;
    @SerializedName("total_results")
    private int m_totalResults;
    @SerializedName("total_pages")
    private int m_totalPages;

    public int getPage() {
        return m_page;
    }

    public void setPage(int page) {
        this.m_page = page;
    }

    public int getTotalPages() {
        return m_totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.m_totalPages = totalPages;
    }

    public int getTotalResults() {
        return m_totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.m_totalResults = totalResults;
    }

    public ArrayList<Movie> getMovies() {
        return m_movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.m_movies = movies;
    }
}
