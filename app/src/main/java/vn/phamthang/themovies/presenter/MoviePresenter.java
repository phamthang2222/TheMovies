package vn.phamthang.themovies.presenter;

import vn.phamthang.themovies.Interface.MostMovie.IMoviePresenter;
import vn.phamthang.themovies.Interface.MostMovie.IMovieView;
import vn.phamthang.themovies.interactors.DetailMovieInteractor;
import vn.phamthang.themovies.interactors.MovieInteractor;
import vn.phamthang.themovies.objects.BestMovieRespone;
import vn.phamthang.themovies.objects.Result;

public class MoviePresenter implements IMoviePresenter {
    private IMovieView mIMovieView;
    private MovieInteractor mMovieInteractor;

    public MoviePresenter(IMovieView mIMovieView) {
        this.mIMovieView = mIMovieView;
        mMovieInteractor = new MovieInteractor(this);
    }

    public void getTopRateMovie() {
        mMovieInteractor.getTopRateMovie();
    }

    public void getUpComingMovie() {
        mMovieInteractor.getUpComingMovie();
    }

    public void getNowPlayingMovie() {
        mMovieInteractor.getAllNowPlayingMovie();
    }

    public void getPopularMovie() {
        mMovieInteractor.getPopularMovie();
    }

    public void getDiscoverMovie() {
        mMovieInteractor.getDiscoverMovie();
    }

    public void getSearchMovie(String input){
        mMovieInteractor.getSearchMovie(input);
    }
    public void getFavMovie() {
        mMovieInteractor.getFavMovie();
    }
    @Override
    public void getMovieSuccess(BestMovieRespone response) {
        if (mIMovieView != null) {
            mIMovieView.getMovieSuccess(response);
        }
    }

    @Override
    public void getMovieError(String error) {
        if (mIMovieView != null) {
            mIMovieView.getMovieError(error);
        }
    }


}
