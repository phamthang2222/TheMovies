package vn.phamthang.themovies.fragments.DetailMovie;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import vn.phamthang.themovies.Interface.ReviewMovie.IReviewMovieView;
import vn.phamthang.themovies.R;
import vn.phamthang.themovies.adapters.ListReviewAdapter;
import vn.phamthang.themovies.databinding.FragmentReviewBinding;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.Review.Result;
import vn.phamthang.themovies.objects.Review.ReviewResponse;
import vn.phamthang.themovies.presenter.ReviewPresenter;
import vn.phamthang.themovies.ultis.MessageEvent;

public class ReviewFragment extends Fragment implements IReviewMovieView {

    private Movie movie;
    private int iDMovie;
    private static final String TAG = "ReviewPresenter";
    private ReviewPresenter mReviewPresenter;
    private ArrayList<Result> mListReview;
    private ListReviewAdapter mAdapter;


    FragmentReviewBinding binding;

    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart: ");
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReviewBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();

    }

    private void initData() {
        mReviewPresenter = new ReviewPresenter(this);
        mListReview = new ArrayList<>();
        mAdapter = new ListReviewAdapter(mListReview);

        binding.rcvListReview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        binding.rcvListReview.setAdapter(mAdapter);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onGetReViewMovie(MessageEvent event) {
        movie = event.getMovie();
        iDMovie = movie.getId();
        mReviewPresenter.getReviewMovie(iDMovie);
    }

    @Override
    public void getReviewSuccess(ReviewResponse reviewResponse) {
        mListReview.clear();
        mAdapter.updateData((ArrayList<Result>) reviewResponse.getResults());
    }

    @Override
    public void getReviewError(String error) {
        Toast.makeText(getContext(), "that bai", Toast.LENGTH_SHORT).show();
    }
}