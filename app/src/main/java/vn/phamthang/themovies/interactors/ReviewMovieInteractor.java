package vn.phamthang.themovies.interactors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.phamthang.themovies.api.ApiUtils;
import vn.phamthang.themovies.api.services.IDummyServices;
import vn.phamthang.themovies.objects.Review.ReviewResponse;
import vn.phamthang.themovies.objects.Video.ResultVideoMovie;
import vn.phamthang.themovies.presenter.ReviewPresenter;

public class ReviewMovieInteractor {
    private ReviewPresenter  mReviewPresenter;
    private IDummyServices iDummyServices;

    public ReviewMovieInteractor(ReviewPresenter reviewPresenter) {
        this.mReviewPresenter = reviewPresenter;
        iDummyServices = ApiUtils.getDummyServices();
    }

    public void getReview(int iDMovie){
        iDummyServices.getReviewMovie(iDMovie).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if(response.isSuccessful()){
                    if(mReviewPresenter!=null){
                        mReviewPresenter.getReviewSuccess(response.body());
                    }
                }else{
                    if(mReviewPresenter!=null){
                        mReviewPresenter.getReviewError(response.message());
                    }
                }
            }
            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable throwable) {
                if(mReviewPresenter!=null){
                    mReviewPresenter.getReviewError(throwable.getMessage());
                }
            }
        });
    }
}
