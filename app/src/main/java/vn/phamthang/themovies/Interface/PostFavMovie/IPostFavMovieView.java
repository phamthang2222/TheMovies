package vn.phamthang.themovies.Interface.PostFavMovie;

import okhttp3.ResponseBody;

public interface IPostFavMovieView {
    void postMovieSuccess(ResponseBody responseBody);
    void postMovieError(String error);
}
