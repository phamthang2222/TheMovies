package vn.phamthang.themovies.interactors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.phamthang.themovies.Interface.DetailMovie.IMovieDetailPresenter;
import vn.phamthang.themovies.Interface.MostMovie.DetailMovie.IMovieDetailView;
import vn.phamthang.themovies.api.ApiUtils;
import vn.phamthang.themovies.api.services.IDummyServices;
import vn.phamthang.themovies.objects.BestMovieRespone;
import vn.phamthang.themovies.Interface.MostMovie.IMoviePresenter;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.ultis.Constant;

public class MovieInteractor {
    private IMoviePresenter iMoviePresenter;
    private IDummyServices iDummyServices;

    public MovieInteractor(IMoviePresenter iMoviePresenter) {
        this.iMoviePresenter = iMoviePresenter;
        iDummyServices = ApiUtils.getDummyServices();
    }

    public void getTopRateMovie() {
        iDummyServices.getTopRateMovie().enqueue(new Callback<BestMovieRespone>() {
            @Override
            public void onResponse(Call<BestMovieRespone> call, Response<BestMovieRespone> response) {
                if (response.isSuccessful()) {
                    if (iMoviePresenter != null) {
                        iMoviePresenter.getMovieSuccess(response.body());
                    }
                }else {
                    if (iMoviePresenter != null) {
                        iMoviePresenter.getMovieError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<BestMovieRespone> call, Throwable throwable) {
                iMoviePresenter.getMovieError(throwable.getMessage());
            }
        });

    }
    public void getUpComingMovie() {
        iDummyServices.getUpComingMovie().enqueue(new Callback<BestMovieRespone>() {
            @Override
            public void onResponse(Call<BestMovieRespone> call, Response<BestMovieRespone> response) {
                if (response.isSuccessful()) {
                    if (iMoviePresenter != null) {
                        iMoviePresenter.getMovieSuccess(response.body());
                    }
                }else {
                    if (iMoviePresenter != null) {
                        iMoviePresenter.getMovieError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<BestMovieRespone> call, Throwable throwable) {
                iMoviePresenter.getMovieError(throwable.getMessage());
            }
        });

    }
    public void getAllNowPlayingMovie(){
        iDummyServices.getNowPlayingMovie().enqueue(new Callback<BestMovieRespone>() {
            @Override
            public void onResponse(Call<BestMovieRespone> call, Response<BestMovieRespone> response) {
                if(response.isSuccessful()){
                    if(iMoviePresenter != null){
                        iMoviePresenter.getMovieSuccess(response.body());
                    }
                }else {
                    if(iMoviePresenter != null){
                        iMoviePresenter.getMovieError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<BestMovieRespone> call, Throwable throwable) {
                iMoviePresenter.getMovieError(throwable.getMessage());

            }
        });
    }
    public void getPopularMovie(){
        iDummyServices.getPopularMovie().enqueue(new Callback<BestMovieRespone>() {
            @Override
            public void onResponse(Call<BestMovieRespone> call, Response<BestMovieRespone> response) {
                if(response.isSuccessful()){
                    if(iMoviePresenter != null){
                        iMoviePresenter.getMovieSuccess(response.body());
                    }
                }else {
                    if(iMoviePresenter != null){
                        iMoviePresenter.getMovieError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<BestMovieRespone> call, Throwable throwable) {
                if(iMoviePresenter != null){
                    iMoviePresenter.getMovieError(throwable.getMessage());
                }
            }
        });
    }
    public void getDiscoverMovie(){
        iDummyServices.getDiscoverMovie().enqueue(new Callback<BestMovieRespone>() {
            @Override
            public void onResponse(Call<BestMovieRespone> call, Response<BestMovieRespone> response) {
                if(response.isSuccessful()){
                    if(iMoviePresenter != null){
                        iMoviePresenter.getMovieSuccess(response.body());
                    }
                }else {
                    if(iMoviePresenter != null){
                        iMoviePresenter.getMovieError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<BestMovieRespone> call, Throwable throwable) {
                if(iMoviePresenter != null){
                    iMoviePresenter.getMovieError(throwable.getMessage());
                }
            }
        });
    }

    public void getSearchMovie(String query){
        iDummyServices.getSearchMovie(query).enqueue(new Callback<BestMovieRespone>() {
            @Override
            public void onResponse(Call<BestMovieRespone> call, Response<BestMovieRespone> response) {
                if(response.isSuccessful()){
                    if(iMoviePresenter != null){
                        iMoviePresenter.getMovieSuccess(response.body());
                    }
                }else{
                    if(iMoviePresenter != null){
                        iMoviePresenter.getMovieError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<BestMovieRespone> call, Throwable throwable) {
                if(iMoviePresenter != null){
                    iMoviePresenter.getMovieError(throwable.getMessage());
                }
            }
        });
    }

}
