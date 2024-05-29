package vn.phamthang.themovies.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.phamthang.themovies.objects.request.MovieRequest;

public class PushObjectFirebase {
    public static final String FAV_MOVIE = "FAV_MOVIE";

    public static void pushMovieRequestToFirebase(MovieRequest movieRequest) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = currentUser.getUid();
        DatabaseReference myRef = database.getReference(FAV_MOVIE).child(userId);
        myRef.setValue(movieRequest);
    }
    public static void removeMovieRequestFromFirebase(final String mediaId) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        String userId = currentUser.getUid();

        DatabaseReference myRef = database.getReference(FAV_MOVIE).child(userId);

        // Tìm và xóa đối tượng dựa trên mediaId
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MovieRequest movieRequest = snapshot.getValue(MovieRequest.class);
                    if (movieRequest != null && mediaId.equals(movieRequest.getMedia_id())) {
                        snapshot.getRef().removeValue();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}