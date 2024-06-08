package vn.phamthang.themovies.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import vn.phamthang.themovies.Interface.PostMovieToFireBase.IPostMovieToFireBaseView;
import vn.phamthang.themovies.Interface.PostRatingToFireBase.IPostRatingView;
import vn.phamthang.themovies.Interface.RatingMovieFromFireBase.IGetRatingMovieView;
import vn.phamthang.themovies.Interface.RemoveMovieFromFireBase.IRemoveMovieFromFireBaseView;
import vn.phamthang.themovies.custom_toast.AddFavMovieToast;
import vn.phamthang.themovies.custom_toast.FailToast;
import vn.phamthang.themovies.custom_toast.RemoveMovieToast;
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
import vn.phamthang.themovies.objects.Genre;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.RatingMovie;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.objects.Video.ResultVideoMovie;
import vn.phamthang.themovies.presenter.FireBase.GetRatingMoviePresenter;
import vn.phamthang.themovies.presenter.FireBase.PostRatingMovieToFireBasePresenter;
import vn.phamthang.themovies.presenter.FireBase.RemoveMovieFromFireBasePresenter;
import vn.phamthang.themovies.presenter.MoviePresenter;
import vn.phamthang.themovies.presenter.FireBase.PostMovieToFireBasePresenter;
import vn.phamthang.themovies.presenter.SimilarMoviePresenter;
import vn.phamthang.themovies.presenter.VideoMoviePresenter;
import vn.phamthang.themovies.ultis.Constant;
import vn.phamthang.themovies.ultis.MessageEvent;

public class DetailActivity extends AppCompatActivity implements IPostFavMovieView, IMovieView, IVideoMovieView, ISimilarMovieView,
        SimilarMovieAdapter.OnItemClickListener, IGetRatingMovieView, IPostRatingView, IPostMovieToFireBaseView, IRemoveMovieFromFireBaseView {

    private PostMovieToFireBasePresenter mPostMovieToFireBasePresenter;
    private PostRatingMovieToFireBasePresenter mPostRatingMovieToFireBasePresenter;
    private RemoveMovieFromFireBasePresenter mRemoveMovieFromFireBasePresenter;
    private GetRatingMoviePresenter mGetRatingMoviePresenter;
    private MoviePresenter mMoviePresenter;
    //    private DetailMoviePresenter mDetailMoviePresenter;
    private VideoMoviePresenter mVideoMoviePresenter;
    private SimilarMoviePresenter mSimilarMoviePresenter;
    private VideoMovieAdapter mVideoMovieAdapter;
    private SimilarMovieAdapter mSimilarMovieAdapter;

    private ActivityDetailBinding binding;
    private Movie movie = new Movie();
    private Result similarMovie = new Result();
    private double progressValue;

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
        handleIntent(getIntent());


    }


    private void handleIntent(Intent intent) {
        if (intent != null && intent.hasExtra("similarMovie")) {
            similarMovie = (Result) intent.getSerializableExtra("similarMovie");
            if (similarMovie != null) {
                movie.setId(similarMovie.getId());
                movie.setOverview(similarMovie.getOverview());
                movie.setPosterPath(similarMovie.getPosterPath());
                movie.setBackdropPath(similarMovie.getBackdropPath());
                movie.setVoteAverage(similarMovie.getVoteAverage());
                movie.setTitle(similarMovie.getTitle());
                movie.setReleaseDate(similarMovie.getReleaseDate());
                movie.setRuntime(90);
                List<Genre> listGenre = new ArrayList<>(Arrays.asList(
                        new Genre("0", "Action"),
                        new Genre("0", "Drama")
                ));
                movie.setGenres(listGenre);
                updateMovieDetails();
            }
        }
    }

    private void updateMovieDetails() {
        int idMovie = movie.getId();
        Glide.with(binding.imgMovieDetail)
                .load(Constant.convertLinkImage(movie.getPosterPath()))
                .transform(new CenterCrop(), new RoundedCorners(10)) // cắt và bo góc
                .into(binding.imgMovieDetail);
        Glide.with(binding.imgBgMovie)
                .load(Constant.convertLinkImage2(movie.getBackdropPath()))
                .transform(new CenterCrop(), new RoundedCorners(10)) // cắt và bo góc
                .into(binding.imgBgMovie);
        binding.tvStar.setText(String.valueOf(movie.getVoteAverage()));
        binding.tvTitleMovie.setText(movie.getTitle());
        binding.tvCalendar.setText(movie.getReleaseDate());
        binding.tvMinus.setText(movie.getRuntime() + " Phút");
        binding.tvGenre.setText(movie.getGenres().get(0).getName());

        if (!isCheckFav(idMovie)) {
            binding.imgAddToFav.setImageResource(R.drawable.ic_whislist);
        } else {
            binding.imgAddToFav.setImageResource(R.drawable.ic_wishlisted);
        }

        getVideoMovie(idMovie);
        //ẩn similar movie
        binding.tvSimilar.setVisibility(View.GONE);
        binding.rcvSimilarMovie.setVisibility(View.GONE);
        binding.emptySimilar.setVisibility(View.GONE);
        getSimilarMovie(idMovie);
    }

    private void initData() {
        //----------------------------------------------------------------------------------
        //presenter
        mMoviePresenter = new MoviePresenter(this);
        mVideoMoviePresenter = new VideoMoviePresenter(this);
        mSimilarMoviePresenter = new SimilarMoviePresenter(this);
//        mDetailMoviePresenter = new DetailMoviePresenter(this);
        mRemoveMovieFromFireBasePresenter = new RemoveMovieFromFireBasePresenter(this);
        mPostMovieToFireBasePresenter = new PostMovieToFireBasePresenter(this);
        mPostRatingMovieToFireBasePresenter = new PostRatingMovieToFireBasePresenter(this);
        mGetRatingMoviePresenter = new GetRatingMoviePresenter(this);
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
        if (intent != null && intent.hasExtra("movie")) {
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
            getRating(idMovie);
        }
    }
    private void getRating(int idMovie){
        mGetRatingMoviePresenter.getRatingMovie(idMovie);
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
//            MovieRequest requestAddToFav = new MovieRequest(mediaID, "movie", true);
//            MovieRequest requestRemoveFromFav = new MovieRequest(mediaID, "movie", false);

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
            if (!isCheckFav(mediaID)) {
                Constant.wishListMovieLocal.add(movie);
                mPostMovieToFireBasePresenter.PostMovieToFireBase(movie);
            } else {
                Constant.wishListMovieLocal.remove(movie);
                mRemoveMovieFromFireBasePresenter.removeMovieFromFireBase(mediaID);
            }
        });

        binding.btnRating.setOnClickListener(v -> {
            showBottomSheet();
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
        binding.imgAddToFav.setImageResource(R.drawable.ic_whislist);
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
    public void IPostMovieToFireBaseSuccess(Movie request) {
        binding.imgAddToFav.setImageResource(R.drawable.ic_wishlisted);
        new AddFavMovieToast(this, "Add to wishlist").showToast();
    }

    @Override
    public void IPostMovieToFireBaseError(String error) {
        Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void IRemoveMovieFromFireBaseSuccess(String success) {
        binding.imgAddToFav.setImageResource(R.drawable.ic_whislist);
        new RemoveMovieToast(this, "Remove from wishlist").showToast();

    }

    @Override
    public void IRemoveMovieFromFireBaseError(String error) {
        new FailToast(this, "Fail").showToast();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateFade(this);
    }

    private void showBottomSheet() {

        View viewDialog = getLayoutInflater().inflate(R.layout.layout_bottomsheet_rating, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewDialog);

        SeekBar seekBar = viewDialog.findViewById(R.id.seekBar);
        TextView tvValue = viewDialog.findViewById(R.id.valueRating);
        Button btnRating = viewDialog.findViewById(R.id.btnRatingOke);

        // Đặt giá trị SeekBar ban đầu
        int initialProgress = (int) (progressValue * 10);
        seekBar.setProgress(initialProgress);
        tvValue.setText(String.format("%.1f", progressValue));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressValue = (double) progress / 10;
                tvValue.setText(String.format("%.1f", progressValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        bottomSheetDialog.show();

        btnRating.setOnClickListener(v -> {
            RatingMovie ratingMovie = new RatingMovie(movie.getId(),progressValue);
            mPostRatingMovieToFireBasePresenter.postRating(ratingMovie);
            bottomSheetDialog.dismiss();
        });

    }

    @Override
    public void onClick(Result result) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("similarMovie", (Serializable) result);
        startActivity(intent);
        Animatoo.INSTANCE.animateZoom(this);
    }

    @Override
    public void IPostRatingSuccess() {
        new SuccessfulToast(DetailActivity.this, "Rating successful").showToast();
    }

    @Override
    public void IPostRatingError() {

    }

    @Override
    public void getRatingMovieSuccess(double valueRating) {
        if(valueRating !=0 ){
            progressValue = valueRating;
        }else{
            progressValue = 0.0;
        }
    }

    @Override
    public void getRatingMovieError() {

    }
}