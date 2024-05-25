package vn.phamthang.themovies.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import vn.phamthang.themovies.CustomToast.FailToast;
import vn.phamthang.themovies.CustomToast.SuccessfulToast;
import vn.phamthang.themovies.view.FlashLoginSuccessActivity;
import vn.phamthang.themovies.view.HomeActivity;

public class Authentication {
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static void onSignUp(String email, String password, Context context) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            new SuccessfulToast(context, "Đăng kí thành công").showToast();
                        } else {
                            new FailToast(context,"Đăng kí thất bại ").showToast();
                        }
                    }
                });
    }

    public static void onSignIn(String email, String password,  final Activity activity) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //toast
                            new SuccessfulToast(activity, "Đăng nhập thành công").showToast();
                            //intent
                            Intent intent = new Intent(activity, FlashLoginSuccessActivity.class);
                            activity.startActivity(intent);
                            activity.finish();
                            Animatoo.INSTANCE.animateSlideUp(activity);

                        } else {
                            new FailToast(activity,"Sai tên đăng nhập/mật khẩu").showToast();
                        }
                    }
                });
    }

}
