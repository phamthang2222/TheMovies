package vn.phamthang.themovies.presenter;

import vn.phamthang.themovies.Interface.VideoMovie.IVideoMoviePresenter;
import vn.phamthang.themovies.Interface.VideoMovie.IVideoMovieView;
import vn.phamthang.themovies.interactors.VideoMovieInteractor;
import vn.phamthang.themovies.objects.Video.ResultVideoMovie;

public class VideoMoviePresenter implements IVideoMoviePresenter {

    private IVideoMovieView iVideoMovieView;
    private VideoMovieInteractor mVideoMovieInteractor;

    public VideoMoviePresenter(IVideoMovieView iVideoMovieView) {
        this.iVideoMovieView = iVideoMovieView;
        mVideoMovieInteractor = new VideoMovieInteractor(this);
    }
    public void getDetailMovie(int idMovie){
        mVideoMovieInteractor.getVideoMovie(idMovie);
    }

    @Override
    public void getVideoMovieSuccess(ResultVideoMovie responseVideoMovie) {
        iVideoMovieView.getVideoMovieSuccess(responseVideoMovie);
    }

    @Override
    public void getVideoMovieError(String error) {
        iVideoMovieView.getVideoMovieError(error);
    }
}
