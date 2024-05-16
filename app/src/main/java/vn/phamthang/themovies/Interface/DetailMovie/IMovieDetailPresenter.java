package vn.phamthang.themovies.Interface.DetailMovie;

import vn.phamthang.themovies.objects.Result;

public interface IMovieDetailPresenter {
    void getDetailMovieSuccess(Result response);
    void getDetailMovieError(String message);
}
