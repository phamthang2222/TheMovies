package vn.phamthang.themovies.presenter.FireBase;

import vn.phamthang.themovies.Interface.RemoveMovieFromFireBase.IRemoveMovieFromFireBasePresenter;
import vn.phamthang.themovies.Interface.RemoveMovieFromFireBase.IRemoveMovieFromFireBaseView;
import vn.phamthang.themovies.interactors.FireBase.RemoveMovieFromFireBaseInteractor;

public class RemoveMovieFromFireBasePresenter implements IRemoveMovieFromFireBasePresenter {
    private IRemoveMovieFromFireBaseView iRemoveMovieFromFireBaseView;
    private RemoveMovieFromFireBaseInteractor mRemoveMovieFromFireBaseInteractor;

    public RemoveMovieFromFireBasePresenter(IRemoveMovieFromFireBaseView iRemoveMovieFromFireBaseView) {
        this.iRemoveMovieFromFireBaseView = iRemoveMovieFromFireBaseView;
        mRemoveMovieFromFireBaseInteractor = new RemoveMovieFromFireBaseInteractor(this);
    }

    public void removeMovieFromFireBase(int idMovie) {
        mRemoveMovieFromFireBaseInteractor.RemoveMovieFromFireBase(idMovie);
    }

    @Override
    public void IRemoveMovieFromFireBaseSuccess(String success) {
        iRemoveMovieFromFireBaseView.IRemoveMovieFromFireBaseSuccess(success);
    }

    @Override
    public void IRemoveMovieFromFireBaseError(String error) {
        iRemoveMovieFromFireBaseView.IRemoveMovieFromFireBaseError(error);
    }
}
