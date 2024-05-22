package vn.phamthang.themovies.interactors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.phamthang.themovies.api.ApiUtils;
import vn.phamthang.themovies.api.services.IDummyServices;
import vn.phamthang.themovies.objects.Cast.CastingRespone;
import vn.phamthang.themovies.presenter.CastingMoviePresenter;

public class CastingMovieInteractor {
    private CastingMoviePresenter mCastingMoviePresenter;
    private IDummyServices iDummyServices;

    public CastingMovieInteractor(CastingMoviePresenter mCastingMoviePresenter) {
        this.mCastingMoviePresenter = mCastingMoviePresenter;
        this.iDummyServices = ApiUtils.getDummyServices();
    }
    public void getCasting(int iDMovie){
        iDummyServices.getCastMovie(iDMovie).enqueue(new Callback<CastingRespone>() {
            @Override
            public void onResponse(Call<CastingRespone> call, Response<CastingRespone> response) {
                if(response.isSuccessful()){
                    if(mCastingMoviePresenter != null){
                        mCastingMoviePresenter.getCastingMovieSuccess(response.body());
                    }
                }else{
                    if(mCastingMoviePresenter != null){
                        mCastingMoviePresenter.getCastingMovieError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<CastingRespone> call, Throwable throwable) {
                if(mCastingMoviePresenter != null){
                    mCastingMoviePresenter.getCastingMovieError(throwable.getMessage());
                }
            }
        });
    }
}
