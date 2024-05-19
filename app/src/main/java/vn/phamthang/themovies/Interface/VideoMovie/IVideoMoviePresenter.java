package vn.phamthang.themovies.Interface.VideoMovie;

import vn.phamthang.themovies.objects.Video.ResultVideoMovie;

public interface IVideoMoviePresenter {
    void getVideoMovieSuccess(ResultVideoMovie responseVideoMovie);
    void getVideoMovieError(String error);
}
