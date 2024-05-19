package vn.phamthang.themovies.interactors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.phamthang.themovies.api.ApiUtils;
import vn.phamthang.themovies.api.services.IDummyServices;
import vn.phamthang.themovies.objects.Video.ResultVideoMovie;
import vn.phamthang.themovies.presenter.VideoMoviePresenter;

public class VideoMovieInteractor {
    private VideoMoviePresenter mVideoMoviePresenter;
    private IDummyServices iDummyServices;

    public VideoMovieInteractor(VideoMoviePresenter mVideoMoviePresenter) {
        this.mVideoMoviePresenter = mVideoMoviePresenter;
        iDummyServices = ApiUtils.getDummyServices();
    }
    public void getVideoMovie(int idMovie){
        iDummyServices.getVideoMovie(idMovie).enqueue(new Callback<ResultVideoMovie>() {
            @Override
            public void onResponse(Call<ResultVideoMovie> call, Response<ResultVideoMovie> response) {
                if(response.isSuccessful()){
                    if(mVideoMoviePresenter != null){
                        mVideoMoviePresenter.getVideoMovieSuccess(response.body());
                    }
                }else{
                    if(mVideoMoviePresenter != null){
                        mVideoMoviePresenter.getVideoMovieError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultVideoMovie> call, Throwable throwable) {
                if(mVideoMoviePresenter != null){
                    mVideoMoviePresenter.getVideoMovieError(throwable.getMessage());
                }
            }
        });
    }
}
