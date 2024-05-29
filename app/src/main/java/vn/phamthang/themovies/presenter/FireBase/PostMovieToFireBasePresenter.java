package vn.phamthang.themovies.presenter.FireBase;

import vn.phamthang.themovies.Interface.PostMovieToFireBase.IPostMovieToFireBasePresenter;
import vn.phamthang.themovies.Interface.PostMovieToFireBase.IPostMovieToFireBaseView;
import vn.phamthang.themovies.interactors.FireBase.PostMovieToFireBaseInteractor;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.request.MovieRequest;

public class PostMovieToFireBasePresenter implements IPostMovieToFireBasePresenter {
    private IPostMovieToFireBaseView iPostMovieToFireBaseView;
    private PostMovieToFireBaseInteractor postMovieToFireBaseInteractor;

    public PostMovieToFireBasePresenter(IPostMovieToFireBaseView iPostMovieToFireBaseView) {
        this.iPostMovieToFireBaseView = iPostMovieToFireBaseView;
        postMovieToFireBaseInteractor = new PostMovieToFireBaseInteractor(this);
    }

    public void PostMovieToFireBase(Movie movieRequest){
        postMovieToFireBaseInteractor.PostMovieToFireBase(movieRequest);
    }
    @Override
    public void IPostMovieToFireBaseSuccess(Movie request) {
        iPostMovieToFireBaseView.IPostMovieToFireBaseSuccess(request);
    }

    @Override
    public void IPostMovieToFireBaseError(String error) {
        iPostMovieToFireBaseView.IPostMovieToFireBaseError(error);
    }
}
