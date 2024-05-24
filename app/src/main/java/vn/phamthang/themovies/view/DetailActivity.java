package vn.phamthang.themovies.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
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
import vn.phamthang.themovies.databinding.ActivityDetailBinding;
import vn.phamthang.themovies.fragments.DetailMovie.AboutMovieFragment;
import vn.phamthang.themovies.fragments.DetailMovie.CastFragment;
import vn.phamthang.themovies.fragments.DetailMovie.ReviewFragment;
import vn.phamthang.themovies.objects.BestMovieRespone;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.objects.Video.ResultVideoMovie;
import vn.phamthang.themovies.objects.request.MovieRequest;
import vn.phamthang.themovies.presenter.MoviePresenter;
import vn.phamthang.themovies.presenter.PostFavMoviePresenter;
import vn.phamthang.themovies.presenter.VideoMoviePresenter;
import vn.phamthang.themovies.ultis.Constant;
import vn.phamthang.themovies.ultis.DataManager;
import vn.phamthang.themovies.ultis.MessageEvent;

public class DetailActivity extends AppCompatActivity implements IPostFavMovieView, IMovieView, IVideoMovieView {

    private PostFavMoviePresenter postFavMoviePresenter;
    private MoviePresenter mMoviePresenter;
    private VideoMoviePresenter mVideoMoviePresenter;
    private VideoMovieAdapter mAdapter;
    private ActivityDetailBinding binding;
    private Movie movie = new Movie();
    private ArrayList<vn.phamthang.themovies.objects.Video.Result> listVideoTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.prime_color));

        Constant.wishListMovieLocal = DataManager.loadFavoriteMovie(this);
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
        if (!isCheckFav(idMovie)) {
            binding.imgAddToFav.setImageResource(R.drawable.ic_whislist);
        }else{
            binding.imgAddToFav.setImageResource(R.drawable.ic_wishlisted);
        }

        getVideoMovie(idMovie);


    }

    private void getVideoMovie(int idMovie) {
        mVideoMoviePresenter.getDetailMovie(idMovie);
    }

    private void initView() {
        binding.btnBack.setOnClickListener(v -> {
            finish();
            Animatoo.INSTANCE.animateFade(this);
        });

        binding.imgAddToFav.setOnClickListener(v -> {
            int mediaID = movie.getId();
            postFavMoviePresenter = new PostFavMoviePresenter(this);

            MovieRequest request = new MovieRequest(mediaID, "movie", true);

            if (Constant.wishListMovieLocal == null) {

                Constant.wishListMovieLocal.add(request);
                DataManager.saveFavoriteMovie(this,Constant.wishListMovieLocal);

                postFavMoviePresenter.postFavMovie(request);
                binding.imgAddToFav.setImageResource(R.drawable.ic_wishlisted);
            } else {
                if (!isCheckFav(mediaID)) {

                    Constant.wishListMovieLocal.add(request);
                    DataManager.saveFavoriteMovie(this,Constant.wishListMovieLocal);

                    postFavMoviePresenter.postFavMovie(request);
                    binding.imgAddToFav.setImageResource(R.drawable.ic_wishlisted);
                }else{
                    MovieRequest requestRemove = new MovieRequest(mediaID, "movie", false);
                    postFavMoviePresenter.postFavMovie(requestRemove);

                    Constant.wishListMovieLocal.remove(requestRemove);
                    DataManager.saveFavoriteMovie(this,Constant.wishListMovieLocal);

                    binding.imgAddToFav.setImageResource(R.drawable.ic_whislist);
                }
            }

        });
        binding.tabLayoutDetailMovie.setupWithViewPager(binding.viewPagerDetailMovie);
        ViewPagerDetailMovieAdapter viewPagerDetailMovieAdapter = new ViewPagerDetailMovieAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerDetailMovieAdapter.addFragment(new AboutMovieFragment(), "About");
        viewPagerDetailMovieAdapter.addFragment(new ReviewFragment(), "Review");
        viewPagerDetailMovieAdapter.addFragment(new CastFragment(), "Cast");
        binding.viewPagerDetailMovie.setAdapter(viewPagerDetailMovieAdapter);
        binding.viewPagerDetailMovie.setOffscreenPageLimit(4);

        EventBus.getDefault().postSticky(new MessageEvent(movie));

    }


    @Override
    public void postMovieSuccess(ResponseBody responseBody) {
        Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void postMovieError(String error) {
        Toast.makeText(this, "lỗi", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void getMovieSuccess(BestMovieRespone response) {
//        for (Result movie : response.getResults()) {
//            listFav.add(movie.getId());
//        }
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

    private boolean isCheckFav(int idMovie) {
        if(Constant.wishListMovieLocal == null){
            return false;
        }
        for (MovieRequest movieRequest : Constant.wishListMovieLocal) {
            if (movieRequest.getMedia_id() == idMovie) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateFade(this);

    }
}