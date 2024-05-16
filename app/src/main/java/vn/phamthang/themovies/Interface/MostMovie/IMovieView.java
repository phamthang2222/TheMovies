package vn.phamthang.themovies.Interface.MostMovie;

import vn.phamthang.themovies.objects.BestMovieRespone;
import vn.phamthang.themovies.objects.Result;

public interface IMovieView {
    void getMovieSuccess(BestMovieRespone response);
    void getMovieError(String error);


}
