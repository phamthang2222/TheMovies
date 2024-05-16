package vn.phamthang.themovies.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import vn.phamthang.themovies.R;
import vn.phamthang.themovies.databinding.ActivityFlashArtBinding;

public class FlashArtActivity extends AppCompatActivity {
    ActivityFlashArtBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFlashArtBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        initView();

    }

    private void initView() {
    }
}