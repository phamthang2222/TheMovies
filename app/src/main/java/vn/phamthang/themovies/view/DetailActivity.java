package vn.phamthang.themovies.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;

import vn.phamthang.themovies.R;
import vn.phamthang.themovies.adapters.ViewPagerAdapter.ViewPagerDetailMovieAdapter;
import vn.phamthang.themovies.adapters.ViewPagerAdapter.ViewPagerMainActivityAdapter;
import vn.phamthang.themovies.databinding.ActivityDetailBinding;
import vn.phamthang.themovies.fragments.DetailMovie.AboutMovieFragment;
import vn.phamthang.themovies.fragments.DetailMovie.CastFragment;
import vn.phamthang.themovies.fragments.DetailMovie.ReviewFragment;
import vn.phamthang.themovies.fragments.SubFragmentHome.NowPlayingFragment;
import vn.phamthang.themovies.fragments.SubFragmentHome.PopularFragment;
import vn.phamthang.themovies.fragments.SubFragmentHome.TopRateFragment;
import vn.phamthang.themovies.fragments.SubFragmentHome.UpComingFragment;
import vn.phamthang.themovies.objects.Result;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        Result movie = new Result();
        movie = (Result) intent.getSerializableExtra("movie");

        Toast.makeText(this, ""+movie.toString(), Toast.LENGTH_SHORT).show();

    }

    private void initView() {
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
        binding.tabLayoutDetailMovie.setupWithViewPager(binding.viewPagerDetailMovie);
        ViewPagerDetailMovieAdapter viewPagerDetailMovieAdapter = new ViewPagerDetailMovieAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerDetailMovieAdapter.addFragment(new AboutMovieFragment(),"About");
        viewPagerDetailMovieAdapter.addFragment(new ReviewFragment(),"Review");
        viewPagerDetailMovieAdapter.addFragment(new CastFragment(),"Case");
        binding.viewPagerDetailMovie.setAdapter(viewPagerDetailMovieAdapter);
    }
}