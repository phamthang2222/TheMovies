package vn.phamthang.themovies.presenter.FireBase;

import vn.phamthang.themovies.Interface.RatingMovieFromFireBase.IGetRatingMoviePresenter;
import vn.phamthang.themovies.Interface.RatingMovieFromFireBase.IGetRatingMovieView;
import vn.phamthang.themovies.interactors.FireBase.GetRatingMovieFromFireBaseInteractor;

public class GetRatingMoviePresenter implements IGetRatingMoviePresenter {
    private IGetRatingMovieView iGetRatingMovieView;
    private GetRatingMovieFromFireBaseInteractor getRatingMovieFromFireBaseInteractor;

    public GetRatingMoviePresenter(IGetRatingMovieView iGetRatingMovieView) {
        this.iGetRatingMovieView = iGetRatingMovieView;
        this.getRatingMovieFromFireBaseInteractor = new GetRatingMovieFromFireBaseInteractor(this);
    }

    public void getRatingMovie(int idMovie) {
        getRatingMovieFromFireBaseInteractor.getRatingMovie(idMovie);
    }

    @Override
    public void getRatingMovieSuccess(double valueRating) {
        iGetRatingMovieView.getRatingMovieSuccess(valueRating);

    }

    @Override
    public void getRatingMovieError() {
        iGetRatingMovieView.getRatingMovieError();
    }
}
