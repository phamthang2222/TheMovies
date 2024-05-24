package vn.phamthang.themovies.ultis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
                            Toast.makeText(context, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Đăng kí thất bại ", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(activity, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity, FlashLoginSuccessActivity.class);
                            activity.startActivity(intent);
                            activity.finish();

                        } else {
                            Toast.makeText(activity, "Sai tên đăng nhập/mật khẩu ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
