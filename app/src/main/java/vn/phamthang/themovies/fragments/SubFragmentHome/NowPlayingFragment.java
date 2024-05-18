package vn.phamthang.themovies.fragments.SubFragmentHome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;

import vn.phamthang.themovies.Interface.MostMovie.IMovieView;
import vn.phamthang.themovies.adapters.ListCategoryMovieAdapter;
import vn.phamthang.themovies.databinding.FragmentNowPlayingBinding;
import vn.phamthang.themovies.objects.BestMovieRespone;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.presenter.DetailMoviePresenter;
import vn.phamthang.themovies.presenter.MoviePresenter;
import vn.phamthang.themovies.view.DetailActivity;

public class NowPlayingFragment extends Fragment implements IMovieView, vn.phamthang.themovies.Interface.MostMovie.DetailMovie.IMovieDetailView,
ListCategoryMovieAdapter.OnItemClickListener
{

    private MoviePresenter mMoviePresenter;
    private ArrayList<Result> mListMovie;
    private ListCategoryMovieAdapter mAdapter;
    private DetailMoviePresenter mDetailMoviePresenter;
    FragmentNowPlayingBinding binding;

    public NowPlayingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("NowPlayingFragment","OnCreate");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("NowPlayingFragment","onResume");

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e("NowPlayingFragment","onAttach");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("NowPlayingFragment","onDestroy");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNowPlayingBinding.inflate(getLayoutInflater());
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        getMovies();
    }

    private void getMovies() {
        mMoviePresenter.getNowPlayingMovie();
    }

    private void initData() {
        mDetailMoviePresenter = new DetailMoviePresenter(this);
        mMoviePresenter = new MoviePresenter(this);
        mListMovie = new ArrayList<>();
        mAdapter = new ListCategoryMovieAdapter(mListMovie,this);

        binding.rcvNowPlayingMovie.setAdapter(mAdapter);
        binding.rcvNowPlayingMovie.setLayoutManager(
                new GridLayoutManager(getContext(), 3)
        );
    }


    @Override
    public void getMovieSuccess(BestMovieRespone response) {
        mListMovie.clear();
        mAdapter.updateData((ArrayList<Result>) response.getResults());
    }

    @Override
    public void getMovieError(String error) {

    }

    @Override
    public void getDetailMovieSuccess(Movie response) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("movie",(Serializable)response);
        startActivity(intent);
    }

    @Override
    public void getDetailMovieError(String message) {

    }

    @Override
    public void onItemClick(int idMovie) {
        mDetailMoviePresenter.getDetailMovie(idMovie);
    }
}