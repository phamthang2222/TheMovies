package vn.phamthang.themovies.fragments.DetailMovie;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import vn.phamthang.themovies.R;
import vn.phamthang.themovies.databinding.FragmentAboutMovieBinding;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.ultis.MessageEvent;
import vn.phamthang.themovies.view.DetailActivity;


public class AboutMovieFragment extends Fragment {

    FragmentAboutMovieBinding binding;
    private static final String TAG = "AboutMovieFragment";
    private Movie movie;

    public AboutMovieFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.d(TAG, "onDestroy: ");

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(MessageEvent.class)) {
        }
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAboutMovieBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onNoteCreated(MessageEvent event) {
        movie = event.getMovie();
        binding.tvAboutMovie.setText(movie.getOverview() + "");
    }
}