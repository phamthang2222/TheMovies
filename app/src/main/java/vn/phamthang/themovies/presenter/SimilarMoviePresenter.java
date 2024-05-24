package vn.phamthang.themovies.presenter;

import vn.phamthang.themovies.Interface.SimilarMovie.ISimilarMoviePresenter;
import vn.phamthang.themovies.Interface.SimilarMovie.ISimilarMovieView;
import vn.phamthang.themovies.interactors.SimilarMovieInteractor;
import vn.phamthang.themovies.objects.BestMovieRespone;

public class SimilarMoviePresenter implements ISimilarMoviePresenter {

    private SimilarMovieInteractor mSimilarMovieInteractor;
    private ISimilarMovieView iSimilarMovieView;

    public SimilarMoviePresenter(ISimilarMovieView iSimilarMovieView) {
        this.iSimilarMovieView = iSimilarMovieView;
        mSimilarMovieInteractor = new SimilarMovieInteractor(this);
    }

    public void getSimilar(int idMovie){
        mSimilarMovieInteractor.getSimilarMovie(idMovie);
    }

    @Override
    public void getSimilarMovieSuccess(BestMovieRespone response) {
        iSimilarMovieView.getSimilarMovieSuccess(response);
    }

    @Override
    public void getSimilarMovieError(String error) {
        iSimilarMovieView.getSimilarMovieError(error);
    }
}
