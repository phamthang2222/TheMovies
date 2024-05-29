package vn.phamthang.themovies.interactors.FireBase;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.phamthang.themovies.firebase.FirebaseUtils;
import vn.phamthang.themovies.presenter.FireBase.RemoveMovieFromFireBasePresenter;
import vn.phamthang.themovies.ultis.Constant;

public class RemoveMovieFromFireBaseInteractor {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private RemoveMovieFromFireBasePresenter movieFromFireBasePresenter;

    public RemoveMovieFromFireBaseInteractor(RemoveMovieFromFireBasePresenter movieFromFireBasePresenter) {
        this.movieFromFireBasePresenter = movieFromFireBasePresenter;
        this.mAuth = FirebaseUtils.getFirebaseAuthInstance();
        this.database = FirebaseUtils.getFirebaseDatabaseInstance();
    }

    public void RemoveMovieFromFireBase(int IdMovie){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = currentUser.getUid();
        DatabaseReference myRef = database.getReference(Constant.FAV_MOVIE).child(userId);

        myRef.orderByChild("id").equalTo(IdMovie).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot movieSnapshot : snapshot.getChildren()) {
                    movieSnapshot.getRef().removeValue().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                                movieFromFireBasePresenter.IRemoveMovieFromFireBaseSuccess("Xóa khỏi yêu thích");
                        } else {
                            movieFromFireBasePresenter.IRemoveMovieFromFireBaseError("that bai");
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                movieFromFireBasePresenter.IRemoveMovieFromFireBaseError("that bai");
            }
        });
    }
}
