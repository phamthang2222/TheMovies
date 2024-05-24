package vn.phamthang.themovies.interactors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.phamthang.themovies.api.ApiUtils;
import vn.phamthang.themovies.api.services.IDummyServices;
import vn.phamthang.themovies.objects.BestMovieRespone;
import vn.phamthang.themovies.presenter.SimilarMoviePresenter;

public class SimilarMovieInteractor {
    private SimilarMoviePresenter similarMoviePresenter;
    private IDummyServices iDummyServices;

    public SimilarMovieInteractor(SimilarMoviePresenter similarMoviePresenter) {
        this.similarMoviePresenter = similarMoviePresenter;
        this.iDummyServices = ApiUtils.getDummyServices();
    }
    public void getSimilarMovie(int idMovie){
        iDummyServices.getSimilarMovie(idMovie).enqueue(new Callback<BestMovieRespone>() {
            @Override
            public void onResponse(Call<BestMovieRespone> call, Response<BestMovieRespone> response) {
                if(response.isSuccessful()){
                    if(similarMoviePresenter != null){
                        similarMoviePresenter.getSimilarMovieSuccess(response.body());
                    }else{
                        similarMoviePresenter.getSimilarMovieError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<BestMovieRespone> call, Throwable throwable) {
                if(similarMoviePresenter != null){
                    similarMoviePresenter.getSimilarMovieError(throwable.getMessage());
                }
            }
        });
    }
}
