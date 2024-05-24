package vn.phamthang.themovies.Interface.SimilarMovie;

import vn.phamthang.themovies.objects.BestMovieRespone;

public interface ISimilarMoviePresenter {
    public void getSimilarMovieSuccess(BestMovieRespone response);
    public void getSimilarMovieError(String error);
}
