package vn.phamthang.themovies.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import vn.phamthang.themovies.Interface.MostMovie.IMovieView;
import vn.phamthang.themovies.Interface.PostFavMovie.IPostFavMovieView;
import vn.phamthang.themovies.Interface.VideoMovie.IVideoMovieView;
import vn.phamthang.themovies.R;
import vn.phamthang.themovies.adapters.VideoMovieAdapter;
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
import vn.phamthang.themovies.objects.BestMovieRespone;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.objects.Video.ResultVideoMovie;
import vn.phamthang.themovies.objects.request.FavoriteMovieRequest;
import vn.phamthang.themovies.presenter.MoviePresenter;
import vn.phamthang.themovies.presenter.PostFavMoviePresenter;
import vn.phamthang.themovies.presenter.VideoMoviePresenter;
import vn.phamthang.themovies.ultis.Constant;
import vn.phamthang.themovies.ultis.MessageEvent;

public class DetailActivity extends AppCompatActivity implements IPostFavMovieView, IMovieView, IVideoMovieView {

    private PostFavMoviePresenter postFavMoviePresenter;
    private MoviePresenter mMoviePresenter;
    private VideoMoviePresenter mVideoMoviePresenter;
    private VideoMovieAdapter mAdapter;
    private ActivityDetailBinding binding;
    private Movie movie = new Movie();

    public static ArrayList<Integer> listFav = new ArrayList<Integer>();
    private ArrayList<vn.phamthang.themovies.objects.Video.Result> listVideoTrailer;

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

        mMoviePresenter = new MoviePresenter(this);
        mVideoMoviePresenter = new VideoMoviePresenter(this);
        listVideoTrailer = new ArrayList<>();
        mAdapter = new VideoMovieAdapter(listVideoTrailer);

        mMoviePresenter.getFavMovie();

        binding.rcvVideoTrailer.setAdapter(mAdapter);
        binding.rcvVideoTrailer.setLayoutManager(
                new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.HORIZONTAL, false));

        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra("movie");
        int idMovie = movie.getId();
        Glide.with(binding.imgMovieDetail)
                .load(Constant.convertLinkImage(movie.getPosterPath()))
                .transform(new CenterCrop(), new RoundedCorners(10)) // crop and border
                .into(binding.imgMovieDetail);
        Glide.with(binding.imgBgMovie)
                .load(Constant.convertLinkImage2(movie.getBackdropPath()))
                .transform(new CenterCrop(), new RoundedCorners(10)) // crop and border
                .into(binding.imgBgMovie);
        binding.tvStar.setText(movie.getVoteAverage() + "");
        binding.tvStar.setText(movie.getVoteAverage() + "");
        binding.tvTitleMovie.setText(movie.getTitle());
        binding.tvCalendar.setText(movie.getReleaseDate());
        binding.tvMinus.setText(movie.getRuntime() + " Minutes");
        binding.tvGenre.setText(movie.getGenres().get(0).getName() + "");// 1list lấy tạm phần tử 0
        for (Integer value : listFav) {
            if (idMovie == value) {
                binding.imgAddToFav.setImageResource(R.drawable.ic_wishlisted);
            }
            binding.imgAddToFav.setImageResource(R.drawable.ic_whislist);
        }

        getVideoMovie(idMovie);

    }

    private void getVideoMovie(int idMovie) {
        mVideoMoviePresenter.getDetailMovie(idMovie);
    }

    private void initView() {
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.imgAddToFav.setOnClickListener(v -> {
            int mediaID = movie.getId();
            postFavMoviePresenter = new PostFavMoviePresenter(this);

            FavoriteMovieRequest request = new FavoriteMovieRequest(mediaID, "movie", true);
            postFavMoviePresenter.postFavMovie(request);
            listFav.add(mediaID);

        });
        binding.tabLayoutDetailMovie.setupWithViewPager(binding.viewPagerDetailMovie);
        ViewPagerDetailMovieAdapter viewPagerDetailMovieAdapter = new ViewPagerDetailMovieAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerDetailMovieAdapter.addFragment(new AboutMovieFragment(), "About");
        viewPagerDetailMovieAdapter.addFragment(new ReviewFragment(), "Review");
        viewPagerDetailMovieAdapter.addFragment(new CastFragment(), "Case");
        binding.viewPagerDetailMovie.setAdapter(viewPagerDetailMovieAdapter);
        binding.viewPagerDetailMovie.setOffscreenPageLimit(4);

        EventBus.getDefault().postSticky(new MessageEvent(movie));

    }


    @Override
    public void postMovieSuccess(ResponseBody responseBody) {
        Toast.makeText(this, "Đã thêm vào Wish List", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postMovieError(String error) {
        Toast.makeText(this, "lỗi", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void getMovieSuccess(BestMovieRespone response) {
        for (Result movie : response.getResults()) {
            listFav.add(movie.getId());
        }
    }

    @Override
    public void getMovieError(String error) {

    }

    @Override
    public void getVideoMovieSuccess(ResultVideoMovie responseVideoMovie) {
        listVideoTrailer.clear();
        mAdapter.updateData((ArrayList<vn.phamthang.themovies.objects.Video.Result>) responseVideoMovie.getResults());
    }

    @Override
    public void getVideoMovieError(String error) {

    }
}