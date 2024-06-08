package vn.phamthang.themovies.interactors.FireBase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vn.phamthang.themovies.firebase.FirebaseUtils;
import vn.phamthang.themovies.objects.RatingMovie;
import vn.phamthang.themovies.presenter.FireBase.PostRatingMovieToFireBasePresenter;
import vn.phamthang.themovies.ultis.Constant;

public class PostRatingMovieToFireBaseInteractor {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private PostRatingMovieToFireBasePresenter presenter;

    public PostRatingMovieToFireBaseInteractor(PostRatingMovieToFireBasePresenter presenter) {
        this.presenter = presenter;
        this.mAuth = FirebaseUtils.getFirebaseAuthInstance();
        this.database = FirebaseUtils.getFirebaseDatabaseInstance();
    }

    public void postRatingMovie(RatingMovie ratingMovie){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = currentUser.getUid();
        DatabaseReference myRef = database.getReference(Constant.RATING).child(userId).push();
        myRef.setValue(ratingMovie);
        presenter.IPostRatingSuccess();
    }
}
