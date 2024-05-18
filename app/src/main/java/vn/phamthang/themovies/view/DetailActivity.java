package vn.phamthang.themovies.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import org.greenrobot.eventbus.EventBus;

import okhttp3.ResponseBody;
import vn.phamthang.themovies.Interface.PostFavMovie.IPostFavMovieView;
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
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.objects.request.FavoriteMovieRequest;
import vn.phamthang.themovies.presenter.PostFavMoviePresenter;
import vn.phamthang.themovies.ultis.Constant;
import vn.phamthang.themovies.ultis.MessageEvent;

public class DetailActivity extends AppCompatActivity implements IPostFavMovieView {

    private PostFavMoviePresenter postFavMoviePresenter;

    private ActivityDetailBinding binding;
    private Movie movie  = new Movie();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.prime_color));

        initData();
        initView();


    }

    private void initData() {
        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra("movie");

        Glide.with(binding.imgMovieDetail)
                .load(Constant.convertLinkImage(movie.getPosterPath()))
                .transform(new CenterCrop(), new RoundedCorners(10)) // crop and border
                .into(binding.imgMovieDetail);
        Glide.with(binding.imgBgMovie)
                .load(Constant.convertLinkImage2(movie.getBackdropPath()))
                .transform(new CenterCrop(), new RoundedCorners(10)) // crop and border
                .into(binding.imgBgMovie);
        binding.tvStar.setText(movie.getVoteAverage()+"");
        binding.tvStar.setText(movie.getVoteAverage()+"");
        binding.tvTitleMovie.setText(movie.getTitle());
        binding.tvCalendar.setText(movie.getReleaseDate());
        binding.tvMinus.setText(movie.getRuntime()+" Minutes");
        binding.tvGenre.setText(movie.getGenres().get(0).getName()+"");// 1list lấy tạm phần tử 0

        EventBus.getDefault().post(new MessageEvent(movie));
    }

    private void initView() {
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.imgAddToFav.setOnClickListener(v -> {
            int mediaID= movie.getId();

            FavoriteMovieRequest request = new FavoriteMovieRequest(mediaID,"movie",true);
            postFavMoviePresenter = new PostFavMoviePresenter(this);
            postFavMoviePresenter.postFavMovie(request);

        });


        binding.tabLayoutDetailMovie.setupWithViewPager(binding.viewPagerDetailMovie);
        ViewPagerDetailMovieAdapter viewPagerDetailMovieAdapter = new ViewPagerDetailMovieAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerDetailMovieAdapter.addFragment(new AboutMovieFragment(),"About");
        viewPagerDetailMovieAdapter.addFragment(new ReviewFragment(),"Review");
        viewPagerDetailMovieAdapter.addFragment(new CastFragment(),"Case");
        binding.viewPagerDetailMovie.setAdapter(viewPagerDetailMovieAdapter);


    }


    @Override
    public void postMovieSuccess(ResponseBody responseBody) {
        Toast.makeText(this, ""+responseBody.toString(), Toast.LENGTH_SHORT).show();
        Log.e("Response:",responseBody.toString());
    }

    @Override
    public void postMovieError(String error) {
        Toast.makeText(this, "lỗi", Toast.LENGTH_SHORT).show();

    }
}