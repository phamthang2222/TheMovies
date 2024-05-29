package vn.phamthang.themovies.Interface.PostMovieToFireBase;

import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.request.MovieRequest;

public interface IPostMovieToFireBasePresenter {
    void IPostMovieToFireBaseSuccess(Movie request);
    void IPostMovieToFireBaseError(String error);
}
