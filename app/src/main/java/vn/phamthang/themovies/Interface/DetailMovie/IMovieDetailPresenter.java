package vn.phamthang.themovies.Interface.DetailMovie;

import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.Result;

public interface IMovieDetailPresenter {
    void getDetailMovieSuccess(Movie response);
    void getDetailMovieError(String message);
}
