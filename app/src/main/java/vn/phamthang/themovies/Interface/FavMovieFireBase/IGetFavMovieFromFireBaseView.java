package vn.phamthang.themovies.Interface.FavMovieFireBase;

import java.util.ArrayList;
import java.util.List;

import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.request.MovieRequest;

public interface IGetFavMovieFromFireBaseView {
    void GetFavMovieFromFireBaseSuccess(List<Movie> movieRequestArrayList);
    void GetFavMovieFromFireBaseError(String error);
}
