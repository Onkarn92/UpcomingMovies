package com.onkarnene.upcomingmovies.model;
/*
 * Created by Onkar Nene on 13-01-2017.
 */

import com.google.gson.annotations.SerializedName;

/**
 * Model class for MovieDetails ViewPager
 */
public class MovieImage {

    @SerializedName("width")
    private String m_width;
    @SerializedName("height")
    private String m_height;
    @SerializedName("file_path")
    private String m_filePath;
    @SerializedName("aspect_ratio")
    private String m_aspectRation;

    public String getWidth() {
        return m_width;
    }

    public void setWidth(String width) {
        this.m_width = width;
    }

    public String getHeight() {
        return m_height;
    }

    public void setHeight(String height) {
        this.m_height = height;
    }

    public String getFilePath() {
        return m_filePath;
    }

    public void setFilePath(String filePath) {
        this.m_filePath = filePath;
    }

    public String getAspectRation() {
        return m_aspectRation;
    }

    public void setAspectRation(String aspectRation) {
        this.m_aspectRation = aspectRation;
    }
}
