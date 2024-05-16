package vn.phamthang.themovies.fragments.SubFragmentHome;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.phamthang.themovies.Interface.MostMovie.IMovieView;
import vn.phamthang.themovies.adapters.ListCategoryMovieAdapter;
import vn.phamthang.themovies.databinding.FragmentNowPlayingBinding;
import vn.phamthang.themovies.objects.BestMovieRespone;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.presenter.MoviePresenter;

public class NowPlayingFragment extends Fragment implements IMovieView {

    private MoviePresenter mMoviePresenter;
    private ArrayList<Result> mListMovie;
    private ListCategoryMovieAdapter mAdapter;

    FragmentNowPlayingBinding binding;

    public NowPlayingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mMoviePresenter = new MoviePresenter(this);
        mListMovie = new ArrayList<>();
        mAdapter = new ListCategoryMovieAdapter(mListMovie);
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
}