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
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import vn.phamthang.themovies.Interface.FavMovieFireBase.IGetFavMovieFromFireBaseView;
import vn.phamthang.themovies.Interface.PostMovieToFireBase.IPostMovieToFireBaseView;
import vn.phamthang.themovies.custom_toast.SuccessfulToast;
import vn.phamthang.themovies.Interface.MostMovie.IMovieView;
import vn.phamthang.themovies.Interface.PostFavMovie.IPostFavMovieView;
import vn.phamthang.themovies.Interface.SimilarMovie.ISimilarMovieView;
import vn.phamthang.themovies.Interface.VideoMovie.IVideoMovieView;
import vn.phamthang.themovies.R;
import vn.phamthang.themovies.adapters.SimilarMovieAdapter;
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
import vn.phamthang.themovies.presenter.DetailMoviePresenter;
import vn.phamthang.themovies.presenter.FireBase.GetFavMovieFromFireBasePresenter;
import vn.phamthang.themovies.presenter.MoviePresenter;
import vn.phamthang.themovies.presenter.PostFavMoviePresenter;
import vn.phamthang.themovies.presenter.FireBase.PostMovieToFireBasePresenter;
import vn.phamthang.themovies.presenter.SimilarMoviePresenter;
import vn.phamthang.themovies.presenter.VideoMoviePresenter;
import vn.phamthang.themovies.ultis.Constant;
import vn.phamthang.themovies.ultis.DataManager;
import vn.phamthang.themovies.ultis.MessageEvent;

public class DetailActivity extends AppCompatActivity implements IPostFavMovieView, IMovieView, IVideoMovieView, ISimilarMovieView,
        SimilarMovieAdapter.OnItemClickListener, IPostMovieToFireBaseView, vn.phamthang.themovies.Interface.MostMovie.DetailMovie.IMovieDetailView {

    private PostFavMoviePresenter postFavMoviePresenter;
    private PostMovieToFireBasePresenter mPostMovieToFireBasePresenter;
    private MoviePresenter mMoviePresenter;
    private DetailMoviePresenter mDetailMoviePresenter;
    private VideoMoviePresenter mVideoMoviePresenter;
    private SimilarMoviePresenter mSimilarMoviePresenter;
    private VideoMovieAdapter mVideoMovieAdapter;
    private SimilarMovieAdapter mSimilarMovieAdapter;

    private ActivityDetailBinding binding;
    private Movie movie = new Movie();
    private ArrayList<vn.phamthang.themovies.objects.Video.Result> listVideoTrailer;
    private ArrayList<Result> listSimilarMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.prime_color));

//        Constant.wishListMovieLocal = DataManager.loadFavoriteMovie(this);
        initData();
        initView();

    }

    private void initData() {
        //----------------------------------------------------------------------------------
        //presenter
        mMoviePresenter = new MoviePresenter(this);
        mVideoMoviePresenter = new VideoMoviePresenter(this);
        mSimilarMoviePresenter = new SimilarMoviePresenter(this);
        mDetailMoviePresenter = new DetailMoviePresenter(this);
        mPostMovieToFireBasePresenter = new PostMovieToFireBasePresenter(this);

        //----------------------------------------------------------------------------------
        //array list
        listVideoTrailer = new ArrayList<>();
        listSimilarMovie = new ArrayList<>();
        //----------------------------------------------------------------------------------
        //adapter
        mVideoMovieAdapter = new VideoMovieAdapter(listVideoTrailer);
        mSimilarMovieAdapter = new SimilarMovieAdapter(listSimilarMovie, this);
        mMoviePresenter.getFavMovie();
        //----------------------------------------------------------------------------------
        //recyclerview
        binding.rcvVideoTrailer.setAdapter(mVideoMovieAdapter);
        binding.rcvVideoTrailer.setLayoutManager(
                new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.HORIZONTAL, false));

        binding.rcvSimilarMovie.setAdapter(mSimilarMovieAdapter);
        binding.rcvSimilarMovie.setLayoutManager(
                new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
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
        getSimilarMovie(idMovie);
    }

    private void getVideoMovie(int idMovie) {
        mVideoMoviePresenter.getDetailMovie(idMovie);
    }

    private void getSimilarMovie(int idMovie) {
        mSimilarMoviePresenter.getSimilar(idMovie);
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
////            ---------------------------------------------------------------------------------------------------------
//            if (Constant.wishListMovieLocal == null) {
//                Constant.wishListMovieLocal = new ArrayList<>();
//            }
//            if (!isCheckFav(mediaID)) {
//                Constant.wishListMovieLocal.add(requestAddToFav);
//                DataManager.saveFavoriteMovie(this, Constant.wishListMovieLocal);
//                postFavMoviePresenter.postFavMovie(requestAddToFav);
//                binding.imgAddToFav.setImageResource(R.drawable.ic_wishlisted);
//            } else {
//                postFavMoviePresenter.postFavMovie(requestRemoveFromFav);
//                Constant.wishListMovieLocal.removeIf(movieRequest -> movieRequest.getMedia_id() == mediaID);
//                DataManager.saveFavoriteMovie(this, Constant.wishListMovieLocal);
//                binding.imgAddToFav.setImageResource(R.drawable.ic_whislist);
//            }
            //sử dụng FireBase để quản lí wish list của nhiều user
            //---------------------------------------------------------------------------------------------------------
            mPostMovieToFireBasePresenter.PostMovieToFireBase(movie);

        });
        binding.tabLayoutDetailMovie.setupWithViewPager(binding.viewPagerDetailMovie);
        ViewPagerDetailMovieAdapter viewPagerDetailMovieAdapter = new ViewPagerDetailMovieAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerDetailMovieAdapter.addFragment(new AboutMovieFragment(), "About");
        viewPagerDetailMovieAdapter.addFragment(new ReviewFragment(), "Review");
        viewPagerDetailMovieAdapter.addFragment(new CastFragment(), "Cast");
        binding.viewPagerDetailMovie.setAdapter(viewPagerDetailMovieAdapter);
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

    @Override
    public void getSimilarMovieSuccess(BestMovieRespone response) {
        listSimilarMovie.clear();
        mSimilarMovieAdapter.updateData((ArrayList<Result>) response.getResults());
        if (response.getResults().isEmpty()) {
            binding.emptySimilar.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void getSimilarMovieError(String error) {

    }

    private boolean isCheckFav(int idMovie) {
        if (Constant.wishListMovieLocal.isEmpty()) {
            return false;
        }
        for (Movie movieRequest : Constant.wishListMovieLocal) {
            if (movieRequest.getId() == idMovie) {
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

    @Override
    public void onClick(int idMovie) {
        mDetailMoviePresenter.getDetailMovie(idMovie);
    }

    @Override
    public void getDetailMovieSuccess(Movie response) {
        Intent intent = new Intent(this, DetailSimilarActivity.class);
        intent.putExtra("movie", (Serializable) response);
        startActivity(intent);
        Animatoo.INSTANCE.animateZoom(this);
    }

    @Override
    public void getDetailMovieError(String message) {

    }

    @Override
    public void IPostMovieToFireBaseSuccess(Movie request) {
        Toast.makeText(this, "thanh cong", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void IPostMovieToFireBaseError(String error) {
        Toast.makeText(this, "that bai", Toast.LENGTH_SHORT).show();

    }


}