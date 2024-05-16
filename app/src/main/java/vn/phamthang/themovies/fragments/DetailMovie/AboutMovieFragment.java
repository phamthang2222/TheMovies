package vn.phamthang.themovies.fragments.DetailMovie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.phamthang.themovies.R;
import vn.phamthang.themovies.databinding.FragmentAboutMovieBinding;
import vn.phamthang.themovies.objects.Movie;


public class AboutMovieFragment extends Fragment {

    FragmentAboutMovieBinding binding;
    private Movie movie = new Movie();

    public AboutMovieFragment() {
        // Required empty public constructor
    }

    public static AboutMovieFragment newInstance(Movie movie) {
        AboutMovieFragment fragment = new AboutMovieFragment();
        Bundle args = new Bundle();
        args.putSerializable("movie", movie);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable("movie");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAboutMovieBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
    private void initData() {
        binding.tvAboutMovie.setText(movie.getOverview());
    }
}