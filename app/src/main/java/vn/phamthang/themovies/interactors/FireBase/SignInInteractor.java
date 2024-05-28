package vn.phamthang.themovies.interactors.FireBase;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import vn.phamthang.themovies.Interface.SignIn.ISignInPresenter;
import vn.phamthang.themovies.custom_toast.FailToast;
import vn.phamthang.themovies.custom_toast.SuccessfulToast;
import vn.phamthang.themovies.objects.User.User;
import vn.phamthang.themovies.view.FlashLoginSuccessActivity;

public class SignInInteractor {
    private FirebaseAuth firebaseAuth;
    private ISignInPresenter iSignInPresenter;

    public SignInInteractor(ISignInPresenter iSignInPresenter) {
        this.iSignInPresenter = iSignInPresenter;
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public void signIn(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        iSignInPresenter.signInSuccess();
                    } else {
                        iSignInPresenter.signInError("Error");
                    }
                });
    }
}
