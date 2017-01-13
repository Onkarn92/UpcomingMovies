package com.onkarnene.upcomingmovies.model;
/*
 * Created by Onkar Nene on 12-01-2017.
 */

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Model class for Movie ListItem
 */
public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @SerializedName("id")
    private int m_movieID;
    @SerializedName("poster_path")
    private String m_posterPath;
    @SerializedName("release_date")
    private String m_releaseDate;
    @SerializedName("title")
    private String m_movieTitle;
    @SerializedName("adult")
    private boolean m_adultStatus;
    @SerializedName("overview")
    private String m_overview;
    @SerializedName("original_language")
    private String m_language;
    @SerializedName("popularity")
    private double m_movieRating;

    private Movie(Parcel in) {
        this.m_movieID = in.readInt();
        this.m_posterPath = in.readString();
        this.m_releaseDate = in.readString();
        this.m_movieTitle = in.readString();
        this.m_adultStatus = in.readByte() != 0;
        this.m_overview = in.readString();
        this.m_language = in.readString();
        this.m_movieRating = in.readDouble();
    }

    public double getMovieRating() {
        return m_movieRating;
    }

    public void setMovieRating(double movieRating) {
        this.m_movieRating = movieRating;
    }

    public int getMovieID() {
        return m_movieID;
    }

    public void setMovieID(int movieID) {
        this.m_movieID = movieID;
    }

    public String getPosterPath() {
        return m_posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.m_posterPath = posterPath;
    }

    @SuppressLint("SimpleDateFormat")
    public String getReleaseDate() {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(this.m_releaseDate);
            this.m_releaseDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.m_releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.m_releaseDate = releaseDate;
    }

    public String getMovieTitle() {
        return m_movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.m_movieTitle = movieTitle;
    }

    public boolean getAdultStatus() {
        return m_adultStatus;
    }

    public void setAdultStatus(boolean adultStatus) {
        this.m_adultStatus = adultStatus;
    }

    public String getOverview() {
        return m_overview;
    }

    public void setOverview(String overview) {
        this.m_overview = overview;
    }

    public String getLanguage() {
        return m_language;
    }

    public void setLanguage(String language) {
        this.m_language = language;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.m_movieID);
        dest.writeString(this.m_posterPath);
        dest.writeString(this.m_releaseDate);
        dest.writeString(this.m_movieTitle);
        dest.writeByte((byte) (this.m_adultStatus ? 1 : 0));
        dest.writeString(this.m_overview);
        dest.writeString(this.m_language);
        dest.writeDouble(this.m_movieRating);
    }
}