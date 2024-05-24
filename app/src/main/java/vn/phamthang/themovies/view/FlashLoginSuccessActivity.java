package vn.phamthang.themovies.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;

import vn.phamthang.themovies.R;
import vn.phamthang.themovies.databinding.ActivityFlashLoginSuccessBinding;

public class FlashLoginSuccessActivity extends AppCompatActivity {
    ActivityFlashLoginSuccessBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFlashLoginSuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(getResources().getColor(R.color.prime_color));


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(FlashLoginSuccessActivity.this, HomeActivity.class));
                Animatoo.INSTANCE.animateShrink(FlashLoginSuccessActivity.this);
                finish();
            }
        }, 1500);
    }
}