package vn.phamthang.themovies.Interface.CastingMovie;

import vn.phamthang.themovies.objects.Cast.CastingRespone;

public interface ICastingMoviePresenter {
    void getCastingMovieSuccess(CastingRespone castingRespone);
    void getCastingMovieError(String error);
}
