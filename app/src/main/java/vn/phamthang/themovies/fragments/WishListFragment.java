package vn.phamthang.themovies.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import vn.phamthang.themovies.Interface.MostMovie.IMovieView;
import vn.phamthang.themovies.Interface.PostFavMovie.IPostFavMovieView;
import vn.phamthang.themovies.R;
import vn.phamthang.themovies.adapters.WhisListMovieAdapter;
import vn.phamthang.themovies.databinding.FragmentWhisListBinding;
import vn.phamthang.themovies.objects.BestMovieRespone;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.objects.request.MovieRequest;
import vn.phamthang.themovies.presenter.DetailMoviePresenter;
import vn.phamthang.themovies.presenter.MoviePresenter;
import vn.phamthang.themovies.presenter.PostFavMoviePresenter;
import vn.phamthang.themovies.ultis.Constant;
import vn.phamthang.themovies.view.DetailActivity;

public class WishListFragment extends Fragment implements vn.phamthang.themovies.Interface.MostMovie.DetailMovie.IMovieDetailView,
        WhisListMovieAdapter.OnItemClickListener, IPostFavMovieView {
    private static final String TAG = "WishListFragment";
    private MoviePresenter mMoviePresenter;
    private DetailMoviePresenter mDetailMoviePresenter;
    private PostFavMoviePresenter postFavMoviePresenter;
    //    private ArrayList<Result> mListMovie;
    private ArrayList<Movie> mListFavMovie;
    private WhisListMovieAdapter mAdapter;

    FragmentWhisListBinding binding;

    public WishListFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");

    }

    @Override
    public void onResume() {
        super.onResume();
//        getMostMovie();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        binding = FragmentWhisListBinding.inflate(getLayoutInflater());
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");

        initData();
    }

    private void getMostMovie() {
        mMoviePresenter.getFavMovie();
    }

    private void initData() {
        mDetailMoviePresenter = new DetailMoviePresenter(this);
        postFavMoviePresenter = new PostFavMoviePresenter(this);
        mListFavMovie = new ArrayList<>();

        getListFavMovie();
        mAdapter = new WhisListMovieAdapter(mListFavMovie, this);
        binding.rcvFavMovie.setAdapter(mAdapter);
        binding.rcvFavMovie.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void getListFavMovie() {
        if (Constant.wishListMovieLocal != null) {
            for (Movie movieRequest : Constant.wishListMovieLocal) {
                int movieId = movieRequest.getId();
                mDetailMoviePresenter.getDetailMovie(movieId);
            }
        }
    }

    @Override
    public void getDetailMovieSuccess(Movie response) {

        mListFavMovie.add(response);
        mAdapter.updateData(response);

    }

    @Override
    public void getDetailMovieError(String message) {

    }

//    public void getMovieSuccess(BestMovieRespone response) {
//        mListMovie.clear();
//        mAdapter.updateData((ArrayList<Result>) response.getResults());
//        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation);
//        binding.rcvFavMovie.setLayoutAnimation(layoutAnimationController);
//    }


    @Override
    public void postMovieSuccess(ResponseBody responseBody) {
        Toast.makeText(getContext(), "Đã xoá khỏi WishList", Toast.LENGTH_SHORT).show();
//        getMostMovie();
    }

    @Override
    public void postMovieError(String error) {

    }

    @Override
    public void onItemClick(int idMovie, int layout) {
        if (layout == R.id.itemFavMovie) {
//            mDetailMoviePresenter.getDetailMovie(idMovie);
        } else if (layout == R.id.imgRemoveMovieFromList) {
            MovieRequest request = new MovieRequest(idMovie, "movie", false);
            postFavMoviePresenter.postFavMovie(request);
        }
    }

}