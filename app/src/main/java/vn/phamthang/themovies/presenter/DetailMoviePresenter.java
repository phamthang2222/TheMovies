package vn.phamthang.themovies.presenter;

import vn.phamthang.themovies.Interface.DetailMovie.IMovieDetailPresenter;
import vn.phamthang.themovies.Interface.MostMovie.DetailMovie.IMovieDetailView;
import vn.phamthang.themovies.interactors.DetailMovieInteractor;
import vn.phamthang.themovies.objects.BestMovieRespone;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.Result;

public class DetailMoviePresenter  implements IMovieDetailPresenter {
    private IMovieDetailView iMovieDetailView;
    private DetailMovieInteractor detailMovieInteractor;

    public DetailMoviePresenter(IMovieDetailView iMovieDetailView) {
        this.iMovieDetailView = iMovieDetailView;
        detailMovieInteractor = new DetailMovieInteractor(this);
    }
    public void getDetailMovie(int idMovie){
        detailMovieInteractor.getDetailMovie(idMovie);
    }
    @Override
    public void getDetailMovieSuccess(Movie response) {
        if(iMovieDetailView !=null){
            iMovieDetailView.getDetailMovieSuccess(response);
        }
    }

    @Override
    public void getDetailMovieError(String message) {
        if(iMovieDetailView !=null){
            iMovieDetailView.getDetailMovieError(message);
        }
    }
}
