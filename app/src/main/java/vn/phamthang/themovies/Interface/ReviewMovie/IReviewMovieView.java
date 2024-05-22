package vn.phamthang.themovies.Interface.ReviewMovie;

import vn.phamthang.themovies.objects.Review.ReviewResponse;

public interface IReviewMovieView {
    public void getReviewSuccess(ReviewResponse reviewResponse);
    public void getReviewError(String error);
}
