package vn.phamthang.themovies.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import vn.phamthang.themovies.Interface.MostMovie.IMovieView;
import vn.phamthang.themovies.Interface.PostFavMovie.IPostFavMovieView;
import vn.phamthang.themovies.Interface.SimilarMovie.ISimilarMovieView;
import vn.phamthang.themovies.Interface.VideoMovie.IVideoMovieView;
import vn.phamthang.themovies.R;
import vn.phamthang.themovies.adapters.SimilarMovieAdapter;
import vn.phamthang.themovies.adapters.VideoMovieAdapter;
import vn.phamthang.themovies.adapters.ViewPagerAdapter.ViewPagerDetailMovieAdapter;
import vn.phamthang.themovies.custom_toast.SuccessfulToast;
import vn.phamthang.themovies.databinding.ActivityDetailBinding;
import vn.phamthang.themovies.databinding.ActivityDetailSimilarBinding;
import vn.phamthang.themovies.fragments.DetailMovie.AboutMovieFragment;
import vn.phamthang.themovies.fragments.DetailMovie.CastFragment;
import vn.phamthang.themovies.fragments.DetailMovie.ReviewFragment;
import vn.phamthang.themovies.fragments.DetailSimilarMovie.AboutSimilarMovieFragment;
import vn.phamthang.themovies.fragments.DetailSimilarMovie.CastSimilarFragment;
import vn.phamthang.themovies.fragments.DetailSimilarMovie.ReviewSimilarFragment;
import vn.phamthang.themovies.objects.BestMovieRespone;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.objects.Video.ResultVideoMovie;
import vn.phamthang.themovies.objects.request.MovieRequest;
import vn.phamthang.themovies.presenter.DetailMoviePresenter;
import vn.phamthang.themovies.presenter.MoviePresenter;
import vn.phamthang.themovies.presenter.PostFavMoviePresenter;
import vn.phamthang.themovies.presenter.SimilarMoviePresenter;
import vn.phamthang.themovies.presenter.VideoMoviePresenter;
import vn.phamthang.themovies.ultis.Constant;
import vn.phamthang.themovies.ultis.DataManager;
import vn.phamthang.themovies.ultis.MessageEvent;

public class DetailSimilarActivity extends AppCompatActivity implements IPostFavMovieView, IMovieView, IVideoMovieView
{

    private PostFavMoviePresenter postFavMoviePresenter;
    private MoviePresenter mMoviePresenter;
    private VideoMoviePresenter mVideoMoviePresenter;
    private VideoMovieAdapter mVideoMovieAdapter;

    private ActivityDetailSimilarBinding binding;
    private Movie movie = new Movie();
    private ArrayList<vn.phamthang.themovies.objects.Video.Result> listVideoTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailSimilarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.prime_color));

        Constant.wishListMovieLocal = DataManager.loadFavoriteMovie(this);
        initData();
        initView();

    }

    private void initData() {
        //----------------------------------------------------------------------------------
        //presenter
        mMoviePresenter = new MoviePresenter(this);
        mVideoMoviePresenter = new VideoMoviePresenter(this);
        //----------------------------------------------------------------------------------
        //array list
        listVideoTrailer = new ArrayList<>();
        //----------------------------------------------------------------------------------
        //adapter
        mVideoMovieAdapter = new VideoMovieAdapter(listVideoTrailer);
        mMoviePresenter.getFavMovie();
        //----------------------------------------------------------------------------------
        //recyclerview
        binding.rcvVideoTrailer.setAdapter(mVideoMovieAdapter);
        binding.rcvVideoTrailer.setLayoutManager(
                new LinearLayoutManager(DetailSimilarActivity.this, LinearLayoutManager.HORIZONTAL, false));

        //----------------------------------------------------------------------------------
        // set Data
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
        binding.tvGenre.setText(movie.getGenres().get(0).getName());// 1list lấy tạm phần tử 0
        if (!isCheckFav(idMovie)) {
            binding.imgAddToFav.setImageResource(R.drawable.ic_whislist);
        } else {
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

            MovieRequest requestAddToFav = new MovieRequest(mediaID, "movie", true);
            MovieRequest requestRemoveFromFav = new MovieRequest(mediaID, "movie", false);

//            sử dụng SharedPre để quản lí wish list
//            ---------------------------------------------------------------------------------------------------------
            if (Constant.wishListMovieLocal == null) {
                Constant.wishListMovieLocal = new ArrayList<>();
            }
            if (!isCheckFav(mediaID)) {
                Constant.wishListMovieLocal.add(requestAddToFav);
                DataManager.saveFavoriteMovie(this, Constant.wishListMovieLocal);
                postFavMoviePresenter.postFavMovie(requestAddToFav);
                binding.imgAddToFav.setImageResource(R.drawable.ic_wishlisted);
            } else {
                postFavMoviePresenter.postFavMovie(requestRemoveFromFav);
                Constant.wishListMovieLocal.removeIf(movieRequest -> movieRequest.getMedia_id() == mediaID);
                DataManager.saveFavoriteMovie(this, Constant.wishListMovieLocal);
                binding.imgAddToFav.setImageResource(R.drawable.ic_whislist);
            }
            //sử dụng FireBase để quản lí wish list của nhiều user
            //---------------------------------------------------------------------------------------------------------
//            PushObjectFirebase.pushMovieRequestToFirebase(requestAddToFav);
        });
        binding.tabLayoutDetailMovie.setupWithViewPager(binding.viewPagerDetailMovie);
        ViewPagerDetailMovieAdapter viewPagerDetailMovieAdapter2 = new ViewPagerDetailMovieAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerDetailMovieAdapter2.addFragment(new AboutSimilarMovieFragment(), "About");
        viewPagerDetailMovieAdapter2.addFragment(new ReviewSimilarFragment(), "Review");
        viewPagerDetailMovieAdapter2.addFragment(new CastSimilarFragment(), "Cast");
        binding.viewPagerDetailMovie.setAdapter(viewPagerDetailMovieAdapter2);
        binding.viewPagerDetailMovie.setOffscreenPageLimit(4);

        //truyền dữ liệu cho các sub fragment
        EventBus.getDefault().postSticky(new MessageEvent(movie));

    }

    @Override
    public void postMovieSuccess(ResponseBody responseBody) {
        new SuccessfulToast(this, "Cập nhật thành công!");
        SuccessfulToast.showToast();
    }

    @Override
    public void postMovieError(String error) {
        Toast.makeText(this, "lỗi", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void getMovieSuccess(BestMovieRespone response) {

    }

    @Override
    public void getMovieError(String error) {

    }

    @Override
    public void getVideoMovieSuccess(ResultVideoMovie responseVideoMovie) {
        listVideoTrailer.clear();
        mVideoMovieAdapter.updateData((ArrayList<vn.phamthang.themovies.objects.Video.Result>) responseVideoMovie.getResults());
        if (responseVideoMovie.getResults().isEmpty()) {
            binding.emptyVideo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getVideoMovieError(String error) {

    }



    private boolean isCheckFav(int idMovie) {
        if (Constant.wishListMovieLocal.isEmpty()) {
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