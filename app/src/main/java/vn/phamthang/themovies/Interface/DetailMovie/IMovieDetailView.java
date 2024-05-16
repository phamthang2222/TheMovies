package vn.phamthang.themovies.Interface.MostMovie.DetailMovie;
import vn.phamthang.themovies.objects.Movie;

public interface IMovieDetailView {
    void getDetailMovieSuccess(Movie response);
    void getDetailMovieError(String message);
}
