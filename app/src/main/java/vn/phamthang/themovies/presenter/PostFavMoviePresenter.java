package vn.phamthang.themovies.presenter;

import okhttp3.ResponseBody;
import vn.phamthang.themovies.Interface.PostFavMovie.IPostFavMoviePresenter;
import vn.phamthang.themovies.Interface.PostFavMovie.IPostFavMovieView;
import vn.phamthang.themovies.interactors.PostFavoriteMovieInteractor;
import vn.phamthang.themovies.objects.request.FavoriteMovieRequest;

public class PostFavMoviePresenter implements IPostFavMoviePresenter {

    private IPostFavMovieView iPostFavMovieView;
    private PostFavoriteMovieInteractor postFavoriteMovieInteractor;

    public PostFavMoviePresenter(IPostFavMovieView iPostFavMovieView) {
        this.iPostFavMovieView = iPostFavMovieView;
         postFavoriteMovieInteractor = new PostFavoriteMovieInteractor(this);
    }

    public void postFavMovie(FavoriteMovieRequest request) {
        postFavoriteMovieInteractor.postFavMovie(request);
    }

    @Override
    public void postMovieSuccess(ResponseBody responseBody) {
        if (iPostFavMovieView != null) {
            iPostFavMovieView.postMovieSuccess(responseBody);

        }
    }

    @Override
    public void postMovieError(String error) {
        if (iPostFavMovieView != null) {
            iPostFavMovieView.postMovieError(error);
        }
    }
}
