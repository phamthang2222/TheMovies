package vn.phamthang.themovies.fragments.SubFragmentHome;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import vn.phamthang.themovies.Interface.MostMovie.IMovieView;
import vn.phamthang.themovies.R;
import vn.phamthang.themovies.adapters.ListCategoryMovieAdapter;
import vn.phamthang.themovies.databinding.FragmentTopRateBinding;
import vn.phamthang.themovies.objects.BestMovieRespone;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.presenter.MoviePresenter;


public class TopRateFragment extends Fragment implements IMovieView {

    private MoviePresenter mMoviePresenter;
    private ArrayList<Result> mListMovie;
    private ListCategoryMovieAdapter mAdapter;

    FragmentTopRateBinding binding;

    public TopRateFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTopRateBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        getMovies();
    }

    private void getMovies() {
        mMoviePresenter.getTopRateMovie();
    }

    private void initData() {
        mMoviePresenter = new MoviePresenter(this);
        mListMovie = new ArrayList<>();
        mAdapter = new ListCategoryMovieAdapter(mListMovie);

        binding.rcvTopRateMovie.setAdapter(mAdapter);
        binding.rcvTopRateMovie.setLayoutManager(
                new GridLayoutManager(getContext(), 3)
        );

    }

    @Override
    public void getMovieSuccess(BestMovieRespone response) {
        mListMovie.clear();
        mAdapter.updateData((ArrayList<Result>) response.getResults());
        Log.d("TOP RATE", " "+response.getResults().toString());
    }

    @Override
    public void getMovieError(String error) {

    }
}