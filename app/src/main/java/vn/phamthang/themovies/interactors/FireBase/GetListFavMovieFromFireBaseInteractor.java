package vn.phamthang.themovies.interactors.FireBase;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.phamthang.themovies.firebase.FirebaseUtils;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.request.MovieRequest;
import vn.phamthang.themovies.presenter.FireBase.GetFavMovieFromFireBasePresenter;
import vn.phamthang.themovies.presenter.FireBase.PostMovieToFireBasePresenter;
import vn.phamthang.themovies.ultis.Constant;

public class GetListFavMovieFromFireBaseInteractor {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    private GetFavMovieFromFireBasePresenter presenter;

    public GetListFavMovieFromFireBaseInteractor(GetFavMovieFromFireBasePresenter presenter) {
        this.presenter = presenter;
        this.mAuth = FirebaseUtils.getFirebaseAuthInstance();
        this.database = FirebaseUtils.getFirebaseDatabaseInstance();
    }

    public void getListFavMovieFromFireBase() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = "";
        if (currentUser == null) {
            userId = "";
        } else {
            userId = currentUser.getUid();
        }
        DatabaseReference favMoviesRef = database.getReference(Constant.FAV_MOVIE).child(userId);
        favMoviesRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Movie> favoriteMovies = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Movie movie = snapshot.getValue(Movie.class);
                    if (movie != null) {
                        favoriteMovies.add(movie);
                    }
                }
                presenter.GetFavMovieFromFireBaseSuccess(favoriteMovies);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                presenter.GetFavMovieFromFireBaseError("Lỗi lấy ds yêu thích");
            }
        });
    }
}
