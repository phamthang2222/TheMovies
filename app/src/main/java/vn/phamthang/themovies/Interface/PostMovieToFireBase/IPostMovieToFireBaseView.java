package vn.phamthang.themovies.Interface.PostMovieToFireBase;

import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.request.MovieRequest;

public interface IPostMovieToFireBaseView {
    void IPostMovieToFireBaseSuccess(Movie request);
    void IPostMovieToFireBaseError(String error);
}
