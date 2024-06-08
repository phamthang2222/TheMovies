package vn.phamthang.themovies.presenter.FireBase;

import vn.phamthang.themovies.Interface.PostRatingToFireBase.IPostRatingPresenter;
import vn.phamthang.themovies.Interface.PostRatingToFireBase.IPostRatingView;
import vn.phamthang.themovies.interactors.FireBase.PostRatingMovieToFireBaseInteractor;
import vn.phamthang.themovies.objects.RatingMovie;

public class PostRatingMovieToFireBasePresenter implements IPostRatingPresenter {

    private PostRatingMovieToFireBaseInteractor postRatingMovieToFireBaseInteractor;
    private IPostRatingView iPostRatingView;

    public PostRatingMovieToFireBasePresenter(IPostRatingView iPostRatingView) {
        this.iPostRatingView = iPostRatingView;
        this.postRatingMovieToFireBaseInteractor = new PostRatingMovieToFireBaseInteractor(this);
    }

    public void postRating(RatingMovie ratingMovie){
        postRatingMovieToFireBaseInteractor.postRatingMovie(ratingMovie);
    }

    @Override
    public void IPostRatingSuccess() {
        iPostRatingView.IPostRatingSuccess();
    }

    @Override
    public void IPostRatingError() {
        iPostRatingView.IPostRatingError();
    }
}
