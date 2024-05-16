package vn.phamthang.themovies.interactors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.phamthang.themovies.Interface.DetailMovie.IMovieDetailPresenter;
import vn.phamthang.themovies.Interface.MostMovie.IMoviePresenter;
import vn.phamthang.themovies.api.ApiUtils;
import vn.phamthang.themovies.api.services.IDummyServices;
import vn.phamthang.themovies.objects.BestMovieRespone;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.ultis.Constant;

public class DetailMovieInteractor{
    private IMovieDetailPresenter iMovieDetailPresenter;
    private IDummyServices iDummyServices;
    public DetailMovieInteractor(IMovieDetailPresenter iMovieDetailPresenter) {
        this.iMovieDetailPresenter = iMovieDetailPresenter;
        iDummyServices = ApiUtils.getDummyServices();
    }
    public void getDetailMovie(int idMovie){
        iDummyServices.getDetailMovie(idMovie).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful()){
                    if(iMovieDetailPresenter !=null){
                        iMovieDetailPresenter.getDetailMovieSuccess(response.body());
                    }
                }else{
                    if(iMovieDetailPresenter !=null){
                        iMovieDetailPresenter.getDetailMovieError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable throwable) {
                if(iMovieDetailPresenter !=null){
                    iMovieDetailPresenter.getDetailMovieError(throwable.getMessage());
                }
            }
        });
    }

}
