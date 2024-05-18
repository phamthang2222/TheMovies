package vn.phamthang.themovies.Interface.PostFavMovie;

import okhttp3.ResponseBody;
import vn.phamthang.themovies.objects.request.FavoriteMovieRequest;

public interface IPostFavMoviePresenter {
    void postMovieSuccess(ResponseBody ResponseBody);
    void postMovieError(String error);
}
