package com.example.cinefast;

import java.io.Serializable;

public class Movie implements Serializable {
    private String name;
    private int posterResId;
    private String genre;
    private String duration;
    private String trailerUrl;
    private boolean isNowShowing;

    public Movie(String name, int posterResId, String genre, String duration, String trailerUrl, boolean isNowShowing) {
        this.name = name;
        this.posterResId = posterResId;
        this.genre = genre;
        this.duration = duration;
        this.trailerUrl = trailerUrl;
        this.isNowShowing = isNowShowing;
    }

    public String getName() { return name; }
    public int getPosterResId() { return posterResId; }
    public String getGenre() { return genre; }
    public String getDuration() { return duration; }
    public String getTrailerUrl() { return trailerUrl; }
    public boolean isNowShowing() { return isNowShowing; }
}
