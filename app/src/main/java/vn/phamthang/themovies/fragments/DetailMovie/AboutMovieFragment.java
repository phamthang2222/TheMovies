package vn.phamthang.themovies.fragments.DetailMovie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import vn.phamthang.themovies.R;
import vn.phamthang.themovies.databinding.FragmentAboutMovieBinding;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.ultis.MessageEvent;


public class AboutMovieFragment extends Fragment {

    FragmentAboutMovieBinding binding;
    private Movie movie;

    public AboutMovieFragment() {
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Subscribe
    public void onNoteCreated(MessageEvent event) {
        movie = event.getMovie();
        binding.tvAboutMovie.setText(movie.getOverview()+"");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAboutMovieBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

}