package vn.phamthang.themovies.Interface.CastingMovie;

import vn.phamthang.themovies.objects.Cast.CastingRespone;

public interface ICastingMovieView {
    void getCastingMovieSuccess(CastingRespone castingRespone);
    void getCastingMovieError(String error);
}
