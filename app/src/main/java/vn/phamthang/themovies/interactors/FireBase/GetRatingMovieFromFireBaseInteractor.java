package vn.phamthang.themovies.interactors.FireBase;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.phamthang.themovies.firebase.FirebaseUtils;
import vn.phamthang.themovies.objects.RatingMovie;
import vn.phamthang.themovies.presenter.FireBase.GetRatingMoviePresenter;
import vn.phamthang.themovies.ultis.Constant;

public class GetRatingMovieFromFireBaseInteractor {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private GetRatingMoviePresenter mPresenter;

    public GetRatingMovieFromFireBaseInteractor(GetRatingMoviePresenter mPresenter) {
        this.mPresenter = mPresenter;
        this.mAuth = FirebaseUtils.getFirebaseAuthInstance();
        this.database = FirebaseUtils.getFirebaseDatabaseInstance();
    }

    public void getRatingMovie(int idMovie) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = "";
        if (currentUser == null) {
            userId = "";
        } else {
            userId = currentUser.getUid();
        }
        DatabaseReference valueRatingRef = database.getReference(Constant.RATING).child(userId);
        valueRatingRef.orderByChild("idMovie").equalTo(idMovie).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        RatingMovie ratingMovie = childSnapshot.getValue(RatingMovie.class);
                        if (ratingMovie != null && ratingMovie.getIdMovie() == idMovie) {
                            double valueRating = ratingMovie.getRatingValue();
                            mPresenter.getRatingMovieSuccess(valueRating);
                            return; // Thoát sau khi tìm thấy kết quả đầu tiên
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
