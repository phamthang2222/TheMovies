package vn.phamthang.themovies.objects;

import java.io.Serializable;

public class RatingMovie implements Serializable {
    private int idMovie;
    private double ratingValue;

    public RatingMovie() {
    }

    public RatingMovie(int idMovie, double ratingValue) {
        this.idMovie = idMovie;
        this.ratingValue = ratingValue;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public double getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(double ratingValue) {
        this.ratingValue = ratingValue;
    }
}
