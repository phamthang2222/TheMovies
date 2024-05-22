package vn.phamthang.themovies.Interface.ReviewMovie;

import vn.phamthang.themovies.objects.Review.ReviewResponse;

public interface IReviewMoviePresenter {
    public void getReviewSuccess(ReviewResponse reviewResponse);
    public void getReviewError(String error);
}
