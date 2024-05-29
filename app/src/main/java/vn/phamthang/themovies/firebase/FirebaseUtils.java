package vn.phamthang.themovies.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {
    private static FirebaseAuth firebaseAuth;
    private static FirebaseDatabase firebaseDatabase;

    private FirebaseUtils() {}

    public static FirebaseAuth getFirebaseAuthInstance() {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }
    public static FirebaseDatabase getFirebaseDatabaseInstance() {
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
        }
        return firebaseDatabase;
    }
}
