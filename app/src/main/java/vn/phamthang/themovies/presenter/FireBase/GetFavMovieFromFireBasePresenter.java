package vn.phamthang.themovies.presenter.FireBase;

import java.util.ArrayList;
import java.util.List;

import vn.phamthang.themovies.Interface.FavMovieFireBase.IGetFavMovieFromFireBasePresenter;
import vn.phamthang.themovies.Interface.FavMovieFireBase.IGetFavMovieFromFireBaseView;
import vn.phamthang.themovies.interactors.FireBase.GetListFavMovieFromFireBaseInteractor;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.request.MovieRequest;

public class GetFavMovieFromFireBasePresenter implements IGetFavMovieFromFireBasePresenter {
    private IGetFavMovieFromFireBaseView iGetFavMovieFromFireBaseView;
    private GetListFavMovieFromFireBaseInteractor interactor;

    public GetFavMovieFromFireBasePresenter(IGetFavMovieFromFireBaseView iGetFavMovieFromFireBaseView) {
        this.iGetFavMovieFromFireBaseView = iGetFavMovieFromFireBaseView;
        this.interactor = new GetListFavMovieFromFireBaseInteractor(this);
    }

    public void getFavMovieFromFireBase( ){
        interactor.getListFavMovieFromFireBase();
    }
    @Override
    public void GetFavMovieFromFireBaseSuccess(List<Movie> movieRequestArrayList) {
        iGetFavMovieFromFireBaseView.GetFavMovieFromFireBaseSuccess(movieRequestArrayList);
    }

    @Override
    public void GetFavMovieFromFireBaseError(String error) {
        iGetFavMovieFromFireBaseView.GetFavMovieFromFireBaseError(error);
    }
}
