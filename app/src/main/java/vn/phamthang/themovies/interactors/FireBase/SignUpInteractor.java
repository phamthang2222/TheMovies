package vn.phamthang.themovies.interactors.FireBase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import vn.phamthang.themovies.objects.User.User;
import vn.phamthang.themovies.presenter.SignUpPresenter;
import vn.phamthang.themovies.ultis.Constant;

public class SignUpInteractor {
    private SignUpPresenter signUpPresenter;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public SignUpInteractor(SignUpPresenter signUpPresenter) {
        this.signUpPresenter = signUpPresenter;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void signUp(String email, String password, String username) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            String userId = firebaseUser.getUid();
                            User user = new User(userId, email, username, password, new ArrayList<>());
                            databaseReference.child(Constant.USER).child(userId).setValue(user)
                                    .addOnCompleteListener(userTask -> {
                                        if (userTask.isSuccessful()) {
                                            signUpPresenter.signUpSuccess();
                                        } else {
                                            signUpPresenter.signUpError(userTask.getException().getMessage());
                                        }
                                    });
                        }
                    } else {
                        signUpPresenter.signUpError(task.getException().getMessage());
                    }
                });
    }
}
