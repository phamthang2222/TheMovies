package vn.phamthang.themovies.Interface.PostFavMovie;

import okhttp3.ResponseBody;

public interface IPostFavMoviePresenter {
    void postMovieSuccess(ResponseBody ResponseBody);
    void postMovieError(String error);
}
