package vn.phamthang.themovies.presenter;

import vn.phamthang.themovies.Interface.ReviewMovie.IReviewMoviePresenter;
import vn.phamthang.themovies.Interface.ReviewMovie.IReviewMovieView;
import vn.phamthang.themovies.interactors.ReviewMovieInteractor;
import vn.phamthang.themovies.objects.Review.ReviewResponse;

public class ReviewPresenter implements IReviewMoviePresenter {
    private IReviewMovieView iReviewMovieView;
    private ReviewMovieInteractor mReviewMovieInteractor;

    public ReviewPresenter(IReviewMovieView iReviewMovieView) {
        this.iReviewMovieView = iReviewMovieView;
        mReviewMovieInteractor = new ReviewMovieInteractor(this);
    }

    public void getReviewMovie(int idMovie) {
        mReviewMovieInteractor.getReview(idMovie);
    }

    @Override
    public void getReviewSuccess(ReviewResponse reviewResponse) {
        iReviewMovieView.getReviewSuccess(reviewResponse);
    }

    @Override
    public void getReviewError(String error) {
        iReviewMovieView.getReviewError(error);
    }
}
