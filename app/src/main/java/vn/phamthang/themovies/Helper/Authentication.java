package vn.phamthang.themovies.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import vn.phamthang.themovies.CustomToast.FailToast;
import vn.phamthang.themovies.CustomToast.SuccessfulToast;
import vn.phamthang.themovies.objects.User.User;
import vn.phamthang.themovies.ultis.MessageEvent;
import vn.phamthang.themovies.view.FlashLoginSuccessActivity;

public class Authentication {
    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    public static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public static void onSignUp(String email, String password, Context context, String username) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            String userId = firebaseUser.getUid();
                            User user = new User(userId, email, username, password, new ArrayList<>());
                            addUser(user);
                            new SuccessfulToast(context, "Đăng ký thành công!").showToast();
                        }
                    } else {
                        new FailToast(context, "Email đã đăng ký!").showToast();
                    }
                });
    }

    public static void onSignIn(String email, String password, final Activity activity) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            new SuccessfulToast(activity, "Đăng nhập thành công").showToast();
                            Intent intent = new Intent(activity, FlashLoginSuccessActivity.class);
                            activity.startActivity(intent);
                            activity.finish();
                            Animatoo.INSTANCE.animateSlideUp(activity);
                        } else {
                            new FailToast(activity, "Sai tên đăng nhập/mật khẩu").showToast();
                        }
                    }
                });
    }

    public static void addUser(User user) {
        String childId = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("USER").child(childId).setValue(user);
    }


}
