package vn.phamthang.themovies.fragments.DetailSimilarMovie;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import vn.phamthang.themovies.R;
import vn.phamthang.themovies.databinding.FragmentAboutMovieBinding;
import vn.phamthang.themovies.databinding.FragmentAboutSimilarMovieBinding;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.ultis.MessageEvent;


public class AboutSimilarMovieFragment extends Fragment {

    FragmentAboutSimilarMovieBinding binding;
    private static final String TAG = "AboutSimilarMovieFragment";
    private Movie movie;

    public AboutSimilarMovieFragment() {
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
        binding = FragmentAboutSimilarMovieBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onNoteCreated(MessageEvent event) {
        movie = event.getMovie();
        String overview = movie.getOverview();
        if (overview.isEmpty()) {
            binding.tvAboutSimilarMovie.setTextColor(getResources().getColor(R.color.red));
            binding.tvAboutSimilarMovie.setText("None information");
        } else {
            binding.tvAboutSimilarMovie.setText(overview);
        }

    }
}