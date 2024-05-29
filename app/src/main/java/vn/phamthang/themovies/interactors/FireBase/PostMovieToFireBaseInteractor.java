package vn.phamthang.themovies.interactors.FireBase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vn.phamthang.themovies.firebase.FirebaseUtils;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.request.MovieRequest;
import vn.phamthang.themovies.presenter.FireBase.PostMovieToFireBasePresenter;
import vn.phamthang.themovies.ultis.Constant;

public class PostMovieToFireBaseInteractor {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    private PostMovieToFireBasePresenter presenter;

    public PostMovieToFireBaseInteractor(PostMovieToFireBasePresenter presenter) {
        this.presenter = presenter;
        this.mAuth = FirebaseUtils.getFirebaseAuthInstance();
        this.database = FirebaseUtils.getFirebaseDatabaseInstance();
    }

    public void PostMovieToFireBase(Movie request) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = currentUser.getUid();
        DatabaseReference myRef = database.getReference(Constant.FAV_MOVIE).child(userId).push();
        myRef.setValue(request);
        presenter.IPostMovieToFireBaseSuccess(request);

    }
}
