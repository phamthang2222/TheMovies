package vn.phamthang.themovies.interactors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.phamthang.themovies.Interface.PostFavMovie.IPostFavMoviePresenter;
import vn.phamthang.themovies.api.ApiUtils;
import vn.phamthang.themovies.api.services.IDummyServices;
import vn.phamthang.themovies.objects.request.MovieRequest;

public class PostFavoriteMovieInteractor {
    private IPostFavMoviePresenter iPostFavMoviePresenter;
    private IDummyServices iDummyServices;

    public PostFavoriteMovieInteractor(IPostFavMoviePresenter iPostFavMoviePresenter) {
        this.iPostFavMoviePresenter = iPostFavMoviePresenter;
       iDummyServices = ApiUtils.getDummyServices();
    }

    public void postFavMovie(MovieRequest request) {
        iDummyServices.addToFavorite(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (iPostFavMoviePresenter != null) {
                        iPostFavMoviePresenter.postMovieSuccess(response.body());
                    }
                } else {
                    if (iPostFavMoviePresenter != null) {
                        iPostFavMoviePresenter.postMovieError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                iPostFavMoviePresenter.postMovieError(throwable.getMessage());
            }
        });
    }
}
