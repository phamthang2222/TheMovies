package vn.phamthang.themovies.Interface.MostMovie.DetailMovie;

import vn.phamthang.themovies.objects.BestMovieRespone;
import vn.phamthang.themovies.objects.Result;

public interface IMovieDetailView {
    void getDetailMovieSuccess(Result response);
    void getDetailMovieError(String message);
}
