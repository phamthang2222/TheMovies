package vn.phamthang.themovies.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;

import vn.phamthang.themovies.Interface.MostMovie.IMovieView;
import vn.phamthang.themovies.R;
import vn.phamthang.themovies.adapters.SpecialMovieAdapter;
import vn.phamthang.themovies.adapters.WhisListMovieAdapter;
import vn.phamthang.themovies.databinding.FragmentWhisListBinding;
import vn.phamthang.themovies.interactors.MovieInteractor;
import vn.phamthang.themovies.objects.BestMovieRespone;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.presenter.DetailMoviePresenter;
import vn.phamthang.themovies.presenter.MoviePresenter;
import vn.phamthang.themovies.view.DetailActivity;

public class WhisListFragment extends Fragment implements IMovieView, vn.phamthang.themovies.Interface.MostMovie.DetailMovie.IMovieDetailView,
        WhisListMovieAdapter.OnItemClickListener {
    private MoviePresenter mMoviePresenter;
    private DetailMoviePresenter mDetailMoviePresenter;
    private ArrayList<Result> mListMovie;
    private WhisListMovieAdapter mAdapter;

    FragmentWhisListBinding binding;

    public WhisListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWhisListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        getMostMovie();
    }

    private void getMostMovie() {
        mMoviePresenter.getFavMovie();
    }

    private void initData() {
        mMoviePresenter = new MoviePresenter(this);
        mDetailMoviePresenter = new DetailMoviePresenter(this);
        mListMovie = new ArrayList<>();
        mAdapter = new WhisListMovieAdapter(mListMovie, this);


        binding.rcvFavMovie.setAdapter(mAdapter);
        binding.rcvFavMovie.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void getDetailMovieSuccess(Movie response) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("movie", (Serializable) response);
        startActivity(intent);
    }

    @Override
    public void getDetailMovieError(String message) {

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
    public void onItemClick(int idMovie) {
        mDetailMoviePresenter.getDetailMovie(idMovie);
    }
}