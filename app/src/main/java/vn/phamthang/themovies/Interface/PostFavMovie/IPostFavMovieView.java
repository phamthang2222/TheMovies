package vn.phamthang.themovies.Interface.PostFavMovie;

import okhttp3.ResponseBody;
import vn.phamthang.themovies.objects.request.FavoriteMovieRequest;

public interface IPostFavMovieView {
    void postMovieSuccess(ResponseBody responseBody);
    void postMovieError(String error);
}
