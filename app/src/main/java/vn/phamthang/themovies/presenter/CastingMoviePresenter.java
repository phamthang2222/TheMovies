package vn.phamthang.themovies.presenter;

import vn.phamthang.themovies.Interface.CastingMovie.ICastingMoviePresenter;
import vn.phamthang.themovies.Interface.CastingMovie.ICastingMovieView;
import vn.phamthang.themovies.interactors.CastingMovieInteractor;
import vn.phamthang.themovies.objects.Cast.CastingRespone;

public class CastingMoviePresenter implements ICastingMoviePresenter {

    private CastingMovieInteractor mCastingMovieInteractor;
    private ICastingMovieView iCastingMovieView;

    public CastingMoviePresenter(ICastingMovieView iCastingMovieView) {
        this.iCastingMovieView = iCastingMovieView;
        mCastingMovieInteractor = new CastingMovieInteractor(this);
    }

    public void getCasting(int iDMovie) {
        mCastingMovieInteractor.getCasting(iDMovie);
    }

    @Override
    public void getCastingMovieSuccess(CastingRespone castingRespone) {
        iCastingMovieView.getCastingMovieSuccess(castingRespone);
    }

    @Override
    public void getCastingMovieError(String error) {
        iCastingMovieView.getCastingMovieError(error);
    }
}
