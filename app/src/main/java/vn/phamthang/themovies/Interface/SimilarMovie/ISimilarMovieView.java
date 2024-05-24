package vn.phamthang.themovies.Interface.SimilarMovie;

import vn.phamthang.themovies.objects.BestMovieRespone;

public interface ISimilarMovieView {
    public void getSimilarMovieSuccess(BestMovieRespone response);
    public void getSimilarMovieError(String error);
}
